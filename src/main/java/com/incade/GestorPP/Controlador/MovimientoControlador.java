
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

    @PostMapping
    public ResponseEntity<?> crearMovimiento(@RequestParam int id, @Valid @RequestBody Movimiento m) {
        try {
            service.registrar(m,id);
            return new ResponseEntity(new String("Movimiento guardado"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
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
            Movimiento mov = service.getOne(id).orElseThrow(() -> new RuntimeException("No encontrado"));;
            mov.setMonto(m.getMonto());
            mov.setDescripcion(m.getDescripcion());
            mov.setFecha(m.getFecha());
            service.registrar(mov, catId);
            return new ResponseEntity(new String("Movimiento actualizado"), HttpStatus.OK);
        } catch (Exception e) {
            if(!service.existe(id))
                return new ResponseEntity(new String("El id " +id+ " no existe."), HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
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
