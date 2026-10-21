<%-- 
    Document   : registroCliente
    Created on : 16 may. 2025, 12:35:26 a. m.
    Author     : Fabrizio AD
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.sql.*"%>
<%@page import="clases.ConexionBD"%>

<%
    String mensaje = "";
    boolean mostrarMensaje = false;

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        // Obtener datos
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

        String contraseña = "123456";
        String rol = "Usuario";

        ConexionBD cnn = new ConexionBD();
        Connection conn = null;
        PreparedStatement ps = null;
        boolean valido = true;

        try {
            conn = cnn.Conectar();

            // Validar DNI
            ps = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE nDocumento = ?");
            ps.setString(1, nDocumento);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                mensaje += "El número de documento ya existe.<br>";
                valido = false;
            }
            ps.close();

            // Validar correo
            ps = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE correo = ?");
            ps.setString(1, correo);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                mensaje += "El correo ya existe.<br>";
                valido = false;
            }
            ps.close();

            // Validar teléfono
            ps = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE telefono = ?");
            ps.setString(1, telefono);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                mensaje += "El teléfono ya existe.<br>";
                valido = false;
            }
            ps.close();

            if (valido) {
                String SQL = "INSERT INTO usuarios (nombre, apaterno, amaterno, fechaN, tipoDocumento, nDocumento, nacionalidad, sexo, correo, telefono, direccion, departamento, provincia, distrito, contraseña, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(SQL);
                ps.setString(1, nombre);
                ps.setString(2, apaterno);
                ps.setString(3, amaterno);
                ps.setString(4, fechaN);
                ps.setString(5, tipoDocumento);
                ps.setString(6, nDocumento);
                ps.setString(7, nacionalidad);
                ps.setString(8, sexo);
                ps.setString(9, correo);
                ps.setString(10, telefono);
                ps.setString(11, direccion);
                ps.setString(12, departamento);
                ps.setString(13, provincia);
                ps.setString(14, distrito);
                ps.setString(15, contraseña);
                ps.setString(16, rol);

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    mensaje = "✅ Usuario registrado correctamente.";
                } else {
                    mensaje = "❌ No se pudo registrar el usuario.";
                }
            }

        } catch (Exception e) {
            mensaje = "❌ Error: " + e.getMessage();
            e.printStackTrace();
        } finally {
            if (ps != null) ps.close();
            cnn.Desconectar();
        }

        mostrarMensaje = true;
    }
%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Virtual Store</title>
    <!-- CSS -->
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/plugins/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet">
    <link href="assets/plugins/chartist-js/dist/chartist.min.css" rel="stylesheet">
    <link href="assets/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.css" rel="stylesheet">
    <link href="assets/plugins/c3-master/c3.min.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/pages/dashboard2.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="main/css/pTienda.css">
    <link rel="stylesheet" href="main/css/margin.css">
</head>

<body class="fix-header fix-sidebar card-no-border">
    <div class="preloader">
        <div class="loader">
            <div class="loader__figure"></div>
            <p class="loader__label">Proceso tienda</p>
        </div>
    </div>

    <div id="main-wrapper">
        
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">
                        <img style="margin-left: 50px;" src="assets/images/ImagenesVirtualStore/StoreIcon.png" alt="" class="dark-logo" />
                        <span>
                            <img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" class="dark-logo" alt="homepage" />
                        </span>
                    </a>
                </div>
                <div class="navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item"><a class="nav-link nav-toggler hidden-md-up waves-effect waves-dark" href="#"><i class="ti-menu"></i></a></li>
                        <li class="nav-item" style="margin-left: -62px;"><a class="nav-link sidebartoggler hidden-sm-down waves-effect waves-dark" href="#"><i class="ti-menu"></i></a></li>
                    </ul>
                    <div class="ml-auto">
                        <a class="labelPersonalizado" href="index.jsp">Salir</a>
                    </div>
                </div>
            </nav>
        </header>

        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="user-profile"><a><img src="assets/images/icon/staff.png" alt="user" /><span class="hide-menu">Administrador</span></a></li>
                        <li class="nav-devider"></li>  
                        <li><a href="CreacionCuenta.jsp" aria-expanded="false">
                            <i class="mdi mdi-file"></i><span class="hide-menu">Registro del Cliente</span></a></li> 
                    </ul>
                </nav>
            </div>
        </aside>

        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles">
                    <div class="col-md-7 align-self-center"></div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <div class="card-body">
                                    <div class="d-flex">
                                        <div>
                                            <h3 class="card-title m-b-5"><span class="lstick"></span>Datos del Cliente</h3>
                                        </div>
                                    </div>
                                </div>
                                <form id="form-registroAlumno" method="POST">
                                    <div class="form-group">
                                        <label for="nombre">Primer Nombre</label>
                                        <input type="text" name="nombre" id="nombre" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="aPaterno">Apellido Paterno</label>
                                        <input type="text" name="apellido" id="aPaterno" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="aMaterno">Apellido Materno</label>
                                        <input type="text" name="apellidoMaterno" id="aMaterno" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="fechaNacimiento">Fecha de Nacimiento</label>
                                        <input type="date" name="fecha" id="fechaNacimiento" class="form-control" required>
                                    </div>

                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label for="tipoDocumento">Tipo de documento</label>
                                            <select name="tDocumento" id="tipoDocumento" class="form-control" required>
                                                <option value="DNI" selected>DNI</option>
                                                <option value="passport">Pasaporte</option>
                                                <option value="other">Otro</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="nDocumento">Número de documento</label>
                                            <input type="text" name="nDocumento" id="nDocumento" class="form-control" maxlength="20" required>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="nacionalidad">Nacionalidad</label>
                                        <input type="text" name="nacionalidad" id="nacionalidad" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="sexo">Sexo</label>
                                        <input type="text" name="sexo" id="sexo" class="form-control" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="email">Correo</label>
                                        <input type="text" name="email" id="email" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="telefono">Teléfono</label>
                                        <input type="text" name="telefono" id="telefono" maxlength="9" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="departamento">Departamento</label>
                                        <input type="text" name="departamento" id="departamento" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="provincia">Provincia</label>
                                        <input type="text" name="provincia" id="provincia" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="distrito">Distrito</label>
                                        <input type="text" name="distrito" id="distrito" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="direccion">Dirección</label>
                                        <input type="text" name="direccion" id="direccion" class="form-control" required>
                                    </div>                 
                                    <div class="form-group text-center">
                                        <button type="submit" class="btn btn-primary mx-2" style="width: 30%;">Siguiente</button>
                                    </div>
                                </form>
                                <% if (mostrarMensaje) { %>
                                    <div class="alert alert-info mt-3" role="alert">
                                        <%= mensaje %>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
        </div>
    </div>

   <!-- Scripts -->
    <script src="assets/plugins/jquery/jquery.min.js"></script>
    <script src="assets/plugins/bootstrap/js/popper.min.js"></script>
    <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="main/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="main/js/waves.js"></script>
    <script src="main/js/sidebarmenu.js"></script>
    <script src="main/js/custom.min.js"></script>
    <script src="assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
</body>
</html>



