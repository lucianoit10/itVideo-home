package entidades;

import java.util.Date;

public class Comentario{
    protected String texto;
    protected String remitente;
   // protected boolean aceptado;
    protected Long id;
    protected Date fecha;
    protected Pelicula pelicula;
    
    
    public Comentario(String text, String remit,Pelicula pelicula) {
        this.texto = text;
        this.remitente = remit;
        this.pelicula = pelicula;
      //  this.aceptado = false;
        this.fecha = new Date();
    }

    public Comentario() {
    }
    
    /* Get y Set : aceptado*/
 /*   public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }*/
    /* Fin Get y Set : aceptado*/


    /* Get y Set : remitente*/
    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }
    /* Fin Get y Set : remitente*/

    /* Get y Set : texto*/
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    /* Fin Get y Set : texto*/

    /* Get y Set : perteneceA*/
    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula perteneceA) {
        this.pelicula = perteneceA;
    }
    /*Fin Get y Set : perteneceA*/

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    /*Get y Set : id*/
    public Long getId() {
        return this.id;
    }

    public void setId(Long idN) {
        this.id = idN;
    }
    /*Fin Get y Set : id*/

 /*   @Override
    public int compareTo(Object o) {
        Boolean prim = this.aceptado;
        Boolean seg = ((Comentario)o).aceptado;
        if (prim==seg){
            return 0;
        }else{
            if (prim){
                return 1;
            }else{
                return -1;
            }
        }
    }*/
}
