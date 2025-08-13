package org.kodigo.swagger_librery.swagger.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;



public class LibroDTO {


    private Long id;

    @NotBlank(message = "El título del libro es obligatorio")
    @Size(min = 1, max = 200, message = "El título debe tener entre 1 y 200 caracteres")
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "El formato del ISBN no es válido")
    private String isbn;

    private LocalDate fechaPublicacion;

    @Min(value = 1, message = "El número de páginas debe ser mayor a 0")
    @Max(value = 10000, message = "El número de páginas no puede exceder 10000")
    private Integer numeroPaginas;

    @Size(max = 50, message = "El género no puede exceder 50 caracteres")
    private String genero;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "999.99", message = "El precio no puede exceder 999.99")
    private BigDecimal precio;

    @NotNull(message = "El ID del autor es obligatorio")
    private Long autorId;

    private String nombreAutor;

    // Constructores
    public LibroDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public Integer getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(Integer numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }
}