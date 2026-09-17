package com.example.demo.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contrato propio de la API. No expone el JSON de DummyJSON tal cual:
 * se eligen y renombran los campos que le interesan a nuestro cliente
 * (title -&gt; nombre, thumbnail -&gt; imagen, etc.).
 */
@Schema(description = "Producto del catálogo (proyección propia sobre DummyJSON)")
public record ProductoDTO(
        @Schema(example = "1") Long id,
        @Schema(example = "Essence Mascara Lash Princess") String nombre,
        @Schema(example = "Máscara de pestañas voluminizadora") String descripcion,
        @Schema(example = "beauty") String categoria,
        @Schema(example = "Essence") String marca,
        @Schema(example = "9.99") double precio,
        @Schema(example = "4.7") double rating,
        @Schema(example = "99") int stock,
        @Schema(example = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp") String imagen
) {
}
