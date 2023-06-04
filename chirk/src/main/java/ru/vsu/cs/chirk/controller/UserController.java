package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.vsu.cs.chirk.entity.DTO.JwtTokensDto;
import ru.vsu.cs.chirk.entity.DTO.UserAuthorisationDTO;
import ru.vsu.cs.chirk.entity.DTO.UserRegistrationDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RefreshTokenRequest;
import ru.vsu.cs.chirk.service.AuthenticationService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<JwtTokensDto> register (@RequestBody UserRegistrationDTO userRegistrationDTO) {
        JwtTokensDto token = authenticationService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(value = "/authorisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokensDto> authorise (@RequestBody UserAuthorisationDTO userAuthorisationDTO) {
        JwtTokensDto token = authenticationService.loginUser(userAuthorisationDTO);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(value = "/updateTokens", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokensDto> updateTokens(@RequestBody RefreshTokenRequest refreshTokenRequest){
        String refreshToken = refreshTokenRequest.getRefreshToken();
        JwtTokensDto token = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok().body(token);
    }






}
