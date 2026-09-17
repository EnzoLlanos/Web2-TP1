package com.example.demo.service;

import com.example.demo.client.dummyjson.DummyJsonClient;
import com.example.demo.client.dummyjson.DummyJsonProducto;
import com.example.demo.dto.producto.ProductoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de servicio del catálogo: orquesta al cliente externo y mapea
 * el modelo externo al DTO propio. El controller nunca ve DummyJson*.
 */
@Service
public class ProductoService {

    private final DummyJsonClient client;

    public ProductoService(DummyJsonClient client) {
        this.client = client;
    }

    public List<ProductoDTO> listar(int limit, int skip) {
        return client.listar(limit, skip).products().stream()
                .map(this::mapear)
                .toList();
    }

    public ProductoDTO obtenerPorId(long id) {
        return mapear(client.obtenerPorId(id));
    }

    private ProductoDTO mapear(DummyJsonProducto externo) {
        if (externo == null) {
            return null;
        }
        return new ProductoDTO(
                externo.id(),
                externo.title(),
                externo.description(),
                externo.category(),
                externo.brand(),
                externo.price(),
                externo.rating(),
                externo.stock(),
                externo.thumbnail()
        );
    }
}
