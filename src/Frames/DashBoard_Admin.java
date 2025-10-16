package Frames;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import Panel.Venta;
import config.conexion; // Asegúrate de tener tu clase de conexión aquí
import dao.InicioDAO;

public class DashBoard_Admin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panelContenido;
    private InicioDAO inicioDAO = new InicioDAO();
    

    public DashBoard_Admin() {
        getContentPane().setBackground(new Color(255, 255, 255));
        setTitle("Panel Administrador - PeruFarma");
        getContentPane().setLayout(null);
        setSize(980, 629);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== PANEL SUPERIOR =====
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(0, 128, 255));
        panel_1.setBounds(205, 0, 759, 54);
        getContentPane().add(panel_1);

        // ===== PANEL LATERAL =====
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 192));
        panel.setBounds(0, 0, 207, 604);
        panel.setLayout(null);
        getContentPane().add(panel);

        JLabel lblNewLabel = new JLabel("PeruFarma");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBackground(new Color(240, 240, 240));
        lblNewLabel.setBounds(48, 140, 97, 23);
        lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
        panel.add(lblNewLabel);

        JButton btnInicio = new JButton("Inicio");
        btnInicio.setBackground(new Color(255, 255, 255));
        btnInicio.setBounds(0, 200, 207, 39);
        panel.add(btnInicio);

        JButton btnVentas = new JButton("Ventas");
        btnVentas.setBackground(new Color(255, 255, 255));
        btnVentas.setBounds(0, 237, 207, 39);
        panel.add(btnVentas);

        JButton btnInventario = new JButton("Inventario");
        btnInventario.setBackground(new Color(255, 255, 255));
        btnInventario.setBounds(0, 274, 207, 39);
        panel.add(btnInventario);

        JButton btnReportes = new JButton("Reportes");
        btnReportes.setBackground(new Color(255, 255, 255));
        btnReportes.setBounds(0, 383, 207, 39);
        panel.add(btnReportes);
        
        JButton btnProveedor = new JButton("Proveedor");
        btnProveedor.setBackground(new Color(255, 255, 255));
        btnProveedor.setBounds(0, 311, 207, 39);
        panel.add(btnProveedor);
        
        JButton btnClientes = new JButton("Clientes");
        btnClientes.setBackground(new Color(255, 255, 255));
        btnClientes.setBounds(0, 347, 207, 39);
        panel.add(btnClientes);
        
        JButton BTN_cERRARCESION = new JButton("Cerrar Sesion");
        BTN_cERRARCESION.setBackground(Color.WHITE);
        BTN_cERRARCESION.setBounds(0, 420, 207, 39);
        panel.add(BTN_cERRARCESION);

        // ===== PANEL CONTENIDO =====
        panelContenido = new JPanel();
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBounds(215, 55, 736, 549);
        panelContenido.setLayout(null);
        getContentPane().add(panelContenido);

        // Mostrar panel inicio por defecto
        mostrarPanel(crearPanelInicio());

        // Botones de navegación
        btnInicio.addActionListener(e -> mostrarPanel(crearPanelInicio()));
        btnVentas.addActionListener(e -> mostrarPanel(new Venta()));
    }

    // ===== Cambiar panel dinámicamente =====
    private void mostrarPanel(JPanel nuevoPanel) {
        panelContenido.removeAll();
        nuevoPanel.setBounds(0, 0, 746, 549);
        panelContenido.add(nuevoPanel);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // ===== Panel Inicio =====
    private JPanel crearPanelInicio() {
        JPanel PanelInicio = new JPanel();
        PanelInicio.setBackground(Color.WHITE);
        PanelInicio.setLayout(null);

        JLabel txtotitulo = new JLabel("Bienvenido Administrador:");
        txtotitulo.setBounds(20, 11, 300, 21);
        txtotitulo.setFont(new Font("Arial Black", Font.PLAIN, 14));
        PanelInicio.add(txtotitulo);

        JLabel txto1 = new JLabel("Te damos la bienvenida a FarmaPerú, el software de gestión de farmacia.");
        txto1.setFont(new Font("Arial", Font.PLAIN, 11));
        txto1.setBounds(20, 43, 451, 26);
        PanelInicio.add(txto1);

        // ===== BOTONES =====
        JButton btnVentasdeldia = new JButton("Ventas del día");
        btnVentasdeldia.setBackground(new Color(255, 255, 255));
        btnVentasdeldia.setBounds(20, 139, 119, 34);
        PanelInicio.add(btnVentasdeldia);

        JButton btnStockBajo = new JButton("Stock Bajo");
        btnStockBajo.setBackground(new Color(255, 255, 255));
        btnStockBajo.setBounds(158, 139, 119, 34);
        PanelInicio.add(btnStockBajo);

        JButton btnPorVencer = new JButton("Por vencer");
        btnPorVencer.setBackground(new Color(255, 255, 255));
        btnPorVencer.setBounds(298, 139, 119, 34);
        PanelInicio.add(btnPorVencer);

        // ===== TABLA =====
        JTable tabla = new JTable();
        tabla.setFont(new Font("Arial", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setEnabled(false);
        scroll.setBounds(20, 200, 700, 320);
        PanelInicio.add(scroll);

        // Eventos
        btnVentasdeldia.addActionListener(e -> inicioDAO.actualizarTabla(tabla, "ventas_dia"));
        btnStockBajo.addActionListener(e -> inicioDAO.actualizarTabla(tabla, "stock_bajo"));
        btnPorVencer.addActionListener(e -> inicioDAO.actualizarTabla(tabla, "por_vencer"));

        // Por defecto: ventas del día
        inicioDAO.actualizarTabla(tabla, "ventas_dia");
        
        JLabel lblRegistrarYAdministrar = new JLabel("registrar y administrar productos, proveedores e inventario, así como gestionar las citas de ");
        lblRegistrarYAdministrar.setFont(new Font("Arial", Font.PLAIN, 11));
        lblRegistrarYAdministrar.setBounds(20, 63, 474, 26);
        PanelInicio.add(lblRegistrarYAdministrar);
        
        JLabel lblAtencinAClientes = new JLabel("atención a clientes de manera rápida y organizada.");
        lblAtencinAClientes.setFont(new Font("Arial", Font.PLAIN, 11));
        lblAtencinAClientes.setBounds(20, 89, 432, 26);
        PanelInicio.add(lblAtencinAClientes);

        return PanelInicio;
    }

    // ===== Método para actualizar la tabla =====
    private void actualizarTabla(JTable tabla, String tipo) {
        String query = "";

        switch (tipo) {
            case "ventas_dia":
                query = """
                    SELECT 
                        v.id_venta AS ID_Venta,
                        COALESCE(c.nombre, 'Sin Cliente') AS Cliente,
                        v.hora_venta AS Hora,
                        p.nombre AS Producto,
                        dv.cantidad AS Cantidad,
                        dv.subtotal AS Subtotal,
                        v.total AS Total
                    FROM Venta v
                    JOIN Detalle_Venta dv ON v.id_venta = dv.id_venta
                    JOIN Producto p ON dv.id_producto = p.id_producto
                    LEFT JOIN Cliente c ON v.id_cliente = c.id_cliente
                    WHERE DATE(v.fecha_venta) = CURDATE();
                    """;
                break;

            case "stock_bajo":
                query = """
                    SELECT 
                        i.id_inventario AS ID,
                        p.nombre AS Producto,
                        i.stock AS Stock,
                        CASE 
                            WHEN i.stock <= i.stock_minimo THEN 'Bajo'
                            ELSE 'Normal'
                        END AS Estado
                    FROM Inventario i
                    JOIN Producto p ON i.id_producto = p.id_producto;
                    """;
                break;

            case "por_vencer":
                query = """
                    SELECT 
                        p.id_producto AS ID,
                        p.nombre AS Producto,
                        p.fecha_vencimiento AS Fecha_Vencimiento,
                        CASE 
                            WHEN p.fecha_vencimiento <= CURDATE() THEN 'Vencido'
                            WHEN p.fecha_vencimiento <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) THEN 'Por vencer'
                            ELSE 'Vigente'
                        END AS Estado
                    FROM Producto p
                    WHERE p.fecha_vencimiento IS NOT NULL;
                    """;
                break;

            default:
                JOptionPane.showMessageDialog(null, "⚠️ Tipo de tabla desconocido: " + tipo);
                return;
        }

        // ===== Ejecutar consulta =====
        try (Connection cn = conexion.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();
            DefaultTableModel modelo = new DefaultTableModel();

            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DashBoard_Admin frame = new DashBoard_Admin();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
