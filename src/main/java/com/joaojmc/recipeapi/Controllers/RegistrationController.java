package com.joaojmc.recipeapi.Controllers;

import com.joaojmc.recipeapi.Entities.User;
import com.joaojmc.recipeapi.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class RegistrationController {

    final
    UserRepository userRepo;

    final
    PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("register")
    public void register(@RequestBody @Valid User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (userRepo.findById(user.getEmail()).isEmpty()) {
            userRepo.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}