package com.joaojmc.recipeapi.Services;

import com.joaojmc.recipeapi.Entities.IUserDetails;
import com.joaojmc.recipeapi.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IUserDetailsService implements UserDetailsService {

    final
    UserRepository userRepository;

    public IUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findById(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        return new IUserDetails(user.get());
    }
}
