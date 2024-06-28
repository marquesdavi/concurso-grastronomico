package com.gastronomic.contest.repository;

import com.gastronomic.contest.domain.Dish;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    @Override
    Optional<Dish> findById(Long aLong);
}
