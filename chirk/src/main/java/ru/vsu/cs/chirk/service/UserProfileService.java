package ru.vsu.cs.chirk.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.DTO.UserInfoUpdateDTO;
import ru.vsu.cs.chirk.entity.DTO.UserPasswordUpdateDTO;
import ru.vsu.cs.chirk.entity.DTO.UserProfileDTO;
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
    private final ChirkService chirkService;
    private final PasswordEncoder passwordEncoder;


    public List<ChirkFeedDTO> getUsersPosts(Long userID, int page){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + userID + "not exist"));
        return chirkService.createListChirkInProfile(page, user);
    }

    public List<ChirkFeedDTO> getLikedUsersPosts(Long userID, int page){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + userID + "not exist"));
        return chirkService.createListLikedUserChirks(page, user);
    }

    public List<ChirkFeedDTO> getDislikedUsersPosts(Long userID, int page){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + userID + "not exist"));
        return chirkService.createListDislikedUserChirks(page, user);
    }

    public UserProfileDTO getUserProfileDTO(Long userID){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + userID + "not exist"));
        return new UserProfileDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getIconId()
        );
    }
    public void updateUserInfo(Long userId, UserInfoUpdateDTO userInfoUpdateDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + " not found"));
        user.setFirstname(userInfoUpdateDTO.getFirstname());
        user.setLastname(userInfoUpdateDTO.getLastname());
        userRepository.save(user);
    }

    public UserInfoUpdateDTO getUserInfoUpdateDTO(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + " not found"));
        return new UserInfoUpdateDTO(user.getFirstname(), user.getLastname());

    }

    public void updateUserPassword(UserPasswordUpdateDTO userPasswordUpdateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + " not found"));

        String oldPassword = userPasswordUpdateDTO.getOldPassword();
        String newPassword = userPasswordUpdateDTO.getNewPassword();

        if (passwordEncoder.matches(oldPassword, user.getPassword()) && !oldPassword.equals(newPassword)) {
            // Старый пароль совпадает, обновляем пароль пользователя
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            userRepository.save(user);
        } else {
            // Старый пароль совпадает или новый пароль совпадает со старым, выбрасываем ошибку
            throw new IllegalArgumentException("Invalid old password or new password");
        }
    }






}
