// Confirmación antes de eliminar. Va en un archivo externo para poder usar
// una política CSP sin scripts en línea.
document.addEventListener('DOMContentLoaded', function () {
    var formularios = document.querySelectorAll('form[data-confirmar]');
    Array.prototype.forEach.call(formularios, function (form) {
        form.addEventListener('submit', function (evento) {
            if (!window.confirm(form.getAttribute('data-confirmar'))) {
                evento.preventDefault();
            }
        });
    });
});
