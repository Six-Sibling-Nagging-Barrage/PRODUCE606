package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.todo.domain.Todo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByMemberAndCreatedAtBetweenOrderByCreatedAtDesc(Member member,
      LocalDateTime today, LocalDateTime tomorrow);

  List<Todo> findAllByMemberOrderByCreatedAtDesc(Member member);

  @Query(value = "SELECT distinct td FROM todo td " +
      "JOIN td.member m " +
      "JOIN td.nag n " +
      "JOIN td.todoTags tt " +
      "JOIN tt.tag t " +
      "WHERE t IN :tags " +
      "AND td.id < :cursor " +
      "AND td.display = true " +
      "ORDER BY td.id DESC ")
  Slice<Todo> findByFollowingTagsWithCursor(List<Tag> tags, Long cursor, Pageable pageable);

  @Query(value = "SELECT distinct td FROM todo td " +
      "JOIN td.member m " +
      "JOIN td.nag n " +
      "JOIN td.todoTags tt " +
      "JOIN tt.tag t " +
      "WHERE t IN :tags " +
      "AND td.display = true " +
      "ORDER BY td.id DESC ")
  Slice<Todo> findByFollowingTagsWithoutCursor(List<Tag> tags, Pageable pageable);

  @Query(value = "SELECT distinct td FROM todo td " +
      "JOIN td.member m " +
      "JOIN td.nag n " +
      "JOIN td.todoTags tt " +
      "JOIN tt.tag t " +
      "WHERE t = :tag " +
      "AND td.display = true " +
      "ORDER BY td.id DESC ")
  Slice<Todo> findByTagWithoutCursor(Tag tag, PageRequest of);

  @Query(value = "SELECT distinct td FROM todo td " +
      "JOIN td.member m " +
      "JOIN td.nag n " +
      "JOIN td.todoTags tt " +
      "JOIN tt.tag t " +
      "WHERE t = :tag " +
      "AND td.id < :cursor " +
      "AND td.display = true " +
      "ORDER BY td.id DESC ")
  Slice<Todo> findByTagWithCursor(Tag tag, Long cursor, PageRequest of);
}
