package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.FeedRequest;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.repository.UserRepository;
import ru.vsu.cs.chirk.service.ChirkService;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    //TODO добавить токены в параметры

    @Autowired
    private ChirkService chirkService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    //@RequestBody Chirk chirk
//    public ChirkFeedDTO createChirk() {
//        for (int i = 0; i < 10; i++) {
//
//            Chirk chirk = chirkService.getChirk(1);
//            var chirkFeedDTO = chirkService.createChirkForFeed(chirk);
//            System.out.println("----------------------------");
//            System.out.println(chirkFeedDTO);
//            System.out.println("----------------------------");
//
//        }
////        System.out.println(chirk);
//        return null;
//    }

//    @GetMapping
//    public List<ChirkFeedDTO> feed(){
//        //TODO доставать id из токена @Pekanov
//        List<ChirkFeedDTO> chirkFeedDTOList = chirkService.createListChirkFeed(0, 1L);
//        System.out.println(chirkFeedDTOList);
//        return chirkFeedDTOList;
//    }

    @GetMapping
    public List<ChirkFeedDTO> feed(@RequestBody FeedRequest feedRequest){
        //TODO доставать id из токена @Pekanov
//        System.out.println(feedRequest);

        List<ChirkFeedDTO> chirkFeedDTOList = chirkService.createListChirkFeed(feedRequest.getPage(), feedRequest.getUserId());
        System.out.println(chirkFeedDTOList);
        return chirkFeedDTOList;
    }



}
