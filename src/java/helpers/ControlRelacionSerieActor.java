/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;


import entidades.RelacionSerieActor;
import entidades.Serie;
import java.util.Iterator;
import persistencia.AccesoBD;

/**
 *
 * @author usuario
 */
public class ControlRelacionSerieActor {
    
    
    public Iterator obtenerRelacionSeriesActores(AccesoBD abd){
        return abd.obtenerPorClase(RelacionSerieActor.class);
    }
    
    public Iterator obtenerRelacionSeriesActoresDeSerie(AccesoBD abd, Serie s){
        return abd.buscarPorFiltro(RelacionSerieActor.class, "serie.id=="+s.getId()).iterator();
    }
    
    public Iterator obtenerRelacionSerieActor(AccesoBD abd,Long id){
        return abd.buscarPorFiltro(RelacionSerieActor.class, "actor.id=="+id).iterator();
    }
}
