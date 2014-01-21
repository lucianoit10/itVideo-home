package helpers;

import entidades.Publicidad;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;


public class HelperEspera extends HelperGeneral{

    @Override
    public String importsCSS() {
        return "";
    }

    @Override
    public String importsJS() {
        return "";
    }

    @Override
    public void inicializacion(AccesoBD abd,HttpSession session,HttpServletRequest request) {
        
    }

    @Override
    public String title( HttpSession session, String title) {
        return "";
    }

    @Override
    public String contenido(AccesoBD abd,HttpSession session, HttpServletRequest request, String pag) {
        StringBuilder html=new StringBuilder("<center><h1>Registrando en el Sistema</h1><div ><IMG SRC='styles/images/animated-loading-bar.gif' BORDER=0 ALT=\"cargando\"></div>");
        try{
            List<Publicidad> publicidad = abd.listar(new Publicidad());
            Publicidad p = publicidad.get(new Random().nextInt(publicidad.size()));
            html.append("<div ><a href=\"#\" OnClick=\"window.open('");
            html.append(p.getLinkPublicidad());
            html.append("', 'Publicidad', 'width=800,height=600');\" ><IMG SRC='");
            html.append(p.getPublicidad().getPath());
            html.append("' title=\"");
            html.append(p.getTitulo());
            html.append("\" ALT=\"");
            html.append(p.getTitulo());
            html.append("\"></a></div>");
            html.append("</center>");
        }catch (Exception e){
            html=new StringBuilder("<center><h1>Registrando en el Sistema</h1><div ><IMG SRC='styles/images/animated-loading-bar.gif' BORDER=0 ALT=\"cargando\"></div>");
        }
        return html.toString();
    }
    
    
    
}
