package ru.vsu.cs.chirk.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.vsu.cs.chirk.config.RunnableTask;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.entity.Reaction;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.EstimateChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ChirkService {

    private final  TaskScheduler scheduler;
    private final ChirkRepository chirkRepository;
    private final UserRepository userRepository;
    private final EstimateChirkRepository estimateChirkRepository;

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

    public List<Chirk> getPage(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Chirk> chirkPage = chirkRepository.findAll(pageable);
        return chirkPage.stream().toList();
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

    public ChirkFeedDTO createChirkForFeed(Chirk chirk, Reaction reaction){
        int[] count = estimateChirkService.getCountLikeAndDis(chirk);

        return new ChirkFeedDTO(chirk.getId(), chirk.getText(), chirk.getDate(),
                chirk.getUser().getFirstname(), chirk.getUser().getLastname(), count[0], count[1], reaction);
    }

    public List<ChirkFeedDTO> createListChirkFeed(int page, long userId){
        List<Chirk> chirkList = getPage(page);
        User user = userRepository.findById(userId).get();
        List<ChirkFeedDTO> chirkFeedDTOList = new ArrayList<>();
        for(Chirk chirk : chirkList){
            EstimateChirk estimateChirk = estimateChirkRepository.findByUserIDAndChirkID(user, chirk);
            if(estimateChirk != null && !estimateChirk.isCanceledReaction())
                if(estimateChirk.isLiked())
                    chirkFeedDTOList.add(createChirkForFeed(chirk, Reaction.LIKE));
                else
                    chirkFeedDTOList.add(createChirkForFeed(chirk, Reaction.DISLIKE));
            else
                chirkFeedDTOList.add(createChirkForFeed(chirk, Reaction.NON));
        }
        return chirkFeedDTOList;
    }
    public List<ChirkFeedDTO> createListChirkFeed(int page){
        List<Chirk> chirkList = getPage(page);
        List<ChirkFeedDTO> chirkFeedDTOList = new ArrayList<>();
        for(Chirk chirk : chirkList){
            chirkFeedDTOList.add(createChirkForFeed(chirk, Reaction.NON));
        }
        return chirkFeedDTOList;
    }
}
