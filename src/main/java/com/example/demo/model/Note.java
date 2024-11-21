package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonManagedReference
  private User user;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "note_category", // nom de la table de jointure
      joinColumns = @JoinColumn(name = "note_id"), // clé étrangère vers la note
      inverseJoinColumns = @JoinColumn(name = "category_id") // clé étrangère vers la category
  )
  @JsonManagedReference
  @Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Set<Category> categories = new HashSet<>();

  // Hibernate expects entities to have a no-arg constructor,
  // though it does not necessarily have to be public.
  private Note() {
  }

  @Override
  public String toString() {
    return "Note{" +
        "id=" + id +
        "title=" + title + '\'' +
        ", author='" + user.getFullName() + '\'' +
        ", content='" + content + '\'' +
        '}';
  }

  public Note(String content, String title) {
    this.title = title;
    this.content = content;
    // this.categoryId = categoryId;
  }

  public Long getId() {
    return this.id;
  }

  public String getContent() {
    return this.content;
  }

  public User getUser() {
    return this.user;
  }

  public String getTitle() {
    return this.title;
  }

  public Set<Category> getCategories() {
    return this.categories;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

}