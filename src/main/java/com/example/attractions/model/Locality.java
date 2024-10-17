package com.example.attractions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
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
