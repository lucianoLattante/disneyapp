
package com.alkemyprueba.disney.Repositorios;

import com.alkemyprueba.disney.entities.PeliculaOSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaOSerieRepository extends JpaRepository<PeliculaOSerie, String> {


    @Query("select p from PeliculaOSerie p where p.titulo like CONCAT('%',:titulo,'%') and p.genero.id= :genre order by p.titulo asc")
    List<PeliculaOSerie> findByTituloGeneroOrdenar(@Param(value = "titulo") String titulo,
                                                   @Param(value = "genre") String genre);

    @Query("select p from PeliculaOSerie p where p.titulo like CONCAT('%',:titulo,'%') and p.genero.id= :genre order by p.titulo desc")
    List<PeliculaOSerie> findByTituloGeneroOrdenarDesc(@Param(value = "titulo") String titulo,
                                                       @Param(value = "genre") String genre);
}
