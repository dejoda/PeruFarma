package Panel.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * PanelVentasMes
 * Muestra todas las ventas realizadas durante el mes actual.
 * Permite actualizar los datos y exportar el reporte a PDF.
 */
public class PanelVentasMes extends JPanel {

    private JTable tablaVentasMes;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnGenerarPDF;

    public PanelVentasMes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // ====== TÍTULO ======
        JLabel lblTitulo = new JLabel("📅 Reporte de Ventas del Mes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // ====== TABLA ======
        String[] columnas = {
            "ID Venta", "Fecha", "Cliente", "Empleado", 
            "Método de Pago", "Tipo Comprobante", "N° Comprobante", "Total (S/)"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaVentasMes = new JTable(modeloTabla);

        tablaVentasMes.setRowHeight(28);
        tablaVentasMes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaVentasMes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaVentasMes.getTableHeader().setBackground(new Color(240, 240, 240));
        tablaVentasMes.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tablaVentasMes);
        scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scroll, BorderLayout.CENTER);

        // ====== BOTONES INFERIORES ======
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(Color.WHITE);

        btnActualizar = new JButton("Actualizar datos");
        btnActualizar.setBackground(new Color(255, 204, 102));
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGenerarPDF = new JButton("Descargar PDF");
        btnGenerarPDF.setBackground(new Color(255, 102, 102));
        btnGenerarPDF.setForeground(Color.WHITE);
        btnGenerarPDF.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerarPDF.setFocusPainted(false);
        btnGenerarPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnGenerarPDF);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // ==== GETTERS ====
    public JTable getTablaVentasMes() { return tablaVentasMes; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnGenerarPDF() { return btnGenerarPDF; }
}
