package ru.vsu.cs.chirk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "chirk")
@Getter @Setter
public class Chirk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "user_id")
    private long roleId;

    @ManyToOne
    @JoinColumn(name = "\"user\"", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "is_visible")
    private boolean isVisible;

    @Column(name = "date_publication")
    private LocalDateTime date;

    @Column(name = "is_one_day")
    private boolean isOneDay;

    @Override
    public String toString() {
        return "Chirk{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", user=" + user +
                ", text=" + text +
                ", isVisible=" + isVisible +
                ", date=" + date +
                ", isOneDay=" + isOneDay +
                '}';
    }


}
