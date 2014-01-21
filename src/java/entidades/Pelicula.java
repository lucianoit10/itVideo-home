package entidades;

import java.io.UnsupportedEncodingException;

public class Pelicula implements Comparable<Object>{
    
    protected String titulo;
    protected String idioma;
    protected String subtitulos;
    protected int duracion;
    protected String clasificacion;
    protected String sinopsis;
    protected Imagen poster;
    protected Imagen cartelera;
    protected String trailer;
    protected Genero genero;
    protected int anio;
    protected String pais;
    protected Long id;
    //nuevos trabutos
    protected boolean baja;
    protected Double precioFHD;
    protected Double precioHD;
    protected Double precioSD;
    protected boolean esGratisFHD;
    protected boolean esGratisHD;
    protected boolean esGratisSD;
    protected String extSD;
    protected String extHD;
    protected String extFHD;
    protected String repVideo;
    protected String calidadMax;
    protected String tituloOriginal;
    protected boolean estreno=true;

    public Pelicula() {
        super();
    }
    
    
    public Pelicula(String titulo, String tituloOriginal,String idioma,String subtitulos,int duracion,
            String clasificacion,String sinopsis, Imagen poster,Imagen cartelera, String trailer, 
            int anio, String pais,Genero genero,boolean baja,Double precioHD,
            Double precioSD,boolean esGratisHD,boolean esGratisSD,String repVideo, 
            String calMax, String extSD,String extHD, String extFHD, boolean estreno) throws UnsupportedEncodingException {
        super();
        this.titulo =titulo;
        this.tituloOriginal =tituloOriginal;
        this.idioma = idioma;
        this.subtitulos = subtitulos;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.poster = poster;
        this.cartelera = cartelera;
        this.trailer = trailer;
        this.anio = anio;
        this.pais = pais;
        this.genero=genero;
        this.baja=baja;
        this.esGratisHD=esGratisHD;
        this.esGratisSD=esGratisSD;
        this.precioHD=precioHD;
        this.precioSD=precioSD;
        this.repVideo=repVideo;
        this.calidadMax=calMax;
        this.extSD=extSD;
        this.extHD=extHD;
        this.extFHD=extFHD;
        this.estreno=estreno;
        
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getCalidadMax() {
        return calidadMax;
    }

    public void setCalidadMax(String calidadMax) {
        this.calidadMax = calidadMax;
    }

    


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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


    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getSubtitulos() {
        return subtitulos;
    }

    public void setSubtitulos(String subtitulos) {
        this.subtitulos = subtitulos;
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

    
    
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    

    public boolean isBaja() {
		return baja;
	}


    public void setBaja(boolean baja) {
            this.baja = baja;
    }


    public Double getPrecioHD() {
            return precioHD;
    }


    public void setPrecioHD(Double precioHD) {
            this.precioHD = precioHD;
    }


    public Double getPrecioSD() {
            return precioSD;
    }


    public void setPrecioSD(Double precioSD) {
            this.precioSD = precioSD;
    }


    public boolean isEsGratisHD() {
            return esGratisHD;
    }


    public void setEsGratisHD(boolean esGratisHD) {
            this.esGratisHD = esGratisHD;
    }


    public boolean isEsGratisSD() {
            return esGratisSD;
    }


    public void setEsGratisSD(boolean esGratisSD) {
            this.esGratisSD = esGratisSD;
    }

    public Double getPrecioFHD() {
            return precioFHD;
    }


    public void setPrecioFHD(Double precioFHD) {
            this.precioFHD = precioFHD;
    }


    public boolean isEsGratisFHD() {
            return esGratisFHD;
    }


    public void setEsGratisFHD(boolean esGratisFHD) {
            this.esGratisFHD = esGratisFHD;
    }

    public String getRepVideo() {
        return repVideo;
    }

    public void setRepVideo(String repVideo) {
        this.repVideo = repVideo;
    }

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

    public String getExtFHD() {
        return extFHD;
    }

    public void setExtFHD(String extFHD) {
        this.extFHD = extFHD;
    }

    public String getExtHD() {
        return extHD;
    }

    public void setExtHD(String extHD) {
        this.extHD = extHD;
    }

    public String getExtSD() {
        return extSD;
    }

    public void setExtSD(String extSD) {
        this.extSD = extSD;
    }

    public boolean isEstreno() {
        return estreno;
    }

    public void setEstreno(boolean estreno) {
        this.estreno = estreno;
    }
    
    


	@Override
    public int compareTo(Object o) {
        String prim = this.titulo.toUpperCase();
        String seg = ((Pelicula)o).titulo.toUpperCase();
        return prim.compareTo(seg);
    }

    
}
