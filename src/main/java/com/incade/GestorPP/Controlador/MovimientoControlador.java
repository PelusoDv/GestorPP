
package com.incade.GestorPP.Controlador;

import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Service.PresupuestoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin (origins = {"http://localhost:3306", "http://localhost:3000"})
public class MovimientoControlador {

    private final PresupuestoService service;

    public MovimientoControlador(PresupuestoService service) {
        this.service = service;
    }

    @PostMapping
    public Movimiento crearMovimiento(@Valid @RequestBody Movimiento movimiento) {
        return service.registrar(movimiento);
    }

    @GetMapping
    public List<Movimiento> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/balance")
    public double balance() {
        return service.calcularBalance();
    }
}
