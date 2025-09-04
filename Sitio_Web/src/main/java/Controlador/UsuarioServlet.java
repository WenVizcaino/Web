package Controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import DAO.UsuarioDAO;
import Modelo.Usuario;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Ruta del JSP para insertar o editar usuario
    private static final String INSERT_OR_EDIT = "/vista/formularioUsuario.jsp";
    // Ruta del JSP para listar usuarios
    private static final String LIST_USER = "/vista/listaUsuarios.jsp";

    // Instancia del DAO para operaciones en BD
    private UsuarioDAO dao = new UsuarioDAO();

    
    //Genera la accion
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Se obtiene la acción enviada como parámetro
        String action = request.getParameter("action");
        String forward;

        
        
        
     //Accion para eliminar el usuario existente 
        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteUser(id);
            // Se obtiene la lista actualizada y se envía a la vista
            request.setAttribute("users", dao.getAllUsers());
            forward = LIST_USER;

            
            
            //Actualizar el usuario ya existente
        } else if ("edit".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario user = dao.getUserById(id);
            request.setAttribute("user", user);
            forward = INSERT_OR_EDIT;

            //Accion que agrega nuevo usuario
        } else if ("insert".equalsIgnoreCase(action)) {
            // Si la acción es insertar, se envía un nuevo usuario vacío para el formulario
            Usuario user = new Usuario();
            request.setAttribute("user", user);
            forward = INSERT_OR_EDIT;

        } else {
            // Por defecto, se muestra la lista de usuarios
            request.setAttribute("users", dao.getAllUsers());
            forward = LIST_USER;
        }

        //  Aquí se envía la solicitud y la respuesta a la página indicada para mostrarla al usuario
        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Se crea un objeto Usuario y se llenan sus campos con datos del formulario
        Usuario user = new Usuario();
        user.setNombre(request.getParameter("nombre"));
        user.setCedula(request.getParameter("cedula"));
        user.setEmail(request.getParameter("email"));
        user.setTelefono(request.getParameter("telefono"));
        user.setDireccion(request.getParameter("direccion"));

        // Se obtiene el id para determinar si es insertar o actualizar
        String id = request.getParameter("id");

        if (id == null || id.isEmpty() || id.equals("0")) {
            // Si no hay id, se agrega un nuevo usuario
            dao.addUser(user);
        } else {
            // Si hay id, se actualiza el usuario existente
            user.setId(Integer.parseInt(id));
            dao.updateUser(user);
        }

        // Después de guardar o actualizar, se obtiene la lista actualizada y se envía a la vista
        request.setAttribute("users", dao.getAllUsers());
        RequestDispatcher dispatcher = request.getRequestDispatcher(LIST_USER);
        dispatcher.forward(request, response);
    }
}
