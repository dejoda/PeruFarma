package interfaces;

import java.util.List;
import modelo.Proveedor;

public interface IProveedorDAO {
    public boolean registrar(Proveedor p);
    public boolean actualizar(Proveedor p);
    public boolean eliminar(int id);
    public Proveedor buscarPorId(int id);
    public Proveedor buscarPorRuc(String ruc);
    public List<Proveedor> listar();
}
