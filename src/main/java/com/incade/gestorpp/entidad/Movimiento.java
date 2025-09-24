
package com.incade.gestorpp.entidad;

import java.time.LocalDate;
import javax.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double monto;
    private String descripcion;
    private LocalDate fecha;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    @ManyToOne(optional = false)
    @JoinColumn(name = "divisa_id")
    private Divisa divisa;
    @ManyToOne (optional = false)
    @JoinColumn (name = "usuario_id")
    private Usuario usuario;
    @ManyToOne (optional = false)
    @JoinColumn (name = "grupo_id")
    private Grupo grupo;
    @Column(name = "sender_name")
    private String emisor;
    @Column(name = "sender_email")
    private String email;
}
