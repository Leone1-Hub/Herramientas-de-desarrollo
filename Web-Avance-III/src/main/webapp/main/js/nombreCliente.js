document.addEventListener("DOMContentLoaded", function () {
    const currentAlumno = JSON.parse(localStorage.getItem("currentAlumno"));

    if (currentAlumno && currentAlumno.nombre && currentAlumno.aPaterno) {
        const nombreElemento = document.getElementById("nombreAlumno");
        if (nombreElemento) {
            nombreElemento.textContent = `${currentAlumno.nombre} ${currentAlumno.aPaterno}`; 
        }
    } else {
        console.error("No se encontró un alumno autenticado con los datos requeridos en el localStorage.");
    }
});

