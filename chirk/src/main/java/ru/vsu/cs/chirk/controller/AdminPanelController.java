package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.DTO.UserForAdminPanelDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/adminPanel")
public class AdminPanelController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;



    @PostMapping("/addAdmin")
    @PreAuthorize("hasAuthority('ADD_NEW_MODER_AUTHORITY')")
    public void addAdmin(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestParam String email) {
//        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
//        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        // requestChirkDTO.setIdUser(userId);
        userService.setAdminRole(email);
    }

    @PostMapping("/deleteAdmin")
    @PreAuthorize("hasAuthority('ADD_NEW_MODER_AUTHORITY')")
    public void deleteAdmin(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestParam String email) {
//        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
//        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        // requestChirkDTO.setIdUser(userId);
        userService.deleteAdminRole(email);
    }

    @GetMapping
    public List<UserForAdminPanelDTO> getAllUsersForAdminPanel(){
        return userService.getAllUsersForAdminPanel();
    }


    // if filterByEmail == false it's filter by lastname
    @GetMapping("/byFilter")
    public List<UserForAdminPanelDTO> getAllUsersByFilter( @RequestParam String search,  @RequestParam boolean filterByEmail){
        return userService.getAllUsersByFilter(search, filterByEmail);
    }





}
