
package com.incade.gestorpp.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovimientoDTO {
    private double monto;
    private String descripcion;
    private LocalDate fecha;
    private String tipo;
    private String categoria;
    private String divisa;
    private String usuario;
    private String grupo;
    private String emisor;
    private String email;
}
