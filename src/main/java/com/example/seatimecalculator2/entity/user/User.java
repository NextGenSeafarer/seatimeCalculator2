package com.example.seatimecalculator2.entity.user;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
    @Column(name = "first_name")
    @NotBlank(message = "First name is not to be blank")
    String firstname;
    @Column(name = "last_name")
    @NotBlank(message = "Last name is not to be blank")
    String lastname;
    @Column(name = "email", unique = true)
    @NotBlank(message = "Enter valid email")
    @Email
    String email;
    @Column(name = "password")
    @Size(min = 8, message = "Password must be 8 symbols or more")
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(name = "registration_date_time")
    LocalDateTime registrationDateAndTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<SeaTimeEntity> seaTimeEntityList;

    public void addSeaTimeEntityToTheList(SeaTimeEntity seaTimeEntity) {
        if (seaTimeEntityList == null) {
            seaTimeEntityList = new ArrayList<>();
        }
        seaTimeEntityList.add(seaTimeEntity);
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
        return Objects.equals(id, user.id) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(registrationDateAndTime, user.registrationDateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, password, role, registrationDateAndTime);
    }
}
