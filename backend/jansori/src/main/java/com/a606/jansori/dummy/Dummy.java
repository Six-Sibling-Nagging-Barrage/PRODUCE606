package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.persona.domain.PersonaReaction;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.todo.domain.Todo;
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
@Profile("release")
public class Dummy implements CommandLineRunner {

  private final EntityManager entityManager;

  private final MemberDummy memberDummy;
  private final NagWithNagTagDummy nagWithNagTagDummy;
  private final TagDummy tagDummy;
  private final TagFollowDummy tagFollowDummy;
  private final NagLikeDummy nagLikeDummy;
  private final TodoWithTodoTagDummy todoWithTodoTagDummy;
  private final TodoPersonaDummy todoPersonaDummy;
  private final PersonaReactionDummy personaReactionDummy;

  private void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }

  @Override
  public void run(String... args) throws Exception {

    log.info("dummy insertion start");

    List<Member> members =  memberDummy.createMembers();
    List<Nag> nags = nagWithNagTagDummy.createNags(members);
    List<Tag> tags = tagDummy.createTags();
    List<TagFollow> tagFollows = tagFollowDummy.createTagFollows(tags, members);
    List<NagTag> nagTags = nagWithNagTagDummy.createNagTags(nags, tags);
    List<Todo> todos = todoWithTodoTagDummy.createTodosWithTodoTags(members, tags, nags);
    List<TodoPersona> todoPersonas = todoPersonaDummy.createTodoPersonas(todos);
    List<PersonaReaction> personaReactions = personaReactionDummy.createPersonaReactions(members,
        todoPersonas);


    nagLikeDummy.createNagLikes(members, nags);

    flushAndClear();

    log.info("dummy insertion complete");
  }
}
