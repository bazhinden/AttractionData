package com.example.attractions.repository;

import com.example.attractions.model.Assistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления сущностями Assistance.
 */

@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
}