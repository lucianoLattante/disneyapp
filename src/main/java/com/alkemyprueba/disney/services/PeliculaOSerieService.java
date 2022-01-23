package com.alkemyprueba.disney.services;

import com.alkemyprueba.disney.Repositorios.FotoRepository;
import com.alkemyprueba.disney.Repositorios.PeliculaOSerieRepository;
import com.alkemyprueba.disney.entities.Foto;
import com.alkemyprueba.disney.entities.PeliculaOSerie;
import com.alkemyprueba.disney.entities.Personaje;
import com.alkemyprueba.disney.errores.DisneyError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeliculaOSerieService {

    @Autowired
    private PeliculaOSerieRepository peliculaOSerieRepository;

    @Autowired
    private FotoServicio fotoServicio;

    public List<PeliculaOSerie> listAll(){
        return peliculaOSerieRepository.findAll();
    }

    public PeliculaOSerie findById(String id) {
        return peliculaOSerieRepository.findById(id).get();
    }

    public PeliculaOSerie save(PeliculaOSerie peliculaOSerie, MultipartFile imagen) throws DisneyError, IOException {

        if (imagen == null || imagen.isEmpty()){
            if(peliculaOSerie.getId() != null){
                PeliculaOSerie peliculaOSerie1= peliculaOSerieRepository.findById(peliculaOSerie.getId()).get();
                if(peliculaOSerie1.getFoto()!=null){
                    peliculaOSerie.setFoto(peliculaOSerie1.getFoto());
                }else{
                    throw new DisneyError("La foto no puede estar vacia");
                }
            }else{
                throw new DisneyError("La foto no puede estar vacia");
            }
        }else{
            Foto fotox=fotoServicio.guardar(imagen);
            peliculaOSerie.setFoto(fotox);
        }

        if(peliculaOSerie.getCalificacion()>5 || peliculaOSerie.getCalificacion()<1){
            throw new DisneyError("La calificacion no debe ser menor a 1 estrella ni mayor a 5");
        }

        if(peliculaOSerie.getCalificacion() ==null){
            throw new DisneyError("La calificacion no puede ser nula");
        }

        if (peliculaOSerie.getFechaDeCreacion() == null || peliculaOSerie.getFechaDeCreacion().after(new Date())){
            throw new DisneyError("La pelicula debe tener una fecha de creacion valida");
        }

        if (peliculaOSerie.getTitulo() == null){
            throw new DisneyError("El titulo no debe estar vacio");
        }

        return peliculaOSerieRepository.save(peliculaOSerie);
    }

    @Transactional
    public void eliminar(String id) {
        peliculaOSerieRepository.deleteById(id);
    }

    public List<PeliculaOSerie> buscar(String titulo,String genre,String order) {

        List<PeliculaOSerie> listila=null;

        if(order.equals("asc")){
            listila= peliculaOSerieRepository.findByTituloGeneroOrdenar(titulo,genre);
        }

        if(order.equals("desc")){
            listila= peliculaOSerieRepository.findByTituloGeneroOrdenarDesc(titulo,genre);
        }

        return listila;
    }
}
