package dao;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import config.conexion;

public class InicioDAO {

    public void actualizarTabla(JTable tabla, String tipo) {
        String query = "";
        String[] columnas;

        switch (tipo) {
            // === 1️⃣ VENTAS DEL DÍA ===
            case "ventas_dia":
                columnas = new String[]{"ID_Venta", "Cliente", "Hora", "Producto", "Cantidad", "Subtotal", "Total"};
                query = """
                        SELECT 
                            v.id_venta,
                            c.nombre AS Cliente,
                            v.hora_venta AS Hora,
                            p.nombre AS Producto,
                            dv.cantidad,
                            dv.subtotal,
                            v.total
                        FROM Venta v
                        INNER JOIN Detalle_Venta dv ON v.id_venta = dv.id_venta
                        INNER JOIN Producto p ON dv.id_producto = p.id_producto
                        INNER JOIN Cliente c ON v.id_cliente = c.id_cliente
                        WHERE DATE(v.fecha_venta) = CURDATE()
                        ORDER BY v.hora_venta ASC;
                        """;
                break;

             // === 2️⃣ STOCK BAJO ===
            case "stock_bajo":
                columnas = new String[]{"ID", "Producto", "Stock", "Estado"};
                query = """
                        SELECT 
                            i.id_inventario AS ID,
                            p.nombre AS Producto,
                            i.stock,
                            i.estado_stock AS Estado
                        FROM Inventario i
                        JOIN Producto p ON i.id_producto = p.id_producto
                        WHERE i.estado_stock IN ('Bajo', 'Crítico')
                        ORDER BY i.stock ASC;
                        """;
                break;



            // === 3️⃣ PRODUCTOS POR VENCER ===
            case "por_vencer":
                columnas = new String[]{"ID", "Producto", "Fecha_Vencimiento", "Estado"};
                query = """
                        SELECT 
                            p.id_producto AS ID,
                            p.nombre AS Producto,
                            p.fecha_vencimiento AS Fecha_Vencimiento,
                            CASE 
                                WHEN p.fecha_vencimiento <= CURDATE() THEN 'Vencido'
                                WHEN p.fecha_vencimiento <= DATE_ADD(CURDATE(), INTERVAL 60 DAY) THEN 'Por vencer'
                                ELSE 'Vigente'
                            END AS Estado
                        FROM Producto p
                        WHERE p.fecha_vencimiento <= DATE_ADD(CURDATE(), INTERVAL 60 DAY)
                        ORDER BY p.fecha_vencimiento ASC;
                        """;
                break;

            default:
                JOptionPane.showMessageDialog(null, "⚠️ Tipo de consulta no válido.");
                return;
        }

        // ===  EJECUTAR CONSULTA Y MOSTRAR EN TABLA ===
        try (Connection cn = conexion.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            // Crear modelo dinámico
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
            int cols = columnas.length;

            while (rs.next()) {
                Object[] fila = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar los datos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
