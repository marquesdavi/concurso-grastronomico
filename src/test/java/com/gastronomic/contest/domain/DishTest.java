package com.gastronomic.contest.domain;

import static com.gastronomic.contest.domain.DishTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gastronomic.contest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DishTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dish.class);
        Dish dish1 = getDishSample1();
        Dish dish2 = new Dish();
        assertThat(dish1).isNotEqualTo(dish2);

        dish2.setId(dish1.getId());
        assertThat(dish1).isEqualTo(dish2);

        dish2 = getDishSample2();
        assertThat(dish1).isNotEqualTo(dish2);
    }
}
