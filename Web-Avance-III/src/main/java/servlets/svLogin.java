
    package servlets;
    import clases.ConexionBD;
    import clases.Usuario;
    import java.io.IOException;
    import java.sql.CallableStatement;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;

    @WebServlet(name = "svLogin", urlPatterns = {"/svLogin"})
public class svLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String documento = request.getParameter("nDocumento");
        String contraseña = request.getParameter("password");
        String rol = request.getParameter("rol");

        ConexionBD cnn = new ConexionBD();
        Usuario usuario = null;

        try (Connection con = cnn.Conectar()) {
            if (con != null) {
                // Verificar si el usuario existe
                PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM usuarios WHERE nDocumento = ?");
                checkStmt.setString(1, documento);
                ResultSet checkRs = checkStmt.executeQuery();
                checkRs.next();
                int count = checkRs.getInt(1);
                checkRs.close();
                checkStmt.close();

                if (count == 0) {
                    response.sendRedirect("index.jsp?error=usuario_no_registrado");
                    return;
                }

                // Llamar procedimiento
                String sql = "{CALL validar_login(?, ?, ?)}";
                CallableStatement stmt = con.prepareCall(sql);
                stmt.setString(1, documento);
                stmt.setString(2, contraseña);
                stmt.setString(3, rol); // sin convertir a lowercase
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int idUsuario = rs.getInt("idusuario");

                    PreparedStatement datosStmt = con.prepareStatement(
                        "SELECT * FROM usuarios WHERE idusuario = ?");
                    datosStmt.setInt(1, idUsuario);
                    ResultSet datosRs = datosStmt.executeQuery();

                    if (datosRs.next()) {
                        usuario = new Usuario();
                        usuario.setIdusuario(idUsuario);
                        usuario.setNombre(datosRs.getString("nombre"));
                        usuario.setApaterno(datosRs.getString("apaterno"));
                        usuario.setAmaterno(datosRs.getString("amaterno"));
                        usuario.setTipoDocumento(datosRs.getString("tipoDocumento"));
                        usuario.setnDocumento(datosRs.getString("nDocumento"));
                        usuario.setCorreo(datosRs.getString("correo"));
                        usuario.setTelefono(datosRs.getString("telefono"));
                        usuario.setDireccion(datosRs.getString("direccion"));
                        usuario.setRol(datosRs.getString("rol"));

                        HttpSession sesion = request.getSession();
                        sesion.setAttribute("usuario", usuario);

                        if ("Usuario".equals(rol)) {
                            response.sendRedirect("pMain.jsp");
                        } else if ("Administrador".equals(rol)) {
                            response.sendRedirect("adminDashboard.jsp");
                        } else {
                            response.sendRedirect("index.jsp?error=rol_desconocido");
                        }
                    }
                    datosRs.close();
                    datosStmt.close();
                } else {
                    response.sendRedirect("index.jsp?error=credenciales_invalidas");
                }
                rs.close();
                stmt.close();
            } else {
                response.sendRedirect("index.jsp?error=conexion");
            }
        } catch (Exception e) {
                e.printStackTrace(); // Muestra el error en la consola
                request.setAttribute("mensaje", "Error: " + e.getMessage());
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
    }
}
