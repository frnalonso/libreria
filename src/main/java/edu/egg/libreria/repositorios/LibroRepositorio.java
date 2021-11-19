package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepositorio extends JpaRepository<Libro,String> {
    
  @Query("SELECT l FROM Libro l WHERE l.titulo = titulo")
  public Libro buscarPorTitulo(@Param("titulo") String titulo);
  
    
}
