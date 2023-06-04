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
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.entity.Reaction;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.EstimateChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private final EstimateChirkService estimateChirkService;

    public void createChirk(RequestChirkDTO requestChirkDTO, Long userId) {
        Chirk chirk = new Chirk();
        chirk.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует")));
        chirk.setText(requestChirkDTO.getText());
        chirk.setOneDay(requestChirkDTO.isOneDay());
        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of("Europe/Moscow");
          ZonedDateTime zdt = now.atZone(zone);
        chirk.setDate(zdt);
        chirk.setVisible(true);
        chirkRepository.save(chirk);
        if (chirk.isOneDay()){
            scheduler.schedule(new RunnableTask(chirk.getId(), chirkRepository), Instant.now().plusSeconds(24*60*60));
        }
    }

    public Chirk getChirk(long chirkId){
        return chirkRepository.findById(chirkId).orElseThrow(()-> new NoSuchElementException("Публикация не существует"));
    }

    public List<Chirk> getPage(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Chirk> chirkPage = chirkRepository.findAllByIsVisibleOrderByDateDesc(pageable, true);
        return chirkPage.stream().toList();
    }

    public List<Chirk> getPage(int page, User user){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Chirk> chirkPage = chirkRepository.findAllByUserOrderByDateDesc(user, pageable);
        return chirkPage.stream().toList();
    }

    public List<Chirk> getPage(int page, User user, boolean isLiked){
        Pageable pageable = PageRequest.of(page, 10);
        Page<EstimateChirk> estimateChirkPage = estimateChirkRepository.findAllByUserIDAndIsCanceledReactionAndIsLiked(
                                                                                            user,
                                                                                            false,
                                                                                            isLiked,
                                                                                            pageable);
        List<EstimateChirk> estimateChirkList = estimateChirkPage.stream().toList();
        List<Chirk> chirkList = new ArrayList<>();
        for(EstimateChirk e : estimateChirkList){
            chirkList.add(e.getChirkID());
        }
        return chirkList;
    }

    public void deleteChirk(Long chirkId) {
        Chirk chirk = chirkRepository.findById(chirkId)
                .orElseThrow(() -> new NoSuchElementException("Публикация не существует"));
        estimateChirkRepository.deleteEstimateChirkByChirkID(chirk);
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

        return new ChirkFeedDTO(
                chirk.getId(),
                chirk.getText(),
                chirk.getDate(),
                chirk.getUser().getFirstname(),
                chirk.getUser().getLastname(),
                chirk.getUser().getIconId(),
                count[0],
                count[1],
                reaction,
                chirk.isVisible()
        );
    }

    public List<ChirkFeedDTO> createListChirkFeed(int page, long userId){
        List<Chirk> chirkList = getPage(page);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + userId + "not exist"));
        return createListChirkFeedDTO(user, chirkList);
    }

    public List<ChirkFeedDTO> createListChirkFeedWithoutUser(int page){
        List<Chirk> chirkList = getPage(page);
        return createListChirkFeedDTOWithoutUser(chirkList);
    }

    public List<ChirkFeedDTO> createListChirkInProfile(int page, User user){
        List<Chirk> chirkList = getPage(page, user);
        return createListChirkFeedDTO(user, chirkList);
    }

    public List<ChirkFeedDTO> createListLikedUserChirks(int page, User user){
        List<Chirk> chirkList = getPage(page, user, true);
        return createListChirkFeedDTO(user, chirkList);
    }

    public List<ChirkFeedDTO> createListDislikedUserChirks(int page, User user){
        List<Chirk> chirkList = getPage(page, user, false);
        return createListChirkFeedDTO(user, chirkList);
    }

    private List<ChirkFeedDTO> createListChirkFeedDTO(User user, List<Chirk> chirkList){
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

    private List<ChirkFeedDTO> createListChirkFeedDTOWithoutUser(List<Chirk> chirkList){
        List<ChirkFeedDTO> chirkFeedDTOList = new ArrayList<>();
        for(Chirk chirk : chirkList){
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
