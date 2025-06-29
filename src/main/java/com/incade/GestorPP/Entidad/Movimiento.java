
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    protected double monto;
    protected String descripcion;
    protected LocalDate fecha;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    protected Categoria categoria;
    @ManyToOne(optional = false)
    @JoinColumn(name = "divisa_id")
    protected Divisa divisa;
}
