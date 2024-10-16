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
 */

@RestController
@RequestMapping("/localities")
@RequiredArgsConstructor
@Slf4j
public class LocalityController {

    private final LocalityService localityService;

    @PostMapping
    public LocalityDto addLocality(@RequestBody LocalityDto localityDto) {
        log.info("Добавление нового местоположения: {}", localityDto.getName());
        return localityService.addLocality(localityDto);
    }

    @GetMapping("/{id}")
    public LocalityDto getLocalityById(@PathVariable Long id) {
        log.info("Получение местоположения по ID: {}", id);
        return localityService.getLocalityById(id);
    }

    @GetMapping
    public Page<LocalityDto> getAllLocalities(@PageableDefault(sort = {"name"}) Pageable pageable) {
        log.info("Получение всех местоположений");
        return localityService.getAllLocalities(pageable);
    }

    @PutMapping("/{id}")
    public LocalityDto updateLocality(@PathVariable Long id, @RequestBody LocalityDto localityDto) {
        log.info("Обновление местоположения с ID: {}", id);
        return localityService.updateLocality(id, localityDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLocality(@PathVariable Long id) {
        log.info("Удаление местоположения с ID: {}", id);
        localityService.deleteLocality(id);
    }
}
