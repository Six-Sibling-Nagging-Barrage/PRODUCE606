package com.a606.jansori.domain.nag.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.a606.jansori.domain.persona.domain.Line;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.persona.exception.LineNotFoundException;
import com.a606.jansori.domain.persona.repository.LineRepository;
import com.a606.jansori.global.util.RandomUtil;
import java.util.List;
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
  @Mock
  private RandomUtil randomUtil;
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

  @DisplayName("데이터베이스에 페르소나의 대사가 존재한다면 랜덤 대사를 가져오는데 성공한다.")
  @Test
  void Given_Valid_Persona_When_GetRandomLine_Then_Success() {
    //given
    Persona persona = mock(Persona.class);
    Line line = mock(Line.class);
    Pageable pageRequest = PageRequest.of(3, 1);
    given(lineRepository.countByPersona(persona)).willReturn(3L);
    given(randomUtil.generate(3L)).willReturn(3);
    given(lineRepository.findLineByPersona(persona, pageRequest))
        .willReturn(List.of(line));

    //then
    assertThat(nagRandomGenerator.getRandomLineOfPersona(persona))
        .isEqualTo(line);

    //verify
    verify(lineRepository, times(1)).countByPersona(persona);
    verify(randomUtil, times(1)).generate(3L);
    verify(lineRepository, times(1)).findLineByPersona(persona, pageRequest);
  }
}