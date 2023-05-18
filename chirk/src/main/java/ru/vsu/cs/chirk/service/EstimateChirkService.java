package ru.vsu.cs.chirk.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestEstimateDTO;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.EstimateChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;
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
    public void createEstimate(RequestEstimateDTO requestEstimateDTO) {
        User user = userRepository.findById(requestEstimateDTO.getIdUser())
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует"));
        Chirk chirk = chirkRepository.findById(requestEstimateDTO.getIdChirk())
                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));
        EstimateChirk estimateChirk1 = estimateChirkRepository.findByChirkIDAndUserID(chirk, user);
        if (requestEstimateDTO.getIsLiked() == null){
            estimateChirk1.setCanceledReaction(true);
            estimateChirkRepository.save(estimateChirk1);
        } else if (estimateChirk1 != null){
            estimateChirk1.setCanceledReaction(false);
            estimateChirk1.setLiked(requestEstimateDTO.getIsLiked());
            estimateChirkRepository.save(estimateChirk1);
        }else {
            EstimateChirk estimateChirk= new EstimateChirk();
            estimateChirk.setLiked(requestEstimateDTO.getIsLiked());
            estimateChirk.setCanceledReaction(false);
            estimateChirk.setUserID(userRepository.findById(requestEstimateDTO.getIdUser())
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не существует")));
            estimateChirk.setChirkID(chirkRepository.findById(requestEstimateDTO.getIdChirk())
                    .orElseThrow(() -> new NoSuchElementException("Публикация не существует")));
            estimateChirkRepository.save(estimateChirk);
        }
    }

//    public void deleteEstimate(RequestEstimateDTO requestEstimateDTO) {
//        User user = userRepository.findById(requestEstimateDTO.getIdUser())
//                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует"));
//        Chirk chirk = chirkRepository.findById(requestEstimateDTO.getIdChirk())
//                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));
//        EstimateChirk estimateChirk1 = estimateChirkRepository.findByChirkIDAndUserID(chirk, user);
//        estimateChirk1.setCanceledReaction(true);
//        System.out.println(estimateChirk1);
//        estimateChirkRepository.save(estimateChirk1);
//    }
}
