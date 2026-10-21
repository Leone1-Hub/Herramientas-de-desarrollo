<%-- 
    Document   : editar_usuario
    Created on : 16 jun 2025, 1:54:00 p.m.
    Author     : Fabrizio
--%>

<%@page import="clases.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>

    <!-- CSS de plantilla -->
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
        <!-- Topbar -->
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

        <!-- Sidebar -->
        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="user-profile"> 
                            <a><img src="assets/images/icon/staff.png" alt="user" /><span class="hide-menu">Administrador</span></a>
                        </li>
                        <li class="nav-devider"></li>    
                        <li><a href="listarUsuarios.jsp" aria-expanded="false">
                            <i class="mdi mdi-account-edit"></i><span class="hide-menu">Usuarios</span></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>

        <!-- Contenido principal -->
        <div class="page-wrapper">
            <div class="container-fluid">
                <div class="row page-titles">
                    <div class="col-md-12 text-center">
                        <h2 class="card-title">Editar Usuario</h2>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-lg-8">

                        <%
                            Usuario usu = (Usuario) session.getAttribute("usuEditar");
                            if (usu == null) {
                        %>
                            <div class="alert alert-danger mt-4">
                                No se encontró el usuario para editar.
                            </div>
                            <a href="listarUsuarios.jsp" class="btn btn-secondary mt-3">Volver</a>
                        <%
                            } else {
                        %>
                              
                        <div class="card">
                            <div class="card-body">
                                <form action="svEditar" method="POST">
                                   <input type="hidden" name="idusuario" value="<%= usu.getIdusuario() %>">

                                    <div class="form-group">
                                        <label>Número Documento:</label>
                                        <input type="text" name="nDocumento" class="form-control" value="<%= usu.getnDocumento() %>" readonly>
                                    </div>

                                    <div class="form-group">
                                        <label>Nombre:</label>
                                        <input type="text" name="nombre" class="form-control" value="<%= usu.getNombre() %>" readonly>
                                    </div>

                                    <div class="form-group">
                                        <label>Apellido Paterno:</label>
                                        <input type="text" name="apaterno" class="form-control" value="<%= usu.getApaterno() %>" readonly>
                                    </div>

                                    <div class="form-group">
                                        <label>Apellido Materno:</label>
                                        <input type="text" name="amaterno" class="form-control" value="<%= usu.getAmaterno() %>" readonly>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Fecha de Nacimiento:</label>
                                        <input type="date" name="fechaN" class="form-control" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(usu.getFechaN()) %>" readonly>
                                    </div>

                                    <div class="form-group">
                                        <label>Nacionalidad:</label>
                                        <input type="text" name="nacionalidad" class="form-control" value="<%= usu.getNacionalidad() %>" readonly>
                                    </div>

                                    <div class="form-group">
                                        <label>Teléfono:</label>
                                        <input type="text" name="telefono" class="form-control" value="<%= usu.getTelefono() %>" required>
                                    </div>

                                    <div class="form-group">
                                        <label>Correo:</label>
                                        <input type="email" name="correo" class="form-control" value="<%= usu.getCorreo() %>" required>
                                    </div>
                         
                                    <div class="form-group">
                                        <label>Departamento:</label>
                                        <input type="text" name="departamento" class="form-control" value="<%= usu.getDepartamento() %>" required>
                                    </div>

                                    <div class="form-group">
                                        <label>Provincia:</label>
                                        <input type="text" name="provincia" class="form-control" value="<%= usu.getProvincia() %>" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Distrito:</label>
                                        <input type="text" name="distrito" class="form-control" value="<%= usu.getDistrito() %>" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Dirección:</label>
                                        <input type="text" name="direccion" class="form-control" value="<%= usu.getDireccion() %>" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Rol:</label>
                                        <input type="text" name="rol" class="form-control" value="<%= usu.getRol() %>" readonly>
                                    </div>
                                    
                                     <% String error = (String) request.getAttribute("errorEditar"); %>
                                    <% if (error != null) { %>
                                        <div class="alert alert-danger text-center font-weight-bold">
                                            <%= error %>
                                        </div>
                                    <% } %>
                                    
                                    <div class="form-group text-center">
                                        <button type="submit" class="btn btn-primary mx-2" style="width: 45%;">Guardar Cambios</button>
                                        <a href="listarUsuarios.jsp" class="btn btn-info" style="width: 45%;">Cancelar</a>
                                    </div>
                                </form>          
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <!-- Footer -->
            <footer class="footer text-center font-weight-bold">© 2025 Virtual Store</footer>
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
