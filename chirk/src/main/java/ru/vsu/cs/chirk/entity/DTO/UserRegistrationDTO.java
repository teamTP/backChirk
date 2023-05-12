package ru.vsu.cs.chirk.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;


}
