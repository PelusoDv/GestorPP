
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Movimiento {   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    protected double monto;
    protected String descripcion;
    protected LocalDate fecha;   
}
