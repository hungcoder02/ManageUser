package com.example.usermanage.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tblRole")
public class Role {
    @Id
    @Column(name = "roleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "roleName")
    private String name;
}
