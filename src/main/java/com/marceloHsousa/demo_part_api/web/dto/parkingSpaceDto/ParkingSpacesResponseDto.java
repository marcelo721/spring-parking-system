package com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpacesResponseDto {

    private Long id;
    private String code;
    private String Status;
}
