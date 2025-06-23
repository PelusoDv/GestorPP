
package com.incade.GestorPP.Controlador;

import com.incade.GestorPP.Entidad.Gasto;
import com.incade.GestorPP.Entidad.Ingreso;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = {"http://localhost:3000"})
public class MovimientoControlador {

    private final PresupuestoService service;

    public MovimientoControlador(PresupuestoService service) {
        this.service = service;
    }

    @PostMapping("/guardarI")
    public ResponseEntity<?> crearIngreso(@RequestParam int catId, @Valid @RequestBody Ingreso ingreso) {
        try {
            service.registrarI(ingreso, catId);
            return new ResponseEntity("Ingreso guardado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/guardarG")
    public ResponseEntity<?> crearGasto(@RequestParam int catId, @Valid @RequestBody Gasto gasto) {
        try {
            service.registrarG(gasto, catId);
            return new ResponseEntity ("Gasto guardado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        try {
            service.borrar(id);
            return new ResponseEntity(new String("Movimiento borrado"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestParam int catId, @Valid @RequestBody Movimiento m) {
        try {      
            if (m.getMonto() > 0){
                service.getOne(id).orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));
                Ingreso ing = new Ingreso();
                ing.setId(id);
                ing.setMonto(m.getMonto()); 
                ing.setDescripcion(m.getDescripcion());
                ing.setFecha(m.getFecha());
                service.registrarI(ing, catId);
                return new ResponseEntity(new String("Ingreso actualizado"), HttpStatus.OK);
            } else {
                service.getOne(id).orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
                Gasto gas = new Gasto();
                gas.setId(id);
                gas.setMonto(m.getMonto());
                gas.setDescripcion(m.getDescripcion());
                gas.setFecha(m.getFecha());
                service.registrarG(gas, catId);
                return new ResponseEntity(new String("Gasto actualizado"), HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }   
              
    }

    @GetMapping("/categorias")
    public List<?> categorias(String tipo) {
        return service.listarCategorias(tipo);
    }
    
    @GetMapping("/todos")
    public List<Movimiento> listarTodo() {
        return service.obtenerTodos();
    }
    
    @GetMapping("/ingresos")
    public List<Ingreso> listarIngresos() {
        return service.obtenerIngresos();
    }    
    
    @GetMapping("/gastos")
    public List<Gasto> listarGastos() {
        return service.obtenerGastos();
    }

    @GetMapping("/balance")
    public double balance() {
        return service.calcularBalance();
    }
}
