package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Producto;
import config.conexion;

public class VentaDAO {

    public List<Producto> buscarProductoPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT 
                p.id_producto,
                p.nombre,
                p.presentacion,
                p.laboratorio,
                p.precio_venta,
                p.fecha_vencimiento,
                p.llevarreceta,
                i.stock,
                i.stock_minimo,
                i.estado_stock
            FROM Producto p
            INNER JOIN Inventario i ON p.id_producto = i.id_producto
            WHERE p.estado = 1
              AND i.stock > 0
              AND p.nombre LIKE ?
            ORDER BY p.nombre ASC;
        """;

        try (Connection cn = conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();

                // Datos de la tabla Producto
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPresentacion(rs.getString("presentacion"));
                p.setLaboratorio(rs.getString("laboratorio"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                p.setLlevarReceta(rs.getBoolean("llevarreceta"));

                // Datos del Inventario
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setEstadoStock(rs.getString("estado_stock"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar producto: " + e.getMessage());
        }

        return lista;
    }
    
 // 🔹 Consultar stock disponible de un producto
    public int obtenerStockDisponible(int idProducto) {
        String sql = "SELECT stock FROM Inventario WHERE id_producto = ?";
        try (Connection cn = conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener stock disponible: " + e.getMessage());
        }
        return 0; // si no hay registro o error
    }
 // ====================== REGISTRAR VENTA COMPLETA ======================
    public boolean registrarVentaCompleta(
            JTable tablaVenta, String dni, String nombre, String telefono,
            String correo, String direccion, String metodoPago, double total) {

        String sqlVenta = "INSERT INTO Venta (fecha_venta, hora_venta, id_usuario, id_comprobante, id_metodo_pago, id_cliente, total, estado) "
                        + "VALUES (CURDATE(), CURTIME(), 1, NULL, ?, ?, ?, 'COMPLETADO')";
        String sqlDetalle = "INSERT INTO Detalle_Venta (id_producto, id_venta, cantidad, precio_unitario, precio_igv, subtotal) "
                          + "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE Inventario SET stock = stock - ? WHERE id_producto = ?";

        try (Connection cn = conexion.getConnection()) {
            cn.setAutoCommit(false);

            // 1️⃣ Obtener id del método de pago
            int idMetodoPago = obtenerIdMetodoPago(cn, metodoPago);

            // 2️⃣ Obtener o crear cliente si tiene DNI
            Integer idCliente = null;
            if (dni != null && !dni.trim().isEmpty()) {
                idCliente = obtenerIdCliente(cn, dni, nombre, telefono, correo, direccion);
            }

            // 3️⃣ Insertar venta
            int idVenta;
            try (PreparedStatement ps = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idMetodoPago);
                if (idCliente == null) ps.setNull(2, Types.INTEGER);
                else ps.setInt(2, idCliente);
                ps.setDouble(3, total);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                idVenta = rs.getInt(1);
                System.out.println("✅ Venta insertada con ID: " + idVenta);
            }

            // 4️⃣ Crear comprobante y vincularlo directamente a la venta
            int idComprobante = crearComprobante(cn, idVenta);
            System.out.println("✅ Comprobante creado y vinculado correctamente con la venta " + idVenta);

            // 4️⃣ Insertar detalle de venta y actualizar stock
            try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
                 PreparedStatement psStock = cn.prepareStatement(sqlStock)) {

                for (int i = 0; i < tablaVenta.getRowCount(); i++) {
                    int idProd = Integer.parseInt(tablaVenta.getValueAt(i, 0).toString());
                    int cantidad = Integer.parseInt(tablaVenta.getValueAt(i, 3).toString());
                    double precioUnit = Double.parseDouble(tablaVenta.getValueAt(i, 4).toString());
                    double precioIGV = Double.parseDouble(tablaVenta.getValueAt(i, 5).toString());
                    double subtotal = Double.parseDouble(tablaVenta.getValueAt(i, 6).toString());

                    psDetalle.setInt(1, idProd);
                    psDetalle.setInt(2, idVenta);
                    psDetalle.setInt(3, cantidad);
                    psDetalle.setDouble(4, precioUnit);
                    psDetalle.setDouble(5, precioIGV);
                    psDetalle.setDouble(6, subtotal);
                    psDetalle.addBatch();

                    psStock.setInt(1, cantidad);
                    psStock.setInt(2, idProd);
                    psStock.addBatch();
                }

                psDetalle.executeBatch();
                psStock.executeBatch();
            }

            cn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int obtenerIdMetodoPago(Connection cn, String nombreMetodo) throws SQLException {
        String sql = "SELECT id_metodo_pago FROM Metodo_Pago WHERE nombre_metodo = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombreMetodo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_metodo_pago");
            } else {
                throw new SQLException("Método de pago no encontrado: " + nombreMetodo);
            }
        }
    }

    private Integer obtenerIdCliente(Connection cn, String dni, String nombre,
            String telefono, String correo, String direccion) throws SQLException {
String select = "SELECT id_cliente FROM Cliente WHERE dni = ?";
String insert = """
INSERT INTO Cliente (dni, nombre, telefono, correo, direccion, fecha_registro)
VALUES (?, ?, ?, ?, ?, CURDATE())
""";

try (PreparedStatement ps = cn.prepareStatement(select)) {
ps.setString(1, dni);
ResultSet rs = ps.executeQuery();
if (rs.next()) {
return rs.getInt("id_cliente");
}
}

// Si no existe el cliente, lo crea
try (PreparedStatement ps = cn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, dni);
ps.setString(2, nombre);
ps.setString(3, (telefono == null || telefono.isBlank()) ? null : telefono);
ps.setString(4, (correo == null || correo.isBlank()) ? null : correo);
ps.setString(5, (direccion == null || direccion.isBlank()) ? null : direccion);
ps.executeUpdate();

ResultSet rs = ps.getGeneratedKeys();
if (rs.next()) return rs.getInt(1);
}

return null;
}


    private int crearComprobante(Connection cn, int idVenta) throws SQLException {
        String tipo = "Boleta";
        String nro = "B" + System.currentTimeMillis();

        // ✅ Insertamos el comprobante vinculado directamente a la venta
        String sql = """
            INSERT INTO Comprobante (tipo_comprobante, nro_comprobante, fecha_emision, id_venta)
            VALUES (?, ?, CURDATE(), ?)
        """;

        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tipo);
            ps.setString(2, nro);
            ps.setInt(3, idVenta);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idComprobante = rs.getInt(1);
                System.out.println("🧾 Comprobante generado (ID: " + idComprobante + ") vinculado con venta " + idVenta);
                return idComprobante;
            }
        }

        throw new SQLException("❌ No se pudo generar el comprobante.");
    }

 // 🔹 Obtiene el último ID de venta registrada
    public int obtenerUltimaVenta() {
        String sql = "SELECT MAX(id_venta) AS ultima FROM Venta";
        try (Connection cn = conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("ultima");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener la última venta: " + e.getMessage());
        }
        return -1;
    }

    public int insertarRecetaMedica(String nombrePaciente, String dniPaciente, String nombreMedico,
            String cmpMedico, java.sql.Date fechaEmision, String observaciones) {
String sql = """
INSERT INTO Receta_Medica (nombre_paciente, dni_paciente, nombre_medico, cmp_medico, fecha_emision, observaciones, estado)
VALUES (?, ?, ?, ?, ?, ?, 'ACTIVO')
""";
try (Connection cn = conexion.getConnection();
PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

ps.setString(1, nombrePaciente);
ps.setString(2, dniPaciente);
ps.setString(3, nombreMedico);
ps.setString(4, cmpMedico);
ps.setDate(5, fechaEmision);
ps.setString(6, observaciones);
ps.executeUpdate();

ResultSet rs = ps.getGeneratedKeys();
if (rs.next()) return rs.getInt(1);

} catch (SQLException e) {
System.err.println("❌ Error al insertar receta médica: " + e.getMessage());
}
return -1;
}

public void relacionarRecetaVenta(int idVenta, int idReceta) {
String sql = "INSERT INTO Receta_Venta (id_usuario, id_venta, id_receta) VALUES (1, ?, ?)";
try (Connection cn = conexion.getConnection();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setInt(1, idVenta);
ps.setInt(2, idReceta);
ps.executeUpdate();
} catch (SQLException e) {
System.err.println("❌ Error al relacionar receta con venta: " + e.getMessage());
}
}



}
