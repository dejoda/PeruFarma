package util;

import config.conexion;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class ReporteValorInventario {

    public static boolean generar(String ruta) {
        String sql = """
            SELECT p.id_producto, p.nombre, i.stock, p.precio_compra,
                   (i.stock * p.precio_compra) AS valor_total
            FROM producto p
            JOIN inventario i ON p.id_producto = i.id_producto
            ORDER BY valor_total DESC
        """;

        try (Connection con = conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileOutputStream archivo = new FileOutputStream(ruta)) {

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            doc.add(new Paragraph("REPORTE DE VALORACIÓN DE INVENTARIO", FontFactory.getFont("Helvetica", 16, Font.BOLD)));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date()));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("ID");
            tabla.addCell("Producto");
            tabla.addCell("Stock");
            tabla.addCell("Precio Compra");
            tabla.addCell("Valor Total");

            double totalGeneral = 0;

            while (rs.next()) {
                double valor = rs.getDouble("valor_total");
                totalGeneral += valor;

                tabla.addCell(String.valueOf(rs.getInt("id_producto")));
                tabla.addCell(rs.getString("nombre"));
                tabla.addCell(String.valueOf(rs.getInt("stock")));
                tabla.addCell(String.valueOf(rs.getDouble("precio_compra")));
                tabla.addCell(String.format("%.2f", valor));
            }

            doc.add(tabla);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("VALOR TOTAL DEL INVENTARIO: S/. " + String.format("%.2f", totalGeneral)));

            doc.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error generando reporte de valoración de inventario: " + e.getMessage());
            return false;
        }
    }
}
