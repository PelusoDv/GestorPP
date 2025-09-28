
package com.incade.gestorpp.controlador;

import com.incade.gestorpp.dto.MovimientoDTO;
import com.incade.gestorpp.service.PresupuestoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
public class MovimientoControlador {

    private final PresupuestoService service;

    public MovimientoControlador(PresupuestoService service) {
        this.service = service;
    }
  
    @PostMapping("/registrar")
    public ResponseEntity<?> crearMovi(@RequestBody @Valid MovimientoDTO dto) {
        try {
            service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movimiento registrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarMovi(@Valid @RequestBody MovimientoDTO dto) {
        try {      
            service.actualizar(dto);
            return new ResponseEntity(new String("Movimiento actualizado"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }          
    }    
    
    @DeleteMapping("/borrar")
    public ResponseEntity<?> borrarMovi(@Valid @RequestBody MovimientoDTO dto){
        try {
            service.borrar(dto.getId());
            return new ResponseEntity(new String("Movimiento borrado"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } 
    }

    @GetMapping("/tipos")
    public List<String> tipos() {
        return service.listarTipos();
    }
    
    @GetMapping("/categorias")
    public List<String> categorias(@RequestParam String tipo) {
        return service.listarCategorias(tipo);
    }
    
    @GetMapping("/divisas")
    public List<String> divisas() {
        return service.listarDivisas();
    }
    
    @GetMapping("/todos/{usuario}")
    public List<MovimientoDTO> listarTodo(@PathVariable String usuario, @RequestParam(defaultValue = "") String orderby) {
        return service.obtenerTodos(usuario, orderby);
    }
    
    @GetMapping("/gastos/{usuario}")
    public List<MovimientoDTO> listarGastos(@PathVariable String usuario, @RequestParam(defaultValue = "") String orderby) {
        return service.obtenerGastos(usuario, orderby);
    }
    
    @GetMapping("/ingresos/{usuario}")
    public List<MovimientoDTO> listarIngresos(@PathVariable String usuario, @RequestParam(defaultValue = "") String orderby) {
        return service.obtenerIngresos(usuario, orderby);
    }    

    @GetMapping("/balance/{usuario}")
    public double balance(@PathVariable String usuario) {
        return service.calcularBalance(usuario, "");
    }
}
