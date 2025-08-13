package org.kodigo.swagger_librery.swagger.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AutorDTO {


    private Long id;

    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos del autor son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    private String apellidos;

    private LocalDate fechaNacimiento;

    @Size(max = 50, message = "La nacionalidad no puede exceder 50 caracteres")
    private String nacionalidad;

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