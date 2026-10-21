<%-- 
    Document   : ADMINDASHBOARD
    Created on : 16 may. 2025, 12:35:26 a. m.
    Author     : Fabrizio AD
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Virtual Store</title>
    <!-- Bootstrap Core CSS -->
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/plugins/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet">
    <link href="assets/plugins/chartist-js/dist/chartist.min.css" rel="stylesheet">
    <link href="assets/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.css" rel="stylesheet">
    <!--c3 CSS -->
    <link href="assets/plugins/c3-master/c3.min.css" rel="stylesheet">
    <link href="assets/plugins/toast-master/css/jquery.toast.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <!-- Dashboard 1 Page CSS -->
    <link href="main/css/pages/dashboard1.css" rel="stylesheet">
    <!-- You can change the theme colors from here -->
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="main/css/pTienda.css">
    <link rel="stylesheet" href="main/css/margin.css">
    <link href="main/scss/responsive.scss" id="theme" rel="stylesheet">
    <script src="main/js/nombreCliente.js"></script>
    <script src="main/js/panel.js"></script>
</head>

<body class="fix-header fix-sidebar card-no-border">
    <div class="preloader">
        <div class="loader">
            <div class="loader__figure"></div>
            <p class="loader__label">Virtual Store</p>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper">
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                        <a class="navbar-brand" href="index.html">
                            <img style="margin-left: 50px;" src="assets/images/ImagenesVirtualStore/StoreIcon.png" alt="" class="dark-logo"/>                            <!-- Light Logo icon -->
                        <img  alt="" class="dark-logo" />
                    </b>
                    <span><img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" class="dark-logo" alt="homepage" /></span> 
                </a>
                </div>
               <div class="navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link nav-toggler hidden-md-up waves-effect waves-dark" href="javascript:void(0)">
                                <i class="ti-menu"></i>
                            </a>
                        </li>
                        <li class="nav-item" style="margin-left: -62px;">
                            <a class="nav-link sidebartoggler hidden-sm-down waves-effect waves-dark" href="javascript:void(0)">
                                <i class="ti-menu"></i>
                            </a>
                        </li>
                    </ul>
                        <div class="ml-auto">
                         <%
                             HttpSession sesion = request.getSession(false);
                             clases.Usuario usuario = (sesion != null) ? (clases.Usuario) sesion.getAttribute("usuario") : null;
                         %>

                         <% if (usuario != null && "Administrador".equalsIgnoreCase(usuario.getRol())) { %>
                             <a href="svLogout" class="labelPersonalizado">Salir de la cuenta</a>
                         <% } else { %>
                             <a href="login.jsp" class="labelPersonalizado">Iniciar sesión</a>
                         <% } %>
                     </div>
                </div>
            </nav>
        </header>

        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
                     <ul id="sidebarnav">
                            <li class="user-profile">
                                <a>
                                    <%
                                        if (usuario != null && "Administrador".equalsIgnoreCase(usuario.getRol())) {
                                    %>
                                        <img src="assets/images/users/HombreGuapo.jpg" alt="Administrador autenticado"/>
                                        <span class="hide-menu" id="nombreAdmin"><%= usuario.getNombre() %> <%= usuario.getApaterno() %>(Admin)</span>
                                    <%
                                        } else {
                                    %>
                                        <img src="assets/images/users/Perfil.png" alt="Acceso no autorizado"/>
                                        <span class="hide-menu">Acceso restringido</span>
                                    <%
                                        }
                                    %>
                                </a>
                            </li>
                            <li class="nav-devider"></li>
                        <li>
                            <a href="TiendaMain.jsp" aria-expanded="false">
                                <i class="mdi mdi-store"></i>
                                <span class="hide-menu">Tienda</span>
                            </a>
                        </li>
                        <li><a href="cambioContrasena.jsp" aria-expanded="false">
                            <i class="mdi mdi-bullseye"></i><span class="hide-menu">Contraseña</span></a></li>
                        <li>
                            <a href="SvDatosCliente" aria-expanded="false">
                                <i class="mdi mdi-account-card-details"></i>
                                <span class="hide-menu">Datos del Cliente</span>
                            </a>
                        </li>
                        <li>
                            <a href="listarPedidos.jsp" aria-expanded="false">
                                <i class="mdi mdi-format-list-bulleted"></i>
                                <span class="hide-menu">Listado de Pedidos</span>
                            </a>
                        </li>
                        <li>
                            <a href="SvProductos" aria-expanded="false">
                                <i class="mdi mdi-package-variant"></i>
                                <span class="hide-menu">Datos de Productos</span>
                            </a>
                        </li>
                        <li>
                            <a href="registroProductos.jsp" aria-expanded="false">
                                <i class="mdi mdi-plus-box"></i>
                                <span class="hide-menu">RegistroDeProductos</span>
                            </a>
                        </li>
                        <li>
                            <a href="registroCliente.jsp" aria-expanded="false">
                                <i class="mdi mdi-account-plus"></i>
                                <span class="hide-menu">Registro de Clientes</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>
        
        <div class="page-wrapper">
        <!-- Container fluid -->
        <div class="container-fluid r-aside">
              <section id="inicio" >
                <article>
                    <a><img width="1260px" style="margin-left: -30px; margin-top: -30px; margin-bottom: 50px;" src="assets/images/ImagenesVirtualStore/Banner.jpg" alt="Imagen principal"></a>
                </article>
             </section>

            <!-- Título o encabezado -->
            <div class="row page-titles">
                <div class="col-md-7 align-self-center">
                    <div class="d-flex" style="margin-left: 130px;">
                        <div>
                            <h3 class="card-title m-b-5"><span class="lstick"></span>Bienvenido a Virtual Store</h3>
                        </div>
                    </div>
                </div>
            </div>

        <div class="row" style="margin-left: 100px;">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title" style="margin-bottom: 20px;">Nuestros Productos</h4>
                        <div class="row gx-5 gy-4">
                            <div class="col-md-3">
                                <div class="card">
                                    <img class="card-img-top" src="assets/images/ImagenesVirtualStore/refresco.jpg" alt="Coca Cola">
                                    <div class="card-body">
                                        <h5 class="card-title" style="text-align: center;">Coca Cola</h5>
                                        <p class="card-text" style="text-align: center;">Bebida en lata.<br>S/ 5.20</p>
                                        <a href="#" class="btn btn-primary" style="display: block; margin: 0 auto;" onclick="event.preventDefault(); openPanel(
                                            'Coca Cola',
                                            '../assets/images/ImagenesVirtualStore/refresco.jpg',
                                            'Tipo: Bebida\nPeso: 30.5 gramos\nTamaño: Mediano\nColor: Rojo',
                                            '',
                                            'https://static.vecteezy.com/system/resources/previews/020/190/670/non_2x/cocacola-logo-cocacola-icon-free-free-vector.jpg',
                                            'Precio: S/ 5.2 soles'
                                        )">Ver más</a>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="card">
                                    <img class="card-img-top" src="assets/images/ImagenesVirtualStore/aceite.jpg" alt="Aceite Vegetal">
                                    <div class="card-body">
                                        <h5 class="card-title" style="text-align: center;">Aceite Vegetal</h5>
                                        <p class="card-text" style="text-align: center;">Aceite cocina.<br>S/ 8.70</p>
                                        <a href="#" class="btn btn-primary" style="display: block; margin: 0 auto;" onclick="event.preventDefault(); openPanel(
                                            'Aceite Vegetal',
                                            '../assets/images/ImagenesVirtualStore/aceite.jpg',
                                            'Tipo: Aceites\nPeso: 220 gramos\nTamaño: Mediano\nColor: Amarillo',
                                            '',
                                            'https://plazavea.vteximg.com.br/arquivos/PRIMOR_logo.jpg',
                                            'Precio: S/ 8.7 soles'
                                        )">Ver más</a>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="card">
                                    <img class="card-img-top" src="assets/images/ImagenesVirtualStore/pescado.jpg" alt="Atún en lata">
                                    <div class="card-body">
                                        <h5 class="card-title" style="text-align: center;">Atún en lata</h5>
                                        <p class="card-text" style="text-align: center;">Atún enlatado.<br>S/ 5.50</p>
                                        <a href="#" class="btn btn-primary" style="display: block; margin: 0 auto;" onclick="event.preventDefault(); openPanel(
                                            'Atún en lata',
                                            '../assets/images/ImagenesVirtualStore/pescado.jpg',
                                            'Tipo: Enlatado\nPeso: 215 gramos\nTamaño: Pequeño\nColor: Azul',
                                            '',
                                            'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTd959Ey__fDOi9fqnaUXKitrc-T0TJwodBqw&s',
                                            'Precio: S/ 5.5 soles'
                                        )">Ver más</a>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="card">
                                    <img class="card-img-top" src="assets/images/ImagenesVirtualStore/leche.jpg" alt="Leche en caja">
                                    <div class="card-body">
                                        <h5 class="card-title" style="text-align: center;">Leche en caja</h5>
                                        <p class="card-text" style="text-align: center;">Bebida láctea.<br>S/ 6.00</p>
                                        <a href="#" class="btn btn-primary" style="display: block; margin: 0 auto;" onclick="event.preventDefault(); openPanel(
                                            'Leche en caja',
                                            '../assets/images/ImagenesVirtualStore/leche.jpg',
                                            'Tipo: Bebida\nPeso: 1 litro\nTamaño: Mediano\nColor: Blanco',
                                            '',
                                            'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStLg7luNGU2puMy73YDd5lFDg4OSE37gy0Ig&s',
                                            'Precio: S/ 6.0 soles'
                                        )">Ver más</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Sección Nosotros -->
        <div class="row page-titles">
            <div class="col-md-12">
                <div class="d-flex" style="margin-left: 130px;">
                    <div>
                        <h3 class="card-title m-b-5"><span class="lstick"></span>Nosotros</h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin-left: 100px;">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col-md-6">
                                <h4 class="card-title">¿Quiénes Somos?</h4>
                                <p style="text-align: justify;">
                                    En <strong>Virtual Store</strong> nos dedicamos a llevar hasta tu hogar los mejores productos de abarrotes,
                                    con calidad garantizada y precios accesibles. Nuestra tienda virtual nace con el compromiso de hacer tus
                                    compras más fáciles, seguras y rápidas, para que dediques tu tiempo a lo que realmente importa.
                                </p>
                            </div>
                            <div class="col-md-6 text-center">
                                <img src="assets/images/ImagenesVirtualStore/TiendaVirtualLogoCalidad.png" alt="Nosotros" style="width: 100%; max-width: 400px; border-radius: 10px;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin-left: 100px;">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title text-center">Misión</h4>
                        <p style="text-align: center;">
                            Ofrecer una experiencia de compra ágil, confiable y cercana, brindando productos de primera necesidad con un
                            excelente servicio al cliente en todo momento.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title text-center">Visión</h4>
                        <p style="text-align: center;">
                            Ser la tienda virtual líder en distribución de abarrotes a nivel nacional, reconocida por su eficiencia,
                            innovación y compromiso con las familias peruanas.
                        </p>
                    </div>
                </div>
            </div>
        </div>


        <!-- Opiniones de clientes -->
        <div class="row" style="margin-left: 100px;">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Opiniones de Clientes</h4>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body">
                                        <img class="card-img-top" src="assets/images/users/MariaT.jpg" alt="user" style="margin-bottom: 30px;">
                                        <h5 class="card-title" style="font-weight: bold;">María T.</h5>
                                        <p class="card-text" style="font-weight: bold; text-align: center;">“Todo llega fresco y a tiempo, excelente servicio.”</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body">
                                        <img class="card-img-top" src="assets/images/users/JoseR.jpg" alt="user" style="margin-bottom: 30px;">
                                        <h5 class="card-title" style="font-weight: bold;">José R.</h5>
                                        <p class="card-text" style="font-weight: bold; text-align: center;">“Muy buenos precios, buena variedad y atención rápida.”</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body">
                                        <img class="card-img-top" src="assets/images/users/LuciaP.jpg" alt="user" style="margin-bottom: 30px;">
                                        <h5 class="card-title" style="font-weight: bold;">Lucía P.</h5>
                                        <p class="card-text" style="font-weight: bold; text-align: center;">“Comprar desde casa nunca fue tan fácil. Recomendado.”</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

          <!-- Proveedores de productos -->
        <div class="brand-area">
            <div class="container">
                <div class="row page-titles">
                    <div class="col-md-12">
                        <div class="d-flex" style="margin-left: 110px;">
                            <div>
                                <h3 class="card-title m-b-5"><span class="lstick"></span>Proveedores</h3>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row text-center" style="margin-top: 30px; margin-left: 50px;">
                    <!-- Coca-Cola -->
                    <div class="col-md-4 mb-4">
                        <img src="https://static.vecteezy.com/system/resources/previews/020/190/670/non_2x/cocacola-logo-cocacola-icon-free-free-vector.jpg" alt="Coca-Cola" class="img-fluid" style="max-height: 180px;">
                    </div>
                    <!-- D'gussto -->
                    <div class="col-md-4 mb-4">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTd959Ey__fDOi9fqnaUXKitrc-T0TJwodBqw&s" alt="D'gussto" class="img-fluid" style="max-height: 180px;">
                    </div>
                    <!-- Faraón -->
                    <div class="col-md-4 mb-4">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUicycRMot-D1yAYPUsyitqgp4HfwlAfxlrQ&s" alt="Arroz Faraón" class="img-fluid" style="max-height: 180px;">
                    </div>
                    <!-- Paracas -->
                    <div class="col-md-4 mb-4">
                        <img src="https://d1yjjnpx0p53s8.cloudfront.net/styles/logo-thumbnail/s3/092013/papel_paracas.png?itok=QIQNxqq2" alt="Paracas" class="img-fluid" style="max-height: 180px;">
                    </div>
                    <!-- Primor -->
                    <div class="col-md-4 mb-4">
                        <img src="https://plazavea.vteximg.com.br/arquivos/PRIMOR_logo.jpg" alt="Aceite Primor" class="img-fluid" style="max-height: 180px;">
                    </div>
                    <!-- Gloria -->
                    <div class="col-md-4 mb-4">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStLg7luNGU2puMy73YDd5lFDg4OSE37gy0Ig&s" alt="Leche Gloria" class="img-fluid" style="max-height: 180px; max-width: 190px; margin-top: 30px;">
                    </div>
                </div>
            </div>
        </div>

      <!-- Sección de Contacto -->
        <div class="row page-titles">
            <div class="col-md-12">
                <div class="d-flex" style="margin-left: 130px;">
                    <div>
                        <h3 class="card-title m-b-5"><span class="lstick"></span>Contáctanos</h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin-left: 100px;">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <form>
                            <h4 class="card-title" style="margin-bottom: 20px;">Formulario de Contacto</h4>
                            <div class="row">
                                <!-- Número de Documento -->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="nDocumento">N° de Documento:</label>
                                        <input type="text" class="form-control" id="nDocumento" name="nDocumento" required>
                                    </div>
                                </div>
                                <!-- Teléfono -->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="telefono">Teléfono:</label>
                                        <input type="tel" class="form-control" id="telefono" name="telefono" required>
                                    </div>
                                </div>
                                <!-- Correo -->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="correo">Correo electrónico:</label>
                                        <input type="email" class="form-control" id="correo" name="correo" required>
                                    </div>
                                </div>
                                <!-- Fecha -->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="fecha">Fecha:</label>
                                        <input type="date" class="form-control" id="fecha" name="fecha" required>
                                    </div>
                                </div>
                                <!-- Descripción -->
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="descripcion">Consulta / Duda:</label>
                                        <textarea class="form-control" id="descripcion" name="descripcion" rows="5" required></textarea>
                                    </div>
                                </div>
                                <!-- Botón -->
                                <div class="col-md-12 text-center">
                                    <button type="submit" class="btn btn-success">Enviar</button>
                                </div>
                            </div> <!-- /.row -->
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="product-panel" class="product-panel">
            <div class="product-panel-content">
                <span class="close-btn" onclick="closePanel()">×</span>
                <h4 id="panel-title"></h4>
                <img id="panel-img" src="" alt="" style="width: 100%; max-height: 200px; object-fit: contain; margin-bottom: 10px;">
                <pre id="panel-description" style="white-space: pre-wrap;"></pre>
                <p id="panel-brand-name" style="font-weight: bold;"></p>
                <img id="panel-brand-logo" src="" alt="Logo de marca" style="max-width: 100px; margin-bottom: 10px;">
                <p id="panel-price" style="font-weight: bold; font-size: 16px;"></p>
            </div>
        </div>


         </div>
            <footer class="footer" style="text-align: center; font-weight: bold;"> © 2025 Virtual Store </footer>
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
    <!-- This page plugins -->
    <script src="assets/plugins/sparkline/jquery.sparkline.min.js"></script>
    <script src="assets/plugins/chartist-js/dist/chartist.min.js"></script>
    <script src="assets/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.min.js"></script>
    <script src="assets/plugins/d3/d3.min.js"></script>
    <script src="assets/plugins/c3-master/c3.min.js"></script>
    <script src="assets/plugins/toast-master/js/jquery.toast.js"></script>
    <script src="main/js/dashboard1.js"></script>
    <script src="assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
</body>

</html>