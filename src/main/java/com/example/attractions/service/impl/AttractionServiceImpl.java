package com.example.attractions.service.impl;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.AttractionMapper;
import com.example.attractions.model.Attraction;
import com.example.attractions.model.AttractionType;
import com.example.attractions.repository.AttractionRepository;
import com.example.attractions.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления достопримечательностями.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;

    @Override
    public AttractionDto addAttraction(AttractionDto attractionDto) {
        log.info("Добавление достопримечательности: {}", attractionDto.getName());
        Attraction attraction = attractionMapper.toEntity(attractionDto);
        Attraction savedAttraction = attractionRepository.save(attraction);
        return attractionMapper.toDto(savedAttraction);
    }

    @Override
    public Page<AttractionDto> getAllAttractions(String type, Pageable pageable) {
        log.info("Получение всех достопримечательностей с фильтром по типу: {}", type);
        Page<Attraction> attractions;
        if (type != null) {
            try {
                AttractionType attractionType = AttractionType.valueOf(type.toUpperCase());
                attractions = attractionRepository.findAllByType(attractionType, pageable);
            } catch (IllegalArgumentException e) {
                log.error("Некорректный тип достопримечательности: {}", type);
                throw new NotFoundException("Invalid attraction type: " + type);
            }
        } else {
            attractions = attractionRepository.findAll(pageable);
        }
        return attractions.map(attractionMapper::toDto);
    }

    @Override
    public AttractionDto updateAttraction(Long id, AttractionDto attractionDto) {
        log.info("Обновление достопримечательности с ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attraction not found"));
        attraction.setShortDescription(attractionDto.getShortDescription());
        Attraction updatedAttraction = attractionRepository.save(attraction);
        return attractionMapper.toDto(updatedAttraction);
    }

    @Override
    public void deleteAttraction(Long id) {
        log.info("Удаление достопримечательности с ID: {}", id);
        attractionRepository.deleteById(id);
    }

    @Override
    public Page<AttractionDto> getAttractionsByLocality(Long localityId, Pageable pageable) {
        log.info("Получение достопримечательностей для местоположения с ID: {}", localityId);
        Page<Attraction> attractions = attractionRepository.findByLocalityId(localityId, pageable);
        return attractions.map(attractionMapper::toDto);
    }
}
