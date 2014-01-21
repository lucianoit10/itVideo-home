package entidades;

public class Imagen{
    protected String path;
    protected Long id;
    protected int ancho;
    protected int alto;
    
    /* Constructor Clase Imagen */
    public Imagen(String path,int ancho, int alto) {
        this.path = path;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Imagen() {
    }
    /* Fin Constructor Clase Imagen */
 
    /*Get y Set : alto*/
    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }
    /*Fin Get y Set : alto*/

    /*Get y Set : ancho*/
    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    /*Fin Get y Set : ancho*/
    

    /* Get y Set : path */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /* Fin Get y Set : path */

    /*Get y Set : id*/
    public Long getId() {
        return this.id;
    }

    
    public void setId(Long idN) {
        this.id = idN;
    }
    /*Fin Get y Set : id*/
    
}
