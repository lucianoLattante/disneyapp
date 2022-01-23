
package com.alkemyprueba.disney.controller;

import com.alkemyprueba.disney.entities.PeliculaOSerie;
import com.alkemyprueba.disney.entities.Personaje;
import com.alkemyprueba.disney.errores.DisneyError;
import com.alkemyprueba.disney.services.PeliculaOSerieService;
import com.alkemyprueba.disney.services.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private PeliculaOSerieService peliculaOSerieService;
    
    @GetMapping("/personaje")
    public ResponseEntity<byte[]> fotoUsuario(@RequestParam String id){
        try {
            Personaje personaje = personajeService.findById(id);
            if(personaje.getFoto()==null){
                throw new DisneyError("El usuario no tiene foto");
            }
            byte[] foto= personaje.getFoto().getContenido();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>(foto,headers, HttpStatus.OK);
            
        } catch (DisneyError e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pelicula")
    public ResponseEntity<byte[]> fotoPelicula(@RequestParam String id){
        try {
            PeliculaOSerie peliculaOSerie = peliculaOSerieService.findById(id);
            if(peliculaOSerie.getFoto()==null){
                throw new DisneyError("El usuario no tiene foto");
            }
            byte[] foto= peliculaOSerie.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto,headers, HttpStatus.OK);

        } catch (DisneyError e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
