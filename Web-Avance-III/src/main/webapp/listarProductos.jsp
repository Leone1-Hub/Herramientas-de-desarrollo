<%-- 
    Document   : listarProductos
    Created on : 18 may. 2025, 11:10:14?p. m.
    Author     : Fabrizio AD
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Productos</title>
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/plugins/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet">
    <link href="assets/plugins/c3-master/c3.min.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/pages/dashboard2.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="main/css/margin.css">

    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
            font-weight: bold;
            color: #0a1f44;
        }
        .table-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        table {
            font-size: 14px;
            font-weight: bold;
        }
    </style>
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
                        <span><img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" class="dark-logo" alt="homepage" /></span>
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
                        <li><a href="SvProductos"><i class="mdi mdi-file"></i><span class="hide-menu">Listado de Productos</span></a></li> 
                    </ul>
                </nav>
            </div>
        </aside>

        <div class="page-wrapper">
            <div class="container-fluid">
                <h1>Lista de Productos</h1>
                <div class="table-container">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Marca</th>
                                    <th>Precio</th>
                                    <th>Stock</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="producto" items="${productos}">
                                    <tr>
                                        <td>${producto.idproducto}</td>
                                        <td>${producto.nombre}</td>
                                        <td>${producto.descripcion}</td>
                                        <td>${producto.marca}</td>
                                        <td>S/ ${producto.precio}</td>
                                        <td>${producto.stock}</td>
                                        <td>
                                            <a href="SvEditarProducto?idproducto=${producto.idproducto}" class="btn btn-sm btn-warning">Editar</a>
                                            <a href="SvEliminarProducto?idproducto=${producto.idproducto}" class="btn btn-sm btn-danger"
                                               onclick="return confirm('¿Estás seguro de eliminar este producto?');">Eliminar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty productos}">
                                    <tr><td colspan="7" class="text-danger">No hay productos registrados.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                    <a href="index.jsp" class="back-link">← Volver a la página principal</a>
                </div>
            </div>
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
