package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public Editorial alta(String nombre) throws ErrorServicio {
        
        validar(nombre);
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        editorialRepositorio.save(editorial);
        
        
        return editorial;
    }
    
    @Transactional
    public Editorial modificar(String id, String nombre) throws ErrorServicio {
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()) {
            
        Editorial editorial = editorialRepositorio.findById(id).get();
        
        editorial.setNombre(nombre);
        editorial.setAlta(false);
        editorialRepositorio.save(editorial);
        return editorial;
        } else {
            throw new ErrorServicio("No se encuentra ese editorial.");
        }
        
    }
    
    @Transactional
    public void eliminarEditorial(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            if(editorial.isAlta() == false) {
            editorialRepositorio.delete(editorial);   
            } else {
                throw new ErrorServicio("No se puede eliminar ya que no está dado de baja.");
            }
        }
    }
    
     public void bajaAutor(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setAlta(false);
        }
    }
    
    @Transactional
    public Editorial buscarEditorialPorId(String id) throws Exception {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()) {
            return editorialRepositorio.findById(id).get();
        } else {
            throw new Exception("No hay editorial con esa id.");
        }
    }
    
    public List<Editorial> mostrarEditoriales() {
        List<Editorial> editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
    
    public void validar(String nombre) throws ErrorServicio {
        if(nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("No puede colocar una editorial vacia.");
        }
    }
    
}
