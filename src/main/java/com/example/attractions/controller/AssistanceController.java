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
 */

@RestController
@RequestMapping("/assistances")
@RequiredArgsConstructor
@Slf4j
public class AssistanceController {

    private final AssistanceService assistanceService;

    @PostMapping
    public AssistanceDto addAssistance(@RequestBody AssistanceDto assistanceDto) {
        log.info("Добавление новой услуги сопровождения: {}", assistanceDto.getType());
        return assistanceService.addAssistance(assistanceDto);
    }

    @GetMapping("/{id}")
    public AssistanceDto getAssistanceById(@PathVariable Long id) {
        log.info("Получение услуги сопровождения по ID: {}", id);
        return assistanceService.getAssistanceById(id);
    }

    @GetMapping
    public Page<AssistanceDto> getAllAssistances(@PageableDefault(sort = {"type"}) Pageable pageable) {
        log.info("Получение всех услуг сопровождения");
        return assistanceService.getAllAssistances(pageable);
    }

    @PutMapping("/{id}")
    public AssistanceDto updateAssistance(@PathVariable Long id, @RequestBody AssistanceDto assistanceDto) {
        log.info("Обновление услуги сопровождения с ID: {}", id);
        return assistanceService.updateAssistance(id, assistanceDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAssistance(@PathVariable Long id) {
        log.info("Удаление услуги сопровождения с ID: {}", id);
        assistanceService.deleteAssistance(id);
    }
}
