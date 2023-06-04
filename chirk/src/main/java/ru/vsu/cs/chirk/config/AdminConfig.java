package ru.vsu.cs.chirk.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.cs.chirk.entity.DTO.UserRegistrationDTO;
import ru.vsu.cs.chirk.service.AuthenticationService;


@Configuration
public class AdminConfig {
    @Value("${adm.firstname}")
    private String firstname;
    @Value("${adm.lastname}")
    private String lastname;
    @Value("${adm.email}")
    private String email;
    @Value("${adm.password}")
    private String password;
    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    public void addAdmirator(){
        try {
            UserRegistrationDTO user = new UserRegistrationDTO(
                    firstname,
                    lastname,
                    email,
                    password
            );
            authenticationService.registerAdmirator(user);
        }
        catch (Exception e)
        {
            System.out.println("exception: " + e);
        }
    }

}
