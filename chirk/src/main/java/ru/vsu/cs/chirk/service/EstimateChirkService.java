package ru.vsu.cs.chirk.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.config.RunnableTask;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.EstimateChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Transactional
@Service
public class EstimateChirkService {

    @Autowired
    EstimateChirkRepository estimateChirkRepository;
    @Autowired
    ChirkRepository chirkRepository;
    @Autowired
    UserRepository userRepository;


    public int[] getCountLikeAndDis(Chirk chirk){
        int[] countOfReaction = new int[2];
        countOfReaction[0] = estimateChirkRepository.countByChirkIDAndIsLikedAndIsCanceledReaction(chirk,
                true, false);
        countOfReaction[1] = estimateChirkRepository.countByChirkIDAndIsLikedAndIsCanceledReaction(chirk,
                false, false);
        return countOfReaction;
    }
    public void createEstimate(EstimateChirk estimateChirk) {
        User user = userRepository.findById(estimateChirk.getUserID().getId())
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует"));
        Chirk chirk = chirkRepository.findById(estimateChirk.getChirkID().getId())
                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));

        EstimateChirk estimateChirk1 = estimateChirkRepository.findByChirkIDAndUserID(chirk, user);
        if (estimateChirk1 != null){
            estimateChirk1.setCanceledReaction(false);
            estimateChirk1.setLiked(estimateChirk.isLiked());
            estimateChirkRepository.save(estimateChirk1);
        }else {
            estimateChirk.setUserID(userRepository.findById(estimateChirk.getUserID().getId())
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не существует")));
            estimateChirk.setChirkID(chirkRepository.findById(estimateChirk.getChirkID().getId())
                    .orElseThrow(() -> new NoSuchElementException("Публикация не существует")));
            estimateChirkRepository.save(estimateChirk);
        }
    }

    public void deleteEstimate(EstimateChirk estimateChirk) {
        User user = userRepository.findById(estimateChirk.getUserID().getId())
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует"));
        Chirk chirk = chirkRepository.findById(estimateChirk.getChirkID().getId())
                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));

        EstimateChirk estimateChirk1 = estimateChirkRepository.findByChirkIDAndUserID(chirk, user);
        estimateChirk1.setCanceledReaction(true);
        estimateChirkRepository.save(estimateChirk1);
    }

    public EstimateChirk getEstimate(long chirkId){
        return estimateChirkRepository.findById(chirkId).orElseThrow(()-> new NoSuchElementException("Реакция не существует"));
    }

}
