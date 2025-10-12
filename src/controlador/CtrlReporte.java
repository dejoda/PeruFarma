package controlador;

import Panel.PanelReportes;
import servicio.ReporteService;

import javax.swing.*;
import java.awt.event.*;

/**
 * Controlador del módulo de Reportes.
 * Gestiona los eventos del PanelReportes y coordina la generación de PDFs.
 * Cumple con SOLID: separa la lógica de presentación (vista) de la lógica de negocio (servicio).
 */
public class CtrlReporte implements ActionListener {

    private final PanelReportes vista;
    private final ReporteService servicio;

    public CtrlReporte(PanelReportes vista) {
        this.vista = vista;
        this.servicio = new ReporteService();

        // Asociar los botones a eventos
        vista.getBtnProductosVencer().addActionListener(this);
        vista.getBtnStockRiesgo().addActionListener(this);
        vista.getBtnValorInventario().addActionListener(this);
        vista.getBtnVentasDia().addActionListener(this);
        vista.getBtnVentasMes().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        boolean ok = false;
        String tipo = "";

        if (src == vista.getBtnProductosVencer()) {
            tipo = "Productos a vencer";
            ok = servicio.generarProductosAVencer();
        } else if (src == vista.getBtnStockRiesgo()) {
            tipo = "Stocks en riesgo";
            ok = servicio.generarStockRiesgo();
        } else if (src == vista.getBtnValorInventario()) {
            tipo = "Valoración de inventario";
            ok = servicio.generarValorInventario();
        } else if (src == vista.getBtnVentasDia()) {
            tipo = "Ventas del día";
            ok = servicio.generarVentasDia();
        } else if (src == vista.getBtnVentasMes()) {
            tipo = "Ventas del mes";
            ok = servicio.generarVentasMes();
        }

        // Mostrar resultado
        if (ok) {
            JOptionPane.showMessageDialog(null,
                    "✅ Reporte generado correctamente: " + tipo,
                    "Reporte generado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al generar el reporte: " + tipo,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
