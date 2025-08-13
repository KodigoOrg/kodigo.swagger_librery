package org.kodigo.swagger_librery.swagger.repository;

import org.kodigo.swagger_librery.swagger.entity.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByAutorId(Long autorId);

    List<Libro> findByGeneroIgnoreCase(String genero);

    Page<Libro> findByAutorId(Long autorId, Pageable pageable);

    List<Libro> findByGeneroIgnoreCaseAndFechaPublicacionAfter(String genero, LocalDate fecha);

    List<Libro> findByPrecioBetween(Double precioMin, Double precioMax);

    List<Libro> findByNumeroPaginasGreaterThan(Integer numeroPaginas);

    List<Libro> findByAutorIdOrderByFechaPublicacionDesc(Long autorId);

    List<Libro> findByGeneroIgnoreCaseOrderByTituloAsc(String genero);

    boolean existsByIsbn(String isbn);

    long countByAutorId(Long autorId);

    long countByGeneroIgnoreCase(String genero);

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo% OR l.descripcion LIKE %:titulo%")
    List<Libro> buscarPorTituloODescripcion(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE a.nombre LIKE %:nombreAutor% OR a.apellidos LIKE %:nombreAutor%")
    List<Libro> buscarPorNombreAutor(@Param("nombreAutor") String nombreAutor);

    @Query("SELECT l FROM Libro l WHERE l.fechaPublicacion BETWEEN :fechaInicio AND :fechaFin")
    Page<Libro> buscarPorRangoFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                     @Param("fechaFin") LocalDate fechaFin,
                                     Pageable pageable);

    @Query(value = "SELECT AVG(precio) FROM libros WHERE genero = :genero", nativeQuery = true)
    Double obtenerPrecioPromedioPorGenero(@Param("genero") String genero);
}