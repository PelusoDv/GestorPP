
package com.incade.GestorPP.Service;

import com.incade.GestorPP.Dto.MovimientoDTO;
import com.incade.GestorPP.Entidad.Categoria;
import com.incade.GestorPP.Entidad.Divisa;
import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Repositorio.CategoriaRepositorio;
import com.incade.GestorPP.Repositorio.DivisaRepositorio;
import com.incade.GestorPP.Repositorio.MovimientoRepositorio;
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
   
    public Movimiento registrar(MovimientoDTO dto) {

        if (dto.getMonto() != 0 ) {          
            Movimiento mov = new Movimiento();
            
            //Buscamos si existe el tipo especificado
            if (repoC.existsByTipo(dto.getTipo())){
            //Si existe, buscamos la categoria
                Categoria categoria = repoC.findByCategoria(dto.getCategoria())
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
            //Si existe, buscamos la categoria
                Categoria categoria = repoC.findByCategoria(dto.getCategoria())
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
            
            // Guarda el movimiento
            return repoM.save(mov);
        } else {
            throw new IllegalArgumentException("El monto no puede ser cero");
        }   
    }
    
    public void borrar(int id) {
        if (repoM.existsById(id)){
        } else {
            throw new IllegalArgumentException("El Movimiento no existe.");
        }
    }
    
    public List<String> listarTipo() {
       List<String> tipos = repoC.findDistinctTipos();
       return tipos;
   }
    
    public List<String> listarCategorias(String tipo) {
        List<String> categorias = repoC.findCategoriasByTipo(tipo);
        return categorias;
    }
    
    public List<String> listarDivisa() {
        List<String> divisas = repoD.findDistinctNombre();
        return divisas;
    }
      
    public List<Movimiento> obtenerTodos() {
        List<Movimiento> movimientos = repoM.findAll();
        return movimientos;
    }
    
    public List<Movimiento> obtenerGastos() {
        List<String> tipos = repoC.findDistinctTipos();
        List<Movimiento> gastos = repoM.findByCategoria_Tipo(tipos.get(0));
        return gastos;
    }
    
    public List<Movimiento> obtenerIngresos() {  
        List<String> tipos = repoC.findDistinctTipos();
        List<Movimiento> ingresos = repoM.findByCategoria_Tipo(tipos.get(1));
        return ingresos;
    }
    
    public double calcularBalance() { 

        double totalGastos = obtenerGastos() //Trae todos los Gastos
                .stream() //Pasa la info a un stream
                .mapToDouble(Movimiento::getMonto).sum(); //Mapea los montos en tipo double y los suma
        double totalIngresos = obtenerIngresos() //Trae todos los Ingresos
                .stream() //Pasa la info a un stream
                .mapToDouble(Movimiento::getMonto).sum(); //Mapea los montos en tipo double y los suma 
        return totalIngresos - totalGastos;
    }
}
