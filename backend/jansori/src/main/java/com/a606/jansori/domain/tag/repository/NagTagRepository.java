package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NagTagRepository extends JpaRepository<NagTag, Long> {

  @Query(value = "select count(distinct nt) from nag_tag nt "
      + "join nt.nag n "
      + "join nt.tag t "
      + "where n.member <> :member "
      + "and t in :tags ")
  Long countDistinctByNag_MemberNotAndTagIn(Member member, List<Tag> tags);

  @Query(value = "select nt from nag_tag nt "
      + "join nt.nag n "
      + "join nt.tag t "
      + "where n.member <> :member "
      + "and t in :tags ")
  List<NagTag> findByNag_MemberNotAndTagIn(Member member, List<Tag> tags, Pageable pageable);

  @Query(value = "select nt from nag_tag nt "
      + "join fetch nt.nag n "
      + "join fetch nt.tag t "
      + "left join fetch n.todos "
      + "where n.member = :member")
  List<NagTag> findByMember(Member member);
}
