package org.kodigo.swagger_librery.swagger.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.kodigo.swagger_librery.swagger.dto.ErrorResponse;
import org.kodigo.swagger_librery.swagger.dto.LibroDTO;
import org.kodigo.swagger_librery.swagger.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "API para gestión de libros")
@Validated
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Operation(summary = "Obtener todos los libros", description = "Retorna una lista paginada de todos los libros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros obtenida", content = @Content(schema = @Schema(implementation = Page.class))),
    })
    @GetMapping
    public ResponseEntity<Page<LibroDTO>> getAllLibros(
            @Parameter(description = "Número de página (0..N)", example = "0") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Tamaño de la página", example = "10") @RequestParam(defaultValue = "10") @Min(1) int size,
            @Parameter(description = "Campo por el que ordenar (ej: 'titulo', 'autor.nombre')", example = "titulo") @RequestParam(defaultValue = "titulo") String sortBy,
            @Parameter(description = "Dirección de ordenación (ASC o DESC)", example = "ASC") @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection) {

        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        Page<LibroDTO> librosPage = libroService.obtenerTodosPaginado(pageable);
        return ResponseEntity.ok(librosPage);
    }

    @Operation(summary = "Obtener un libro por ID", description = "Retorna un libro individual por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado", content = @Content(schema = @Schema(implementation = LibroDTO.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibroById(
            @Parameter(description = "ID del libro a buscar", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo libro", description = "Crea un nuevo libro y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente", content = @Content(schema = @Schema(implementation = LibroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<LibroDTO> createLibro(@Valid @RequestBody LibroDTO libroDTO) {
        return new ResponseEntity<>(libroService.crear(libroDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un libro por ID", description = "Actualiza los datos de un libro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado", content = @Content(schema = @Schema(implementation = LibroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> updateLibro(
            @Parameter(description = "ID del libro a actualizar", required = true) @PathVariable Long id,
            @Valid @RequestBody LibroDTO libroDTO) {
        return ResponseEntity.ok(libroService.actualizar(id, libroDTO));
    }

    @Operation(summary = "Eliminar un libro por ID", description = "Elimina un libro de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(
            @Parameter(description = "ID del libro a eliminar", required = true) @PathVariable Long id) {
        libroService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar libros por título o descripción", description = "Busca libros cuyo título o descripción contenga el texto dado (insensible a mayúsculas)")
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarPorTituloODescripcion(
            @Parameter(description = "Texto a buscar en el título o descripción del libro", required = true) @RequestParam String texto) {
        return ResponseEntity.ok(libroService.buscarPorTituloODescripcion(texto));
    }

    @Operation(summary = "Buscar libros por rango de fechas de publicación", description = "Retorna una lista paginada de libros publicados entre dos fechas")
    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<Page<LibroDTO>> buscarPorRangoFechas(
            @Parameter(description = "Fecha de inicio del rango (formato YYYY-MM-DD)", required = true, example = "2000-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin del rango (formato YYYY-MM-DD)", required = true, example = "2010-12-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @Parameter(description = "Número de página (0..N)", example = "0") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Tamaño de la página", example = "10") @RequestParam(defaultValue = "10") @Min(1) int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(libroService.buscarPorRangoFechas(fechaInicio, fechaFin, pageable));
    }

    @Operation(summary = "Obtener precio promedio de libros por género", description = "Calcula el precio promedio de todos los libros de un género específico")
    @GetMapping("/precio-promedio-por-genero")
    public ResponseEntity<Double> obtenerPrecioPromedioPorGenero(
            @Parameter(description = "Género a buscar", required = true) @RequestParam String genero) {
        return ResponseEntity.ok(libroService.obtenerPrecioPromedioPorGenero(genero));
    }
}