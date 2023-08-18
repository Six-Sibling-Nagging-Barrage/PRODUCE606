package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.tag.domain.Tag;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NagRepository extends JpaRepository<Nag, Long>, NagDynamicQueryRepository {

  @Query(value = "select n from nag n order by n.likeCount desc ")
  List<Nag> findByRandomNagsNotInTopRank(Pageable pageable);

  @Query(value = "select n from nag n join fetch n.member order by n.likeCount desc ")
  List<Nag> findByNagsOfNagBox(Pageable pageable);

  @Query(value = "select count (distinct n) from nag n "
      + "join n.tag as nt "
      + "join n.member as nm "
      + "where nm <> :member "
      + "and nt in :tags")
  Long countDistinctByNag_MemberNotAndTagIn(Member member, List<Tag> tags);

  @Query(value = "select n from nag n "
      + "join n.tag as nt "
      + "join n.member as nm "
      + "where nm <> :member "
      + "and nt in :tags")
  List<Nag> findByNag_MemberNotAndTagIn(Member member, List<Tag> tags, Pageable pageable);

  boolean existsByTag(Tag tag);
}
