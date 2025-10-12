package interfaces;

import java.util.List;
import modelo.Cliente;

public interface IClienteDAO {
    public boolean registrar(Cliente c);
    public boolean actualizar(Cliente c);
    public boolean eliminar(int id);
    public Cliente buscarPorId(int id);
    public Cliente buscarPorDni(String dni);
    public List<Cliente> listar();
}
