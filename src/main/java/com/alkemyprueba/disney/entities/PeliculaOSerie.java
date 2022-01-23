
package com.alkemyprueba.disney.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class PeliculaOSerie {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @OneToOne
    private Foto foto;
    
    private String titulo;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaDeCreacion;
    
    private Integer calificacion;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Personaje> personajesList;

    @ManyToOne(cascade = CascadeType.ALL)
    private Genero genero;

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public List<Personaje> getPersonajesList() {
        return personajesList;
    }

    public void setPersonajesList(List<Personaje> personajesList) {
        this.personajesList = personajesList;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
