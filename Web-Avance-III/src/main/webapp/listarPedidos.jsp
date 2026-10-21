<%-- 
    Document   : listarPedidos
    Created on : 18 may. 2025, 11:10:14?p. m.
    Author     : Fabrizio AD
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="clases.ConexionBD"%>

<%
    ConexionBD cnn;
    String SQL_SELECT = "SELECT p.idpedido, p.fechapedido, u.nombre AS cliente, pr.nombre AS producto, " +
                        "dp.cantidad, dp.subtotal, dp.igv, dp.totalpago, pg.monto AS monto_pagado, " +
                        "pg.metodopago, pg.cambio " +
                        "FROM pedido p " +
                        "JOIN usuarios u ON p.idusuario = u.idusuario " +
                        "JOIN detallepedido dp ON dp.idpedido = p.idpedido " +
                        "JOIN productos pr ON pr.idproducto = dp.idproducto " +
                        "JOIN pago pg ON pg.idpago = dp.idpago " +
                        "ORDER BY p.idpedido DESC";
    ResultSet registros;
    PreparedStatement sentencia;
%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedidos</title>

    <!-- Estilos y librerías base -->
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/plugins/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet">
    <link href="assets/plugins/c3-master/c3.min.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/pages/dashboard2.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="main/css/margin.css">

    <style>
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

    .pedido-card {
        background-color: #fff;
        border: 1px solid #ccc;
        padding: 16px;
        margin: 16px auto;
        width: 80%;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        font-weight: bold;
    }

    .producto-info {
        margin-left: 20px;
        padding: 10px;
        background-color: #f9f9f9;
        border-left: 3px solid #007bff;
        margin-top: 10px;
    }

    .label {
        font-weight: bold;
    }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body class="fix-header fix-sidebar card-no-border">
    <!-- Preloader -->
    <div class="preloader">
        <div class="loader">
            <div class="loader__figure"></div>
            <p class="loader__label">Proceso tienda</p>
        </div>
    </div>

    <div id="main-wrapper">
        <!-- Topbar -->
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

        <!-- Sidebar -->
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
                        <li><a href="TiendaMain.jsp" aria-expanded="false">
                            <i class="mdi mdi-store"></i><span class="hide-menu">Tienda</span></a></li>
                            <li><a href="cambioContrasena.jsp" aria-expanded="false">
                            <i class="mdi mdi-bullseye"></i><span class="hide-menu">Contraseña</span></a></li>
                            <li><a href="DatosCliente.jsp" aria-expanded="false">
                            <i class="mdi mdi-file"></i><span class="hide-menu">Datos del cliente</span></a></li>
                            <li><a href="listarPedidos.jsp"><i class="mdi mdi-file"></i><span class="hide-menu">Listado de Pedidos</span></a></li>
                        </ul>
                    </ul>
                </nav>
            </div>
        </aside>

        <!-- Contenido -->
            <div class="page-wrapper">
                <div class="container-fluid">
                    <h1>Lista de Pedidos</h1>
                    <div class="table-container">
                        <div class="table-responsive">
                            <%
                                try {
                                    cnn = new ConexionBD();
                                    sentencia = cnn.Conectar().prepareStatement(SQL_SELECT);
                                    registros = sentencia.executeQuery();

                                    int pedidoActual = -1;
                                    double totalPedido = 0.0;
                                    double montoPagado = 0.0;
                                    double cambio = 0.0;

                                    while (registros.next()) {
                                        int idPedido = registros.getInt("idpedido");

                                        if (idPedido != pedidoActual) {
                                            if (pedidoActual != -1) {
                            %>
                                                <!-- Formulario para el pedido anterior -->
                                                <form method="post" action="GenerarFacturaServlet" target="_blank">
                                                    <input type="hidden" name="idpedido" value="<%= pedidoActual %>">
                                                    <input type="hidden" name="total" value="<%= totalPedido %>">
                                                    <input type="hidden" name="recibido" value="<%= montoPagado %>">
                                                    <input type="hidden" name="cambio" value="<%= cambio %>">
                                                    <input type="hidden" name="moneda" value="Soles">
                                                    <input type="hidden" name="empresa" value="Empresa VirtualStore">
                                                    <button type="submit" class="btn btn-primary">Descargar Factura</button>
                                                </form>
                                            </div> <!-- Cierra el pedido anterior -->
                            <%
                                            }
                                            pedidoActual = idPedido;

                                            // Actualizar montos para el nuevo pedido
                                            totalPedido = registros.getDouble("totalpago");
                                            montoPagado = registros.getDouble("monto_pagado");
                                            cambio = registros.getDouble("cambio");
                            %>
                                            <div class="pedido-card">
                                                <h2>Pedido N° <%= idPedido %></h2>
                                                <p><span class="label">Fecha:</span> <%= registros.getString("fechapedido") %></p>
                                                <p><span class="label">Cliente:</span> <%= registros.getString("cliente") %></p>
                                                <p><span class="label">Método de pago:</span> <%= registros.getString("metodopago") %></p>
                                                <p><span class="label">Monto pagado:</span> S/ <%= montoPagado %></p>
                                                <p><span class="label">Cambio:</span> S/ <%= cambio %></p>
                                                <p><span class="label">Productos:</span></p>
                            <%
                                        } else {
                                            // Actualizar montos en caso haya más de un producto del mismo pedido
                                            totalPedido = registros.getDouble("totalpago");
                                            montoPagado = registros.getDouble("monto_pagado");
                                            cambio = registros.getDouble("cambio");
                                        }
                            %>
                                            <div class="producto-info">
                                                <p><span class="label">Producto:</span> <%= registros.getString("producto") %></p>
                                                <p><span class="label">Cantidad:</span> <%= registros.getInt("cantidad") %></p>
                                                <p><span class="label">Subtotal:</span> S/ <%= registros.getDouble("subtotal") %></p>
                                                <p><span class="label">IGV:</span> S/ <%= registros.getDouble("igv") %></p>
                                                <p><span class="label">Total:</span> S/ <%= registros.getDouble("totalpago") %></p>
                                            </div>
                            <%
                                    }

                                    if (pedidoActual != -1) {
                            %>
                                        <!-- Formulario para el último pedido -->
                                        <form method="post" action="GenerarFacturaServlet" target="_blank">
                                            <input type="hidden" name="idpedido" value="<%= pedidoActual %>">
                                            <input type="hidden" name="total" value="<%= totalPedido %>">
                                            <input type="hidden" name="recibido" value="<%= montoPagado %>">
                                            <input type="hidden" name="cambio" value="<%= cambio %>">
                                            <input type="hidden" name="moneda" value="Soles">
                                            <input type="hidden" name="empresa" value="Empresa VirtualStore">
                                            <button type="submit" class="btn btn-primary" style="margin-top: 20px">Descargar Factura</button>
                                        </form>
                                    </div> <!-- Cierra el último pedido -->
                            <%
                                    }

                                    cnn.Desconectar();
                                } catch (Exception e) {
                                    e.printStackTrace();
                            %>
                                    <p style="color:red; text-align:center;">Error al recuperar los pedidos.</p>
                            <%
                                }
                            %>
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
