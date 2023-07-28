package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NagLikeRepository extends JpaRepository<NagLike, Long> {
    Optional<NagLike> findNagLikeByNagAndMember(Nag nag, Member member);
}
