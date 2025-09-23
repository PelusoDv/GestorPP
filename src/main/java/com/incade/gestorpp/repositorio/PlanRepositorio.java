
package com.incade.gestorpp.repositorio;

import com.incade.gestorpp.entidad.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepositorio extends JpaRepository<Plan, Integer>{
    Plan findByNombre(String nombre);
}
