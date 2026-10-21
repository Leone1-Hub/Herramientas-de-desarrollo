package clases;

/**
 * Se lanza cuando una operación incumple una regla de negocio (por ejemplo,
 * nombre duplicado o categoría en uso). El mensaje es seguro para mostrarlo
 * al usuario porque lo redactamos nosotros, nunca proviene de la base de datos.
 */
public class ReglaNegocioException extends Exception {

    private static final long serialVersionUID = 1L;

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
