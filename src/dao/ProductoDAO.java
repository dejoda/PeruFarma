package dao;

import config.conexion;
import interfaces.IProductoDAO;
import java.sql.*;
import java.util.*;
import modelo.Producto;

public class ProductoDAO implements IProductoDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Producto p) {
        String sql = "INSERT INTO Producto (presentacion, nombre, id_categoria, lote, llevarreceta, descripcion, "
                   + "precio_con_igv, fecha_vencimiento, id_proveedor, precio_venta, precio_compra, laboratorio, "
                   + "registro_sanitario, contenido, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getPresentacion());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getIdCategoria());
            ps.setString(4, p.getLote());
            ps.setBoolean(5, p.isLlevarReceta());
            ps.setString(6, p.getDescripcion());
            ps.setDouble(7, p.getPrecioConIgv());
            ps.setDate(8, new java.sql.Date(p.getFechaVencimiento().getTime()));
            ps.setInt(9, p.getIdProveedor());
            ps.setDouble(10, p.getPrecioVenta());
            ps.setDouble(11, p.getPrecioCompra());
            ps.setString(12, p.getLaboratorio());
            ps.setString(13, p.getRegistroSanitario());
            ps.setString(14, p.getContenido());
            ps.setBoolean(15, p.isEstado());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar producto: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean actualizar(Producto p) {
        String sql = "UPDATE Producto SET presentacion=?, nombre=?, id_categoria=?, lote=?, llevarreceta=?, "
                   + "descripcion=?, precio_con_igv=?, fecha_vencimiento=?, id_proveedor=?, precio_venta=?, "
                   + "precio_compra=?, laboratorio=?, registro_sanitario=?, contenido=?, estado=? "
                   + "WHERE id_producto=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getPresentacion());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getIdCategoria());
            ps.setString(4, p.getLote());
            ps.setBoolean(5, p.isLlevarReceta());
            ps.setString(6, p.getDescripcion());
            ps.setDouble(7, p.getPrecioConIgv());
            ps.setDate(8, new java.sql.Date(p.getFechaVencimiento().getTime()));
            ps.setInt(9, p.getIdProveedor());
            ps.setDouble(10, p.getPrecioVenta());
            ps.setDouble(11, p.getPrecioCompra());
            ps.setString(12, p.getLaboratorio());
            ps.setString(13, p.getRegistroSanitario());
            ps.setString(14, p.getContenido());
            ps.setBoolean(15, p.isEstado());
            ps.setInt(16, p.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar producto: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Producto WHERE id_producto=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar producto: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public Producto buscarPorId(int id) {
        Producto p = null;
        String sql = "SELECT * FROM Producto WHERE id_producto=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = mapearProducto(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar producto: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return p;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar productos: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    @Override
    public List<Producto> listarPorCategoria(int idCategoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto WHERE id_categoria=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCategoria);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar por categoría: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setPresentacion(rs.getString("presentacion"));
        p.setNombre(rs.getString("nombre"));
        p.setIdCategoria(rs.getInt("id_categoria"));
        p.setLote(rs.getString("lote"));
        p.setLlevarReceta(rs.getBoolean("llevarreceta"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecioConIgv(rs.getDouble("precio_con_igv"));
        p.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setPrecioVenta(rs.getDouble("precio_venta"));
        p.setPrecioCompra(rs.getDouble("precio_compra"));
        p.setLaboratorio(rs.getString("laboratorio"));
        p.setRegistroSanitario(rs.getString("registro_sanitario"));
        p.setContenido(rs.getString("contenido"));
        p.setEstado(rs.getBoolean("estado"));
        return p;
    }

    private void cerrarRecursos() {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
    }
}
