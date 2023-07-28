package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NagTagRepository extends JpaRepository<NagTag, Long> {

  Long countDistinctByNag_MemberNotAndTagIn(Member member, List<Tag> tags);

  @Query(value = "select nt from nag_tag nt "
      + "join fetch nag n "
      + "join fetch tag t "
      + "where t in :tags ")
  List<NagTag> findByNag_MemberNotAndTagIn(Member member, List<Tag> tags, Pageable pageable);

}
