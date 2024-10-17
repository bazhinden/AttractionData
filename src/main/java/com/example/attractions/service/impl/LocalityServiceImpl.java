package com.example.attractions.service.impl;

import com.example.attractions.dto.LocalityDto;
import com.example.attractions.exception.NotFoundException;
import com.example.attractions.mapper.LocalityMapper;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.Locality;
import com.example.attractions.repository.AssistanceRepository;
import com.example.attractions.repository.LocalityRepository;
import com.example.attractions.service.LocalityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса для управления местоположениями.
 * <p>
 * Предоставляет бизнес-логику для выполнения операций CRUD над местоположениями.
 * </p>
 *
 * @see com.example.attractions.service.LocalityService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocalityServiceImpl implements LocalityService {

    private final LocalityRepository localityRepository;
    private final AssistanceRepository assistanceRepository;
    private final LocalityMapper localityMapper;

    /**
     * Добавляет новое местоположение.
     *
     * @param localityDto DTO объекта местоположения, содержащий данные для создания.
     * @return Созданный объект {@link LocalityDto} с присвоенным идентификатором.
     */
    @Override
    public LocalityDto addLocality(LocalityDto localityDto) {
        log.info("Добавление местоположения: {}", localityDto.getName());
        Locality locality = localityMapper.toEntity(localityDto);
        if (localityDto.getAssistanceIds() != null) {
            List<Assistance> assistanceList = assistanceRepository.findAllById(localityDto.getAssistanceIds());
            locality.setAssistanceList(assistanceList);
        }
        Locality savedLocality = localityRepository.save(locality);
        return localityMapper.toDto(savedLocality);
    }

    /**
     * Обновляет существующее местоположение.
     *
     * @param id           Идентификатор местоположения, которое необходимо обновить.
     * @param localityDto  DTO объекта местоположения, содержащий обновленные данные.
     * @return Обновленный объект {@link LocalityDto}.
     * @throws NotFoundException если местоположение с данным ID не найдено.
     */
    @Override
    public LocalityDto updateLocality(Long id, LocalityDto localityDto) {
        log.info("Обновление местоположения с ID: {}", id);
        Locality existingLocality = localityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locality not found with ID: " + id));

        existingLocality.setShortDescription(localityDto.getShortDescription());

        if (localityDto.getAssistanceIds() != null) {
            List<Assistance> assistanceList = assistanceRepository.findAllById(localityDto.getAssistanceIds());
            existingLocality.setAssistanceList(assistanceList);
        }

        Locality updatedLocality = localityRepository.save(existingLocality);
        return localityMapper.toDto(updatedLocality);
    }

    /**
     * Удаляет местоположение по его идентификатору.
     *
     * @param id Идентификатор местоположения, которое необходимо удалить.
     * @throws NotFoundException если местоположение с данным ID не найдено.
     */
    @Override
    public void deleteLocality(Long id) {
        log.info("Удаление местоположения с ID: {}", id);
        if (!localityRepository.existsById(id)) {
            throw new NotFoundException("Locality not found with ID: " + id);
        }
        localityRepository.deleteById(id);
        log.info("Местоположение с ID: {} удалено", id);
    }

    /**
     * Получает местоположение по его идентификатору.
     *
     * @param id Идентификатор местоположения.
     * @return Объект {@link LocalityDto} с указанным идентификатором.
     * @throws NotFoundException если местоположение с данным ID не найдено.
     */
    @Override
    public LocalityDto getLocalityById(Long id) {
        log.info("Получение местоположения по ID: {}", id);
        Locality locality = localityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locality not found with ID: " + id));
        return localityMapper.toDto(locality);
    }

    /**
     * Получает список всех местоположений с поддержкой пагинации и сортировки.
     *
     * @param pageable Объект {@link Pageable}, содержащий информацию о пагинации и сортировке.
     * @return Страница {@link Page} объектов {@link LocalityDto}.
     */
    @Override
    public Page<LocalityDto> getAllLocalities(Pageable pageable) {
        log.info("Получение всех местоположений");
        return localityRepository.findAll(pageable).map(localityMapper::toDto);
    }
}
