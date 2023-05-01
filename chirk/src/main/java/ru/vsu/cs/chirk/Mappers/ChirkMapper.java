package ru.vsu.cs.chirk.Mappers;

import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.ChirkDTO;

public class ChirkMapper implements EntityMapper<ChirkDTO, Chirk>{

@Override
public ChirkDTO convertToDTO(Chirk chirk) {
        return new ChirkDTO(
                chirk.getId(),
                chirk.getText(),
                chirk.getDate(),
                chirk.isOneDay()
            );
        }
}
