package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface NagDynamicQueryRepository {

  Slice<Nag> findByNagsByMemberAndPages(Member member, Long cursor, Pageable pageable);
}
