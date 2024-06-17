package com.marceloHsousa.demo_part_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "client_parking_space")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpaceClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_receipt", nullable = false, unique = true, length = 15)
    private String receipt;

    @Column(name = "number_plate", nullable = false, length = 8)
    private String numberPlate;

    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "car_model", nullable = false, length = 45)
    private String carModel;

    @Column(name = "color", nullable = false, length = 45)
    private String color;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = true)
    private LocalDateTime checkOutDate;

    @Column(name = "value", columnDefinition = "decimal(7,2)")
    private BigDecimal value;

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_parking_spaces", nullable = false)
    private ParkingSpaces parkingSpaces;

    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSpaceClient that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
