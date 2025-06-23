
package com.incade.GestorPP.Entidad;

import javax.persistence.Entity;
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
public class Ingreso extends Movimiento {
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_ingreso_id")
    private CategoriaIngreso categoria;
}