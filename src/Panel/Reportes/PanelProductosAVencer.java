package Panel.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * PanelProductosAVencer
 * Muestra los productos próximos a vencer.
 * Contiene una tabla con datos reales desde la BD
 * y un botón para generar el PDF del reporte.
 */
public class PanelProductosAVencer extends JPanel {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerarPDF, btnActualizar;

    public PanelProductosAVencer() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {

        // ====== TÍTULO ======
        JLabel lblTitulo = new JLabel("📦 Productos próximos a vencer", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // ====== TABLA ======
        String[] columnas = {"ID", "Producto", "Unidad", "Precio Unitario", "Tipo", "Fecha Vencimiento", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);

        tablaProductos.setRowHeight(28);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaProductos.getTableHeader().setBackground(new Color(245, 245, 245));
        tablaProductos.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tablaProductos);
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
    public JTable getTablaProductos() { return tablaProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnGenerarPDF() { return btnGenerarPDF; }
    public JButton getBtnActualizar() { return btnActualizar; }
}
