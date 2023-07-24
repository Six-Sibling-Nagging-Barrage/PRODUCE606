package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.repository.NagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

    @Mock
    private NagRepository nagRepository;
    @InjectMocks
    private NagService nagService;

    @DisplayName()
}