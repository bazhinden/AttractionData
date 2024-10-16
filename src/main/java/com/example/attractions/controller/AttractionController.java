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
 */

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
@Slf4j
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping
    public AttractionDto addAttraction(@RequestBody AttractionDto attractionDto) {
        log.info("Добавление новой достопримечательности: {}", attractionDto.getName());
        return attractionService.addAttraction(attractionDto);
    }

    @GetMapping
    public Page<AttractionDto> getAllAttractions(
            @RequestParam(value = "type", required = false) String type,
            @PageableDefault(sort = {"name"}) Pageable pageable) {
        log.info("Получение всех достопримечательностей с фильтром по типу: {}", type);
        return attractionService.getAllAttractions(type, pageable);
    }

    @GetMapping("/locality/{localityId}")
    public Page<AttractionDto> getAttractionsByLocality(
            @PathVariable Long localityId,
            @PageableDefault(sort = {"name"}) Pageable pageable) {
        log.info("Получение достопримечательностей для местоположения с ID: {}", localityId);
        return attractionService.getAttractionsByLocality(localityId, pageable);
    }

    @PutMapping("/{id}")
    public AttractionDto updateAttraction(
            @PathVariable Long id,
            @RequestBody AttractionDto attractionDto) {
        log.info("Обновление достопримечательности с ID: {}", id);
        return attractionService.updateAttraction(id, attractionDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAttraction(@PathVariable Long id) {
        log.info("Удаление достопримечательности с ID: {}", id);
        attractionService.deleteAttraction(id);
    }
}
