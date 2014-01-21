/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Notebook
 */
public class RelacionPreferenciaGenero {
   
    protected Long id;
    protected Preferencia preferencia;
    protected Genero genero;
    
    
    
    public RelacionPreferenciaGenero(Preferencia p, Genero g){
      //  id=p.getId()+""+a.getId();
        preferencia=p;
        genero=g;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero g) {
        this.genero = g;
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public void setPelicula(Preferencia p) {
        this.preferencia = p;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    
}
