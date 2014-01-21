package entidades;

public class Temporada implements Comparable<Object> {
    
    private Long id;
    private int numTem;
    private String titulo;
    private String tituloOriginal;
    private Serie serie;
    private Double rating;
    private boolean baja;
    private int anio;
    protected Imagen poster;

    public Temporada(int numTem, String titulo, String tituloOriginal,int anio, Serie serie, Double rating, Imagen poster) {
        this.numTem = numTem;
        this.titulo = titulo;
        this.tituloOriginal = tituloOriginal;
        this.serie = serie;
        this.anio = anio;
        this.rating = rating;
        this.baja = false;
        this.poster=poster;
    }
    
    public Temporada(){
        super();
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumTem() {
        return numTem;
    }

    public void setNumTem(int numTem) {
        this.numTem = numTem;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public Imagen getPoster() {
        return poster;
    }

    public void setPoster(Imagen poster) {
        this.poster = poster;
    }

    
    
    @Override
    public int compareTo(Object o) {
        Integer prim = this.numTem;
        Integer seg = ((Temporada)o).numTem;
        return prim.compareTo(seg);
    }
    
    
}
