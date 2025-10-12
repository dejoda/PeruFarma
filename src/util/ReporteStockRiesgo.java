package util;

import config.conexion;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class ReporteStockRiesgo {

    public static boolean generar(String ruta) {
        String sql = """
            SELECT p.id_producto, p.nombre, i.stock, i.stock_minimo
            FROM inventario i
            JOIN producto p ON i.id_producto = p.id_producto
            WHERE i.stock <= i.stock_minimo
            ORDER BY i.stock ASC
        """;

        try (Connection con = conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileOutputStream archivo = new FileOutputStream(ruta)) {

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            doc.add(new Paragraph("REPORTE DE STOCKS EN RIESGO", FontFactory.getFont("Helvetica", 16, Font.BOLD)));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date()));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("ID");
            tabla.addCell("Producto");
            tabla.addCell("Stock");
            tabla.addCell("Stock Mínimo");

            while (rs.next()) {
                tabla.addCell(String.valueOf(rs.getInt("id_producto")));
                tabla.addCell(rs.getString("nombre"));
                tabla.addCell(String.valueOf(rs.getInt("stock")));
                tabla.addCell(String.valueOf(rs.getInt("stock_minimo")));
            }

            doc.add(tabla);
            doc.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error generando reporte de stock en riesgo: " + e.getMessage());
            return false;
        }
    }
}
