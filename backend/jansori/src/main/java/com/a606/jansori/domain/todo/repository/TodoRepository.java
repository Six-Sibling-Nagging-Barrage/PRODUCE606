package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.todo.domain.Todo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoDynamicQueryRepository {

  List<Todo> findAllByMemberAndTodoAtIsOrderByCreatedAtDesc(Member member, LocalDate date);

  List<Todo> findAllByMemberAndTodoAtIsAndDisplayTrueOrderByCreatedAtDesc(Member memberWhoWatched,
      LocalDate date);

  Long countTodosByFinishedIsTrue();

  @Query(value = "SELECT DISTINCT t.todoAt as todoAt "
      + "FROM todo t "
      + "WHERE t.member = :member "
      + "AND YEAR(todoAt) = :year "
      + "AND MONTH(todoAt) = :month")
  List<TodoAt> findAllTodoAtByMemberAndMonthAndYear(Member member, int year, int month);
}
