
package com.alkemyprueba.disney.controller;

import com.alkemyprueba.disney.services.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalController {
    
    @Autowired
    private PersonajeService personajeService;
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    
}
