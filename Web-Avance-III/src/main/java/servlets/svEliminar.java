
package servlets;

import clases.Controladora;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "svEliminar", urlPatterns = {"/svEliminar"})
public class svEliminar extends HttpServlet {

    Controladora con = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idusuario");
        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta idusuario");
            return;
        }

        try {
            int id_eliminar = Integer.parseInt(idStr);
            con.borrarUsuario(id_eliminar);

            // Actualiza lista
            request.getSession().setAttribute("listaUsuarios", con.traerUsuarios());

            response.sendRedirect("listarUsuarios.jsp");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "idusuario inválido");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para eliminar un usuario.";
    }
}
