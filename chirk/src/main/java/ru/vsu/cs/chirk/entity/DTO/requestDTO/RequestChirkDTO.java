package ru.vsu.cs.chirk.entity.DTO.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestChirkDTO {
        //private long idUser;
        private String text;
        private boolean isOneDay;
    }

