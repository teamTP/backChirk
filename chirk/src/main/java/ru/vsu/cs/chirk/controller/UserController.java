package ru.vsu.cs.chirk.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.vsu.cs.chirk.entity.DTO.UserDTO;
import ru.vsu.cs.chirk.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "User API", description = "Allows to view profiles, search for users and change roles")
@SecurityRequirement(name = "JWT Authentication")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Finds users by part of nickname")
    @GetMapping("search")
    public ResponseEntity<UserDTO> findUsersByNickname(
            @RequestParam("size") @Min(value = 1, message = "Размер страницы должен быть >=1")
            @Parameter(description = "Size of pages") int size
            ) {
//        String searchingUserNickname = Optional.ofNullable(authentication).map(Authentication::getName).orElse(null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.hello());
    }

    @GetMapping("/hello")
    public String hello() {
        userService.hello();
        System.out.println("aaaaaaaaa");
        return "Hello, World!";
    }

    @GetMapping("/register")
    public String register() {
        userService.hello();
        System.out.println("бббббббб");
        return "register";
    }


}
