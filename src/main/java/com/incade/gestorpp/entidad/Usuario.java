
package com.incade.gestorpp.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private String nombreCompleto;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)    
    private LocalDate fecha = LocalDate.now();
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Suscripcion> Suscripcion = new HashSet<>();
}
