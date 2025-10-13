package Panel.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * PanelValorInventario
 * Muestra el valor total del inventario actual.
 * Permite actualizar datos desde la BD y generar un PDF con los resultados.
 */
public class PanelValorInventario extends JPanel {

    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnGenerarPDF;

    public PanelValorInventario() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // ====== TÍTULO ======
        JLabel lblTitulo = new JLabel("💰 Valoración de Inventario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // ====== TABLA ======
        String[] columnas = {
            "ID", "Nombre", "Unidad Medida", "Precio Unitario", 
            "Precio + IGV", "Stock", "Fecha Vencimiento", "Tipo", "Total"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaInventario = new JTable(modeloTabla);

        tablaInventario.setRowHeight(28);
        tablaInventario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaInventario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaInventario.getTableHeader().setBackground(new Color(240, 240, 240));
        tablaInventario.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tablaInventario);
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
    public JTable getTablaInventario() { return tablaInventario; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnGenerarPDF() { return btnGenerarPDF; }
}
