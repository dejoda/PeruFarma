package controlador;

import dao.VentaDAO;
import modelo.Producto;
import modelo.RecetarioTemp;
import servicio.VentaService;
import Frames.Recetario_Registro;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class CtrlVenta {
	private final CtrlRecetario ctrlRecetario = new CtrlRecetario();
    private final VentaDAO dao = new VentaDAO();
    private final VentaService service = new VentaService();
    private boolean recetarioRegistrado = false; // 🔹 Estado del recetario

    // ========================================================
    // MÉTODOS DE CONSULTA
    // ========================================================
    public void buscarYMostrarProductos(JTable tabla, String nombre) {
        List<Producto> lista = dao.buscarProductoPorNombre(nombre);

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Producto");
        modelo.addColumn("Presentación");
        modelo.addColumn("Laboratorio");
        modelo.addColumn("Precio Venta (S/)");
        modelo.addColumn("Stock");
        modelo.addColumn("Estado Stock");
        modelo.addColumn("Vencimiento");
        modelo.addColumn("Receta");

        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getPresentacion(),
                    p.getLaboratorio(),
                    String.format("%.2f", p.getPrecioVenta()),
                    p.getStock(),
                    p.getEstadoStock(),
                    p.getFechaVencimiento(),
                    p.isLlevarReceta() ? "Sí" : "No"
            });
        }

        tabla.setModel(modelo);
    }

    public void cargarTodosLosProductos(JTable tabla) {
        buscarYMostrarProductos(tabla, "");
    }

    // ========================================================
    // MÉTODOS DE VENTA
    // ========================================================
    public void agregarProducto(JTable tablaInventario, JTable tablaVenta, JTextField txtTotal) {
        service.agregarProductoAVenta(tablaInventario, tablaVenta);
        service.recalcularTotal(tablaVenta, txtTotal);
    }

    public void detectarCambiosCantidad(JTable tablaVenta, JTextField txtTotal) {
        tablaVenta.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) { // columna "Cantidad"
                int fila = e.getFirstRow();
                service.actualizarFilaVenta(tablaVenta, fila);
                service.recalcularTotal(tablaVenta, txtTotal);
            }
        });
    }

    public void recalcularTotal(JTable tablaVenta, JTextField txtTotal) {
        service.recalcularTotal(tablaVenta, txtTotal);
    }

    public void eliminarProducto(JTable tablaVenta, JTextField txtTotal) {
        service.eliminarProductoSeleccionado(tablaVenta, txtTotal);
    }

    public void eliminarTodo(JTable tablaVenta, JTextField txtTotal) {
        service.eliminarTodosLosProductos(tablaVenta, txtTotal);
    }

    // ========================================================
    // REGISTRO DE VENTA
    // ========================================================
    public boolean registrarVenta(
            JTable tablaVenta, JTextField txtDNI, JTextField txtNombre, JTextField txtTelefono,
            JComboBox<String> comboPago, JTextField txtTotal) {

        // 1️⃣ Validar que haya productos
        if (tablaVenta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 2️⃣ Detectar si algún producto requiere receta
        boolean requiereReceta = false;
        for (int i = 0; i < tablaVenta.getRowCount(); i++) {
            String receta = tablaVenta.getValueAt(i, 2).toString();
            if (receta.equalsIgnoreCase("Sí")) {
                requiereReceta = true;
                break;
            }
        }

     // 3️⃣ Validaciones SOLO si hay receta
        if (requiereReceta) {
            if (txtDNI.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Debe ingresar los datos del cliente (DNI y Nombre) para productos con receta.",
                    "Datos del cliente requeridos", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Si no hay recetario temporal registrado
            if (!modelo.RecetarioTemp.estaCompleto()) {
                JOptionPane.showMessageDialog(null,
                    "Debe registrar el recetario antes de continuar.",
                    "Recetario obligatorio", JOptionPane.WARNING_MESSAGE);
                Frames.Recetario_Registro recetarioFrame = new Frames.Recetario_Registro();
                recetarioFrame.setLocationRelativeTo(null); // ✅ Centra la ventana en pantalla
                recetarioFrame.setVisible(true);
                return false;
            }
        }

        // 4️⃣ Obtener datos
        String metodoPago = comboPago.getSelectedItem().toString();
        double total = Double.parseDouble(txtTotal.getText().isEmpty() ? "0" : txtTotal.getText());

        // 5️⃣ Registrar venta completa
        boolean exito = service.registrarVentaCompleta(
                tablaVenta,
                txtDNI.getText(),
                txtNombre.getText(),
                txtTelefono.getText(),
                "",
                "",
                metodoPago,
                total
        );

        if (exito) {
        	System.out.println("✅ Venta registrada correctamente.");     
            ((DefaultTableModel) tablaVenta.getModel()).setRowCount(0);
            txtTotal.setText("0.00");
            txtDNI.setText("");
            txtNombre.setText("");
            txtTelefono.setText("");
            recetarioRegistrado = false; // reiniciar estado del recetario
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        if (exito && requiereReceta) {
            service.registrarRecetaConVenta(); // 👇 la implementaremos
        }


        return exito;
        
        
    }

    // ========================================================
    // MÉTODOS AUXILIARES PARA CONTROLAR EL RECETARIO
    // ========================================================
    public void setRecetarioRegistrado(boolean valor) {
        this.recetarioRegistrado = valor;
    }

    public boolean isRecetarioRegistrado() {
        return recetarioRegistrado;
    }
    
  
    


}
