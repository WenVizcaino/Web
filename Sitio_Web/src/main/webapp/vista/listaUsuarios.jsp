<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Modelo.Usuario"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>

    <!-- Inclusión de Bootstrap CSS desde CDN para estilos modernos y responsive -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Inclusión de íconos de Bootstrap (Bootstrap Icons) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Estilos personalizados -->
    <style>
        body {
            background: linear-gradient(to right, #f8f9fa, #e9ecef); /* Fondo degradado */
        }
        .table-container {
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1); /* Sombra elegante */
        }
        h2 {
            font-weight: bold;
            color: #212529;
        }
        .btn {
            border-radius: 8px;
        }
        .btn i {
            margin-right: 5px;
        }
        .table thead {
            background: #343a40;
            color: white;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f3f5;
        }
    </style>
</head>

<body class="container py-5">
    <div class="table-container">

        <!-- Alerta si no hay datos para generar PDF (se activa por parámetro GET "noData=true") -->
        <%
            String noData = request.getParameter("noData");
            if ("true".equals(noData)) {
        %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            No hay usuarios registrados para generar el PDF.
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <%
            }
        %>

        <!-- Título principal de la página -->
        <h2 class="mb-4 text-center">👥 Usuarios Registrados</h2>

        <!-- Botones de acción: Agregar, PDF, Enviar correo -->
        <div class="mb-4 d-flex justify-content-center gap-3">
            <!-- Redirige a formulario para agregar usuario -->
            <a href="UsuarioServlet?action=insert" class="btn btn-dark">
                <i class="bi bi-plus-circle"></i> Nuevo Usuario
            </a>

            <!-- Llama al Servlet que genera PDF (en nueva pestaña) -->
            <a href="GenerarPdfServlet" class="btn btn-danger" target="_blank">
                <i class="bi bi-file-earmark-pdf"></i> Descargar PDF
            </a>

            <!-- Redirige a la vista para enviar correo -->
            <a href="vista/EnviarC.jsp" class="btn btn-success">
                <i class="bi bi-envelope"></i> Enviar Correo
            </a>
        </div>

        <!-- Inicio de tabla de usuarios (se muestra solo si hay datos) -->
        <%
            List<Usuario> users = (List<Usuario>) request.getAttribute("users"); // Obtener lista de usuarios desde el Servlet
            if (users != null && !users.isEmpty()) {
        %>
        <div class="table-responsive">
            <table class="table table-hover align-middle text-center">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Cédula</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Dirección</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre la lista de usuarios -->
                    <%
                        for (Usuario user : users) {
                    %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= user.getNombre() %></td>
                        <td><%= user.getCedula() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getTelefono() %></td>
                        <td><%= user.getDireccion() %></td>
                        <td>
                            <!-- Botón para editar usuario -->
                            <a href="UsuarioServlet?action=edit&id=<%= user.getId() %>"
                               class="btn btn-outline-primary btn-sm" title="Editar">
                                <i class="bi bi-pencil-square"></i> Editar
                            </a>

                            <!-- Botón para eliminar usuario con confirmación -->
                            <a href="UsuarioServlet?action=delete&id=<%= user.getId() %>"
                               class="btn btn-outline-danger btn-sm"
                               onclick="return confirm('¿Eliminar este usuario?');" title="Eliminar">
                                <i class="bi bi-trash3"></i> Eliminar
                            </a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <%
            } else {
        %>
        <!-- Si no hay usuarios registrados -->
        <div class="alert alert-warning text-center">⚠️ No hay usuarios registrados.</div>
        <%
            }
        %>
    </div>

    <!-- Scripts de Bootstrap para alertas, botones, etc. -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
