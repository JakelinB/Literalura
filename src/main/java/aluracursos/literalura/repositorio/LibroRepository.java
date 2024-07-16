package aluracursos.literalura.repositorio;

import aluracursos.literalura.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository <Libros, Long> {
//    Optional<Autor> findByNombre(String nombre);
    @Query("SELECT new aluracursos.literalura.model.LibrosResponse(l.id, l.titulo, l.idiomas, l.numeroDeDescargas, a.id, a.nombre) " +
            "FROM Libros l " +
            "JOIN l.autor a")
    List<LibrosResponse> findAllLibrosAndAutorNombre();

    @Query("SELECT new aluracursos.literalura.model.AutorResponse(l.id, l.titulo, a.id, a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento) " +
            "FROM Libros l " +
            "JOIN l.autor a")
    List<AutorResponse> findAllAutorAndLibrosNombre();

    @Query("SELECT new aluracursos.literalura.model.LibrosResponse(l.id, l.titulo, l.idiomas, l.numeroDeDescargas, a.id, a.nombre) " +
            "FROM Libros l JOIN l.autor a " +
            "WHERE l.idiomas = :idiomas")
    List<LibrosResponse> findLibrosAndAutoresByIdioma(Idiomas idiomas);

}
