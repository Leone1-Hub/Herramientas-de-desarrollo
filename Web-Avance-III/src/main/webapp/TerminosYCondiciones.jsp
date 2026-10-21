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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Virtual Store</title>
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/images/favicon.png">

    <!-- Estilos -->
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

    <!-- Scripts -->
    <script src="main/js/nombreCliente.js"></script>
    <script src="main/js/pTienda.js"></script>
</head>

<body class="fix-header fix-sidebar card-no-border">
    <div class="preloader">
        <div class="loader">
            <div class="loader__figure"></div>
            <p class="loader__label">Proceso de compras</p>
        </div>
    </div>

    <div id="main-wrapper">
        <header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.jsp">
                        <img style="margin-left: 50px;" src="assets/images/ImagenesVirtualStore/StoreIcon.png" alt="logo" class="dark-logo"/>
                        <span>
                            <img style="margin-left: -5px;" src="assets/images/ImagenesVirtualStore/LetritasLogo.png" class="dark-logo" alt="Virtual Store Logo" />
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

        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles">
                    <div class="col-md-5 align-self-center">
                        <h3 class="text-themecolor">Proceso de Compras</h3>
                    </div>
                    <div class="col-md-7 align-self-center">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Inicio</a></li>
                            <li class="breadcrumb-item active">Tienda</li>
                        </ol>
                    </div>
                </div>

                <!-- Contenido -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="card-title m-b-5"><span class="lstick"></span>Compras en línea</h3>
                            </div>

                            <div class="card-body container1">
                                <div class="text-center">
                                        <h4>Términos y condiciones :</h4> <br>
                                        <p style="text-align: justify;">
                                            Al acceder y utilizar este sitio web, aplicación o servicio (en adelante, "el Servicio"), usted acepta cumplir y estar sujeto a los siguientes Términos y Condiciones de Uso (en adelante, "Términos"). Si no está de acuerdo con estos Términos, le rogamos que no utilice el Servicio.Nos reservamos el derecho de modificar, actualizar o cambiar estos Términos en cualquier momento y a nuestra entera discreción. Le notificaremos sobre cualquier cambio publicando los nuevos Términos en el Servicio y actualizando la "Última actualización" en la parte superior. Es su responsabilidad revisar estos Términos periódicamente. Su uso continuado del Servicio después de la publicación de cambios constituirá su aceptación de dichos cambios. El uso del Servicio es únicamente para fines legales y de acuerdo con estos Términos. Usted se compromete a no utilizar el Servicio para:
                                            Violar cualquier ley, reglamento o normativa aplicable.
                                            Enviar o distribuir contenido ilegal, ofensivo, difamatorio, engañoso, o que infrinja derechos de propiedad intelectual de terceros. El uso del Servicio es únicamente para fines legales y de acuerdo con estos Términos. Usted se compromete a no utilizar el Servicio para:
                                            Violar cualquier ley, reglamento o normativa aplicable.
                                            Enviar o distribuir contenido ilegal, ofensivo, difamatorio, engañoso, o que infrinja derechos de propiedad intelectual de terceros. Para acceder a ciertas funciones del Servicio, es posible que deba crear una cuenta. Usted es responsable de mantener la confidencialidad de su cuenta y contraseña, y acepta notificar de inmediato cualquier uso no autorizado de su cuenta. Nos reservamos el derecho de suspender o cancelar su cuenta en caso de incumplimiento de estos Términos.En la medida máxima permitida por la ley aplicable, no seremos responsables de ningún daño directo, indirecto, incidental, especial, consecuencial, o punitivo, incluyendo, sin limitación, pérdida de beneficios, datos, uso, o cualquier otra pérdida intangible, resultante del uso o la imposibilidad de usar el Servicio.
                                        </p>
                                </div>
                            </div>
                            <!-- Formulario para aceptar términos -->
                            <form action="TiendaMain.jsp" method="POST" class="text-center mb-4">
                                <button type="submit" class="btn btn-primary">Aceptar</button>
                            </form>
                        </div>
                    </div>
                </div>

            </div>

            <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
        </div>
    </div>

    <!-- JS -->
    <script src="assets/plugins/jquery/jquery.min.js"></script>
    <script src="assets/plugins/bootstrap/js/popper.min.js"></script>
    <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="main/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="main/js/waves.js"></script>
    <script src="main/js/sidebarmenu.js"></script>
    <script src="main/js/custom.min.js"></script>
    <script src="assets/plugins/chartist-js/dist/chartist.min.js"></script>
    <script src="assets/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.min.js"></script>
    <script src="assets/plugins/d3/d3.min.js"></script>
    <script src="assets/plugins/c3-master/c3.min.js"></script>
    <script src="assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
</body>

</html>
