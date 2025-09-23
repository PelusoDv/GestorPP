
package com.incade.gestorpp.repositorio;

import com.incade.gestorpp.entidad.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByUsuarioNombre(String usuario);
    boolean existsByUsuarioNombre(String nombre);
    boolean existsByEmail(String email);
}
