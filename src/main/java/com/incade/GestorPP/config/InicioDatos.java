
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

    private final CategoriaGastoRepositorio gastoRepo;
    private final CategoriaIngresoRepositorio ingresoRepo;

    @Override
    public void run(String... args) {
        if (gastoRepo.count() == 0) {
            List<String> categoriasGasto = List.of("Comida", "Transporte", "Servicios");
            categoriasGasto.forEach(nombre ->
                gastoRepo.save(new CategoriaGasto(null, nombre))
            );
        }

        if (ingresoRepo.count() == 0) {
            List<String> categoriasIngreso = List.of("Salario", "Premio", "Venta");
            categoriasIngreso.forEach(nombre ->
                ingresoRepo.save(new CategoriaIngreso(null, nombre))
            );
        }
    }
}
