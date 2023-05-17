package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkIdDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.ChirkService;
import ru.vsu.cs.chirk.service.UserProfileService;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private ChirkService chirkService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserProfileService userProfileService;



    @GetMapping("/myChirks")
    public List<Chirk> UsersChirks(String accessToken){

        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        return userProfileService.getAllUsersPosts(userId);

    }


        @PostMapping("/add")
        public void createChirk(@RequestBody RequestChirkDTO requestChirkDTO) {
            chirkService.createChirk(requestChirkDTO);
        }
        @DeleteMapping("/delete")
        public void deleteChirk(@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
            chirkService.deleteChirk(requestChirkIdDTO.getId());
        }
        @PutMapping("/updateVisible")
        public void updateVisible(@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
            chirkService.updateVisible(requestChirkIdDTO.getId());
        }







}
