package controlador;

import Panel.Reportes.*;
import servicio.ReporteService;

import javax.swing.*;
import java.awt.event.*;

/**
 * Controlador del PanelValorInventario
 * Gestiona la carga de datos desde la BD y la generación del PDF del reporte.
 */
public class CtrlValorInventario implements ActionListener {

    private final PanelValorInventario vista;
    private final ReporteService servicio;

    public CtrlValorInventario(PanelValorInventario vista) {
        this.vista = vista;
        this.servicio = new ReporteService();

        // Cargar los datos al iniciar
        cargarDatos();

        // Asignar eventos
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnGenerarPDF().addActionListener(this);
    }

    private void cargarDatos() {
        Object[][] datos = servicio.obtenerValorInventario();
        var modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        if (datos != null) {
            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al obtener los datos del reporte de inventario.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnActualizar()) {
            cargarDatos();
        } else if (src == vista.getBtnGenerarPDF()) {
            boolean ok = servicio.generarPDFDesdeTabla(
                    vista.getTablaInventario(),
                    "Valoración de Inventario"
            );
            JOptionPane.showMessageDialog(null,
                    ok ? "✅ PDF generado correctamente." : "❌ Error al generar el PDF.");
        }
    }
}
