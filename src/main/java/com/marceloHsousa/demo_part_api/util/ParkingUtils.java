package com.marceloHsousa.demo_part_api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

    private static final double FIRST_15_MINUTES = 5.00;
    private static final double FIRST_60_MINUTES = 9.25;
    private static final double ADDITIONAL_15_MINUTES = 1.75;

    public static String generateReceipt(){
        LocalDateTime date = LocalDateTime.now();
        String receipt = date.toString().substring(0, 19);
        return receipt.replace("-", "").replace(":", "").replace("T", "-");
    }

    public static BigDecimal calculateValue(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        long minutes = checkInDate.until(checkOutDate, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total =  FIRST_15_MINUTES;
        } else if (minutes <= 60) {
            total =  FIRST_60_MINUTES;
        } else {
            int additional = (int)Math.floor(minutes/15.0);
            total = FIRST_60_MINUTES * additional * (FIRST_60_MINUTES  + ADDITIONAL_15_MINUTES * additional);
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    private static final double DESCONTO_PERCENTUAL = 0.30;

    public static BigDecimal calculateDiscount(BigDecimal value, long numberOfTimes) {
        BigDecimal discount = null;

        if ( numberOfTimes % 10 != 0 || numberOfTimes == 0)
            discount = BigDecimal.valueOf(0);
        else {
            discount = value.multiply(new BigDecimal(DESCONTO_PERCENTUAL));
        }
        return discount.setScale(2, RoundingMode.HALF_EVEN);
    }
}
