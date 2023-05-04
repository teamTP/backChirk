package ru.vsu.cs.chirk.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.hibernate.annotations.Entity;
//import org.hibernate.annotations.Table;

@Entity
//@Table(name = "estimate_chirk")
@Table(uniqueConstraints = {@UniqueConstraint(name = "estimate_chirk", columnNames = { "users", "chirk" })})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateChirk {

//    @EmbeddedId
//    private EstimateChirkPK   id;




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "users", nullable = false)
    private User userID;

    @ManyToOne
    @JoinColumn(name = "chirk", nullable = false)
    private Chirk chirkID;

    @Column(name = "is_liked")
    private boolean isLiked;

    @Column(name = "is_canceled_reaction")
    private boolean isCanceledReaction;

    @Override
    public String toString() {
        return "EstimateChirk{" +
                "id=" + id +
                ", userID=" + userID +
                ", chirkID=" + chirkID +
                ", isLiked=" + isLiked +
                ", isCanceledReaction=" + isCanceledReaction +
                '}';
    }
}
