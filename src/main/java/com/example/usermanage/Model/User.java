package com.example.usermanage.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tblUser")
public class User {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userName")
    private String name;

    @Column(name = "passwordHash")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(){}

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "";
    }
}
