package com.joaojmc.recipeapi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    int id;
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotBlank
    String category;
    LocalDateTime date;
    @NotEmpty
    @ElementCollection
    List<String> ingredients;
    @NotEmpty
    @ElementCollection
    List<String> directions;
    @JsonIgnore
    String author;
}

