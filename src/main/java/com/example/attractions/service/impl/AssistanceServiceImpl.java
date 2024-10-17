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
 * <p>
 * Предоставляет бизнес-логику для выполнения операций CRUD над услугами сопровождения.
 * </p>
 *
 * @see com.example.attractions.service.AssistanceService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AssistanceServiceImpl implements AssistanceService {

    private final AssistanceRepository assistanceRepository;
    private final AssistanceMapper assistanceMapper;

    /**
     * Добавляет новую услугу сопровождения.
     *
     * @param assistanceDto DTO объекта услуги сопровождения, содержащий данные для создания.
     * @return Созданный объект {@link AssistanceDto} с присвоенным идентификатором.
     */
    @Override
    public AssistanceDto addAssistance(AssistanceDto assistanceDto) {
        log.info("Добавление услуги сопровождения: {}", assistanceDto.getType());
        Assistance assistance = assistanceMapper.toEntity(assistanceDto);
        Assistance savedAssistance = assistanceRepository.save(assistance);
        return assistanceMapper.toDto(savedAssistance);
    }

    /**
     * Обновляет существующую услугу сопровождения.
     *
     * @param id            Идентификатор услуги сопровождения, которую необходимо обновить.
     * @param assistanceDto DTO объекта услуги сопровождения, содержащий обновленные данные.
     * @return Обновленный объект {@link AssistanceDto}.
     * @throws NotFoundException если услуга с данным ID не найдена.
     */
    @Override
    public AssistanceDto updateAssistance(Long id, AssistanceDto assistanceDto) {
        log.info("Обновление услуги сопровождения с ID: {}", id);
        Assistance existingAssistance = assistanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assistance not found with ID: " + id));

        try {
            existingAssistance.setType(AssistanceType.valueOf(assistanceDto.getType()));
        } catch (IllegalArgumentException e) {
            log.error("Некорректный тип услуги сопровождения: {}", assistanceDto.getType());
            throw new IllegalArgumentException("Invalid assistance type: " + assistanceDto.getType());
        }

        existingAssistance.setShortDescription(assistanceDto.getShortDescription());
        existingAssistance.setExecutor(assistanceDto.getExecutor());

        Assistance updatedAssistance = assistanceRepository.save(existingAssistance);
        return assistanceMapper.toDto(updatedAssistance);
    }

    /**
     * Удаляет услугу сопровождения по ее идентификатору.
     *
     * @param id Идентификатор услуги сопровождения, которую необходимо удалить.
     * @throws NotFoundException если услуга с данным ID не найдена.
     */
    @Override
    public void deleteAssistance(Long id) {
        log.info("Удаление услуги сопровождения с ID: {}", id);
        if (!assistanceRepository.existsById(id)) {
            throw new NotFoundException("Assistance not found with ID: " + id);
        }
        assistanceRepository.deleteById(id);
    }

    /**
     * Получает услугу сопровождения по ее идентификатору.
     *
     * @param id Идентификатор услуги сопровождения.
     * @return Объект {@link AssistanceDto} с указанным идентификатором.
     * @throws NotFoundException если услуга с данным ID не найдена.
     */
    @Override
    public AssistanceDto getAssistanceById(Long id) {
        log.info("Получение услуги сопровождения по ID: {}", id);
        Assistance assistance = assistanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assistance not found with ID: " + id));
        return assistanceMapper.toDto(assistance);
    }

    /**
     * Получает список всех услуг сопровождения с поддержкой пагинации и сортировки.
     *
     * @param pageable Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link AssistanceDto}.
     */
    @Override
    public Page<AssistanceDto> getAllAssistances(Pageable pageable) {
        log.info("Получение всех услуг сопровождения");
        return assistanceRepository.findAll(pageable).map(assistanceMapper::toDto);
    }
}
