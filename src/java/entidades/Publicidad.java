package entidades;


public class Publicidad implements Comparable<Object>{
    
    protected Long id;
    protected String titulo;
    protected Imagen publicidad;
    protected String linkPublicidad;
    

    public Publicidad() {
    }
    
    
    public Publicidad(String titulo, Imagen publicidad,String link) {
        this.titulo = titulo;
        this.publicidad = publicidad;        
        this.linkPublicidad = link;        
    }

    public String getLinkPublicidad() {
        return linkPublicidad;
    }

    public void setLinkPublicidad(String linkPublicidad) {
        this.linkPublicidad = linkPublicidad;
    }

    public Imagen getPublicidad() {
        return publicidad;
    }

    public void setPublicidad(Imagen publicidad) {
        this.publicidad = publicidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    public Long getId() {
            return id;
    }


    public void setId(Long id) {
            this.id = id;
    }


    @Override
    public int compareTo(Object o) {        
        return this.id.compareTo(((Publicidad)o).id);
    }

    
}
