package Panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class Inventario_Admin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtfieldnombre;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTable table;
	private JTextField textField_8;

	public Inventario_Admin() {
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Inventario :");
		lblTitulo.setBounds(10, 11, 142, 27);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblTitulo);
		
		JLabel lblNewLabel = new JLabel("Producto :");
		lblNewLabel.setBounds(10, 46, 61, 14);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 11));
		add(lblNewLabel);
		
		JLabel lblnombre = new JLabel("Nombre :");
		lblnombre.setBounds(10, 71, 46, 14);
		add(lblnombre);
		
		txtfieldnombre = new JTextField();
		txtfieldnombre.setBounds(66, 68, 108, 20);
		add(txtfieldnombre);
		txtfieldnombre.setColumns(10);
		
		JLabel lblPresentacion = new JLabel("Presentacion :");
		lblPresentacion.setBounds(184, 71, 87, 14);
		add(lblPresentacion);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(260, 67, 108, 22);
		add(comboBox);
		
		JLabel lblContenido = new JLabel("Contenido :");
		lblContenido.setBounds(378, 71, 78, 14);
		add(lblContenido);
		
		textField = new JTextField();
		textField.setBounds(439, 68, 101, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblLaboratorio = new JLabel("Laboratorio :");
		lblLaboratorio.setBounds(550, 71, 78, 14);
		add(lblLaboratorio);
		
		textField_1 = new JTextField();
		textField_1.setBounds(621, 68, 111, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblStock = new JLabel("Stock :");
		lblStock.setBounds(10, 106, 46, 14);
		add(lblStock);
		
		textField_2 = new JTextField();
		textField_2.setBounds(66, 99, 78, 20);
		add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblFecha_vencimiento = new JLabel("Fecha vencible :");
		lblFecha_vencimiento.setBounds(154, 106, 78, 14);
		add(lblFecha_vencimiento);
		
		textField_3 = new JTextField();
		textField_3.setBounds(240, 103, 87, 20);
		add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo :");
		lblTipo.setBounds(335, 106, 46, 14);
		add(lblTipo);
		
		textField_4 = new JTextField();
		textField_4.setBounds(365, 103, 108, 20);
		add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblRecetario = new JLabel("Recetario :");
		lblRecetario.setBounds(483, 106, 78, 14);
		add(lblRecetario);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Si");
		chckbxNewCheckBox.setBackground(new Color(255, 255, 255));
		chckbxNewCheckBox.setBounds(565, 102, 54, 23);
		add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("No");
		chckbxNewCheckBox_1.setBackground(new Color(255, 255, 255));
		chckbxNewCheckBox_1.setBounds(639, 102, 61, 23);
		add(chckbxNewCheckBox_1);
		
		JLabel lblProveedor = new JLabel("Proveedor :");
		lblProveedor.setBounds(10, 136, 78, 14);
		add(lblProveedor);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(76, 130, 123, 22);
		add(comboBox_1);
		
		JLabel lblRegistroSanitario = new JLabel("Registro Sanitario :");
		lblRegistroSanitario.setBounds(209, 136, 108, 14);
		add(lblRegistroSanitario);
		
		textField_5 = new JTextField();
		textField_5.setBounds(315, 134, 108, 20);
		add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblLote = new JLabel("Lote :");
		lblLote.setBounds(438, 136, 35, 14);
		add(lblLote);
		
		textField_6 = new JTextField();
		textField_6.setBounds(475, 134, 86, 20);
		add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblPrecio_Unit = new JLabel("Precio Unitario :");
		lblPrecio_Unit.setBounds(575, 136, 87, 14);
		add(lblPrecio_Unit);
		
		textField_7 = new JTextField();
		textField_7.setBounds(656, 133, 76, 20);
		add(textField_7);
		textField_7.setColumns(10);
		
		JPanel panelBotonAgregar = new JPanel();
		panelBotonAgregar.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonAgregar.setBackground(new Color(0, 128, 192));
		panelBotonAgregar.setBounds(10, 174, 123, 27);
		add(panelBotonAgregar);
		panelBotonAgregar.setLayout(null);
		
		JLabel lblAgregar = new JLabel("Agregar ");
		lblAgregar.setFont(new Font("Arial", Font.BOLD, 11));
		lblAgregar.setForeground(new Color(255, 255, 255));
		lblAgregar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgregar.setBounds(0, 0, 123, 25);
		panelBotonAgregar.add(lblAgregar);
		
		JLabel lblTablaProductos = new JLabel("Tabla de productos :");
		lblTablaProductos.setFont(new Font("Arial", Font.BOLD, 14));
		lblTablaProductos.setBounds(10, 224, 164, 14);
		add(lblTablaProductos);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBounds(10, 252, 722, 221);
		add(table);
		
		JLabel lblProductoBuscar = new JLabel("Producto :");
		lblProductoBuscar.setBounds(10, 498, 61, 14);
		add(lblProductoBuscar);
		
		textField_8 = new JTextField();
		textField_8.setBounds(66, 495, 133, 20);
		add(textField_8);
		textField_8.setColumns(10);
		
		JPanel panelBotonBuscar = new JPanel();
		panelBotonBuscar.setLayout(null);
		panelBotonBuscar.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonBuscar.setBackground(new Color(0, 128, 192));
		panelBotonBuscar.setBounds(209, 491, 123, 27);
		add(panelBotonBuscar);
		
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setForeground(new Color(255, 255, 255));
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 11));
		lblBuscar.setBounds(0, 0, 123, 25);
		panelBotonBuscar.add(lblBuscar);
		
		JPanel panelbotonmostrar = new JPanel();
		panelbotonmostrar.setLayout(null);
		panelbotonmostrar.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelbotonmostrar.setBackground(new Color(0, 128, 192));
		panelbotonmostrar.setBounds(417, 491, 87, 27);
		add(panelbotonmostrar);
		
		JLabel lblmostrar = new JLabel("Buscar");
		lblmostrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblmostrar.setForeground(Color.WHITE);
		lblmostrar.setFont(new Font("Arial", Font.BOLD, 11));
		lblmostrar.setBounds(0, 0, 87, 25);
		panelbotonmostrar.add(lblmostrar);
		
		JPanel PanelBotonActualizar = new JPanel();
		PanelBotonActualizar.setLayout(null);
		PanelBotonActualizar.setBorder(new LineBorder(new Color(0, 0, 0)));
		PanelBotonActualizar.setBackground(new Color(0, 128, 192));
		PanelBotonActualizar.setBounds(532, 491, 87, 27);
		add(PanelBotonActualizar);
		
		JLabel lblActualizar = new JLabel("Actualizar");
		lblActualizar.setHorizontalAlignment(SwingConstants.CENTER);
		lblActualizar.setForeground(Color.WHITE);
		lblActualizar.setFont(new Font("Arial", Font.BOLD, 11));
		lblActualizar.setBounds(0, 0, 87, 25);
		PanelBotonActualizar.add(lblActualizar);
		
		JPanel panelBotonEliminar = new JPanel();
		panelBotonEliminar.setLayout(null);
		panelBotonEliminar.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonEliminar.setBackground(new Color(0, 128, 192));
		panelBotonEliminar.setBounds(645, 491, 87, 27);
		add(panelBotonEliminar);
		
		JLabel lblEliminar = new JLabel("Eliminar");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Arial", Font.BOLD, 11));
		lblEliminar.setBounds(0, 0, 87, 25);
		panelBotonEliminar.add(lblEliminar);

	}
}
