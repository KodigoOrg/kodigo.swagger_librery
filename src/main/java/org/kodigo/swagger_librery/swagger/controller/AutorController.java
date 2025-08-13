package org.kodigo.swagger_librery.swagger.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kodigo.swagger_librery.swagger.dto.AutorDTO;
import org.kodigo.swagger_librery.swagger.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "API para gestión de autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Operation(summary = "Obtener todos los autores", description = "Retorna una lista de todos los autores")
    @GetMapping
    public ResponseEntity<List<AutorDTO>> getAllAutores() {
        return ResponseEntity.ok(autorService.obtenerTodos());
    }

    @Operation(summary = "Obtener un autor por ID", description = "Retorna un autor individual por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado", content = @Content(schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> getAutorById(
            @Parameter(description = "ID del autor a buscar", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(autorService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo autor", description = "Crea un nuevo autor y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor creado exitosamente", content = @Content(schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación)", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AutorDTO> createAutor(@Valid @RequestBody AutorDTO autorDTO) {
        return new ResponseEntity<>(autorService.crear(autorDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un autor por ID", description = "Actualiza los datos de un autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor actualizado", content = @Content(schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> updateAutor(
            @Parameter(description = "ID del autor a actualizar", required = true) @PathVariable Long id,
            @Valid @RequestBody AutorDTO autorDTO) {
        return ResponseEntity.ok(autorService.actualizar(id, autorDTO));
    }

    @Operation(summary = "Eliminar un autor por ID", description = "Elimina un autor de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(
            @Parameter(description = "ID del autor a eliminar", required = true) @PathVariable Long id) {
        autorService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

