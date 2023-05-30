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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.cs.chirk.controller.UserProfileController;
import ru.vsu.cs.chirk.entity.DTO.ChirkFeedDTO;
import ru.vsu.cs.chirk.entity.DTO.UserInfoUpdateDTO;
import ru.vsu.cs.chirk.entity.DTO.UserPasswordUpdateDTO;
import ru.vsu.cs.chirk.entity.DTO.UserProfileDTO;
import ru.vsu.cs.chirk.entity.Reaction;
import ru.vsu.cs.chirk.security.JwtTokenProvider;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileController userProfileController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${authorizationHeader}")
    private String authorizationHeader;

    private int page;

    @Value("${userFirstName}")
    private String userFirstName;

    @Value("${userLastName}")
    private String userLastName;

    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

    private ChirkFeedDTO chirkFeedLikeDTO;

    private ChirkFeedDTO chirkFeedDislikeDTO;

    private List<ChirkFeedDTO> chirkFeedDTOList;

    private List<ChirkFeedDTO> chirkFeedDTOLikeList;

    private List<ChirkFeedDTO> chirkFeedDTODislikeList;

    private UserInfoUpdateDTO userInfoUpdateDTO;

    private UserProfileDTO userProfileDTO;

    private UserPasswordUpdateDTO userPasswordUpdateDTO;

    @BeforeEach
    public void init() {
        page = 1;

        chirkFeedLikeDTO = new ChirkFeedDTO(1, "Hello chirk", ZonedDateTime.now(), userFirstName,
                userLastName, 1, 500, 2, Reaction.LIKE);

        chirkFeedDislikeDTO = new ChirkFeedDTO(1, "Hello chirk", ZonedDateTime.now(), userFirstName,
                userLastName, 1, 500, 3, Reaction.DISLIKE);

        chirkFeedDTOList = Arrays.asList(chirkFeedLikeDTO, chirkFeedDislikeDTO);

        chirkFeedDTOLikeList = Arrays.asList(chirkFeedLikeDTO);

        chirkFeedDTODislikeList = Arrays.asList(chirkFeedDislikeDTO);

        userProfileDTO = new UserProfileDTO(userFirstName, userLastName, email, 1);

        userInfoUpdateDTO = new UserInfoUpdateDTO(userFirstName, userLastName);

        userPasswordUpdateDTO = new UserPasswordUpdateDTO(password, "NewVerySecuredSecretPasswordThatNoOneKnowsAboutExceptMe");
    }

    @Test
    public void testUsersChirks() throws Exception {
        when(userProfileController.usersChirks(authorizationHeader, page)).thenReturn(chirkFeedDTOList);

        mockMvc.perform(get("/profile/myChirks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader)
                        .param("page", "1"))
                .andExpect(status().isOk());
    }
}