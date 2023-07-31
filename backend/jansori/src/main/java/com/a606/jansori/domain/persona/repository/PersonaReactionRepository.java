package com.a606.jansori.domain.persona.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.persona.domain.PersonaReaction;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaReactionRepository extends JpaRepository<PersonaReaction, Long> {

  Optional<PersonaReaction> findByMemberAndTodoPersona(Member member, TodoPersona todopersona);
}
