
package com.incade.GestorPP.Service;

import com.incade.GestorPP.Entidad.Grupo;
import com.incade.GestorPP.Entidad.Plan;
import com.incade.GestorPP.Entidad.Suscripcion;
import com.incade.GestorPP.Entidad.Usuario;
import com.incade.GestorPP.Entidad.UsuarioGrupoId;
import com.incade.GestorPP.Repositorio.GrupoRepositorio;
import com.incade.GestorPP.Repositorio.PlanRepositorio;
import com.incade.GestorPP.Repositorio.SuscripcionRepositorio;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SuscripcionService {
    @Autowired
    GrupoRepositorio repoG;
    @Autowired
    SuscripcionRepositorio repoS;
    @Autowired
    PlanRepositorio repoP;
    
    public Suscripcion registrarSus(Usuario user, String plan, LocalDate finale, String grup) {
        Suscripcion sus = new Suscripcion();
        Grupo grupoAsignado;
        List<Plan> planes = repoP.findAll();
        List<Grupo> grupos = repoG.findAll();
        
        // Buscamos el plan seleccionado al cual suscribirse
        Plan planSus = repoP.findByNombre(plan);
        
        // Verificamos si es el gratuito
        if (planes.get(0).getNombre().equals(plan)) {          
            grupoAsignado = grupos.get(0);
            sus.setRol("Miembro");
            
        // Verificamos si es el premium
        } else if (planes.get(1).getNombre().equals(plan)) {
            grupoAsignado = grupos.get(1);
            sus.setRol("Miembro");
            sus.setFin(finale); // Si no es gratuito se pone una fecha limite a la suscripcion
            
        // Y si no es ninguno, creamos un nuevo grupo
        } else {
            Grupo nuevoGrupo = new Grupo();
            nuevoGrupo.setNombre(grup);
            grupoAsignado = repoG.save(nuevoGrupo);
            sus.setRol("Dueño");
            sus.setFin(finale); // Si no es  se pone una fecha limite a la suscripcion
        }
        
        // Now, create and set the composite key
        UsuarioGrupoId id = new UsuarioGrupoId(user.getId(), grupoAsignado.getId());
        sus.setId(id);
        
        // Creamos la relacion
        sus.setPlan(planSus);
        sus.setUsuario(user);
        sus.setGrupo(grupoAsignado);
        
        return repoS.save(sus);
    }  
}