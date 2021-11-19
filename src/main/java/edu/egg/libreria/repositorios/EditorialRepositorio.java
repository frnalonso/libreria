package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EditorialRepositorio extends JpaRepository<Editorial,String> {
    
}
