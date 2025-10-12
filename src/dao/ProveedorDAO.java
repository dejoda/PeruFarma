package dao;

import config.conexion;
import interfaces.IProveedorDAO;
import java.sql.*;
import java.util.*;
import modelo.Proveedor;

public class ProveedorDAO implements IProveedorDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Proveedor p) {
        String sql = "INSERT INTO Proveedor (ruc, empresa, representante, fecha_registro, telefono, correo, direccion) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getRuc());
            ps.setString(2, p.getEmpresa());
            ps.setString(3, p.getRepresentante());
            ps.setDate(4, new java.sql.Date(p.getFechaRegistro().getTime()));
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getCorreo());
            ps.setString(7, p.getDireccion());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar proveedor: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean actualizar(Proveedor p) {
        String sql = "UPDATE Proveedor SET ruc=?, empresa=?, representante=?, fecha_registro=?, telefono=?, correo=?, direccion=? "
                   + "WHERE id_proveedor=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getRuc());
            ps.setString(2, p.getEmpresa());
            ps.setString(3, p.getRepresentante());
            ps.setDate(4, new java.sql.Date(p.getFechaRegistro().getTime()));
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getCorreo());
            ps.setString(7, p.getDireccion());
            ps.setInt(8, p.getIdProveedor());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar proveedor: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proveedor WHERE id_proveedor=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar proveedor: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public Proveedor buscarPorId(int id) {
        Proveedor p = null;
        String sql = "SELECT * FROM Proveedor WHERE id_proveedor=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = mapearProveedor(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar proveedor: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return p;
    }

    @Override
    public Proveedor buscarPorRuc(String ruc) {
        Proveedor p = null;
        String sql = "SELECT * FROM Proveedor WHERE ruc=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ruc);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = mapearProveedor(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar proveedor por RUC: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return p;
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearProveedor(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar proveedores: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setRuc(rs.getString("ruc"));
        p.setEmpresa(rs.getString("empresa"));
        p.setRepresentante(rs.getString("representante"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        p.setTelefono(rs.getString("telefono"));
        p.setCorreo(rs.getString("correo"));
        p.setDireccion(rs.getString("direccion"));
        return p;
    }

    private void cerrarRecursos() {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
    }
}
