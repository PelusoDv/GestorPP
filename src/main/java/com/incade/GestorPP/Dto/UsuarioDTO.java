
package com.incade.GestorPP.Dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private String usuarioNombre;
    private String nombreCompleto;
    private String password;
    private String email;
    private String plan;
    private LocalDate fin;
    private String grupo;
}
