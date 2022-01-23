
package com.alkemyprueba.disney.Repositorios;

import com.alkemyprueba.disney.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
    @Query("select p from User p where p.username= :username")
    User findByUsername(@Param(value = "username") String username);
}
