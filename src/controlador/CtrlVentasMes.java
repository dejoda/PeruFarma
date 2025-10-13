package controlador;

import Panel.Reportes.*;
import servicio.ReporteService;

import javax.swing.*;
import java.awt.event.*;

/**
 * Controlador del PanelVentasMes.
 * Carga las ventas del mes actual desde la BD y permite generar un PDF.
 */
public class CtrlVentasMes implements ActionListener {

    private final PanelVentasMes vista;
    private final ReporteService servicio;

    public CtrlVentasMes(PanelVentasMes vista) {
        this.vista = vista;
        this.servicio = new ReporteService();

        // Cargar datos iniciales
        cargarDatos();

        // Asignar eventos
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnGenerarPDF().addActionListener(this);
    }

    private void cargarDatos() {
        Object[][] datos = servicio.obtenerVentasMes();
        var modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        if (datos != null) {
            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se pudieron cargar las ventas del mes.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnActualizar()) {
            cargarDatos();
        } else if (src == vista.getBtnGenerarPDF()) {
            boolean ok = servicio.generarPDFDesdeTabla(
                    vista.getTablaVentasMes(),
                    "Ventas del Mes"
            );
            JOptionPane.showMessageDialog(null,
                    ok ? "✅ PDF generado correctamente." : "❌ Error al generar el PDF.");
        }
    }
}
