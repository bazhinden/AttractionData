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
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalityServiceImpl implements LocalityService {

    private final LocalityRepository localityRepository;
    private final AssistanceRepository assistanceRepository;
    private final LocalityMapper localityMapper;

    @Override
    public LocalityDto addLocality(LocalityDto localityDto) {
        log.info("Добавление местоположения: {}", localityDto.getName());
        Locality locality = localityMapper.toEntity(localityDto);
        if (localityDto.getAssistanceIds() != null) {
            List<Assistance> assistanceList = assistanceRepository.findAllById(
                    localityDto.getAssistanceIds());
            locality.setAssistanceList(assistanceList);
        }
        Locality savedLocality = localityRepository.save(locality);
        return localityMapper.toDto(savedLocality);
    }

    @Override
    public LocalityDto updateLocality(Long id, LocalityDto localityDto) {
        log.info("Обновление местоположения с ID: {}", id);
        Locality existingLocality = localityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locality not found"));

        existingLocality.setShortDescription(localityDto.getShortDescription());

        if (localityDto.getAssistanceIds() != null) {
            List<Assistance> assistanceList = assistanceRepository.findAllById(
                    localityDto.getAssistanceIds());
            existingLocality.setAssistanceList(assistanceList);
        }

        Locality updatedLocality = localityRepository.save(existingLocality);
        return localityMapper.toDto(updatedLocality);
    }

    @Override
    public void deleteLocality(Long id) {
        log.info("Удаление местоположения с ID: {}", id);
        localityRepository.deleteById(id);
    }

    @Override
    public LocalityDto getLocalityById(Long id) {
        log.info("Получение местоположения по ID: {}", id);
        Locality locality = localityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locality not found"));
        return localityMapper.toDto(locality);
    }

    @Override
    public Page<LocalityDto> getAllLocalities(Pageable pageable) {
        log.info("Получение всех местоположений");
        return localityRepository.findAll(pageable).map(localityMapper::toDto);
    }
}
