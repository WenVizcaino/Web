<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Modelo.Usuario"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>

    <!-- Bootstrap CSS para estilos -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons para iconos en botones -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* Estilos personalizados para la página */
        body {
            background-color: #f8f9fa; /* Fondo claro */
        }
        .table-container {
            background-color: #ffffff; /* Fondo blanco para el contenedor de la tabla */
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05); /* Sombra sutil */
        }
        h2 {
            color: #343a40; /* Color oscuro para el título */
        }
        .btn-icon {
            font-size: 1.2rem; /* Tamaño de iconos en botones */
        }
        .btn-icon:hover {
            opacity: 0.8; /* Efecto hover más suave */
        }
    </style>
</head>

<body class="container py-5">
    <div class="table-container">

        <!-- Validación para mostrar alerta si no hay datos para generar el PDF -->
        <%
            String noData = request.getParameter("noData");
            if ("true".equals(noData)) {
        %>
        <script>
            alert("No hay usuarios registrados para generar el PDF.");
        </script>
        <%
            }
        %>

        <!-- Título principal -->
        <h2 class="mb-4">Usuarios Registrados</h2>

        <!-- Botones para agregar nuevo usuario y descargar PDF -->
        <div class="mb-3 d-flex gap-2">
            <!-- Botón para insertar nuevo usuario, redirige al formulario -->
            <a href="UsuarioServlet?action=insert" class="btn btn-dark">
                <i class="bi bi-plus-circle"></i> Agregar nuevo usuario
            </a>

            <!-- Botón para descargar el listado de usuarios en PDF -->
            <a href="GenerarPdfServlet" class="btn btn-success" target="_blank">
                <i class="bi bi-file-earmark-pdf"></i> Descargar PDF
            </a>
        </div>

        <!-- Tabla para mostrar los usuarios -->
        <%
            // Obtener la lista de usuarios desde el request
            List<Usuario> users = (List<Usuario>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
        %>

        <table class="table table-hover align-middle">
            <thead class="table-secondary">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Cédula</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Dirección</th>
                    <th class="text-center">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Iterar y mostrar cada usuario en una fila
                    for (Usuario user : users) {
                %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getNombre() %></td>
                    <td><%= user.getCedula() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getTelefono() %></td>
                    <td><%= user.getDireccion() %></td>
                    <td class="text-center">
                        <!-- Botón para editar el usuario (redirecciona con action=edit e id) -->
                        <a href="UsuarioServlet?action=edit&id=<%= user.getId() %>"
                           class="btn btn-outline-primary btn-sm btn-icon me-1" title="Editar">
                            <i class="bi bi-arrow-repeat"></i>
                        </a>

                        <!-- Botón para eliminar el usuario (pide confirmación antes) -->
                        <a href="UsuarioServlet?action=delete&id=<%= user.getId() %>"
                           class="btn btn-outline-danger btn-sm btn-icon"
                           onclick="return confirm('¿Eliminar este usuario?');" title="Eliminar">
                            <i class="bi bi-trash"></i>
                        </a>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <%
            } else {
        %>
        <!-- Mostrar mensaje si no hay usuarios en la base de datos -->
        <div class="alert alert-warning">No hay usuarios registrados.</div>
        <%
            }
        %>

    </div>
</body>
</html>
