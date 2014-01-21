/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Notebook
 */
public class RelacionPreferenciaActor {
   
    protected Long id;
    protected Preferencia preferencia;
    protected Actor actor;
    
    
    
    public RelacionPreferenciaActor(Preferencia p, Actor a){
      //  id=p.getId()+""+a.getId();
        preferencia=p;
        actor=a;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
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
