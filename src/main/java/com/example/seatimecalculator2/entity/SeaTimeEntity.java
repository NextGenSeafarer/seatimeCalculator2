package com.example.seatimecalculator2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "seatime")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeaTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "sign_on_date")
    LocalDate signOnDate;
    @Column(name = "sign_off_date")
    LocalDate signOffDate;
    @Column(name = "ship_name", length = 100)
    String shipName;
    @Column(name = "contractLength")
    String contractLength;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeaTimeEntity that = (SeaTimeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(signOnDate, that.signOnDate) && Objects.equals(signOffDate, that.signOffDate) && Objects.equals(shipName, that.shipName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, signOnDate, signOffDate, shipName);
    }
}
