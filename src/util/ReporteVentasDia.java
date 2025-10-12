package util;

import config.conexion;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class ReporteVentasDia {

    public static boolean generar(String ruta) {
        String sql = """
            SELECT v.id_venta, v.fecha_venta, v.hora_venta, u.nombre AS usuario, v.total
            FROM venta v
            JOIN usuario u ON v.id_usuario = u.id_usuario
            WHERE DATE(v.fecha_venta) = CURDATE()
            ORDER BY v.hora_venta ASC
        """;

        try (Connection con = conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileOutputStream archivo = new FileOutputStream(ruta)) {

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            doc.add(new Paragraph("REPORTE DE VENTAS DEL DÍA", FontFactory.getFont("Helvetica", 16, Font.BOLD)));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date()));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("ID Venta");
            tabla.addCell("Fecha");
            tabla.addCell("Hora");
            tabla.addCell("Usuario");
            tabla.addCell("Total (S/.)");

            double totalDia = 0;

            while (rs.next()) {
                totalDia += rs.getDouble("total");
                tabla.addCell(String.valueOf(rs.getInt("id_venta")));
                tabla.addCell(rs.getString("fecha_venta"));
                tabla.addCell(rs.getString("hora_venta"));
                tabla.addCell(rs.getString("usuario"));
                tabla.addCell(String.format("%.2f", rs.getDouble("total")));
            }

            doc.add(tabla);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("TOTAL VENTAS DEL DÍA: S/. " + String.format("%.2f", totalDia)));

            doc.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error generando reporte de ventas del día: " + e.getMessage());
            return false;
        }
    }
}
