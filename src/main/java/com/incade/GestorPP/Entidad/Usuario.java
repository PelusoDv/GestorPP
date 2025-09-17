
package com.incade.GestorPP.Entidad;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(name = "usuario_nombre", unique = true, nullable = false)
    private String usuarioNombre;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)    
    private LocalDate fecha = LocalDate.now();
}
