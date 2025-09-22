
package com.incade.GestorPP.Service;

import com.incade.GestorPP.Entidad.Grupo;
import com.incade.GestorPP.Entidad.Plan;
import com.incade.GestorPP.Entidad.Suscripcion;
import com.incade.GestorPP.Entidad.Usuario;
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
        Grupo grupo = new Grupo();
        List<Plan> planes = repoP.findAll();
        List<Grupo> grupos = repoG.findAll();
        
        // Buscamos el plan seleccionado al cual suscribirse
        Plan planSus = repoP.findByNombre(plan);
        
        // Verificamos si es el gratuito
        if (plan == planes.get(0).getNombre()) {         
            sus.setPlan(planSus);   
            sus.setGrupo(grupos.get(0));
            sus.setRol("Miembro");
            
        // Verificamos si es el premium
        } else if (plan == planes.get(1).getNombre()) {
            sus.setPlan(planSus);
            sus.setGrupo(grupos.get(1));
            sus.setRol("Miembro");
            sus.setFin(finale); // Si no es gratuito se pone una fecha limite a la suscripcion
            
        // Y si no es ninguno, creamos un nuevo grupo
        } else {
            grupo.setNombre(grup);
            repoG.save(grupo);
            sus.setPlan(planSus);
            sus.setGrupo(repoG.findByNombre(grup).get());
            sus.setRol("Dueño");
            sus.setFin(finale); // Si no es  se pone una fecha limite a la suscripcion
        }
        
        // Creamos la relacion
        sus.setUsuario(user);
        sus.setGrupo(grupo);
        
        // Añadimos la asociacion
        user.getSuscripcion().add(sus);
        grupo.getSuscripcion().add(sus);
        
        return repoS.save(sus);
    }
    
}
