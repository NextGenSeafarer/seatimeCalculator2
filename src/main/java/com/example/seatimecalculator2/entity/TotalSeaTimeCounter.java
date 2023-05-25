package com.example.seatimecalculator2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "total_sea_time")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TotalSeaTimeCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "counter")
    Long counter;
    @Column(name = "last_updated_at")
    @DateTimeFormat
    LocalDateTime last_updated_at;

    public void updateTotalCounter(int value) {
        counter = counter + value;
        last_updated_at = LocalDateTime.now();
    }


}
