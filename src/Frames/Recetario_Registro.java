package Frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.CtrlVenta;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Recetario_Registro extends JFrame {
	
	private CtrlVenta ctrlVenta; // referencia al controlador

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldPaciente;
	private JTextField textFieldDniPaciente;
	private JTextField textFieldNombremedico;
	private JTextField textFieldCMPMedico;
	private JTextField textFieldFechaEmision;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Recetario_Registro frame = new Recetario_Registro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Recetario_Registro() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 192));
		panel.setBounds(0, 0, 479, 472);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblRegistroRecetario = new JLabel("Registrar Recetario :");
		lblRegistroRecetario.setForeground(new Color(255, 255, 255));
		lblRegistroRecetario.setFont(new Font("Arial", Font.BOLD, 14));
		lblRegistroRecetario.setBounds(10, 22, 167, 14);
		panel.add(lblRegistroRecetario);
		
		JLabel lblNombrePaciente = new JLabel("Nombre del Paciente :");
		lblNombrePaciente.setForeground(new Color(255, 255, 255));
		lblNombrePaciente.setFont(new Font("Arial", Font.BOLD, 11));
		lblNombrePaciente.setBounds(41, 82, 130, 14);
		panel.add(lblNombrePaciente);
		
		textFieldPaciente = new JTextField();
		textFieldPaciente.setBounds(181, 79, 260, 20);
		panel.add(textFieldPaciente);
		textFieldPaciente.setColumns(10);
		
		JLabel lblDniPaciente = new JLabel("DNI del Paciente :");
		lblDniPaciente.setForeground(Color.WHITE);
		lblDniPaciente.setFont(new Font("Arial", Font.BOLD, 11));
		lblDniPaciente.setBounds(41, 118, 130, 14);
		panel.add(lblDniPaciente);
		
		textFieldDniPaciente = new JTextField();
		textFieldDniPaciente.setColumns(10);
		textFieldDniPaciente.setBounds(181, 115, 260, 20);
		panel.add(textFieldDniPaciente);
		
		JLabel lblNombreMedico = new JLabel("Nombre del Medico :");
		lblNombreMedico.setForeground(Color.WHITE);
		lblNombreMedico.setFont(new Font("Arial", Font.BOLD, 11));
		lblNombreMedico.setBounds(41, 154, 130, 14);
		panel.add(lblNombreMedico);
		
		textFieldNombremedico = new JTextField();
		textFieldNombremedico.setColumns(10);
		textFieldNombremedico.setBounds(181, 151, 260, 20);
		panel.add(textFieldNombremedico);
		
		JLabel lblCMPMedico = new JLabel("CMP del medico :");
		lblCMPMedico.setForeground(Color.WHITE);
		lblCMPMedico.setFont(new Font("Arial", Font.BOLD, 11));
		lblCMPMedico.setBounds(41, 189, 130, 14);
		panel.add(lblCMPMedico);
		
		textFieldCMPMedico = new JTextField();
		textFieldCMPMedico.setColumns(10);
		textFieldCMPMedico.setBounds(181, 186, 260, 20);
		panel.add(textFieldCMPMedico);
		
		JLabel lblFechaEmision = new JLabel("Fecha de Emision :");
		lblFechaEmision.setForeground(Color.WHITE);
		lblFechaEmision.setFont(new Font("Arial", Font.BOLD, 11));
		lblFechaEmision.setBounds(41, 225, 130, 14);
		panel.add(lblFechaEmision);
		
		textFieldFechaEmision = new JTextField();
		textFieldFechaEmision.setBounds(181, 222, 260, 20);
		panel.add(textFieldFechaEmision);
		textFieldFechaEmision.setColumns(10);
		
		JLabel lblObservaciones = new JLabel("Observaciones :");
		lblObservaciones.setForeground(Color.WHITE);
		lblObservaciones.setFont(new Font("Arial", Font.BOLD, 11));
		lblObservaciones.setBounds(41, 259, 130, 14);
		panel.add(lblObservaciones);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setBounds(41, 284, 402, 88);
		panel.add(textArea);
		
		JButton btnNewButton = new JButton("Registrar Recetario");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(149, 383, 185, 23);
		panel.add(btnNewButton);
		
		btnNewButton.addActionListener(e -> {
		    try {
		        String nombrePaciente = textFieldPaciente.getText().trim();
		        String dniPaciente = textFieldDniPaciente.getText().trim();
		        String nombreMedico = textFieldNombremedico.getText().trim();
		        String cmpMedico = textFieldCMPMedico.getText().trim();
		        String fechaTexto = textFieldFechaEmision.getText().trim();
		        String observaciones = textArea.getText().trim();

		        if (nombrePaciente.isEmpty() || dniPaciente.isEmpty() ||
		            nombreMedico.isEmpty() || cmpMedico.isEmpty() || fechaTexto.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        java.util.Date fecha = java.sql.Date.valueOf(fechaTexto);

		        modelo.RecetarioTemp.setDatos(nombrePaciente, dniPaciente, nombreMedico, cmpMedico, fecha, observaciones);
		        System.out.println( "✅ Recetario registrado temporalmente. Se vinculará al momento del pago.");
		        dispose(); // cierra el frame

		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(null, "Error en el formato de fecha (use YYYY-MM-DD).", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		});
	}
}
