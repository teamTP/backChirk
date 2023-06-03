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
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.cs.chirk.controller.AdminController;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminControllerTests.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AdminControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminController adminController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${authorizationHeader}")
    private String authorizationHeader;

    @Value("${email}")
    private String email;

    @BeforeEach
    public void init() {
    }

    @Test
    public void testAddAdmin() throws Exception {
        doNothing().when(adminController).addAdmin(authorizationHeader, email);

        mockMvc.perform(post("/admirator/addAdmin")
                .header("Authorization", authorizationHeader)
                .param("email", "janjav@mail.ru"))
                .andExpect(status().isOk());
    }
}
