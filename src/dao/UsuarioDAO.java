package dao;

import config.conexion;
import interfaces.IUsuarioDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Rol;
import modelo.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nombre, apellido, correo, dni, telefono, estado, contrasena, nombre_usuario, direccion, fecha_creacion, id_rol) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getDni());
            ps.setString(5, usuario.getTelefono());
            ps.setBoolean(6, usuario.isEstado());
            ps.setString(7, usuario.getContrasena());
            ps.setString(8, usuario.getNombreUsuario());
            ps.setString(9, usuario.getDireccion());
            ps.setDate(10, new java.sql.Date(usuario.getFechaCreacion().getTime()));
            ps.setInt(11, usuario.getIdRol());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar usuario: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre=?, apellido=?, correo=?, dni=?, telefono=?, estado=?, contrasena=?, nombre_usuario=?, direccion=?, id_rol=? WHERE id_usuario=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getDni());
            ps.setString(5, usuario.getTelefono());
            ps.setBoolean(6, usuario.isEstado());
            ps.setString(7, usuario.getContrasena());
            ps.setString(8, usuario.getNombreUsuario());
            ps.setString(9, usuario.getDireccion());
            ps.setInt(10, usuario.getIdRol());
            ps.setInt(11, usuario.getIdUsuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar usuario: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Usuario WHERE id_usuario=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar usuario: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuario WHERE id_usuario=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar usuario: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return usuario;
    }

    @Override
    public Usuario buscarPorUsuario(String nombreUsuario) {
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuario WHERE nombre_usuario=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por usuario: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setCorreo(rs.getString("correo"));
        u.setDni(rs.getString("dni"));
        u.setTelefono(rs.getString("telefono"));
        u.setEstado(rs.getBoolean("estado"));
        u.setContrasena(rs.getString("contrasena"));
        u.setNombreUsuario(rs.getString("nombre_usuario"));
        u.setDireccion(rs.getString("direccion"));
        u.setFechaCreacion(rs.getDate("fecha_creacion"));
        u.setIdRol(rs.getInt("id_rol"));
        return u;
    }

    private void cerrarRecursos() {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
        // No cerramos Connection porque usamos Singleton
    }
    
    public List<Rol> listarRoles() {
    List<Rol> lista = new ArrayList<>();
    String sql = "SELECT * FROM Rol";
    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Rol r = new Rol(rs.getInt("id_rol"),
                            rs.getString("nombre_rol"),
                            rs.getString("descripcion"));
            lista.add(r);
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al listar roles: " + e.getMessage());
    } finally {
        cerrarRecursos();
    }
    return lista;
}

}
