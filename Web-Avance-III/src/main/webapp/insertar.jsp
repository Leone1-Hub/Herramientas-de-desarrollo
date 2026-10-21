<%-- 
    Document   : insertar
    Created on : 18 may. 2025, 11:25:13 p. m.
    Author     : Fabrizio AD
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="clases.ConexionBD"%>

<%
    // Obtener parámetros desde el formulario
    String nombre = request.getParameter("nombre");
    String apaterno = request.getParameter("apellido");
    String amaterno = request.getParameter("apellidoMaterno");
    String fechaN = request.getParameter("fecha");
    String tipoDocumento = request.getParameter("tDocumento");
    String nDocumento = request.getParameter("nDocumento");
    String nacionalidad = request.getParameter("nacionalidad");
    String sexo = request.getParameter("sexo");
    String correo = request.getParameter("email");
    String telefono = request.getParameter("telefono");
    String departamento = request.getParameter("departamento");
    String direccion = request.getParameter("direccion");
    String provincia = request.getParameter("provincia");
    String distrito = request.getParameter("distrito");

    // Contraseña y rol por defecto
    String contraseña = "123456";
    String rol = "Usuario";

    ConexionBD cnn = new ConexionBD();
    Connection conn = null;
    PreparedStatement sentencia = null;

    boolean valido = true;
    String mensaje = "";

    try {
        conn = cnn.Conectar();

        // Validar duplicidad de nDocumento
        sentencia = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE nDocumento = ?");
        sentencia.setString(1, nDocumento);
        ResultSet rs = sentencia.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            mensaje += "El número de documento ya está registrado.<br>";
            valido = false;
        }
        sentencia.close();

        // Validar duplicidad de correo
        sentencia = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE correo = ?");
        sentencia.setString(1, correo);
        rs = sentencia.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            mensaje += "El correo ya está registrado.<br>";
            valido = false;
        }
        sentencia.close();

        // Validar duplicidad de teléfono
        sentencia = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE telefono = ?");
        sentencia.setString(1, telefono);
        rs = sentencia.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            mensaje += "El teléfono ya está registrado.<br>";
            valido = false;
        }
        sentencia.close();

        // Si todo está bien, realizar el insert
        if (valido) {
            String SQL_INSERT = "INSERT INTO usuarios (nombre, apaterno, amaterno, fechaN, tipoDocumento, nDocumento, nacionalidad, sexo, correo, telefono, direccion, departamento, provincia, distrito, contraseña, rol) "
                      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            sentencia = conn.prepareStatement(SQL_INSERT);
            sentencia.setString(1, nombre);
            sentencia.setString(2, apaterno);
            sentencia.setString(3, amaterno);
            sentencia.setString(4, fechaN);
            sentencia.setString(5, tipoDocumento);
            sentencia.setString(6, nDocumento);
            sentencia.setString(7, nacionalidad);
            sentencia.setString(8, sexo);
            sentencia.setString(9, correo);
            sentencia.setString(10, telefono);
            sentencia.setString(11, direccion);
            sentencia.setString(12, departamento);
            sentencia.setString(13, provincia);
            sentencia.setString(14, distrito);
            sentencia.setString(15, contraseña);
            sentencia.setString(16, rol);

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                out.println("<p style='color: green;'>Usuario registrado correctamente.</p>");
            } else {
                out.println("<p style='color: red;'>No se pudo registrar el usuario.</p>");
            }
        } else {
            out.println("<p style='color: red; font-weight:bold;'>" + mensaje + "</p>");
            out.println("<a href='registroCliente.jsp'>← Volver al registro</a>");
        }

    } catch (Exception e) {
        out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
        e.printStackTrace();
    } finally {
        if (sentencia != null) sentencia.close();
        cnn.Desconectar();
    }
%>

<br><br>
<a href="index.jsp">← Volver a la página principal</a><br>
<a href="listarUsuarios.jsp">→ Ver lista de usuarios</a>
