package entidades;

public class Configuracion {

	protected int id;
	protected String nombre;
	protected String telefono;
	protected String direccion;
	protected String email;
	protected String pagWeb;
	protected Imagen logo;
	protected int cantPeliCartelera;
	protected int cantPeliPorPag;
	protected int hsAlquiler;
	protected String ipServidorVideo;
        protected String usuario;
        protected String password;
        protected int MaxCantConexion;
	
	
	
	public Configuracion(){
		this.id=1;
	}
	
	public Configuracion(String nombre,String telefono,String direccion,Imagen logo,int cantPeliCartelera,
	int cantPeliPorPag,int hsAlquiler,String ipServidorVideo,String email, String pagWeb, String u, String p, int mcc){
		this.nombre=nombre;
		this.telefono=telefono;
		this.direccion=direccion;
		this.logo=logo;
		this.cantPeliCartelera=cantPeliCartelera;
		this.cantPeliPorPag=cantPeliPorPag;
		this.hsAlquiler=hsAlquiler;
		this.ipServidorVideo=ipServidorVideo;
		this.email=email;
		this.pagWeb=pagWeb;
                this.usuario=u;
                this.password=p;
                this.MaxCantConexion=mcc;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Imagen getLogo() {
		return logo;
	}

	public void setLogo(Imagen logo) {
		this.logo = logo;
	}

	public int getCantPeliCartelera() {
		return cantPeliCartelera;
	}

	public void setCantPeliCartelera(int cantPeliCartelera) {
		this.cantPeliCartelera = cantPeliCartelera;
	}

	public int getCantPeliPorPag() {
		return cantPeliPorPag;
	}

	public void setCantPeliPorPag(int cantPeliPorPag) {
		this.cantPeliPorPag = cantPeliPorPag;
	}

	public int getHsAlquiler() {
		return hsAlquiler;
	}

	public void setHsAlquiler(int hsAlquiler) {
		this.hsAlquiler = hsAlquiler;
	}

	public String getIpServidorVideo() {
		return ipServidorVideo;
	}

	public void setIpServidorVideo(String ipServidorVideo) {
		this.ipServidorVideo = ipServidorVideo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPagWeb() {
		return pagWeb;
	}

	public void setPagWeb(String pagWeb) {
		this.pagWeb = pagWeb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public int getMaxCantConexion() {
            return MaxCantConexion;
        }

        public void setMaxCantConexion(int MaxCantConexion) {
            this.MaxCantConexion = MaxCantConexion;
        }

}
