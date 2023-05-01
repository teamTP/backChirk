package ru.vsu.cs.chirk.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChirkDTO {

    private long idUser;
    private String text;
    private LocalDateTime datePublication;
    private boolean isOneDay;
}
