package clases;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Utilidades de seguridad: autorización por rol, token CSRF, validación y
 * normalización de entradas y cabeceras HTTP de protección.
 */
public final class SeguridadUtil {

    public static final String ATTR_USUARIO = "usuario";
    public static final String ROL_ADMIN = "Administrador";
    public static final String PARAM_CSRF = "csrfToken";

    public static final int NOMBRE_MIN = 2;
    public static final int NOMBRE_MAX = 50;
    public static final int DESCRIPCION_MAX = 150;

    private static final String ATTR_CSRF = "csrfToken";
    private static final SecureRandom RANDOM = new SecureRandom();

    // Lista blanca: letras (con tildes), números, espacio y unos pocos signos.
    private static final Pattern PATRON_NOMBRE
            = Pattern.compile("[\\p{L}\\p{N}][\\p{L}\\p{N} .,&()\\-]*");
    private static final Pattern PATRON_DESCRIPCION
            = Pattern.compile("[\\p{L}\\p{N} .,;:()\\u00BF?\\u00A1!&%\\-]*");
    private static final Pattern ESPACIOS = Pattern.compile("\\s+");
    private static final Pattern SOLO_DIGITOS = Pattern.compile("[0-9]{1,9}");

    private SeguridadUtil() {
    }

    // ==================== AUTORIZACIÓN ====================
    public static boolean esAdministrador(HttpSession sesion) {
        if (sesion == null) {
            return false;
        }
        Object u = sesion.getAttribute(ATTR_USUARIO);
        return (u instanceof Usuario) && ROL_ADMIN.equals(((Usuario) u).getRol());
    }

    // ==================== CSRF ====================
    public static String obtenerTokenCsrf(HttpSession sesion) {
        Object existente = sesion.getAttribute(ATTR_CSRF);
        if (existente instanceof String) {
            return (String) existente;
        }
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        sesion.setAttribute(ATTR_CSRF, token);
        return token;
    }

    public static boolean tokenCsrfValido(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return false;
        }
        Object esperado = sesion.getAttribute(ATTR_CSRF);
        String recibido = request.getParameter(PARAM_CSRF);
        if (!(esperado instanceof String) || recibido == null) {
            return false;
        }
        // Comparación en tiempo constante
        return MessageDigest.isEqual(
                ((String) esperado).getBytes(StandardCharsets.UTF_8),
                recibido.getBytes(StandardCharsets.UTF_8));
    }

    // ==================== ENTRADAS ====================
    /** Normaliza a NFC, recorta y colapsa cualquier espacio/salto de línea en un solo espacio. */
    public static String normalizar(String valor) {
        if (valor == null) {
            return "";
        }
        String s = Normalizer.normalize(valor, Normalizer.Form.NFC);
        return ESPACIOS.matcher(s).replaceAll(" ").trim();
    }

    /** Devuelve un id positivo o null si el texto no es un entero válido. */
    public static Integer parsearId(String texto) {
        if (texto == null || !SOLO_DIGITOS.matcher(texto).matches()) {
            return null;
        }
        int id = Integer.parseInt(texto);
        return id > 0 ? id : null;
    }

    /** Devuelve el mensaje de error o null si el nombre es válido. */
    public static String validarNombreCategoria(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return "El nombre de la categoría es obligatorio.";
        }
        if (nombre.length() < NOMBRE_MIN) {
            return "El nombre debe tener al menos " + NOMBRE_MIN + " caracteres.";
        }
        if (nombre.length() > NOMBRE_MAX) {
            return "El nombre no puede superar los " + NOMBRE_MAX + " caracteres.";
        }
        if (!PATRON_NOMBRE.matcher(nombre).matches()) {
            return "El nombre solo puede contener letras, números, espacios y los signos . , & ( ) -";
        }
        return null;
    }

    /** Devuelve el mensaje de error o null si la descripción es válida (puede ir vacía). */
    public static String validarDescripcionCategoria(String descripcion) {
        if (descripcion == null) {
            return null;
        }
        if (descripcion.length() > DESCRIPCION_MAX) {
            return "La descripción no puede superar los " + DESCRIPCION_MAX + " caracteres.";
        }
        if (!PATRON_DESCRIPCION.matcher(descripcion).matches()) {
            return "La descripción contiene caracteres no permitidos.";
        }
        return null;
    }

    // ==================== CABECERAS ====================
    public static void aplicarCabecerasSeguras(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("Referrer-Policy", "same-origin");
        response.setHeader("Content-Security-Policy",
                "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; "
                + "img-src 'self' data:; font-src 'self' data:; frame-ancestors 'none'; "
                + "form-action 'self'; base-uri 'self'");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }
}
