package ru.vsu.cs.chirk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public String register() {
        var token = authenticationService.registerUser(userService.hello());
        System.out.println("бббббббб");
        return "register";
    }


}
