document.addEventListener("DOMContentLoaded", function () {
    let currentCliente = JSON.parse(localStorage.getItem("currentCliente"));

    // Verificar si currentCliente existe
    if (!currentCliente) {
     
    }

    llenarFormulario(currentCliente);

    document.getElementById("form-editarCliente").addEventListener("submit", function (event) {
        event.preventDefault();

        let updatedData = obtenerDatosFormulario();

        if (!datosHanCambiado(currentCliente, updatedData)) {
            alert("No se realizó ningún cambio.");
            window.location.href = 'index.jsp'; // Redirigir a la página principal
            return;
        }

        let clientes = JSON.parse(localStorage.getItem("clientes")) || [];
        let clienteIndex = clientes.findIndex(c => c.nDocumento === currentCliente.nDocumento);

        if (clienteIndex === -1) {
            alert("No se encontró al cliente en el registro.");
            return;
        }

        // Actualizar los datos del cliente
        clientes[clienteIndex] = { ...clientes[clienteIndex], ...updatedData };

        // Guardar los cambios en localStorage
        localStorage.setItem("clientes", JSON.stringify(clientes));
        localStorage.setItem("currentCliente", JSON.stringify(clientes[clienteIndex]));

        alert("Datos actualizados con éxito.");
        window.location.href = 'index.jsp'; // Redirigir a la página principal
    });
});

function llenarFormulario(cliente) {
    document.getElementById("nombre").value = cliente.nombre;
    document.getElementById("apaterno").value = cliente.apaterno; // Cambiado a 'apaterno'
    document.getElementById("amaterno").value = cliente.amaterno; // Cambiado a 'amaterno'
    document.getElementById("fechaNacimiento").value = cliente.fechaNacimiento;
    document.getElementById("tipoDocumento").value = cliente.tipoDocumento;
    document.getElementById("nDocumento").value = cliente.nDocumento;
    document.getElementById("nacionalidad").value = cliente.nacionalidad;
    document.getElementById("sexo").value = cliente.sexo;
    document.getElementById("email").value = cliente.email;
    document.getElementById("telefono").value = cliente.telefono;
    document.getElementById("departamento").value = cliente.departamento;
    document.getElementById("provincia").value = cliente.provincia;
    document.getElementById("distrito").value = cliente.distrito;
    document.getElementById("direccion").value = cliente.direccion;
}

function obtenerDatosFormulario() {
    return {
        email: document.getElementById("email").value.trim(),
        telefono: document.getElementById("telefono").value.trim(),
        departamento: document.getElementById("departamento").value.trim(),
        provincia: document.getElementById("provincia").value.trim(),
        distrito: document.getElementById("distrito").value.trim(),
        direccion: document.getElementById("direccion").value.trim()
    };
}

function datosHanCambiado(cliente, updatedData) {
    return Object.keys(updatedData).some(key => cliente[key] !== updatedData[key]);
}

function cancelarEditCliente() {
    window.location.href = 'index.jsp'; // Redirigir a la página principal
}
