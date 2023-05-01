package ru.vsu.cs.chirk.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.UserRepository;

@Slf4j
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User hello(){
        User user = new User();
        user.setFirstname("Oo");
        user.setEmail("111");
        user.setPassword("222");
        user.setLastname("Bb");
        user.setUsername("id" + user.getId());
        userRepository.save(user);
        System.out.println("helloo");
        return user;
    }


}
