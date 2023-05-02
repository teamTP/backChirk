package ru.vsu.cs.chirk.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String username;



}
