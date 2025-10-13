package controlador;

import Panel.Reportes.*;
import servicio.ReporteService;

import javax.swing.*;
import java.awt.event.*;

/**
 * Controlador del PanelStockRiesgo.
 * Carga los productos cuyo stock está en riesgo o por debajo del mínimo,
 * y permite generar el PDF con el reporte actual.
 */
public class CtrlStockRiesgo implements ActionListener {

    private final PanelStockRiesgo vista;
    private final ReporteService servicio;

    public CtrlStockRiesgo(PanelStockRiesgo vista) {
        this.vista = vista;
        this.servicio = new ReporteService();

        // Cargar datos al iniciar el panel
        cargarDatos();

        // Asignar listeners
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnGenerarPDF().addActionListener(this);
    }

    /**
     * Carga desde la BD los productos con stock en riesgo.
     */
    private void cargarDatos() {
        Object[][] datos = servicio.obtenerStockRiesgo();
        var modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        if (datos != null) {
            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se pudieron cargar los productos en riesgo de stock.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnActualizar()) {
            cargarDatos();

        } else if (src == vista.getBtnGenerarPDF()) {
            boolean ok = servicio.generarPDFDesdeTabla(
                    vista.getTablaStock(),
                    "Stock en riesgo"
            );
            JOptionPane.showMessageDialog(null,
                    ok ? "✅ PDF generado correctamente." : "❌ Error al generar el PDF.");
        }
    }
}
