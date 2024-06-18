package com.marceloHsousa.demo_part_api.web.dto.parkingDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingCreateDto {

    @NotBlank
    @Size(min = 8 , max = 8)
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "the license plate format must follow the following pattern: 'xxx-0000'")
    private String numberPlate;

    @NotBlank
    private String carModel;

    @NotBlank
    private String color;

    @NotBlank
    private String brand;

    @Size(min = 11, max = 11)
    @CPF
    private String clientCpf;
}
