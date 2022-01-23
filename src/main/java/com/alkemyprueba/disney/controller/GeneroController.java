package com.alkemyprueba.disney.controller;

import com.alkemyprueba.disney.entities.Genero;
import com.alkemyprueba.disney.errores.DisneyError;
import com.alkemyprueba.disney.services.GeneroService;
import com.alkemyprueba.disney.services.PeliculaOSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/crear-generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @Autowired
    private PeliculaOSerieService peliculaOSerieService;

    @GetMapping("")
    public String crearGenero(Model model){
        model.addAttribute("pelicula",peliculaOSerieService.listAll());
        model.addAttribute("genero",new Genero());

        return "crear-genero";
    }

    @PostMapping("/crear-geneross")
    public String postGeneros(Model model, @ModelAttribute Genero genero, MultipartFile imagen){
        try {

            generoService.save(genero,imagen);

        }catch (DisneyError e){
            e.printStackTrace();
            model.addAttribute("pelicula",peliculaOSerieService.listAll());
            model.addAttribute("genero",genero);
            return "crear-genero";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("pelicula",peliculaOSerieService.listAll());
            model.addAttribute("genero",genero);
            return "crear-genero";
        }
        return "redirect:/movies";
    }

}
