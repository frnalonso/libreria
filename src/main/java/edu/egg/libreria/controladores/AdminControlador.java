package edu.egg.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    
    @GetMapping("/inicioAdmin")
    public String inicioAdmin(ModelMap model) {
        
        return "inicioAdmin";
    }
    
}
