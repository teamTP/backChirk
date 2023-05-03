package ru.vsu.cs.chirk.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.vsu.cs.chirk.config.RunnableTask;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ChirkService {

    private final  TaskScheduler scheduler;
    private final ChirkRepository chirkRepository;
    private final UserRepository userRepository;

    //??????????
    private final EstimateChirkService estimateChirkService;

    //??????????

    public void createChirk(Chirk chirk) {
        chirk.setDate(LocalDateTime.now());
        chirk.setVisible(true);
        chirk.setUser(userRepository.findById(chirk.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует")));
        chirkRepository.save(chirk);
        if (chirk.isOneDay()){
            scheduler.schedule(new RunnableTask(chirk.getId(), chirkRepository), Instant.now().plusSeconds(6));
        }
    }

    public Chirk getChirk(long chirkId){
        return chirkRepository.findById(chirkId).orElseThrow();
    }

    public void deleteChirk(Long chirkId) {
        Chirk chirk = chirkRepository.findById(chirkId)
                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));
        chirkRepository.delete(chirk);
    }

    public void updateVisible(Long chirkId) {
        Chirk chirk = chirkRepository.findById(chirkId)
                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));
        if (chirk.isVisible()) {
            chirk.setVisible(false);
            chirkRepository.save(chirk);
        } else {
            chirk.setVisible(true);
            chirkRepository.save(chirk);
        }
    }

    public ChirkFeedDTO createChirkForFeed(Chirk chirk){
        int[] count = estimateChirkService.getCountLikeAndDis(chirk);
        ChirkFeedDTO chirkFeedDTO = new ChirkFeedDTO(chirk.getId(), chirk.getText(), chirk.getDate(),
                chirk.getUser().getFirstname(), chirk.getUser().getLastname(), count[0], count[1]);

        return chirkFeedDTO;
    }
}
