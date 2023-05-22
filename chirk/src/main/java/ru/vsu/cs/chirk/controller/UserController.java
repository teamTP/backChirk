package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.vsu.cs.chirk.entity.DTO.JwtTokensDto;
import ru.vsu.cs.chirk.entity.DTO.UserAuthorisationDTO;
import ru.vsu.cs.chirk.entity.DTO.UserRegistrationDTO;
import ru.vsu.cs.chirk.service.AuthenticationService;
import ru.vsu.cs.chirk.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {




    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/hello")
    public String hello() {
        userService.hello();
        System.out.println("aaaaaaaaa");
        return "Hello, World!";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<JwtTokensDto> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        public ResponseEntity<JwtTokensDto> register (@RequestBody UserRegistrationDTO userRegistrationDTO) {
//        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Oleg", "Oc", "AdminDu@Good", "000000" );

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







}
