
package com.incade.GestorPP.Entidad;
import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Ingreso extends Movimiento {
    
    public Ingreso(double monto, String categoria, String descripcion, LocalDate fecha) {
        this.monto = monto;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // private String categoria; // Ej: "Salario", "Premio", "Venta"
}