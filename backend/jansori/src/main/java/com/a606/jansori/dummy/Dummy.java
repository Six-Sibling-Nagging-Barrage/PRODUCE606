package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
@Profile("local")
public class Dummy implements CommandLineRunner {

  private final EntityManager entityManager;

  private final MemberDummy memberDummy;

  private final NagLikeDummy nagLikeDummy;

  private void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }

  @Override
  public void run(String... args) throws Exception {

    log.info("dummy insertion start");

    List<Member> members =  memberDummy.createMembers();

    List<Nag> nags = nagDummy.createNags();

    nagLikeDummy.createNagLikes(members, nags);

    flushAndClear();

    log.info("dummy insertion complete");
  }
}
