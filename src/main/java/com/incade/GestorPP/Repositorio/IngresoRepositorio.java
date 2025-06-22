
package com.incade.GestorPP.Repositorio;

import com.incade.GestorPP.Entidad.Ingreso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngresoRepositorio extends JpaRepository<Ingreso, Integer> {
List<String> findDistinctCategoria();
}
    

