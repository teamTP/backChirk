package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkIdDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.ChirkService;

@RestController
@RequestMapping("/chirks")
public class ChirkController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ChirkService chirkService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('UPLOAD_AUTHORITY')")
    public void createChirk(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkDTO requestChirkDTO) {
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        chirkService.createChirk(requestChirkDTO, userId);
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('UPLOAD_AUTHORITY')")
    public void deleteChirk(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.deleteChirk(requestChirkIdDTO.getId());
    }
    @PutMapping("/updateVisible")
    @PreAuthorize("hasAuthority('UPLOAD_AUTHORITY')")
    public void updateVisible(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.updateVisible(requestChirkIdDTO.getId());
    }

}
