package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Cliente;
import edu.egg.libreria.enums.Rol;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClienteServicio implements UserDetailsService {
    
  @Autowired
  private ClienteRepositorio repositorioCliente;
  
  

  public void alta(Long documento, String nombre, String apellido, String telefono, String email, String password) throws ErrorServicio {
      try {
          Cliente cliente = new Cliente();
          cliente.setNombre(nombre);
          cliente.setApellido(apellido);
          cliente.setDocumento(documento);
          cliente.setTelefono(telefono);
          cliente.setEmail(email);
          cliente.setAlta(true);
          cliente.setRol(Rol.USUARIO);
          
          String encriptada = new BCryptPasswordEncoder().encode(password);
          cliente.setPassword(encriptada);
          
          repositorioCliente.save(cliente);
      } catch (Exception e) {
          throw new ErrorServicio("No se puede registrar el Cliente.");
      }   
  }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = repositorioCliente.buscarPorEmail(email);
        
        
            List<GrantedAuthority> permisos = new ArrayList<>();
            //Creo una lista de permisos!
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+cliente.getRol().name());
            permisos.add(p1);
            
            //Esto me permite guadar el objeto USUARIO LOG, para luego ser utilizado.
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("clientesession", cliente); //llave + valor
         
            
            User user = new User(cliente.getEmail(), cliente.getPassword(), permisos);
            
            return user;
    
    }
    
    
  
  
    
}
