package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestEstimateDTO;
import ru.vsu.cs.chirk.security.JwtTokenProvider;
import ru.vsu.cs.chirk.service.EstimateChirkService;

@RestController
@RequestMapping("/estimate")
public class EstimateChirkController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EstimateChirkService estimateChirkService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('REACT_AUTHORITY')")
    public void createEstimate(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody RequestEstimateDTO requestEstimateDTO) {
        String accessToken =jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        estimateChirkService.createEstimate(requestEstimateDTO, userId);
    }

}
