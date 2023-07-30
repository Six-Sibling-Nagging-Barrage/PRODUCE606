package com.a606.jansori.domain.nag.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.persona.exception.LineNotFoundException;
import com.a606.jansori.domain.persona.repository.LineRepository;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class NagRandomGeneratorTest {
  @Mock
  private LineRepository lineRepository;
  @InjectMocks
  private NagRandomGenerator nagRandomGenerator;

  @DisplayName("데이터베이스에 페르소나의 대사가 존재하지 않는다면 랜덤 대사를 가져오는데 실패한다.")
  @Test
  void Given_Invalid_Persona_When_GetRandomLine_Then_Fail() {
    //given
    Persona persona = mock(Persona.class);
    given(lineRepository.countByPersona(persona)).willReturn(0L);
    Pageable pageRequest = PageRequest.of(1, 1);
    //then
    assertThrows(LineNotFoundException.class,
        () -> nagRandomGenerator.getRandomLineOfPersona(persona));

    //verify
    verify(lineRepository, times(1)).countByPersona(persona);
    verify(lineRepository, times(0)).findLineByPersona(persona, pageRequest);
  }
}