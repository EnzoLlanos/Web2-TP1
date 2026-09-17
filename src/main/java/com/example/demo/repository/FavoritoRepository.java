package com.example.demo.repository;

import com.example.demo.model.Favorito;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repository. La implementación es en memoria (sin JPA).
 */
public interface FavoritoRepository {

    Favorito save(Favorito favorito);

    List<Favorito> findAll();

    Optional<Favorito> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
