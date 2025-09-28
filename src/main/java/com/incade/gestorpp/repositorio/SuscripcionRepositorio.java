
package com.incade.gestorpp.repositorio;

import com.incade.gestorpp.entidad.Suscripcion;
import com.incade.gestorpp.entidad.UsuarioGrupoId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepositorio extends JpaRepository<Suscripcion, UsuarioGrupoId>{
    List<Suscripcion> findByUsuario_Id(Integer id);
}
