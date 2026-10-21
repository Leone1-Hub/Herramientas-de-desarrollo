<%-- 
    Document   : reigstroContraseña
    Created on : 16 may. 2025, 12:35:26 a. m.
    Author     : Fabrizio AD
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Virtual Store</title>

    <!-- Estilos -->
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link href="main/css/margin.css" rel="stylesheet">
</head>

<body class="fix-header fix-sidebar card-no-border">
    <div class="preloader">
        <div class="loader">
            <div class="loader__figure"></div>
            <p class="loader__label">Proceso matrícula</p>
        </div>
    </div>

    <div id="main-wrapper">
        <!-- Encabezado -->
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.jsp">
                        <img style="margin-left: 50px;" src="assets/images/ImagenesVirtualStore/StoreIcon.png" alt="Logo" class="dark-logo"/>
                        <span>
                            <img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" class="dark-logo" alt="Virtual Store" />
                        </span>
                    </a>
                </div>
                <div class="navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item"><a class="nav-link nav-toggler hidden-md-up waves-effect waves-dark" href="#"><i class="ti-menu"></i></a></li>
                        <li class="nav-item" style="margin-left: -62px;"><a class="nav-link sidebartoggler hidden-sm-down waves-effect waves-dark" href="#"><i class="ti-menu"></i></a></li>
                    </ul>
                    <div class="ml-auto">
                            <%
                                HttpSession sesion = request.getSession(false);
                                clases.Usuario usuario = (sesion != null) ? (clases.Usuario) sesion.getAttribute("usuario") : null;
                            %>

                            <% if (usuario != null) { %>
                                <a href="svLogout" class="labelPersonalizado">Salir de la cuenta</a>
                            <% } else { %>
                                <a href="login.jsp" class="labelPersonalizado">Iniciar sesión</a>
                            <% } %>
                        </div>
                    </div>
                </nav>
            </header>

        <!-- Barra lateral -->
        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="user-profile">
                                <a>
                                    <% if (usuario != null) { %>
                                        <img src="assets/images/users/HombreGuapo.jpg" alt="Usuario autenticado"/>
                                        <span class="hide-menu" id="nombreAlumno"><%= usuario.getNombre() %> <%= usuario.getApaterno() %></span>
                                    <% } else { %>
                                        <img src="assets/images/users/Perfil.png" alt="Usuario invitado"/>
                                        <span class="hide-menu">Invitado</span>
                                    <% } %>
                                </a>
                            </li>
                        <li class="nav-devider"></li>
                        <li><a href="TiendaMain.jsp"><i class="mdi mdi-store"></i><span class="hide-menu">Tienda</span></a></li>
                        <li><a href="cambioContrasena.jsp"><i class="mdi mdi-bullseye"></i><span class="hide-menu">Contraseña</span></a></li>
                        <li><a href="SvDatosCliente"><i class="mdi mdi-file"></i><span class="hide-menu">Datos del cliente</span></a></li>
                        <li><a href="listarPedidos.jsp" aria-expanded="false"><i class="mdi mdi-file"></i><span class="hide-menu">Listado de Pedidos</span></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>

        <!-- Contenido -->
        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles">
                    <div class="col-md-5 align-self-center">
                        <h3 class="text-themecolor">Contraseña</h3>
                    </div>
                    <div class="col-md-7 align-self-center">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Inicio</a></li>
                            <li class="breadcrumb-item active">Configuración</li>
                        </ol>
                    </div>
                </div>

                <!-- Formulario de cambio de contraseña -->
                <div class="row">
                    <div class="col-9 mx-auto" style="margin-top: 50px;">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="card-title m-b-5"><span class="lstick"></span>Actualizar Contraseña</h3>
                                <div class="container">
                                    <% if (request.getParameter("mensaje") != null) { %>
                                                <div class="alert alert-success">
                                                    <%= request.getParameter("mensaje") %>
                                                </div>
                                            <% } else if (request.getParameter("error") != null) { %>
                                                <div class="alert alert-danger">
                                                    <%= request.getParameter("error") %>
                                                </div>
                                            <% } %>
                                    <form id="form-cambiarPassword" action="SvCambioContrasena" method="POST">
                                        <div class="mb-3">
                                            <label for="new-password" class="form-label">Nueva contraseña</label>
                                            <input type="password" name="newPassword" id="new-password" class="form-control" placeholder="********" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="confirm-password" class="form-label">Confirmar contraseña</label>
                                            <input type="password" name="confirmPassword" id="confirm-password" class="form-control" placeholder="********" required>
                                        </div>
                                        <div class="d-flex justify-content-between" style="margin-top: 35px;">
                                            <button class="btn btn-danger mx-2" type="button" onclick="cancelarResetPassword()">Cancelar</button>
                                            <button class="btn btn-primary" type="submit">Guardar Contraseña</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
        </div>
    </div>

                                    
    <!-- JS -->
    <script src="assets/plugins/jquery/jquery.min.js"></script>
    <script src="main/js/custom.min.js"></script>
</body>

</html>
