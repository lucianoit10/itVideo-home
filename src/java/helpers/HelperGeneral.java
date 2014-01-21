package helpers;

import entidades.Alquiler;
import entidades.Configuracion;
import entidades.Estadistica;
import entidades.Genero;
import entidades.Pelicula;
import entidades.RelacionPeliculaActor;
import entidades.RelacionPeliculaDirector;
import entidades.Usuario;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public abstract class HelperGeneral {
    
    
    /**
     * si no existia una conexion la establece y  guarda en la session.
     * 
     * @param session session actual la cual almacena la conexion con la base de datos.
     */
    public void iniciarConexion (HttpSession session){
        if (session.getAttribute("conexion")==null){
            AccesoBD abd = new AccesoBD();
            session.setAttribute("conexion", abd);
        }
    }
    
    /**
     * Genera el comienzo del codigo de cada una de las paginas,llamando a los diferentes 
     * metodos, tanto implementados en esta clase como los abstractos implementados
     * en las demas clases que heredan de esta.
     * 
     * Retorna el codigo del comienzo de la pagina con imports y definicion de JS y CSS.
     * 
     * @param abd
     * @param session
     * @param request
     * @param title
     * @return String
     */
    public String GenerarComienzoGeneral(AccesoBD abd, HttpSession session, HttpServletRequest request, String title){
        inicializacion(abd,session,request);
        StringBuilder html = new StringBuilder(generarComienzo());
        html.append(importsCSS());
        html.append(title(session, title));
        html.append(generarMedio());
        html.append(importsJS());
        return html.toString();
    }
    
    /**
     * Genera el codigo del comienzo de la pagina.
     *
     * @return String.
     */
    public String generarComienzo (){
        StringBuilder html = new StringBuilder("<html>\n");
        html.append("   <head>\n");
        html.append("       <META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\">\n");
        html.append("       <META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">\n");
        html.append("       <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\"/>\n");
        html.append("       <link rel=\"stylesheet\" type=\"text/css\" href=\"styles/style_1.css\" />\n");
        html.append("       <link href='styles/images/itVideo.gif' rel='shortcut icon' type='image/gif'>\n");
        html.append("<script type=\"text/javascript\">\n");
        html.append("  var _gaq = _gaq || [];\n");
        html.append("  _gaq.push(['_setAccount', 'UA-33713732-1']);\n");
        html.append("  _gaq.push(['_trackPageview']);\n");
        html.append("  (function() {\n");
        html.append("    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n");
        html.append("    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n");
        html.append("    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n");
        html.append("  })();\n");
        html.append("function hacerVisible(actual,nuevo){var A=document.getElementById(actual);var B=document.getElementById(nuevo);A.style.display=\"none\";B.style.display=\"block\";B.disabled=false;}");
        html.append("</script>");
        return html.toString();
    }
    
    /**
     * Genera el codigo de los CSS particulares de la pagina.
     * @return String 
     */
    public abstract String importsCSS ();  
    
    /**
     * Genera el codigo de los JS particulares de la pagina.
     * @return String 
     */
    public abstract String importsJS (); 
    
    /**
     * Inicializa la pagina con los valores necesarios dentro de el Session como en el request.
     * @param abd
     * @param session
     * @param request 
     */
    public abstract void inicializacion (AccesoBD abd,HttpSession session,HttpServletRequest request);
    
    /**
     * Genera el codigo del titulo de la pagina.
     * 
     * Retorna el codigo del titulo de la pagina.
     * 
     * @param session
     * @param title
     * @return String
     */
    public abstract String title (HttpSession session,String title); 
    
    /**
     * Genera el contenido de la pagina junto a la implementacion de su comportamineto.
     * 
     * Retorna el contenido de la Pagina.
     * @param abd
     * @param session
     * @param request
     * @param pag
     * @return String
     */
    public abstract String contenido (AccesoBD abd,HttpSession session,HttpServletRequest request,String pag);  
    
    /**
     * Genera el codigo de la interseccion entre el head de la pagina y el body,
     * junto a el JS comun a todas las paginas.
     * 
     * Reorna el codigo central de la pagina.
     * @return String
     */
    public String generarMedio(){
        StringBuilder html = new StringBuilder("   </head>\n");
        html.append("   <body  OnLoad=\"NoBack();\">\n");
        html.append("       <script type=\"text/javascript\" src=\"js/FuncionesComunes.js\"></script>\n");
        return html.toString();
    }
    
    /**
     * Genera el final del codigo (cierra el body y el html)
     * 
     * Reotrna el codigo del final de la pagina.
     * @return String
     */
    public String generarFinal(){
        StringBuilder html = new StringBuilder("   </body>\n");
        html.append("</html>\n");
        return html.toString();
    }    
    
   /**
     * Genera el codigo del Baner de la pagina donde el usuario se loguea  y aparecen
     * las opciones de cerrar secion y modificar perfil. se implementa dicha funcionalidad.
     * 
     * Retorna el codigo para el banner de la pagina.
     * 
     * @param abd
     * @param session
     * @param request
     * @param pag
     * @return String
     */
    public String banner(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {
        StringBuilder html =  new StringBuilder("");
        boolean loginError=false;
        if(request.getParameter("cerrar")!= null){
            session.setAttribute("login", false);
            session.setAttribute("user", null);             
        }
        if(request.getParameter("ingresar")!= null && request.getParameter("ingresar").toLowerCase().equals("true")){
            /*seteo en la sesion el atributo user q contendra la info de la persona logeada*/
              
            String usuario  = request.getParameter("usuarioLogin");
            String password = request.getParameter("passLogin");
            //if(esUsuario(usuario,password)){ //dentro de la base de datos
                StringBuilder filtro = new StringBuilder ("nameUsuario.equals(\"");
                filtro.append(usuario);
                filtro.append("\") && password.equals(\"");
                filtro.append(password);
                filtro.append("\")");
                Usuario user = null;
                List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,filtro.toString()));                
                if(l.size()>0){
                    user=l.get(0);
                }
                // session.setAttribute("user", usuario); 
            //*/
                if (user!=null){
                    session.setAttribute("user", user.getNameUsuario()); 
                    session.setAttribute("login", true);
                }else {
                    loginError = true;
                }
            /*
            } else {
                String datos = esUsuarioWS(usuario,password);
                if(!datos.equals("")){
                    String filtro   = "nameUsuario=="+usuario;
                    Usuario usuario = (Usuario)abd.buscarPorFiltro(Usuario.getClass(),filtro);
                    if (usuario==null){
                        //se crea el nuevo usuario en el sistema con los datos provistos en el WS
                        usuario = new Usuario (...);
                    }
                    usuario.setPassword(password);
                    abd.hacerPersistente(usuario);
                }
                loginError = true;
            }
            */
        }
        //si no esta logeado
        if (session.getAttribute("login")==null || session.getAttribute("login").toString().equals("false")){
            html.append("           <script LANGUAGE=\"javascript\">");                        
            html.append("               window.onload = function(){");
            html.append("                   window.location = 'index.jsp';");
            html.append("               };");
            html.append("           </script> ");
            session.setAttribute("elegirCalidad", false);    
            session.setAttribute("calidad", null);    
            session.setAttribute("ver", false);         
        }
        else{            
            Usuario u =  (Usuario) new LinkedList(abd.buscarPorFiltro(Usuario.class, "nameUsuario.equals('"+session.getAttribute("user").toString()+"')")).get(0);
            String nombre="";
            if (u.getNombreApellido()!=null && !u.getNombreApellido().equals(""))
                nombre=u.getNombreApellido();
            else
                nombre=u.getNameUsuario();
            html.append("       <form method=\"post\" id=\"CerrarSesion\" name=\"CerrarSesion\" action= \"");
            html.append(pag);
            html.append("\">\n");            
            html.append("           <input type=\"hidden\" name=\"cerrar\">\n");
            html.append("           <table>\n");
            html.append("               <tr>\n");
            html.append("                   <td></td> <td><a href='perfil.jsp' title='Edita tu Perfil'><IMG SRC=\"styles/images/botones/edit_perfil.png\" WIDTH=28 HEIGHT=28 ALT=\"editar\" ></a></td> <td>");
            html.append(nombre);
            html.append("</td>\n");
            html.append("                   <td><a href=\"#\"   OnClick= \"cerrarSesion()\" ><IMG SRC=\"styles/images/botones/logoutG.png\" WIDTH=37 HEIGHT=37 ALT=\"logout\" title=\"logout\" ></a></td>\n");
            html.append("               </tr>\n");
            html.append("           </table>\n");
            html.append("       </form>\n");
        }
        return html.toString();
    }
    
    /**
     * Genera el codigo de el menu de busqueda con los distintos formularios.
     * 
     * Retorna el codigo del sector del menu y busqueda.
     * 
     * @param session
     * @param pag
     * @return String
     */
    public String menuYBusqueda(HttpSession session,String pag){
        AccesoBD abd = (AccesoBD) session.getAttribute("conexion");
        StringBuilder html = new StringBuilder( "<br> <br><center>\n");
            //    "   <form name=\"menuPrincipal\" method=\"get\" action=\"ListadoPeliculas.jsp\">\n"+
        html.append("       <table id='tablaMenu' style=\"width:1000px;\">\n");  
        html.append("           <tr>\n");
        html.append("               <td align=\"right\">\n");
        html.append("                   <form name=\"menuBuscar\" method=\"get\" action=\"busquedas.jsp\" >\n");
        html.append("       <table>\n"); 
        html.append("           <tr>\n");
                     //           "               <td>\n"+
                          //      "               <td style=\"text-align: left;\">\n"+
                       //         "                       Ordenar la proxima busqueda por : \n"+
                         //       "               </td>\n"+
        html.append("               <td>\n");
                            //    "               <td style=\"text-align: left;\">\n"+
        html.append("                       Buscar por : \n");
        html.append("               </td>\n");
        html.append("           </tr>\n");
        html.append("           <tr>\n");
                    //            "               <td style=\"text-align: left; width:130px\">\n"+
                             //   "               <td>\n"+
                      /*          "                       <select name=\"orden\">\n"+
                                "                           <option selected value=\"1\">Recientes</option>\n"+
                                "                           <option value=\"2\">Mejor Votadas</option>\n"+
                                "                           <option value=\"3\">+ Vistas</option>\n"+
                                "                       </select>\n"+
                                "               </td>\n"+*/
        html.append("               <td style=\"text-align: left;\">\n");
        try{
            abd.iniciarTransaccion();
            List<Genero> list = abd.listar(new Genero());
            html.append("                   <!-- Buscador -->\n");
            html.append("                       <select name=\"generobusc\" id=\"generobusc\">\n");
            if (list.size()>0){
                html.append("                            <option selected value=\"0\">Todos los Generos</option>\n");
                for (int i=0;i<list.size();i++){
                    html.append("                            <option value=\"");
                    html.append(list.get(i).getId());
                    html.append("\">");
                    html.append(list.get(i).getNombre().toUpperCase());
                    html.append("</option>\n");
                }                                
                html.append("                       </select>\n");
            }
           // abd.concretarTransaccion();
        }catch (Exception e){
            abd.rollbackTransaccion();
        }
        html.append("                   <!-- Buscador -->\n");
        html.append("                       <select name=\"calidadBusc\">\n");
        html.append("                           <option selected value=\"0\">Todas las Calidades</option>\n");
        html.append("                           <option value=\"1\">SD</option>\n");
        html.append("                           <option value=\"2\">HD</option>\n");
        html.append("                       </select>\n");
        html.append("                   <!-- Buscador -->\n");
        html.append("                       <select name=\"idioma\">\n");
        html.append("                           <option selected value=\"0\">Todos Idiomas</option>\n");
        html.append("                           <option value=\"1\">Español</option>\n");
        html.append("                           <option value=\"2\">Subtitulado</option>\n");
        html.append("                       </select>\n");
        html.append("                   <!-- Buscador -->\n");
        html.append("                       <select name=\"clasificacion\">\n");
        html.append("                           <option selected value=\"0\">Todas las Clasif.</option>\n");
        html.append("                           <option value=\"ATP\">ATP</option>\n");
        html.append("                           <option value=\"+13\">+13</option>\n");
        html.append("                           <option value=\"+16\">+16</option>\n");
        html.append("                           <option value=\"+18\">+18</option>\n");
        html.append("                       </select>\n");
        html.append("                   <!-- Buscador -->\n");
        html.append("                       <select id=\"criterio\" name=\"criterio\">\n");
        html.append("                           <option selected value=\"1\">Titulo</option>\n");
        html.append("                           <option value=\"2\">Año</option>\n");
        html.append("                           <option value=\"3\" style=\"display:none;\">Genero</option>\n");
        html.append("                           <option value=\"4\">Actor</option>\n");
        html.append("                           <option value=\"5\">Director</option>\n");
        html.append("                           <option value=\"6\" style=\"display:none;\">Clasificaci&oacute;n</option>\n");
        html.append("                           <option value=\"7\" style=\"display:none;\">Estreno</option>\n");
        html.append("                           <option value=\"8\" style=\"display:none;\">Mas Recientes</option>\n");
        html.append("                           <option value=\"9\" style=\"display:none;\">Mas Vistas</option>\n");
        html.append("                           <option value=\"10\" style=\"display:none;\">Mejor Votadas</option>\n");
        html.append("                       </select>\n");
        html.append("                       <input type=\"text\" name=\"textoBuscar\" value=\"Buscar...\" onclick=\"limpiandoTexto('Buscar...',this)\"/>\n");
        html.append("                       <input  class=\"boton\" type=\"submit\" id=\"buscar\" name=\"buscar\" value=\"Buscar\"/>\n");
        html.append("               </td>\n");
        html.append("           </tr>\n");
        html.append("       </table>\n");
        html.append("                   </form>\n");
        html.append("               </td>\n");
        html.append("           </tr>\n");
        html.append("       </table>\n");
   //     "   </form>\n"+
        html.append("<br> </center>\n");
       return html.toString();
    }
    
    public String menuIzq (AccesoBD abd, HttpSession session){
        StringBuilder html = new StringBuilder("");
        Usuario u = null;
        try{
            List lista = new LinkedList(abd.buscarPorFiltro(Usuario.class, "nameUsuario.equals(\""+session.getAttribute("user") +"\")"));
            if (lista.size()>0){
                u= (Usuario) lista.get(0);
            }
            html.append("                       <form name=\"menuPrincipal\" method=\"get\" action=\"busquedas.jsp\">\n");
            html.append("                           <input type=\"hidden\" name=\"alquiladas\" value=\"0\"/>\n");
            html.append("                           <input type=\"hidden\" name=\"orden\" value=\"0\"/>\n");
            html.append("                       </form>\n");
            html.append("<div id='menu' class='menu'>");
            html.append("   <div>");
            html.append("      <hr>");
            html.append("   </div>");
            html.append("   <div class='itemMenu'>");
            html.append("           <a href='#' onclick=\"cargarHome();\">Inicio</a><hr>");
            html.append("   </div>");
            html.append("   <div class='itemMenu'>");
            html.append("   <a href='#' Onclick='cargarListasPeliculas();'>Pel&iacute;culas</a><hr>");
            html.append("   </div>");
            html.append("   <div class='itemMenu'>");
            html.append("   <a href='#' Onclick='cargarListasSeries();'>Series</a><hr>");
            html.append("   </div>");
            html.append("   <div class='itemMenu'>");
            html.append("   <a href='#' onclick=\"cargarTV();\">TV</a><hr>");
            html.append("   </div>");
            if(u!=null && u.getTipoUsuario()!=0){
               html.append("   <div class='itemMenu'>");
               html.append("   <a href='#' onclick=\"alquilada();\">Alquiladas</a><hr>");
               html.append("   </div>");
            }
            html.append("</div>");
        }catch (Exception e){
        //   TENGO Q Escribir cual es el tratamiento por el error de lista vacia
        }
        return html.toString();
    }
    
    /**
     * Genera el codigo del footer de la pagina.
     * 
     * Reotrna el codigo del footer.
     * 
     * @return String
     */
    public String PieDePagina (){
        StringBuilder html = new StringBuilder("<br><br><br><div id='footer'><br>\n");
        html.append("<center> Optimizado para <a href=\"http://www.mozilla.org/es-AR/firefox/new/\">Firefox</a> y <a href=\"https://www.google.com/chrome\">Chrome</a><br>");
        html.append(" Desarrollado para Pampacom SRL. Firmat - Santa Fe");
        html.append("<br>© 2012 -  <a href=\"http://www.it10coop.com.ar/\">Cooperativa de Trabajo Inform&aacute;tica y Telecomunicaciones 10 Ltda.</a></center></div>\n");
        return html.toString();
    }
    
    /**
     * Evalua si una pelicua esta dentro de su periodo de alquiler por el usuario logueado
     * de ser asi, retorna el alquiler correspondiente, en caso contrario retorna NULL
     * @param abd
     * @param session
     * @param p
     * @param calidad
     * @return Alquiler
     */
    protected Alquiler estabaAlquilada(AccesoBD abd,HttpSession session,  Pelicula p, String calidad){
        StringBuilder filtro = new StringBuilder("nameUsuario.equals(\"");
        filtro.append(session.getAttribute("user"));
        filtro.append("\")");
        Usuario user = null;
        List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,filtro.toString()));                
        if(l.size()>0){
            user=l.get(0);
        }
        Alquiler a = null;
        try {            
            StringBuilder filtro2 = new StringBuilder ("usuario.id == ");
            filtro2.append(user.getId());
            filtro2.append(" && pelicula.id == ");
            filtro2.append(p.getId());
            filtro2.append(" && estaAbierta == true && calidad.equals(\"");
            filtro2.append(calidad);
            filtro2.append("\")");
            List<Alquiler>la=new LinkedList<Alquiler>(abd.buscarPorFiltro(Alquiler.class,filtro2.toString()));                
            if(la.size()>0){
                a=la.get(0);
                //verifica q no se cunmplio el tiempo del alquiles
                Configuracion c = (Configuracion) abd.buscarPorId(Configuracion.class, Long.parseLong("1"));
                Calendar cal = Calendar.getInstance();
                cal.setTime(a.getFechaDeAlquiler());
                cal.add(Calendar.HOUR_OF_DAY, c.getHsAlquiler());
                Date aux = cal.getTime();
                if(aux.before(new Date())){
                    a.setEstaAbierta(false);
                    abd.hacerPersistente(a);
                    return null;
                }else{
                    return a;                
                }                
            }else{
                return null;
            }            
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Evalua si una pelicua esta dentro de su periodo de alquiler por el usuario logueado
     * de ser asi, retorna el alquiler correspondiente, en caso contrario retorna NULL
     * @param abd
     * @param session
     * @param p
     * @param calidad
     * @return Alquiler
     */
    protected Alquiler estabaAlquilada(AccesoBD abd,HttpSession session,  Pelicula p){
        StringBuilder filtro = new StringBuilder("nameUsuario.equals(\"");
        filtro.append(session.getAttribute("user"));
        filtro.append("\")");
        Usuario user = null;
        List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,filtro.toString()));                
        if(l.size()>0){
            user=l.get(0);
        }
        Alquiler a = null;
        try {            
            StringBuilder filtro2 = new StringBuilder("usuario.id == ");
            filtro2.append(user.getId());
            filtro2.append(" && pelicula.id == ");
            filtro2.append(p.getId());
            filtro2.append(" && estaAbierta == true");
            List<Alquiler>la=new LinkedList<Alquiler>(abd.buscarPorFiltro(Alquiler.class,filtro2.toString()));                
            if(la.size()>0){
                a=la.get(0);
                //verifica q no se cunmplio el tiempo del alquiles
                Configuracion c = (Configuracion) abd.buscarPorId(Configuracion.class, Long.parseLong("1"));
                Calendar cal = Calendar.getInstance();
                cal.setTime(a.getFechaDeAlquiler());
                cal.add(Calendar.HOUR_OF_DAY, c.getHsAlquiler());
                Date aux = cal.getTime();
                if(aux.before(new Date())){
                    a.setEstaAbierta(false);
                    abd.hacerPersistente(a);
                    return null;
                }else{
                    return a;                
                }                
            }else{
                return null;
            }            
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Retorna el src de la imagen que es el logo de la pagina.
     * @param abd
     * @return String
     */
    public String getLogo(AccesoBD abd){
        List l = abd.listar(new Configuracion());
        if (l.size()>0){
            if(((Configuracion) l.get(0)).getLogo()!=null){
                return ((Configuracion) l.get(0)).getLogo().getPath();
            }
        }
        return "";
    }
    
    /**
     * Genera el codigo del contenido que se muestra en la pagina si esta es abierta con un navegador no compatible.
     * 
     * Retorna el codigo del contenido en caso de un navegador incompatible.
     * 
     * @return 
     */
    public String navegadorIncompatible (){
        StringBuilder html =  new StringBuilder ("<table border=\"0\" style=\"color:black\">");
        html.append("   <thead>");
        html.append("       <tr>");
        html.append("           <th colspan=\"2\"><h2>Su navegador no es compatible con nuestro sitio.</h2></th>");
        html.append("       </tr>");
        html.append("       <tr>");
        html.append("           <th colspan=\"2\">Por favor instale alguno de los siguientes navegadores <br>");
        html.append("               para poder disfrutar de nuestros servicios.");
        html.append("           </th>");
        html.append("       </tr>");
        html.append("    </thead>");
        html.append("    <tbody>");
        html.append("       <tr>");
        html.append("           <td><center><b>MOZILLA FIREFOX</b></center></td>");
        html.append("           <td><center><b>GOOGLE CHROME</b></center></td>");
        html.append("       </tr>");
        html.append("       <tr>");
        html.append("           <td><a href=\"http://www.mozilla.org/es-AR/firefox/new/\"><img src=\"styles/images/firefoxLogo.png\" width=\"400px\" height=\"160px\"/></a></td>");
        html.append("           <td><a href=\"https://www.google.com/chrome\"><img src=\"styles/images/chrome.gif\" width=\"560px\"  height=\"160px\"/></a></td>");
        html.append("       </tr>");
        html.append("    </tbody>");
        html.append("</table>");
      return html.toString();
   }
      
    /**
     * Genera el codigo de un tooltip para una pelicula p y establese el link 
     * para ver el overlay de la pelicula
     * 
     * Retorna el codigo del tooptip.
     * 
     * @param p
     * @param abd
     * @return String
     */
    public String codTooltip (Pelicula p, AccesoBD abd){
        StringBuilder html=new StringBuilder("                           <div class=\"tooltip\" id=\"tooltip");
        html.append(p.getId());
        html.append("\"  style=\"padding-left: 10px; background: #333030; width: 300px;display: none; color:white;\" >\n");
        html.append("                               <div style=\"padding-left: 10px;padding-right: 10px;padding-bottom: 10px;\">\n");
        Estadistica e = null;
        StringBuilder filtro = new StringBuilder("pelicula.id==");
        filtro.append(p.getId());
        List<Estadistica>ce=new LinkedList<Estadistica>(abd.buscarPorFiltro(Estadistica.class,filtro.toString()));                
        if(ce.size()>0){
            e=ce.get(0);
        }else{
            e= new Estadistica(p);
        }
        abd.hacerPersistente(e);
        html.append("                                       <div style=\"float: right;\">\n");
        html.append("                                           <table border=\"2\" style=\"background: grey;\">\n");
        html.append("                                               <tr>\n");
        html.append("                                                   <td>\n");
        html.append("                                                       <p>PUNTAJE</p>\n");
        html.append("                                                       <h style=\"color: yellow;\">");
        html.append(e.getPromedioVotos());
        html.append("/5 </h1>\n");
        html.append("                                                       <hr>\n");
        html.append("                                                       <p>Votos: ");
        html.append(e.getCantidadVotos());
        html.append("<br>\n");
        html.append("                                                       Vistas: ");
        html.append(e.getCantidadDeVistas());
        html.append("</p>\n");
        html.append("                                                   </td>\n");
        html.append("                                               </tr>\n");
        html.append("                                           </table>\n");
        html.append("                                       </div>\n");
        html.append("                                    <a href=\"#\" OnClick=\"linkAVideo('");
        html.append(p.getId());
        html.append("')\"><h3 style=\"color: white;\">");
        html.append(p.getTitulo().toUpperCase());
        html.append("</h3>");
        if(p.getTituloOriginal()!=null && (!p.getTituloOriginal().equals(""))){
            html.append("<h4  style=\"color: white;\">(");
            html.append(p.getTituloOriginal());
            html.append(")</h4>");
        }
        html.append("</a>\n");
        html.append("                                    <p>");
        html.append(p.getGenero().getNombre());
        html.append(" | ");
        html.append(p.getIdioma());
        html.append(" | ");
        html.append(p.getClasificacion());
        html.append("<br>\n");
        html.append("                                       Año: ");
        html.append(p.getAnio());
        html.append(" | Duracion: ");
        html.append(p.getDuracion());
        html.append(" min <br>\n");
        Collection c =abd.buscarPorFiltro(RelacionPeliculaDirector.class,filtro.toString());        
                List<RelacionPeliculaDirector> rpds = null;
                if (c!=null){
                    rpds = new LinkedList<RelacionPeliculaDirector>(c);
                }
                if (rpds != null && rpds.size()>0){
                    html.append("                                       <p>Directores: ");
                    for (int i = 0; i < rpds.size(); i++) {
                        html.append(rpds.get(i).getDirector().getNombreApellido());
                        if (i<rpds.size()-1){
                            html.append(", "); 
                        }
                    }
                    html.append("                                           </p>\n");
                }
                c=abd.buscarPorFiltro(RelacionPeliculaActor.class,filtro.toString());
                List<RelacionPeliculaActor> rpas = null;
                if (c!=null){
                    rpas = new LinkedList<RelacionPeliculaActor>(c);
                }
                if (rpas !=null && rpas.size()>0){
                    html.append("                                       <p>Actores: ");
                    for (int i = 0; i < rpas.size(); i++) {
                        html.append(rpas.get(i).getActor().getNombreApellido());
                        if (i<rpas.size()-1){
                            html.append(", "); 
                        }
                    }
                    html.append("                                           </p>\n");
                }

        html.append("                                    <button  rel=\"#popup");
        html.append(p.getId());
        html.append("\" type=\"button\" class=\"boton\">+INFO Y TRAILER</button>\n");
        html.append("                                </div>\n");
        html.append("                            </div>\n");
        return html.toString();
    }
    
    /**
     * Genera el codigo de un overlay para una pelicula p y establese el link 
     * para ver la pelicula
     * 
     * Retorna el codigo del overlay.
     * 
     * @param p
     * @param abd
     * @return String
     */
    public String codPopups (Pelicula p, AccesoBD abd){
        StringBuilder html =  new StringBuilder ("                                   <div class=\"apple_overlay\" id=\"popup");
        html.append(p.getId());
        html.append("\" style=\"background-image:url(styles/images/petrol.png);color:#fff;\"><a class=\"close\"></a>");
        html.append("                                       <h2 style=\"margin:0px\">");
        html.append(p.getTitulo());
        html.append("</h2>\n");
        if(p.getTituloOriginal()!=null && (!p.getTituloOriginal().equals(""))){
            html.append("<h4>(");
            html.append(p.getTituloOriginal());
            html.append(")</h4>");
        }
        try{
            String trailer = p.getTrailer();
            if (trailer!=null && !trailer.equals("")){                    
                trailer =trailer.split("&")[0];
                trailer =trailer.split("=")[1];
                html.append("                                   <object width=\"420\" height=\"315\" style=\"float: left; margin:0px 20px 0 0;\">\n");
                html.append("                                       <param name=\"movie\" value=\"http://www.youtube-nocookie.com/v/");
                html.append(trailer);
                html.append("?version=3&amp;hl=es_ES&amp;rel=0\"></param>\n");
                html.append("                                       <param name=\"allowFullScreen\" value=\"true\"></param>\n");
                html.append("                                       <param name=\"allowscriptaccess\" value=\"always\"></param>\n");
                html.append("                                       <embed src=\"http://www.youtube-nocookie.com/v/");
                html.append(trailer);
                html.append("?version=3&amp;hl=es_ES&amp;rel=0\" type=\"application/x-shockwave-flash\" width=\"420\" height=\"315\" allowscriptaccess=\"always\" allowfullscreen=\"true\"></embed>\n");
                html.append("                                   </object>");
           }
        }catch(Exception e){}
        html.append("                                       <p  sytle=\"color: blue;\"><button type=\"button\" class=\"boton\" OnClick=\"linkAVideo('");
        html.append(p.getId());
        html.append("')\">VER LA PELICULA</button></p>");
        Estadistica e = null;
        StringBuilder filtro = new StringBuilder("pelicula.id==");
        filtro.append(p.getId());
        List<Estadistica>ce=new LinkedList<Estadistica>(abd.buscarPorFiltro(Estadistica.class,filtro.toString()));                
        if(ce.size()>0){
            e=ce.get(0);
        }else{
            e= new Estadistica(p);
        }
        abd.hacerPersistente(e);
        html.append("                                       <div style=\"float: right;\">\n");
        html.append("                                           <table border=\"2\" style=\"background: grey;\">\n");
        html.append("                                               <tr>\n");
        html.append("                                                   <td>\n");
        html.append("                                                       <p>PUNTAJE</p>\n");
        html.append("                                                       <h1 style=\"color: yellow;\">");
        html.append(e.getPromedioVotos());
        html.append("/5 </h1>\n");
        html.append("                                                       <hr>\n");
        html.append("                                                       <p>Votos: ");
        html.append(e.getCantidadVotos());
        html.append("<br>\n");
        html.append("                                                       Vistas: ");
        html.append(e.getCantidadDeVistas());
        html.append("</p>\n");
        html.append("                                                   </td>\n");
        html.append("                                               </tr>\n");
        html.append("                                           </table>\n");
        html.append("                                       </div>\n");
        html.append("                                       <p>");
        html.append(p.getGenero().getNombre());
        html.append(" | ");
        html.append(p.getIdioma());
        html.append(" | Año: ");
        html.append(p.getAnio());
        html.append(" | Duracion: ");
        html.append(p.getDuracion());
        html.append(" min </p>\n");
        html.append("                                       <p>");
        html.append(p.getSinopsis());
        html.append("</p>\n");
        Collection c =abd.buscarPorFiltro(RelacionPeliculaDirector.class,filtro.toString());        
        List<RelacionPeliculaDirector> rpds = null;
        if (c!=null){
            rpds = new LinkedList<RelacionPeliculaDirector>(c);
        }
        if (rpds != null && rpds.size()>0){
            html.append("                                       <p>Directores: ");
            for (int i = 0; i < rpds.size(); i++) {
                html.append(rpds.get(i).getDirector().getNombreApellido());
                if (i<rpds.size()-1){
                    html.append(", "); 
                }
            }
            html.append("                                           </p>\n");
        }
        c=abd.buscarPorFiltro(RelacionPeliculaActor.class, filtro.toString());
        List<RelacionPeliculaActor> rpas = null;
        if (c!=null){
            rpas = new LinkedList<RelacionPeliculaActor>(c);
        }
        if (rpas !=null && rpas.size()>0){
            html.append("                                       <p>Actores: ");
            for (int i = 0; i < rpas.size(); i++) {
                html.append(rpas.get(i).getActor().getNombreApellido());
                if (i<rpas.size()-1){
                    html.append(", "); 
                }
            }
            html.append("                                           </p>\n");
        }
        
        if(p.getExtSD()!=null){ 
            html.append("                                           <p>CALIDAD SD: ");
            if (p.isEsGratisSD()){
                html.append("ES GRATIS ");
            }else{
                html.append("$");
                html.append(p.getPrecioSD().toString());
            }
            html.append("                                       </p>\n");
        }
        if(p.getExtHD()!=null){
            html.append("                                           <p>CALIDAD HD: ");
            if (p.isEsGratisHD()){
               html.append("ES GRATIS ");
            }else{
                html.append("$");
                html.append(p.getPrecioHD().toString());
            }
            html.append("                                           </p>\n");
        }    
        
        html.append("                                   </div>\n");
        return html.toString();
    }

    public String generarLogo(String url){
        StringBuilder html = new StringBuilder("<a href='#' onclick=\"cargarHome();\"><IMG SRC=\"");
        html.append(url);
        html.append("\" ALT=\"logo\"></a>");
        return html.toString();
    }
}

