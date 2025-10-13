package servicio;

import config.conexion;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Servicio de Reportes (conectado a BD)
 * - Consulta datos reales desde MySQL
 * - Genera PDF desde JTable
 */
public class ReporteService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // ==============================
    // MÉTODOS DE CONSULTA REAL A BD
    // ==============================

    public Object[][] obtenerProductosAVencer() {
        String sql = """
            SELECT p.id_producto, p.nombre, p.presentacion AS unidad,
                   p.precio_venta AS precio_unitario, c.nombre_categoria AS tipo,
                   p.fecha_vencimiento, 
                   CASE WHEN p.fecha_vencimiento < CURDATE() THEN 'No vendible'
                        ELSE 'Vendible' END AS estado
            FROM producto p
            JOIN categoria_producto c ON p.id_categoria = c.id_categoria
            ORDER BY p.fecha_vencimiento ASC
            LIMIT 20
        """;
        return ejecutarConsulta(sql);
    }

    public Object[][] obtenerStockRiesgo() {
        String sql = """
            SELECT p.id_producto, p.nombre, p.presentacion AS unidad,
                   p.precio_venta, c.nombre_categoria AS tipo, 
                   p.fecha_vencimiento,
                   CASE WHEN i.stock <= i.stock_minimo THEN 'Stock en riesgo'
                        ELSE 'Normal' END AS estado
            FROM producto p
            JOIN categoria_producto c ON p.id_categoria = c.id_categoria
            JOIN inventario i ON p.id_producto = i.id_producto
            WHERE i.stock <= i.stock_minimo
            ORDER BY i.stock ASC
            LIMIT 20
        """;
        return ejecutarConsulta(sql);
    }

    public Object[][] obtenerValorInventario() {
        String sql = """
            SELECT p.id_producto, p.nombre, p.presentacion AS unidad,
                   p.precio_compra AS precio_unitario, c.nombre_categoria AS tipo,
                   p.fecha_vencimiento,
                   CASE WHEN i.stock > 0 THEN 'En stock' ELSE 'Sin stock' END AS estado
            FROM producto p
            JOIN categoria_producto c ON p.id_categoria = c.id_categoria
            JOIN inventario i ON p.id_producto = i.id_producto
            ORDER BY p.nombre
            LIMIT 20
        """;
        return ejecutarConsulta(sql);
    }

    public Object[][] obtenerVentasDia() {
        String sql = """
            SELECT dv.id_detalle_venta, p.nombre, p.presentacion AS unidad,
                   dv.precio_unitario, c.nombre_categoria AS tipo,
                   v.fecha_venta, 'Vendido' AS estado
            FROM detalle_venta dv
            JOIN producto p ON dv.id_producto = p.id_producto
            JOIN categoria_producto c ON p.id_categoria = c.id_categoria
            JOIN venta v ON dv.id_venta = v.id_venta
            WHERE v.fecha_venta = CURDATE()
            LIMIT 20
        """;
        return ejecutarConsulta(sql);
    }

    public Object[][] obtenerVentasMes() {
        String sql = """
            SELECT dv.id_detalle_venta, p.nombre, p.presentacion AS unidad,
                   dv.precio_unitario, c.nombre_categoria AS tipo,
                   v.fecha_venta, 'Vendido' AS estado
            FROM detalle_venta dv
            JOIN producto p ON dv.id_producto = p.id_producto
            JOIN categoria_producto c ON p.id_categoria = c.id_categoria
            JOIN venta v ON dv.id_venta = v.id_venta
            WHERE MONTH(v.fecha_venta) = MONTH(CURDATE())
            AND YEAR(v.fecha_venta) = YEAR(CURDATE())
            LIMIT 20
        """;
        return ejecutarConsulta(sql);
    }

    // ==============================
    // MÉTODO GENÉRICO PARA CONSULTAS
    // ==============================

    private Object[][] ejecutarConsulta(String sql) {
        try (Connection cn = conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            java.util.List<Object[]> filas = new java.util.ArrayList<>();

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int j = 0; j < columnas; j++) {
                    fila[j] = rs.getObject(j + 1);
                }
                filas.add(fila);
            }

            // Convertir lista dinámica en arreglo
            Object[][] datos = new Object[filas.size()][columnas];
            for (int i = 0; i < filas.size(); i++) {
                datos[i] = filas.get(i);
            }

            return datos;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // ==============================
    // GENERAR PDF DESDE TABLA
    // ==============================

    public boolean generarPDFDesdeTabla(JTable tabla, String tipoReporte) {
        try {
            Document documento = new Document(PageSize.A4.rotate(), 30, 30, 40, 30);
            String fecha = sdf.format(new Date());
            String nombreArchivo = tipoReporte.replace(" ", "_") + "_" + fecha + ".pdf";

            File carpeta = new File("reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            FileOutputStream archivo = new FileOutputStream("reportes/" + nombreArchivo);
            PdfWriter.getInstance(documento, archivo);
            documento.open();

            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte: " + tipoReporte, tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            documento.add(titulo);

            Paragraph fechaP = new Paragraph("Generado el: " + fecha);
            fechaP.setAlignment(Element.ALIGN_RIGHT);
            fechaP.setSpacingAfter(10);
            documento.add(fechaP);

            // Tabla PDF
            TableModel modelo = tabla.getModel();
            PdfPTable pdfTable = new PdfPTable(modelo.getColumnCount());
            pdfTable.setWidthPercentage(100);

            Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase(modelo.getColumnName(i), headFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            for (int r = 0; r < modelo.getRowCount(); r++) {
                for (int c = 0; c < modelo.getColumnCount(); c++) {
                    Object valor = modelo.getValueAt(r, c);
                    pdfTable.addCell(valor != null ? valor.toString() : "");
                }
            }

            documento.add(pdfTable);
            documento.close();
            archivo.close();

            return true;

        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
