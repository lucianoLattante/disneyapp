package com.alkemyprueba.disney.controller;

import com.alkemyprueba.disney.entities.PeliculaOSerie;
import com.alkemyprueba.disney.entities.Personaje;
import com.alkemyprueba.disney.services.GeneroService;
import com.alkemyprueba.disney.services.PeliculaOSerieService;
import com.alkemyprueba.disney.services.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class PeliculaOSerieController {

    @Autowired
    private PeliculaOSerieService peliculaOSerieService;

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private GeneroService generoService;

    @GetMapping("")
    public String paginaPeliculas(Model model){
        model.addAttribute("peliculasList",peliculaOSerieService.listAll());

        model.addAttribute("generos",generoService.listAll());

        model.addAttribute("pelicula",new PeliculaOSerie());

        return "peliculas-series";
    }

    @GetMapping("/filtrar")
    public String filtrar(@RequestParam(required = false) String titulo,
                          @RequestParam(required = false) String genre,
                          @RequestParam(required = false) String order,
                          Model model){

        model.addAttribute("peliculasList",peliculaOSerieService.buscar(titulo,genre,order));

        model.addAttribute("generos",generoService.listAll());

        model.addAttribute("pelicula",new PeliculaOSerie());

        return "peliculas-series";
    }

    @GetMapping("/crear-peliculas")
    public String crearNuevaPelicula(Model model){
        model.addAttribute("pelicula",new PeliculaOSerie());
        model.addAttribute("personajes",personajeService.listAll());
        model.addAttribute("generos",generoService.listAll());

        return "crear-peliculas";
    }

    @PostMapping("/postpeliculas")
    public String postPelicula(MultipartFile imagen, @ModelAttribute PeliculaOSerie peliculaOSerie,Model model){
        try {
            peliculaOSerieService.save(peliculaOSerie,imagen);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("personajes",personajeService.listAll());

            model.addAttribute("generos",generoService.listAll());

            model.addAttribute("pelicula",peliculaOSerie);

            return "crear-peliculas";
        }
        return "redirect:/movies";
    }

    @GetMapping("/detalles")
    public String verDetalles(@RequestParam(required = true) String id,Model model){
        model.addAttribute("pelicula",peliculaOSerieService.findById(id));

        return "detalles2";
    }

    @GetMapping("/eliminar-pelicula")
    public String eliminar(@RequestParam(required = true) String id, RedirectAttributes redirectAttributes){
        peliculaOSerieService.eliminar(id);
        return "redirect:/movies";
    }

    @GetMapping("/editar-pelicula")
    public String editar(@RequestParam(required = true) String id,Model model){

        model.addAttribute("pelicula",peliculaOSerieService.findById(id));

        model.addAttribute("generos",generoService.listAll());

        model.addAttribute("personajes",personajeService.listAll());

        return "editar-pelicula";

    }

    @PostMapping("/editando-pelicula")
    public String editando(Model model,@ModelAttribute PeliculaOSerie peliculaOSerie,MultipartFile foto){
        try {
            peliculaOSerieService.save(peliculaOSerie,foto);
        }catch (Exception e){
            model.addAttribute("personaje",peliculaOSerie);
            model.addAttribute("generos",generoService.listAll());
            model.addAttribute("peliculas",peliculaOSerieService.listAll());
            return "editar-pelicula";
        }

        return "redirect:/movies";
    }
}
