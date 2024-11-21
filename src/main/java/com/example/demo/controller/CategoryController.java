package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Category> findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    // create a note
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Category create(@RequestBody CategoryDTO categoryDto) {
        // logger.error("NOTE {}", categoryDto);
        Category category = new Category(categoryDto.getName());
        return categoryService.save(category);
    }

    // update a book
    // @PutMapping
    // public Book update(@RequestBody Book book) {
    // return bookService.save(book);
    // }

    // delete a note
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
public void deleteById(@PathVariable String id) {
    try {
        Long categoryId = Long.valueOf(id); // Conversion explicite
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category non trouvée"));

        category.getNotes().forEach(note -> note.getCategories().remove(category));

        categoryService.delete(category);
    } catch (NumberFormatException e) {
        throw new RuntimeException("ID invalide, il doit être un nombre entier valide.");
    }
}
}
