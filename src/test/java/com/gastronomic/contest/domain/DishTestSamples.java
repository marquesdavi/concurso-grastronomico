package com.gastronomic.contest.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DishTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dish getDishSample1() {
        return new Dish().id(1L).title("title1").description("description1").restaurant("restaurant1");
    }

    public static Dish getDishSample2() {
        return new Dish().id(2L).title("title2").description("description2").restaurant("restaurant2");
    }

    public static Dish getDishRandomSampleGenerator() {
        return new Dish()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .restaurant(UUID.randomUUID().toString());
    }
}
