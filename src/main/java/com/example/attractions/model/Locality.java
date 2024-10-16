package com.example.attractions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

/**
 * Модель данных для местоположений.
 */

@Data
@Entity
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //населенный пункт
    private String region;
    private Double latitude;
    private Double longitude;
    private String shortDescription;

    @OneToMany(mappedBy = "locality")
    private List<Attraction> attractions;

    @ManyToMany
    @JoinTable(
            name = "locality_assistance",
            joinColumns = @JoinColumn(name = "locality_id"),
            inverseJoinColumns = @JoinColumn(name = "assistance_id")
    )
    private List<Assistance> assistanceList;
}