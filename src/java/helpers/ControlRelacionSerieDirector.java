/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import entidades.RelacionSerieDirector;
import entidades.Serie;
import java.util.Iterator;
import persistencia.AccesoBD;

/**
 *
 * @author usuario
 */
public class ControlRelacionSerieDirector{
    
    
    public Iterator obtenerRelacionSeriesDirectores(AccesoBD abd){
        return abd.obtenerPorClase(RelacionSerieDirector.class);
    }
    
    public Iterator obtenerRelacionSeriesDirectoresDeSerie(AccesoBD abd, Serie s){
        return abd.buscarPorFiltro(RelacionSerieDirector.class, "serie.id=="+s.getId()).iterator();
    }
    
    public Iterator obtenerRelacionSerieDirector(AccesoBD abd,Long id){
        return abd.buscarPorFiltro(RelacionSerieDirector.class, "director.id=="+id).iterator();
    }
    
}
