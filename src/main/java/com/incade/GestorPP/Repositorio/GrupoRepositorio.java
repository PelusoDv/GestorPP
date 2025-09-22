
package com.incade.GestorPP.Repositorio;

import com.incade.GestorPP.Entidad.Grupo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepositorio extends JpaRepository<Grupo, Integer>{
    Optional<Grupo> findByNombre(String nombre);
    boolean existByNombre(String nombre);
}
