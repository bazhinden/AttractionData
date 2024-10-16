package com.example.attractions.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO для услуг сопровождения.
 */

@Data
public class AssistanceDto {
    private Long id;
    private String type;
    private String shortDescription;
    private String executor;
    private List<Long> attractionIds;
}