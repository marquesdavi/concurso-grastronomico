package com.gastronomic.contest.service.dto;

import com.gastronomic.contest.domain.Dish;

public class DishDTO {

    private String title;
    private String description;
    private byte[] image;
    private String imageContentType;
    private String restaurant;

    // Default constructor
    public DishDTO() {}

    // Parameterized constructor
    public DishDTO(String title, String description, byte[] image, String imageContentType, String restaurant) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.imageContentType = imageContentType;
        this.restaurant = restaurant;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    // toDto method
    public static DishDTO toDto(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setTitle(dish.getTitle());
        dto.setDescription(dish.getDescription());
        dto.setImage(dish.getImage());
        dto.setImageContentType(dish.getImageContentType());
        dto.setRestaurant(dish.getRestaurant());
        return dto;
    }
}
