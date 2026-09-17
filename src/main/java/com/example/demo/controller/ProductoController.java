package com.example.demo.controller;

import com.example.demo.dto.producto.ProductoDTO;
import com.example.demo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Catálogo de solo lectura (consumo de DummyJSON)")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Devuelve el catálogo mapeado a nuestro DTO. Reutiliza la paginación limit/skip de DummyJSON.")
    public List<ProductoDTO> listar(
            @Parameter(description = "Cantidad máxima a devolver", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Cantidad a saltear", example = "0")
            @RequestParam(defaultValue = "0") int skip) {
        return service.listar(limit, skip);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por id", description = "Busca el producto en DummyJSON y lo devuelve con nuestro contrato. 404 si no existe.")
    public ProductoDTO obtenerPorId(
            @Parameter(description = "Id del producto en DummyJSON", example = "1")
            @PathVariable long id) {
        return service.obtenerPorId(id);
    }
}
