package edu.egg.libreria;

import edu.egg.libreria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibreriaApplication {
    
    @Autowired
    private ClienteServicio servicioCliente;

	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}
        
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { //este metodo accede para saber que rol va a tomar el usuario cuando ingresa.
            auth
                    .userDetailsService(servicioCliente)
                    .passwordEncoder(new BCryptPasswordEncoder());
                   
        }

}
