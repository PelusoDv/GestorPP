
package com.incade.gestorpp.service;

import com.incade.gestorpp.dto.MovimientoDTO;
import com.incade.gestorpp.entidad.Categoria;
import com.incade.gestorpp.entidad.Divisa;
import com.incade.gestorpp.entidad.Movimiento;
import com.incade.gestorpp.repositorio.CategoriaRepositorio;
import com.incade.gestorpp.repositorio.DivisaRepositorio;
import com.incade.gestorpp.repositorio.GrupoRepositorio;
import com.incade.gestorpp.repositorio.MovimientoRepositorio;
import com.incade.gestorpp.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PresupuestoService {
    @Autowired
    MovimientoRepositorio repoM;
    @Autowired
    CategoriaRepositorio repoC;
    @Autowired
    DivisaRepositorio repoD;
    @Autowired
    UsuarioRepositorio repoU;
    @Autowired
    GrupoRepositorio repoG;
   
    public Movimiento registrar(MovimientoDTO dto) {

        // Verificamos que el monto sea distinto a 0
        if (dto.getMonto() != 0 ) {          
            Movimiento mov = new Movimiento();
            
            //Buscamos si existe el tipo especificado
            if (repoC.existsByTipo(dto.getTipo())){
                //Si existe, buscamos la categorias asociada al tipo
                Categoria categoria = repoC.findByTipoAndCategoria(dto.getTipo(),dto.getCategoria())
                    //Si no encuentra la categoria lanza una excepcion 
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                //Si todo sale bien, setea el tipo y categoria
                mov.setCategoria(categoria);  
            // Y si no encuentra el tipo lanza una excepcion
            } else {throw new RuntimeException("Tipo no encontrado");}
            
            // Buscamos el tipo de divisa
            Divisa divisa = repoD.findByNombre(dto.getDivisa()) 
                //Si no la encuentra lanza una excepcion
                .orElseThrow(() -> new RuntimeException("Divisa no encontrada"));
            mov.setDivisa(divisa);
            
            // Settea los demas parametros   
            mov.setMonto(dto.getMonto());
            mov.setDescripcion(dto.getDescripcion());
            mov.setFecha(dto.getFecha());
            mov.setUsuario(repoU.findByUsuarioNombre(dto.getUsuario()).get());
            mov.setGrupo(repoG.findByNombre(dto.getGrupo()).get());
            mov.setEmisor(dto.getEmisor());
            mov.setEmail(dto.getEmail());
            
            // Guarda el movimiento
            return repoM.save(mov);
            
        } else {
            throw new IllegalArgumentException("El monto no puede ser cero");
        }
    }  
    
    public Movimiento actualizar(MovimientoDTO dto, int id) {
        
        if (dto.getMonto() != 0) {
            Movimiento mov = repoM.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));
            
            //Buscamos si existe el tipo especificado
            if (repoC.existsByTipo(dto.getTipo())){
            //Si existe, buscamos la categoria asociada al tipo
                Categoria categoria = repoC.findByTipoAndCategoria(dto.getTipo(),dto.getCategoria())
                    //Si no encuentra la categoria lanza una excepcion
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada")); 
                //Si todo sale bien, setea el tipo y categoria
                mov.setCategoria(categoria);
            // Y si no encuentra el tipo lanza una excepcion
            } else {throw new RuntimeException("Tipo no encontrado");}
            
            // Buscamos el tipo de divisa
            Divisa divisa = repoD.findByNombre(dto.getDivisa()) 
                //Si no la encuentra lanza una excepcion
                .orElseThrow(() -> new RuntimeException("Divisa no encontrada"));
            mov.setDivisa(divisa);
            
            // Settea los demas parametros   
            mov.setMonto(dto.getMonto());
            mov.setDescripcion(dto.getDescripcion());
            mov.setFecha(dto.getFecha());
            mov.setUsuario(repoU.findByUsuarioNombre(dto.getUsuario()).get());
            mov.setGrupo(repoG.findByNombre(dto.getGrupo()).get());
            mov.setEmisor(dto.getEmisor());
            mov.setEmail(dto.getEmail());
            
            // Guarda el movimiento
            return repoM.save(mov);
        } else {
            throw new IllegalArgumentException("El monto no puede ser cero");
        }   
    }
    
    public void borrar(int id) {
        if (repoM.existsById(id)){
            repoM.deleteById(id);         
        } else {
            throw new IllegalArgumentException("El Movimiento no existe.");
        }
    }
    
    public List<String> listarTipos() {
       List<String> tipos = repoC.findDistinctTipos();
       return tipos;
   }
    
    public List<String> listarCategorias(String tipo) {
        List<String> categorias = repoC.findCategoriasByTipo(tipo);
        return categorias;
    }
    
    public List<String> listarDivisas() {
        List<String> divisas = repoD.findDistinctNombre();
        return divisas;
    }
     
    private MovimientoDTO convertirDTO(Movimiento movimiento) {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setMonto(movimiento.getMonto());
        dto.setDescripcion(movimiento.getDescripcion());
        dto.setFecha(movimiento.getFecha());
        dto.setTipo(movimiento.getCategoria().getTipo());
        dto.setCategoria(movimiento.getCategoria().getCategoria());
        dto.setDivisa(movimiento.getDivisa().getNombre());
        dto.setUsuario(movimiento.getUsuario().getUsuarioNombre());
        dto.setGrupo(movimiento.getGrupo().getNombre());
        dto.setEmisor(movimiento.getEmisor());
        dto.setEmail(movimiento.getEmail());
        return dto;
    }
    
    public List<MovimientoDTO> obtenerTodos() {
        List<Movimiento> movimientos = repoM.findAll();
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        movimientos.forEach( movimiento ->
                movimientosDTO.add(convertirDTO(movimiento))
        );
        return movimientosDTO;
    }
    
    public List<MovimientoDTO> obtenerGastos() {
        List<String> tipos = repoC.findDistinctTipos();
        List<Movimiento> gastos = repoM.findByCategoria_Tipo(tipos.get(0));
        List<MovimientoDTO> gastosDTO = new ArrayList<>();
        gastos.forEach( movimiento ->
                gastosDTO.add(convertirDTO(movimiento))
        );
        return gastosDTO;
    }
    
    public List<MovimientoDTO> obtenerIngresos() {  
        List<String> tipos = repoC.findDistinctTipos();
        List<Movimiento> ingresos = repoM.findByCategoria_Tipo(tipos.get(1));
        List<MovimientoDTO> ingresosDTO = new ArrayList<>();
        ingresos.forEach( movimiento ->
                ingresosDTO.add(convertirDTO(movimiento))
        );
        return ingresosDTO;
    }
    
    public double calcularBalance() { 

        double totalGastos = obtenerGastos() //Trae todos los Gastos
                .stream() //Pasa la info a un stream
                .mapToDouble(MovimientoDTO::getMonto).sum(); //Mapea los montos en tipo double y los suma
        double totalIngresos = obtenerIngresos() //Trae todos los Ingresos
                .stream() //Pasa la info a un stream
                .mapToDouble(MovimientoDTO::getMonto).sum(); //Mapea los montos en tipo double y los suma 
        return totalIngresos - totalGastos;
    }
}
