package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NagLikeRepository extends JpaRepository<NagLike, Long> {

  Optional<NagLike> findNagLikeByNagAndMember(Nag nag, Member member);

  List<NagLike> findNagLikesByNagIsInAndMember(List<Nag> nags, Member member);

  Boolean existsByNagAndMember(Nag nag, Member member);
}
