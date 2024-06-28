package com.gastronomic.contest.service;

import com.gastronomic.contest.domain.Dish;
import com.gastronomic.contest.repository.DishRepository;
import com.gastronomic.contest.service.dto.DishDTO;
import com.gastronomic.contest.web.rest.exception.GenericException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<DishDTO> getAll() {
        return dishRepository.findAll().stream().map(DishDTO::toDto).toList();
    }

    public DishDTO getById(Long id) {
        Dish instance = dishRepository
            .findById(id)
            .orElseThrow(() -> new GenericException("There is no Dish with id " + id, HttpStatus.NOT_FOUND));

        return DishDTO.toDto(instance);
    }
}
