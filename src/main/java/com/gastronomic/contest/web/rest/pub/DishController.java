package com.gastronomic.contest.web.rest.pub;

import com.gastronomic.contest.service.DishService;
import com.gastronomic.contest.service.dto.DishDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/dish")
public class DishController {

    private final DishService service;

    @Autowired
    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getById(@PathVariable("id") Long id) {
        DishDTO dishDTO = service.getById(id);
        return ResponseEntity.ok(dishDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<DishDTO>> getAll() {
        List<DishDTO> dtoList = service.getAll();
        return ResponseEntity.ok(dtoList);
    }
}
