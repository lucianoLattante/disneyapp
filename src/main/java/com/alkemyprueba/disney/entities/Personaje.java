
package com.alkemyprueba.disney.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Personaje {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @OneToOne
    private Foto foto;
    
    private String nombre;

    private Integer edad;
    
    private double peso;
    
    private String historia;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<PeliculaOSerie> peliculaOSerie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
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
