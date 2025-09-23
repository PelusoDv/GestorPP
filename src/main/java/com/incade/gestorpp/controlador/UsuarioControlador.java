
package com.incade.gestorpp.controlador;

import com.incade.gestorpp.dto.UsuarioDTO;
import com.incade.gestorpp.service.UsuarioService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    private final UsuarioService service;
    
    public UsuarioControlador(UsuarioService service) {
        this.service = service;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<?> crearUser(@RequestBody @Valid UsuarioDTO dto) {
        try {
            service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/borrar")
    public ResponseEntity<?> borrarUser(@RequestParam String usuario, @RequestParam String password){
        try {
            service.borrar(usuario, password);
            return new ResponseEntity(new String("Usuario eliminado"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } 
    }
}
