package com.example.attractions.mapper;

import com.example.attractions.dto.AttractionDto;
import com.example.attractions.model.Assistance;
import com.example.attractions.model.Attraction;
import com.example.attractions.model.AttractionType;
import com.example.attractions.model.Locality;
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

    @Mapping(source = "localityId", target = "locality")
    @Mapping(target = "assistanceList", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "mapStringToType")
    Attraction toEntity(AttractionDto dto);

    @Named("mapAssistanceIds")
    default List<Long> mapAssistanceIds(List<Assistance> assistanceList) {
        if (assistanceList == null) return null;
        return assistanceList.stream().map(Assistance::getId).collect(Collectors.toList());
    }

    default Locality map(Long localityId) {
        if (localityId == null) {
            return null;
        }
        Locality locality = new Locality();
        locality.setId(localityId);
        return locality;
    }

    @Named("mapTypeToString")
    default String mapTypeToString(AttractionType type) {
        return type != null ? type.name() : null;
    }

    @Named("mapStringToType")
    default AttractionType mapStringToType(String type) {
        return type != null ? AttractionType.valueOf(type) : null;
    }
}
