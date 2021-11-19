package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import edu.egg.libreria.repositorios.LibroRepositorio;
import edu.egg.libreria.servicios.AutorServicio;
import edu.egg.libreria.servicios.EditorialServicio;
import edu.egg.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
   @Autowired
    private EditorialServicio servicioEditorial;
    @Autowired
   private AutorServicio servicioAutor;
    @Autowired
    private LibroServicio servicioLibro;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio repositorioAutor;
    @Autowired
    private EditorialRepositorio repositorioEditorial;
   
    
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    @GetMapping("/registro") 
    public String registro(ModelMap model) {
        
         List<Autor> autores = repositorioAutor.findAll();
         List<Editorial> editoriales = repositorioEditorial.findAll();
        model.put("autores", autores);
        model.put("editoriales",editoriales);
      
        
        return "registro.html";
    }
    
    @PostMapping("/registro")
    public String registrar(ModelMap modelo, 
                            @RequestParam @Nullable String tituloLibro, 
                            @RequestParam @Nullable Long isbn, 
                            @RequestParam @Nullable Integer anio, 
                            @RequestParam @Nullable Integer ejemplares,
                            @RequestParam @Nullable Editorial idEditorial,
                            @RequestParam @Nullable Autor idAutor
                            ) throws Exception {
        try {
           
            
            servicioLibro.alta(isbn, tituloLibro, anio, ejemplares,idAutor.getId(), idEditorial.getId());
            
            
 
        } catch (ErrorServicio ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("tituloLibro", tituloLibro);
            modelo.put("isbn", isbn);
            List<Autor> autores = servicioAutor.mostrarAutores();
            modelo.put("autores",autores);
            modelo.put("ejemplares", ejemplares);
            modelo.put("anio", anio);
            
            
            return "registro.html";
        }
        return "index.html";
    }
    
    
    @GetMapping("/mostrar")
    public String mostrarLibros(ModelMap model) {
        List<Libro> libros = servicioLibro.mostrarLibros();
        List<Autor> autores = servicioAutor.mostrarAutores();
        List<Editorial> editoriales = servicioEditorial.mostrarEditoriales();
        
        
        model.put("autores", autores);
         model.put("editoriales",editoriales);
        model.put("libros", libros);


        return "mostrar.html";
    }
    
    @GetMapping("/modificar/{id}") //PATHVARIABLE:configuro variables dentro de los segmentos de la url.
    public String modificar(@PathVariable String id, ModelMap model ) {
        //model.put("autor",servicioAutor.buscarPorId(id));
        //model.put("editorial", servicioEditorial.buscarEditorialPorId(id));
        model.put("libro", servicioLibro.buscarPorId(id));
        

        return "modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, 
                            ModelMap modelo, RedirectAttributes redirect,
                            @RequestParam @Nullable String tituloLibro, 
                            @RequestParam @Nullable Long isbn, 
                            @RequestParam @Nullable Integer anio, 
                            @RequestParam @Nullable Integer ejemplares, 
                            @RequestParam @Nullable String nombreAutor, 
                            @RequestParam @Nullable String tituloEditorial
                            ) {
        
        try {
            servicioLibro.modificarLibro(id, isbn, tituloLibro, anio, ejemplares);
            
            redirect.addFlashAttribute("exito","Modificado con exito!");
            return "redirect:/mostrar";
        } catch (Exception e) {
            modelo.put("error","Falto algún dato.");
        }
        return "modificar.html";
    }
    
    @GetMapping("/mostrar/{id}")
    public String eliminar(@PathVariable String id, ModelMap model, RedirectAttributes redirect) {
        try {
            servicioLibro.eliminarLibro(id);
            redirect.addFlashAttribute("exito","Eliminado con exito.");
            return "redirect:/mostrar";
        } catch (Exception e) {
            model.put("error", "No se pudo eliminar.");
        }
        
        return "modificar.html";
        
    }
    
    

    
    
}
