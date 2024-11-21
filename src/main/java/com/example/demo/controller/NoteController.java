package com.example.demo.controller;

import com.example.demo.dto.NoteDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.NoteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/notes")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Note> findAll() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Note> findById(@PathVariable Long id) {
        return noteService.findById(id);
    }

    // create a note
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Note create(@RequestBody NoteDTO noteDto) {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        logger.error("USER {}", user);

        logger.error("NOTE {}", noteDto);
        Note note = new Note(noteDto.getContent(), noteDto.getTitle());
        note.setUser(user);

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : noteDto.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            categories.add(category);
        }
        note.setCategories(categories);
        return noteService.save(note);
    }

    // update a book
    // @PutMapping
    // public Book update(@RequestBody Book book) {
    // return bookService.save(book);
    // }
    @GetMapping("/category/{id}")
    public Set<Note> findByCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        return category.getNotes();
    }

    @GetMapping("/user/{id}")
    public List<Note> findByUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        return user.getNotes();
    }

    // delete a note
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {

        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Note note = noteService.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée"));

        // Vérifier que l'utilisateur connecté est bien le propriétaire de la note
        if (!note.getUser().equals(user)) {
            throw new RuntimeException("Non autorisé à supprimer cette note");
        }

        // Supprimer la note
        noteService.delete(note);
    }
}
