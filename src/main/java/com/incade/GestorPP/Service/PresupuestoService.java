
package com.incade.GestorPP.Service;

import com.incade.GestorPP.Dto.MovimientoDTO;
import com.incade.GestorPP.Entidad.CategoriaGasto;
import com.incade.GestorPP.Entidad.CategoriaIngreso;
import com.incade.GestorPP.Entidad.Gasto;
import com.incade.GestorPP.Entidad.Ingreso;
import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Repositorio.CategoriaGastoRepositorio;
import com.incade.GestorPP.Repositorio.CategoriaIngresoRepositorio;
import com.incade.GestorPP.Repositorio.GastoRepositorio;
import com.incade.GestorPP.Repositorio.IngresoRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PresupuestoService {
    @Autowired
    IngresoRepositorio repoI;
    @Autowired
    GastoRepositorio repoG;
    @Autowired
    CategoriaGastoRepositorio repoCatG;
    @Autowired
    CategoriaIngresoRepositorio repoCatI;
   
    public Movimiento registrar(MovimientoDTO dto) {
        //Si el monto es menor a 0
        if (dto.getMonto() < 0) {
            // Crea un nuevo Gasto
            Gasto g = new Gasto();
            // El usuario elige una categoria de la lista y con el id se busca si existe la misma en el repo
            CategoriaGasto categoria = repoCatG.findById(dto.getCategoriaId())
                // Al ser Optional el findById, si no encuentra tiene que devolver una excepcion
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            // Settea los parametros del Gasto
            g.setMonto(dto.getMonto());
            g.setDescripcion(dto.getDescripcion());
            g.setFecha(dto.getFecha());
            g.setCategoria(categoria);
            // Guarda el Gasto
            return repoG.save(g);
        //Si el monto es mayor a 0
        } else if (dto.getMonto() > 0) {
            // Crea un nuevo Ingreso
            Ingreso i = new Ingreso();
            // El usuario elige una categoria de la lista y con el id se busca si existe la misma en el repo
            CategoriaIngreso categoria = repoCatI.findById(dto.getCategoriaId())
                // Al ser Optional el findById, si no encuentra tiene que devolver una excepcion
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            // Settea los parametros del Ingreso
            i.setMonto(dto.getMonto());
            i.setDescripcion(dto.getDescripcion());
            i.setFecha(dto.getFecha());
            i.setCategoria(categoria);
            // Guarda el Ingreso
            return repoI.save(i);
        } else {
            throw new IllegalArgumentException("El monto no puede ser cero");
        }
    }  
    
    public Movimiento actualizar(MovimientoDTO dto, int id) {
        if (dto.getMonto() > 0) {
            Ingreso i = repoI.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));
            CategoriaIngreso categoria = repoCatI.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría de gasto no encontrada"));
            i.setMonto(dto.getMonto());
            i.setDescripcion(dto.getDescripcion());
            i.setFecha(dto.getFecha());
            i.setCategoria(categoria);
            return repoI.save(i);
        } else if (dto.getMonto() < 0){
            Gasto g = repoG.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
            CategoriaGasto categoria = repoCatG.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría de gasto no encontrada"));
            g.setMonto(dto.getMonto());
            g.setDescripcion(dto.getDescripcion());
            g.setFecha(dto.getFecha());
            g.setCategoria(categoria);
            return repoG.save(g);
        } else {
            throw new IllegalArgumentException("El monto no puede ser cero");
        }   
    }
    
    public void borrar(int id) {
        if (repoI.existsById(id)){
            repoI.deleteById(id);
        } else if (repoG.existsById(id)){
            repoG.deleteById(id); 
        } else {
            throw new IllegalArgumentException("El Movimiento no existe.");
        }
    }
    
    public List<?> listarCategorias(String tipo) {
        // Busca las Categorias por tipo de Movimiento
        if (tipo.equalsIgnoreCase("Ingreso")){
            return repoCatI.findAll();
        } else if (tipo.equalsIgnoreCase("Gasto")){
            return repoCatG.findAll();
        } else { // Si la categoria no es valida, devuelve una excepcion
            throw new IllegalArgumentException("Tipo de Movimiento incorrecto.");
        }
    }
          
    public List<Ingreso> obtenerIngresos() {
        List<Ingreso> ingresos = repoI.findAll();
        return ingresos;
    }
    
    public List<Gasto> obtenerGastos() {
        List<Gasto> gastos = repoG.findAll();
        return gastos;
    }
    
    public List<Movimiento> obtenerTodos() {
        List<Ingreso> ingresos = repoI.findAll(); // Trae todos los Ingresos
        List<Gasto> gastos = repoG.findAll(); // Trae todos los Gastos
        List<Movimiento> movimientos = // Crea una nueva lista de Movimietos
                Stream.concat(ingresos.stream(), gastos.stream()) // Concatena Ingresos y gastos en un stream
                .collect(Collectors.toList()); // Lista el stream dentro del List<Movimiento>
        return movimientos; // Devuelve la lista con todos los movimientos
    }
    
    public double calcularBalance() { 
        double totalIngresos = repoI.findAll() //Trae todos los Ingresos
                .stream() //Pasa la info a un stream
                .mapToDouble(Ingreso::getMonto).sum(); //Mapea los montos en tipo double y los suma 
        double totalGastos = repoG.findAll() //Trae todos los Gastos
                .stream() //Pasa la info a un stream
                .mapToDouble(Gasto::getMonto).sum(); //Mapea los montos en tipo double y los suma
        return totalIngresos + totalGastos; //Como Gastos guarda en nº negativo sumamos ambos
    }
}
