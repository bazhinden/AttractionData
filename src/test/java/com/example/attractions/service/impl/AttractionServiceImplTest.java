package com.example.attractions.service.impl;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.AttractionMapper;
import com.example.attractions.model.Attraction;
import com.example.attractions.repository.AttractionRepository;
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

class AttractionServiceImplTest {

    @Mock
    private AttractionRepository attractionRepository;

    @Mock
    private AttractionMapper attractionMapper;

    @InjectMocks
    private AttractionServiceImpl attractionService;

    private AttractionDto attractionDto;
    private Attraction attraction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        attractionDto = new AttractionDto();
        attractionDto.setId(1L);
        attractionDto.setName("Attraction Name");

        attraction = new Attraction();
        attraction.setId(1L);
        attraction.setName("Attraction Name");
    }

    @Test
    void testAddAttraction() {
        when(attractionMapper.toEntity(any(AttractionDto.class))).thenReturn(attraction);
        when(attractionRepository.save(any(Attraction.class))).thenReturn(attraction);
        when(attractionMapper.toDto(any(Attraction.class))).thenReturn(attractionDto);

        AttractionDto result = attractionService.addAttraction(attractionDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(attractionRepository, times(1)).save(any(Attraction.class));
    }

    @Test
    void testUpdateAttraction_NotFound() {
        when(attractionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> attractionService.updateAttraction(1L, attractionDto));
        verify(attractionRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteAttraction() {
        doNothing().when(attractionRepository).deleteById(anyLong());
        attractionService.deleteAttraction(1L);
        verify(attractionRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetAllAttractions() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(attractionRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(attraction)));
        when(attractionMapper.toDto(any(Attraction.class))).thenReturn(attractionDto);

        Page<AttractionDto> result = attractionService.getAllAttractions(null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(attractionRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAttractionsByLocality() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(attractionRepository.findByLocalityId(anyLong(), eq(pageable))).thenReturn(new PageImpl<>(Collections.singletonList(attraction)));
        when(attractionMapper.toDto(any(Attraction.class))).thenReturn(attractionDto);

        Page<AttractionDto> result = attractionService.getAttractionsByLocality(1L, pageable);

        assertEquals(1, result.getTotalElements());
        verify(attractionRepository, times(1)).findByLocalityId(anyLong(), eq(pageable));
    }
}
