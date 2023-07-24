package com.a606.jansori.domain.member.repository;


import com.a606.jansori.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean duplicateCheckByNickname(String nickname);
}
