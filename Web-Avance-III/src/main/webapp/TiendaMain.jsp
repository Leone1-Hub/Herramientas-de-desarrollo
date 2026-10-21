<%-- 
    Document   : TiendaMain
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
            <p class="loader__label">Proceso compras</p>
        </div>
    </div>

    <div id="main-wrapper">

        <!-- Encabezado -->
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.jsp">
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
                        <li><a href="SvDatosCliente"><i class="mdi mdi-file"></i>Datos del cliente</a></li>   
                        <li><a href="listarPedidos.jsp" aria-expanded="false"><i class="mdi mdi-file"></i><span class="hide-menu">Listado de Pedidos</span></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>

        <!-- Contenido principal -->
        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles">
                    <div class="col-md-5 align-self-center">
                        <h3 class="text-themecolor">Tienda Virtual</h3>
                    </div>
                    <div class="col-md-7 align-self-center">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Inicio</a></li>
                            <li class="breadcrumb-item active">Tienda</li>
                        </ol>
                    </div>
                </div>

                <!-- Productos -->
                <div class="row">
                    <div class="col-12 col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <div>
                                        <h3 class="card-title m-b-12" style="margin-top: 15px;"><span class="lstick"></span>Compras en línea</h3>
                                    </div>
                                    <div class="combo">
                                        <button class="btn-combo" style="margin-left: 120px; margin-top: 15px;">New Combo</button>
                                    </div>
                                </div>
                            </div>
                            
                            <form method="get" action="TiendaMain.jsp">
                                <div class="row mb-4">
                                    <div class="col-md-4 offset-md-1">
                                            <input type="text" name="buscar" class="form-control" placeholder="Buscar producto..." 
                                            value="<%= request.getParameter("buscar") != null ? request.getParameter("buscar") : "" %>">
                                        </div>
                                        <div class="col-md-4">
                                        <select name="categoria" class="form-control">
                                            <option value="">-- Todas las categorías --</option>
                                            <option value="Bebidas" <%= "Bebidas".equals(request.getParameter("categoria")) ? "selected" : "" %>>Bebidas</option>
                                            <option value="Lácteos" <%= "Lácteos".equals(request.getParameter("categoria")) ? "selected" : "" %>>Lácteos</option>
                                            <option value="Aceites" <%= "Aceites".equals(request.getParameter("categoria")) ? "selected" : "" %>>Aceites</option>
                                            <option value="Conservas" <%= "Conservas".equals(request.getParameter("categoria")) ? "selected" : "" %>>Conservas</option>
                                            <!-- Agrega más si tienes -->
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <button class="btn btn-primary" type="submit">Filtrar</button>
                                    </div>
                                </div>
                            </form>

                            <form action="ProcesoCompras2.jsp" method="post">
                                <%@ page import="clases.Producto, clases.ProductoDAO, java.util.*" %>
                                    <%
                                        ProductoDAO dao = new ProductoDAO();
                                        String categoria = request.getParameter("categoria");
                                        String buscar = request.getParameter("buscar");

                                        List<Producto> productos = dao.obtenerTodos(); // Asegúrate de tener este método en tu DAO

                                        // Filtro por categoría
                                        if (categoria != null && !categoria.isEmpty()) {
                                            productos.removeIf(p -> !p.getCategoria().equalsIgnoreCase(categoria));
                                        }

                                        // Filtro por texto de búsqueda
                                        if (buscar != null && !buscar.isEmpty()) {
                                            productos.removeIf(p -> !p.getNombre().toLowerCase().contains(buscar.toLowerCase()));
                                        }
                                    %>

                                    <div class="contenedor-productos">
                                        <%
                                            for (Producto p : productos) { 
                                                String idInput = p.getNombre().toLowerCase().replace(" ", "_");

                                                // Lógica para determinar la imagen
                                                String imagen = "default.jpg"; // imagen por defecto
                                                String nombre = p.getNombre().toLowerCase();

                                                if (nombre.contains("aceite")) {
                                                    imagen = "aceite.jpg";
                                                } else if (nombre.contains("leche")) {
                                                    imagen = "leche.jpg";
                                                } else if (nombre.contains("atun") || nombre.contains("atun")) {
                                                    imagen = "pescado.jpg";
                                                } else if (nombre.contains("refresco") || nombre.contains("gaseosa") || nombre.contains("coca")) {
                                                    imagen = "refresco.jpg";
                                                }else if (nombre.contains("red bull") || nombre.contains("redbull")) {
                                                    imagen = "redbull.jpg";
                                                }else if (nombre.contains("arroz") || nombre.contains("arroz")) {
                                                    imagen = "arroz.jpg";
                                                }
                                        %>
                                            <div class="producto">
                                                <h2><%= p.getNombre() %></h2>
                                                <img src="assets/images/ImagenesVirtualStore/<%= imagen %>" alt="<%= p.getNombre() %>"/>
                                                <div class="contador">
                                                    <button type="button" onclick="modificar('<%= idInput %>', -1)">-</button>
                                                    <input type="text" id="<%= idInput %>" name="cantidad_<%= p.getIdproducto() %>" value="0" readonly>
                                                    <input type="hidden" name="producto_id" value="<%= p.getIdproducto() %>">
                                                    <input type="hidden" name="producto_nombre_<%= p.getIdproducto() %>" value="<%= p.getNombre() %>">
                                                    <input type="hidden" name="producto_precio_<%= p.getIdproducto() %>" value="<%= p.getPrecio() %>">
                                                    <button type="button" onclick="modificar('<%= idInput %>', 1)">+</button>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>

                                <div class="columna2 text-center mg-12">
                                    <label>
                                        <p>AL DAR SIGUIENTE ACEPTO LOS &nbsp;<a href="TerminosYCondiciones.jsp">TÉRMINOS, CONDICIONES</a><br> Y AUTORIZACIÓN PARA EL TRATAMIENTO DE DATOS PERSONALES.</p>
                                    </label><br>
                                    <button type="reset" class="btn btn-danger mx-2" style="margin-right: 80px;">Reiniciar Compra</button>
                                    <button type="submit" class="btn btn-primary">Proceder al pago</button>
                                    <br><br>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
        </div>
    </div>

     <script>
        function modificar(id, cambio) {
        const input = document.getElementById(id);
        let valor = parseInt(input.value);
        if (!isNaN(valor)) {
            valor += cambio;
            if (valor < 0) valor = 0;
            input.value = valor.toString().padStart(2, '0');
        }
        }
    </script>   
                             
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

