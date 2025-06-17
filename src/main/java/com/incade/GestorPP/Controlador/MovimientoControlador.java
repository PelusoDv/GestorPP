
package com.incade.GestorPP.Controlador;

import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Service.PresupuestoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Movimiento> crearMovimiento(@Valid @RequestBody Movimiento movimiento) {
        service.registrar(movimiento);
        return new ResponseEntity(new String("Movimiento guardado"), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Movimiento> delete(@PathVariable("id") int id){
        if(!service.existe(id))
            return new ResponseEntity(new String("El ID no existe"), HttpStatus.BAD_REQUEST);
        
        service.borrar(id);
        return new ResponseEntity(new String("Movimiento borrado"), HttpStatus.OK);
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
