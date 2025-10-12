package interfaces;

import java.util.List;
import modelo.Usuario;

public interface IUsuarioDAO {
    public boolean registrar(Usuario usuario);
    public boolean actualizar(Usuario usuario);
    public boolean eliminar(int id);
    public Usuario buscarPorId(int id);
    public Usuario buscarPorUsuario(String nombreUsuario);
    public List<Usuario> listar();
}
