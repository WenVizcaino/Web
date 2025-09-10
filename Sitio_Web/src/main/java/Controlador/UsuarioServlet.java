package Controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import DAO.UsuarioDAO;
import Modelo.Usuario;

// Anotación que indica la URL con la que se accede a este Servlet
@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constantes con las rutas de las vistas JSP
    private static final String INSERT_OR_EDIT = "/vista/formularioUsuario.jsp"; // Formulario para insertar o editar usuarios
    private static final String LIST_USER = "/vista/listaUsuarios.jsp"; // Lista de usuarios

    // Instancia del DAO que se encarga de la lógica de acceso a datos
    private UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Se obtiene el parámetro 'action' de la URL para saber qué operación realizar
        String action = request.getParameter("action");
        String forward; // Variable que define a qué vista se va a redirigir

        // Acción: eliminar usuario
        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id")); // Se obtiene el ID del usuario a eliminar
            dao.deleteUser(id); // Se llama al método del DAO para eliminarlo

            request.setAttribute("users", dao.getAllUsers()); // Se actualiza la lista de usuarios en el request  
            forward = LIST_USER; // Se redirige a la vista de lista

        // Acción: editar usuario
        } else if ("edit".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id")); // Se obtiene el ID del usuario a editar
            Usuario user = dao.getUserById(id); // Se obtiene el objeto Usuario desde el DAO
            request.setAttribute("user", user); // Se añade al request para rellenar el formulario
            forward = INSERT_OR_EDIT; // Se redirige al formulario de edición

        // Acción: insertar nuevo usuario
        } else if ("insert".equalsIgnoreCase(action)) {
            Usuario user = new Usuario(); // Se crea un objeto Usuario vacío
            request.setAttribute("user", user); // Se pasa al formulario para ingresar los datos
            forward = INSERT_OR_EDIT;

        // Acción por defecto: mostrar lista de usuarios
        } else {
            request.setAttribute("users", dao.getAllUsers()); // Se obtiene la lista completa de usuarios
            forward = LIST_USER;
        }

        // Se hace el forward hacia la vista correspondiente
        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Se crea un objeto Usuario y se le asignan los datos del formulario
        Usuario user = new Usuario();
        user.setNombre(request.getParameter("nombre"));
        user.setCedula(request.getParameter("cedula"));
        user.setEmail(request.getParameter("email"));
        user.setTelefono(request.getParameter("telefono"));
        user.setDireccion(request.getParameter("direccion"));

        // Se obtiene el parámetro "id" para saber si se trata de una inserción o actualización
        String id = request.getParameter("id");

        if (id == null || id.isEmpty() || id.equals("0")) {
            // Si no hay ID, es un nuevo usuario
            dao.addUser(user);
        } else {
            // Si hay ID, se trata de una actualización
            user.setId(Integer.parseInt(id));
            dao.updateUser(user);
        }

        // Se actualiza la lista de usuarios y se redirige a la vista
        request.setAttribute("users", dao.getAllUsers());
        RequestDispatcher dispatcher = request.getRequestDispatcher(LIST_USER);
        dispatcher.forward(request, response);
    }
}
