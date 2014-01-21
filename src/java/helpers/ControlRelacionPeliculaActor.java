package helpers;

import entidades.Pelicula;
import entidades.RelacionPeliculaActor;
import java.util.Iterator;
import persistencia.AccesoBD;
  

public class ControlRelacionPeliculaActor{
    
    
    public Iterator obtenerRelacionPeliculasActores(AccesoBD abd){
        return abd.obtenerPorClase(RelacionPeliculaActor.class);
    }
    
    public Iterator obtenerRelacionPeliculaActor(AccesoBD abd,Long id){
        return abd.buscarPorFiltro(RelacionPeliculaActor.class, "actor.id=="+id).iterator();
    }
    
    public Iterator obtenerRelacionPeliculaActoresDePelicula(AccesoBD abd,Pelicula p){
        return abd.buscarPorFiltro(RelacionPeliculaActor.class, "pelicula.id=="+p.getId()).iterator();
    }
}
