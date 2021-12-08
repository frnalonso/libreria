package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio servicioAutor;
    @Autowired
    private EditorialServicio servicioEditorial;
    
    @Transactional
    public void alta(Long isbn, String titulo, Integer anio, Integer ejemplares,String idAutor, String idEditorial) throws ErrorServicio, Exception  {
        validar(isbn, titulo, anio, ejemplares);
        
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(true);
        
        Autor autor = servicioAutor.buscarPorId(idAutor);
        libro.setAutor(autor);
        Editorial editorial = servicioEditorial.buscarEditorialPorId(idEditorial);
        libro.setEditorial(editorial);
      

        //libro.setEjemplaresPrestados(ejemplaresPrestados);
        //libro.setEjemplaresRestantes(ejemplaresRestantes);
        
        
        libroRepositorio.save(libro);
        
        
    }
    
    
    @Transactional
    public void baja(String idLibro) {
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if(respuesta.isPresent()) {
            Libro libro = libroRepositorio.findById(idLibro).get();
            libro.setAlta(false);
        }
    }
    
    @Transactional
    public void modificarLibro(String id,Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        
        validar(isbn, titulo, anio, ejemplares);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()) {
            
        Libro libro = libroRepositorio.findById(id).get();
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
//       libro.setAutor(servicioAutor.modificar(nombreAutor));
//       libro.setEditorial(servicioEditorial.modificar(tituloEditorial));
        
        libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro solicitado.");
        }
        
    }
    
    @Transactional
    public void eliminarLibro(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Libro libro = libroRepositorio.findById(id).get();
            if(libro.isAlta() == false) {  
            libroRepositorio.delete(libro);
            } else {
                throw new ErrorServicio("El libro no está dado de baja, no se puede eliminar.");
            }
        }
    }
    
    @Transactional
    public List<Libro> mostrarLibros() {
       List <Libro> libro = libroRepositorio.findAll();
        
        return libro;
    }
    
    public Libro buscarLibro(String titulo) {
        
        Libro libro = libroRepositorio.buscarPorTitulo(titulo);
        
        return libro;
    }
    
    @Transactional
    public Libro buscarPorId(String id) {
        return libroRepositorio.getOne(id);
    }
    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
       
        
       if(titulo == null || titulo.isEmpty()) {
           throw new ErrorServicio("El titulo no puede ser nulo.");
       }
        
        if(isbn == null || isbn<0) {
           throw new ErrorServicio("El isbn esta incorrecto.");
       }
        
       
       if(anio == null || anio<0) {
           throw new ErrorServicio("El anio no puede ser vacio o negativo.");
       } 
       
       if(ejemplares == null || ejemplares<0) {
           throw new ErrorServicio("Los ejemplares no pueden ser nulos o negativos.");
       }
//       if(ejemplaresPrestados == null || ejemplares<0) {
//           throw new ErrorServicio("Los ejemplares prestados no pueden ser nulos o negativos.");
//       }
//       if(ejemplaresRestantes == null || ejemplares<0) {
//           throw new ErrorServicio("Los ejemplares restantes no pueden ser nulos o negativos.");
//       }
    }
    
}
