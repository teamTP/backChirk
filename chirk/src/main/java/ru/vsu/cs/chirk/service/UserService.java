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

    public User hello(){
        System.out.println("helloo2222");
        User user = new User();
//        User user1 = new User("firstname", "lastname", "username", "email", "password");
        user.setFirstname("firstname");
        user.setEmail("email");
        user.setPassword("password");
        user.setLastname("lastname");
        user.setUsername("id" + user.getId());
        user.setRole(ERole.ORDINARY);
//        userRepository.save(user);
        System.out.println("helloo!!!!!!!!!!!!!!!!!!");
        return user;
    }


}
