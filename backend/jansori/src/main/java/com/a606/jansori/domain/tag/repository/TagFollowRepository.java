package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagFollowRepository extends JpaRepository<TagFollow, Long> {

  Optional<TagFollow> findTagFollowByTagAndMember(Tag tag, Member member);

  @Query("select tf from tag_follow as tf left join fetch tf.tag")
  Slice<TagFollow> findTagFollowsByMember(Member member, Pageable pageable);

  @Query(value = "select tf from tag_follow tf join fetch tf.tag tg join fetch tf.member m where tf.member = :member")
  List<TagFollow> findAllByMember(Member member);
}
