
package com.incade.GestorPP.Dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovimientoDTO {
    private double monto;
    private String descripcion;
    private LocalDate fecha;
    private int categoriaId;
}
