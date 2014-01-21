package helpers;

import entidades.Alquiler;
import entidades.Capitulo;
import entidades.Configuracion;
import entidades.Pelicula;
import entidades.RelacionPeliculaActor;
import entidades.RelacionPeliculaDirector;
import entidades.Usuario;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperVideo extends HelperGeneral{

    private boolean cantMaxCon=false;
    
    @Override
    public String importsCSS() {
        return "";
    }

    @Override
    public String importsJS() {
        return "       <script type=\"text/javascript\" src=\"js/jwplayer/jwplayer.js\"></script>\n";
    }

    @Override
    public void inicializacion(AccesoBD abd,HttpSession session,HttpServletRequest request) {
        if (request.getParameter("idVideo") != null){
            Pelicula p=(Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(request.getParameter("idVideo")));
            session.setAttribute("ver", null);
            session.setAttribute("path",null);
            session.setAttribute("subs",null);
            session.setAttribute("elegirCalidad", null);
            session.setAttribute("calidad", null);
            session.setAttribute("serie",null);
            session.setAttribute("movie",p.getId() );
        }        
        session.setAttribute("pagActual","Video.jsp");
    }

    @Override
    public String title(HttpSession session, String title) {
        String nombre = "";
        if (((AccesoBD)session.getAttribute("conexion")).buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()))!=null){
            Pelicula p = (Pelicula)((AccesoBD)session.getAttribute("conexion")).buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
            nombre = p.getTitulo();
        }
        StringBuilder html = new StringBuilder ("<title>");
        html.append(title);
        html.append(" - ");
        html.append(nombre);
        html.append("</title>\n");        
        return html.toString();
    }

    @Override
    public String contenido(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {
          StringBuilder html = new StringBuilder("<center>");
          if (cantMaxCon){
              html.append("<h2><br><br> El servidor a llegado a su maximo de conexiones simultaneas, por favor intentelo de nuevo mas tarde.<br><br>\n");
              html.append("Recuerde que el servidor esta en fase de prueba, esto no sucedera en la versión final.<br><br>\n\n");
              html.append("Muchas Gracias por utilizar itVideo.<br><br></h2>");
          }else{
              if (session.getAttribute("ver")!=null && session.getAttribute("ver").toString().toLowerCase().equals("true")){
                  /**************************************/
                  html.append(menuIzq(abd,session));
                  html.append("<div id='cont'>");
                  /**************************************/
                  Pelicula p = (Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
                  html.append("<h2>");
                  html.append(p.getTitulo().toUpperCase());
                  html.append("</h2>");
                  session.setAttribute("esVotado",false);
                  html.append(votacion());
                  html.append(video(session,request));
                  if(!Boolean.parseBoolean(session.getAttribute("esVotado").toString())){
                      html.append(cargando(request));
                  }
                  /**************************************/
                  html.append("</div>");
                  /**************************************/
              } else {   
                  Usuario u = null;
                  List lista = new LinkedList(abd.buscarPorFiltro(Usuario.class, "nameUsuario.equals(\""+session.getAttribute("user") +"\")"));
                  if (lista.size()>0){
                      u= (Usuario) lista.get(0);
                  }
                  if(u!= null && u.getTipoUsuario()!=0){
                      /**************************************/
                      html.append(menuIzq(abd,session));
                      html.append("<div id='cont'>");
                      /**************************************/
                      html.append(presentacionPelicula(request, session,abd));
                      /**************************************/
                      html.append("</div>");
                      /**************************************/
                  }
              }
          }
          html.append("</center>");
          return html.toString();
    }
       
    public String video(HttpSession session,HttpServletRequest request) {        
        try {
            Pelicula p =(Pelicula)((AccesoBD)session.getAttribute("conexion")).buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
            StringBuilder html =   new StringBuilder("");
           /* String userAgent = request.getHeader("user-agent");
            if ((userAgent != null)&&(userAgent.indexOf("MSIE") <= -1)) {*/
            //    html +=     "<button class=\"boton\" onclick=\"goFullscreen('container');\" id=\"full\">REPRODUCIR A PANTALLA COMPLETA</button><br><br>\n";
            /*}*/
            html.append("       <div class=\"container\" id=\"container\">Cargando video...</div>\n ");
            html.append("       <script type=\"text/javascript\">\n");
            html.append("           jwplayer(\"container\").setup({\n");
               //si no es internet explorer
            /*if ((userAgent != null)&&(userAgent.indexOf("MSIE") <= -1)) {*/
              //        html+=    "           modes: [{ type: 'html5' },{type: 'flash', src: 'js/jwplayer/player.swf'}],";
            /*}else{*/
            html.append("           flashplayer: \"js/jwplayer/player.swf\",\n");
           // }
            if (p.getExtSD()!=null){
                html.append("           file: sessionStorage.getItem(\"pathSD\"),\n");
            }else{
                if (p.getExtHD()!=null){
                    html.append("           file: sessionStorage.getItem(\"pathHD\"),\n");
                }else{
                    html.append("           file: sessionStorage.getItem(\"pathFHD\"),\n");
                }
            }
            html.append("           skin: \"js/jwplayer/skins/blueratio.zip\",\n");
            html.append("           height: 56%,\n");
            html.append("           width: 100%,\n");
            html.append("           stretching: \"uniform\",\n");
            html.append("           provider: \"http\",\n");
            html.append("           plugins :{\n");
            String calidad = session.getAttribute("calidad").toString();
            if (p.getExtSD()!=null){ 
                if (calidad.equals("hd")){
                    if (p.getExtHD()!=null){            
                        html.append("                    'hd-2': {file: sessionStorage.getItem(\"pathHD\"),state: 'true'},\n");
                    }else{
                        if (p.getExtFHD()!=null){            
                            html.append("                    'hd-2': {file: sessionStorage.getItem(\"pathFHD\"),state: 'true'},\n");
                        }
                    }
                }
            } else{
                if (calidad.equals("fhd")){
                    if (p.getExtHD()!=null){            
                        if (p.getExtFHD()!=null){            
                            html.append("                    'hd-2': {file: sessionStorage.getItem(\"pathFHD\"),state: 'true'},\n");
                        }
                    }
                }
            }
            html.append("                       \"captions-2\":{\n");
            if (p!=null && p.getSubtitulos()!=null && !p.getSubtitulos().equals("")){
                html.append("                           label : \"español\"\n");
            }
            html.append("                       },\n");
            html.append("                        \"gapro-2\": {}");
            html.append("                   }\n");
            html.append("           });\n");
            html.append("       </script>\n");

            html.append(anteriorSiguiente((AccesoBD)session.getAttribute("conexion"), session));
            return html.toString();
        } catch (Exception ex) {
            return "";
        }
    }
        
    public String manejoDeBotonesVideo (AccesoBD abd, HttpServletRequest request, HttpSession session){
        /*busca el usuario que esta logueado*/
        StringBuilder html = new StringBuilder(""); 
        try{
            Pelicula p = (Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
            Usuario u = null;
            List lista = new LinkedList(abd.buscarPorFiltro(Usuario.class, "nameUsuario.equals(\""+session.getAttribute("user") +"\")"));
            if (lista.size()>0){
                u= (Usuario) lista.get(0);
            } 
            
            /*si hizo clic en alguna calidad o el usuario logueado es un abonado*/
            if (((request.getParameter("sd")!=null&&(!request.getParameter("sd").equals("0")))||((request.getParameter("hd")!=null)&&
                (!request.getParameter("hd").equals("0")))||(u!=null&&u.getTipoUsuario()==0)/*||(al!=null)*/)&&session.getAttribute("ver")==null){
                /*si hizo clic pero no se encuentra logueado muestra el cartel de informacion*/
                if (session.getAttribute("login")==null || session.getAttribute("login").toString().equals("false")){
                    session.setAttribute("elegirCalidad", false);    
                    html.append("       <script LANGUAGE=\"javascript\">\n");
                    html.append("           window.onload = infoLogin();\n");
                    html.append("       </script> \n");
                }
                else{
                    /* si esta todo ok pasa a verificar si no supero la cantidad   *
                     * de usuarios en simultaneo.                                  */
                    boolean capacidad_ver=true;
                    StringBuilder cmd= new StringBuilder("");
                    String res="";
                    try {
                //        System.out.println("3");
                        Configuracion conf=(Configuracion)abd.buscarPorId(Configuracion.class, new Long(1));
                        String ipServ = conf.getIpServidorVideo();
                        ipServ=ipServ.trim();
                        //String cmd = "netstat -ant | grep -e 200.50.183.131:5[0-9][0-9][0-9][0-9] | grep ESTABLISHED | wc -l";
                       // cmd = "sudo netstat -ant | grep -e "+ipServ+":5[0-9][0-9][0-9][0-9] | grep ESTABLISHED | wc -l";
                        cmd.append("sudo /usr/bin/python /scripts/cuenta.py ");
                        cmd.append(ipServ);
                        Process proc = Runtime.getRuntime().exec(cmd.toString());
                        InputStream is = proc.getInputStream();
                        int size;
                        String s;
                        int exCode = proc.waitFor();
                        StringBuffer ret = new StringBuffer();
                        while((size = is.available()) != 0) {
                            byte[] b = new byte[size];
                            is.read(b);
                            s = new String(b);
                            ret.append(s);
                        }
                        res = ret.toString().replace("\n", "");
                        String[] aux = res.split("<");
                        if(aux.length>1){
                            res=aux[0];
                        }
                        if ((Integer.parseInt(res)+1)>conf.getMaxCantConexion()){
                            capacidad_ver=false;
                            cantMaxCon=true;
                        }

                    } catch (Exception ex) {
                        System.out.println("ERROORRR HelperVideo.java 238");
                    }
                    /*si hay lugar para ver la pelicula este la ve*/
                    if (capacidad_ver){
                        session.setAttribute("calidad", "sd");
                        if (request.getParameter("hd")!=null && (!request.getParameter("hd").equals("0"))){
                            session.setAttribute("calidad", "hd");
                        }
                        if (u.getTipoUsuario()==0){
                            if(p.getExtHD()!=null){
                                session.setAttribute("calidad", "hd");
                            }
                        }
                        Double precio = 0.0; 
                        String calidad =session.getAttribute("calidad").toString();
                        Alquiler a = null;
                        a=estabaAlquilada(abd,session,p,calidad);
                        Boolean infoAlquilar=true;
                        if (calidad.equals("sd")){
                            if(!p.isEsGratisSD()){
                                precio=p.getPrecioSD();
                                if (a==null){
                                    infoAlquilar=false;
                                }
                            }
                        }else{
                            if(!p.isEsGratisHD()){
                                precio=p.getPrecioHD();
                                if (a==null){
                                    infoAlquilar=false;
                                }
                            }                        
                        }
                        html.append("       <script LANGUAGE=\"javascript\">\n");
                        html.append("           window.onload = function(){\n");
                        html.append("              window.location = 'auxiliar.jsp';\n");
                        html.append("           };\n");
                        html.append("       </script> \n");
                    }
                    else{
                        cantMaxCon=true;
                    }
                }
            }
        }catch(Exception e){}
        return html.toString();
    }
    
    public String redireccionar (HttpSession session){
        return "";
    }
    
    public String presentacionPelicula(HttpServletRequest request, HttpSession session, AccesoBD abd){
        Pelicula p = (Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
        StringBuilder html= new StringBuilder("           <table border=\"0\" class=\"presentacion\">\n");
        html.append("           <tr>\n");
        html.append("               <td class=\"fondoRedondoTL\"></td>\n");
        html.append("               <td class=\"fondoRedondo\"></td>\n");
        html.append("               <td class=\"fondoRedondoTR\"></td>\n");
        html.append("           </tr>\n");
        html.append("           <tr>\n");
        html.append("               <td class=\"fondoRedondo\"></td>\n");
        html.append("               <td class=\"fondoRedondo\"> \n");
        html.append("                   <div class=\"Descripcion\">  \n");
        html.append("                       <table border=\"0\" width=\"100%\">\n");
        html.append("                           <tr>\n");
        html.append("                               <td>\n");
        html.append("                                   <IMG SRC=\"");
        html.append(p.getPoster().getPath());
        html.append("\" WIDTH=176 HEIGHT=261 BORDER=2 ALT=\"imagen\">\n");
        html.append("                               </td>\n");
        html.append("                               <td style=\"vertical-align: top\">\n");
        html.append("                                   <table border=\"0\" >\n");
        html.append("                                       <tr>\n");
        html.append("                                           <td class=\"titulo\">");
        html.append(p.getTitulo());
        if(p.getTituloOriginal()!=null && (!p.getTituloOriginal().equals(""))){
            html.append("<h4>(");
            html.append(p.getTituloOriginal());
            html.append(")</h4>");
        }
        html.append("</td>\n");
        html.append("                                       </tr>\n");
        html.append("                                       <tr>\n");
        html.append("                                           <td class=\"datos\">");
        html.append(p.getGenero().getNombre());
        html.append(" | ");
        html.append(p.getIdioma());
        html.append(" | Año: ");
        html.append(p.getAnio());
        html.append(" | Duracion: ");
        html.append(p.getDuracion());
        html.append(" min </td>\n");
        html.append("                                       </tr>\n");
        html.append("                                       <tr>\n");
        html.append("                                           <td class=\"desc\"><p>");
        html.append(p.getSinopsis());
        html.append("</p>\n");
        Collection c =abd.buscarPorFiltro(RelacionPeliculaDirector.class, "pelicula.id == "+p.getId());        
        List<RelacionPeliculaDirector> rpds = null;
        if (c!=null){
            rpds = new LinkedList<RelacionPeliculaDirector>(c);
        }
        if (rpds != null && rpds.size()>0){
            html.append("                                           <p>Directores: ");
            for (int i = 0; i < rpds.size(); i++) {
                RelacionPeliculaDirector rpd = rpds.get(i);
                html.append(rpd.getDirector().getNombre());
                html.append(" ");
                html.append(rpd.getDirector().getApellido());
                if (i<rpds.size()-1){
                     html.append(","); 
                }
            }
             html.append("                                       </p>\n");
        }
        c=abd.buscarPorFiltro(RelacionPeliculaActor.class, "pelicula.id == "+p.getId());
        List<RelacionPeliculaActor> rpas = null;
        if (c!=null){
            rpas = new LinkedList<RelacionPeliculaActor>(c);
        }
        if (rpas !=null && rpas.size()>0){
            html.append("                                           <p>Actores: ");
            for (int i = 0; i < rpas.size(); i++) {
                RelacionPeliculaActor rpa = rpas.get(i);
                html.append(rpa.getActor().getNombre());
                html.append(" ");
                html.append(rpa.getActor().getApellido());
                if (i<rpas.size()-1){
                    html.append(","); 
                }
            }
            html.append("                                       </p>\n");
        }
        html.append("                                           </td>\n");
        html.append("                                       </tr>\n");
        html.append("                                   </table>\n");
        //        "                               <div id=\"linkTrailer\"> <a href=\"#\" OnClick=\"ver_trailer_pelicula();\">Ver el Trailer</a>\n"+
          //      "                               </div>\n"+
        html.append("                               <div id=\"trailer\" style=\"display:none;\"> ");
        String trailer = p.getTrailer();
        html.append("                               </div>\n");
        html.append("                               </td>\n");
        html.append("                               <td >\n");
        html.append("                                   <div style=\"float: right; width: 150px;\">\n");
        html.append("                                       <center>\n");
        html.append("                                           <form method=\"post\" name=\"vistaVideo\" action=\"Video.jsp\" >\n");
        html.append("<b>SELECCIONE LA CALIDAD A REPRODUCIR</b><br><br>\n");
        html.append("                                                   <input type=\"hidden\" name=\"sd\" value=\"0\">\n");
        html.append("                                                   <input type=\"hidden\" name=\"hd\" value=\"0\">\n");        
         if(p.getExtSD()!=null){
            html.append("                                                   <a href=\"#\" OnClick=\"linkSD(1);\"><IMG SRC=\"styles/images/botones/sd.jpg\" WIDTH=86 HEIGHT=70 border=2 ALT=\"sdef\"  box-shadow: 10px 10px 5px #888\"><div style=\"font-size:36px;\"></a>$");
            html.append(p.getPrecioSD().toString());
            html.append("</div><br><br>\n");
         }
         if(p.getExtHD()!=null){
            html.append("                                                   <a href=\"#\" OnClick=\"linkHD(2);\"><IMG SRC=\"styles/images/botones/hd.jpg\" WIDTH=86 HEIGHT=70 border=2  ALT=\"hdef\" style=\"box-shadow: 10px 10px 5px #888\"><div style=\"font-size:36px;\"></a>$");
            html.append(p.getPrecioHD().toString());
            html.append("</div><br><br>\n");
         }
         if(p.getExtFHD()!=null){
             //html +=    "                                                   <a href=\"#\" OnClick=\"linkHD(3);\"><IMG SRC=\"styles/images/botones/fhd_1.jpg\" WIDTH=86 HEIGHT=70  ALT=\"hdef\" style=\"box-shadow: 10px 10px 5px #888\"></a>\n";
         }
         html.append("                                               </form>\n");
        html.append("                                           </center>\n");
        html.append("                                       </div>\n");
        html.append("                                   </td>\n");
        html.append("                               </tr>\n");
        html.append("                           </table>\n");
        html.append("                       </div>\n");
        html.append("                   </td>\n");
        html.append( "                   <td class=\"fondoRedondo\"></td>\n");
        html.append("               </tr>\n");
        html.append("               <tr>\n");
        html.append("                   <td class=\"fondoRedondo\"></td>\n");
        html.append("                   <td class=\"fondoRedondo\">");
        html.append(anteriorSiguiente(abd, session));
        html.append("</td>\n");
        html.append("                   <td class=\"fondoRedondo\"></td>\n");
        html.append("               </tr>\n");
        html.append( "               <tr>\n");
        html.append("                   <td class=\"fondoRedondoBL\"></td>\n");
        html.append("                   <td class=\"fondoRedondo\"></td>\n");
        html.append( "                   <td class=\"fondoRedondoBR\"></td>\n");
        html.append("               </tr>\n");
        html.append("           </table>\n");
        html.append("           <br><br><br><br>\n");        
        return html.toString();
    }
    
    public String cargando(HttpServletRequest request){
       return "       <script type=\"text/javascript\" src=\"js/overlayLogin.js\"></script>\n<script>\njwplayer('container').play('true');</script>";
   }
    
   private String votacion (){
       StringBuilder html = new StringBuilder("<div id=\"votoEstrellas\">\n");
       html.append("    <table border=\"0\">\n");
       html.append("        <tr>\n");
       html.append("            <td id=\"voto1\" style=\"width: 30px; height: 32px; background-image: url('styles/images/star_off.png');\" onmouseover=\"marcar1();\" onclick=\"votar(1);\"></td>\n");
       html.append("            <td id=\"voto2\" style=\"width: 30px; height: 32px; background-image: url('styles/images/star_off.png');\" onmouseover=\"marcar2();\" onclick=\"votar(2);\"></td>\n");
       html.append("            <td id=\"voto3\" style=\"width: 30px; height: 32px; background-image: url('styles/images/star_off.png');\" onmouseover=\"marcar3();\" onclick=\"votar(3);\"></td>\n");
       html.append("            <td id=\"voto4\" style=\"width: 30px; height: 32px; background-image: url('styles/images/star_off.png');\" onmouseover=\"marcar4();\" onclick=\"votar(4);\"></td>\n");
       html.append("            <td id=\"voto5\" style=\"width: 30px; height: 32px; background-image: url('styles/images/star_off.png');\" onmouseover=\"marcar5();\" onclick=\"votar(5);\"></td>\n");
       html.append("        </tr>\n");
       html.append("    </table>\n");
       html.append("</div>\n");
       return html.toString();
   }
   
   public String anteriorSiguiente (AccesoBD abd, HttpSession session){
       StringBuilder html = new StringBuilder("");
       String id=session.getAttribute("movie").toString();
       Capitulo cap = (Capitulo)abd.buscarPorId(Capitulo.class, Long.parseLong(id));
       if (cap!=null){
           List<Capitulo> caps = new LinkedList<Capitulo>(abd.getObjectsOrdered(Capitulo.class, "baja == false && temporada.id=="+cap.getTemporada().getId(), "numCap ascending"));
           html.append("<div class='navCap'>");
           html.append("       <form method=\"post\" name=\"selectVideo\" action= \"Video.jsp\">\n");
           html.append("           <input type=\"hidden\" name=\"idVideo\">\n");
           html.append("       </form>\n");
           Long idPre=null;
           Long idSig=null;
           for (int i = 0; i < caps.size(); i++) {
               if (caps.get(i).getId().toString().equals(id)){
                   if (i+1<caps.size()){
                       idSig=caps.get(i+1).getId();
                   }
                   break;
               }
               idPre=caps.get(i).getId();
           }
           if (idPre==null)
            html.append( "   <div class='navCapPre'></div>");
           else{
               html.append("   <div class='navCapPre'><a href=\"#\" OnClick=\"linkAVideo('");
               html.append(idPre);
               html.append("');\"><IMG SRC=\"styles/images/botones/capAnterior.png\"></a></div>");
           }
           if (idSig==null)
           html.append("   <div class='navCapSig'></div>");           
           else{
               html.append("   <div class='navCapSig'><a href=\"#\" OnClick=\"linkAVideo('");
           html.append(idSig);
           html.append("');\"><IMG SRC=\"styles/images/botones/capSiguiente.png\"></a></div>");
           }
           html.append("</div>");
       }
       return html.toString();
   }
}
