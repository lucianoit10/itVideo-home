package persistencia;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.jdo.*;

public class AccesoBD{
    protected Singleton singleton;
    protected PersistenceManager persistencia;
    //-------------------------------------------
    protected PersistenceManagerFactory pmf;
    //-------------------------------------------
    protected Transaction tx;
    
    /**
     * Constructor necesario para la creacion de la base de datos.
     * @param s parametro para distiguir un constructor de otro.
     */
    public  AccesoBD(String s){        
    }
    
    /**
     * Constructor de la clase que nos garantiza que sea una unica instancia
     * a traves del singleton.
     */
    public  AccesoBD(){
        singleton = Singleton.getInstance();
    //-------------------------------------------
    //  PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("jpox.properties");
        pmf = JDOHelper.getPersistenceManagerFactory("jpox.properties");
    //-------------------------------------------
    //  PersistenceManager pm = pmf.getPersistenceManager();
    //  persistencia = pm;
    }
    
    /**
     * Constructor con PersistenceManager ya existente.
     * @param pm 
     */
    public AccesoBD(PersistenceManager pm){
        persistencia = pm;
   }

    /**
     * Actualiza el valor del PersistenceManager.
     * @param pm 
     */
    public void setPersistencia(PersistenceManager pm){
        persistencia = pm;
    }
    
    /**
     * Obtiene el estado actual de la persistencia.
     * @return el valor del PersistenceManager.
     */
    public PersistenceManager getPersistencia(){
       return persistencia;
    }

    /**
     * Metodo que mantiene la persistencia de los objetos.
     * @param obj Objeto el cual se actualiza y se mantiene persistente.
     */
     public Object hacerPersistente(Object obj){
        return persistencia.makePersistent(obj);
     }

     /**
      * Metodo que elimina de la persistencia el objeto pasado por parametro.
      * @param obj Objeto a eliminar.
      */
     public void eliminar(Object obj){
        persistencia.deletePersistent(obj);
     }

     /**
      * Abre una transaccion.
      */
     public void iniciarTransaccion(){
         try {
             if (tx !=null && tx.isActive()){
                 concretarTransaccion();
             }
             //-----------------------------------------
             persistencia = pmf.getPersistenceManager();
             //-----------------------------------------
             //setTransactionIsolation ???
             tx = persistencia.currentTransaction();
             tx.begin();
         }
         catch ( Exception E){
             System.out.println("Error al iniciar la transaccion: \n");
             E.printStackTrace();
         }
     }

     /**
      * Concreta todas las actualizaciones de la transaccion en la persistencia.
      */
     public void concretarTransaccion(){
         try {
           // System.out.println("estado de la transaccion" + tx.isActive());
            tx.commit();
           //  System.out.println("despues de concretar transaccion ");
            //----------------------------------------------
            if (!persistencia.isClosed())
                persistencia.close();
            //----------------------------------------------
         }catch (Exception e){
             try {
                 rollbackTransaccion();
                 
             }catch (Exception a){
                 System.out.println("error al concretar");
                 a.printStackTrace();
             }
         }
     }
     
     /**
      * Descarta todas las actualizaciones realizadas en la transaccion.
      */
     public void rollbackTransaccion(){
         try {
            //System.out.println("estado de la transaccion en el rollback" + tx.isActive());
             tx.rollback();
            // System.out.println("despues del rollback de transaccion ");
            //----------------------------------------------
            if (!persistencia.isClosed())
                persistencia.close();
            //----------------------------------------------
         }catch (Exception a){
             System.out.println("error al concretar");
             a.printStackTrace();
         }
     }

     /**
      * es necesario que se encuentre instalado el motor de base de datos 
      * MySql con usuario "root" y password "".
      * 
      * En caso en que no este creada la base de datos la crea.
      * @throws SQLException
      * @throws InstantiationException
      * @throws IllegalAccessException
      * @throws ClassNotFoundException 
      */
     public void CrearBaseDeDatos() throws SQLException, FileNotFoundException, IOException {
         File dir=new File("webapps/pruebaPersistencia/WEB-INF/classes/jpox.properties");
                    System.out.println("dire path "+dir.getAbsolutePath()); 
         FileReader fr = new FileReader(dir.getAbsolutePath());
          BufferedReader bf = new BufferedReader(fr);
          String cadena = "";
          String user = "";
          String pass = "";
          String puerto = "";
          String nameDB = "";
          while ((cadena=bf.readLine())!=null) {
              if (cadena.split("javax.jdo.option.ConnectionUserName=").length>1){
                  user=cadena.split("javax.jdo.option.ConnectionUserName=")[1];
              }
              if (cadena.split("javax.jdo.option.ConnectionPassword=").length>1){
                  pass=cadena.split("javax.jdo.option.ConnectionPassword=")[1];
              }
              if (cadena.split("puertoMySql=").length>1){
                  puerto=cadena.split("puertoMySql=")[1];
              }
              if (cadena.split("javax.jdo.option.ConnectionURL=").length>1){
                  String[] aux = (cadena.split("javax.jdo.option.ConnectionURL=")[1]).split("/");
                  nameDB= aux[aux.length-1];
                    System.out.println("dire path "+nameDB); 
              }
          }
          if (!puerto.equals("")){
              Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:"+puerto+"/", user, pass);               
              String query = "create database IF NOT EXISTS "+nameDB;
              Statement st  = (Statement) conexion.createStatement();
              st.executeUpdate(query);
          }
          
     }
          
     /**
      * Retorna la lista de elementos que son del mismo tipo que el parametro.
      * 
      * @param o Objeto del tipo a listar.
      * @return Lista todos los elementos del mismo tipo que o.
      */
     public List listar (Object o){

        List resultado = new LinkedList();
        Extent e = persistencia.getExtent(o.getClass(),true);
        String condicion = "";
        Query q = persistencia.newQuery(e,condicion);
        Collection c = (Collection)q.execute();
        Iterator iter = c.iterator();
        while (iter.hasNext()){
            Object p = (Object)iter.next();
            resultado.add(p);
        }
        return resultado;
    }
     
     public Iterator obtenerPorClase(Class clase){
            Extent e=persistencia.getExtent(clase,true);
            Query q=persistencia.newQuery(e);
            Collection c =(Collection)q.execute();
            Iterator iter=c.iterator();
            return iter;
	}

     /**
      * Busca un elemento por su id.
      * @param o Objeto del tipo a buscar.
      * @param id Identificacor de busqueda del objeto.
      * @return retorna el obejto de tipo de o que tiene como identificador a id, en caso contrario retorna null.
      */
     public Object buscarPorId (Class o, Long id){
        Object p = null;
        try {
            Extent e = persistencia.getExtent(o,true);
            String condicion = "id == "+id;
            Query q = persistencia.newQuery(e,condicion);
            Collection c = (Collection)q.execute();
            Iterator iter = c.iterator();
            if (iter.hasNext()){
                p = (Object)iter.next();
            }
        }
        catch (Exception e){
            p=null;
        }
        return p;
    }
     
     public Collection<Object> obtenerObjetosFecha(Class clase,Integer anio, Integer mes,Integer dia ){
        Query query = persistencia.newQuery(clase);
        query.declareParameters("int anio, int mes, int dia");
        query.setFilter("fecha.getYear() == anio && fecha.getMonth() == mes && fecha.getDay() == dia");
        Collection<Object> results = (Collection<Object>)query.execute(anio,mes,dia);
        return results;
    }

     
     /**
      * Busca un elemento por un filtro.
      * @param o Objeto del tipo a buscar.
      * @param id Identificacor de busqueda del objeto.
      * @return retorna el obejto de tipo de o que tiene como identificador a id, en caso contrario retorna null.
      */
     public Collection buscarPorFiltro (Class o,String cond){
        try {
            Extent e = persistencia.getExtent(o,true);
            Query q = persistencia.newQuery(e,cond);
            Collection c = (Collection)q.execute();
            return c;
        }
        catch (Exception e){
            return null;
        }
    }
     
     public List getAllOrdered(Class clase, String ordering){
        Collection coleccion=null;
        try{
            Class clienteClass = clase;
            Extent clnCliente = persistencia.getExtent(clienteClass, false);
            Query query = persistencia.newQuery(clnCliente,"");
            query.setOrdering(ordering); 
            coleccion=(Collection)query.execute();
            return new ArrayList(coleccion);
        } catch(Exception e){
            coleccion=null;
            return null;
        }
    }
   
    public List getObjectsOrdered(Class clase,String filter, String ordering){
        Collection coleccion=null;
        try{
            Class clienteClass = clase;
            Extent clnCliente = persistencia.getExtent(clienteClass, false);
            Query query = persistencia.newQuery(clnCliente,filter);
            query.setOrdering(ordering); 
            coleccion=(Collection)query.execute();
            return new ArrayList(coleccion);
        } catch(Exception e) {
            return null;
        }
    }
    
    public List getObjectosOrdenadosYAgrupados(Class clase,String filter, String ordering,String agrupar){
        Collection coleccion=null;
        try{
            Class clienteClass = clase;
            Extent clnCliente = persistencia.getExtent(clienteClass, false);
            Query query = persistencia.newQuery(clnCliente,filter);
            query.setOrdering(ordering); 
            query.setGrouping(agrupar);
            coleccion=(Collection)query.execute();
            return new ArrayList(coleccion);
        } catch(Exception e) {
            return null;
        }
    }
    
    
}


