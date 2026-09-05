const API_URL = "/api/categorias";

const formulario = document.getElementById("formCategoria");
const tabla = document.getElementById("tablaCategorias");


// LISTAR CATEGORÍAS
function listarCategorias() {

    fetch(API_URL)
        .then(response => response.json())
        .then(categorias => {

            tabla.innerHTML = "";

            categorias.forEach(categoria => {

                const fila = document.createElement("tr");

                fila.innerHTML = `
                    <td>${categoria.id}</td>
                    <td>${categoria.nombre}</td>
                    <td>${categoria.descripcion || ""}</td>
                    <td>
                        <button onclick="editarCategoria(${categoria.id})">
                            Editar
                        </button>

                        <button onclick="eliminarCategoria(${categoria.id})">
                            Eliminar
                        </button>
                    </td>
                `;

                tabla.appendChild(fila);
            });

        })
        .catch(error => {
            console.error("Error al listar categorías:", error);
        });
}


// CREAR CATEGORÍA
formulario.addEventListener("submit", function(event) {

    event.preventDefault();

    const categoria = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value
    };

    fetch(API_URL, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(categoria)

    })
    .then(response => response.json())
    .then(() => {

        alert("Categoría creada correctamente");

        formulario.reset();

        listarCategorias();

    })
    .catch(error => {
        console.error("Error al crear categoría:", error);
    });

});


// ELIMINAR CATEGORÍA
function eliminarCategoria(id) {

    if (!confirm("¿Deseas eliminar esta categoría?")) {
        return;
    }

    fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    })
    .then(response => {

        if (response.ok) {

            alert("Categoría eliminada correctamente");

            listarCategorias();

        } else {

            alert("No se pudo eliminar la categoría");
        }

    })
    .catch(error => {
        console.error("Error al eliminar:", error);
    });
}


// EDITAR CATEGORÍA
function editarCategoria(id) {

    const nuevoNombre =
        prompt("Ingrese el nuevo nombre de la categoría:");

    if (!nuevoNombre) {
        return;
    }

    const nuevaDescripcion =
        prompt("Ingrese la nueva descripción:");

    const categoria = {
        nombre: nuevoNombre,
        descripcion: nuevaDescripcion
    };

    fetch(`${API_URL}/${id}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(categoria)

    })
    .then(response => response.json())
    .then(() => {

        alert("Categoría actualizada correctamente");

        listarCategorias();

    })
    .catch(error => {
        console.error("Error al actualizar:", error);
    });
}


// CARGAR CATEGORÍAS AL INICIAR
listarCategorias();