package ru.vsu.cs.chirk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter @Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

//    @Column(name = "role_name")
//    private String roleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20)
    private ERole roleName;

    @Column(name = "description")
    private String description;

    public UserRole(ERole roleName) {
        this.roleName = roleName;
    }

    public UserRole() {}


}
