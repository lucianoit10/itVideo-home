package helpers;

import entidades.Capitulo;
import entidades.Serie;
import entidades.Temporada;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

    public class HelperListadoSeries_ajax extends HelperGeneral{
    
    @Override
    public String importsCSS() {
        StringBuilder html = new StringBuilder("<script type=\"text/javascript\" src=\"js/FuncionesComunes.js\"></script>\n");
        html.append("<style>\n");
        html.append("    a {               \n");
        html.append("        text-decoration: none;\n");
        html.append("        text-transform: capitalize;\n");
        html.append("    }\n");
        html.append("    li{\n");
        html.append("        list-style: none;\n");
        html.append("    }\n");
        html.append("    .busq{\n");
        html.append("        margin-left: 40px;\n");
        html.append("    }\n");
        html.append("</style>\n");
        return html.toString();
    }

    @Override
    public String importsJS() {       
        StringBuilder html = new StringBuilder ("       <script type=\"text/javascript\" src=\"js/FuncionesComunes.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/scroll/jquery.jscrollpane.min.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/todo.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/scroll/quicksilver.js\"></script>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/scroll/jquery.livesearch.js\"></script>\n");
        html.append("       <script type=\"text/javascript\">\n");
        html.append("       function isTouchDevice(){\n");
        html.append("           try{\n");
        html.append("               document.createEvent(\"TouchEvent\");\n");
        html.append("               return true;\n");
        html.append("           }catch(e){\n");
        html.append("               return false;\n");
        html.append("           }\n");
        html.append("       }\n");
        html.append("       function touchScroll(id){\n");
        html.append("           if(isTouchDevice()){ //if touch events exist...\n");
        html.append("               var el=document.getElementById(id);\n");
        html.append("               var scrollStartPos=0;\n");
        html.append("               document.getElementById(id).addEventListener(\"touchstart\", function(event) {\n");
        html.append("                   scrollStartPos=this.scrollTop+event.touches[0].pageY;\n");
        html.append("                   event.preventDefault();\n");
        html.append("               },false);\n");
        html.append("               document.getElementById(id).addEventListener(\"touchmove\", function(event) {\n");
        html.append("                   this.scrollTop=scrollStartPos-event.touches[0].pageY;\n");
        html.append("                   event.preventDefault();\n");
        html.append("               },false);\n");
        html.append("           }\n");
        html.append("       }\n");
        html.append("           $(document).ready(function() {\n");
        html.append("               $('#q').liveUpdate('lista').focus();\n");
        html.append("               $('.scroll-pane').jScrollPane();\n");
        html.append("           });\n");
        html.append("       </script>\n"); 
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
        session.setAttribute("pagActual","ListadoSeries.jsp");
    }

    @Override
    public String title(HttpSession session, String title) {
        StringBuilder html = new StringBuilder("<title>");
        html.append(title);
        html.append("</tilte>\n");
        return html.toString();
    }

    @Override
    public String contenido(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {
        StringBuilder html = new StringBuilder("");
        String userAgent = request.getHeader("user-agent");
        html.append("<center>\n");
        html.append("       <form method=\"post\" name=\"selectVideo\" action= \"Video.jsp\">\n");
        html.append("           <input type=\"hidden\" name=\"idVideo\">\n");
        html.append("       </form>\n");
        html.append("       <form method=\"post\" name=\"selectSerie\" action= \"Serie.jsp\">\n");
        html.append("           <input type=\"hidden\" name=\"idSerie\">\n");
        html.append("       </form>\n");
        html.append("    <table border=\"0\" style=\"text-align: center;\">\n");
        html.append("        <thead>\n");
        html.append("            <tr>\n");
        html.append("                <th colspan=\"3\">Buscador Series</th>\n");
        html.append("            </tr>\n");
        html.append("        </thead>\n");
        html.append("        <tbody>\n");
        html.append("            <tr>\n");
        html.append("                <td>1 Seleccione La Serie</td>\n");
        html.append("                <td>2 Seleccione La Temporada</td>\n");
        html.append("                <td>3 Seleccione El Cap&iacute;tulo</td>\n");
        html.append( "            </tr>\n");
        html.append("            <tr>\n");
        html.append("                <td>\n");
        html.append("                    <form method=\"get\">\n");
        html.append("                        <div class=\"busq\">\n");
        html.append("                           <input type=\"text\" value=\"\" name=\"q\" id=\"q\" />\n");
        html.append("                        </div> \n");
        html.append("                    </form>\n");
        html.append("                </td>\n");
        html.append("                <td></td>\n");
        html.append("                <td></td>\n");
        html.append("            </tr>\n");
        html.append("            <tr>\n");
        html.append("                <td>\n");
        html.append("                    <div id=\"lista_series\" class=\"scroll-pane\" style='background-color:#aaa;width: 250px;");
               if ((userAgent != null)&&(userAgent.indexOf("Android") <= -1)) {
                   html.append("height: 350px;overflow:auto;");
               }else{
                    html.append("height: auto;overflow:auto;");
               }
               html.append("'>\n                        <ul  id=\"lista\">\n");

        List <Serie> gen = abd.getAllOrdered(Serie.class, "titulo.toLowerCase() ascending");
        for(int i=0;i<gen.size();i++){
            html.append("                       <li>\n");
            html.append("                            <a id=\"serie");
            html.append(gen.get(i).getId());
            html.append("\" href=\"#\" OnClick=\"cargar_temp('lista_temp','cargar_temporada.jsp?idserie=");
            html.append(gen.get(i).getId());
            html.append("');\">");
            html.append(gen.get(i).getTitulo().toLowerCase());
            html.append("                            </a>");
            html.append("                        </li>\n");
        }
        html.append("                         </ul>\n");
        html.append("                    </div>\n");
        html.append("                </td>\n");
        html.append("                <td><div id=\"lista_temp\" class=\"scroll-pane\" style='background-color:#aaa;width: 250px;");
        if ((userAgent != null)&&(userAgent.indexOf("Android") <= -1)) {
           html.append("height: 350px;overflow:auto;");
        }else{
        //   html+= "height: auto;";
        }
        html.append("'>\n&nbsp;</div></td>\n                <td><div id=\"lista_cap\" class=\"scroll-pane\" style='background-color:#aaa;width: 250px;");
        if ((userAgent != null)&&(userAgent.indexOf("Android") <= -1)) {
           html.append("height: 350px;overflow:auto;");
        }else{
        //     html+= "height: auto;overflow:auto;";
        }
        html.append("'>\n&nbsp;</div></td>\n");
        html.append("            </tr>\n");
        html.append("        </tbody>\n");
        html.append("    </table>\n");
        html.append("</center>\n");
        return html.toString();
    }
    
    public String cargarTemporada (AccesoBD abd,HttpServletRequest request){
        StringBuilder html = new StringBuilder("");
        try{
            String idserie= request.getParameter("idserie");
            html.append("            <ul>");
            Serie serie = (Serie)abd.buscarPorId(Serie.class, Long.parseLong(idserie));
            List <Temporada> tem = abd.getObjectsOrdered(Temporada.class,"serie.id=="+serie.getId()+" && baja==false", "numTem ascending");
            html.append("                <li><a id=\"todaLaSerie\" href=\"#\"  OnClick=\"linkASerie('");
            html.append(serie.getId());
            html.append("');\">Ver Todo Sobre La Serie</a></li>");
            for(int i=0;i<tem.size();i++){
                html.append("                <li>");
                html.append(" <a id=\"temp");
                html.append(tem.get(i).getId());
                html.append("\" href=\"#\" OnClick=\"cargar_cap('lista_cap','cargar_capitulo.jsp?idtemp=");
                html.append(tem.get(i).getId());
                html.append("');\">");
                html.append(tem.get(i).getTitulo().toLowerCase());
                html.append(" </a>");
                html.append("</li>");
            }
            html.append("            </ul>");
            html.append("<script type=\"text/javascript\">window.onload= document.getElementById('todaLaSerie').focus();</script>\n");
        }catch(Exception e){
            html = new StringBuilder(" Error al cargar las temporadas...<br>intentelo de nuevo...");
        }
        return html.toString();
    }
     
    public String cargarCapitulos (AccesoBD abd,HttpServletRequest request){
        StringBuilder html = new StringBuilder("");
        try{
            String idtemp= request.getParameter("idtemp");
            html.append("            <ul>");
            Temporada temp = (Temporada)abd.buscarPorId(Temporada.class, Long.parseLong(idtemp));
            List <Capitulo> cap = abd.getObjectsOrdered(Capitulo.class,"temporada.id=="+temp.getId()+" && baja==false", "numCap ascending");
            String id = "";
            for(int i=0;i<cap.size();i++){
                if (i==0){id="cap"+cap.get(i).getId();}
                html.append("                <li>");
                html.append("   <a id=\"cap");
                html.append(cap.get(i).getId());
                html.append("\" href=\"#\" OnClick=\"linkAVideo('");
                html.append(cap.get(i).getId());
                html.append("');\"  class=\"imgTool\" rel=\"tooltip");
                html.append(cap.get(i).getId());
                html.append("\" >");
                html.append((cap.get(i).getNumCap()));
                html.append(" - ");
                html.append((cap.get(i).getTitulo().toLowerCase()));
                html.append("   </a>");
                html.append("</li>");
                //codTooltip(cap.get(i), abd)+codPopups(cap.get(i), abd)+
            }
            html.append("            </ul>");
            if (!id.equals("")){
                html.append("<script type=\"text/javascript\">window.onload= document.getElementById('");
                html.append(id);
                html.append("').focus();</script>\n");
            }else{
                html.append("<script type=\"text/javascript\">window.onload= document.getElementById('todaLaSerie').focus();</script>\n");
            }

        }catch(Exception e){
            html.append(" Error al cargar los capitulos...<br>intentelo de nuevo...");
        }
        return html.toString();
    }
    
}
