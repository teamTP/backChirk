package ru.vsu.cs.chirk.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    })
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;


    @Column(name = "username")
    private String username;
    @PostPersist
    public void setUsernameBeforePersist() {
        this.username = "id" + this.id;
    }

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    @ManyToOne
//    @JoinColumn(name = "role", referencedColumnName = "id", insertable = false, updatable = false)
//    private UserRole role;



    @Column(name = "icon_id")
    private int iconId = (int)(Math.random()*5);

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//
//    private Set<UserRole> roles = new HashSet<>();


    @NotNull(message = "Enter user role")
    private ERole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname);
    }

    public User(String firstname, String lastname, String username, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public User(String firstname, String lastname, String email, String password, ERole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
