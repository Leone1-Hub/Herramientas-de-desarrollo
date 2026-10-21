
package servlets;
import clases.Controladora;
import clases.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "svEditar", urlPatterns = {"/svEditar"})
public class svEditar extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idusuario");
        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta idusuario");
            return;
        }

        try {
            int id_usuarioEditar = Integer.parseInt(idStr);
            Usuario usu = control.traerUsuario(id_usuarioEditar);

            if (usu == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            HttpSession miSesion = request.getSession();
            miSesion.setAttribute("usuEditar", usu);

            response.sendRedirect("editar_usuario.jsp");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            String idStr = request.getParameter("idusuario");
            if (idStr == null || idStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta idusuario en el formulario.");
                return;
            }

            int id = Integer.parseInt(idStr);
            Usuario usu = control.traerUsuario(id);

            if (usu == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
                return;
            }

            // Datos del formulario
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String departamento = request.getParameter("departamento");
            String provincia = request.getParameter("provincia");
            String distrito = request.getParameter("distrito");

            // Validaciones
            boolean correoExiste = control.existeCorreoEnOtroUsuario(correo, id);
            boolean telefonoExiste = control.existeTelefonoEnOtroUsuario(telefono, id);

            String mensaje = "";
            if (correoExiste) mensaje += "El correo ya está registrado por otro usuario. ";
            if (telefonoExiste) mensaje += "El teléfono ya está registrado por otro usuario. ";

            if (!mensaje.isEmpty()) {
                request.setAttribute("errorEditar", mensaje);
                request.setAttribute("usuEditar", usu);
                request.getRequestDispatcher("editar_usuario.jsp").forward(request, response);
                return;
            }

            // Actualizar objeto
            usu.setTelefono(telefono);
            usu.setCorreo(correo);
            usu.setDireccion(direccion);
            usu.setDepartamento(departamento);
            usu.setProvincia(provincia);
            usu.setDistrito(distrito);

            control.editarUsuario(usu);

            // Verificar rol del usuario actual
            HttpSession sesion = request.getSession();
            Usuario usuarioSesion = (Usuario) sesion.getAttribute("usuario");

            if (usuarioSesion != null && "administrador".equalsIgnoreCase(usuarioSesion.getRol())) {
                // Si es administrador, redirige a listarUsuarios.jsp
                request.getSession().setAttribute("listaUsuarios", control.traerUsuarios());
                response.sendRedirect("listarUsuarios.jsp");
            } else {
                // Si es usuario normal, mostrar mensaje de éxito
                request.setAttribute("msg", "Se actualizó los datos satisfactoriamente");
                request.setAttribute("cliente", usu); // vuelve con los datos
                request.getRequestDispatcher("DatosCliente.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorEditar", "Error inesperado: " + e.getMessage());
            request.getRequestDispatcher("editar_usuario.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para editar un usuario existente.";
    }
}
