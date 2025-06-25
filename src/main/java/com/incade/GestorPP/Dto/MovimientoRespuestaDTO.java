
package com.incade.GestorPP.Dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovimientoRespuestaDTO {
    private Long id;
    private double monto;
    private String descripcion;
    private LocalDate fecha;
    private String tipo; // "Ingreso" o "Gasto"
    private String categoria;
}
