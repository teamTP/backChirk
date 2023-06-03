package ru.vsu.cs.chirk.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserForAdminPanelDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String authority;

}
