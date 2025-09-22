
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {
    @EmbeddedId
    private UsuarioGrupoId id;
    
    @ManyToOne
    @MapsId("usuarioId") // enlaza la parte usuarioId de la PK
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("grupoId") // enlaza la parte grupoId de la PK
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    
    @Column(nullable = false)
    private String rol;
    private LocalDate inicio = LocalDate.now();
    private LocalDate fin;
    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id")
    private Plan plan;  
}
