package ru.vsu.cs.chirk.Mappers;

import ru.vsu.cs.chirk.entity.DTO.UserForAdminPanelDTO;
import ru.vsu.cs.chirk.entity.User;

public class UserForAdminPanelMapper implements EntityMapper<UserForAdminPanelDTO, User>{

    @Override
    public UserForAdminPanelDTO convertToDTO(User user) {
        return new UserForAdminPanelDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
    }


}
