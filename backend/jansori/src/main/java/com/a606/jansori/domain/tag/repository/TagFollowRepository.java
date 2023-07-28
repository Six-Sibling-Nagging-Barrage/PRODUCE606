package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.TagFollow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagFollowRepository extends JpaRepository<TagFollow, Long> {

  @Query(value = "select tf from tag_follow tf join tag tg, member m where member = :member")
  List<TagFollow> findAllByMember(Member member);
}
