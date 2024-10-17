package com.example.attractions.controller;

import com.example.attractions.dto.LocalityDto;
import com.example.attractions.service.LocalityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления местоположениями.
 * <p>
 * Предоставляет REST API для выполнения операций CRUD над местоположениями.
 * </p>
 */
@RestController
@RequestMapping("/localities")
@RequiredArgsConstructor
@Slf4j
public class LocalityController {

    private final LocalityService localityService;

    /**
     * Добавляет новое местоположение.
     *
     * @param localityDto DTO объекта местоположения, содержащий данные для создания.
     * @return Созданный объект {@link LocalityDto} с присвоенным идентификатором.
     */
    @PostMapping
    public LocalityDto addLocality(@RequestBody LocalityDto localityDto) {
        log.info("Добавление нового местоположения: {}", localityDto.getName());
        return localityService.addLocality(localityDto);
    }

    /**
     * Получает местоположение по его идентификатору.
     *
     * @param id Идентификатор местоположения.
     * @return Объект {@link LocalityDto} с указанным идентификатором.
     * @throws com.example.attractions.exception.NotFoundException если местоположение с данным ID не найдено.
     */
    @GetMapping("/{id}")
    public LocalityDto getLocalityById(@PathVariable Long id) {
        log.info("Получение местоположения по ID: {}", id);
        return localityService.getLocalityById(id);
    }

    /**
     * Получает список всех местоположений с поддержкой пагинации и сортировки.
     *
     * @param pageable Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link LocalityDto}.
     */
    @GetMapping
    public Page<LocalityDto> getAllLocalities(
            @PageableDefault(sort = {"name"}) Pageable pageable) {
        log.info("Получение всех местоположений");
        return localityService.getAllLocalities(pageable);
    }

    /**
     * Обновляет существующее местоположение.
     *
     * @param id           Идентификатор местоположения, которое необходимо обновить.
     * @param localityDto  DTO объекта местоположения, содержащий обновленные данные.
     * @return Обновленный объект {@link LocalityDto}.
     * @throws com.example.attractions.exception.NotFoundException если местоположение с данным ID не найдено.
     */
    @PutMapping("/{id}")
    public LocalityDto updateLocality(
            @PathVariable Long id,
            @RequestBody LocalityDto localityDto) {
        log.info("Обновление местоположения с ID: {}", id);
        return localityService.updateLocality(id, localityDto);
    }

    /**
     * Удаляет местоположение по его идентификатору.
     *
     * @param id Идентификатор местоположения, которое необходимо удалить.
     * @throws com.example.attractions.exception.NotFoundException если местоположение с данным ID не найдено.
     */
    @DeleteMapping("/{id}")
    public void deleteLocality(@PathVariable Long id) {
        log.info("Удаление местоположения с ID: {}", id);
        localityService.deleteLocality(id);
    }
}
