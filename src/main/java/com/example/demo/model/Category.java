package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @JsonBackReference
    @Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Note> notes = new HashSet<>();

    // Hibernate expects entities to have a no-arg constructor,
    // though it does not necessarily have to be public.
    private Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                "name=" + name + '\'' +
                '}';
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }
}