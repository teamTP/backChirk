package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.service.ChirkService;
@RestController
@RequestMapping("/chirks")
public class ChirkController {
    @Autowired
    private ChirkService chirkService;

    @PostMapping("/add")
    public Chirk createChirk(@RequestBody Chirk chirk) {
        chirkService.createChirk(chirk);
        System.out.println(chirk);
        return chirk;
    }
    @GetMapping("/")
    public String chirk() {
        return "Hello, World!";
    }
}
