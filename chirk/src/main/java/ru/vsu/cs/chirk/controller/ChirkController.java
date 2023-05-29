package ru.vsu.cs.chirk.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //TODO: что то сделать с секьюрити куда и как только при токене пускают?


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('UPLOAD_AUTHORITY')")
    public void createChirk(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkDTO requestChirkDTO) {
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
       // requestChirkDTO.setIdUser(userId);
        chirkService.createChirk(requestChirkDTO, userId);
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('BAN_PUBLICATION_AUTHORITY')")
    public void deleteChirk(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.deleteChirk(requestChirkIdDTO.getId());
    }
    @PutMapping("/updateVisible")
    @PreAuthorize("hasAuthority('UPLOAD_AUTHORITY')")
    public void updateVisible(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.updateVisible(requestChirkIdDTO.getId());
    }

}
