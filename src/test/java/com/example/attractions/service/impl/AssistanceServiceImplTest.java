package com.example.attractions.service.impl;

import com.example.attractions.dto.AssistanceDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.AssistanceMapper;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.AssistanceType;
import com.example.attractions.repository.AssistanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AssistanceServiceImplTest {

    @Mock
    private AssistanceRepository assistanceRepository;

    @Mock
    private AssistanceMapper assistanceMapper;

    @InjectMocks
    private AssistanceServiceImpl assistanceService;

    private AssistanceDto assistanceDto;
    private Assistance assistance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        assistanceDto = new AssistanceDto();
        assistanceDto.setId(1L);
        assistanceDto.setType("GUIDE");
        assistanceDto.setShortDescription("Description");

        assistance = new Assistance();
        assistance.setId(1L);
        assistance.setType(AssistanceType.GUIDE);
        assistance.setShortDescription("Description");
    }

    @Test
    void testAddAssistance() {
        when(assistanceMapper.toEntity(any(AssistanceDto.class))).thenReturn(assistance);
        when(assistanceRepository.save(any(Assistance.class))).thenReturn(assistance);
        when(assistanceMapper.toDto(any(Assistance.class))).thenReturn(assistanceDto);

        AssistanceDto result = assistanceService.addAssistance(assistanceDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(assistanceRepository, times(1)).save(any(Assistance.class));
    }

    @Test
    void testUpdateAssistance_NotFound() {
        when(assistanceRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> assistanceService.updateAssistance(1L, assistanceDto));
        verify(assistanceRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteAssistance() {
        doNothing().when(assistanceRepository).deleteById(anyLong());
        assistanceService.deleteAssistance(1L);
        verify(assistanceRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetAssistanceById_NotFound() {
        when(assistanceRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> assistanceService.getAssistanceById(1L));
    }

    @Test
    void testGetAllAssistances() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(assistanceRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(assistance)));
        when(assistanceMapper.toDto(any(Assistance.class))).thenReturn(assistanceDto);

        Page<AssistanceDto> result = assistanceService.getAllAssistances(pageable);

        assertEquals(1, result.getTotalElements());
        verify(assistanceRepository, times(1)).findAll(pageable);
    }
}
