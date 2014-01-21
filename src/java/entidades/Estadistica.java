package entidades;

public class Estadistica  implements Comparable<Object>{
    protected Pelicula pelicula;
    protected int sumaVotos =0;
    protected int cantidadVotos=0;
    protected double promedioVotos=0; 
    protected int cantidadDeVistas=0;
    protected double promedioVisitas=0; 
    protected double rating=0; 
    protected Long id; 

    public Estadistica() {
    }
    
    public Estadistica(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
/******************************************************/
/******************************************************/ 
    public void votar(int voto){
        sumaVotos+=voto;
        cantidadVotos++;
        String[] aux=(new Double ((new Double(sumaVotos))/(new Double(cantidadVotos)))).toString().split("\\.");
        String res=aux[0];
        if (aux.length>1){
            if(aux[1].length()>2)
                res+="."+aux[1].substring(0,2);
            else
                res+="."+aux[1];
        }    
        promedioVotos=Double.parseDouble(res);
    }
    
    public void mirar(){
        cantidadDeVistas=cantidadDeVistas+1;
    }
    
    public void calcularRating(){
        rating = pelicula.anio+promedioVotos+cantidadDeVistas;
    }
/******************************************************/
/******************************************************/
    
    

    public double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public int getCantidadDeVistas() {
        return cantidadDeVistas;
    }

    public void setCantidadDeVistas(int cantidadDeVistas) {
        this.cantidadDeVistas = cantidadDeVistas;
    }

    public int getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public double getPromedioVisitas() {
        return promedioVisitas;
    }

    public void setPromedioVisitas(double promedioVisitas) {
        this.promedioVisitas = promedioVisitas;
    }

    public double getPromedioVotos() {
        return promedioVotos;
    }

    public void setPromedioVotos(double promedioVotos) {
        this.promedioVotos = promedioVotos;
    }

    public int getSumaVotos() {
        return sumaVotos;
    }

    public void setSumaVotos(int sumaVotos) {
        this.sumaVotos = sumaVotos;
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
        if (this.rating==(((Estadistica)o).rating)){
            return 0;
        }else{
            if (this.rating<(((Estadistica)o).rating)){
                return -1;
            }else{
                return 1;
            }
        }
    }
    
    
}
