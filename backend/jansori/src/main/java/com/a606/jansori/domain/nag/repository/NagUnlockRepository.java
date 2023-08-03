package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagUnlock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NagUnlockRepository extends JpaRepository<NagUnlock, Long> {

    Boolean existsByNagAndMember(Nag nag, Member member);

    List<NagUnlock> findNagUnlocksByNagIsInAndMember(List<Nag> nags, Member member);
}
