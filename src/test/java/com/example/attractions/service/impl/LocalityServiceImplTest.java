package com.example.attractions.service.impl;

import com.example.attractions.dto.LocalityDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.LocalityMapper;
import com.example.attractions.model.Locality;
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

class LocalityServiceImplTest {

    @Mock
    private LocalityRepository localityRepository;

    @Mock
    private LocalityMapper localityMapper;

    @InjectMocks
    private LocalityServiceImpl localityService;

    private LocalityDto localityDto;
    private Locality locality;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        localityDto = new LocalityDto();
        localityDto.setId(1L);
        localityDto.setName("Locality Name");
        localityDto.setRegion("Region Name");

        locality = new Locality();
        locality.setId(1L);
        locality.setName("Locality Name");
        locality.setRegion("Region Name");
    }

    @Test
    void testAddLocality() {
        when(localityMapper.toEntity(any(LocalityDto.class))).thenReturn(locality);
        when(localityRepository.save(any(Locality.class))).thenReturn(locality);
        when(localityMapper.toDto(any(Locality.class))).thenReturn(localityDto);

        LocalityDto result = localityService.addLocality(localityDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(localityRepository, times(1)).save(any(Locality.class));
    }

    @Test
    void testUpdateLocality_NotFound() {
        when(localityRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                localityService.updateLocality(1L, localityDto)
        );
        assertEquals("Locality not found with ID: 1", exception.getMessage());
        verify(localityRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteLocality() {
        when(localityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(localityRepository).deleteById(1L);

        localityService.deleteLocality(1L);

        verify(localityRepository, times(1)).existsById(1L);
        verify(localityRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLocality_NotFound() {
        when(localityRepository.existsById(1L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                localityService.deleteLocality(1L)
        );
        assertEquals("Locality not found with ID: 1", exception.getMessage());
        verify(localityRepository, times(1)).existsById(1L);
        verify(localityRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testGetLocalityById_NotFound() {
        when(localityRepository.findById(anyLong())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                localityService.getLocalityById(1L)
        );
        assertEquals("Locality not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetAllLocalities() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(localityRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(locality)));
        when(localityMapper.toDto(any(Locality.class))).thenReturn(localityDto);

        Page<LocalityDto> result = localityService.getAllLocalities(pageable);

        assertEquals(1, result.getTotalElements());
        verify(localityRepository, times(1)).findAll(pageable);
    }
}
