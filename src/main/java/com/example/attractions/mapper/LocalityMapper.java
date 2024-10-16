package com.example.attractions.mapper;

import com.example.attractions.dto.LocalityDto;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.Attraction;
import com.example.attractions.model.Locality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между Locality и LocalityDto.
 */

@Mapper(componentModel = "spring")
public interface LocalityMapper {

    @Mapping(target = "attractionIds", source = "attractions", qualifiedByName = "mapAttractionIds")
    @Mapping(target = "assistanceIds", source = "assistanceList", qualifiedByName = "mapAssistanceIds")
    LocalityDto toDto(Locality locality);

    @Mapping(target = "attractions", ignore = true)
    @Mapping(target = "assistanceList", ignore = true)
    Locality toEntity(LocalityDto dto);

    @Named("mapAttractionIds")
    default List<Long> mapAttractionIds(List<Attraction> attractions) {
        if (attractions == null) return null;
        return attractions.stream().map(Attraction::getId).collect(Collectors.toList());
    }

    @Named("mapAssistanceIds")
    default List<Long> mapAssistanceIds(List<Assistance> assistanceList) {
        if (assistanceList == null) return null;
        return assistanceList.stream().map(Assistance::getId).collect(Collectors.toList());
    }
}
