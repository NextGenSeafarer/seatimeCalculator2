package com.example.seatimecalculator2.entity.user;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usr")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Enter valid email")
    @Email
    String email;
    @Column(name = "password")
    @Size(min = 8, message = "Password must be 8 symbols or more")
    String password;
    @Transient
    String passwordConfirm;
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(name = "first_name")
    String firstname;
    @Column(name = "last_name")
    String lastname;
    @Column(name = "registration_date_time", updatable = false)
    LocalDateTime registrationDateAndTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    List<SeaTimeEntity> seaTimeEntityList;

    public void addSeaTimeEntityToTheList(SeaTimeEntity seaTimeEntity) {
        if (seaTimeEntityList == null) {
            seaTimeEntityList = new ArrayList<>();
        }
        seaTimeEntityList.add(seaTimeEntity);
        seaTimeEntity.setUser(this);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
