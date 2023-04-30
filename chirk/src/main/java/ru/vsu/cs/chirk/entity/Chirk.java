package ru.vsu.cs.chirk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "chirk")
@Getter @Setter
public class Chirk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "text")
    private long text;

    @Column(name = "is_visible")
    private boolean isVisible;

    @Column(name = "date_publication")
    private Date date;

    @Column(name = "is_one_day")
    private boolean isOneDay;


}
