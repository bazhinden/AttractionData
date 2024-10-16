package com.example.attractions.service;

import com.example.attractions.dto.LocalityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс сервиса для управления местоположениями.
 */

public interface LocalityService {
    LocalityDto addLocality(LocalityDto localityDto);

    LocalityDto updateLocality(Long id, LocalityDto localityDto);

    void deleteLocality(Long id);

    LocalityDto getLocalityById(Long id);

    Page<LocalityDto> getAllLocalities(Pageable pageable);
}
