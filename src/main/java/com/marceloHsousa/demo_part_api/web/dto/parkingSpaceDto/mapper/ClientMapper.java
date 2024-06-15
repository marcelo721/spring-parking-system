package com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.mapper;


import com.marceloHsousa.demo_part_api.entities.Client;
import com.marceloHsousa.demo_part_api.web.dto.clientDTO.ClientCreateDto;
import com.marceloHsousa.demo_part_api.web.dto.clientDTO.ClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto clientCreateDto){

        return new ModelMapper().map(clientCreateDto, Client.class);
    }

    public static ClientResponseDto toDto(Client client){

        return new ModelMapper().map(client, ClientResponseDto.class);
    }
}
