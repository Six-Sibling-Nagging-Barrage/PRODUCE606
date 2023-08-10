package com.a606.jansori.domain.member.repository;


import com.a606.jansori.domain.member.domain.Member;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Boolean existsByNickname(String nickname);
  Optional<Member> findByOauthIdentifier(String oauthIdentifier);
  Optional<Member> findMemberById(Long id);

  @Modifying
  @Query(value = "update member m set m.createdAt = :date, m.modifiedAt = :date where m = :member")
  Integer updateDate(@Param("member") Member member,@Param("date") LocalDateTime date);

  Optional<Member> findMemberByEmail(String email);

  boolean existsByEmail(String email);

}
