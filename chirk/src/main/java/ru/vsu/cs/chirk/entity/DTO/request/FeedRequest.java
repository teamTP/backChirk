package ru.vsu.cs.chirk.entity.DTO.request;

import lombok.Getter;
import ru.vsu.cs.chirk.entity.DTO.ChirkDTO;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.DTO.UserDTO;

@Getter
public class FeedRequest {
    private long userId;
    private int page;

}
