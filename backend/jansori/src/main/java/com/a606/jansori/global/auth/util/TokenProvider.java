package com.a606.jansori.global.auth.util;

import com.a606.jansori.global.auth.dto.TokenResDto;
import com.a606.jansori.global.exception.domain.UnauthorizedException;
import com.a606.jansori.infra.redis.util.BlackListUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

  @Value("${jwt.authorities.key}")
  private String AUTHORITIES_KEY;
  @Value("${jwt.bearer.type}")
  private String BEARER_TYPE;
  @Value("${jwt.access-token-expire-time}")
  private long ACCESS_TOKEN_EXPIRE_TIME;
  @Value("${jwt.refresh-token-expire-time}")
  private long REFRESH_TOKEN_EXPIRE_TIME;

  private final Key key;

  @Autowired
  private BlackListUtil redisBlackListUtil;

  public TokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateRefreshTokenDto() {
    long now = (new Date()).getTime();

    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return refreshToken;

  }

  public TokenResDto generateAccessTokenDto(Authentication authentication, String refreshToken) {

    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

    String accessToken = Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return TokenResDto.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
        .refreshToken(refreshToken)
        .build();

  }

  public Authentication getAuthentication(String accessToken) {

    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {

      throw new UnauthorizedException();

    }

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UserDetails principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);

  }

  public boolean validateToken(String token) {

    try {

      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

      if (redisBlackListUtil.isBlackList(token)) {
        return false;
      }

      return true;
    } catch (ExpiredJwtException e) {
      log.debug("토큰 만료 예외 발생 : {}", e.getMessage());
    } catch (Exception e) {
      log.debug("인증 예외 발생 : {}", e.getMessage());
    }
    return false;
  }

  public Long getExpiration(String accessToken) {
    Date expiration = parseClaims(accessToken).getExpiration();

    Long now = new Date().getTime();
    return expiration.getTime() - now;
  }

  private Claims parseClaims(String accessToken) {

    try {

      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();

    } catch (ExpiredJwtException e) {

      return e.getClaims();

    }
  }
}