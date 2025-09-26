
package com.incade.gestorpp.repositorio;

import com.incade.gestorpp.entidad.Movimiento;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Integer> {
    List<Movimiento> findByCategoria_Tipo(String tipo, Sort sort);
}
