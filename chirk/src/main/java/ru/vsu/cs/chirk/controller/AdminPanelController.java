package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.AdminFilterDTO;
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
//    public void addAdmin(@RequestBody String email) {
//        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
//        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        // requestChirkDTO.setIdUser(userId);
        if(!checkIsAdmirator(email))
            userService.setAdminRole(email);
    }

    @PostMapping("/deleteAdmin")
    @PreAuthorize("hasAuthority('ADD_NEW_MODER_AUTHORITY')")
    public void deleteAdmin(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestParam String email) {
//    public void deleteAdmin(@RequestBody String email) {
//        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
//        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        // requestChirkDTO.setIdUser(userId);
        if(!checkIsAdmirator(email))
            userService.deleteAdminRole(email);
    }



    @GetMapping
    @PreAuthorize("hasAuthority('ADD_NEW_MODER_AUTHORITY')")
    public List<UserForAdminPanelDTO> getAllUsersForAdminPanel(@RequestHeader(name = "Authorization") String authorizationHeader){
        return userService.getAllUsersForAdminPanel();
    }


//     if filterByEmail == false it's filter by lastname
    @GetMapping("/byFilter")
    public List<UserForAdminPanelDTO> getAllUsersByFilter( @RequestBody AdminFilterDTO adminFilterDTO){
        return userService.getAllUsersByFilter(adminFilterDTO.getSearch(), adminFilterDTO.isFilterByEmail());
    }

//    @GetMapping("/byFilter")
//    public List<UserForAdminPanelDTO> getAllUsersByFilter( @RequestBody String search, @RequestBody boolean filterByEmail){
//        return userService.getAllUsersByFilter(search, filterByEmail);
//    }

    private boolean checkIsAdmirator(String email){
        return userService.isAdmirator(email);
    }



}
