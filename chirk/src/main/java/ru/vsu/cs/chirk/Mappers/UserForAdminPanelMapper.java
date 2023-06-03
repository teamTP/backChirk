package ru.vsu.cs.chirk.Mappers;

import org.springframework.security.core.GrantedAuthority;
import ru.vsu.cs.chirk.entity.DTO.UserForAdminPanelDTO;
import ru.vsu.cs.chirk.entity.User;

import java.util.stream.Collectors;

public class UserForAdminPanelMapper implements EntityMapper<UserForAdminPanelDTO, User>{

    @Override
    public UserForAdminPanelDTO convertToDTO(User user) {
        return new UserForAdminPanelDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole().getSimpleGrantedAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );
    }


}
