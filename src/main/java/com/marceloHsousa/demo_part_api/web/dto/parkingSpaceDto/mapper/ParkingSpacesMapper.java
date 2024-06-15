package com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.mapper;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesDto;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesResponseDto;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpacesMapper {

    public static ParkingSpaces toParkingSpaces(ParkingSpacesDto dto){

        return new ModelMapper().map(dto, ParkingSpaces.class);
    }

    public static ParkingSpacesResponseDto toDto(ParkingSpaces parkingSpaces){

        return new ModelMapper().map(parkingSpaces, ParkingSpacesResponseDto.class);
    }
}
