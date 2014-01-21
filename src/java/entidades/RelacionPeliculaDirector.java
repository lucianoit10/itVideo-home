/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Notebook
 */
public class RelacionPeliculaDirector {
 
    protected Long id;
    protected Pelicula pelicula ;
    protected Director director;
    
    
    public RelacionPeliculaDirector(Pelicula p, Director d){
     //   id=p.getId()+""+d.getId();
        pelicula=p;
        director=d;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
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
