package com.example.attractions.service;

import com.example.attractions.dto.AssistanceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс сервиса для управления услугами сопровождения.
 */

public interface AssistanceService {
    AssistanceDto addAssistance(AssistanceDto assistanceDto);

    AssistanceDto updateAssistance(Long id, AssistanceDto assistanceDto);

    void deleteAssistance(Long id);

    AssistanceDto getAssistanceById(Long id);

    Page<AssistanceDto> getAllAssistances(Pageable pageable);
}