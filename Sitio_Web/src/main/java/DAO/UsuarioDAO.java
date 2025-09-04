package DAO;

import Modelo.Usuario;
import Controlador.Conexion;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    /**
     * Método para agregar un nuevo usuario a la base de datos.
     * Recibe un objeto Usuario y lo inserta en la tabla usuario.
     *
     * @param user Objeto Usuario con los datos a insertar
     */
    public void addUser(Usuario user) {
        try (Connection dbConnection = Conexion.getConnection()) {
            String sql = "INSERT INTO usuario(nombre, cedula, email, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            // Se establecen los parámetros del query
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCedula());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelefono());
            ps.setString(5, user.getDireccion());

            ps.executeUpdate(); // Ejecuta la inserción

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para eliminar un usuario según su ID.
     *

     */
    public void deleteUser(int userId) {
        try (Connection dbConnection = Conexion.getConnection()) {
            String sql = "DELETE FROM usuario WHERE id=?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeUpdate(); // Ejecuta la eliminación

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para actualizar un usuario existente en la base de datos.
     *
     *
     */
    public void updateUser(Usuario user) {
        try (Connection dbConnection = Conexion.getConnection()) {
            String sql = "UPDATE usuario SET nombre=?, cedula=?, email=?, telefono=?, direccion=? WHERE id=?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            // Se establecen los parámetros para actualización
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCedula());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelefono());
            ps.setString(5, user.getDireccion());
            ps.setInt(6, user.getId());

            ps.executeUpdate(); // Ejecuta la actualización

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para obtener la lista completa de usuarios almacenados en la base de datos.
     *
     */
    public List<Usuario> getAllUsers() {
        List<Usuario> users = new ArrayList<>();

        try (Connection dbConnection = Conexion.getConnection()) {
            String sql = "SELECT * FROM usuario";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Se recorren todos los registros y se crean objetos Usuario con la info
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setCedula(rs.getString("cedula"));
                user.setEmail(rs.getString("email"));
                user.setTelefono(rs.getString("telefono"));
                user.setDireccion(rs.getString("direccion"));
                users.add(user);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;  // return a la lista de usuario
    }

    /**
     * Método para obtener un usuario específico por su ID.
     *
     * 
     *
     */
    public Usuario getUserById(int id) {
        Usuario user = null;

        try (Connection dbConnection = Conexion.getConnection()) {
            String sql = "SELECT * FROM usuario WHERE id=?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Si se encuentra el usuario, se crea el objeto y se llenan sus datos
            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setCedula(rs.getString("cedula"));
                user.setEmail(rs.getString("email"));
                user.setTelefono(rs.getString("telefono"));
                user.setDireccion(rs.getString("direccion"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
