package com.joaojmc.recipeapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
public class User {
    @Id
    @Pattern(regexp = ".*@.*\\..*")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String role;

    public User() {
        this.role = "ROLE_BASIC";
    }
}
