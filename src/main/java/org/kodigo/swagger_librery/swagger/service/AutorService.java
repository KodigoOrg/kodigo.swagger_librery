package org.kodigo.swagger_librery.swagger.service;

import jakarta.transaction.Transactional;
import org.kodigo.swagger_librery.swagger.dto.AutorDTO;
import org.kodigo.swagger_librery.swagger.entity.Autor;
import org.kodigo.swagger_librery.swagger.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<AutorDTO> obtenerTodos() {
        return autorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AutorDTO obtenerPorId(Long id){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author no encontrado con ID:" +  id));
        return convertirADTO(autor);
    }

    public AutorDTO crear(AutorDTO autorDTO){
        // La validación de unicidad se debe manejar con una restricción UNIQUE en la entidad.
        Autor autor = convertirAEntidad(autorDTO);
        Autor saveAuthor = autorRepository.save(autor);
        return convertirADTO(saveAuthor);
    }

    public AutorDTO actualizar(Long id, AutorDTO autorDTO){
        Autor authorExistente = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author no encontrado con Id:" + id));

        // Asigna directamente los valores del DTO a la entidad existente.
        // La validación de unicidad se maneja con la restricción de la base de datos.
        authorExistente.setNombre(autorDTO.getNombre());
        authorExistente.setApellidos(autorDTO.getApellidos());
        authorExistente.setFechaNacimiento(autorDTO.getFechaNacimiento());
        authorExistente.setNacionalidad(autorDTO.getNacionalidad());

        Autor updateAuthor = autorRepository.save(authorExistente);
        return convertirADTO(updateAuthor);
    }

    public void eliminar(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor no encontrado con ID: " + id);
        }
        autorRepository.deleteById(id);
    }

    private Autor convertirAEntidad(AutorDTO dto) {
        Autor autor = new Autor();
        if (dto.getId() != null && dto.getId() > 0) {
            autor.setId(dto.getId());
        }
        autor.setNombre(dto.getNombre());
        autor.setApellidos(dto.getApellidos());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        // CORRECCIÓN: Agregar el mapeo para nacionalidad
        autor.setNacionalidad(dto.getNacionalidad());
        return autor;
    }

    private AutorDTO convertirADTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setApellidos(autor.getApellidos());
        dto.setFechaNacimiento(autor.getFechaNacimiento());
        dto.setNacionalidad(autor.getNacionalidad());
        // CORRECCIÓN: Se podría añadir una verificación si la lista es nula o vacía
        dto.setCantidadLibros(autor.getLibros() != null ? autor.getLibros().size() : 0);
        return dto;
    }
}