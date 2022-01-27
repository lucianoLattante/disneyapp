
package com.alkemyprueba.disney.services;

import com.alkemyprueba.disney.Repositorios.UserRepository;
import com.alkemyprueba.disney.entities.User;
import com.alkemyprueba.disney.enums.Rol;
import com.alkemyprueba.disney.errores.DisneyError;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository usuarioRepositorio;

    @Autowired
    private MailService mailService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = usuarioRepositorio.findByUsername(username);

            List<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol()));

            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");

        }
    }
    
    @Transactional
    public User registrar(User user,String password2) throws DisneyError{
        if (user.getPassword()==null || user.getPassword().isEmpty()){
            throw new DisneyError("La contraseña no debe estar vacía");
        }
        
        if (!user.getPassword().equals(password2)){
            throw new DisneyError("Las contraseñas deben coincidir");
        }
        
        if(user.getUsername()==null || user.getUsername().isEmpty()){
            throw new DisneyError("Ingrese un nombre de usuario correcto");
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new DisneyError("Ingrese un email valido");
        }

        user.setRol(Rol.USER);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setPassword(encoder.encode(user.getPassword()));

        mailService.enviarMail(user.getEmail(),"Registro","Muchas gracias por registrarte en la aplicacion de disney "+user.getUsername()+"! Se viene una experiencia inolvidable para vos!");
        
        return usuarioRepositorio.save(user);
    }

    public User findById(String id) {
        return usuarioRepositorio.findById(id).get();
    }
}
