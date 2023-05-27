package ru.vsu.cs.chirk;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.cs.chirk.controller.EstimateChirkController;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestEstimateDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EstimateChirkController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EstimateChirkControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstimateChirkController estimateChirkController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    private RequestEstimateDTO requestEstimateDTO;

    @Value("${authorizationHeader}")
    private String authorizationHeader;

    @BeforeEach
    public void init() {
        requestEstimateDTO = new RequestEstimateDTO(1, true);
    }

    @Test
    public void testCreateEstimate() throws Exception {
        doNothing().when(estimateChirkController).createEstimate(authorizationHeader, requestEstimateDTO);

        String requestBody = objectMapper.writeValueAsString(requestEstimateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/estimate/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(estimateChirkController).createEstimate(authorizationHeader, requestEstimateDTO);
    }
}