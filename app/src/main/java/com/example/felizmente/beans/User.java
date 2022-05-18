package com.example.felizmente.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username, email, password;

    public User(String user, String pass) {
        this.username = user;
        this.password = pass;
    }
}
