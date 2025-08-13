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
public class autorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<AutorDTO> obtenerTodos() {
        return autorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private AutorDTO convertirADTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setApellidos(autor.getApellidos());
        dto.setFechaNacimiento(autor.getFechaNacimiento());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setCantidadLibros(autor.getLibros().size());
        return dto;
    }
}
