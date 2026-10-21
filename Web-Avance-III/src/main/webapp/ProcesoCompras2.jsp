<%-- 
    Document   : ProcesoCompras2
    Created on : 16 may. 2025, 12:35:26 a. m.
    Author     : Fabrizio AD
--%>

<%@page import="java.text.Normalizer"%>
<%@page import="java.text.DecimalFormat"%>
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
    <script src="main/js/pTienda.js"></script>
</head>

        <%@ page import="java.util.*, clases.Producto, clases.ProductoDAO" %>
        <%
            List<Producto> carrito = new ArrayList<>();
            Set<Integer> productosAgregados = new HashSet<>();
            ProductoDAO dao = new ProductoDAO();

            String[] ids = request.getParameterValues("producto_id");

            if (ids != null) {
                for (String idStr : ids) {
                    int id = Integer.parseInt(idStr);
                    String nombre = request.getParameter("producto_nombre_" + id);
                    String precioStr = request.getParameter("producto_precio_" + id);
                    String cantidadStr = request.getParameter("cantidad_" + id);

                    if (cantidadStr != null && !cantidadStr.isEmpty()) {
                        int cantidad = Integer.parseInt(cantidadStr);
                        if (cantidad > 0) {
                            double precio = Double.parseDouble(precioStr);

                            String nombreSinTildes = Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
                            Producto producto = dao.obtenerPorNombre(nombreSinTildes);

                            if (producto != null) {
                                if (!productosAgregados.contains(producto.getIdproducto())) {
                                    if (cantidad <= producto.getStock()) {
                                        double subtotal = cantidad * precio;
                                        producto.setCantidad(cantidad);
                                        producto.setSubtotal(subtotal);
                                        carrito.add(producto);
                                        productosAgregados.add(producto.getIdproducto());
                                    } else {
                                        session.setAttribute("errorStock", "No hay suficiente stock para: " + nombre);
                                    }
                                }
                            } else {
                                session.setAttribute("errorStock", "Producto no encontrado: " + nombre);
                            }
                        }
                    }
                }
            }

            session.setAttribute("carrito", carrito);
        %>

        <%
        DecimalFormat df = new DecimalFormat("#0.00");
        double total = 0;
        for (Producto p : carrito) {
            total += p.getSubtotal();      
        }
        %>

    <body class="fix-header fix-sidebar card-no-border">
        <div class="preloader">
            <div class="loader">
                <div class="loader__figure"></div>
                <p class="loader__label">Proceso Tienda</p>
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
                        <li><a href="TiendaMain.jsp" aria-expanded="false">
                            <i class="mdi mdi-store"></i><span class="hide-menu">Tienda</span></a></li>
                            <li><a href="cambioContrasena.jsp" aria-expanded="false">
                            <i class="mdi mdi-bullseye"></i><span class="hide-menu">Contraseña</span></a></li>
                            <li><a href="DatosCliente.jsp" aria-expanded="false">
                            <i class="mdi mdi-file"></i><span class="hide-menu">Datos del cliente</span></a></li>
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

                <!-- Página de pago -->
                <div class="row justify-content-center">
                    <div class="col-12 col-md-8">
                        <div class="card shadow p-4">
                            <div class="card-body">
                                <div class="text-center mb-4">
                                    <h3 class="card-title" style="margin-right: 520px"><span class="lstick"></span>Compras en línea </h3>
                                </div>
                                
                                <% if (session.getAttribute("errorStock") != null) { %>
                                    <div class="alert alert-danger mt-3">
                                        <pre><%= session.getAttribute("errorStock") %></pre>
                                    </div>
                                    <% session.removeAttribute("errorStock"); %>
                                <% } %>

                                <form action="GenerarFacturaServlet" method="post">
                                    <h1 class="titulo text-center mb-4">TOTAL A PAGAR:</h1>

                                    <div class="mb-3 text-center">
                                        <input type="hidden" name="total" value="<%= df.format(total) %>">
                                        <span id="totalPagar" class="monto fs-4 fw-bold">S/ <%= df.format(total) %></span>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">RECIBIDO:</label>
                                        <input type="number" id="recibido" name="recibido" class="form-control" step="0.01" required />
                                    </div>

                                    <!-- Moneda -->
                                    <div class="mb-3 d-flex align-items-center justify-content-between">
                                        <select id="moneda" name="moneda" class="form-select w-50 me-2">
                                            <option value="SOLES">SOLES</option>
                                            <option value="DOLARES">DOLARES</option>
                                        </select>
                                        <button type="button" class="btn btn-success" onclick="calcularCambio()">CALCULAR</button>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">CAMBIO:</label>
                                        <input type="hidden" name="cambio" id="cambioInput" value="0.00" />
                                        <span id="cambio" class="monto fs-5">S/ 0.00</span>
                                    </div>

                                    <!-- Empresa -->
                                    <div class="mb-4">
                                        <label class="form-label">EMPRESA:</label>
                                        <select class="form-select" name="empresa">
                                            <option>&lt;&lt;SELECCIONA&gt;&gt;</option>
                                            <option>DHL</option>
                                            <option>FedEX</option>
                                        </select>
                                    </div>

                                    <div class="text-center">
                                        <button class="btn btn-warning me-2" type="button" onclick="Estado()">ESTADO</button>
                                        <button type="button" class="btn btn-danger me-2" onclick="window.location.href='TiendaMain.jsp'">Atrás</button>
                                        <%
                                            boolean hayErrorStock = session.getAttribute("errorStock") != null;
                                            boolean usuarioLogeado = (usuario != null);
                                            boolean desactivarBoton = hayErrorStock || !usuarioLogeado;
                                        %>
                                        <button type="submit" class="btn btn-primary" id="btnCerrarVenta" <%= desactivarBoton ? "disabled" : "" %>>Cerrar Venta</button> 
                                    </div>
                                            <div class="resumen-carrito mt-4">
                                                <h4>Resumen del carrito:</h4>
                                                <ul>
                                                    <% for (Producto p : carrito) { %>
                                                        <li>
                                                            <%= p.getNombre() %> x <%= p.getCantidad() %> = S/ <%= df.format(p.getSubtotal()) %>
                                                        </li>
                                                    <% } %>
                                                </ul>
                                                <h4>Total a pagar: <span style="color: green;">S/ <%= df.format(total) %></span></h4>
                                            </div>
                                                <%
                                                for (Producto p : carrito) {
                                                    int nuevoStock = p.getStock() - p.getCantidad();
                                                    dao.actualizarStock(p.getIdproducto(), nuevoStock);
                                                }
                                            %>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                           <script>
                                function calcularCambio() {
                                    const recibidoInput = document.getElementById("recibido").value;
                                    const totalText = document.getElementById("totalPagar").innerText;
                                    const moneda = document.getElementById("moneda").value;
                                    const cambioSpan = document.getElementById("cambio");
                                    const btnCerrarVenta = document.getElementById("btnCerrarVenta");

                                    // Limpiar símbolos y convertir a número
                                    const totalNumerico = parseFloat(totalText.replace(/[^0-9.,]/g, '').replace(',', '.'));
                                    const recibido = parseFloat(recibidoInput);

                                    if (isNaN(recibido) || recibido <= 0) {
                                        alert("Ingrese un monto recibido válido.");
                                        cambioSpan.innerText = "S/ 0.00";
                                        btnCerrarVenta.disabled = true;
                                        return;
                                    }

                                    let cambio = 0;

                                    if (moneda === "SOLES") {
                                        cambio = recibido - totalNumerico;
                                    } else if (moneda === "DOLARES") {
                                        const tasa = 3.71;
                                        cambio = (recibido * tasa) - totalNumerico;
                                    }

                                    if (cambio < 0) {
                                        alert("Monto insuficiente. Faltan S/ " + Math.abs(cambio).toFixed(2));
                                        cambioSpan.innerText = "S/ 0.00";
                                        btnCerrarVenta.disabled = true;
                                    } else {
                                        cambioSpan.innerText = "S/ " + cambio.toFixed(2);
                                        document.getElementById("cambioInput").value = cambio.toFixed(2);
                                        btnCerrarVenta.disabled = false;
                                    }
                                }
                            </script>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
        </div>
    </div>
                        
    <style>
        select.form-select {
            background-color: #f8f9fa;
            color: #333;
            border: 1px solid #ced4da;
            border-radius: 8px;
            padding: 10px;
            font-weight: 500;
            transition: all 0.2s ease-in-out;
        }

        select.form-select:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            background-color: #fff;
        }

        select.form-select option {
            background-color: #fff;
            color: #333;
            padding: 10px;
        }

        select.form-select option:hover {
            background-color: #e9ecef;
            color: #000;
        }
    </style>
    
     <!-- JS Scripts -->
    <script src="assets/plugins/jquery/jquery.min.js"></script>
    <script src="assets/plugins/bootstrap/js/popper.min.js"></script>
    <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="main/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="main/js/waves.js"></script>
    <script src="main/js/custom.min.js"></script>
    <script src="assets/plugins/chartist-js/dist/chartist.min.js"></script>
    <script src="assets/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.min.js"></script>
    <script src="assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
</body>
</html>
