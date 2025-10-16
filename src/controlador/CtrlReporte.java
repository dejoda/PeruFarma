package controlador;

import Panel.*;
import Panel.Reportes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Controlador principal del módulo de reportes.
 * Administra el PanelReportes (menú lateral o superior)
 * y muestra el subpanel correspondiente mediante CardLayout.
 *
 * Cumple con SOLID (Responsabilidad Única):
 * - No contiene lógica de negocio.
 * - Solo gestiona la navegación entre subpaneles.
 */
public class CtrlReporte implements ActionListener {

    private final PanelReportes panelPrincipal;
    private final JPanel contenedor; // Contenedor con CardLayout
    private final CardLayout cardLayout;

    // Subpaneles
    private final PanelProductosAVencer panelProductosVencer;
    private final PanelStockRiesgo panelStockRiesgo;
    private final PanelValorInventario panelValorInventario;
    private final PanelVentasDia panelVentasDia;
    private final PanelVentasMes panelVentasMes;

    public CtrlReporte(PanelReportes panelPrincipal, JPanel contenedor, CardLayout cardLayout) {
        this.panelPrincipal = panelPrincipal;
        this.contenedor = contenedor;
        this.cardLayout = cardLayout;

        // ===== Inicializar subpaneles =====
        panelProductosVencer = new PanelProductosAVencer();
        panelStockRiesgo = new PanelStockRiesgo();
        panelValorInventario = new PanelValorInventario();
        panelVentasDia = new PanelVentasDia();
        panelVentasMes = new PanelVentasMes();

        // ===== Asignar controladores a cada subpanel =====
        new CtrlProductosAVencer(panelProductosVencer);
        new CtrlStockRiesgo(panelStockRiesgo);
        new CtrlValorInventario(panelValorInventario);
        new CtrlVentasDia(panelVentasDia);
        new CtrlVentasMes(panelVentasMes);

        // ===== Añadir subpaneles al contenedor =====
        contenedor.add(panelProductosVencer, "productos");
        contenedor.add(panelStockRiesgo, "stock");
        contenedor.add(panelValorInventario, "valor");
        contenedor.add(panelVentasDia, "dia");
        contenedor.add(panelVentasMes, "mes");

        // ===== Escuchar los botones principales =====
        panelPrincipal.getBtnProductosVencer().addActionListener(this);
        panelPrincipal.getBtnStockRiesgo().addActionListener(this);
        panelPrincipal.getBtnValorInventario().addActionListener(this);
        panelPrincipal.getBtnVentasDia().addActionListener(this);
        panelPrincipal.getBtnVentasMes().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == panelPrincipal.getBtnProductosVencer()) {
            cardLayout.show(contenedor, "productos");
        } else if (src == panelPrincipal.getBtnStockRiesgo()) {
            cardLayout.show(contenedor, "stock");
        } else if (src == panelPrincipal.getBtnValorInventario()) {
            cardLayout.show(contenedor, "valor");
        } else if (src == panelPrincipal.getBtnVentasDia()) {
            cardLayout.show(contenedor, "dia");
        } else if (src == panelPrincipal.getBtnVentasMes()) {
            cardLayout.show(contenedor, "mes");
        }
    }
}
