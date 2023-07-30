package com.a606.jansori.domain.persona.repository;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoPersonaRepository extends JpaRepository<TodoPersona, Long> {
}
