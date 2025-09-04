<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Modelo.Usuario" %>

<%
    // Obtener el objeto Usuario enviado desde el Servlet
    Usuario user = (Usuario) request.getAttribute("user");

    // Si es nulo (por ejemplo, en modo insertar), crear uno vacío
    if (user == null) {
        user = new Usuario();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <!-- Importación de Bootstrap 5 para estilos -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container mt-5">

    <!-- Título que cambia dinámicamente según si se está insertando o editando -->
    <h2 class="mb-4"><%=(user.getId() == 0) ? "Agregar Usuario" : "Editar Usuario"%></h2>

    <!-- Formulario que se envía al servlet UsuarioServlet -->
    <form action="UsuarioServlet" method="post" class="row g-3">
        
        <!-- Campo oculto para el ID del usuario (si existe) -->
        <input type="hidden" name="id" value="<%=(user.getId() == 0) ? "" : user.getId()%>" />

        <!-- Campo Nombre -->
        <div class="col-md-6">
            <label class="form-label">Nombre</label>
            <input type="text" name="nombre" class="form-control"
                   value="<%= (user.getNombre() != null) ? user.getNombre() : "" %>" required />
        </div>

        <!-- Campo Cédula -->
        <div class="col-md-6">
            <label class="form-label">Cédula</label>
            <input type="number" name="cedula" class="form-control"
                   value="<%= (user.getCedula() != null) ? user.getCedula() : "" %>" required />
        </div>

        <!-- Campo Email -->
        <div class="col-md-6">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control"
                   value="<%= (user.getEmail() != null) ? user.getEmail() : "" %>" required />
        </div>

        <!-- Campo Teléfono -->
        <div class="col-md-6">
            <label class="form-label">Teléfono</label>
            <input type="number" name="telefono" class="form-control"
                   value="<%= (user.getTelefono() != null) ? user.getTelefono() : "" %>" required />
        </div>

        <!-- Campo Dirección -->
        <div class="col-md-6">
            <label class="form-label">Dirección</label>
            <input type="text" name="direccion" class="form-control"
                   value="<%= (user.getDireccion() != null) ? user.getDireccion() : "" %>" required />
        </div>

        <!-- Botones -->
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="UsuarioServlet?action=listUser" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>

</body>
</html>
