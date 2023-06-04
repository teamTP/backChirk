package ru.vsu.cs.chirk.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ChirkDTO {

    private long idUser;
    private String text;
    private ZonedDateTime datePublication;
    private boolean isOneDay;
}
