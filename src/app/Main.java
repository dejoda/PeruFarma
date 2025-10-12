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
}
