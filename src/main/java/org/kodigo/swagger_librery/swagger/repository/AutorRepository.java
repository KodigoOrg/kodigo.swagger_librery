package org.kodigo.swagger_librery.swagger.repository;

import com.biblioteca.api.entity.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);

    List<Autor> findByNacionalidadIgnoreCase(String nacionalidad);

    List<Autor> findByFechaNacimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Autor> findByNacionalidadIgnoreCaseOrderByApellidosAscNombreAsc(String nacionalidad);

    boolean existsByNombreIgnoreCaseAndApellidosIgnoreCase(String nombre, String apellidos);

    @Query("SELECT a FROM Autor a WHERE SIZE(a.libros) > :cantidadMinima")
    List<Autor> buscarAutoresConMasDeXLibros(@Param("cantidadMinima") int cantidadMinima);

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.id = :id")
    Autor buscarAutorConLibros(@Param("id") Long id);

    @Query("SELECT a, COUNT(l) as cantidadLibros FROM Autor a LEFT JOIN a.libros l GROUP BY a ORDER BY cantidadLibros DESC")
    Page<Object[]> buscarAutoresConCantidadLibros(Pageable pageable);
}