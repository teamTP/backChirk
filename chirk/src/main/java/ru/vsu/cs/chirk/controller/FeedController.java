package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.service.ChirkService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private ChirkService chirkService;

    @GetMapping("/")
    //@RequestBody Chirk chirk
    public List<ChirkFeedDTO> createChirk() {
        List<ChirkFeedDTO> chirkFeedDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Chirk chirk = chirkService.getChirk(1);
            var chirkFeedDTO = chirkService.createChirkForFeed(chirk);
            System.out.println("----------------------------");
            chirkFeedDTOList.add(chirkFeedDTO);
            System.out.println(chirkFeedDTO);
            System.out.println("----------------------------");

        }
//        System.out.println(chirk);
        return chirkFeedDTOList;
    }
}
