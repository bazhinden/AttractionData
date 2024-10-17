package com.example.attractions.controller;

import com.example.attractions.dto.AssistanceDto;
import com.example.attractions.service.AssistanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления услугами сопровождения.
 * <p>
 * Предоставляет REST API для выполнения операций CRUD над услугами сопровождения.
 * </p>
 */
@RestController
@RequestMapping("/assistances")
@RequiredArgsConstructor
@Slf4j
public class AssistanceController {

    private final AssistanceService assistanceService;

    /**
     * Добавляет новую услугу сопровождения.
     *
     * @param assistanceDto DTO объекта услуги сопровождения, содержащий данные для создания.
     * @return Созданный объект {@link AssistanceDto} с присвоенным идентификатором.
     */
    @PostMapping
    public AssistanceDto addAssistance(@RequestBody AssistanceDto assistanceDto) {
        log.info("Добавление новой услуги сопровождения: {}", assistanceDto.getType());
        return assistanceService.addAssistance(assistanceDto);
    }

    /**
     * Получает услугу сопровождения по ее идентификатору.
     *
     * @param id Идентификатор услуги сопровождения.
     * @return Объект {@link AssistanceDto} с указанным идентификатором.
     * @throws com.example.attractions.exception.NotFoundException если услуга с данным ID не найдена.
     */
    @GetMapping("/{id}")
    public AssistanceDto getAssistanceById(@PathVariable Long id) {
        log.info("Получение услуги сопровождения по ID: {}", id);
        return assistanceService.getAssistanceById(id);
    }

    /**
     * Получает список всех услуг сопровождения с поддержкой пагинации и сортировки.
     *
     * @param pageable Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link AssistanceDto}.
     */
    @GetMapping
    public Page<AssistanceDto> getAllAssistances(
            @PageableDefault(sort = {"type"}) Pageable pageable) {
        log.info("Получение всех услуг сопровождения");
        return assistanceService.getAllAssistances(pageable);
    }

    /**
     * Обновляет существующую услугу сопровождения.
     *
     * @param id            Идентификатор услуги сопровождения, которую необходимо обновить.
     * @param assistanceDto DTO объекта услуги сопровождения, содержащий обновленные данные.
     * @return Обновленный объект {@link AssistanceDto}.
     * @throws com.example.attractions.exception.NotFoundException если услуга с данным ID не найдена.
     */
    @PutMapping("/{id}")
    public AssistanceDto updateAssistance(
            @PathVariable Long id,
            @RequestBody AssistanceDto assistanceDto) {
        log.info("Обновление услуги сопровождения с ID: {}", id);
        return assistanceService.updateAssistance(id, assistanceDto);
    }

    /**
     * Удаляет услугу сопровождения по ее идентификатору.
     *
     * @param id Идентификатор услуги сопровождения, которую необходимо удалить.
     * @throws com.example.attractions.exception.NotFoundException если услуга с данным ID не найдена.
     */
    @DeleteMapping("/{id}")
    public void deleteAssistance(@PathVariable Long id) {
        log.info("Удаление услуги сопровождения с ID: {}", id);
        assistanceService.deleteAssistance(id);
    }
}
