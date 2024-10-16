package com.example.attractions.model;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Модель данных для достопримечательностей.
 */

@Data
@Entity
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date creationDate;
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    private AttractionType type;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @ManyToMany
    @JoinTable(
            name = "attraction_assistance",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "assistance_id")
    )
    private List<Assistance> assistanceList;
}