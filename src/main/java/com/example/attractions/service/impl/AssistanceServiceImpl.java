package com.example.attractions.service.impl;

import com.example.attractions.dto.AssistanceDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.AssistanceMapper;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.AssistanceType;
import com.example.attractions.repository.AssistanceRepository;
import com.example.attractions.service.AssistanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления услугами сопровождения.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AssistanceServiceImpl implements AssistanceService {

    private final AssistanceRepository assistanceRepository;
    private final AssistanceMapper assistanceMapper;

    @Override
    public AssistanceDto addAssistance(AssistanceDto assistanceDto) {
        log.info("Добавление услуги сопровождения: {}", assistanceDto.getType());
        Assistance assistance = assistanceMapper.toEntity(assistanceDto);
        Assistance savedAssistance = assistanceRepository.save(assistance);
        return assistanceMapper.toDto(savedAssistance);
    }

    @Override
    public AssistanceDto updateAssistance(Long id, AssistanceDto assistanceDto) {
        log.info("Обновление услуги сопровождения с ID: {}", id);
        Assistance existingAssistance = assistanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assistance not found"));

        existingAssistance.setType(AssistanceType.valueOf(assistanceDto.getType()));
        existingAssistance.setShortDescription(assistanceDto.getShortDescription());
        existingAssistance.setExecutor(assistanceDto.getExecutor());

        Assistance updatedAssistance = assistanceRepository.save(existingAssistance);
        return assistanceMapper.toDto(updatedAssistance);
    }

    @Override
    public void deleteAssistance(Long id) {
        log.info("Удаление услуги сопровождения с ID: {}", id);
        assistanceRepository.deleteById(id);
    }

    @Override
    public AssistanceDto getAssistanceById(Long id) {
        log.info("Получение услуги сопровождения по ID: {}", id);
        Assistance assistance = assistanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assistance not found"));
        return assistanceMapper.toDto(assistance);
    }

    @Override
    public Page<AssistanceDto> getAllAssistances(Pageable pageable) {
        log.info("Получение всех услуг сопровождения");
        return assistanceRepository.findAll(pageable).map(assistanceMapper::toDto);
    }
}
