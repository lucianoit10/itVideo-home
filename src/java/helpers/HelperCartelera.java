package helpers;

import entidades.Configuracion;
import entidades.Pelicula;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperCartelera extends HelperGeneral{

    @Override
    public String importsCSS() {
        return "       <link rel=\"stylesheet\" type=\"text/css\" href=\"styles/style_Slider.css\" />\n";
        
    }

    @Override
    public String title(HttpSession session, String title) {
        StringBuilder html =   new StringBuilder("<title>");
        html.append(title);
        html.append("</title>\n");
        return html.toString();
    }

    @Override
    public String importsJS() {
        StringBuilder html =   new StringBuilder("       <script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/scripts.js\"></script>\n");
        return html.toString();
    }

    @Override
    public void inicializacion(AccesoBD abd,HttpSession session, HttpServletRequest request) {
        session.setAttribute("ver", null);
        session.setAttribute("path",null);
        session.setAttribute("subs",null);
        session.setAttribute("elegirCalidad", null);
        session.setAttribute("calidad", null);
        session.setAttribute("movie",null);
        session.setAttribute("pagActual","cartelera.jsp");
    }

    
    public String slider (AccesoBD abd){
        StringBuilder html = new StringBuilder("");
        try {
            int cota =((Configuracion)(abd.listar(new Configuracion()).get(0))).getCantPeliCartelera();
            List<Pelicula> pelis = new LinkedList<Pelicula>();
            pelis = abd.getObjectsOrdered(Pelicula.class,"cartelera!=null && baja==false","id descending");
            /*obtener la lista de peliculas ordenadas por ultimas subidas*/
            if (pelis.size()<cota){
                cota = pelis.size();
            }
            
            html.append("   <br><div id=\"header\"  style=\"text-align: center;\">\n");
            html.append("       <div class=\"wrap\">\n");
            html.append("           <div id=\"slide-holder\">\n");
            html.append("               <div id=\"slide-runner\">\n");
            html.append("                   <form method=\"post\" name=\"selectVideo\" action= \"Video.jsp\">\n");
            html.append("                       <input type=\"hidden\" name=\"idVideo\">\n");
            for (int i=0;i<cota;i++){
                html.append("                   <a href=\"#\" OnClick=\"linkAVideo('");
                html.append(pelis.get(i).getId());
                html.append("');\"><img id=\"slide-img-");
                html.append(i+1);
                html.append("\" src=\"");
                html.append(pelis.get(i).getCartelera().getPath());
                html.append("\" class=\"slide\" alt=\"\" width=\"967\" height=\"280\"/></a>\n");
            }
            html.append("                   </form>\n");
            html.append("                   <div id=\"slide-controls\">\n");
            html.append("                       <p id=\"slide-client\" class=\"text\"><strong><!--pelicula:--> </strong><span></span></p>\n");
            html.append("                       <p id=\"slide-desc\" class=\"text\"></p>\n");
            html.append("                       <p id=\"slide-nav\"></p>\n");
            html.append("                   </div>\n");
            html.append("               </div>\n");
            html.append("               <!--content featured gallery here -->\n");
            html.append("           </div>\n");
            html.append("           <script type=\"text/javascript\">\n");
            html.append("               if(!window.slider) \n");
            html.append("                   var slider={};\n");
            html.append("                   slider.data=[\n");
             for (int i=0;i<cota;i++){
                    Pelicula p = pelis.get(i);
                    html.append("{\"id\":\"slide-img-");
                    html.append(i+1);
                    html.append("\",\"client\":\"");
                    html.append(p.getTitulo());
                    html.append("\",\"desc\":\"\"}");
                    if(i<cota-1){
                        html.append(",");
                    }
                    html.append("\n");
                }   
             html.append("                   ];\n");
             html.append("           </script>\n");
             html.append("       </div>\n");
             html.append("       <br>\n");
             html.append("   </div>\n");
        } catch (Exception ex) {
        }
        return html.toString();
    }

    @Override
    public String contenido(AccesoBD abd, HttpSession session, HttpServletRequest request,String pag) {
        return "";
    }

}
