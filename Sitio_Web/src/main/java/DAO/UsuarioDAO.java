package DAO;

import Modelo.Usuario;
import Controlador.Conexion;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    /**
     * Método para agregar un nuevo usuario a la base de datos.
     * @param user Objeto Usuario con los datos a insertar.
     */
    public void addUser(Usuario user) {
        try (Connection dbConnection = Conexion.getConnection()) {
            // Sentencia SQL para insertar un nuevo usuario
            String sql = "INSERT INTO usuario(nombre, cedula, email, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            // Se establecen los parámetros de la consulta con los datos del usuario
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCedula());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelefono());
            ps.setString(5, user.getDireccion());

            // Se ejecuta la sentencia
            ps.executeUpdate(); 

        } catch (SQLException e) {
            // En caso de error, se imprime la traza
            e.printStackTrace();
        }
    }

    /**
     * Método para eliminar un usuario por su ID.
     * @param userId ID del usuario a eliminar.
     */
    public void deleteUser(int userId) {
        try (Connection dbConnection = Conexion.getConnection()) {
            // Sentencia SQL para eliminar un usuario
            String sql = "DELETE FROM usuario WHERE id=?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            // Se establece el parámetro ID
            ps.setInt(1, userId);

            // Se ejecuta la sentencia
            ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para actualizar los datos de un usuario existente.
     * @param user Objeto Usuario con los datos actualizados.
     */
    public void updateUser(Usuario user) {
        try (Connection dbConnection = Conexion.getConnection()) {
            // Sentencia SQL para actualizar un usuario
            String sql = "UPDATE usuario SET nombre=?, cedula=?, email=?, telefono=?, direccion=? WHERE id=?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            // Se establecen los parámetros con los datos del objeto Usuario
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCedula());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelefono());
            ps.setString(5, user.getDireccion());
            ps.setInt(6, user.getId());

            // Se ejecuta la sentencia
            ps.executeUpdate(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para obtener la lista de todos los usuarios.
     * @return Lista de objetos Usuario.
     */
    public List<Usuario> getAllUsers() {
        List<Usuario> users = new ArrayList<>();

        try (Connection dbConnection = Conexion.getConnection()) {
            // Consulta SQL para obtener todos los usuarios, ordenados por email
            String sql = "SELECT * FROM usuario ORDER BY email";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Se recorre el resultado y se crean objetos Usuario
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setCedula(rs.getString("cedula"));
                user.setEmail(rs.getString("email"));
                user.setTelefono(rs.getString("telefono"));
                user.setDireccion(rs.getString("direccion"));

                // Se añade el usuario a la lista
                users.add(user);
            }

            // Se cierra el ResultSet
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se retorna la lista completa de usuarios
        return users; 
    }

    /**
     * Método para obtener un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return Objeto Usuario si se encuentra, null si no.
     */
    public Usuario getUserById(int id) {
        Usuario user = null;

        try (Connection dbConnection = Conexion.getConnection()) {
            // Consulta SQL para obtener un usuario por su ID
            String sql = "SELECT * FROM usuario WHERE id=?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Si se encuentra el usuario, se crea y se llena el objeto
            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setCedula(rs.getString("cedula"));
                user.setEmail(rs.getString("email"));
                user.setTelefono(rs.getString("telefono"));
                user.setDireccion(rs.getString("direccion"));
            }

            // Se cierra el ResultSet
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se retorna el usuario (o null si no se encontró)
        return user;
    }
}
