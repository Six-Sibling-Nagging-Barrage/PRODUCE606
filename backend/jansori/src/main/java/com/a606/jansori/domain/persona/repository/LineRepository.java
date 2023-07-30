package com.a606.jansori.domain.persona.repository;

import com.a606.jansori.domain.persona.domain.Line;
import com.a606.jansori.domain.persona.domain.Persona;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {

  Long countByPersona(Persona persona);

  List<Line> findLineByPersona(Persona persona, Pageable pageable);
}
