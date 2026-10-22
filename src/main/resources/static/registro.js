document.getElementById('formulario-registro').addEventListener('submit', async (evento) => {
    evento.preventDefault();

    const nombre = document.getElementById('campo-nombre').value.trim();
    const username = document.getElementById('campo-usuario').value.trim();
    const email = document.getElementById('campo-correo').value.trim();
    const telefono = document.getElementById('campo-telefono').value.trim();
    const password = document.getElementById('campo-clave').value.trim();
    const rol = document.getElementById('campo-rol').value;

    const mensajeAlerta = document.getElementById('mensaje-estado-registro');
    mensajeAlerta.className = 'mensaje-alerta';
    mensajeAlerta.textContent = '';

    const nuevoUsuario = {
        nombre: nombre,
        username: username,
        email: email,
        telefono: telefono,
        password: password,
        rol: rol
    };

    try {
        const respuesta = await fetch('/api/auth/registro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(nuevoUsuario)
        });

        const resultado = await respuesta.json();

        if (respuesta.ok) {
            mensajeAlerta.className = 'mensaje-alerta mensaje-exito';
            mensajeAlerta.textContent = '¡Usuario creado con éxito! Redirigiendo al login...';
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1200);
        } else {
            mensajeAlerta.className = 'mensaje-alerta mensaje-error';
            mensajeAlerta.textContent = resultado.error || 'No se pudo completar el registro.';
        }
    } catch (error) {
        mensajeAlerta.className = 'mensaje-alerta mensaje-error';
        mensajeAlerta.textContent = 'Error de comunicación con el servidor.';
    }
});