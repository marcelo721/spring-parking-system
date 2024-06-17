package com.marceloHsousa.demo_part_api.web.dto.parking;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResponseDto {


    private String numberPlate;

    private String carModel;

    private String color;

    private String brand;

    private String clientCpf;

    private String receipt;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private String parkingSpacesCode;

    private BigDecimal value;

    private BigDecimal discount;

}
