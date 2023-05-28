package com.example.seatimecalculator2.entity;

import com.example.seatimecalculator2.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeaTimeEntity that = (SeaTimeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Column(name = "sign_off_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate signOffDate;
    @Column(name = "ship_name", length = 50)
    String shipName;

    @Column(name = "contract_length")
    String contractLength;
    @Column(name = "days_total")
    int daysTotal;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    User user;

}
