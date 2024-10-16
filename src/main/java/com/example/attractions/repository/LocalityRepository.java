package com.example.attractions.repository;

import com.example.attractions.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления сущностями Locality.
 */

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
}