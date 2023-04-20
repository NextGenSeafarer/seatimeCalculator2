package com.example.seatimecalculator2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", length = 100)
    String name;
    @Column(name = "surname", length = 100)
    String surname;
    @Column(name = "registration_date")
    LocalDateTime registrationDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<SeaTimeEntity> seaTimeEntityList;

    public void addSeaTimeToUser(SeaTimeEntity seaTimeEntity) {
        if (seaTimeEntityList == null) {
            seaTimeEntityList = new ArrayList<>();
        }
        seaTimeEntityList.add(seaTimeEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(registrationDate, user.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, registrationDate);
    }
}
