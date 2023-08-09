package com.a606.jansori.infra.message.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.infra.message.domain.FcmToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

  Optional<FcmToken> findFcmTokenByMember(Member member);
  List<FcmToken> findAllByMember(Member member);

}
