/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Luciano
 */
public class Preferencia {
    
    
    protected Long id;
    protected boolean peliculas = true;
    protected boolean series = false;
    protected boolean cualquiera = false;
    protected boolean estrenos = false;
    protected boolean clasicos = false;
    protected boolean bienPuntuadas = false;
    protected Usuario usuario;
    
    
    public Preferencia() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public boolean isBienPuntuadas() {
        return bienPuntuadas;
    }

    public void setBienPuntuadas(boolean bienPuntuadas) {
        this.bienPuntuadas = bienPuntuadas;
    }

    public boolean isClasicos() {
        return clasicos;
    }

    public void setClasicos(boolean clasicos) {
        this.clasicos = clasicos;
    }

    public boolean isCualquiera() {
        return cualquiera;
    }

    public void setCualquiera(boolean cualquiera) {
        this.cualquiera = cualquiera;
    }

    public boolean isEstrenos() {
        return estrenos;
    }

    public void setEstrenos(boolean estrenos) {
        this.estrenos = estrenos;
    }

    public boolean isPeliculas() {
        return peliculas;
    }

    public void setPeliculas(boolean peliculas) {
        this.peliculas = peliculas;
    }

    public boolean isSeries() {
        return series;
    }

    public void setSeries(boolean series) {
        this.series = series;
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
        
}
