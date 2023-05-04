package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.EstimateChirk;
import ru.vsu.cs.chirk.service.ChirkService;
import ru.vsu.cs.chirk.service.EstimateChirkService;

@RestController
@RequestMapping("/estimate")
public class EstimateChirkController {
    @Autowired
    private EstimateChirkService estimateChirkService;
    @PostMapping("/add")
    public void createEstimate(@RequestBody EstimateChirk estimateChirk) {
        estimateChirkService.createEstimate(estimateChirk);
    }
    @DeleteMapping("/delete")
    public void deleteEstimate(@RequestBody EstimateChirk estimateChirk) {
        estimateChirkService.deleteEstimate(estimateChirk);
    }
    @GetMapping("/")
    public EstimateChirk estimateChirk() {
        return estimateChirkService.getEstimate(1);
        //return "Hello, World!";
    }
}
