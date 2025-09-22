
package com.incade.GestorPP.config;

import com.incade.GestorPP.Entidad.Categoria;
import com.incade.GestorPP.Entidad.Divisa;
import com.incade.GestorPP.Entidad.Grupo;
import com.incade.GestorPP.Entidad.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import com.incade.GestorPP.Repositorio.CategoriaRepositorio;
import com.incade.GestorPP.Repositorio.DivisaRepositorio;
import com.incade.GestorPP.Repositorio.GrupoRepositorio;
import com.incade.GestorPP.Repositorio.PlanRepositorio;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InicioDatos implements CommandLineRunner {
    private final CategoriaRepositorio repoCate;
    private final DivisaRepositorio repoDiv;
    private final PlanRepositorio repoPla;
    private final GrupoRepositorio repoGru;

    @Override
    public void run(String... args) {
        if (repoCate.count() == 0) {
            List<String> categoriasGasto = Arrays.asList("Otros Gastos","Ahorros","Alimentación",
                                                   "Alquiler","Almacen","Cuidado Personal",
                                                   "Deudas","Ocio","Educacion",
                                                   "Inversiones","Salud","Servicio",
                                                   "Vestimenta", "Vivienda");
            categoriasGasto.forEach(categoria ->
                repoCate.save(new Categoria(null, "Gasto", categoria))
            );
            List<String> categoriasIngreso = Arrays.asList("Otros Ingresos", "Inversiones", "Extras",
                                                     "Prestamos", "Reembolsos","Regalos/Donaciones",
                                                     "Salarios","Subsidios/Becas", "Ventas");
            categoriasIngreso.forEach(categoria ->
                repoCate.save(new Categoria(null,"Ingreso", categoria))
            );
        }
        
        if (repoDiv.count() == 0) {
            Map<String, String> divisas = new HashMap<>();
            divisas.put("Pesos","ARS");
            divisas.put("Dólares","USD"); 
            divisas.put("Reales","BRL"); 
            divisas.put("Guaraníes","PYG");
            divisas.forEach((nombre, codigo) -> 
                repoDiv.save(new Divisa(null, nombre, codigo))
            );
        }
        
        if (repoGru.count() == 0) {
            List<String> grupos = Arrays.asList( "Global", "Premiums");            
            grupos.forEach(nombre -> { 
                Grupo grupo = new Grupo();
                grupo.setNombre(nombre); 
                repoGru.save(grupo);
            });
        }
        
        if (repoPla.count() == 0) {
            List<Plan> planes = Arrays.asList(
                new Plan(null, "Free", 1, 0.00),
                new Plan(null, "Premium", 1, 4000.00),
                new Plan(null, "Doble", 2, 6000.00),
                new Plan(null, "Familiar", 5, 8000.00),
                new Plan(null, "Ejecutivo", 20, 12000.00)
            );
        repoPla.saveAll(planes);
        }
    }
}
