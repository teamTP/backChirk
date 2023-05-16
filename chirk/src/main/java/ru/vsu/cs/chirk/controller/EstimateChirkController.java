package ru.vsu.cs.chirk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestEstimateDTO;
import ru.vsu.cs.chirk.service.EstimateChirkService;

@RestController
@RequestMapping("/estimate")
public class EstimateChirkController {
    @Autowired
    private EstimateChirkService estimateChirkService;
    @PostMapping("/add")
    public void createEstimate(@RequestBody RequestEstimateDTO requestEstimateDTO) {
        estimateChirkService.createEstimate(requestEstimateDTO);
    }
    @DeleteMapping("/delete")
    public void deleteEstimate(@RequestBody RequestEstimateDTO requestEstimateDTO) {
        estimateChirkService.deleteEstimate(requestEstimateDTO);
    }
}
