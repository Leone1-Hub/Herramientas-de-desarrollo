
package servlets;

import clases.Controladora;
import clases.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "SvCambioContrasena", urlPatterns = {"/SvCambioContrasena"})
public class SvCambioContrasena extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioSesion = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuarioSesion == null) {
            response.sendRedirect("login.jsp?error=session_expirada");
            return;
        }

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            response.sendRedirect("cambioContrasena.jsp?error=Las contraseñas no coinciden");
            return;
        }

        try {
            // Traer datos actualizados desde BD
            Usuario usuarioBD = control.traerUsuario(usuarioSesion.getIdusuario());
            usuarioBD.setContraseña(newPassword);
            control.editarUsuario(usuarioBD);

            // Actualizar también la sesión
            session.setAttribute("usuario", usuarioBD);

            response.sendRedirect("cambioContrasena.jsp?mensaje=Contraseña actualizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cambioContrasena.jsp?error=Error al actualizar la contraseña");
        }
    }
}


