package com.example.attractions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

/**
 * Модель данных для услуг сопровождения.
 */

@Data
@Entity
public class Assistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssistanceType type;

    private String shortDescription;
    private String executor;

    @ManyToMany(mappedBy = "assistanceList")
    private List<Attraction> attractions;
}