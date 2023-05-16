package ru.vsu.cs.chirk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.entity.User;

import java.util.List;

@Repository
public interface EstimateChirkRepository extends JpaRepository<EstimateChirk, Long> {

    List<EstimateChirkRepository> findByChirkID(Chirk chirk);
    EstimateChirk findByUserIDAndChirkID(User user, Chirk chirk);
    int countByChirkIDAndIsLikedAndIsCanceledReaction(Chirk chirk, boolean isLiked, boolean isCanceledReaction);


    EstimateChirk findByChirkIDAndUserID(Chirk chirk, User user);
    void deleteEstimateChirkByChirkID (Chirk chirk);
}
