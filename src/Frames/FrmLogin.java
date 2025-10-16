package Frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FrmLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JTextField textFieldContraseña;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLogin frame = new FrmLogin();
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
	public FrmLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 581);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(111, 168, 220));
		panel_1.setBounds(199, 0, 432, 542);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblUser = new JLabel("");
		lblUser.setBounds(154, 56, 128, 158);
		panel_1.add(lblUser);
		lblUser.setIcon(new ImageIcon(FrmLogin.class.getResource("/img/user4.png")));
		
		JLabel lblFrase = new JLabel("\"Tu atencion, nuestra prioridad\"");
		lblFrase.setForeground(new Color(255, 255, 255));
		lblFrase.setBackground(new Color(255, 255, 255));
		lblFrase.setFont(new Font("Arial", Font.BOLD, 14));
		lblFrase.setBounds(107, 39, 234, 23);
		panel_1.add(lblFrase);
		
		JLabel lblUsuario = new JLabel("Usuario :");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsuario.setBounds(107, 228, 94, 14);
		panel_1.add(lblUsuario);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(107, 253, 234, 28);
		panel_1.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña :");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setFont(new Font("Arial", Font.BOLD, 14));
		lblContraseña.setBounds(107, 301, 113, 14);
		panel_1.add(lblContraseña);
		
		textFieldContraseña = new JTextField();
		textFieldContraseña.setColumns(10);
		textFieldContraseña.setBounds(107, 326, 234, 28);
		panel_1.add(textFieldContraseña);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 11));
		btnNewButton.setBounds(154, 389, 128, 35);
		panel_1.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(43, 120, 228));
		panel.setBounds(0, 0, 199, 542);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("PeruFarma");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
		lblNombre.setBounds(54, 241, 84, 34);
		panel.add(lblNombre);

	}
}
