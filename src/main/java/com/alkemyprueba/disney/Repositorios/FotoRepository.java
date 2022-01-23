
package com.alkemyprueba.disney.Repositorios;

import com.alkemyprueba.disney.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, String>{
    
}
