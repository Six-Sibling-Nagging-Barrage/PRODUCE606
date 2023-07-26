package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByMemberAndCreatedAtBetweenOrderByCreatedAtDesc(Member member, LocalDateTime today, LocalDateTime tomorrow);

    List<Todo> findAllByMemberOrderByCreatedAtDesc(Member member);
}
