package ru.vsu.cs.chirk.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.DTO.JwtTokensDto;
import ru.vsu.cs.chirk.entity.DTO.UserAuthorisationDTO;
import ru.vsu.cs.chirk.entity.DTO.UserRegistrationDTO;
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

    public JwtTokensDto registerUser(UserRegistrationDTO userDTO){



        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new IllegalArgumentException("User with username: " + userDTO.getEmail() + "already exist");
        }

        User newUser = new User(userDTO.getFirstname(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                ERole.ORDINARY);
        userRepository.save(newUser);
        return createTokensForUser(newUser);

    }


    private JwtTokensDto createTokensForUser(User user) {
//        if (user.getRole().equals(ERole.MODERATOR.BANNED)) {
//            throw new AccessDeniedException("Пользователь заблокирован");
//        }

        return new JwtTokensDto(jwtTokenProvider.generateAccessToken(user),
                jwtTokenProvider.generateRefreshToken(user));
    }


    public JwtTokensDto loginUser(UserAuthorisationDTO userAuthorisationDTO) {
        //TODO хочет убрать 64 стр
        User user = userRepository.findByEmail(userAuthorisationDTO.getEmail()).get();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), userAuthorisationDTO.getPassword()));
        User dbUser = userRepository.findByEmail(userAuthorisationDTO.getEmail())
                .orElseThrow(() -> new NoSuchElementException("User with email: " + userAuthorisationDTO.getEmail() + " not exist"));
        return createTokensForUser(dbUser);
    }




    public JwtTokensDto refreshToken(String refreshToken) {
        String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username: " + username + "not exist"));
        return createTokensForUser(dbUser);
    }




}
