
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String nombre;
    @Column(nullable = false)
    private LocalDate fecha = LocalDate.now();
    @ManyToOne (optional = false)
    @JoinColumn (name = "usuario_id")
    private Usuario usuario;
    @ManyToOne (optional = false)
    @JoinColumn (name = "suscripcion_id")
    private Suscripcion suscripcion;
}
