package edu.egg.libreria.controladores;

import edu.egg.libreria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/registrarse")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio servicioCliente;  
    
    @GetMapping("registrarCliente")
    public String registrarUsuario() {
        
        return "registrarCliente.html";
    }
    
    @PostMapping("registrarCliente")
    public String registrar(ModelMap model, 
                            @RequestParam @Nullable String nombre, 
                            @RequestParam @Nullable Long dni, 
                            @RequestParam @Nullable String apellido, 
                            @RequestParam @Nullable String telefono, 
                            @RequestParam @Nullable String email,
                            @RequestParam @Nullable String password) {
        try {
            servicioCliente.alta(dni, nombre, apellido, telefono, email,password);
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
        }
            
        return "index.html";
    }
    
    
    
}
