
package com.alkemyprueba.disney.entities;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Genero {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String nombre;
    
    @OneToOne
    private Foto foto;
    
    @OneToMany
    private List<PeliculaOSerie> peliculaOSerie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public List<PeliculaOSerie> getPeliculaOSerie() {
        return peliculaOSerie;
    }

    public void setPeliculaOSerie(List<PeliculaOSerie> peliculaOSerie) {
        this.peliculaOSerie = peliculaOSerie;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
