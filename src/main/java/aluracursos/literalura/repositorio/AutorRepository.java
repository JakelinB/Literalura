package aluracursos.literalura.repositorio;

import aluracursos.literalura.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository <Autor, Long> {
  //  Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeFallecimiento < :anio")
    List<Autor> findAutoresByFechaDeFallecimientoBefore(@Param("anio") String anio);

    Autor findByNombre(String nombre);

}
