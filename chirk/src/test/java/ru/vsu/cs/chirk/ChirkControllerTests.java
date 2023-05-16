package ru.vsu.cs.chirk;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.cs.chirk.controller.ChirkController;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChirkController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ChirkControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChirkController chirkController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    private RequestChirkDTO requestChirkDto;

    @BeforeEach
    public void init() {
        requestChirkDto = new RequestChirkDTO(1, "Hello chirk", true);
    }

    @Test
    public void testCreateChirk() throws Exception {
        doNothing().when(chirkController).createChirk(requestChirkDto);

        String requestBody = objectMapper.writeValueAsString(requestChirkDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/chirks/add")
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());

        verify(chirkController).createChirk(requestChirkDto);
    }
}