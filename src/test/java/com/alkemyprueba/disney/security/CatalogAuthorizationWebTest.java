package com.alkemyprueba.disney.security;

import com.alkemyprueba.disney.Security;
import com.alkemyprueba.disney.controller.GeneroController;
import com.alkemyprueba.disney.controller.PeliculaOSerieController;
import com.alkemyprueba.disney.controller.PersonajeController;
import com.alkemyprueba.disney.services.GeneroService;
import com.alkemyprueba.disney.services.PeliculaOSerieService;
import com.alkemyprueba.disney.services.PersonajeService;
import com.alkemyprueba.disney.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({PeliculaOSerieController.class, PersonajeController.class, GeneroController.class})
@Import(Security.class)
class CatalogAuthorizationWebTest {
    @Autowired MockMvc mvc;
    @MockBean UserService users;
    @MockBean GeneroService genres;
    @MockBean PeliculaOSerieService movies;
    @MockBean PersonajeService characters;

    @Test
    void readOnlyUserCannotDeleteOrCreateEvenWithCsrf() throws Exception {
        mvc.perform(post("/movies/eliminar-pelicula").with(user("reader").roles("USER")).with(csrf())
                .param("id", "movie-1")).andExpect(status().isForbidden());
        mvc.perform(post("/crear-generos/crear-geneross").with(user("reader").roles("USER")).with(csrf()))
                .andExpect(status().isForbidden());
        verifyNoInteractions(movies, genres);
    }

    @Test
    void noGetRequestCanDeleteEvenForAnAdministrator() throws Exception {
        mvc.perform(get("/movies/eliminar-pelicula").with(user("admin").roles("ADMIN")).param("id", "movie-1"))
                .andExpect(status().isMethodNotAllowed());
        mvc.perform(get("/characters/eliminar-personaje").with(user("admin").roles("ADMIN")).param("id", "char-1"))
                .andExpect(status().isMethodNotAllowed());
        verifyNoInteractions(movies, characters);
    }

    @Test
    void administratorNeedsCsrfToDelete() throws Exception {
        mvc.perform(post("/characters/eliminar-personaje").with(user("admin").roles("ADMIN")).param("id", "char-1"))
                .andExpect(status().isForbidden());
        verifyNoInteractions(characters);
    }

    @Test
    void authorizedPostsReachTheExpectedService() throws Exception {
        mvc.perform(post("/movies/eliminar-pelicula").with(user("admin").roles("ADMIN")).with(csrf())
                .param("id", "movie-1")).andExpect(redirectedUrl("/movies"));
        mvc.perform(post("/characters/eliminar-personaje").with(user("admin").roles("ADMIN")).with(csrf())
                .param("id", "char-1")).andExpect(redirectedUrl("/characters"));
        verify(movies).eliminar("movie-1");
        verify(characters).eliminar("char-1");
    }
}
