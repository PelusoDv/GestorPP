
package com.incade.GestorPP.config;

import com.incade.GestorPP.Entidad.Categoria;
import com.incade.GestorPP.Entidad.Divisa;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import com.incade.GestorPP.Repositorio.CategoriaRepositorio;
import com.incade.GestorPP.Repositorio.DivisaRepositorio;

@Component
@RequiredArgsConstructor
public class InicioDatos implements CommandLineRunner {
    private final CategoriaRepositorio repoCate;
    private final DivisaRepositorio repoDiv;

    @Override
    public void run(String... args) {
        if (repoCate.count() == 0) {
            List<String> categoriasGasto = List.of("Otros Gastos","Ahorros","Alimentación",
                                                   "Alquiler","Almacen","Cuidado Personal",
                                                   "Deudas","Ocio","Educacion",
                                                   "Inversiones","Salud","Servicio",
                                                   "Vestimenta", "Vivienda");
            categoriasGasto.forEach(categoria ->
                repoCate.save(new Categoria(null, "Gasto", categoria))
            );
            List<String> categoriasIngreso = List.of("Otros Ingresos", "Inversiones", "Extras",
                                                     "Prestamos", "Reembolsos","Regalos/Donaciones",
                                                     "Salarios","Subsidios/Becas", "Ventas");
            categoriasIngreso.forEach(categoria ->
                repoCate.save(new Categoria(null,"Ingreso", categoria))
            );
        }
        
        if (repoDiv.count() == 0) {
            List<String> divisas = List.of("ARS", "USD", "BRL", "PYG");
            divisas.forEach(divisa ->
                repoDiv.save(new Divisa(null, divisa))
            );
        }
    }
}
