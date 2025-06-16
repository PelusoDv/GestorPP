
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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

    //Constructores
    public Movimiento(){
    }
    
    public Movimiento(int id, String tipo, String categoria, double monto, String descripcion, LocalDate fecha) {
        this.id = id;
        this.tipo = tipo;
        this.categoria = categoria;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }
    
    
}
