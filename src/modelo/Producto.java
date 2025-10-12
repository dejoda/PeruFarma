package modelo;

import java.util.Date;

public class Producto {
    private int idProducto;
    private String presentacion;
    private String nombre;
    private int idCategoria;
    private String lote;
    private boolean llevarReceta;
    private String descripcion;
    private double precioConIgv;
    private Date fechaVencimiento;
    private int idProveedor;
    private double precioVenta;
    private double precioCompra;
    private String laboratorio;
    private String registroSanitario;
    private String contenido;
    private boolean estado;

    // Getters y setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public boolean isLlevarReceta() { return llevarReceta; }
    public void setLlevarReceta(boolean llevarReceta) { this.llevarReceta = llevarReceta; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioConIgv() { return precioConIgv; }
    public void setPrecioConIgv(double precioConIgv) { this.precioConIgv = precioConIgv; }

    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public String getRegistroSanitario() { return registroSanitario; }
    public void setRegistroSanitario(String registroSanitario) { this.registroSanitario = registroSanitario; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
