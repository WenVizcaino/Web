<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Modelo.Usuario" %>

<%
    // Obtener el objeto Usuario enviado desde el Servlet
    Usuario user = (Usuario) request.getAttribute("user");

    // Si el usuario es nulo (por ejemplo, en modo "insertar"), se crea una instancia vacía
    if (user == null) {
        user = new Usuario();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>

    <!-- Carga el framework Bootstrap desde CDN para estilos -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container mt-5">

    <!-- Título dinámico: cambia según si se está agregando o editando un usuario -->
    <h2 class="mb-4"><%=(user.getId() == 0) ? "Agregar Usuario" : "Editar Usuario"%></h2>

    <!-- Formulario para enviar datos al Servlet -->
    <!-- El atributo "action" indica que se enviará al Servlet UsuarioServlet vía POST -->
    <form action="UsuarioServlet" method="post" class="row g-3">
        
        <!-- Campo oculto que contiene el ID del usuario -->
        <!-- Si es 0 o vacío, el Servlet sabrá que es un nuevo registro -->
        <input type="hidden" name="id" value="<%=(user.getId() == 0) ? "" : user.getId()%>" />

        <!-- Campo para el nombre del usuario -->
        <div class="col-md-6">
            <label class="form-label">Nombre</label>
            <input type="text" name="nombre" class="form-control"
                   value="<%= (user.getNombre() != null) ? user.getNombre() : "" %>" required />
        </div>

        <!-- Campo para la cédula -->
        <div class="col-md-6">
            <label class="form-label">Cédula</label>
            <input type="number" name="cedula" class="form-control"
                   value="<%= (user.getCedula() != null) ? user.getCedula() : "" %>" required />
        </div>

        <!-- Campo para el email -->
        <div class="col-md-6">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control"
                   value="<%= (user.getEmail() != null) ? user.getEmail() : "" %>" required />
        </div>

        <!-- Campo para el teléfono -->
        <div class="col-md-6">
            <label class="form-label">Teléfono</label>
            <input type="number" name="telefono" class="form-control"
                   value="<%= (user.getTelefono() != null) ? user.getTelefono() : "" %>" required />
        </div>

        <!-- Campo para la dirección -->
        <div class="col-md-6">
            <label class="form-label">Dirección</label>
            <input type="text" name="direccion" class="form-control"
                   value="<%= (user.getDireccion() != null) ? user.getDireccion() : "" %>" required />
        </div>

        <!-- Botones de acción: guardar (submit) y cancelar (volver a la lista) -->
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Guardar</button>
            
            <!-- El botón "Cancelar" redirige al Servlet para mostrar la lista de usuarios -->
            <a href="UsuarioServlet?action=listUser" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>

</body>
</html>
