package util;

import config.conexion;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class ReporteVentasMes {

    public static boolean generar(String ruta) {
        String sql = """
            SELECT DATE(v.fecha_venta) AS fecha, SUM(v.total) AS total_dia
            FROM venta v
            WHERE MONTH(v.fecha_venta) = MONTH(CURDATE()) AND YEAR(v.fecha_venta) = YEAR(CURDATE())
            GROUP BY DATE(v.fecha_venta)
            ORDER BY fecha ASC
        """;

        try (Connection con = conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileOutputStream archivo = new FileOutputStream(ruta)) {

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            doc.add(new Paragraph("REPORTE DE VENTAS DEL MES", FontFactory.getFont("Helvetica", 16, Font.BOLD)));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date()));
            doc.add(new Paragraph(" "));
            
            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Fecha");
            tabla.addCell("Total (S/.)");

            double totalMes = 0;

            while (rs.next()) {
                double totalDia = rs.getDouble("total_dia");
                totalMes += totalDia;

                tabla.addCell(rs.getString("fecha"));
                tabla.addCell(String.format("%.2f", totalDia));
            }

            doc.add(tabla);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("TOTAL VENTAS DEL MES: S/. " + String.format("%.2f", totalMes)));

            doc.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error generando reporte de ventas del mes: " + e.getMessage());
            return false;
        }
    }
}
