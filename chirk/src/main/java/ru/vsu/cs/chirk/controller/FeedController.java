package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.repository.UserRepository;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.ChirkService;
import ru.vsu.cs.chirk.service.UserProfileService;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ChirkService chirkService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<ChirkFeedDTO> feed(@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
                                   @RequestParam int page){
        if (authorizationHeader == null) {
            return chirkService.createListChirkFeedWithoutUser(page);
        } else {
            String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
            Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
            return chirkService.createListChirkFeed(page, userId);
        }
    }




}
