package ru.vsu.cs.chirk.config;

import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.repository.ChirkRepository;

import java.util.Date;
import java.util.NoSuchElementException;

public class RunnableTask implements Runnable{
    private Long id;
    private ChirkRepository chirkRepository;

    public RunnableTask(Long id, ChirkRepository chirkRepository){
        this.id = id;
        this.chirkRepository = chirkRepository;
    }

    @Override
    public void run() {
        setVisible(id);
        System.out.println(new Date()+" Runnable Task with "+id
                +" on thread "+Thread.currentThread().getName());
    }
    private void setVisible(Long id) {
        Chirk chirk = chirkRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Chirk doesn't exist"));
        chirk.setVisible(false);
        chirkRepository.save(chirk);
    }
}