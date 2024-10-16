package com.example.attractions.service;

import com.example.attractions.dto.AttractionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс сервиса для управления достопримечательностями.
 */

public interface AttractionService {
    AttractionDto addAttraction(AttractionDto attractionDto);

    Page<AttractionDto> getAllAttractions(String type, Pageable pageable);

    AttractionDto updateAttraction(Long id, AttractionDto attractionDto);

    void deleteAttraction(Long id);

    Page<AttractionDto> getAttractionsByLocality(Long localityId, Pageable pageable);
}