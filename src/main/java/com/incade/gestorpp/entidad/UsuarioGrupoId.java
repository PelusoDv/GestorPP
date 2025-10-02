
package com.incade.gestorpp.entidad;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioGrupoId implements Serializable {
    private Integer usuarioId;
    private Integer grupoId;
    
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioGrupoId)) return false;
        UsuarioGrupoId that = (UsuarioGrupoId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
               Objects.equals(grupoId, that.grupoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, grupoId);
    }
}
