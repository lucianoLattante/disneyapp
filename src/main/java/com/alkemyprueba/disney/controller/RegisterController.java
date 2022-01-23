/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alkemyprueba.disney.controller;

import com.alkemyprueba.disney.entities.User;
import com.alkemyprueba.disney.errores.DisneyError;
import com.alkemyprueba.disney.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class RegisterController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
    public String registrarse(Model model){
        model.addAttribute("user",new User());
        
        return "register";
    }
    
    @PostMapping("/registrando")
    public String procesando(Model model,@ModelAttribute User user,@RequestParam String password2,RedirectAttributes redirectAttributes){
        try {
            userService.registrar(user, password2);
            
            redirectAttributes.addFlashAttribute("success","El usuario fue registrado con exito");
        } catch (DisneyError e) {
            model.addAttribute("error",e.getMessage());
            model.addAttribute("user",user);
            e.printStackTrace();
            return "register";
        }
        return "redirect:/auth/register";
    }
}
