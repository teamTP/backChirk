package ru.vsu.cs.chirk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vsu.cs.chirk.security.JwtTokenFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final JwtTokenFilter jwtTokenFilter;

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.cors();
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeHttpRequests()
//                .requestMatchers("/auth/").permitAll()
//                .requestMatchers(GET, "/categories/", "/products/", "/v3/api-docs/",
//                        "/swagger-ui/", "/swagger-ui.html").permitAll()
//                .requestMatchers(GET, "/user/").permitAll()
////                .requestMatchers(GET, "/orders/").hasAnyAuthority(USER)
//
////                .requestMatchers(POST, "/categories/**", "/products").hasAnyAuthority(ADMIN)
////                .requestMatchers(PUT, "/products/").hasAnyAuthority(ADMIN)
////                .requestMatchers(DELETE, "/categories/*", "/products/**").hasAnyAuthority(ADMIN)
//
//                .anyRequest().authenticated();
////        http.authorizeHttpRequests().requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
////        http.authorizeHttpRequests().requestMatchers("/user/**").permitAll();
////        http.authorizeHttpRequests().requestMatchers("/api/auth/**").permitAll();
////        http.authorizeHttpRequests().anyRequest().authenticated();
////        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
//                .requestMatchers("/auth/").permitAll()
//                .requestMatchers(GET, "/categories/", "/products/", "/v3/api-docs/",
//                        "/swagger-ui/", "/swagger-ui.html").permitAll()
                .requestMatchers(GET, "/user/**").permitAll()
                .requestMatchers(POST, "/user/**").permitAll()
//                .requestMatchers(GET, "/orders/").hasAnyAuthority(USER)

//                .requestMatchers(POST, "/categories/**", "/products").hasAnyAuthority(ADMIN)
//                .requestMatchers(PUT, "/products/").hasAnyAuthority(ADMIN)
//                .requestMatchers(DELETE, "/categories/*", "/products/**").hasAnyAuthority(ADMIN)

                .anyRequest().authenticated();

//        http.authenticationProvider(authenticationProvider)
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//        ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
