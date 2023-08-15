package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.tag.domain.Tag;
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
@Profile("local")
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
  private final NotificationSettingDummy notificationSettingDummy;
  private final NotificationBoxDummy notificationBoxDummy;

  private void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }

  @Override
  public void run(String... args) throws Exception {

    log.info("dummy insertion start");

    List<Member> members =  memberDummy.createMembers();
    List<Tag> tags = tagDummy.createTags();
    tagFollowDummy.createTagFollows(tags, members);
    List<Nag> nags = nagWithNagTagDummy.createNags(members, tags);
    notificationBoxDummy.createNotificationBox(members);
    notificationSettingDummy.createNotificationSettings(members);
    List<Todo> todos = todoWithTodoTagDummy.createTodosWithTodoTags(members, tags);
    List<TodoPersona> todoPersonas = todoPersonaDummy.createTodoPersonas(todos);
    personaReactionDummy.createPersonaReactions(members,todoPersonas);


    nagLikeDummy.createNagLikes(members, nags);

    flushAndClear();

    log.info("dummy insertion complete");
  }
}
