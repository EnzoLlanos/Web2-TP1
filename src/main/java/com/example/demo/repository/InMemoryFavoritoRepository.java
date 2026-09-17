package com.example.demo.repository;

import com.example.demo.model.Favorito;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación en memoria con una colección concurrente.
 * No hay persistencia real: al reiniciar se pierde todo (a propósito del TP1).
 */
@Repository
public class InMemoryFavoritoRepository implements FavoritoRepository {

    private final Map<Long, Favorito> store = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    @Override
    public Favorito save(Favorito favorito) {
        if (favorito.getId() == null) {
            favorito.setId(secuencia.getAndIncrement());
            favorito.setFechaAgregado(LocalDateTime.now());
        }
        store.put(favorito.getId(), favorito);
        return favorito;
    }

    @Override
    public List<Favorito> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Favorito> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}
