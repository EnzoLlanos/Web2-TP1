package com.example.demo.client.dummyjson;

import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.ServicioExternoException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Único punto de contacto con DummyJSON. Traduce errores HTTP/red a
 * excepciones de dominio que entiende {@code GlobalExceptionHandler}.
 */
@Component
public class DummyJsonClient {

    private final RestClient restClient;

    public DummyJsonClient(RestClient dummyJsonRestClient) {
        this.restClient = dummyJsonRestClient;
    }

    public DummyJsonProductosResponse listar(int limit, int skip) {
        try {
            return restClient.get()
                    .uri("/products?limit={limit}&skip={skip}", limit, skip)
                    .retrieve()
                    .body(DummyJsonProductosResponse.class);
        } catch (RestClientException ex) {
            throw new ServicioExternoException("No se pudo obtener el catálogo de DummyJSON", ex);
        }
    }

    public DummyJsonProducto obtenerPorId(long id) {
        try {
            return restClient.get()
                    .uri("/products/{id}", id)
                    .retrieve()
                    .body(DummyJsonProducto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNoEncontradoException("Producto no encontrado: " + id);
        } catch (RestClientException ex) {
            throw new ServicioExternoException("No se pudo obtener el producto " + id + " de DummyJSON", ex);
        }
    }
}
