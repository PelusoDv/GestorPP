
package com.incade.GestorPP.Repositorio;

import com.incade.GestorPP.Entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    
}
