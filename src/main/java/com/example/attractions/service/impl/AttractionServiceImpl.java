package com.example.attractions.service.impl;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.AttractionMapper;
import com.example.attractions.model.Attraction;
import com.example.attractions.model.AttractionType;
import com.example.attractions.model.Locality;
import com.example.attractions.repository.AttractionRepository;
import com.example.attractions.repository.LocalityRepository;
import com.example.attractions.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления достопримечательностями.
 * <p>
 * Предоставляет бизнес-логику для выполнения операций CRUD над достопримечательностями.
 * </p>
 *
 * @see com.example.attractions.service.AttractionService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final LocalityRepository localityRepository;
    private final AttractionMapper attractionMapper;

    /**
     * Добавляет новую достопримечательность.
     *
     * @param attractionDto DTO объекта достопримечательности, содержащий данные для создания.
     * @return Созданный объект {@link AttractionDto} с присвоенным идентификатором.
     * @throws NotFoundException если местоположение с указанным ID не найдено.
     */
    @Override
    public AttractionDto addAttraction(AttractionDto attractionDto) {
        log.info("Добавление достопримечательности: {}", attractionDto.getName());

        Attraction attraction = attractionMapper.toEntity(attractionDto);

        if (attractionDto.getLocalityId() != null) {
            Locality locality = localityRepository.findById(attractionDto.getLocalityId())
                    .orElseThrow(() -> new NotFoundException("Locality not found with ID: " + attractionDto.getLocalityId()));
            attraction.setLocality(locality);
        } else {
            throw new NotFoundException("Locality ID is required to add an attraction.");
        }

        Attraction savedAttraction = attractionRepository.save(attraction);

        log.info("Достопримечательность добавлена с ID: {}", savedAttraction.getId());

        return attractionMapper.toDto(savedAttraction);
    }

    /**
     * Получает список всех достопримечательностей с опциональным фильтром по типу.
     * Поддерживает пагинацию и сортировку.
     *
     * @param type     Тип достопримечательности для фильтрации (опционально).
     * @param pageable Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link AttractionDto}.
     * @throws NotFoundException если указанный тип достопримечательности некорректен.
     */
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

    /**
     * Обновляет существующую достопримечательность.
     *
     * @param id             Идентификатор достопримечательности, которую необходимо обновить.
     * @param attractionDto  DTO объекта достопримечательности, содержащий обновленные данные.
     * @return Обновленный объект {@link AttractionDto}.
     * @throws NotFoundException если достопримечательность или местоположение с данным ID не найдены.
     */
    @Override
    public AttractionDto updateAttraction(Long id, AttractionDto attractionDto) {
        log.info("Обновление достопримечательности с ID: {}", id);

        Attraction existingAttraction = attractionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attraction not found with ID: " + id));

        existingAttraction.setName(attractionDto.getName());
        existingAttraction.setShortDescription(attractionDto.getShortDescription());

        if (attractionDto.getType() != null) {
            try {
                AttractionType attractionType = AttractionType.valueOf(attractionDto.getType().toUpperCase());
                existingAttraction.setType(attractionType);
            } catch (IllegalArgumentException e) {
                log.error("Некорректный тип достопримечательности: {}", attractionDto.getType());
                throw new NotFoundException("Invalid attraction type: " + attractionDto.getType());
            }
        }

        if (attractionDto.getLocalityId() != null) {
            Locality locality = localityRepository.findById(attractionDto.getLocalityId())
                    .orElseThrow(() -> new NotFoundException("Locality not found with ID: " + attractionDto.getLocalityId()));
            existingAttraction.setLocality(locality);
        }

        Attraction updatedAttraction = attractionRepository.save(existingAttraction);

        log.info("Достопримечательность с ID: {} обновлена", updatedAttraction.getId());

        return attractionMapper.toDto(updatedAttraction);
    }

    /**
     * Удаляет достопримечательность по ее идентификатору.
     *
     * @param id Идентификатор достопримечательности, которую необходимо удалить.
     * @throws NotFoundException если достопримечательность с данным ID не найдена.
     */
    @Override
    public void deleteAttraction(Long id) {
        log.info("Удаление достопримечательности с ID: {}", id);
        if (!attractionRepository.existsById(id)) {
            throw new NotFoundException("Attraction not found with ID: " + id);
        }
        attractionRepository.deleteById(id);
        log.info("Достопримечательность с ID: {} удалена", id);
    }

    /**
     * Получает список достопримечательностей, связанных с определенным местоположением.
     * Поддерживает пагинацию и сортировку.
     *
     * @param localityId Идентификатор местоположения.
     * @param pageable   Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link AttractionDto}.
     */
    @Override
    public Page<AttractionDto> getAttractionsByLocality(Long localityId, Pageable pageable) {
        log.info("Получение достопримечательностей для местоположения с ID: {}", localityId);
        Page<Attraction> attractions = attractionRepository.findByLocalityId(localityId, pageable);
        return attractions.map(attractionMapper::toDto);
    }
}
