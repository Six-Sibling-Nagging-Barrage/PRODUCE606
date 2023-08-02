package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.todo.domain.Todo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoDynamicQueryRepository {

  List<Todo> findAllByMemberAndTodoAtIsOrderByCreatedAtDesc(Member member, LocalDate date);
}
