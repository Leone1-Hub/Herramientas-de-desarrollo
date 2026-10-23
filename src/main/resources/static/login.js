document.getElementById('formulario-login').addEventListener('submit', async (evento) => {
    evento.preventDefault();

    const username = document.getElementById('campo-usuario-login').value.trim();
    const password = document.getElementById('campo-clave-login').value.trim();

    const mensajeAlerta = document.getElementById('mensaje-estado-login');
    mensajeAlerta.className = 'mensaje-alerta';
    mensajeAlerta.textContent = '';

    const credenciales = {
        username: username,
        password: password
    };

    try {
        const respuesta = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credenciales)
        });

        const resultado = await respuesta.json();

        if (respuesta.ok) {
            mensajeAlerta.className = 'mensaje-alerta mensaje-exito';
            mensajeAlerta.textContent = 'Acceso correcto. Redirigiendo...';

            // Guardar información de la sesión en el navegador
            localStorage.setItem('usuarioSesion', JSON.stringify({
                idUsuario: resultado.idUsuario,
                username: resultado.username,
                nombre: resultado.nombre,
                rol: resultado.rol
            }));

            // Redirección por rol según los módulos acordados
            setTimeout(() => {
                if (resultado.rol === 'ADMINISTRADOR') {
                    window.location.href = 'productos.html';
                } else {
                    window.location.href = 'ventas.html';
                }
            }, 800);
        } else {
            mensajeAlerta.className = 'mensaje-alerta mensaje-error';
            mensajeAlerta.textContent = resultado.error || 'Usuario o contraseña incorrectos.';
        }
    } catch (error) {
        mensajeAlerta.className = 'mensaje-alerta mensaje-error';
        mensajeAlerta.textContent = 'Error de comunicación con el servidor.';
    }
});