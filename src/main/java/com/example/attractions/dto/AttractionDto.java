package com.example.attractions.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO для достопримечательностей.
 */
@Data
public class AttractionDto {

    private Long id;

    @NotBlank(message = "Название достопримечательности обязательно")
    private String name;

    private LocalDateTime creationDate;

    private String shortDescription;

    @NotBlank(message = "Тип достопримечательности обязателен")
    private String type;

    @NotNull(message = "Идентификатор местоположения обязателен")
    private Long localityId;

    private List<Long> assistanceIds;
}
