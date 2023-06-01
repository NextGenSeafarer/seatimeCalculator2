package com.example.seatimecalculator2.entity.user.accountToken;


import com.example.seatimecalculator2.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "activation_token")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "token")
    String token;
    @Column(name = "created_at", nullable = false)
    LocalDateTime created_at;
    @Column(name = "expires_at", nullable = false)
    LocalDateTime expires_at;
    @Column(name = "confirmed_at")
    LocalDateTime confirmed_at;
    @Column(name = "counter_send_to_email")
    int counterSendToEmail;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    User user;

    public AccountToken(String token, LocalDateTime created_at, LocalDateTime expires_at, User user) {
        this.token = token;
        this.created_at = created_at;
        this.expires_at = expires_at;
        this.user = user;
    }
}
