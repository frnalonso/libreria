package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio servicioEditorial;
    
    @GetMapping("/mostrarEditoriales")
    public String mostrar(ModelMap model) {
        
        List<Editorial> editoriales = servicioEditorial.mostrarEditoriales();
        
         model.put("editoriales",editoriales);
        
        return "mostrarEditoriales.html";
    }
    
    @GetMapping("/registrarEditorial")
    public String registrar() {
        
        
        
        return "registrarEditorial.html";
    }
    
    @PostMapping("/registrarEditorial")
    public String registrarEditorial(ModelMap model,@RequestParam @Nullable String nombre) {
        try {
            servicioEditorial.alta(nombre);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
        }
        
        return "registrarEditorial.html";
    }
    
    @GetMapping("/mostrarEditoriales/{id}")
    public String eliminarEditorial(ModelMap model, @PathVariable String id, RedirectAttributes redirect) {
        try {
            servicioEditorial.eliminarEditorial(id);
            redirect.addFlashAttribute("exito","Eliminado con exito.");
            return "redirect:/editorial/mostrarEditoriales";
        } catch (Exception e) {
            redirect.addFlashAttribute("error","No se pudo eliminar.");
            return "redirect:/mostrarEditoriales";
        }
    }
    
    
}
