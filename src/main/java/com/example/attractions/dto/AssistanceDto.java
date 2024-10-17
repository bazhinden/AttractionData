package com.example.attractions.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * DTO для услуг сопровождения.
 */
@Data
public class AssistanceDto {

    private Long id;

    @NotBlank(message = "Тип услуги обязателен")
    private String type;

    private String shortDescription;

    private String executor;

    private List<Long> attractionIds;
}
