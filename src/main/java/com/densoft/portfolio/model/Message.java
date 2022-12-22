package com.densoft.portfolio.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(columnDefinition = "boolean default false")
    private boolean isRead;

    public Message(String email, String message) {
        this.email = email;
        this.message = message;
        this.isRead = false;
    }
}
