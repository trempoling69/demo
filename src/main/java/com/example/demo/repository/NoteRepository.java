package com.example.demo.repository;

import com.example.demo.model.Note;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findById(Long id);
}
