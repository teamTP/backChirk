package ru.vsu.cs.chirk.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
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
    public void createChirk(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkDTO requestChirkDTO) {
        String accessToken = extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        requestChirkDTO.setIdUser(userId);
        chirkService.createChirk(requestChirkDTO);
    }
    @DeleteMapping("/delete")
    public void deleteChirk(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.deleteChirk(requestChirkIdDTO.getId());
    }
    @PutMapping("/updateVisible")
    public void updateVisible(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.updateVisible(requestChirkIdDTO.getId());
    }
    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }
}
