package interfaces;

import java.util.List;
import modelo.Producto;

public interface IProductoDAO {
    public boolean registrar(Producto p);
    public boolean actualizar(Producto p);
    public boolean eliminar(int id);
    public Producto buscarPorId(int id);
    public List<Producto> listar();
    public List<Producto> listarPorCategoria(int idCategoria);
}
