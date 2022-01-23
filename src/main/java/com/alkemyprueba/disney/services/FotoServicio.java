package com.alkemyprueba.disney.services;

import com.alkemyprueba.disney.Repositorios.FotoRepository;
import com.alkemyprueba.disney.entities.Foto;
import com.alkemyprueba.disney.errores.DisneyError;
import java.io.IOException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FotoServicio {

    @Autowired
    private FotoRepository fotoRepositorio;

    @Transactional
    public Foto guardar(MultipartFile archivo) throws DisneyError, IOException {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
             

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                throw new IOException("No se ha podido subir su foto "+e.getMessage());
            }
        }else{
            throw new DisneyError("La foto no puede ser nula");
        }
                
    }

    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws DisneyError, IOException {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }}
            return null;

        }
        
       
    }
