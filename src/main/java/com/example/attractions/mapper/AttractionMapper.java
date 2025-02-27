package com.example.attractions.mapper;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.Attraction;
import com.example.attractions.model.AttractionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между Attraction и AttractionDto.
 */
@Mapper(componentModel = "spring")
public interface AttractionMapper {

    @Mapping(source = "locality.id", target = "localityId")
    @Mapping(target = "assistanceIds", source = "assistanceList", qualifiedByName = "mapAssistanceIds")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapTypeToString")
    AttractionDto toDto(Attraction attraction);

    @Mapping(target = "assistanceList", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "mapStringToType")
    @Mapping(target = "creationDate", ignore = true)
    Attraction toEntity(AttractionDto dto);

    @Named("mapAssistanceIds")
    default List<Long> mapAssistanceIds(List<Assistance> assistanceList) {
        if (assistanceList == null) return null;
        return assistanceList.stream()
                .map(Assistance::getId)
                .collect(Collectors.toList());
    }

    @Named("mapTypeToString")
    default String mapTypeToString(AttractionType type) {
        return type != null ? type.name() : null;
    }

    @Named("mapStringToType")
    default AttractionType mapStringToType(String type) {
        return type != null ? AttractionType.valueOf(type.toUpperCase()) : null;
    }
}
