
package com.alkemyprueba.disney;

import com.alkemyprueba.disney.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserService userService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/error", "/css/**", "/imgs/**", "/js/**",
                        "/auth/login", "/auth/register", "/auth/registrando").permitAll()
                .antMatchers("/crear-generos/**", "/movies/crear-peliculas", "/movies/editar-pelicula",
                        "/characters/crear-personajes", "/characters/editar-personaje").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/movies/**", "/characters/**").hasRole("ADMIN")
                .antMatchers("/movies/**", "/characters/**", "/foto/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                      .loginPage("/auth/login")
                      .usernameParameter("username")
                      .passwordParameter("password")
                      .defaultSuccessUrl("/")
                      .loginProcessingUrl("/logincheck")
                      .failureUrl("/auth/login?error=error")
                      .permitAll()
                .and().logout()
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/auth/login?logout");
    }
    
    
}
