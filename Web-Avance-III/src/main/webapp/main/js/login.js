document.addEventListener("DOMContentLoaded", function () {
    const formLogin = document.getElementById("form-login");
    if (formLogin) {
        formLogin.addEventListener("submit", loginAlumno);
    } else {
        console.error("El formulario con ID 'form-login' no se encuentra en el DOM.");
    }

    const recoverform = document.getElementById("recoverform");
    if (recoverform) {
        recoverform.addEventListener("submit", enviarCorreo);
    } else {
        console.error("El formulario con ID 'recoverform' no se encuentra en el DOM.");
    }
});

function loginAlumno(event) {
    event.preventDefault();

    let nDocumento = document.getElementById("nDocumento").value.trim();
    let password = document.getElementById("password").value.trim();


    let alumnos = JSON.parse(localStorage.getItem("alumnos")) || [];


    let alumno = alumnos.find(a => a.nDocumento === nDocumento && a.password === password);

    if (!alumno) {
        alert("Número de documento o contraseña incorrectos. Por favor, intente de nuevo.");
        return;
    }


    localStorage.setItem("currentAlumno", JSON.stringify(alumno));

    alert("Inicio de sesión exitoso.");

    window.location.href = "index.html";
}

function enviarCorreo(event) {
    event.preventDefault();

    let email = document.getElementById("email").value.trim();


    if (!email) {
        alert("Por favor ingrese su correo electrónico.");
        return;
    }


    let alumnos = JSON.parse(localStorage.getItem("alumnos")) || [];


    let alumno = alumnos.find(a => a.email === email);

    if (!alumno) {
        alert("No se encontró un alumno con ese correo electrónico.");
        return;
    }

    const nuevaContraseña = generarContraseña(alumno);


    alumno.password = nuevaContraseña;

    localStorage.setItem("alumnos", JSON.stringify(alumnos));


    const mensaje = `Se ha generado una nueva contraseña para tu cuenta: ${nuevaContraseña}.`;
    alert(mensaje); 


    window.location.href = 'login.html';
}


function generarContraseña(alumno) {
    return alumno.nDocumento;  
}