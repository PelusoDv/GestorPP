
package com.incade.GestorPP.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.incade.GestorPP.Entidad.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByCategoria(String categoira);
    List<Categoria> findByTipo(String tipo);
    @Query("SELECT DISTINCT tipo FROM Categoria")
    List<String> findDistinctTipos();
    public boolean existsByTipo(String tipo);
    @Query("SELECT categoria FROM Categoria WHERE tipo IN :tipo")
    List<String> findCategoriasByTipo(@Param("tipo") String tipo);
}
