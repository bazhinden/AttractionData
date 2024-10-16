package com.example.attractions.repository;

import com.example.attractions.model.Attraction;
import com.example.attractions.model.AttractionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для управления сущностями Attraction.
 */

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Page<Attraction> findByLocalityId(Long localityId, Pageable pageable);

    Page<Attraction> findAllByType(AttractionType type, Pageable pageable);
}
