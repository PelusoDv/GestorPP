
package com.incade.gestorpp.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @MapsId("grupoId") // enlaza la parte grupoId de la PK
    @JoinColumn(name = "grupo_id")
    @JsonBackReference
    private Grupo grupo;
    
    @Column(nullable = false)
    private String rol;
    private LocalDate inicio = LocalDate.now();
    private LocalDate fin;
    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id")
    private Plan plan;  
}
