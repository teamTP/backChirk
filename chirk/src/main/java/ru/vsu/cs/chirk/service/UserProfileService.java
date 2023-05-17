package ru.vsu.cs.chirk.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.UserInfoUpdateDTO;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.ChirkRepository;
import ru.vsu.cs.chirk.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final ChirkRepository chirkRepository;
    private final EstimateChirkService estimateChirkService;

    public List<Chirk> getAllUsersPosts(Long userID){
//        User user = userRepository.findById(userID)
//                .orElseThrow(() -> new NoSuchElementException("User with id: " + userID + "not exist"));
        return chirkRepository.findAllByUserID(userID);
    }

    public List<Chirk> getLikedOrDislikedUsersPosts(Long userID, boolean isLiked){
        return estimateChirkService.estimateChirkRepository.findAllByLikedIsAndCanceledReaction(isLiked, false);
    }

    public void updateUserInfo(Long userId, UserInfoUpdateDTO userInfoUpdateDTO){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + " not found"));

        user.setFirstname(userInfoUpdateDTO.getFirstname());
        user.setLastname(userInfoUpdateDTO.getLastname());

        User updatedUser = userRepository.save(user);

    }


    public UserInfoUpdateDTO getUserInfoUpdateDTO(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + " not found"));

        return new UserInfoUpdateDTO(user.getFirstname(), user.getLastname());

    }






}
