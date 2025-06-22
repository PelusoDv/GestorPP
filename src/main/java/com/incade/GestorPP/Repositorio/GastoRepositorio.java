
package com.incade.GestorPP.Repositorio;

import com.incade.GestorPP.Entidad.Gasto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepositorio extends JpaRepository<Gasto, Integer> {
List<String> findDistinctCategoria();
}