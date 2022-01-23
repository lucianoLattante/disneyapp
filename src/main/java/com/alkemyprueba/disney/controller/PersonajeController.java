
package com.alkemyprueba.disney.controller;
import com.alkemyprueba.disney.entities.PeliculaOSerie;
import com.alkemyprueba.disney.entities.Personaje;
import com.alkemyprueba.disney.services.PeliculaOSerieService;
import com.alkemyprueba.disney.services.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.TableGenerator;
import java.util.List;

@Controller
@RequestMapping("/characters")
public class PersonajeController {
    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private PeliculaOSerieService peliculaOSerieService;
    
    @GetMapping("")
    public String personajesVist(Model model){
        model.addAttribute("personajelist",personajeService.listAll());
        model.addAttribute("peliculas",peliculaOSerieService.listAll());
        return "personajes";
    }

    @GetMapping("/crear-personajes")
    public String crearNuevoPersonaje(Model model){
        model.addAttribute("personaje",new Personaje());
        model.addAttribute("peliculas",peliculaOSerieService.listAll());

        return "crear-personajes";
    }

    @PostMapping("/postpersonajes")
    public String postPersonaje(MultipartFile imagen, @ModelAttribute Personaje personaje,Model model){
        try {
            personajeService.save(personaje,imagen);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("personaje",personaje);
            model.addAttribute("peliculas",peliculaOSerieService.listAll());
            return "crear-personajes";
        }
        return "redirect:/characters";
    }

    @GetMapping("/detalles")
    public String verDetalles(@RequestParam(required = true) String id,Model model){

        model.addAttribute("personaje",personajeService.findById(id));

        return "detalles";
    }

    @GetMapping("/eliminar-personaje")
    public String eliminar(@RequestParam(required = true) String id, RedirectAttributes redirectAttributes){
        personajeService.eliminar(id);
        return "redirect:/characters";
    }

    @GetMapping("/editar-personaje")
    public String editar(@RequestParam(required = true) String id,Model model){

        model.addAttribute("personaje",personajeService.findById(id));

        model.addAttribute("peliculas",peliculaOSerieService.listAll());

        return "editar-personaje";

    }

    @PostMapping("/editando-personaje")
    public String editando(Model model,@ModelAttribute Personaje personaje,MultipartFile foto){
        try {
            personajeService.save(personaje,foto);
        }catch (Exception e){
            model.addAttribute("personaje",personaje);
            model.addAttribute("peliculas",peliculaOSerieService.listAll());
            return "editar-personaje";
        }

        return "redirect:/characters";
    }

    @GetMapping("/filtrar")
    public String filtrarBusqueda(@RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) Integer edad,
                                  @RequestParam(required = false) List<PeliculaOSerie> peliculaOSerieList,
                                  Model model){
        model.addAttribute("peliculas",peliculaOSerieService.listAll());
        model.addAttribute("personajelist",personajeService.buscar(nombre,edad,peliculaOSerieList));
        return "personajes";
    }
    
}
