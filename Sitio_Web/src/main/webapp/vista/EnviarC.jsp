<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Modelo.Usuario" %>

<%
    // Obtener el objeto "user" que fue enviado desde el Servlet
    Usuario user = (Usuario) request.getAttribute("user");

    // Si el objeto es nulo (por ejemplo, si se va a insertar uno nuevo), se crea un objeto Usuario vacío
    if (user == null) {
        user = new Usuario();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>

    <!-- Se importa Bootstrap desde un CDN para aplicar estilos y maquetación responsive -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container mt-5">

    <!-- Título de la página: se muestra "Agregar Usuario" si el ID es 0, de lo contrario "Editar Usuario" -->
    <h2 class="mb-4"><%=(user.getId() == 0) ? "Agregar Usuario" : "Editar Usuario"%></h2>

    <!-- Formulario principal que envía los datos al Servlet "UsuarioServlet" usando el método POST -->
    <form action="UsuarioServlet" method="post" class="row g-3">
        
        <!-- Campo oculto que guarda el ID del usuario -->
        <!-- Si el ID es 0 o vacío, significa que es una inserción nueva -->
        <input type="hidden" name="id" value="<%=(user.getId() == 0) ? "" : user.getId()%>" />

        <!-- Campo para ingresar el nombre del usuario -->
        <div class="col-md-6">
            <label class="form-label">Nombre</label>
            <input type="text" name="nombre" class="form-control"
                   value="<%= (user.getNombre() != null) ? user.getNombre() : "" %>" required />
        </div>

        <!-- Campo para ingresar la cédula del usuario -->
        <div class="col-md-6">
            <label class="form-label">Cédula</label>
            <input type="number" name="cedula" class="form-control"
                   value="<%= (user.getCedula() != null) ? user.getCedula() : "" %>" required />
        </div>

        <!-- Campo para ingresar el email del usuario -->
        <div class="col-md-6">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control"
                   value="<%= (user.getEmail() != null) ? user.getEmail() : "" %>" required />
        </div>

        <!-- Campo para ingresar el teléfono del usuario -->
        <div class="col-md-6">
            <label class="form-label">Teléfono</label>
            <input type="number" name="telefono" class="form-control"
                   value="<%= (user.getTelefono() != null) ? user.getTelefono() : "" %>" required />
        </div>

        <!-- Campo para ingresar la dirección del usuario -->
        <div class="col-md-6">
            <label class="form-label">Dirección</label>
            <input type="text" name="direccion" class="form-control"
                   value="<%= (user.getDireccion() != null) ? user.getDireccion() : "" %>" required />
        </div>

        <!-- Botones de acción del formulario -->
        <div class="col-12">
            <!-- Botón para guardar (envía el formulario) -->
            <button type="submit" class="btn btn-primary">Guardar</button>
            
            <!-- Botón para cancelar (redirige a la lista de usuarios usando una acción del Servlet) -->
            <a href="UsuarioServlet?action=listUser" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>

</body>
</html>
