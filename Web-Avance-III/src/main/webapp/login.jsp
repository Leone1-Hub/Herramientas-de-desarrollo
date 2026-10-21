<%-- 
    Document   : index
    Created on : 16 may. 2025, 12:35:26 a. m.
    Author     : Fabrizio AD
--%>

<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="clases.ConexionBD" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=0.9">
    <title>Virtual Store</title>
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="main/css/pages/login-register-lock.css" rel="stylesheet">
    <link href="main/css/style.css" rel="stylesheet">
    <link href="main/css/colors/default-dark.css" id="theme" rel="stylesheet">
    <link href="main/scss/responsive.scss" id="theme" rel="stylesheet">
</head>
<body>

<%
    String mensaje = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String documento = request.getParameter("nDocumento");
        String contra = request.getParameter("password");
        String rolSeleccionado = request.getParameter("rol");

        ConexionBD cnn = new ConexionBD();

        try {
            Connection con = cnn.Conectar();
            if (con != null) {

                // Paso 1: Verificar si el documento existe
                PreparedStatement checkStmt = con.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE nDocumento = ?");
                checkStmt.setString(1, documento);
                ResultSet checkRs = checkStmt.executeQuery();
                checkRs.next();
                int count = checkRs.getInt(1);

                if (count == 0) {
                    mensaje = "El usuario no está registrado.";
                } else {
                    // Paso 2: Ejecutar procedimiento almacenado para validar login
                    String SQL = "{CALL validar_login(?, ?, ?)}";
                    CallableStatement stmt = con.prepareCall(SQL);
                    stmt.setString(1, documento);
                    stmt.setString(2, contra);
                    stmt.setString(3, rolSeleccionado);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        session.setAttribute("idusuario", rs.getInt("idusuario"));
                        session.setAttribute("nombre", rs.getString("nombre"));
                        session.setAttribute("rol", rs.getString("rol"));

                        if ("usuario".equalsIgnoreCase(rolSeleccionado)) {
                            response.sendRedirect("pMain.jsp");
                        } else {
                            response.sendRedirect("adminDashboard.jsp");
                        }
                    } else {
                        mensaje = "Contraseña o rol incorrecto. Intenta nuevamente.";
                    }

                    stmt.close();
                    rs.close();
                }

                checkStmt.close();
                checkRs.close();
                cnn.Desconectar();
            } else {
                mensaje = "Error de conexión con la base de datos.";
            }
        } catch (Exception e) {
            mensaje = "Error en el login: " + e.getMessage();
        }
    }
%>


<section id="wrapper" class="login-register login-sidebar" style="background-image:url(assets/images/ImagenesVirtualStore/Fondo.jpeg);">
    <div class="login-box card">
        <div class="card-body">
            <form method="post" action="svLogin" class="form-horizontal form-material" id="form-login">
                <a href="javascript:void(0)" class="text-center db">
                    <img src="assets/images/ImagenesVirtualStore/TiendaVirtualLogo.png" /><br/>
                </a>
                <div class="form-group m-t-40">
                    <div class="col-xs-12">
                        <input id="nDocumento" name="nDocumento" class="form-control" type="text" required placeholder="Código o documento de identidad">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-12">
                        <input id="password" name="password" class="form-control" type="password" required placeholder="Contraseña">
                    </div>
                </div>
                
               <div class="form-group">
                    <div class="col-md-12">
                        <label for="rol" style="margin-left: -10px; font-size: 20px;"><b>Rol:</b></label>
                        <select id="rol" name="rol" class="form-control" style="width: auto; display: inline-block;">
                            <option value="Usuario">Usuario</option>
                            <option value="Administrador">Administrador</option>
                        </select>
                    </div>
                </div>
                         
                <div class="form-group row">
                    <div class="col-md-12">
                        <a href="javascript:void(0)" id="to-recover" class="text-muted float-right">
                            <i class="fa fa-lock m-r-5"></i> ¿Olvidaste tu contraseña?
                        </a>
                    </div>
                </div>
                <div class="form-group text-center">
                    <div class="col-xs-12 p-b-20">
                        <button class="btn btn-block btn-lg btn-info btn-rounded" type="submit">Ingresar</button>
                    </div>
                </div>
                <% if (!mensaje.isEmpty()) { %>
                    <div class="form-group text-center">
                        <div class="col-xs-12" style="color: red;">
                            <%= mensaje %>
                        </div>
                    </div>
                <% } %>
                <div class="form-group m-b-0">
                    <div class="col-sm-12 text-center">
                        ¿Aún no tienes una cuenta? <a href="registroCliente.jsp" class="text-primary m-l-5"><b>Regístrate aquí</b></a>
                    </div>
                </div>
            </form>

            <!-- Recuperar contraseña -->
            <form class="form-horizontal" id="recoverform">
                <div class="form-group ">
                    <div class="col-xs-12">
                        <h3>Recuperar contraseña</h3>
                        <p class="text-muted">Ingresa tu correo electrónico</p>
                    </div>
                </div>
                <div class="form-group ">
                    <div class="col-xs-12">
                        <input class="form-control" id="email" type="text" required placeholder="Correo electrónico">
                    </div>
                </div>
                <div class="form-group text-center m-t-20">
                    <div class="col-xs-12">
                        <button class="btn btn-primary btn-lg btn-block text-uppercase waves-effect waves-light" type="submit">Enviar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>

<script src="assets/plugins/jquery/jquery.min.js"></script>
<script src="assets/plugins/bootstrap/js/popper.min.js"></script>
<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

