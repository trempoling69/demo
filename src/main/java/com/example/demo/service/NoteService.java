package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public boolean delete(Note note) {
        if (noteRepository.existsById(note.getId())) { // Vérifie si la note existe
            noteRepository.delete(note); // Supprime la note
            return true; // Confirme la suppression
        }
        return false; // La note n'existe pas
    }
}
