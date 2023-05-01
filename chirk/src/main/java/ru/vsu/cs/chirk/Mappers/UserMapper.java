package ru.vsu.cs.chirk.Mappers;

import ru.vsu.cs.chirk.entity.DTO.UserDTO;
import ru.vsu.cs.chirk.entity.User;

public class UserMapper implements EntityMapper<UserDTO, User>{

    @Override
    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getUsername()
        );
    }
}
