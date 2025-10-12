package dao;

import config.conexion;
import interfaces.IClienteDAO;
import java.sql.*;
import java.util.*;
import modelo.Cliente;

public class ClienteDAO implements IClienteDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Cliente c) {
        String sql = "INSERT INTO Cliente (dni, nombre, telefono, correo, direccion, fecha_registro) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setString(5, c.getDireccion());
            ps.setDate(6, new java.sql.Date(c.getFechaRegistro().getTime()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar cliente: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean actualizar(Cliente c) {
        String sql = "UPDATE Cliente SET dni=?, nombre=?, telefono=?, correo=?, direccion=?, fecha_registro=? "
                   + "WHERE id_cliente=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setString(5, c.getDireccion());
            ps.setDate(6, new java.sql.Date(c.getFechaRegistro().getTime()));
            ps.setInt(7, c.getIdCliente());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Cliente WHERE id_cliente=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        Cliente c = null;
        String sql = "SELECT * FROM Cliente WHERE id_cliente=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar cliente: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return c;
    }

    @Override
    public Cliente buscarPorDni(String dni) {
        Cliente c = null;
        String sql = "SELECT * FROM Cliente WHERE dni=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar cliente por DNI: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return c;
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setDni(rs.getString("dni"));
        c.setNombre(rs.getString("nombre"));
        c.setTelefono(rs.getString("telefono"));
        c.setCorreo(rs.getString("correo"));
        c.setDireccion(rs.getString("direccion"));
        c.setFechaRegistro(rs.getDate("fecha_registro"));
        return c;
    }

    private void cerrarRecursos() {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
    }
}
