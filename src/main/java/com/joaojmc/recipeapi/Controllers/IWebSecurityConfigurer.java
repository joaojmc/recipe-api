package com.joaojmc.recipeapi.Controllers;

import com.joaojmc.recipeapi.Services.IUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class IWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    final
    IUserDetailsService iUserDetailsService;

    public IWebSecurityConfigurer(IUserDetailsService iUserDetailsService) {
        this.iUserDetailsService = iUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iUserDetailsService)
                .passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().mvcMatchers("/api/recipe/**").authenticated().and()
                .authorizeRequests().mvcMatchers("/api/register/").permitAll().and()
                .csrf().disable()
                .formLogin().and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
