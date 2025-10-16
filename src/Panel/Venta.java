package Panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

import controlador.CtrlVenta;
import dao.VentaDAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Venta extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtfieldproducto;
    private JTable tableInventariobusqueda;
    private JTextField txtfieldcliente;
    private JTextField txtfieldNombre;
    private JTable tableVenta;
    private JTextField txtTotal;
    private JComboBox<String> comboBoxPago;
    private CtrlVenta ctrlVenta = new CtrlVenta();
    private JTextField txtfieldTelefono;

    public Venta() {
        setBackground(new Color(255, 255, 255));
        setLayout(null);

        JLabel lblTitulo = new JLabel("Registro de ventas :");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBounds(10, 11, 142, 27);
        add(lblTitulo);

        JLabel lblProducto = new JLabel("Producto :");
        lblProducto.setFont(new Font("Arial", Font.PLAIN, 11));
        lblProducto.setBounds(30, 51, 61, 14);
        add(lblProducto);

        txtfieldproducto = new JTextField();
        txtfieldproducto.setBounds(86, 48, 152, 20);
        add(txtfieldproducto);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(Color.WHITE);
        btnBuscar.setBounds(249, 47, 93, 23);
        add(btnBuscar);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(Color.WHITE);
        btnAgregar.setBounds(592, 47, 125, 23);
        add(btnAgregar);

        add(new JSeparator() {{
            setBounds(30, 95, 706, 2);
            setBackground(Color.BLACK);
        }});

        JLabel lblInventario = new JLabel("Inventario :");
        lblInventario.setFont(new Font("Arial", Font.BOLD, 12));
        lblInventario.setBounds(30, 76, 80, 14);
        add(lblInventario);

        // 🔹 Tabla de inventario
        tableInventariobusqueda = new JTable();
        tableInventariobusqueda.setBorder(new LineBorder(Color.BLACK));
        JScrollPane scrollInventario = new JScrollPane(tableInventariobusqueda);
        scrollInventario.setBounds(30, 108, 706, 156);
        add(scrollInventario);

        JLabel lblVenta = new JLabel("VENTA :");
        lblVenta.setFont(new Font("Arial", Font.BOLD, 12));
        lblVenta.setBounds(30, 269, 60, 20);
        add(lblVenta);

        add(new JSeparator() {{
            setBounds(30, 287, 706, 2);
            setBackground(Color.BLACK);
        }});

        JLabel lbldni = new JLabel("DNI :");
        lbldni.setFont(new Font("Arial", Font.BOLD, 11));
        lbldni.setBounds(115, 295, 35, 17);
        add(lbldni);

        txtfieldcliente = new JTextField();
        txtfieldcliente.setBounds(142, 293, 80, 20);
        add(txtfieldcliente);

        JLabel lblNombre = new JLabel("Nombre :");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNombre.setBounds(232, 296, 57, 14);
        add(lblNombre);

        txtfieldNombre = new JTextField();
        txtfieldNombre.setBounds(286, 293, 161, 20);
        add(txtfieldNombre);

        add(new JSeparator() {{
            setBounds(30, 319, 706, 2);
            setBackground(Color.BLACK);
        }});

     // 🔹 Tabla de venta (ahora incluye columna Receta)
        DefaultTableModel modeloVenta = new DefaultTableModel(
            new Object[]{"ID", "Producto", "Receta", "Cantidad", "Precio Unit", "Precio IGV", "Precio Final"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // solo "Cantidad" editable
            }
        };

        tableVenta = new JTable(modeloVenta);
        tableVenta.setBorder(new LineBorder(Color.BLACK));
        JScrollPane scrollVenta = new JScrollPane(tableVenta);
        scrollVenta.setBounds(29, 330, 572, 164);
        add(scrollVenta);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setBounds(611, 332, 125, 23);
        add(btnEliminar);

        JButton btnEliminarTodo = new JButton("Eliminar Todo");
        btnEliminarTodo.setBackground(Color.WHITE);
        btnEliminarTodo.setBounds(611, 366, 125, 23);
        add(btnEliminarTodo);

        comboBoxPago = new JComboBox<>(new String[]{"Tarjeta", "Efectivo"});
        comboBoxPago.setBackground(Color.WHITE);
        comboBoxPago.setBounds(611, 400, 125, 22);
        add(comboBoxPago);

        JButton btnPagar = new JButton("Pagar");
        btnPagar.setBackground(Color.WHITE);
        btnPagar.setBounds(611, 449, 125, 45);
        add(btnPagar);

        JLabel lblTotal = new JLabel("Total :");
        lblTotal.setBounds(30, 505, 46, 14);
        add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(66, 502, 105, 20);
        add(txtTotal);

        JButton btnComprobante = new JButton("Comprobante");
        btnComprobante.setBackground(Color.WHITE);
        btnComprobante.setBounds(181, 501, 125, 23);
        add(btnComprobante);

        // ===================== EVENTOS =====================
        ctrlVenta.buscarYMostrarProductos(tableInventariobusqueda, ""); // carga inicial
        btnBuscar.addActionListener(e -> ctrlVenta.buscarYMostrarProductos(tableInventariobusqueda, txtfieldproducto.getText().trim()));
        btnAgregar.addActionListener(e -> ctrlVenta.agregarProducto(tableInventariobusqueda, tableVenta, txtTotal));
        ctrlVenta.detectarCambiosCantidad(tableVenta, txtTotal);
        btnEliminar.addActionListener(e -> ctrlVenta.eliminarProducto(tableVenta, txtTotal));
        btnEliminarTodo.addActionListener(e -> ctrlVenta.eliminarTodo(tableVenta, txtTotal));
     // 🔹 BOTÓN PAGAR
        btnPagar.addActionListener(e -> {
            boolean exito = ctrlVenta.registrarVenta(
                tableVenta,
                txtfieldcliente,
                txtfieldNombre,
                txtfieldTelefono,
                comboBoxPago,
                txtTotal
            );

            if (exito) {
                JOptionPane.showMessageDialog(null, "✅ Venta completada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                ctrlVenta.buscarYMostrarProductos(tableInventariobusqueda, ""); // 🔁 refrescar inventario
            }
        });
        
        
        btnComprobante.addActionListener(e -> {
            try {
                VentaDAO dao = new VentaDAO();
                int ultimaVenta = dao.obtenerUltimaVenta();

                if (ultimaVenta <= 0) {
                    System.err.println("⚠️ No hay ventas registradas todavía.");
                    return;
                }

                String ruta = "comprobantes/Comprobante_" + ultimaVenta + ".pdf";
                util.ComprobantePDF.generarPDF(ultimaVenta, ruta);
                System.out.println("✅ Comprobante generado correctamente en: " + ruta);

            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("❌ Error al generar el comprobante.");
            }
        });
        
        
    

        
        JLabel lblcliente = new JLabel("CLIENTE :");
        lblcliente.setFont(new Font("Arial", Font.BOLD, 11));
        lblcliente.setBounds(40, 296, 65, 14);
        add(lblcliente);
        
        JLabel lbltelefono = new JLabel("Telefono :");
        lbltelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbltelefono.setBounds(457, 296, 71, 14);
        add(lbltelefono);
        
        txtfieldTelefono = new JTextField();
        txtfieldTelefono.setBounds(518, 293, 83, 20);
        add(txtfieldTelefono);
        txtfieldTelefono.setColumns(10);
        
        
        
        
        JButton btnRecetario = new JButton("Recetario");
        btnRecetario.setBackground(new Color(255, 255, 255));
        btnRecetario.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Abrir el JFrame del recetario
                Frames.Recetario_Registro recetarioFrame = new Frames.Recetario_Registro();
                recetarioFrame.setLocationRelativeTo(null); // Centrar la ventana
                recetarioFrame.setVisible(true); // Mostrar la ventana
        	}
        });
        btnRecetario.setBounds(611, 292, 125, 23);
        add(btnRecetario);
    }
    
    
}


