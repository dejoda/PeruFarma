package controlador;

import Panel.Reportes.*;
import servicio.ReporteService;

import javax.swing.*;
import java.awt.event.*;

/**
 * Controlador del PanelProductosAVencer.
 * Carga los productos próximos a vencer y permite generar un PDF del reporte.
 */
public class CtrlProductosAVencer implements ActionListener {

    private final PanelProductosAVencer vista;
    private final ReporteService servicio;

    public CtrlProductosAVencer(PanelProductosAVencer vista) {
        this.vista = vista;
        this.servicio = new ReporteService();

        // Cargar datos iniciales al abrir el panel
        cargarDatos();

        // Escuchar eventos de botones
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnGenerarPDF().addActionListener(this);
    }

    /**
     * Obtiene los datos desde la BD y los muestra en la tabla.
     */
    private void cargarDatos() {
        Object[][] datos = servicio.obtenerProductosAVencer();
        var modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        if (datos != null) {
            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se pudieron cargar los productos a vencer.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnActualizar()) {
            cargarDatos();

        } else if (src == vista.getBtnGenerarPDF()) {
            boolean ok = servicio.generarPDFDesdeTabla(
                    vista.getTablaProductos(),
                    "Productos a vencer"
            );
            JOptionPane.showMessageDialog(null,
                    ok ? "✅ PDF generado correctamente." : "❌ Error al generar el PDF.");
        }
    }
}
