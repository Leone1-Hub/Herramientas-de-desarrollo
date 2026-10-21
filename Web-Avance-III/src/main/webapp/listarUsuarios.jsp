<%-- 
    Document   : listarUsuarios
    Created on : 18 may. 2025, 11:10:14?p. m.
    Author     : Fabrizio AD
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="clases.ConexionBD"%>

<%
    ConexionBD cnn;
    String SQL_SELECT = "SELECT * FROM usuarios";
    ResultSet registros;
    PreparedStatement sentencia;
%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Usuarios</title>
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/plugins/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet">
    <link href="assets/plugins/c3-master/c3.min.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/pages/dashboard2.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="main/css/margin.css">
    <link rel="stylesheet" href="main/css/style3.css">
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
                        <li class="user-profile"> 
                            <a><img src="assets/images/icon/staff.png" alt="user" /><span class="hide-menu">Administrador</span></a>
                        </li>
                        <li class="nav-devider"></li>    
                        <li>
                            <a href="listarUsuarios.jsp">
                                <i class="mdi mdi-file"></i><span class="hide-menu">Listado de Usuarios</span>
                            </a>
                        </li> 
                    </ul>
                </nav>
            </div>
        </aside>

        <div class="page-wrapper">
            <div class="container-fluid">
                <h1>Lista de Usuarios</h1>
                <div class="table-container">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>A. Paterno</th>
                                    <th>A. Materno</th>
                                    <th>Fecha Nac.</th>
                                    <th>Documento</th>
                                    <th>N° Documento</th>
                                    <th>Nacionalidad</th>
                                    <th>Sexo</th>
                                    <th>Correo</th>
                                    <th>Teléfono</th>
                                    <th>Dirección</th>
                                    <th>Departamento</th>
                                    <th>Provincia</th>
                                    <th>Distrito</th>
                                    <th>Contraseña</th>
                                    <th>Rol</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    try {
                                        cnn = new ConexionBD();
                                        sentencia = cnn.Conectar().prepareStatement(SQL_SELECT);
                                        registros = sentencia.executeQuery();

                                        boolean hayDatos = false;

                                        while (registros.next()) {
                                            hayDatos = true;
                                %>
                                    <tr>
                                        <td><%= registros.getInt("idusuario") %></td>
                                        <td><%= registros.getString("nombre") %></td>
                                        <td><%= registros.getString("apaterno") %></td>
                                        <td><%= registros.getString("amaterno") %></td>
                                        <td><%= registros.getString("fechaN") %></td>
                                        <td><%= registros.getString("tipoDocumento") %></td>
                                        <td><%= registros.getString("nDocumento") %></td>
                                        <td><%= registros.getString("nacionalidad") %></td>
                                        <td><%= registros.getString("sexo") %></td>
                                        <td><%= registros.getString("correo") %></td>
                                        <td><%= registros.getString("telefono") %></td>
                                        <td><%= registros.getString("direccion") %></td>
                                        <td><%= registros.getString("departamento") %></td>
                                        <td><%= registros.getString("provincia") %></td>
                                        <td><%= registros.getString("distrito") %></td>
                                        <td><%= registros.getString("contraseña") %></td>
                                        <td><%= registros.getString("rol") %></td>
                                        <td>
                                            <form action="svEditar" method="get" style="display:inline;">
                                                <input type="hidden" name="idusuario" value="<%= registros.getInt("idusuario") %>">
                                                <input type="submit" class="btn btn-warning btn-sm" value="Editar">
                                            </form>

                                            <form action="svEliminar" method="post" style="display:inline;" onsubmit="return confirm('¿Eliminar este usuario?');">
                                                <input type="hidden" name="idusuario" value="<%= registros.getInt("idusuario") %>">
                                                <input type="submit" class="btn btn-danger btn-sm" value="Eliminar">
                                            </form>
                                        </td>
                                    </tr>
                                <%
                                        }

                                        if (!hayDatos) {
                                %>
                                    <tr><td colspan="18" class="text-center text-warning">No hay usuarios registrados.</td></tr>
                                <%
                                        }

                                        cnn.Desconectar();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                %>
                                    <tr><td colspan="18" class="text-danger">Error al cargar los usuarios: <%= e.getMessage() %></td></tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                    <a href="index.jsp" class="btn btn-secondary mt-3">← Volver a la página principal</a>
                </div>
            </div>
        </div>
    </div>

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
