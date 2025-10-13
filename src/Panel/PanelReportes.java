package Panel;

import javax.swing.*;
import java.awt.*;

/**
 * PanelReportes
 * Menú principal del módulo de reportes.
 * Contiene los botones para seleccionar el tipo de reporte.
 */
public class PanelReportes extends JPanel {

    private JButton btnProductosVencer;
    private JButton btnStockRiesgo;
    private JButton btnValorInventario;
    private JButton btnVentasDia;
    private JButton btnVentasMes;

    public PanelReportes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // ====== TÍTULO SUPERIOR ======
        JLabel lblTitulo = new JLabel("📊 Módulo de Reportes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        lblTitulo.setForeground(new Color(60, 60, 60));
        add(lblTitulo, BorderLayout.NORTH);

        // ====== PANEL CENTRAL CON BOTONES ======
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 15, 15));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        btnProductosVencer = crearBoton("📦 Productos a vencer", new Color(255, 153, 102));
        btnStockRiesgo = crearBoton("⚠️ Stock en riesgo", new Color(255, 204, 102));
        btnValorInventario = crearBoton("💰 Valoración de inventario", new Color(153, 204, 255));
        btnVentasDia = crearBoton("🕒 Ventas por día", new Color(204, 153, 255));
        btnVentasMes = crearBoton("📅 Ventas por mes", new Color(153, 255, 204));

        panelBotones.add(btnProductosVencer);
        panelBotones.add(btnStockRiesgo);
        panelBotones.add(btnValorInventario);
        panelBotones.add(btnVentasDia);
        panelBotones.add(btnVentasMes);

        add(panelBotones, BorderLayout.CENTER);
    }

    /**
     * Crea un botón con estilo uniforme.
     */
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setBackground(colorFondo);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));
        btn.setOpaque(true);
        btn.setForeground(Color.DARK_GRAY);
        return btn;
    }

    // ==== GETTERS ====
    public JButton getBtnProductosVencer() { return btnProductosVencer; }
    public JButton getBtnStockRiesgo() { return btnStockRiesgo; }
    public JButton getBtnValorInventario() { return btnValorInventario; }
    public JButton getBtnVentasDia() { return btnVentasDia; }
    public JButton getBtnVentasMes() { return btnVentasMes; }
}
