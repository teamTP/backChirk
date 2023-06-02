package ru.vsu.cs.chirk.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.ERole;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.UserRepository;

@Slf4j
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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


}
