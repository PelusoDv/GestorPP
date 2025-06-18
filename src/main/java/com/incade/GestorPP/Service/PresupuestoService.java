
package com.incade.GestorPP.Service;

import com.incade.GestorPP.Entidad.Movimiento;
import com.incade.GestorPP.Repositorio.MovimientoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PresupuestoService {

    private final MovimientoRepositorio repo;

    public PresupuestoService(MovimientoRepositorio repo) {
        this.repo = repo;
    }

    public Movimiento registrar(Movimiento m) {
        return repo.save(m);
    }
    
    public void borrar(int id) {
        repo.deleteById(id);
    }
    
    public boolean existe(int id) {
        return repo.existsById(id);
    }
    
    public Optional<Movimiento> getOne(int id) {
        return repo.findById(id);
    }
    
    public List<Movimiento> obtenerTodos() {
        return repo.findAll();
    }

    public double calcularBalance() {
        double ingresos = repo.findByTipo("Ingreso").stream().mapToDouble(Movimiento::getMonto).sum();
        double gastos = repo.findByTipo("Gasto").stream().mapToDouble(Movimiento::getMonto).sum();
        return ingresos - gastos;
    }
}
