
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor
@AllArgsConstructor

public class Movimiento {   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String tipo; // "Ingreso" o "Gasto"
    @NotBlank
    private String categoria;
    @Positive
    private double monto;
    private String descripcion;
    private LocalDate fecha;
}
