package com.example.attractions.controller;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления достопримечательностями.
 * <p>
 * Предоставляет REST API для выполнения операций CRUD над достопримечательностями.
 * </p>
 */
@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
@Slf4j
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * Добавляет новую достопримечательность.
     *
     * @param attractionDto DTO объекта достопримечательности, содержащий данные для создания.
     * @return Созданный объект {@link AttractionDto} с присвоенным идентификатором.
     */
    @PostMapping
    public AttractionDto addAttraction(@RequestBody AttractionDto attractionDto) {
        log.info("Добавление новой достопримечательности: {}", attractionDto.getName());
        return attractionService.addAttraction(attractionDto);
    }

    /**
     * Получает список всех достопримечательностей с опциональным фильтром по типу.
     * Поддерживает пагинацию и сортировку.
     *
     * @param type     Тип достопримечательности для фильтрации (опционально).
     * @param pageable Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link AttractionDto}.
     */
    @GetMapping
    public Page<AttractionDto> getAllAttractions(
            @RequestParam(value = "type", required = false) String type,
            @PageableDefault(sort = {"name"}) Pageable pageable) {
        log.info("Получение всех достопримечательностей с фильтром по типу: {}", type);
        return attractionService.getAllAttractions(type, pageable);
    }

    /**
     * Получает список достопримечательностей, связанных с определенным местоположением.
     * Поддерживает пагинацию и сортировку.
     *
     * @param localityId Идентификатор местоположения.
     * @param pageable   Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link AttractionDto}.
     */
    @GetMapping("/locality/{localityId}")
    public Page<AttractionDto> getAttractionsByLocality(
            @PathVariable Long localityId,
            @PageableDefault(sort = {"name"}) Pageable pageable) {
        log.info("Получение достопримечательностей для местоположения с ID: {}", localityId);
        return attractionService.getAttractionsByLocality(localityId, pageable);
    }

    /**
     * Обновляет существующую достопримечательность.
     *
     * @param id             Идентификатор достопримечательности, которую необходимо обновить.
     * @param attractionDto  DTO объекта достопримечательности, содержащий обновленные данные.
     * @return Обновленный объект {@link AttractionDto}.
     * @throws com.example.attractions.exception.NotFoundException если достопримечательность с данным ID не найдена.
     */
    @PutMapping("/{id}")
    public AttractionDto updateAttraction(
            @PathVariable Long id,
            @RequestBody AttractionDto attractionDto) {
        log.info("Обновление достопримечательности с ID: {}", id);
        return attractionService.updateAttraction(id, attractionDto);
    }

    /**
     * Удаляет достопримечательность по ее идентификатору.
     *
     * @param id Идентификатор достопримечательности, которую необходимо удалить.
     * @throws com.example.attractions.exception.NotFoundException если достопримечательность с данным ID не найдена.
     */
    @DeleteMapping("/{id}")
    public void deleteAttraction(@PathVariable Long id) {
        log.info("Удаление достопримечательности с ID: {}", id);
        attractionService.deleteAttraction(id);
    }
}
