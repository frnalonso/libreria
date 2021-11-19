package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public Autor alta(String nombre) throws ErrorServicio {
        try {
        validar(nombre);
            Autor autor = new Autor();
                autor.setNombre(nombre);
                autor.setAlta(true);
                
                return autorRepositorio.save(autor);
            
        } catch (Exception e) {
            throw new ErrorServicio("El autor no puede ser nulo.");
        }
        
    }
    
    @Transactional
    public List<Autor> mostrarAutores() {
        List<Autor> autores = autorRepositorio.findAll();
        return autores;
    }
    
    @Transactional
    public Autor modificar(String id, String nombre) throws ErrorServicio {
        
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();
        autor.setNombre(nombre);
        autor.setAlta(false);
        
        autorRepositorio.save(autor);
        
        return autor;
        } else {
            throw new ErrorServicio("No existe ese autor.");
        }
                
    }
    
    public void eliminarAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();
           autorRepositorio.delete(autor);
        }
    }
    
    @Transactional
    public Autor buscarPorId(String id) throws Exception {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()) {
            return autorRepositorio.findById(id).get();
        } else {
            throw new Exception("No se encontró el id del Autor.");
        }
    }
    
    public void validar(String nombre) throws ErrorServicio {
        if(nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("No se puede colocar un autor nulo.");
        }
    }
    
    
}
