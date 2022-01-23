
package com.alkemyprueba.disney.services;

import com.alkemyprueba.disney.Repositorios.PersonajeRepository;
import com.alkemyprueba.disney.entities.Foto;
import com.alkemyprueba.disney.entities.PeliculaOSerie;
import com.alkemyprueba.disney.entities.Personaje;
import com.alkemyprueba.disney.errores.DisneyError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonajeService {
    
    @Autowired
    private PersonajeRepository personajeRepository;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Transactional
    public Personaje save(Personaje personaje,MultipartFile foto) throws DisneyError, IOException{

        if (foto == null || foto.isEmpty()){
            if(personaje.getId() != null){
                Personaje personaje1= personajeRepository.findById(personaje.getId()).get();
                if(personaje1.getFoto()!=null){
                    personaje.setFoto(personaje1.getFoto());
                }else{
                    throw new DisneyError("La foto no puede estar vacia");
                }
            }else{
                throw new DisneyError("La foto no puede estar vacia");
            }
        }else{
            Foto fotox=fotoServicio.guardar(foto);
            personaje.setFoto(fotox);
        }

        if(personaje.getEdad() == null || personaje.getEdad() <=0 ){
            throw new DisneyError("El personaje debe tener una edada valida");
        }
        
        if(personaje.getHistoria() == null || personaje.getHistoria().isEmpty()){
            throw new DisneyError("El personaje no puede tener una historia vacia");
        }
        
        if (personaje.getNombre() == null || personaje.getNombre().isEmpty()){
            throw new DisneyError("El personaje debe tener nombre");
        }
        
        if(personaje.getPeso()<=0){
            throw new DisneyError("Ingrese un peso valido para el personaje");
        }
        
        return personajeRepository.save(personaje);
    }
    
    @Transactional
    public void eliminar(String id){
        personajeRepository.deleteById(id);
    }
    
    public List<Personaje> listAll(){
        return personajeRepository.findAll();
    }

    public Personaje findById(String id) {
        return personajeRepository.findById(id).get();
    }

    public List<Personaje> buscar(String nombre, Integer edad, List<PeliculaOSerie> peliculaOSerieList) {
        List<Personaje> lista= personajeRepository.findByNombre(nombre);
        if(edad!=null){
            lista =personajeRepository.findByNombreEdad(nombre,edad);
        }

        List<Personaje> lista2= new ArrayList<>();

        if(peliculaOSerieList !=null && !peliculaOSerieList.isEmpty()){
            for (PeliculaOSerie p:
                    peliculaOSerieList) {

                for(Personaje l : lista){
                    for (PeliculaOSerie p2:
                            l.getPeliculaOSerie()) {
                        if(p2.getTitulo().equals(p.getTitulo())){
                            lista2.add(l);
                        }
                    }
                }
            }
        }else{
            lista2.addAll(lista);
        }

        return lista2;
    }
}
