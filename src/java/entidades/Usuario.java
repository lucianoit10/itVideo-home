package entidades;

public class Usuario  extends Persona{
    protected String dni;
    protected String nameUsuario;
    protected String password;
    protected String direccion;
    protected String telefono;
    protected String email;
    //atributo para dar de baja logicamente
    protected boolean eliminado;
    protected int tipoUsuario;
    
    public Usuario(){        
        super();
    }

    public Usuario(String doc, String nom, String ape,String nameUs,String pass,String dir,String tel, int tipoUs){
        super(nom,ape);
        this.dni=doc;
        this.nameUsuario=nameUs;
        this.password=pass;
        this.direccion= dir;
        this.telefono= tel;
        this.eliminado=false;
        this.tipoUsuario=tipoUs;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /*Get y Set : Dni*/
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    /*Fin Get y Set : Dni*/


    /*Get y Set : nameUsuario*/
    public String getNameUsuario() {
        return nameUsuario;
    }

    public void setNameUsuario(String nameUsuario) {
        this.nameUsuario = nameUsuario;
    }
    /*Fin Get y Set : nameUsuario*/


    /*Get y Set : password*/
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /*Fin Get y Set : password*/

    public boolean isEliminado() {
            return eliminado;
    }

    public void setEliminado(boolean eliminado) {
            this.eliminado = eliminado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    

    

}
