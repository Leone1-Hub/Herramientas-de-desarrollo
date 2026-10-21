
document.addEventListener('DOMContentLoaded', function () {
    // Selecciona todos los contadores
    const productos = document.querySelectorAll('.producto');

    productos.forEach(producto => {
        const btnMenos = producto.querySelector('button:first-of-type');
        const btnMas = producto.querySelector('button:last-of-type');
        const inputCantidad = producto.querySelector('input');

        btnMas.addEventListener('click', () => {
            let cantidad = parseInt(inputCantidad.value);
            if (!isNaN(cantidad)) {
                cantidad++;
                inputCantidad.value = cantidad.toString().padStart(2, '0');
            }
        });

        btnMenos.addEventListener('click', () => {
            let cantidad = parseInt(inputCantidad.value);
            if (!isNaN(cantidad) && cantidad > 0) {
                cantidad--;
                inputCantidad.value = cantidad.toString().padStart(2, '0');
            }
        });
    });
});

