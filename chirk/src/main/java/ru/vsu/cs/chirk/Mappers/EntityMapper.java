package ru.vsu.cs.chirk.Mappers;

//@Mapper
interface EntityMapper <T, E> {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    @Mapping(source = "firstname", target = "firstname")
//    @Mapping(source = "lastname", target = "lastname")
//    @Mapping(source = "username", target = "username")
//    UserDTO userToUserDTO(User user);

    T convertToDTO(E entity);
}

