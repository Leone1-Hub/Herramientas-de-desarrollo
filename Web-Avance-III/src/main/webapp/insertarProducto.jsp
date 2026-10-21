<%-- 
    Document   : insertarProducto
    Created on : 20 may. 2025, 4:29:14 p. m.
    Author     : Fabrizio AD
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="clases.ConexionBD"%>

<%
    String nombreP = request.getParameter("nombreP");
    String descripcion = request.getParameter("descripcion");
    String marca = request.getParameter("marca");
    String precio = request.getParameter("precio");
    String stock = request.getParameter("stock");

    ConexionBD cnn = new ConexionBD();
    PreparedStatement sentencia = null;

    try {
        String SQL_INSERT = "INSERT INTO productos (nombre, descripcion, marca, precio, stock) VALUES (?, ?, ?, ?, ?)";

        Connection con = cnn.Conectar();
        sentencia = con.prepareStatement(SQL_INSERT);
        sentencia.setString(1, nombreP);
        sentencia.setString(2, descripcion);
        sentencia.setString(3, marca);
        sentencia.setDouble(4, Double.parseDouble(precio));
        sentencia.setInt(5, Integer.parseInt(stock));

        int filas = sentencia.executeUpdate();

        if (filas > 0) {
            out.println("<p style='color: green;'>Producto registrado correctamente.</p>");
        } else {
            out.println("<p style='color: red;'>No se pudo registrar el producto.</p>");
        }

    } catch (Exception e) {
        out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
        e.printStackTrace();
    } finally {
        if (sentencia != null) sentencia.close();
        cnn.Desconectar();
    }
%>

<script type="text/javascript">
    setTimeout(function() {
        window.location.href = "registroProductos.jsp"; 
    }, 5000); // Redirige después de 5 segundos
</script>

<br>
<a href="registroProductos.jsp">← Volver al formulario</a><br>
<a href="SvProductos.jsp">→ Ver productos registrados</a>
