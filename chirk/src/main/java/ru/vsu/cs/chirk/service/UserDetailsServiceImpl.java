package ru.vsu.cs.chirk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.repository.UserRepository;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found!"));

        Collection<SimpleGrantedAuthority> authorities = user.getRole().getSimpleGrantedAuthorities();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);    }


}
