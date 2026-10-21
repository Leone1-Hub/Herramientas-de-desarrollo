document.getElementById('boton-ingresar')?.addEventListener('click', async () => {
    const usuario = document.getElementById('campo-usuario').value.trim();
    const contrasena = document.getElementById('campo-clave').value;
    const mensajeEstado = document.getElementById('mensaje-estado');

    mensajeEstado.textContent = '';
    mensajeEstado.className = 'mensaje-alerta';

    if (!usuario || !contrasena) {
        mensajeEstado.textContent = 'Por favor completa todos los campos';
        mensajeEstado.classList.add('mensaje-error');
        return;
    }

    try {
        const respuesta = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                username: usuario, 
                password: contrasena 
            })
        });

        const resultado = await respuesta.json();

        if (respuesta.ok) {
            mensajeEstado.textContent = resultado.message || 'Ingreso exitoso';
            mensajeEstado.classList.add('mensaje-exito');
            
            localStorage.setItem('usuario_activo', usuario);
            
            setTimeout(() => {
                window.location.href = 'Pagina1.html';
            }, 1000);
        } else {
            mensajeEstado.textContent = resultado.error || 'Credenciales incorrectas';
            mensajeEstado.classList.add('mensaje-error');
        }
    } catch (error) {
        mensajeEstado.textContent = 'Error al conectar con el servidor';
        mensajeEstado.classList.add('mensaje-error');
    }
});