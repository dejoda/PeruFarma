package servicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.VentaDAO;
import modelo.Producto;
import java.util.List;

public class VentaService {

    // Cargar productos en la tabla inventario
    public DefaultTableModel generarModeloInventario(List<Producto> productos) {
        String[] columnas = {"ID", "Producto", "Presentación", "Laboratorio", "Precio Venta", "Stock", "Vencimiento", "Receta"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getPresentacion(),
                p.getLaboratorio(),
                p.getPrecioVenta(),
                "-", // stock se muestra si está en producto o inventario
                p.getFechaVencimiento(),
                p.isLlevarReceta() ? "Sí" : "No"
            });
        }
        return modelo;
    }

 // ================= AGREGAR PRODUCTO =================
    public void agregarProductoAVenta(JTable tablaInventario, JTable tablaVenta) {
        DefaultTableModel modeloVenta = (DefaultTableModel) tablaVenta.getModel();
        int fila = tablaInventario.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto del inventario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(tablaInventario.getValueAt(fila, 0).toString());
        String nombre = tablaInventario.getValueAt(fila, 1).toString();
        int colReceta = tablaInventario.getColumnCount() - 1; // ✅ última columna = Receta
        String receta = tablaInventario.getValueAt(fila, colReceta).toString();
        double precioUnit = Double.parseDouble(tablaInventario.getValueAt(fila, 4).toString());
        double precioIGV = Math.round(precioUnit * 1.18 * 100.0) / 100.0;

        boolean existe = false;
        for (int i = 0; i < modeloVenta.getRowCount(); i++) {
            if (Integer.parseInt(modeloVenta.getValueAt(i, 0).toString()) == id) {
                int nuevaCantidad = Integer.parseInt(modeloVenta.getValueAt(i, 3).toString()) + 1;
                modeloVenta.setValueAt(nuevaCantidad, i, 3);
                modeloVenta.setValueAt(precioIGV * nuevaCantidad, i, 6);
                existe = true;
                break;
            }
        }

        if (!existe) {
            modeloVenta.addRow(new Object[]{id, nombre, receta, 1, precioUnit, precioIGV, precioIGV});
        }
    }




 // ================= ACTUALIZAR FILA =================
    private final VentaDAO dao = new VentaDAO();
    
    public void actualizarFilaVenta(JTable tablaVenta, int fila) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();

        try {
            int idProducto = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            int cantidad = Integer.parseInt(modelo.getValueAt(fila, 3).toString());
            double precioIGV = Double.parseDouble(modelo.getValueAt(fila, 5).toString());

            // 🧮 Verificar stock disponible
            int stockDisponible = dao.obtenerStockDisponible(idProducto);

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0.", "Cantidad inválida", JOptionPane.WARNING_MESSAGE);
                modelo.setValueAt(1, fila, 3); // restablece cantidad
            } else if (cantidad > stockDisponible) {
                JOptionPane.showMessageDialog(null, "Stock insuficiente. Solo hay " + stockDisponible + " unidades disponibles.", "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
                modelo.setValueAt(stockDisponible, fila, 3); // ajusta al máximo permitido
            } else {
                modelo.setValueAt(Math.round(precioIGV * cantidad * 100.0) / 100.0, fila, 6);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido en la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Recalcular total
    public void recalcularTotal(JTable tablaVenta, JTextField txtTotal) {
        double total = 0.0;
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += Double.parseDouble(modelo.getValueAt(i, 6).toString());
        }
        txtTotal.setText(String.format("%.2f", total));
    }
    

 // 🗑️ Eliminar producto seleccionado
    public void eliminarProductoSeleccionado(JTable tablaVenta, JTextField txtTotal) {
        int fila = tablaVenta.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
            modelo.removeRow(fila);
            recalcularTotal(tablaVenta, txtTotal);
        }
    }

    // 🧹 Eliminar todos los productos
    public void eliminarTodosLosProductos(JTable tablaVenta, JTextField txtTotal) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar todos los productos?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modelo.setRowCount(0); // borra todas las filas
            txtTotal.setText("0.00");
        }
    }
    
    public boolean registrarVentaCompleta(JTable tablaVenta, String dni, String nombre,
            String telefono, String correo, String direccion,
            String metodoPago, double total) {
return new dao.VentaDAO().registrarVentaCompleta(tablaVenta, dni, nombre, telefono, correo, direccion, metodoPago, total);
}

    
    public void registrarRecetaConVenta() {
        VentaDAO dao = new VentaDAO();
        modelo.RecetarioTemp r = new modelo.RecetarioTemp();

        if (!modelo.RecetarioTemp.estaCompleto()) return;

        int idReceta = dao.insertarRecetaMedica(
            modelo.RecetarioTemp.getNombrePaciente(),
            modelo.RecetarioTemp.getDniPaciente(),
            modelo.RecetarioTemp.getNombreMedico(),
            modelo.RecetarioTemp.getCmpMedico(),
            new java.sql.Date(modelo.RecetarioTemp.getFechaEmision().getTime()),
            modelo.RecetarioTemp.getObservaciones()
        );

        int idVenta = dao.obtenerUltimaVenta();
        if (idReceta > 0 && idVenta > 0) {
            dao.relacionarRecetaVenta(idVenta, idReceta);
            modelo.RecetarioTemp.limpiar(); // limpia las variables temporales
        }
    }


    
    
    
}
