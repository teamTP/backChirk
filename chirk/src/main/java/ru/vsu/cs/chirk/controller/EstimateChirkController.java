package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    //TODO добавить токены в параметры

    /*
    Работвет все так
    Изначально у пользователя нет токинов, он гость
    после авторизации или регистрации методы из UserController отдают 2 токена пользователю
    Все дальнейшие действия должны проходить только при наличии у пользователя токена
    то есть в токене лежат айдишник юзернейм и роль
    например когда пользователь хочет добавить чирк, вместе с текстом он должен дать свой аксесс токен
    если токен отсутствует либо недействиетльный, то пользователю будет отказано в добавлении чирка
    и так для любый действий кроме просмотра общей ленты, регистрации и авторизации
     */

    @PostMapping("/add")
    public void createEstimate(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody RequestEstimateDTO requestEstimateDTO) {
        String accessToken =jwtTokenProvider.extractAccessToken(authorizationHeader);
        Long userId = jwtTokenProvider.getIdFromJwt(accessToken);
        estimateChirkService.createEstimate(requestEstimateDTO, userId);
    }

}
