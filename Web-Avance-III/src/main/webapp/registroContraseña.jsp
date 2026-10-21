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

    <!-- Bootstrap Core CSS -->
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="main/css/margin.css">
    <link rel="stylesheet" href="main/scss/responsive.scss">
    <script src="js/saveAlumno.js"></script>
</head>

<body class="fix-header fix-sidebar card-no-border">
    <div class="preloader">
        <div class="loader">
            <div class="loader__figure"></div>
            <p class="loader__label">Proceso matrícula</p>
        </div>
    </div>

    <div id="main-wrapper">
        <!-- Header -->
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.jsp">
                        <img style="margin-left: 50px;" src="assets/images/ImagenesVirtualStore/StoreIcon.png" alt="" class="dark-logo"/>
                        <span>
                            <img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" class="dark-logo" alt="homepage" />
                        </span> 
                    </a>
                </div>
                <div class="navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item"> <a class="nav-link nav-toggler hidden-md-up waves-effect waves-dark" href="#"><i class="ti-menu"></i></a> </li>
                        <li style="margin-left: -62px;" class="nav-item"> <a class="nav-link sidebartoggler hidden-sm-down waves-effect waves-dark" href="#"><i class="ti-menu"></i></a> </li>
                    </ul>
                    <div class="ml-auto">
                        <a class="labelPersonalizado" href="login.jsp">Salir</a>
                    </div>
                </div>
            </nav>
        </header>

        <!-- Sidebar -->
        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="user-profile"> 
                            <a><img src="assets/images/users/Perfil.png" alt="user" /><span class="hide-menu">Perfil</span></a>
                        </li>
                        <li class="nav-devider"></li>    
                        <li><a href="registroCliente.jsp" aria-expanded="false">
                            <i class="mdi mdi-file"></i><span class="hide-menu">Registro del Cliente</span></a></li> 
                    </ul>
                </nav>
            </div>
        </aside>

        <!-- Contenido principal -->
        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles"></div>

                <div class="row">
                    <div style="margin-top: 70px; gap: 10px;" class="col-9 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <div>
                                        <h3 class="card-title m-b-5"><span class="lstick"></span>Crear Contraseña</h3>
                                    </div>
                                </div>
                                <div class="container">
                                    <form id="form-crearUsuario" action="CrearUsuarioServlet" method="POST">
                                        <div class="mb-1">
                                            <label for="new-password" class="form-label">Nueva contraseña</label>
                                            <input type="password" id="new-password" name="password" class="form-control" placeholder="********" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="confirm-password" class="form-label">Confirmar contraseña</label>
                                            <input type="password" id="confirm-password" name="confirm_password" class="form-control" placeholder="********" required>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <button class="btn btn-danger" type="button" onclick="window.location.href='registroCliente.jsp'">Cancelar</button>
                                            <button class="btn btn-primary" type="submit">Crear usuario</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <footer class="footer" style="text-align: center; font-weight: bold;"> © 2025 Virtual Store </footer>
        </div>
    </div>

    <!-- Scripts -->
    <script src="assets/plugins/jquery/jquery.min.js"></script>
    <script src="main/js/custom.min.js"></script>
</body>
</html>
