package ru.vsu.cs.chirk.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.vsu.cs.chirk.config.RunnableTask;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
@Component
@Transactional
public class ChirkService {

    @Autowired
    private TaskScheduler scheduler;
    @Autowired
    private ChirkRepository chirkRepository;
    @Autowired
    private UserRepository userRepository;
    public void createChirk(Chirk chirk) {
        chirk.setDate(LocalDateTime.now());
        chirk.setVisible(true);
        chirk.setUser(userRepository.findById(chirk.getRoleId()).orElseThrow(() -> new NoSuchElementException("User doesn't exist")));
        chirkRepository.save(chirk);
        if (chirk.isOneDay()){
            scheduler.schedule(new RunnableTask(chirk.getId(), chirkRepository), new Date(System.currentTimeMillis() + (6*1000)));
        }
    }
}
