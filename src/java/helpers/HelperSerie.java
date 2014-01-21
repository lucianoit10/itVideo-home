package helpers;

import entidades.Alquiler;
import entidades.Capitulo;
import entidades.Pelicula;
import entidades.RelacionSerieActor;
import entidades.RelacionSerieDirector;
import entidades.Serie;
import entidades.Temporada;
import entidades.Usuario;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperSerie extends HelperGeneral{

    @Override
    public String importsCSS() {                        
        return "<link rel=\"stylesheet\" href=\"styles/fundationAccordion/foundation.css\"  type=\"text/css\">\n";
    }

    @Override
    public String importsJS() {
        StringBuilder html =  new StringBuilder ("       <script type=\"text/javascript\" src=\"js/jfoundation/foundation.min.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/foundation/jquery.foundation.accordion.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/foundation/app.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/foundation/jquery.js\"></script>\n");
        html.append("<script type=\"text/javascript\">\n $(document).ready(function() {\n $(document).foundationAccordion();}</script>\n");
        return html.toString();
    }

    @Override
    public void inicializacion(AccesoBD abd,HttpSession session,HttpServletRequest request) {
        if (request.getParameter("idVideo") != null){
            Pelicula p=(Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(request.getParameter("idVideo")));
            session.setAttribute("movie",p.getId() );
        }        
        if (request.getParameter("idSerie") != null){
            Serie p=(Serie)abd.buscarPorId(Serie.class, Long.parseLong(request.getParameter("idSerie")));
            session.setAttribute("serie",p.getId() );
        }        
        session.setAttribute("pagActual","Video.jsp");
    }

    @Override
    public String title(HttpSession session, String title) {
        String nombre = "";
        if (((AccesoBD)session.getAttribute("conexion")).buscarPorId(Serie.class, Long.parseLong(session.getAttribute("serie").toString()))!=null){
            Serie p = (Serie)((AccesoBD)session.getAttribute("conexion")).buscarPorId(Serie.class, Long.parseLong(session.getAttribute("serie").toString()));
            nombre = p.getTitulo();
        }
        StringBuilder html = new StringBuilder("<title>");
        html.append(title);
        html.append(" - ");
        html.append(nombre);
        html.append("</title>\n");
        return html.toString();
    }

    @Override
    public String contenido(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {
        StringBuilder html = new StringBuilder("<center>");
        html.append(presentacion(request, session,abd));
        html.append("</center>");
        return html.toString();
    }
     
    public String presentacion(HttpServletRequest request, HttpSession session, AccesoBD abd){
        Serie p = (Serie)abd.buscarPorId(Serie.class, Long.parseLong(session.getAttribute("serie").toString()));
        if (p != null){
            StringBuilder html= new StringBuilder("       <table border=\"0\" class=\"presentacion\">\n");
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
            html.append("                                           </td>\n");
            html.append("                                       </tr>\n");
            html.append("                                       <tr>\n");
            html.append("                                           <td class=\"desc\"><p>");
            html.append(p.getSinopsis());
            html.append("</p>\n");
            Collection c =abd.buscarPorFiltro(RelacionSerieDirector.class, "serie.id == "+p.getId());        
            List<RelacionSerieDirector> rsds = null;
            if (c!=null){
                rsds = new LinkedList<RelacionSerieDirector>(c);
            }
            if (rsds != null && rsds.size()>0){
                html.append("                                           <p>Directores: ");
                for (int i = 0; i < rsds.size(); i++) {
                    RelacionSerieDirector rpd = rsds.get(i);
                    html.append(rpd.getDirector().getNombre());
                    html.append(" ");
                    html.append(rpd.getDirector().getApellido());
                    if (i<rsds.size()-1){
                         html.append(","); 
                    }
                }
                 html.append("                                       </p>\n");
            }
            c=abd.buscarPorFiltro(RelacionSerieActor.class, "serie.id == "+p.getId());
            List<RelacionSerieActor> rsas = null;
            if (c!=null){
                rsas = new LinkedList<RelacionSerieActor>(c);
            }
            if (rsas !=null && rsas.size()>0){
                html.append("                                           <p>Actores: ");
                for (int i = 0; i < rsas.size(); i++) {
                    RelacionSerieActor rpa = rsas.get(i);
                    html.append(rpa.getActor().getNombre());
                    html.append(" ");
                    html.append(rpa.getActor().getApellido());
                    if (i<rsas.size()-1){
                        html.append(","); 
                    }
                }
                html.append("                                       </p>\n");
            }
            html.append("                                           </td>\n");
            html.append("                                       </tr>\n");
            html.append("                                   </table>\n");
            html.append("                               </td>\n");
            html.append("                           </tr>\n");
            html.append("                       </table>\n");
            html.append("                   </div>\n");
            html.append("                </td>\n");
            html.append("                <td class=\"fondoRedondo\"></td>\n");
            html.append("           </tr>\n");
            html.append("               <tr>\n");
            html.append("                   <td class=\"fondoRedondoBL\"></td>\n");
            html.append("                   <td class=\"fondoRedondo\"></td>\n");
            html.append("                   <td class=\"fondoRedondoBR\"></td>\n");
            html.append("               </tr>\n");
            html.append("           </table>\n");
            html.append("           <br><br><br><br>\n");
            html.append(generarListaTempCap(abd, p.getId(),session)); 
            return html.toString();
        }else 
            return "";
    }
    
    private String generarListaTempCap (AccesoBD abd, Long idSerie, HttpSession session){
        StringBuilder html = new StringBuilder("       <form method=\"post\" name=\"selectVideo\" action= \"Video.jsp\">\n");
        html.append("           <input type=\"hidden\" name=\"idVideo\">\n");
        html.append("       </form>\n");
        try{
            Usuario user = null;
            List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,"nameUsuario.equals(\""+session.getAttribute("user") +"\")"));                
            if(l.size()>0){
                user=l.get(0);
            } 
            List<Temporada> temp = new LinkedList<Temporada>(abd.getObjectsOrdered(Temporada.class, "baja==false && serie.id=="+idSerie, "numTem ascending"));
            if (temp != null &&temp.size()>0){
                html.append("<ul class='accordion'>");
                for (int i = 0; i < temp.size(); i++) {
                    html.append("   <li class='active'>");
                    Temporada t = temp.get(i);
                    html.append("<div class='title'><h5>");
                    html.append(t.getTitulo());
                    html.append("</h5></div>");
                    html.append("<div class='content'>");
                    List<Capitulo> cap = new LinkedList<Capitulo>(abd.getObjectsOrdered(Capitulo.class, "baja==false && temporada.id=="+t.getId(), "numCap ascending"));
                    if (cap!=null && cap.size()>0){
                        for (int j = 0; j < cap.size(); j++) {
                            Capitulo c = cap.get(j);
                            html.append("   <div class='lsitCap'><a id=\"cap");
                            html.append(c.getId());
                            html.append("\" href=\"#\" OnClick=\"linkAVideo('");
                            html.append(c.getId());
                            html.append("');\">");
                            html.append(c.getNumCap());
                            html.append(" - ");
                            html.append(c.getTitulo().toLowerCase());
                            if (estabaVista(abd,c,user)){
                                html.append("<IMG SRC=\"styles/images/visto.png\" WIDTH=25 HEIGHT=25 ALT=\"imagen\">");
                            }
                            html.append("   </a></div><br>");                                    
                        }
                        html.append("</div>"); 
                    }else{
                        html.append("   No Hay capitulos disponibles");
                    }
                    html.append("   </li>");                    
                }
                html.append("   </ul>");
            }else{
                html.append("   No Hay temporadas disponibles");
            }
        }catch(Exception e){
            html.append("ocurrio un error en la carga de temporadas y capitulos...<br>");
            e.printStackTrace();
        }
        return html.toString();
    }

    private boolean estabaVista(AccesoBD abd, Capitulo c, Usuario user) {
        try {
           String filtro = "usuario.id == "+user.getId()+" && pelicula.id == "+c.getId();
           Collection col = abd.buscarPorFiltro(Alquiler.class, filtro);
           if (col!=null&&col.size()>0){
               return true;
           }else{
               return false;
           }
        }catch (Exception e){
            return false;
        }
    }
}
