package com.example.attractions.mapper;

import com.example.attractions.dto.AssistanceDto;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.AssistanceType;
import com.example.attractions.model.Attraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между Assistance и AssistanceDto.
 */

@Mapper(componentModel = "spring")
public interface AssistanceMapper {

    @Mapping(target = "attractionIds", source = "attractions", qualifiedByName = "mapAttractionIds")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapTypeToString")
    AssistanceDto toDto(Assistance assistance);

    @Mapping(target = "attractions", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "mapStringToType")
    Assistance toEntity(AssistanceDto dto);

    @Named("mapAttractionIds")
    default List<Long> mapAttractionIds(List<Attraction> attractions) {
        if (attractions == null) return null;
        return attractions.stream().map(Attraction::getId).collect(Collectors.toList());
    }

    @Named("mapTypeToString")
    default String mapTypeToString(AssistanceType type) {
        return type != null ? type.name() : null;
    }

    @Named("mapStringToType")
    default AssistanceType mapStringToType(String type) {
        return type != null ? AssistanceType.valueOf(type) : null;
    }
}
