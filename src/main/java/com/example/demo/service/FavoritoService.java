package com.example.demo.service;

import com.example.demo.dto.favorito.FavoritoMapper;
import com.example.demo.dto.favorito.FavoritoRequestDTO;
import com.example.demo.dto.favorito.FavoritoResponseDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.Favorito;
import com.example.demo.repository.FavoritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica del recurso propio Favoritos (CRUD sobre repository en memoria).
 */
@Service
public class FavoritoService {

    private final FavoritoRepository repository;

    public FavoritoService(FavoritoRepository repository) {
        this.repository = repository;
    }

    public FavoritoResponseDTO crear(FavoritoRequestDTO dto) {
        Favorito guardado = repository.save(FavoritoMapper.toEntity(dto));
        return FavoritoMapper.toResponse(guardado);
    }

    public List<FavoritoResponseDTO> listar() {
        return repository.findAll().stream()
                .map(FavoritoMapper::toResponse)
                .toList();
    }

    public FavoritoResponseDTO obtenerPorId(Long id) {
        return FavoritoMapper.toResponse(buscar(id));
    }

    public FavoritoResponseDTO actualizar(Long id, FavoritoRequestDTO dto) {
        Favorito existente = buscar(id);
        existente.setProductoId(dto.productoId());
        existente.setNota(dto.nota());
        return FavoritoMapper.toResponse(repository.save(existente));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Favorito no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private Favorito buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Favorito no encontrado: " + id));
    }
}
