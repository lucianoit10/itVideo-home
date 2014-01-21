package entidades;


public class Serie implements Comparable<Object>{
    
    protected Long id;
    protected String titulo;
    protected String tituloOriginal;
    protected String sinopsis;
    protected Imagen poster;
    protected Imagen cartelera;
    protected String idioma;
    protected String pais;
    protected String clasificacion;
    protected Genero genero;
    protected Double rating;
    protected boolean baja;

    public Serie() {
        super();
    }
    
    
    public Serie(String titulo,String sinopsis, Imagen poster,Imagen cartelera, Double rating, String idioma, String pais, String clasificacion, Genero genero) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.poster = poster;
        this.cartelera = cartelera;
        this.idioma=idioma;
        this.pais=pais;
        this.clasificacion=clasificacion;
        this.genero=genero;
        this.rating = rating;
        this.baja = false;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    

    public Imagen getCartelera() {
        return cartelera;
    }

    public void setCartelera(Imagen cartelera) {
        this.cartelera = cartelera;
    }

    public Imagen getPoster() {
        return poster;
    }

    public void setPoster(Imagen poster) {
        this.poster = poster;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
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

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(Object o) {
        String prim = this.titulo.toUpperCase();
        String seg = ((Serie)o).titulo.toUpperCase();
        return prim.compareTo(seg);
    }

    
}
