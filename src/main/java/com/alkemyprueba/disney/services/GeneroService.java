package com.alkemyprueba.disney.services;

import com.alkemyprueba.disney.Repositorios.GeneroRepository;
import com.alkemyprueba.disney.entities.Foto;
import com.alkemyprueba.disney.entities.Genero;
import com.alkemyprueba.disney.entities.Personaje;
import com.alkemyprueba.disney.errores.DisneyError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class GeneroService {
    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public Genero save(Genero genero, MultipartFile foto) throws DisneyError, IOException {
        if (genero.getNombre()==null){
            throw new DisneyError("El nombre no puede ser nulo");
        }

        if(genero.getPeliculaOSerie()==null || genero.getPeliculaOSerie().isEmpty()){
            throw new DisneyError("El genero debe contener peliculas o series");
        }

        if (foto == null || foto.isEmpty()){
            if(genero.getId() != null){
                Genero genero1= generoRepository.findById(genero.getId()).get();
                if(genero1.getFoto()!=null){
                    genero.setFoto(genero1.getFoto());
                }else{
                    throw new DisneyError("La foto no puede estar vacia");
                }
            }else{
                throw new DisneyError("La foto no puede estar vacia");
            }
        }else{
            Foto fotox=fotoServicio.guardar(foto);
            genero.setFoto(fotox);
        }

        return generoRepository.save(genero);
    }

    public List<Genero> listAll(){
        return generoRepository.findAll();
    }
}
