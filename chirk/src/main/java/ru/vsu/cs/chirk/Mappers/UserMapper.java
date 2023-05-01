package ru.vsu.cs.chirk.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.chirk.entity.User;
import ru.vsu.cs.chirk.entity.DTO.UserDTO;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(source = "username", target = "username")
    UserDTO userToUserDTO(User user);
}

