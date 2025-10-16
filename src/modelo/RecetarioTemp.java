package modelo;

import java.util.Date;

public class RecetarioTemp {
    private static String nombrePaciente;
    private static String dniPaciente;
    private static String nombreMedico;
    private static String cmpMedico;
    private static Date fechaEmision;
    private static String observaciones;
    private static boolean activo = false;

    public static void setDatos(String nombrePaciente, String dniPaciente, String nombreMedico,
                                String cmpMedico, Date fechaEmision, String observaciones) {
        RecetarioTemp.nombrePaciente = nombrePaciente;
        RecetarioTemp.dniPaciente = dniPaciente;
        RecetarioTemp.nombreMedico = nombreMedico;
        RecetarioTemp.cmpMedico = cmpMedico;
        RecetarioTemp.fechaEmision = fechaEmision;
        RecetarioTemp.observaciones = observaciones;
        activo = true;
    }

    public static boolean estaCompleto() {
        return activo &&
               nombrePaciente != null && !nombrePaciente.trim().isEmpty() &&
               dniPaciente != null && !dniPaciente.trim().isEmpty() &&
               nombreMedico != null && !nombreMedico.trim().isEmpty() &&
               cmpMedico != null && !cmpMedico.trim().isEmpty() &&
               fechaEmision != null;
    }

    public static void limpiar() {
        nombrePaciente = null;
        dniPaciente = null;
        nombreMedico = null;
        cmpMedico = null;
        fechaEmision = null;
        observaciones = null;
        activo = false;
    }

    // Getters
    public static String getNombrePaciente() { return nombrePaciente; }
    public static String getDniPaciente() { return dniPaciente; }
    public static String getNombreMedico() { return nombreMedico; }
    public static String getCmpMedico() { return cmpMedico; }
    public static Date getFechaEmision() { return fechaEmision; }
    public static String getObservaciones() { return observaciones; }
}
