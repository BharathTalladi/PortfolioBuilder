package com.investment.employeerecurringplans.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_DETAILS")
public class User implements UserDetails {

    @Id
    private String id;

    private String name;

    private String emailId;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role roles;

    @OneToOne(mappedBy = "user")
    private UserRecurringPlanDetails recurringPlanDetails;

    // Get the authorities (roles) granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    // Get the username of the user
    @Override
    public String getUsername() {
        return id;
    }

    // Check if the user's account is not expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Check if the user's account is not locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Check if the user's credentials are not expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Check if the user is enabled
    @Override
    public boolean isEnabled() {
        return true;
    }
}
