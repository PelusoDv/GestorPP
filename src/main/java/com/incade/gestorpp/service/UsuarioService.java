
package com.incade.gestorpp.service;

import com.incade.gestorpp.dto.UsuarioDTO;
import com.incade.gestorpp.entidad.Grupo;
import com.incade.gestorpp.entidad.Usuario;
import com.incade.gestorpp.repositorio.UsuarioRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepositorio repoU;
    @Autowired
    SuscripcionService susService;
    
    private final PasswordEncoder passCode = new BCryptPasswordEncoder();
    
    public Usuario login(String usuarioNombre, String password) {
        // Buscamos que el usuario exista en la base para traer sus datos en una variable
        Usuario user = repoU.findByUsuarioNombre(usuarioNombre)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar la contraseña
        if (passCode.matches(password, user.getPassword())) {
            return user; // ✅ Login correcto
        } else {
            throw new RuntimeException("Usuario o Contraseña incorrecta");
        }
    }
    
    public Usuario registrar(UsuarioDTO dto){
        // Aclaracion: "LocalDate fin" es la fecha de la suscripcion + la cantidad de tiempo de suscripcion elegido
        // ej: La fecha actual + 1 mes (suscripcion menusal) o + 12 meses (suscripcion anual) 
        Usuario newUser = new Usuario();
        
        // Verificamos que el usuario no exista en la base
        if (repoU.existsByUsuarioNombre(dto.getUsuarioNombre())) {
            throw new RuntimeException("El nombre de Usuario ya existe");
        }         
            
        // Revisamos que el email no este ya registrado        
        if(repoU.existsByEmail(dto.getEmail())){
            throw new RuntimeException("El email ya ha sido registrado");
        }                
            
        // Setteamos los demas datos
        newUser.setUsuarioNombre(dto.getUsuarioNombre());
        newUser.setNombreCompleto(dto.getNombreCompleto());
        newUser.setPassword(passCode.encode(dto.getPassword())); //Se encripta la contraseña
        newUser.setEmail(dto.getEmail());  
        
        Usuario savedUser = repoU.save(newUser);
        
        // Registramos la suscripcion elegida por el usuario
        susService.registrarSus(savedUser, dto.getPlan(), dto.getFin(), dto.getGrupo());
        
        // Guardamos el usuario
        return savedUser;
    }
    
    public void borrar(String nombre, String password) {
        // Primero confirmamos usuario y contraseña
        Usuario user = this.login(nombre, password);
        repoU.deleteById(user.getId());   
    }
    
    public List<Grupo> gruposUsuario(String usuario) {
        Usuario user = repoU.findByUsuarioNombre(usuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
       List<Grupo> grupos = susService.buscarGrupos(user.getId());
       return grupos;
    }
    
}
