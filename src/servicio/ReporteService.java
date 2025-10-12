package servicio;

import util.*;
import java.io.File;

/**
 * Servicio de generación de reportes del sistema PeruFarma.
 * 
 * Encargado de coordinar la creación de los 5 tipos de reportes:
 *  - Productos a vencer
 *  - Stock en riesgo
 *  - Valoración de inventario
 *  - Ventas del día
 *  - Ventas del mes
 *
 * Cumple el principio SOLID (Responsabilidad Única):
 * Separa la lógica de negocio del controlador y la vista.
 */
public class ReporteService {

    private final String rutaBase;

    public ReporteService() {
        // Carpeta donde se guardarán los reportes PDF
        rutaBase = System.getProperty("user.dir") + File.separator + "reportes";
        new File(rutaBase).mkdirs(); // crea la carpeta si no existe
    }

    /**
     * Genera el reporte de productos próximos a vencer.
     */
    public boolean generarProductosAVencer() {
        String ruta = rutaBase + File.separator + "ProductosAVencer_" + System.currentTimeMillis() + ".pdf";
        return ReporteProductosAVencer.generar(ruta);
    }

    /**
     * Genera el reporte de productos con stock bajo.
     */
    public boolean generarStockRiesgo() {
        String ruta = rutaBase + File.separator + "StockRiesgo_" + System.currentTimeMillis() + ".pdf";
        return ReporteStockRiesgo.generar(ruta);
    }

    /**
     * Genera el reporte de valoración de inventario.
     */
    public boolean generarValorInventario() {
        String ruta = rutaBase + File.separator + "ValorInventario_" + System.currentTimeMillis() + ".pdf";
        return ReporteValorInventario.generar(ruta);
    }

    /**
     * Genera el reporte de ventas del día actual.
     */
    public boolean generarVentasDia() {
        String ruta = rutaBase + File.separator + "VentasDia_" + System.currentTimeMillis() + ".pdf";
        return ReporteVentasDia.generar(ruta);
    }

    /**
     * Genera el reporte de ventas del mes actual.
     */
    public boolean generarVentasMes() {
        String ruta = rutaBase + File.separator + "VentasMes_" + System.currentTimeMillis() + ".pdf";
        return ReporteVentasMes.generar(ruta);
    }
}
