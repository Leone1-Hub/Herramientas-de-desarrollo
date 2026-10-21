<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${empty idEdicion ? 'Nueva categoría' : 'Editar categoría'}"/></title>
    <link href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f4f6f9; }
        .contenedor { max-width: 640px; margin: 40px auto; background: #fff; padding: 30px;
                      border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,.1); }
        h1 { font-weight: bold; color: #0a1f44; margin-bottom: 25px; }
    </style>
</head>
<body>
    <div class="contenedor">
        <h1><c:out value="${empty idEdicion ? 'Nueva categoría' : 'Editar categoría'}"/></h1>

        <c:if test="${not empty flashError}">
            <div class="alert alert-danger" role="alert"><c:out value="${flashError}"/></div>
        </c:if>

        <form method="post" action="<c:url value='/SvCategorias'/>" autocomplete="off">
            <input type="hidden" name="csrfToken" value="<c:out value='${csrfToken}'/>">
            <input type="hidden" name="accion" value="${empty idEdicion ? 'crear' : 'editar'}">
            <c:if test="${not empty idEdicion}">
                <input type="hidden" name="id" value="<c:out value='${idEdicion}'/>">
            </c:if>

            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre"
                       value="<c:out value='${nombre}'/>" required minlength="2" maxlength="50">
            </div>

            <div class="form-group">
                <label for="descripcion">Descripción (opcional)</label>
                <textarea class="form-control" id="descripcion" name="descripcion"
                          rows="3" maxlength="150"><c:out value="${descripcion}"/></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Guardar</button>
            <a class="btn btn-secondary" href="<c:url value='/SvCategorias'/>">Cancelar</a>
        </form>
    </div>
</body>
</html>
