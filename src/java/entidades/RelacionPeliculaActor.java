/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Notebook
 */
public class RelacionPeliculaActor {
   
    protected Long id;
    protected Pelicula pelicula ;
    protected Actor actor;
    
    
    
    public RelacionPeliculaActor(Pelicula p, Actor a){
      //  id=p.getId()+""+a.getId();
        pelicula=p;
        actor=a;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    
}
