package entidades;


public class Genero implements Comparable<Object>{
    
    protected String nombre;
    protected Long id;

    public Genero() {
    }
    
    public Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /*Get y Set : id*/
    public Long getId() {
        return this.id;
    }

   
    public void setId(Long idN) {
        this.id = idN;
    }
    /*Fin Get y Set : id*/
    
    @Override
    public int compareTo(Object o) {
        String prim = this.nombre.toUpperCase();
        String seg = ((Genero)o).nombre.toUpperCase();
        return prim.compareTo(seg);
    }

}
