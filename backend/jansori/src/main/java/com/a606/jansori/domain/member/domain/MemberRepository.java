package com.a606.jansori.domain.member.domain;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<User> findByEmail(String email); //email과 pid로?

}
