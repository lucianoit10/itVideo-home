package entidades;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class Alquiler{
    protected Boolean estaAbierta=true;
    protected Double importeACobrar;    
    protected String ip;
    protected Date fechaDeAlquiler;
    protected Pelicula pelicula;
    protected String calidad;
    protected Usuario usuario;
    protected Long id;

    public Alquiler() {
    }
    
    public Alquiler(String ip,Double importeACobrar, Date fechaDeAlquiler, Pelicula pelicula, Usuario usuario,String calidad) {
        this.ip = ip;
        this.importeACobrar = importeACobrar;
        this.fechaDeAlquiler = fechaDeAlquiler;
        this.pelicula = pelicula;
        this.usuario = usuario;
        this.calidad = calidad;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    
    public Boolean getEstaAbierta() {
        return estaAbierta;
    }

    public void setEstaAbierta(Boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
    }

    public Date getFechaDeAlquiler() {
        return fechaDeAlquiler;
    }

    public void setFechaDeAlquiler(Date fechaDeAlquiler) {
        this.fechaDeAlquiler = fechaDeAlquiler;
    }

    public Double getImporteACobrar() {
        return importeACobrar;
    }

    public void setImporteACobrar(Double importeACobrar) {
        this.importeACobrar = importeACobrar;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) throws UnsupportedEncodingException {
        this.ip = new String(ip.getBytes("ISO-8859-1"), "UTF-8");
    }

    
         
    public void setId(long id) {
        this.id = id;
    }
    
    
    public Long getId() {
        return this.id;
    }
    
}
