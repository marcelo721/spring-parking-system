package com.marceloHsousa.demo_part_api.web.dto.mapper;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.web.dto.parkingDto.ParkingCreateDto;
import com.marceloHsousa.demo_part_api.web.dto.parkingDto.ParkingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceClientMapper {

    public static ParkingSpaceClient toParkingSpaceClient(ParkingCreateDto dto){
        return new ModelMapper().map(dto, ParkingSpaceClient.class);
    }

    public static ParkingResponseDto toDto(ParkingSpaceClient spaceClient){
        return new ModelMapper().map(spaceClient, ParkingResponseDto.class);
    }
}
