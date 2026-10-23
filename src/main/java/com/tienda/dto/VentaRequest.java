package com.tienda.dto;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record VentaRequest(

        
        @Positive(message = "debe ser un id válido")
        Long clienteId,

        @NotEmpty(message = "la venta debe tener al menos un producto")
        @Size(max = 100, message = "no puede tener más de 100 productos")
        List<@Valid Item> items
) {

    public record Item(
            @NotNull(message = "es obligatorio")
            @Positive(message = "debe ser un id válido")
            Long productoId,

            @NotNull(message = "es obligatoria")
            @Positive(message = "debe ser mayor a 0")
            @Max(value = 1000, message = "no puede superar 1000 unidades")
            Integer cantidad
    ) {
    }
}
