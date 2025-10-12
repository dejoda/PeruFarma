package dao;

import config.conexion;
import interfaces.IRecetarioDAO;
import java.sql.*;
import java.util.*;
import modelo.Recetario;

public class RecetarioDAO implements IRecetarioDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Recetario r) {
        String sql = "INSERT INTO Receta_Medica (nombre_paciente, estado, nombre_medico, fecha_emision, cmp_medico, observaciones, dni_paciente) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, r.getNombrePaciente());
            ps.setString(2, r.getEstado());
            ps.setString(3, r.getNombreMedico());
            ps.setDate(4, new java.sql.Date(r.getFechaEmision().getTime()));
            ps.setString(5, r.getCmpMedico());
            ps.setString(6, r.getObservaciones());
            ps.setString(7, r.getDniPaciente());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar receta médica: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean actualizar(Recetario r) {
        String sql = "UPDATE Receta_Medica SET nombre_paciente=?, estado=?, nombre_medico=?, fecha_emision=?, cmp_medico=?, observaciones=?, dni_paciente=? "
                   + "WHERE id_receta_medica=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, r.getNombrePaciente());
            ps.setString(2, r.getEstado());
            ps.setString(3, r.getNombreMedico());
            ps.setDate(4, new java.sql.Date(r.getFechaEmision().getTime()));
            ps.setString(5, r.getCmpMedico());
            ps.setString(6, r.getObservaciones());
            ps.setString(7, r.getDniPaciente());
            ps.setInt(8, r.getIdRecetaMedica());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar receta médica: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Receta_Medica WHERE id_receta_medica=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar receta médica: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public Recetario buscarPorId(int id) {
        Recetario r = null;
        String sql = "SELECT * FROM Receta_Medica WHERE id_receta_medica=?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                r = mapearReceta(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar receta médica: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return r;
    }

    @Override
    public List<Recetario> listar() {
        List<Recetario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receta_Medica";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReceta(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar recetas médicas: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    private Recetario mapearReceta(ResultSet rs) throws SQLException {
        Recetario r = new Recetario();
        r.setIdRecetaMedica(rs.getInt("id_receta_medica"));
        r.setNombrePaciente(rs.getString("nombre_paciente"));
        r.setEstado(rs.getString("estado"));
        r.setNombreMedico(rs.getString("nombre_medico"));
        r.setFechaEmision(rs.getDate("fecha_emision"));
        r.setCmpMedico(rs.getString("cmp_medico"));
        r.setObservaciones(rs.getString("observaciones"));
        r.setDniPaciente(rs.getString("dni_paciente"));
        return r;
    }

    private void cerrarRecursos() {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
    }
}
