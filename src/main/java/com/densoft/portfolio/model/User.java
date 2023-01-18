package com.densoft.portfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity  implements UserDetails {

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    @Column(nullable = false, length = 30, unique = true)
    private String phone;

    @Column(nullable = false, length = 200, name = "social_media_links")
    private String socialMediaLinks;

    @Column(nullable = false, length = 200, name = "resume_path")
    private String resumePath;

    @Column(nullable = false, length = 1000, name = "personal_statement")
    private String personalStatement;

    @Column(nullable = false, length = 1000)
    private String skills;

    @Column(length = 100)
    private String password;

    public User(String firstName,
                String lastName,
                String email,
                String phone,
                String socialMediaLinks,
                String resumePath,
                String personalStatement,
                String skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.socialMediaLinks = socialMediaLinks;
        this.resumePath = resumePath;
        this.personalStatement = personalStatement;
        this.skills = skills;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
