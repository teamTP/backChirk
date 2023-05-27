package ru.vsu.cs.chirk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.cs.chirk.controller.FeedController;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.Reaction;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeedController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class FeedControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedController feedController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${authorizationHeader}")
    private String authorizationHeader;

    private int page;

    private ChirkFeedDTO chirkFeedDTO;

    private List<ChirkFeedDTO> chirkFeedDTOList;

    @BeforeEach
    public void init() {
        page = 1;

        chirkFeedDTO = new ChirkFeedDTO(1, "Hello chirk", ZonedDateTime.now(), "janat",
                "java", 1, 500, 2, Reaction.LIKE);

        chirkFeedDTOList = Arrays.asList(chirkFeedDTO);
    }

    @Test
    public void testFeed() throws Exception {
        when(feedController.feed(authorizationHeader, page)).thenReturn(chirkFeedDTOList);

        mockMvc.perform(get("/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                        .andExpect(status().isOk());
    }
}