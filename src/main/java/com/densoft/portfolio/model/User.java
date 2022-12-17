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
    @Column(name = "other_name", nullable = false, length = 30)
    private String otherName;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    @Column(nullable = false, length = 30, unique = true)
    private String phone;
    @Column(nullable = false, length = 30)
    private String password;

}
