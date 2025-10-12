package modelo;

import java.util.Date;

public class Recetario {
    private int idRecetaMedica;
    private String nombrePaciente;
    private String estado;
    private String nombreMedico;
    private Date fechaEmision;
    private String cmpMedico;
    private String observaciones;
    private String dniPaciente;

    // Getters y Setters
    public int getIdRecetaMedica() { return idRecetaMedica; }
    public void setIdRecetaMedica(int idRecetaMedica) { this.idRecetaMedica = idRecetaMedica; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }

    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getCmpMedico() { return cmpMedico; }
    public void setCmpMedico(String cmpMedico) { this.cmpMedico = cmpMedico; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getDniPaciente() { return dniPaciente; }
    public void setDniPaciente(String dniPaciente) { this.dniPaciente = dniPaciente; }
}
