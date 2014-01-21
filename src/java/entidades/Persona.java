package entidades;


public class Persona implements Comparable<Object>{    
    
    protected String nombre;
    protected String apellido;
    protected String nombreApellido;
    protected Long id;

    public Persona() {
    }
    
    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreApellido= this.nombre+" "+this.apellido;
    }

     public void setNombreApellido(String nombreYApellido) {
        this.nombreApellido= nombreYApellido;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }
    
     
     
    public void actuailizarNombreApellido() {
        this.nombreApellido= this.nombre+" "+this.apellido;
    }
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    
    public Long getId() {
        return this.id;
    }

    @Override
    public int compareTo(Object o) {
        String prim = this.apellido.toUpperCase()+", "+this.nombre.toUpperCase();
        String seg = ((Usuario)o).apellido.toUpperCase()+", "+((Usuario)o).nombre.toUpperCase();
        return prim.compareTo(seg);
    }

    
}
