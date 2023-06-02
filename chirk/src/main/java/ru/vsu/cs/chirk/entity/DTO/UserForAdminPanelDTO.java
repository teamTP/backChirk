package ru.vsu.cs.chirk.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserForAdminPanelDTO {

    private String firstname;
    private String lastname;
    private String email;

}
