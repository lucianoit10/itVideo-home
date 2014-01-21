package entidades;

public class Capitulo extends Pelicula{
    private Temporada temporada;
    private int numCap;

    public Capitulo(Temporada temporada, int numCap, String titulo, String idioma, String subtitulos, int duracion, String clasificacion, String sinopsis, Imagen poster, Imagen cartelera, String trailer, int anio, String pais, Genero genero, boolean baja, Double precioHD, Double precioSD, boolean esGratisHD, boolean esGratisSD, String repVideo, String calMax, String extSD, String extHD, String extFHD) {
        super();
        this.temporada = temporada;
        this.numCap = numCap;
    }

    public Capitulo(Temporada temporada, int numCap) {
        this.temporada = temporada;
        this.numCap = numCap;
    }
    
    public Capitulo(){
        super();
    }

    public int getNumCap() {
        return numCap;
    }

    public void setNumCap(int numCap) {
        this.numCap = numCap;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }
    
    
    
}
