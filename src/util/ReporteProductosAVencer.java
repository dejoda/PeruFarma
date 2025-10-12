package util;

import config.conexion;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class ReporteProductosAVencer {

    public static boolean generar(String ruta) {
        String sql = """
            SELECT p.id_producto, p.nombre, p.lote, p.fecha_vencimiento, pr.empresa AS proveedor
            FROM producto p
            JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
            WHERE p.fecha_vencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
            ORDER BY p.fecha_vencimiento ASC
        """;

        try (Connection con = conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileOutputStream archivo = new FileOutputStream(ruta)) {

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            doc.add(new Paragraph("REPORTE DE PRODUCTOS PRÓXIMOS A VENCER", FontFactory.getFont("Helvetica", 16, Font.BOLD)));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date()));
            doc.add(new Paragraph(" "));
            
            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Lote");
            tabla.addCell("Vencimiento");

            while (rs.next()) {
                tabla.addCell(String.valueOf(rs.getInt("id_producto")));
                tabla.addCell(rs.getString("nombre"));
                tabla.addCell(rs.getString("lote"));
                tabla.addCell(rs.getString("fecha_vencimiento"));
            }

            doc.add(tabla);
            doc.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error generando reporte de productos a vencer: " + e.getMessage());
            return false;
        }
    }
}
