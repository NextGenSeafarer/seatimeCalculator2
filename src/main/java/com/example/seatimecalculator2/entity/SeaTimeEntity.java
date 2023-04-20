package com.example.seatimecalculator2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "seatime")
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
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
