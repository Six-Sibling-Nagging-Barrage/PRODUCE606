package com.a606.jansori.global.auth.util;

import com.a606.jansori.global.auth.dto.TokenDto;
import com.a606.jansori.global.auth.exception.AuthUnauthorizedException;
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

  public TokenDto generateAccessTokenDto(Authentication authentication, String refreshToken) {

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

    return TokenDto.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
        .refreshToken(refreshToken)
        .build();

  }

  public Authentication getAuthentication(String accessToken) {

    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {

      throw new AuthUnauthorizedException();

    }

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UserDetails principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);

  }

  public boolean validateToken(String token) {

    // 이 부분 exception?

    try {

      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

      return true;

    } catch (ExpiredJwtException e) {
// 재발급
      log.info("만료된 JWT 토큰입니다.");

    } catch (Exception e) {
      log.info("지원할 수 없는 토큰입니다.");
    }

    return false;
  }

  private Claims parseClaims(String accessToken) {

    try {

      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();

    } catch (ExpiredJwtException e) {

      return e.getClaims();

    }
  }
}