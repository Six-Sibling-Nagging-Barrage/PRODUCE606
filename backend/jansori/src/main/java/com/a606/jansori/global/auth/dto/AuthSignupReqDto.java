package com.a606.jansori.global.auth.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.a606.jansori.domain.member.domain.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignupReqDto {

	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
			message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;

	@NotBlank(message = "닉네임이 공백입니다.")
	@Pattern(regexp = "^(?!\\s)(?!.*\\s{2})[가-힣a-zA-Z0-9_\\s]{1,11}(?<!\\s)$", message = "올바른 형식이 아닙니다.")
	@Length(min = 2, max = 11, message = "닉네임은 2자 이상 11자 이하여야 합니다.")
	private String nickname;

	@Length(max = 200, message = "자기소개는 200자 이하여야 합니다.")
	private String bio;

	private List<Long> tags;

	public Member toMember(PasswordEncoder passwordEncoder, String imageUrl) {

		return Member.builder()
				.email(email)
				.password(passwordEncoder.encode(password))
				.memberRole(MemberRole.USER)
				.bio(this.bio)
				.memberState(MemberStatus.ACTIVE)
				.imageUrl(imageUrl)
				.nickname(this.nickname)
				.ticket(50L)
				.build();
	}


}
