package util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import config.conexion;

public class ComprobantePDF {

    public static void generarPDF(int idVenta, String rutaArchivo) {
        String sqlVenta = """
            SELECT v.id_venta, v.fecha_venta, v.total, c.nombre AS cliente,
                   c.dni, c.telefono, mp.nombre_metodo AS metodo_pago,
                   co.nro_comprobante, co.tipo_comprobante
            FROM Venta v
            LEFT JOIN Cliente c ON v.id_cliente = c.id_cliente
            INNER JOIN Metodo_Pago mp ON v.id_metodo_pago = mp.id_metodo_pago
            INNER JOIN Comprobante co ON v.id_venta = co.id_venta
            WHERE v.id_venta = ?
        """;

        String sqlDetalle = """
            SELECT p.nombre, dv.cantidad, dv.precio_unitario, dv.subtotal
            FROM Detalle_Venta dv
            INNER JOIN Producto p ON dv.id_producto = p.id_producto
            WHERE dv.id_venta = ?
        """;

        String sqlReceta = """
            SELECT rm.id_receta_medica, rm.nombre_paciente, rm.dni_paciente,
                   rm.nombre_medico, rm.cmp_medico, rm.fecha_emision, rm.observaciones
            FROM Receta_Venta rv
            INNER JOIN Receta_Medica rm ON rv.id_receta = rm.id_receta_medica
            WHERE rv.id_venta = ?
        """;

        try (Connection cn = conexion.getConnection();
             PreparedStatement psVenta = cn.prepareStatement(sqlVenta);
             PreparedStatement psDet = cn.prepareStatement(sqlDetalle);
             PreparedStatement psReceta = cn.prepareStatement(sqlReceta)) {

            psVenta.setInt(1, idVenta);
            psDet.setInt(1, idVenta);
            psReceta.setInt(1, idVenta);

            ResultSet rsVenta = psVenta.executeQuery();
            if (!rsVenta.next()) {
                System.out.println("⚠️ No se encontró la venta con ID " + idVenta);
                return;
            }

            File file = new File(rutaArchivo);
            file.getParentFile().mkdirs();

            Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            // === Estilos ===
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11);
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);

            // === ENCABEZADO ===
            Paragraph titulo = new Paragraph("COMPROBANTE DE VENTA", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());

            doc.add(new Paragraph("Tipo de comprobante: " + rsVenta.getString("tipo_comprobante"), subtitleFont));
            doc.add(new Paragraph("Número: " + rsVenta.getString("nro_comprobante"), normalFont));
            doc.add(new Paragraph("Fecha de emisión: " +
                    new SimpleDateFormat("dd/MM/yyyy").format(rsVenta.getDate("fecha_venta")), normalFont));
            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            // === DATOS DEL CLIENTE ===
            String cliente = validarTexto(rsVenta.getString("cliente"), "Cliente genérico");
            String dni = validarTexto(rsVenta.getString("dni"), "No registrado");
            String telefono = validarTexto(rsVenta.getString("telefono"), "No registrado");
            String metodoPago = validarTexto(rsVenta.getString("metodo_pago"), "Sin especificar");

            Paragraph datosCliente = new Paragraph("DATOS DEL CLIENTE", subtitleFont);
            doc.add(datosCliente);
            doc.add(new Paragraph("Cliente: " + cliente, normalFont));
            doc.add(new Paragraph("DNI: " + dni, normalFont));
            doc.add(new Paragraph("Teléfono: " + telefono, normalFont));
            doc.add(new Paragraph("Método de pago: " + metodoPago, normalFont));
            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            // === DATOS DE RECETA (solo si existe) ===
            ResultSet rsReceta = psReceta.executeQuery();
            if (rsReceta.next()) {
                Paragraph recetaTitulo = new Paragraph("DATOS DE LA RECETA MÉDICA", subtitleFont);
                recetaTitulo.setAlignment(Element.ALIGN_LEFT);
                doc.add(recetaTitulo);

                doc.add(new Paragraph("N° de Receta: RM-" + String.format("%05d", rsReceta.getInt("id_receta_medica")), normalFont));
                doc.add(new Paragraph("Paciente: " + validarTexto(rsReceta.getString("nombre_paciente"), "No registrado"), normalFont));
                doc.add(new Paragraph("DNI Paciente: " + validarTexto(rsReceta.getString("dni_paciente"), "No registrado"), normalFont));
                doc.add(new Paragraph("Médico: " + validarTexto(rsReceta.getString("nombre_medico"), "No registrado"), normalFont));
                doc.add(new Paragraph("CMP Médico: " + validarTexto(rsReceta.getString("cmp_medico"), "No registrado"), normalFont));
                doc.add(new Paragraph("Fecha de Emisión: " +
                        new SimpleDateFormat("dd/MM/yyyy").format(rsReceta.getDate("fecha_emision")), normalFont));

                String observaciones = rsReceta.getString("observaciones");
                if (observaciones != null && !observaciones.isBlank()) {
                    doc.add(new Paragraph("Observaciones: " + observaciones, normalFont));
                }

                doc.add(new Paragraph(" "));
                doc.add(new LineSeparator());
                doc.add(new Paragraph(" "));
            }

            // === TABLA DE PRODUCTOS ===
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{4, 1.5f, 2, 2});
            tabla.setSpacingBefore(10f);

            String[] headers = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, subtitleFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                tabla.addCell(cell);
            }

            ResultSet rsDet = psDet.executeQuery();
            while (rsDet.next()) {
                tabla.addCell(new Phrase(rsDet.getString("nombre"), normalFont));
                tabla.addCell(new Phrase(String.valueOf(rsDet.getInt("cantidad")), normalFont));
                tabla.addCell(new Phrase(String.format("S/ %.2f", rsDet.getDouble("precio_unitario")), normalFont));
                tabla.addCell(new Phrase(String.format("S/ %.2f", rsDet.getDouble("subtotal")), normalFont));
            }

            doc.add(tabla);
            doc.add(new LineSeparator());
            doc.add(new Paragraph(" "));

            // === TOTAL ===
            Paragraph total = new Paragraph("TOTAL A PAGAR: S/ " + String.format("%.2f", rsVenta.getDouble("total")), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            doc.add(total);
            doc.add(new Paragraph(" "));

            // === PIE DE PÁGINA ===
            Paragraph pie = new Paragraph("Gracias por su compra.\nFarmacia PeruFarma - Atención de calidad garantizada.", normalFont);
            pie.setAlignment(Element.ALIGN_CENTER);
            doc.add(pie);

            doc.close();
            System.out.println("✅ Comprobante generado correctamente en: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String validarTexto(String valor, String reemplazo) {
        return (valor == null || valor.isBlank()) ? reemplazo : valor;
    }
}
