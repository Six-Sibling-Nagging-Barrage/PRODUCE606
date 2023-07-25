package com.a606.jansori.domain.member.repository;


import com.a606.jansori.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByNickname(String nickname);
}