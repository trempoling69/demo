package com.example.demo.dto;

import java.util.Set;

public class NoteDTO {
    private String title;
    private String content;
    private Set<Long> categoryIds;

    // Constructeur
    public NoteDTO(String title, String content, Set<Long> categoryIds) {
        this.title = title;
        this.content = content;
        this.categoryIds = categoryIds;
    }

    // Getters et setters

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }
}
