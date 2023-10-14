package com.example.seatimecalculator2.entity;

import com.example.seatimecalculator2.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate signOnDate;

    @Column(name = "sign_off_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate signOffDate;

    @Column(name = "ship_name", length = 50)
    String shipName;

    @Column(name = "contract_length")
    String contractLength;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeaTimeEntity that = (SeaTimeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(signOnDate, that.signOnDate) && Objects.equals(signOffDate, that.signOffDate) && Objects.equals(shipName, that.shipName) && Objects.equals(contractLength, that.contractLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, signOnDate, signOffDate, shipName, contractLength);
    }
}
