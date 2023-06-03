package ru.vsu.cs.chirk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
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

    Page<EstimateChirk> findAllByIsLikedAndIsCanceledReaction(boolean isLiked,
                                                      boolean isCanceledReaction,
                                                      @NonNull Pageable pageable);

    Page<EstimateChirk> findAllByUserIDAndIsCanceledReactionAndIsLiked(User userID,
                                                                       boolean isCanceledReaction,
                                                                       boolean isLiked,
                                                                       @NonNull Pageable pageable);

}
