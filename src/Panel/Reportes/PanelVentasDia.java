package Panel.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * PanelVentasDia
 * Muestra todas las ventas realizadas en el día actual.
 * Permite actualizar los datos y exportar el reporte a PDF.
 */
public class PanelVentasDia extends JPanel {

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnGenerarPDF;

    public PanelVentasDia() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // ====== TÍTULO ======
        JLabel lblTitulo = new JLabel("🕒 Reporte de Ventas del Día", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // ====== TABLA ======
        String[] columnas = {
            "ID Venta", "Fecha", "Cliente", "Empleado", 
            "Método de Pago", "Tipo Comprobante", "N° Comprobante", "Total (S/)"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaVentas = new JTable(modeloTabla);

        tablaVentas.setRowHeight(28);
        tablaVentas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaVentas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaVentas.getTableHeader().setBackground(new Color(240, 240, 240));
        tablaVentas.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tablaVentas);
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
    public JTable getTablaVentas() { return tablaVentas; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnGenerarPDF() { return btnGenerarPDF; }
}
