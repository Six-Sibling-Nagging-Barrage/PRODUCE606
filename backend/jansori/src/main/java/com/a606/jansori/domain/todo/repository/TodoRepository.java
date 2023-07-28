package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.todo.domain.Todo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByMemberAndCreatedAtBetweenOrderByCreatedAtDesc(Member member,
      LocalDateTime today, LocalDateTime tomorrow);

  List<Todo> findAllByMemberOrderByCreatedAtDesc(Member member);

  @Query(value = "select distinct td from todo td " +
      "join fetch td.todoPersonas tp " +
      "join fetch td.member m " +
      "join fetch td.nag n " +
      "join td.todoTags tt " +
      "join fetch tt.tag t " +
      "join fetch tp.persona " +
      "join fetch tp.line " +
      "where t in :tags " +
      "and td.todo_id > :cursor " +
      "order by td.createdAt desc", nativeQuery = true)
  SliceImpl<Todo> findFollowingFeed(List<Tag> tags, Long cursor, Pageable pageable);
}
