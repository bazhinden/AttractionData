package com.example.attractions.dto;

import lombok.Data;
import java.util.List;

/**
 * DTO для местоположений.
 */

@Data
public class LocalityDto {
    private Long id;
    private String name;
    private String region;
    private Double latitude;
    private Double longitude;
    private String shortDescription;
    private List<Long> attractionIds;
    private List<Long> assistanceIds;
}