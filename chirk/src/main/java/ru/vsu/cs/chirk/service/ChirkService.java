package ru.vsu.cs.chirk.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.vsu.cs.chirk.config.RunnableTask;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.EstimateChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;
import java.time.Instant;
import java.time.LocalDateTime;
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

    public void createChirk(RequestChirkDTO requestChirkDTO) {
        Chirk chirk = new Chirk();
        chirk.setUser(userRepository.findById(requestChirkDTO.getIdUser())
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует")));
        chirk.setText(requestChirkDTO.getText());
        chirk.setOneDay(requestChirkDTO.isOneDay());
        chirk.setDate(LocalDateTime.now());
        chirk.setVisible(true);
        chirkRepository.save(chirk);
        if (chirk.isOneDay()){
            scheduler.schedule(new RunnableTask(chirk.getId(), chirkRepository), Instant.now().plusSeconds(6));
        }
    }

    public Chirk getChirk(long chirkId){
        return chirkRepository.findById(chirkId).orElseThrow(()-> new NoSuchElementException("Публикация не существует"));
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

    public ChirkFeedDTO createChirkForFeed(Chirk chirk){
        int[] count = estimateChirkService.getCountLikeAndDis(chirk);
        ChirkFeedDTO chirkFeedDTO = new ChirkFeedDTO(chirk.getId(), chirk.getText(), chirk.getDate(),
                chirk.getUser().getFirstname(), chirk.getUser().getLastname(), count[0], count[1]);

        return chirkFeedDTO;
    }
}
