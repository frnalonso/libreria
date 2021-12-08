package edu.egg.libreria.servicios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.egg.libreria.entidades.Cliente;
import edu.egg.libreria.repositorios.ClienteRepositorio;

public class AdministradorServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio repositorioCliente;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente admin = repositorioCliente.buscarPorEmail(email);
        if(admin != null) {
        
        List<GrantedAuthority> permisos = new ArrayList<>();    //Creo lista de permisos.

        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+admin.getRol().name());
        permisos.add(p1);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("adminsession", admin);

            User user = new User(admin.getEmail(), admin.getPassword(), permisos);
                
            return user;
        } else {
            return null;
        }
  
    }


  
    
}
