
package com.incade.GestorPP.Service;

import com.incade.GestorPP.Entidad.Gasto;
import com.incade.GestorPP.Entidad.Ingreso;
import com.incade.GestorPP.Entidad.Movimiento;
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

    public Movimiento registrar(Movimiento m) {
        if (m.getMonto() < 0) {
            Gasto gas = new Gasto(m.getMonto(),m.getCategotia(),m.getDescripcion(),m.getFecha());
            return repoG.save(gas);
        } else if (m.getMonto() > 0) {
            Ingreso ing = new Ingreso(m.getMonto(),m.getCategotia(),m.getDescripcion(),m.getFecha());
            return repoI.save(ing);
        } else {
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
        if (tipo.equalsIgnoreCase("Ingreso")){
            return repoI.findDistinctCategoria();
        } else if (tipo.equalsIgnoreCase("Gasto")){
            return repoG.findDistinctCategoria();
        } else {
            throw new IllegalArgumentException("El tipo " +tipo+ " es incorrecto.");
        }
    }
    
    public boolean existe(int id) {
        return repoI.existsById(id) || repoG.existsById(id);
    }
    
    public Optional<Movimiento> getOne(int id) {
        Optional<? extends Movimiento> ing = repoI.findById(id);
        Optional<? extends Movimiento> gas = repoG.findById(id);
        if (ing.isPresent()){
            return Optional.of(ing.get());
        }
        if (gas.isPresent()) {
            return Optional.of(gas.get());
        }
        throw new IllegalArgumentException("El id " +id+ " no existe.");
    }
    
    public List<Movimiento> obtenerTodos() {
        List<Ingreso> ingresos = repoI.findAll();
        List<Gasto> gastos = repoG.findAll();
        List<Movimiento> movimientos = Stream.concat(ingresos.stream(), gastos.stream())
                                       .collect(Collectors.toList());
        return movimientos;
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
        double totalIngresos = repoI.findAll()
                .stream()
                .mapToDouble(Ingreso::getMonto).sum();
        
        double totalGastos = repoG.findAll()
                .stream()
                .mapToDouble(Gasto::getMonto).sum();
        
        return totalIngresos - totalGastos;
    }
}
