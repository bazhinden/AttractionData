package com.example.attractions.service.impl;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.AttractionMapper;
import com.example.attractions.model.Attraction;
import com.example.attractions.model.AttractionType;
import com.example.attractions.model.Locality;
import com.example.attractions.repository.AttractionRepository;
import com.example.attractions.repository.LocalityRepository;
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
    private LocalityRepository localityRepository;

    @Mock
    private AttractionMapper attractionMapper;

    @InjectMocks
    private AttractionServiceImpl attractionService;

    private AttractionDto attractionDto;
    private Attraction attraction;
    private Locality locality;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Настройка объекта Locality
        locality = new Locality();
        locality.setId(1L);
        locality.setName("Locality Name");
        locality.setRegion("Region Name");

        attractionDto = new AttractionDto();
        attractionDto.setId(1L);
        attractionDto.setName("Attraction Name");
        attractionDto.setType("MUSEUM");
        attractionDto.setLocalityId(locality.getId());

        attraction = new Attraction();
        attraction.setId(1L);
        attraction.setName("Attraction Name");
        attraction.setType(AttractionType.MUSEUM);
        attraction.setLocality(locality);
    }

    @Test
    void testAddAttraction() {
        when(attractionMapper.toEntity(any(AttractionDto.class))).thenReturn(attraction);
        when(localityRepository.findById(anyLong())).thenReturn(Optional.of(locality));
        when(attractionRepository.save(any(Attraction.class))).thenReturn(attraction);
        when(attractionMapper.toDto(any(Attraction.class))).thenReturn(attractionDto);

        AttractionDto result = attractionService.addAttraction(attractionDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(attractionMapper, times(1)).toEntity(any(AttractionDto.class));
        verify(localityRepository, times(1)).findById(locality.getId());
        verify(attractionRepository, times(1)).save(attraction);
        verify(attractionMapper, times(1)).toDto(attraction);
    }

    @Test
    void testAddAttraction_NoLocalityId() {
        AttractionDto dto = new AttractionDto();
        dto.setId(2L);
        dto.setName("Attraction 2");
        dto.setType("PARK");

        when(attractionMapper.toEntity(any(AttractionDto.class))).thenReturn(new Attraction());

        assertThrows(NotFoundException.class, () -> attractionService.addAttraction(dto));
        verify(attractionMapper, times(1)).toEntity(any(AttractionDto.class));
        verify(localityRepository, times(0)).findById(anyLong());
        verify(attractionRepository, times(0)).save(any(Attraction.class));
        verify(attractionMapper, times(0)).toDto(any(Attraction.class));
    }

    @Test
    void testUpdateAttraction_NotFound() {
        when(attractionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> attractionService.updateAttraction(1L, attractionDto));
        verify(attractionRepository, times(1)).findById(1L);
        verify(attractionRepository, times(0)).save(any(Attraction.class));
        verify(attractionMapper, times(0)).toDto(any(Attraction.class));
    }

    @Test
    void testDeleteAttraction_Success() {
        Long attractionId = 1L;
        when(attractionRepository.existsById(attractionId)).thenReturn(true);
        doNothing().when(attractionRepository).deleteById(attractionId);

        attractionService.deleteAttraction(attractionId);

        verify(attractionRepository, times(1)).existsById(attractionId);
        verify(attractionRepository, times(1)).deleteById(attractionId);
    }

    @Test
    void testDeleteAttraction_NotFound() {
        Long attractionId = 1L;
        when(attractionRepository.existsById(attractionId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> attractionService.deleteAttraction(attractionId));
        verify(attractionRepository, times(1)).existsById(attractionId);
        verify(attractionRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testGetAllAttractions() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(attractionRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(attraction)));
        when(attractionMapper.toDto(any(Attraction.class))).thenReturn(attractionDto);

        Page<AttractionDto> result = attractionService.getAllAttractions(null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(attractionRepository, times(1)).findAll(pageable);
        verify(attractionMapper, times(1)).toDto(attraction);
    }

    @Test
    void testGetAttractionsByLocality() {
        Long localityId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        when(attractionRepository.findByLocalityId(localityId, pageable)).thenReturn(new PageImpl<>(Collections.singletonList(attraction)));
        when(attractionMapper.toDto(any(Attraction.class))).thenReturn(attractionDto);

        Page<AttractionDto> result = attractionService.getAttractionsByLocality(localityId, pageable);

        assertEquals(1, result.getTotalElements());
        verify(attractionRepository, times(1)).findByLocalityId(localityId, pageable);
        verify(attractionMapper, times(1)).toDto(attraction);
    }
}
