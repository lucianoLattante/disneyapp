
package com.alkemyprueba.disney.Repositorios;

import com.alkemyprueba.disney.entities.PeliculaOSerie;
import com.alkemyprueba.disney.entities.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, String> {

    @Query("select p from Personaje p where p.nombre like CONCAT('%',:nombre,'%') and p.edad= :edad")
    List<Personaje> findByNombreEdad(@Param(value = "nombre") String nombre,@Param(value = "edad") Integer edad);

    @Query("select p from Personaje p where p.nombre like CONCAT('%',:nombre,'%')")
    List<Personaje> findByNombre(@Param(value = "nombre") String nombre);
}
