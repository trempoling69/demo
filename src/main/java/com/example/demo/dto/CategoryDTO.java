package com.example.demo.dto;

public class CategoryDTO {
    private String name;

    public CategoryDTO() {
    }
    // Constructeur
    public CategoryDTO(String name) {
        this.name = name;
    }

    // Getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
