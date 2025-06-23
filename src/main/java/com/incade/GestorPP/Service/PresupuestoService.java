
package com.incade.GestorPP.Service;

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
import java.util.Optional;
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

    public Movimiento registrar(Movimiento m, int categoriaId) {
        // Primero verificamos si el monto es menor a 0 (para gastos)
        if (m.getMonto() < 0) {
            // Crea un nuevo Gasto
            Gasto gas = new Gasto();
            // El usuario elige una categoria de la lista y con el id se busca si existe la misma en el repo
            CategoriaGasto categoria = repoCatG.findById(categoriaId)
                    // Al ser Optional, si no encuentra devuelve una excepcion
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            // Settea los parametros del Gasto
            gas.setMonto(m.getMonto());
            gas.setDescripcion(m.getDescripcion());
            gas.setFecha(m.getFecha());
            gas.setCategoria(categoria);
            return repoG.save(gas); // Guarda el Gasto
        // O mayor a 0 (para ingresos)
        } else if (m.getMonto() > 0) {
            Ingreso ing = new Ingreso();
            // El usuario elige una categoria de la lista y con el id se busca si existe la misma en el repo
            CategoriaIngreso categoria = repoCatI.findById(categoriaId)
                    // Al ser Optional, si no encuentra devuelve una excepcion
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            ing.setMonto(m.getMonto());
            ing.setDescripcion(m.getDescripcion());
            ing.setFecha(m.getFecha());
            ing.setCategoria(categoria);
            return repoI.save(ing); // Guarda el Ingreso
        } else {
            // Si se intenta guardar un Movimiento con monto 0, devuelve una excepcion
            throw new IllegalArgumentException("El valor " +m.getMonto()+ " no es un monto valido.");
        }
    }
    
    public void borrar(int id) {
        if (repoI.existsById(id)){
            repoI.deleteById(id);
        } else if (repoG.existsById(id)){
            repoG.deleteById(id); 
        } else {
            throw new IllegalArgumentException("El id " +id+ " no existe.");
        }
    }
    
    public List<?> listarCategorias(String tipo) {
        // Busca las Categorias por tipo de Movimiento
        if (tipo.equalsIgnoreCase("Ingreso")){
            return repoCatI.findAll();
        } else if (tipo.equalsIgnoreCase("Gasto")){
            return repoCatG.findAll();
        } else { // Si la categoria no es valida, devuelve una excepcion
            throw new IllegalArgumentException("El tipo " +tipo+ " es incorrecto.");
        }
    }
    
    public boolean existe(int id) {
        // Busca si el id existe en como Ingreso o como Gasto
        return repoI.existsById(id) || repoG.existsById(id);
    }
       
    public Optional<Movimiento> getOne(int id) {
        // Como Ingreso y Gasto heredan de Movimiento, 
        // Buscamos por id y traemos como objeto Movimiento de cada repositorio
        Optional<? extends Movimiento> ing = repoI.findById(id); 
        Optional<? extends Movimiento> gas = repoG.findById(id); 
      
        if (ing.isPresent()){ // Si esta presente en el repo Ingreso
            return Optional.of(ing.get()); // Devuelve ing como Optional<Movimiento> nuevamente
        } else if (gas.isPresent()) { // Si esta presente en el repo Gasto
            return Optional.of(gas.get()); // dDvuelve gas como Optional<Movimiento> nuevamente
        } else { // Si no existe devuelve una Exception
            throw new NullPointerException("El id " +id+ " no existe.");    
        }
    }
    
    public List<Movimiento> obtenerTodos() {
        List<Ingreso> ingresos = repoI.findAll(); // Trae todos los Ingresos
        List<Gasto> gastos = repoG.findAll(); // Trae todos los Gastos
        List<Movimiento> movimientos = // Crea una nueva lista de Movimietos
                Stream.concat(ingresos.stream(), gastos.stream()) // Concatena Ingresos y gastos en un stream
                .collect(Collectors.toList()); // Lista el stream dentro del List<Movimiento>
        return movimientos; // Devuelve la lista con todos los movimientos
    }
    
    public List<Ingreso> obtenerIngresos() {
        List<Ingreso> ingresos = repoI.findAll();
        return ingresos;
    }
    
    public List<Gasto> obtenerGastos() {
        List<Gasto> gastos = repoG.findAll();
        return gastos;
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
