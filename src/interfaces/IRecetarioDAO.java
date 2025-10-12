package interfaces;

import java.util.List;
import modelo.Recetario;

public interface IRecetarioDAO {
    public boolean registrar(Recetario r);
    public boolean actualizar(Recetario r);
    public boolean eliminar(int id);
    public Recetario buscarPorId(int id);
    public List<Recetario> listar();
}
