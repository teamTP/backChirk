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
import ru.vsu.cs.chirk.controller.UserController;
import ru.vsu.cs.chirk.entity.DTO.UserAuthorisationDTO;
import ru.vsu.cs.chirk.entity.DTO.UserRegistrationDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RefreshTokenRequest;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserControllerTests.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserRegistrationDTO userRegistrationDTO;

    private UserAuthorisationDTO userAuthorisationDTO;

    private RefreshTokenRequest refreshTokenRequest;

    @Value("${refreshToken}")
    private String refreshToken;

    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

    @BeforeEach
    public void init() {
        userRegistrationDTO = new UserRegistrationDTO("janat", "java",
                email, password);

        userAuthorisationDTO = new UserAuthorisationDTO(email, password);

        refreshTokenRequest = new RefreshTokenRequest(refreshToken);
    }

    @Test
    public void testRegister() throws Exception {
        given(userController.register(userRegistrationDTO))
                .willAnswer((invocation -> invocation.getArgument(0)));

        String requestBody = objectMapper.writeValueAsString(userRegistrationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorise() throws Exception {
        given(userController.authorise(userAuthorisationDTO))
                .willAnswer((invocation -> invocation.getArgument(0)));

        String requestBody = objectMapper.writeValueAsString(userAuthorisationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/authorisation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTokens() throws Exception {
        given(userController.updateTokens(refreshTokenRequest))
                .willAnswer((invocation -> invocation.getArgument(0)));

        String requestBody = objectMapper.writeValueAsString(refreshTokenRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/updateTokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}