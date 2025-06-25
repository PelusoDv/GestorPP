
package com.incade.GestorPP.Controlador;

import com.incade.GestorPP.Dto.MovimientoDTO;
import com.incade.GestorPP.Entidad.Gasto;
import com.incade.GestorPP.Entidad.Ingreso;
import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Service.PresupuestoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
public class MovimientoControlador {

    private final PresupuestoService service;

    public MovimientoControlador(PresupuestoService service) {
        this.service = service;
    }
  
    @PostMapping("/registrar")
    public ResponseEntity<?> crearMovimiento(@RequestBody @Valid MovimientoDTO dto) {
        try {
            Movimiento mov = service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movimiento registrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
    public ResponseEntity<?> update(@PathVariable("id") int id, @Valid @RequestBody MovimientoDTO dto) {
        try {      
            service.getOne(id).orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));
            Movimiento mov = service.actualizar(dto, id);
            return new ResponseEntity(new String("Ingreso actualizado"), HttpStatus.OK);
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
