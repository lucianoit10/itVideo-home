package helpers;

import entidades.Pelicula;
import entidades.RelacionPeliculaDirector;
import java.util.Iterator;
import persistencia.AccesoBD;
  

public class ControlRelacionPeliculaDirector {
    
    
    public Iterator obtenerRelacionPeliculasDirectores(AccesoBD abd){
        return abd.obtenerPorClase(RelacionPeliculaDirector.class);
    }
    
    public Iterator obtenerRelacionPeliculaDirector(AccesoBD abd,Long id){
        return abd.buscarPorFiltro(RelacionPeliculaDirector.class, "director.id=="+id).iterator();
    }
    
    public Iterator obtenerRelacionPeliculaDirectoresDePelicula(AccesoBD abd,Pelicula p){
        return abd.buscarPorFiltro(RelacionPeliculaDirector.class, "pelicula.id=="+p.getId()).iterator();
    }
    
}
