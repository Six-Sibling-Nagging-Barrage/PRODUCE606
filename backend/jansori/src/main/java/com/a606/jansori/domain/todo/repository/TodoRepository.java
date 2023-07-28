package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.todo.domain.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByMemberAndCreatedAtBetweenOrderByCreatedAtDesc(Member member, LocalDateTime today, LocalDateTime tomorrow);

    List<Todo> findAllByMemberOrderByCreatedAtDesc(Member member);

    @Query("select distinct td from todo td " +
            "join fetch td.todoTags tt " +
            "join td.todoPersonas tp " +
            "join tt.tag t " +
            "where t in :tags " +
            "and td.id > :cursor " +
            "order by td.createdAt desc ")
    Slice<Todo> findFollowingFeed(List<Tag> tags, Long cursor, Pageable pageable);
}
