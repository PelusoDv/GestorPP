
package com.incade.gestorpp.repositorio;

import com.incade.gestorpp.entidad.Grupo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepositorio extends JpaRepository<Grupo, Integer>{
    Optional<Grupo> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
