package Panel.Reportes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * PanelStockRiesgo
 * Muestra los productos cuyo stock está en riesgo o por debajo del mínimo.
 * Incluye una tabla y botones para actualizar los datos y generar un PDF.
 */
public class PanelStockRiesgo extends JPanel {

    private JTable tablaStock;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnGenerarPDF;

    public PanelStockRiesgo() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {

        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel("⚠️ Productos con stock en riesgo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // ===== TABLA =====
        String[] columnas = {"ID", "Producto", "Unidad", "Precio Unitario", "Tipo", "Stock", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaStock = new JTable(modeloTabla);

        tablaStock.setRowHeight(28);
        tablaStock.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaStock.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaStock.getTableHeader().setBackground(new Color(240, 240, 240));
        tablaStock.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tablaStock);
        scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scroll, BorderLayout.CENTER);

        // ===== BOTONES INFERIORES =====
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
    public JTable getTablaStock() { return tablaStock; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnGenerarPDF() { return btnGenerarPDF; }
}
