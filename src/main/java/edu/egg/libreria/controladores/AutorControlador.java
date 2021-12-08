package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import edu.egg.libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio servicioAutor;
    @Autowired
    private AutorRepositorio repositorioAutor;
    
    @GetMapping("/registrarAutor")
    public String registrarAutor(ModelMap model) throws ErrorServicio {
        
        List<Autor> autores = repositorioAutor.findAll();
        model.put("autores", autores);
      
        return "registrarAutor.html";

    }
    
    @PostMapping("/registrarAutor")
      public String registrar(ModelMap model, @RequestParam @Nullable String nombre) throws ErrorServicio {
        try {
        servicioAutor.alta(nombre);
            
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);

        }
        return "registrarAutor.html";

    }
    
    @GetMapping("/modificarAutor/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap model) throws Exception {
        model.put("autor",servicioAutor.buscarPorId(id));
        
        return "modificarAutor.html";
    }
    
    
    @PostMapping("/modificarAutor/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap model, @RequestParam @Nullable String nombre, RedirectAttributes redirect) {
        try {
            servicioAutor.modificar(id, nombre);
            redirect.addFlashAttribute("exito", "Modificación exitosa");
            return "redirect:/mostrar";
        } catch (Exception e) {
            model.put("error","Falto algún dato.");
        }
        
        return "modificarAutor.html";
    }
    
    @GetMapping("/mostrarAutores")
    public String mostrar(ModelMap model) {
        
        List<Autor> autores = servicioAutor.mostrarAutores();
        
        model.put("autores", autores);
        
        return "mostrarAutores.html";
    }
    
    @GetMapping("/eliminarAutor/{id}")
    public String eliminarAutor(@PathVariable String id, ModelMap model, RedirectAttributes redirect) {
        try {
            servicioAutor.eliminarAutor(id);
            redirect.addFlashAttribute("exito", "Eliminado con exito.");
            return "redirect:/autor/mostrarAutores";
        } catch (ErrorServicio ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/mostrarAutores";
        }
    }
    
    @GetMapping("/baja/{id}") 
    public String baja(@PathVariable String id, RedirectAttributes redirect) throws ErrorServicio {
        try {
            servicioAutor.bajaAutor(id);
            redirect.addFlashAttribute("exito","Dado de baja con exito.");
            return "redirect:/mostrarAutores";
        } catch (ErrorServicio ex) {
            throw new ErrorServicio("No se puede dar de baja."); 
        }

    }
    
}
