/*package app;

import config.conexion;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection cn = conexion.getConnection();

        if (cn != null) {
            System.out.println("Conexión exitosa con la BD PeruFarma.");
        } else {
            System.out.println("Error al conectar con la BD.");
        }

        conexion.closeConnection();
    }
}
*/
/*
package app;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        System.out.println("Lista de usuarios:");
        List<Usuario> usuarios = dao.listar();
        for (Usuario u : usuarios) {
            System.out.println(u.getIdUsuario() + " - " + u.getNombre() + " " + u.getApellido() + " (" + u.getNombreUsuario() + ")");
        }
    }
}
*/
/*
package app;

import dao.ProductoDAO;
import modelo.Producto;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listar();

        System.out.println("📦 Lista de productos en BD:");
        for (Producto p : lista) {
            System.out.println(p.getIdProducto() + " - " + p.getNombre() + " | Stock: " + p.getDescripcion());
        }
    }
}*/
package app;

import javax.swing.*;
import java.awt.*;
import Panel.PanelReportes;
import controlador.CtrlReporte;

/**
 * Main temporal para probar el módulo de reportes
 * sin el dashboard completo.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Look & Feel moderno
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // Crear ventana principal de prueba
        JFrame frame = new JFrame("🧾 Reportes - PeruFarma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ====== Menú principal de reportes ======
        PanelReportes panelReportes = new PanelReportes();
        panelReportes.setPreferredSize(new Dimension(280, 0));
        frame.add(panelReportes, BorderLayout.WEST);

        // ====== Contenedor central (donde se mostrarán los subpaneles) ======
        CardLayout cardLayout = new CardLayout();
        JPanel contenedor = new JPanel(cardLayout);
        frame.add(contenedor, BorderLayout.CENTER);

        // ====== Conectar controlador principal ======
        new CtrlReporte(panelReportes, contenedor, cardLayout);

        // ====== Mostrar ventana ======
        frame.setVisible(true);
    }
}