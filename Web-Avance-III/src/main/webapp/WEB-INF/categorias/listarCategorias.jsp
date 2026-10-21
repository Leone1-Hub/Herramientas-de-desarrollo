<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Categorías</title>
    <link href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f4f6f9; }
        .contenedor { max-width: 960px; margin: 40px auto; background: #fff; padding: 30px;
                      border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,.1); }
        h1 { font-weight: bold; color: #0a1f44; margin-bottom: 25px; }
    </style>
</head>
<body>
    <div class="contenedor">
        <h1>Categorías</h1>

        <c:if test="${not empty flashOk}">
            <div class="alert alert-success" role="alert"><c:out value="${flashOk}"/></div>
        </c:if>
        <c:if test="${not empty flashError}">
            <div class="alert alert-danger" role="alert"><c:out value="${flashError}"/></div>
        </c:if>

        <p>
            <a class="btn btn-primary" href="<c:url value='/SvCategorias'/>?accion=nueva">Nueva categoría</a>
        </p>

        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cat" items="${categorias}">
                        <tr>
                            <td><c:out value="${cat.idcategoria}"/></td>
                            <td><c:out value="${cat.nombre}"/></td>
                            <td><c:out value="${cat.descripcion}"/></td>
                            <td>
                                <a class="btn btn-sm btn-warning"
                                   href="<c:url value='/SvCategorias'/>?accion=editar&amp;id=<c:out value='${cat.idcategoria}'/>">Editar</a>
                                <form method="post" action="<c:url value='/SvCategorias'/>" class="d-inline"
                                      data-confirmar="¿Seguro que deseas eliminar esta categoría?">
                                    <input type="hidden" name="csrfToken" value="<c:out value='${csrfToken}'/>">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="id" value="<c:out value='${cat.idcategoria}'/>">
                                    <button type="submit" class="btn btn-sm btn-danger">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty categorias}">
                        <tr><td colspan="4" class="text-danger text-center">No hay categorías registradas.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <a href="<c:url value='/adminDashboard.jsp'/>">&larr; Volver al panel</a>
    </div>

    <script src="<c:url value='/main/js/categorias.js'/>"></script>
</body>
</html>
