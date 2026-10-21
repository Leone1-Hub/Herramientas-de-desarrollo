<%-- 
    Document   : registroCliente
    Created on : 16 may. 2025, 12:35:26 a. m.
    Author     : Fabrizio AD
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Virtual Store - Registro Producto</title>
    
    <!-- CSS -->
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/plugins/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
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
        <!-- Barra superior -->
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">
                        <img style="margin-left: 50px;" src="assets/images/ImagenesVirtualStore/StoreIcon.png" alt="logo" />
                        <span>
                            <img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" alt="letras" />
                        </span>
                    </a>
                </div>
                <div class="navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link nav-toggler hidden-md-up waves-effect waves-dark" href="#"><i class="ti-menu"></i></a>
                        </li>
                        <li class="nav-item" style="margin-left: -62px;">
                            <a class="nav-link sidebartoggler hidden-sm-down waves-effect waves-dark" href="#"><i class="ti-menu"></i></a>
                        </li>
                    </ul>
                    <div class="ml-auto">
                        <a class="labelPersonalizado" href="index.jsp">Salir</a>
                    </div>
                </div>
            </nav>
        </header>

        <!-- Menú lateral -->
        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="user-profile"> 
                            <a><img src="assets/images/icon/staff.png" alt="user" /><span class="hide-menu">Administrador</span></a>
                        </li>
                        <li class="nav-devider"></li>    
                        <li><a href="registroProductos.jsp"><i class="mdi mdi-file"></i><span class="hide-menu">Registro del producto</span></a></li> 
                    </ul>
                </nav>
            </div>
        </aside>

        <!-- Contenido principal -->
        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles">
                    <div class="col-md-12 text-center">
                        <h2 class="card-title">Información del Producto</h2>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <form action="SvProductos" method="POST">
                                    <div class="form-group">
                                        <label for="nombre">Nombre del producto</label>
                                        <input type="text" name="nombre" id="nombre" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="descripcion">Descripción</label>
                                        <input type="text" name="descripcion" id="descripcion" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="marca">Marca</label>
                                        <input type="text" name="marca" id="marca" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="precio">Precio</label>
                                        <input type="number" name="precio" id="precio" step="0.01" class="form-control" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="stock">Stock</label>
                                        <input type="number" name="stock" id="stock" class="form-control" required>
                                    </div>
                                    <div class="form-group text-center">
                                        <button type="submit" class="btn btn-primary mx-2" style="width: 30%;">Registrar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
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
