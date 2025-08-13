package org.kodigo.swagger_librery.swagger.service;

import org.kodigo.swagger_librery.swagger.dto.LibroDTO;
import org.kodigo.swagger_librery.swagger.entity.Autor;
import org.kodigo.swagger_librery.swagger.entity.Libro;
import org.kodigo.swagger_librery.swagger.repository.AutorRepository;
import org.kodigo.swagger_librery.swagger.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public List<LibroDTO> obtenerTodos() {
        return libroRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Page<LibroDTO> obtenerTodosPaginado(Pageable pageable) {
        Page<Libro> librosPage = libroRepository.findAll(pageable);
        List<LibroDTO> librosDTO = librosPage.getContent().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new PageImpl<>(librosDTO, pageable, librosPage.getTotalElements());
    }

    public LibroDTO obtenerPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        return convertirADTO(libro);
    }

    public LibroDTO obtenerPorIsbn(String isbn) {
        Libro libro = libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));
        return convertirADTO(libro);
    }

    public LibroDTO crear(LibroDTO libroDTO) {
        if (libroRepository.existsByIsbn(libroDTO.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con el ISBN: " + libroDTO.getIsbn());
        }

        Autor autor = autorRepository.findById(libroDTO.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + libroDTO.getAutorId()));

        Libro libro = convertirAEntidad(libroDTO);
        libro.setAutor(autor);

        Libro libroGuardado = libroRepository.save(libro);
        return convertirADTO(libroGuardado);
    }

    public LibroDTO actualizar(Long id, LibroDTO libroDTO) {
        Libro libroExistente = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!libroExistente.getIsbn().equals(libroDTO.getIsbn()) &&
                libroRepository.existsByIsbn(libroDTO.getIsbn())) {
            throw new RuntimeException("Ya existe otro libro con el ISBN: " + libroDTO.getIsbn());
        }

        Autor autor = autorRepository.findById(libroDTO.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + libroDTO.getAutorId()));

        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setIsbn(libroDTO.getIsbn());
        libroExistente.setFechaPublicacion(libroDTO.getFechaPublicacion());
        libroExistente.setNumeroPaginas(libroDTO.getNumeroPaginas());
        libroExistente.setGenero(libroDTO.getGenero());
        libroExistente.setDescripcion(libroDTO.getDescripcion());
        libroExistente.setPrecio(libroDTO.getPrecio());
        libroExistente.setAutor(autor);

        Libro libroActualizado = libroRepository.save(libroExistente);
        return convertirADTO(libroActualizado);
    }

    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }

    public List<LibroDTO> buscarPorTituloODescripcion(String texto) {
        return libroRepository.buscarPorTituloODescripcion(texto).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Page<LibroDTO> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable) {
        Page<Libro> librosPage = libroRepository.buscarPorRangoFechas(fechaInicio, fechaFin, pageable);
        List<LibroDTO> librosDTO = librosPage.getContent().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new PageImpl<>(librosDTO, pageable, librosPage.getTotalElements());
    }

    public Double obtenerPrecioPromedioPorGenero(String genero) {
        return libroRepository.obtenerPrecioPromedioPorGenero(genero);
    }

    private LibroDTO convertirADTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setIsbn(libro.getIsbn());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        dto.setNumeroPaginas(libro.getNumeroPaginas());
        dto.setGenero(libro.getGenero());
        dto.setDescripcion(libro.getDescripcion());
        dto.setPrecio(libro.getPrecio());
        dto.setAutorId(libro.getAutor().getId());
        dto.setNombreAutor(libro.getAutor().getNombre() + " " + libro.getAutor().getApellidos());
        return dto;
    }

    private Libro convertirAEntidad(LibroDTO dto) {
        Libro libro = new Libro();
        libro.setId(dto.getId());
        libro.setTitulo(dto.getTitulo());
        libro.setIsbn(dto.getIsbn());
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setGenero(dto.getGenero());
        libro.setDescripcion(dto.getDescripcion());
        libro.setPrecio(dto.getPrecio());
        return libro;
    }
}