
package com.incade.GestorPP.config;

import com.incade.GestorPP.Entidad.CategoriaGasto;
import com.incade.GestorPP.Entidad.CategoriaIngreso;
import com.incade.GestorPP.Repositorio.CategoriaGastoRepositorio;
import com.incade.GestorPP.Repositorio.CategoriaIngresoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InicioDatos implements CommandLineRunner {
    private final CategoriaGastoRepositorio repoCatG;
    private final CategoriaIngresoRepositorio repoCatI;

    @Override
    public void run(String... args) {
        if (repoCatG.count() == 0) {
            List<String> categoriasGasto = List.of("Comida", "Transporte", "Servicio", "Otro");
            categoriasGasto.forEach(nombre ->
                repoCatG.save(new CategoriaGasto(null, nombre))
            );
        }

        if (repoCatI.count() == 0) {
            List<String> categoriasIngreso = List.of("Salario", "Premio", "Venta", "Otro");
            categoriasIngreso.forEach(nombre ->
                repoCatI.save(new CategoriaIngreso(null, nombre))
            );
        }
    }
}
