package ru.vsu.cs.chirk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.annotations.Entity;
//import org.hibernate.annotations.Table;

@Entity
@Table(name = "estimate_chirk")
@Getter @Setter
public class EstimateChirk {

//    @EmbeddedId
//    private EstimateChirkPK   id;




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chirk", referencedColumnName = "id", insertable = false, updatable = false)
    private Chirk chirk;

    @Column(name = "is_liked")
    private boolean isLiked;


}
