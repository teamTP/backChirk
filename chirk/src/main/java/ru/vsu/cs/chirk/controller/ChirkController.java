package ru.vsu.cs.chirk.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkDTO;
import ru.vsu.cs.chirk.entity.DTO.requestDTO.RequestChirkIdDTO;
import ru.vsu.cs.chirk.service.ChirkService;
@RestController
@RequestMapping("/chirks")
public class ChirkController {
    @Autowired
    private ChirkService chirkService;

    @PostMapping("/add")
    public void createChirk(@RequestBody RequestChirkDTO requestChirkDTO) {
        chirkService.createChirk(requestChirkDTO);
    }
    @DeleteMapping("/delete")
    public void deleteChirk(@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.deleteChirk(requestChirkIdDTO.getIdChirk());
    }
    @PutMapping("/updateVisible")
    public void updateVisible(@RequestBody RequestChirkIdDTO requestChirkIdDTO) {
        chirkService.updateVisible(requestChirkIdDTO.getIdChirk());
    }
}
