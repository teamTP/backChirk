package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.vsu.cs.chirk.entity.DTO.JwtTokensDto;
import ru.vsu.cs.chirk.entity.DTO.UserAuthorisationDTO;
import ru.vsu.cs.chirk.entity.DTO.UserRegistrationDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.AuthenticationService;
import ru.vsu.cs.chirk.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {




    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/hello")
    public String hello() {
        userService.hello();
        System.out.println("aaaaaaaaa");
        return "Hello, World!";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<JwtTokensDto> register (@RequestBody UserRegistrationDTO userRegistrationDTO) {
        JwtTokensDto token = authenticationService.registerUser(userRegistrationDTO);


        for (int i = 0; i < 10; i++) {
            System.out.println("register is OK");
        }


        return ResponseEntity.ok().body(token);
    }



    @PostMapping(value = "/authorisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokensDto> authorise (@RequestBody UserAuthorisationDTO userAuthorisationDTO) {
        JwtTokensDto token = authenticationService.loginUser(userAuthorisationDTO);



        for (int i = 0; i < 10; i++) {
            System.out.println("authorise is OK");
        }



        return ResponseEntity.ok().body(token);
    }
    @PostMapping(value = "/updateTokens", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokensDto> updateTokens(@RequestHeader("Authorization") String authorizationHeader){
        String refreshToken = extractToken(authorizationHeader);
        JwtTokensDto token = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok().body(token);
    }

    private String extractToken(String authorizationHeader) {
// Assuming the Authorization header value is in the format "Bearer <token>"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract the token part after "Bearer "
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

}
