package controlador;

import Panel.Reportes.*;
import servicio.ReporteService;

import javax.swing.*;
import java.awt.event.*;

/**
 * Controlador del PanelVentasDia.
 * Carga las ventas del día desde la BD y permite generar un PDF.
 */
public class CtrlVentasDia implements ActionListener {

    private final PanelVentasDia vista;
    private final ReporteService servicio;

    public CtrlVentasDia(PanelVentasDia vista) {
        this.vista = vista;
        this.servicio = new ReporteService();

        // Cargar datos iniciales
        cargarDatos();

        // Asignar eventos
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnGenerarPDF().addActionListener(this);
    }

    private void cargarDatos() {
        Object[][] datos = servicio.obtenerVentasDia();
        var modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        if (datos != null) {
            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se pudieron cargar las ventas del día.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnActualizar()) {
            cargarDatos();
        } else if (src == vista.getBtnGenerarPDF()) {
            boolean ok = servicio.generarPDFDesdeTabla(
                    vista.getTablaVentas(),
                    "Ventas del Día"
            );
            JOptionPane.showMessageDialog(null,
                    ok ? "✅ PDF generado correctamente." : "❌ Error al generar el PDF.");
        }
    }
}
