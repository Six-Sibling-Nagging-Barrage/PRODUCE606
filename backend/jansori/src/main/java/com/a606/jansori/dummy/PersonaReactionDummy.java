package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.persona.domain.PersonaReaction;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.repository.PersonaReactionRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class PersonaReactionDummy {

  private final PersonaReactionRepository personaReactionRepository;

  public void createPersonaReactions(List<Member> members,
      List<TodoPersona> todoPersonas) {

    List<PersonaReaction> personaReactions = new ArrayList<>();

    int x = 0;

    for (int i = 0; i < members.size(); i++) {

      for (int j = 0; j < 100; j++) {
        x += 103;
        x %= todoPersonas.size();

        personaReactions.add(PersonaReaction.builder()
            .todoPersona(todoPersonas.get(x))
            .member(members.get(i))
            .todo(todoPersonas.get(x).getTodo())
            .build());

        todoPersonas.get(x).increaseLikeCount();

      }
    }
    personaReactionRepository.saveAll(personaReactions);
  }

}
