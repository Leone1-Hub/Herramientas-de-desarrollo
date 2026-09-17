document.getElementById('boton-registrar')?.addEventListener('click', async () => {
    const usuario = document.getElementById('campo-nuevo-usuario').value.trim();
    const contrasena = document.getElementById('campo-nueva-clave').value;
    const mensajeEstado = document.getElementById('mensaje-estado-registro');

    mensajeEstado.textContent = '';
    mensajeEstado.className = 'mensaje-alerta';

    if (!usuario || !contrasena) {
        mensajeEstado.textContent = 'Por favor completa todos los campos';
        mensajeEstado.classList.add('mensaje-error');
        return;
    }

    try {
        const respuesta = await fetch('/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                username: usuario, 
                password: contrasena 
            })
        });

        const resultado = await respuesta.json();

        if (respuesta.ok) {
            mensajeEstado.textContent = 'Registro exitoso. Redirigiendo...';
            mensajeEstado.classList.add('mensaje-exito');
            
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1200);
        } else {
            mensajeEstado.textContent = resultado.error || 'No se pudo completar el registro';
            mensajeEstado.classList.add('mensaje-error');
        }
    } catch (error) {
        mensajeEstado.textContent = 'Error al conectar con el servidor';
        mensajeEstado.classList.add('mensaje-error');
    }
});