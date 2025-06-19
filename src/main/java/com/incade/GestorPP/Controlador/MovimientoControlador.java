
package com.incade.GestorPP.Controlador;

import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Service.PresupuestoService;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
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
    public ResponseEntity<Movimiento> crearMovimiento(@Valid @RequestBody Movimiento m) {
        service.registrar(m);
        return new ResponseEntity(new String("Movimiento guardado"), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Movimiento> delete(@PathVariable("id") int id){
        if(!service.existe(id))
            return new ResponseEntity(new String("El ID no existe"), HttpStatus.BAD_REQUEST);
        
        service.borrar(id);
        return new ResponseEntity(new String("Movimiento borrado"), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id,@Valid @RequestBody Movimiento m) {
        if(!service.existe(id))
            return new ResponseEntity(new String("El ID no existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(m.getTipo()))
            return new ResponseEntity(new String("Seleccione un tipo"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(m.getCategoria()))
            return new ResponseEntity(new String("Seleccione una categoría"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(String.valueOf(m.getMonto())))
            return new ResponseEntity(new String("El monto no debe estar vacio"), HttpStatus.BAD_REQUEST);
        
        Movimiento mov = service.getOne(id).get();
        mov = m;
        service.registrar(mov);
        return new ResponseEntity(new String("Movimiento actualizado"), HttpStatus.OK);
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
