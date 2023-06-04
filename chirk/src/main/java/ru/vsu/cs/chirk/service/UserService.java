package ru.vsu.cs.chirk.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.Mappers.UserForAdminPanelMapper;
import ru.vsu.cs.chirk.entity.DTO.UserForAdminPanelDTO;
import ru.vsu.cs.chirk.entity.ERole;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final UserForAdminPanelMapper userForAdminPanelMapper = new UserForAdminPanelMapper();

    public boolean setAdminRole(String email){

        if (userRepository.findByEmail(email).isPresent()) {
            User newAdmin = userRepository.findByEmail(email).get();
            newAdmin.setRole(ERole.MODERATOR);
            userRepository.save(newAdmin);
            return true;
        }
        return false;
    }

    public boolean deleteAdminRole(String email){

        if (userRepository.findByEmail(email).isPresent()) {
            User newAdmin = userRepository.findByEmail(email).get();
            newAdmin.setRole(ERole.ORDINARY);
            userRepository.save(newAdmin);
            return true;
        }
        return false;
    }

    public List<UserForAdminPanelDTO> getAllUsersForAdminPanel() {
        List<User> userList = userRepository.findAll();
        List<UserForAdminPanelDTO> userForAdminPanelDTOS = new ArrayList<>();
        for(User user : userList){
            userForAdminPanelDTOS.add(userForAdminPanelMapper.convertToDTO(user));
        }
        return userForAdminPanelDTOS;
    }

    public List<UserForAdminPanelDTO> getAllUsersByFilter(String search, Boolean filterByEmail) {
        List<User> userList = (filterByEmail ?
                userRepository.findByEmail(search) :
                userRepository.findByLastname(search)
        ).stream().toList();

        List<UserForAdminPanelDTO> userForAdminPanelDTOS = new ArrayList<>();
        for(User user : userList){
            userForAdminPanelDTOS.add(userForAdminPanelMapper.convertToDTO(user));
        }
        return userForAdminPanelDTOS;
    }

    public boolean isAdmirator(String email) {
        return userRepository.findByEmail(email).get().getRole() == ERole.ADMIRATOR;
    }
}
