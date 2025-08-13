package org.kodigo.swagger_librery.swagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Datos de un autor")
public class AutorDTO {

    @Schema(description = "ID único del autor", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre del autor", example = "Gabriel", required = true)
    private String nombre;

    @NotBlank(message = "Los apellidos del autor son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    @Schema(description = "Apellidos del autor", example = "García Márquez", required = true)
    private String apellidos;

    @Schema(description = "Fecha de nacimiento", example = "1927-03-06")
    private LocalDate fechaNacimiento;

    @Size(max = 50, message = "La nacionalidad no puede exceder 50 caracteres")
    @Schema(description = "Nacionalidad del autor", example = "Colombiana")
    private String nacionalidad;

    @Schema(description = "Cantidad de libros publicados", example = "15")
    private Integer cantidadLibros;

    // Constructores
    public AutorDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public Integer getCantidadLibros() { return cantidadLibros; }
    public void setCantidadLibros(Integer cantidadLibros) { this.cantidadLibros = cantidadLibros; }
}