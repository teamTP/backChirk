package ru.vsu.cs.chirk.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChirkDTO {

    private String text;
    private String date_publication;
    private String is_one_day;
}
