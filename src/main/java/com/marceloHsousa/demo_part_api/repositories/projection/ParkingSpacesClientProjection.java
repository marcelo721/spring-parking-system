package com.marceloHsousa.demo_part_api.repositories.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ParkingSpacesClientProjection {

     String getNumberPlate();

     String getCarModel();

     String getColor();

     String getBrand();

     String getClientCpf();

     String getReceipt();


    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
     LocalDateTime getCheckInDate();

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
     LocalDateTime getCheckOutDate();

     String getParkingSpacesCode();

     BigDecimal getValue();

     BigDecimal getDiscount();
}
