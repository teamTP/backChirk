package ru.vsu.cs.chirk.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.EstimateChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;

import java.util.List;

@Slf4j
@Transactional
@Service
public class EstimateChirkService {

    @Autowired
    EstimateChirkRepository estimateChirkRepository;
    @Autowired
    ChirkRepository chirkRepository;


    public int[] getCountLikeAndDis(Chirk chirk){
        int[] countOfReaction = new int[2];
        countOfReaction[0] = estimateChirkRepository.countByChirkIDAndIsLikedAndIsCanceledReaction(chirk,
                                                                        true, false);
        countOfReaction[1] = estimateChirkRepository.countByChirkIDAndIsLikedAndIsCanceledReaction(chirk,
                false, false);
        return countOfReaction;
    }

}
