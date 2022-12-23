package com.densoft.portfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

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

    @Column(nullable = false, length = 60, name = "resume_path")
    private String resumePath;

    @Column(nullable = false, length = 200, name = "personal_statement")
    private String personalStatement;

    @Column(nullable = false, length = 1000)
    private String skills;

    @Column(length = 30)
    private String password;

    public User(String firstName, String lastName, String email, String phone, String socialMediaLinks, String resumePath, String personalStatement, String skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.socialMediaLinks = socialMediaLinks;
        this.resumePath = resumePath;
        this.personalStatement = personalStatement;
        this.skills = skills;
    }

}
