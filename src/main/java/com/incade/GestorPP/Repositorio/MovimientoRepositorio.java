
package com.incade.GestorPP.Repositorio;

import com.incade.GestorPP.Entidad.Movimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Integer> {
    List<Movimiento> findByTipo(String tipo);
}
