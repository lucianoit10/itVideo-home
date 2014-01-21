package helpers;

import entidades.Alquiler;
import entidades.Capitulo;
import entidades.Estadistica;
import entidades.Genero;
import entidades.Pelicula;
import entidades.RelacionPeliculaActor;
import entidades.RelacionPeliculaDirector;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperBusquedas_ajax extends HelperGeneral{
    
    private int cantFilas=7;//3
    private int cantEnFilas=3;//4

    @Override
    public String importsCSS() {
        StringBuilder html = new StringBuilder ("<style>\n");
        html.append("   a:link, a:visited {");
        html.append("       color : blue;");
        html.append("       text-decoration : none;");
        html.append("   }");
        html.append("   a img {");
        html.append("       border : 0;");
        html.append("   }");
        html.append("</style>\n");
        html.append("<link rel=\"stylesheet\" href=\"styles/accordionImageMenu.css\" type=\"text/css\" />\n");
        html.append("<link rel=\"stylesheet\" href=\"styles/overlay-apple.css\"  type=\"text/css\">\n");
        return html.toString();
    }

    @Override
    public String importsJS() {        
        StringBuilder html = new StringBuilder ("       <script type=\"text/javascript\" src=\"js/todo.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/jquery-uiAccordion.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/accordionImageMenu.js\"></script>\n");                    
        return html.toString();
    }

    @Override
    public void inicializacion(AccesoBD abd,HttpSession session,HttpServletRequest request) {
        session.setAttribute("ver", null);
        session.setAttribute("path",null);
        session.setAttribute("subs",null);
        session.setAttribute("elegirCalidad", null);
        session.setAttribute("calidad", null);
        session.setAttribute("movie",null);        
        session.setAttribute("serie",null);        
        session.setAttribute("pagActual","busquedas.jsp");
    }

    @Override
    public String title(HttpSession session, String title) {
        StringBuilder html = new StringBuilder("<title>");
        html.append(title);
        html.append("</title>\n");
        return html.toString();
    }

    @Override
    public String contenido(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {        
        return generarLista(abd, session, request, pag, 0);
    }
        
    public String generarLista(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag,int index) {
        StringBuilder ordenar = new StringBuilder("pelicula.anio descending");
        boolean alquiladas= false;
        boolean actor= false;
        boolean director= false;
        boolean mejorVotadas= false;
        boolean masVistas= false;
        StringBuilder filtro = new StringBuilder("pelicula.baja==false"); 
        StringBuilder html = new StringBuilder("<h2>Resultado de la busqueda por: Todas</h2><br>");
        /**********************************************************/
        if (request.getParameter("orden")!=null){
            int v = Integer.parseInt(request.getParameter("orden"));
            switch(v){
                case(2):{ordenar = new StringBuilder("promedioVotos descending");break;}
                case(3):{ordenar = new StringBuilder("cantidadDeVistas descending");break;}
            }
        }
        /**********************************************************/
        if (request.getParameter("alquiladas")!=null&&(request.getParameter("alquiladas").equals("1"))){
            alquiladas=true;
            filtro = new StringBuilder("usuario.nameUsuario.equals(\"");
            filtro.append(session.getAttribute("user").toString());
            filtro.append("\") && estaAbierta==true  && pelicula.baja==false"); 
            html= new StringBuilder("<h2>Resultado de la busqueda por: Peliculas Alquiladas</h2><br>");
        }else{
            if (    request.getParameter("textoBuscar")!=null && 
                    !request.getParameter("textoBuscar").equals("") && !request.getParameter("textoBuscar").equals("Buscar...") &&
                    request.getParameter("buscar")!=null){
                try {
                    Integer criterio = Integer.parseInt(request.getParameter("criterio"));              
                    String valor = new String(request.getParameter("textoBuscar").getBytes("ISO-8859-1"), "UTF-8");

                    switch(criterio){
                        case(1):{
                            filtro = new StringBuilder("(pelicula.titulo.toLowerCase().indexOf(\"");
                            filtro.append(valor);
                            filtro.append("\") >=0 || pelicula.tituloOriginal.toLowerCase().indexOf(\"");
                            filtro.append(valor);
                            filtro.append("\") >=0 ) && pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: titulo: '");
                            html.append(valor);
                            html.append("'</h2><br>");
                            break;
                        }
                        case(2):{
                            filtro = new StringBuilder("pelicula.anio == ");
                            filtro.append(valor);
                            filtro.append("  && pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: año: '");
                            html.append(valor);
                            html.append("'</h2><br>");
                            break;
                        }
                        case(3):{
                            filtro = new StringBuilder("pelicula.genero.nombre.toLowerCase().indexOf(\"");
                            filtro.append(valor.toLowerCase());
                            filtro.append("\")>=0  && pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: genero: '");
                            html.append(valor);
                            html.append("'</h2><br>");
                            break;
                        }
                        case(4):{
                            filtro = new StringBuilder("actor.nombreApellido.toLowerCase().indexOf(\"");
                            filtro.append(valor.toLowerCase());
                            filtro.append("\")>=0 && pelicula.baja==false");
                            actor=true;
                            html = new StringBuilder("<h2>Resultado de la busqueda por: Actor: '");
                            html.append(valor);
                            html.append("'</h2><br>");
                            break;
                        }
                        case(5):{
                            filtro = new StringBuilder("director.nombreApellido.toLowerCase().indexOf(\"");
                            filtro.append(valor.toLowerCase());
                            filtro.append("\")>=0 && pelicula.baja==false");
                            director=true;
                            html = new StringBuilder("<h2>Resultado de la busqueda por: Director: '");
                            html.append(valor);
                            html.append("'</h2><br>");
                            break;
                        }
                        case(6):{
                            filtro = new StringBuilder("pelicula.clasificacion.toLowerCase().indexOf(\"");
                            filtro.append(valor);
                            filtro.append("\")>=0   && pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: clasificacion: '");
                            html.append(valor);
                            html.append("'</h2><br>");
                            break;
                        }
                        case(7):{
                            filtro = new StringBuilder("pelicula.estreno==true   && pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: Estrenos'</h2><br>");
                            break;
                        }
                        case(8):{
                            filtro = new StringBuilder("pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: Las Mas Recientes'</h2><br>");
                            break;
                        }
                        case(9):{
                            filtro = new StringBuilder("pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: Las Mas Vistas'</h2><br>");
                            masVistas=true;
                            break;
                        }
                        case(10):{
                            filtro = new StringBuilder("pelicula.baja==false");
                            html = new StringBuilder("<h2>Resultado de la busqueda por: Las Mejor Votadas'</h2><br>");
                            mejorVotadas=true;
                            break;
                        }
                    }
                    if (request.getParameter("generobusc")!=null &&(!request.getParameter("generobusc").equals("0"))){
                        filtro.append("&& pelicula.genero.id == ");
                        filtro.append(request.getParameter("generobusc"));
                    }
                    if (request.getParameter("clasificacion")!=null &&(!request.getParameter("clasificacion").equals("0"))){
                        filtro.append("&& pelicula.clasificacion.toLowerCase().indexOf(\"");
                        filtro.append(request.getParameter("clasificacion").toLowerCase());
                        filtro.append("\")>=0");
                    }
                    /**************************************************/
                    if (request.getParameter("calidadBusc")!=null &&(!request.getParameter("calidadBusc").equals("0"))){
                        if(request.getParameter("calidadBusc").equals("1")){
                            filtro.append("&& pelicula.extSD!=null");
                        }
                        else{
                            filtro.append("&& pelicula.extHD!=null");                       
                        }
                    }
                    if (request.getParameter("idioma")!=null &&(!request.getParameter("idioma").equals("0"))){
                        if(request.getParameter("idioma").equals("1")){
                            filtro.append("&& pelicula.idioma.toLowerCase().indexOf('espa')>=0");
                        }
                        else{
                            filtro.append("&& pelicula.idioma.toLowerCase().indexOf('espa')<0");
                        }
                    }
                    /**************************************************/
                } catch (UnsupportedEncodingException ex) {
                    
                }
            }else{
                
                /*if( request.getParameter("idGenero")!=null && 
                    !request.getParameter("idGenero").equals("")){
                    Integer criterio = Integer.parseInt(request.getParameter("idGenero"));
                    switch(criterio){
                        case(1):{filtro = "pelicula.genero.nombre.toLowerCase().indexOf(\"drama\")>=0  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'DRAMA'</h2><br>";break;}
                        case(2):{filtro = "(pelicula.genero.nombre.toLowerCase().indexOf(\"accion\")>=0 || pelicula.genero.nombre.toLowerCase().indexOf(\"acción\")>=0)  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'ACCIÓN'</h2><br>";break;}
                        case(3):{filtro = "pelicula.genero.nombre.toLowerCase().indexOf(\"comedia\")>=0  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'COMEDIA'</h2><br>";break;}
                        case(4):{filtro = "(pelicula.genero.nombre.toLowerCase().indexOf(\"animacion\")>=0 || pelicula.genero.nombre.toLowerCase().indexOf(\"animación\")>=0)  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'ANIMACIÓN'</h2><br>";break;}
                        case(5):{filtro = "pelicula.genero.nombre.toLowerCase().indexOf(\"roman\")>=0  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'ROMANTICA'</h2><br>";break;}
                        case(6):{filtro = "pelicula.genero.nombre.toLowerCase().indexOf(\"suspenso\")>=0  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'SUSPENSO'</h2><br>";break;}
                        case(7):{filtro = "pelicula.genero.nombre.toLowerCase().indexOf(\"terror\")>=0  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'TERROR'</h2><br>";break;}
                        case(8):{filtro = "(pelicula.genero.nombre.toLowerCase().indexOf(\"music\")>=0 || pelicula.genero.nombre.toLowerCase().indexOf(\"recit\")>=0)  && pelicula.baja==false";html= "<h2>Resultado de la busqueda por: genero: 'RECITALES'</h2><br>";break;}
                        default:{filtro = "pelicula.baja==false";html= "<h2>Resultado de la busqueda por: 'Todas las Peliculas'</h2><br>";break;}
                    } 
                }*/
                Integer criterio = Integer.parseInt(request.getParameter("criterio"));              
                switch(criterio){
                    case(7):{
                        filtro = new StringBuilder("pelicula.estreno==true   && pelicula.baja==false");
                        html=  new StringBuilder("<h2>Resultado de la busqueda por: Estrenos'</h2><br>");
                        break;
                    }
                    case(8):{
                        filtro =  new StringBuilder("pelicula.baja==false");
                        html=  new StringBuilder("<h2>Resultado de la busqueda por: Las Mas Recientes'</h2><br>");
                        break;
                    }
                    case(9):{
                        filtro =  new StringBuilder("pelicula.baja==false");
                        html=  new StringBuilder("<h2>Resultado de la busqueda por: Las Mas Vistas'</h2><br>");
                        masVistas=true;
                        break;
                    }
                    case(10):{
                        filtro =  new StringBuilder("pelicula.baja==false");
                        html=  new StringBuilder("<h2>Resultado de la busqueda por: Las Mejor Votadas'</h2><br>");
                        mejorVotadas=true;
                        break;
                    }
                }
                if (request.getParameter("generobusc")!=null &&(!request.getParameter("generobusc").equals("0"))){
                    filtro.append("&& pelicula.genero.id == ");
                    filtro.append(request.getParameter("generobusc"));
                    Genero g = (Genero) abd.buscarPorId(Genero.class, Long.parseLong(request.getParameter("generobusc")));
                    if (g!=null)
                        html=  new StringBuilder("<h2>Resultado de la busqueda por: 'Genero: ");
                        html.append(g.getNombre().toUpperCase());
                        html.append("'</h2><br>");
                }
                if (request.getParameter("clasificacion")!=null &&(!request.getParameter("clasificacion").equals("0"))){
                    filtro.append("&& pelicula.clasificacion.toLowerCase().indexOf(\"");
                    filtro.append(request.getParameter("clasificacion").toLowerCase());
                    filtro.append("\")>=0");
                }
                
                    /**************************************************/
                    if (request.getParameter("calidadBusc")!=null &&(!request.getParameter("calidadBusc").equals("0"))){
                        if(request.getParameter("calidadBusc").equals("1")){
                            filtro.append("&& pelicula.extSD!=null");
                        }
                        else{
                            filtro.append("&& pelicula.extHD!=null");                       
                        }
                    }
                    if (request.getParameter("idioma")!=null &&(!request.getParameter("idioma").equals("0"))){
                        if(request.getParameter("idioma").equals("1")){
                            filtro.append("&& pelicula.idioma.toLowerCase().indexOf('espa')>=0");
                        }
                        else{
                            filtro.append("&& pelicula.idioma.toLowerCase().indexOf('espa')<0");
                        }
                    }
                    /**************************************************/
            }
        }
        
        List<Pelicula> pelis=new LinkedList<Pelicula>();
        if(!filtro.toString().equals("")){
            if (alquiladas){
                Collection  c = abd.getObjectosOrdenadosYAgrupados(Alquiler.class,filtro.toString(),"pelicula.id descending","usuario.id,pelicula.id");
                if (c!=null){
                    LinkedList<Alquiler> alquileres = new LinkedList<Alquiler>(c);
                    Alquiler a = null;
                    Pelicula p = null;
                    LinkedList<Pelicula> aux = new LinkedList<Pelicula>();
                    for (int i = 0; i < alquileres.size(); i++) {
                        a = alquileres.get(i);
                        p = a.getPelicula();
                        if (estabaAlquilada(abd,session,p,a.getCalidad())!=null)
                            aux.add(a.getPelicula());      
                    }
                    c=null;
                    c = abd.getObjectsOrdered(Estadistica.class,"pelicula.baja == false",ordenar.toString());
                    if (c!=null){
                        LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                        for (int i = 0; i < est.size(); i++) {
                            if (aux.contains(est.get(i).getPelicula())){
                                pelis.add(est.get(i).getPelicula());                
                            }
                        }
                    }
                }
            }
            else{
                if(actor){
                    Collection c = abd.getObjectsOrdered(RelacionPeliculaActor.class,filtro.toString(),"pelicula.id descending");
                    LinkedList<Pelicula> aux = new LinkedList<Pelicula>();
                    if (c!=null){
                        LinkedList<RelacionPeliculaActor> relacion = new LinkedList<RelacionPeliculaActor>(c);
                        for (int i = 0; i < relacion.size(); i++) {
                            aux.add(relacion.get(i).getPelicula());                
                        }
                    }
                    c=null;
                    c = abd.getObjectsOrdered(Estadistica.class,"pelicula.baja == false",ordenar.toString());
                    if (c!=null){
                        LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                        for (int i = 0; i < est.size(); i++) {
                            if (aux.contains(est.get(i).getPelicula())){
                                pelis.add(est.get(i).getPelicula());                
                            }
                        }
                    }
                }else{
                    if(director){
                        Collection c = abd.getObjectsOrdered(RelacionPeliculaDirector.class,filtro.toString(),"pelicula.id descending");
                        LinkedList<Pelicula> aux = new LinkedList<Pelicula>();
                        if (c!=null){
                            LinkedList<RelacionPeliculaDirector> relacion = new LinkedList<RelacionPeliculaDirector>(c);
                            for (int i = 0; i < relacion.size(); i++) {                                
                                pelis.add(relacion.get(i).getPelicula());                
                            }
                        }
                        c=null;
                        c = abd.getObjectsOrdered(Estadistica.class,"pelicula.baja == false",ordenar.toString());
                        if (c!=null){
                            LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                            for (int i = 0; i < est.size(); i++) {
                                if (aux.contains(est.get(i).getPelicula())){
                                    pelis.add(est.get(i).getPelicula());                
                                }
                            }
                        }
                    }else{
                        if (masVistas){
                            /*-----------------------------Vistas-----------------------------*/
                            Collection c=null;
                            c = abd.getObjectsOrdered(Estadistica.class,filtro.toString(),"cantidadDeVistas descending");
                            if (c!=null){
                                LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                                for (int i = 0; i < est.size(); i++) {
                                    Estadistica e = (Estadistica) est.get(i);
                                    pelis.add(e.getPelicula());
                                }
                            }
                            
                        }else{
                            if (mejorVotadas){
                                /*-----------------------------Votadas-----------------------------*/
                                Collection c=null;
                                c = abd.getObjectsOrdered(Estadistica.class,filtro.toString(),"promedioVotos descending");
                                if (c!=null){
                                    LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                                    for (int i = 0; i < est.size(); i++) {
                                        Estadistica e = (Estadistica) est.get(i);
                                        pelis.add(e.getPelicula());
                                    }
                                }
                            }else{
                                /**/
                                Collection c=abd.getObjectsOrdered(Estadistica.class,filtro.toString(),ordenar.toString());                      
                                if (c!=null){
                                    LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                                    for (int i = 0; i < est.size(); i++) {
                                        Estadistica e = (Estadistica) est.get(i);
                                        pelis.add(e.getPelicula());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*evitar que aparescan capitulosdentro de la busqueda*/
            LinkedList<Pelicula>cap=(LinkedList<Pelicula>) abd.listar(new Capitulo());
            if(cap!=null && !cap.isEmpty()){
                for(int i=0;i<pelis.size();i++){
                    if(cap.contains(pelis.get(i))){
                        pelis.remove(i);
                        i--;
                    }
                }
            }
            /*****************************************************/
            session.setAttribute("listaPeliculas", pelis);
        }else{
            Collection c=abd.getObjectsOrdered(Pelicula.class,filtro.toString(),"id descending");
            if (c!=null){
                pelis = new LinkedList<Pelicula>(c);
            }
        }
        pelis=(List<Pelicula>) session.getAttribute("listaPeliculas");
        if (pelis!=null && pelis.size()>0){
            html.append("       <form method=\"post\" name=\"selectVideo\" action= \"Video.jsp\">\n");
            html.append("           <input type=\"hidden\" name=\"idVideo\">\n");
            html.append("       </form>\n");
            html.append("       <center>\n");
            html.append("       <div  id='lista_resultados'>\n");
            int cant = (cantFilas*cantEnFilas);
            int j=index*cant;
            int k = 0;
            while (k<cant && j<pelis.size()){
                html.append(infoPelicula(pelis.get(j),abd));
                j++;
                k++;
            }
            html.append("      </div>\n");
            html.append("       </center>\n");
            int val =(pelis.size())/cantEnFilas;
            int mod =(pelis.size())%cantEnFilas;
            if (mod!=0){
                val++;
            }
            if(cantFilas<val){
                html.append(this.links(pelis.size(),request,index));
            }
        }
        else {
            html.append("<br><b3>Ups, no se han encontrado resultados en la busqueda.</b3><br><br><br>\n");
        }
        html.append("       <script LANGUAGE=\"javascript\">\n");
        html.append("           $(document).ready(function() {\n");
        html.append("               $(\"button[rel]\").overlay({mask: '#000', effect: 'apple'});");
        html.append("               $(\".imgTool\").tooltip({effect: 'slide',position: 'center right', opacity: 0.95,predelay: 100}).dynamic({ right: { direction: 'left', bounce: true },top: { direction: 'down', bounce: true },bottom: { direction: 'top', bounce: true } });   ");
        html.append("           });\n");
        html.append("</script>");
        return html.toString();
    }
    
    private String infoPelicula (Pelicula p, AccesoBD abd){
        String desc = p.getSinopsis();
        if (desc!=null && desc.length()>250){
            desc=desc.substring(0, 250);
        }
        StringBuilder html =   new StringBuilder("               <div style=\"width:230px;\" class=\"DescripcionLista\">\n");
        html.append("                   <a href=\"#\" HEIGHT=261  OnClick=\"linkAVideo('");
        html.append(p.getId());
        html.append("')\" ><IMG  class=\"imgTool\" rel=\"tooltip");
        html.append(p.getId());
        html.append("\"  SRC=\"");
        html.append(p.getPoster().getPath());
        html.append("\" WIDTH=176 HEIGHT=261 ALT=\"imagen\" style=\"box-shadow: 5px 5px 5px #888\"></a>\n");
        html.append(codTooltip(p, abd));
        html.append(codPopups(p, abd));
        html.append("               </div>\n");
        return html.toString();
    }
    
    public String links(int length,HttpServletRequest request,int select){
        int cantiFilas = ((length)/cantEnFilas);
         int mod =(length)%cantEnFilas;
         if (mod!=0){
             cantiFilas++;
         }       
        StringBuilder html= new StringBuilder("<br><center><div id=\"links\">Pagina: \n");
        int ind=1;
        StringBuilder link = new StringBuilder("cargar_busqueda_pelicula.jsp?");
        if (request.getParameter("orden")!=null){
            link.append("orden=");
            link.append(Integer.parseInt(request.getParameter("orden")));
            link.append("&");
        }
        /**********************************************************/
        if (request.getParameter("alquiladas")!=null&&(request.getParameter("alquiladas").equals("1"))){
            link.append("alquiladas=1&");
        }else{
            if (    request.getParameter("textoBuscar")!=null && 
                    !request.getParameter("textoBuscar").equals("") && 
                    request.getParameter("buscar")!=null){
                try{
                    String generoBusc = request.getParameter("generobusc");
                    Integer criterio = Integer.parseInt(request.getParameter("criterio"));                      
                    String valor = new String(request.getParameter("textoBuscar").getBytes("ISO-8859-1"), "UTF-8");
                    link.append("generobusc=");
                    link.append(generoBusc);
                    link.append("&criterio=");
                    link.append(criterio);
                    link.append("&textoBuscar=");
                    link.append(valor);link.append("&buscar=Buscar&");
                }catch (Exception e){}
            }else{
                if( request.getParameter("idGenero")!=null && 
                    !request.getParameter("idGenero").equals("")){
                    Integer criterio = Integer.parseInt(request.getParameter("idGenero"));
                    link.append("idGenero=");
                    link.append(criterio);
                    link.append("&");                    
                }
            }
        }
        link.append("indice=");
        //System.out.println(link+select);
        if (select > 0){ 
            html.append("<button class=\"boton\" OnClick=\"pagina_ajax('cont','");
            html.append(link);
            html.append(select-1);
            html.append("');\"><< Anterior</button>");
        }
        for(int j=0;j<cantiFilas;j=j+cantFilas){
            String aux = link.toString() + (ind-1);
            if (select==(ind-1)){
                html.append("<a id=\"link");
                html.append(ind);
                html.append("\" href=\"javascript:pagina_ajax('cont','");
                html.append(aux);
                html.append("')\" style=\"color:blue; font-size:medium; background:#b8b8b2;\">");
                html.append(ind);
                html.append("</a>&nbsp;\n");
            }else{
                html.append("<a id=\"link");
                html.append(ind);
                html.append("\" href=\"javascript:pagina_ajax('cont','");
                html.append(aux);
                html.append("')\" style=\"color:blue; font-size:medium; background:none;\">");
                html.append(ind);
                html.append("</a>&nbsp;\n");
            }
            ind++;
            System.out.println(aux);
        }
        if (select < ind-2){
            html.append("<button class=\"boton\"  OnClick=\"pagina_ajax('cont','");
            html.append(link);
                html.append(select+1);
                html.append("');\">Siguiente >></button>");
        }
        html.append("</div></center>\n");
        return html.toString();
    }               
    
}
