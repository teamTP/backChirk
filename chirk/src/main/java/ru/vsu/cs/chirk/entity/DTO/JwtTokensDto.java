package ru.vsu.cs.chirk.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokensDto {
    private String accessToken;
    private String refreshToken;
}
