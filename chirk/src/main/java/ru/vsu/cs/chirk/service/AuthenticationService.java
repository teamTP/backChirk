package ru.vsu.cs.chirk.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.DTO.JwtTokensDto;
import ru.vsu.cs.chirk.entity.ERole;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.UserRepository;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * Тут нужно вместо юзера юзерДТО для регистрации, что бы без лишних данных приходило
     */

    public JwtTokensDto registerUser(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("User with username: " + user.getUsername() + "already exist");
        }

//        User user = modelMapper.map(userDto, User.class);


        User newUser = user;
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(ERole.ORDINARY);
        newUser = userRepository.save(newUser);
        return createTokensForUser(newUser);

    }


    private JwtTokensDto createTokensForUser(User user) {
//        if (user.getRole().equals(ERole.MODERATOR.BANNED)) {
//            throw new AccessDeniedException("Пользователь заблокирован");
//        }

        return new JwtTokensDto(jwtTokenProvider.generateAccessToken(user),
                jwtTokenProvider.generateRefreshToken(user));
    }


    public JwtTokensDto loginUser(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));


        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User with username: " + user.getUsername() + "not exist"));

        return createTokensForUser(dbUser);
    }

    public JwtTokensDto refreshToken(String refreshToken) {
        String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username: " + username + "not exist"));
        return createTokensForUser(dbUser);
    }




}
