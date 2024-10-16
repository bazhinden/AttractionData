package com.example.attractions.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * DTO для достопримечательностей.
 */

@Data
public class AttractionDto {
    private Long id;
    private String name;
    private Date creationDate;
    private String shortDescription;
    private String type;
    private Long localityId;
    private List<Long> assistanceIds;
}