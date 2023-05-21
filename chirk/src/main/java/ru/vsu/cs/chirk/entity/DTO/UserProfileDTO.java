package ru.vsu.cs.chirk.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserProfileDTO {

    private String firstname;
    private String lastname;
    private String email;
    private int iconId;

}
