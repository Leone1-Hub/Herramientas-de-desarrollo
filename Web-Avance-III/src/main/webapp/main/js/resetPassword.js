document.addEventListener("DOMContentLoaded", function () {
    const currentAlumno = JSON.parse(localStorage.getItem("currentAlumno"));

    if (!currentAlumno) {
        alert("No hay una sesión activa. Por favor, inicie sesión.");
        window.location.href = "Login.html"; 
        return;
    }


    document
        .getElementById("form-cambiarPassword")
        .addEventListener("submit", function (event) {
            event.preventDefault();

            const nuevaPassword = document
                .getElementById("new-password")
                .value.trim();
            const confirmarPassword = document
                .getElementById("confirm-password")
                .value.trim();


            if (!nuevaPassword || !confirmarPassword) {
                alert("Por favor, complete ambos campos.");
                return;
            }


            if (nuevaPassword !== confirmarPassword) {
                alert("Las contraseñas no coinciden. Por favor, inténtelo de nuevo.");
                return;
            }


            const alumnos = JSON.parse(localStorage.getItem("alumnos")) || [];
            const alumnoIndex = alumnos.findIndex(
                (a) => a.nDocumento === currentAlumno.nDocumento
            );

            if (alumnoIndex === -1) {
                alert("No se encontró al alumno en el registro.");
                return;
            }

       
            alumnos[alumnoIndex].password = nuevaPassword;


            localStorage.setItem("alumnos", JSON.stringify(alumnos));
            localStorage.setItem("currentAlumno", JSON.stringify(alumnos[alumnoIndex]));

            alert("Contraseña actualizada con éxito.");
            window.location.href = "index.html"; 
        });
});


function cancelarResetPassword() {
    if (confirm("¿Está seguro de que desea cancelar el cambio de contraseña?")) {
        window.location.href = "index.html";
    }
}
