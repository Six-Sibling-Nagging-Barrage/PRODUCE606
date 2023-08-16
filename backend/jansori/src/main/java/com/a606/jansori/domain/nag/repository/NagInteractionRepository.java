package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagInteraction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NagInteractionRepository extends JpaRepository<NagInteraction, Long> {

  Optional<NagInteraction> findNagInteractionByNagAndMember(Nag nag, Member member);
}
