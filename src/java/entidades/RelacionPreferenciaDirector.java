/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Notebook
 */
public class RelacionPreferenciaDirector {
   
    protected Long id;
    protected Preferencia preferencia;
    protected Director director;
    
    
    
    public RelacionPreferenciaDirector(Preferencia p, Director d){
      //  id=p.getId()+""+a.getId();
        preferencia=p;
        director=d;
    }

    public Director getActor() {
        return director;
    }

    public void setActor(Director director) {
        this.director = director;
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
