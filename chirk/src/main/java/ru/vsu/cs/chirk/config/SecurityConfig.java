package ru.vsu.cs.chirk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import ru.vsu.csf.asashina.marketservice.filter.AuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
//import static ru.vsu.csf.asashina.marketservice.model.constant.RoleName.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

//    private final AuthenticationFilter authenticationFilter;
//    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
                .requestMatchers("/auth/*").permitAll()
                .requestMatchers(GET, "/categories/**", "/products/**", "/v3/api-docs/**",
                        "/swagger-ui/**", "/swagger-ui.html").permitAll()

//                .requestMatchers(GET, "/orders/**").hasAnyAuthority(USER)

//                .requestMatchers(POST, "/categories/**", "/products").hasAnyAuthority(ADMIN)
//                .requestMatchers(PUT, "/products/*").hasAnyAuthority(ADMIN)
//                .requestMatchers(DELETE, "/categories/*", "/products/**").hasAnyAuthority(ADMIN)

                .anyRequest().authenticated();

//        http.authenticationProvider(authenticationProvider)
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//        ;

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }
}