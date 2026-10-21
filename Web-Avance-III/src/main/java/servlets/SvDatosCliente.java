package servlets;

import clases.ConexionBD;
import clases.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@WebServlet(name = "SvDatosCliente", urlPatterns = {"/SvDatosCliente"})
public class SvDatosCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null || usuarioSesion.getIdusuario() == 0) {
            response.sendRedirect("login.jsp?msg=Sesión expirada o incompleta");
            return;
        }

        try {
            ConexionBD cnn = new ConexionBD();
            Connection con = cnn.Conectar();

            String sql = "SELECT * FROM usuarios WHERE idusuario = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, usuarioSesion.getIdusuario());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Usuario usuarioCompleto = new Usuario();
                    usuarioCompleto.setIdusuario(rs.getInt("idusuario"));
                    usuarioCompleto.setNombre(rs.getString("nombre"));
                    usuarioCompleto.setApaterno(rs.getString("apaterno"));
                    usuarioCompleto.setAmaterno(rs.getString("amaterno"));
                    usuarioCompleto.setnDocumento(rs.getString("nDocumento"));
                    usuarioCompleto.setCorreo(rs.getString("correo"));
                    usuarioCompleto.setTelefono(rs.getString("telefono"));
                    usuarioCompleto.setRol(rs.getString("rol"));
                    
                    // Campos adicionales que requiere el JSP
                    usuarioCompleto.setFechaN(rs.getDate("fechaN"));
                    usuarioCompleto.setNacionalidad(rs.getString("nacionalidad"));
                    usuarioCompleto.setDepartamento(rs.getString("departamento"));
                    usuarioCompleto.setProvincia(rs.getString("provincia"));
                    usuarioCompleto.setDistrito(rs.getString("distrito"));
                    usuarioCompleto.setDireccion(rs.getString("direccion"));

                    // Enviar al JSP
                    request.setAttribute("cliente", usuarioCompleto);
                    request.getRequestDispatcher("DatosCliente.jsp").forward(request, response);
                } else {
                    response.sendRedirect("error.jsp?msg=No se encontraron datos del cliente");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?msg=Error al obtener datos del cliente");
        }
    }
}
