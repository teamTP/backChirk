package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.DTO.UserInfoUpdateDTO;
import ru.vsu.cs.chirk.entity.DTO.UserPasswordUpdateDTO;
import ru.vsu.cs.chirk.entity.DTO.UserProfileDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkIdDTO;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.ChirkService;
import ru.vsu.cs.chirk.service.UserProfileService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private ChirkService chirkService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/myChirks")
    public List<ChirkFeedDTO> usersChirks(@RequestHeader("Authorization") String authorizationHeader,@RequestParam int page){
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        return userProfileService.getUsersPosts(userId, page);
    }

    @GetMapping("/myLikedChirks")
    public List<ChirkFeedDTO> userLikedChirks(@RequestHeader("Authorization") String authorizationHeader,@RequestParam int page){
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        return userProfileService.getLikedUsersPosts(userId, page);
    }

    @GetMapping("/myDislikedChirks")
    public List<ChirkFeedDTO> userDislikedChirks(@RequestHeader("Authorization") String authorizationHeader,@RequestParam int page){
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        return userProfileService.getDislikedUsersPosts(userId, page);
    }

    @GetMapping("/userProfile")
    public UserProfileDTO userProfile(@RequestHeader("Authorization") String authorizationHeader){
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        return userProfileService.getUserProfileDTO(userId);
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<String> updateUserProfile(@RequestHeader("Authorization") String authorizationHeader,
                                                    @RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
        String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        userProfileService.updateUserInfo(userId, userInfoUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User profile updated successfully");
    }

    @PostMapping("/updateUserPassword")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String authorizationHeader,
                                                 @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {
        try {
            String accessToken = jwtTokenProvider.extractAccessToken(authorizationHeader);
            Long userId = jwtTokenProvider.getIdFromJwt(accessToken);

            userProfileService.updateUserPassword(userPasswordUpdateDTO, userId);

            return ResponseEntity.status(HttpStatus.OK).body("User password updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password or new password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }









}
