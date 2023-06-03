package ru.vsu.cs.chirk.Mappers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.vsu.cs.chirk.entity.DTO.UserForAdminPanelDTO;
import ru.vsu.cs.chirk.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserForAdminPanelMapper implements EntityMapper<UserForAdminPanelDTO, User>{

    @Override
    public UserForAdminPanelDTO convertToDTO(User user) {
        return new UserForAdminPanelDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                convertAuthoritiesToString(user.getRole().getSimpleGrantedAuthorities())
                //                user.getRole().getSimpleGrantedAuthorities().stream()
//                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );
    }

    public String convertAuthoritiesToString(Set<SimpleGrantedAuthority> authorities) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SimpleGrantedAuthority authority : authorities) {
            stringBuilder.append(authority.getAuthority()).append(", ");
        }
        return stringBuilder.toString().trim();
    }

}
