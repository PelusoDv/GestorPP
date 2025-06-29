
package com.incade.GestorPP.Repositorio;

import com.incade.GestorPP.Entidad.Divisa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DivisaRepositorio extends JpaRepository<Divisa, Integer> {
    Optional<Divisa> findByNombre(String nombre);
    @Query("SELECT DISTINCT nombre FROM Divisa")
    List<String> findDistinctNombre();
}
