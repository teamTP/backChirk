package ru.vsu.cs.chirk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.cs.chirk.entity.DTO.UserDTO;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "chirk")
@Getter @Setter
public class Chirk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "users", nullable = false)
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "is_visible")
    private boolean isVisible;

    @Column(name = "date_publication")
    private ZonedDateTime date;

    @Column(name = "is_one_day")
    private boolean isOneDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chirk chirk = (Chirk) o;
        return id == chirk.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Chirk{" +
                "id=" + id +

                ", user=" + user +
                ", text=" + text +
                ", isVisible=" + isVisible +
                ", date=" + date +
                ", isOneDay=" + isOneDay +
                '}';
    }


}
