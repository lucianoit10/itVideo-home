package helpers;

import entidades.Usuario;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperIndex extends HelperGeneral{

    @Override
    public String importsCSS() {
        return"";
    }

    @Override
    public String importsJS() {
        return"       <script type=\"text/javascript\" src=\"js/overlayLogin.js\"></script>\n";
    }

    @Override
    public void inicializacion(AccesoBD abd, HttpSession session, HttpServletRequest request) {}  
    
    @Override
    public String title(HttpSession session, String title) {
        StringBuilder html = new StringBuilder("<title>");
        html.append(title);
        html.append("</title>\n");
        return html.toString();
    }

    @Override
    public String banner(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {
        StringBuilder html = new StringBuilder("");
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
         //   password=funcionesExtras.getMD5(password);
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
            session.setAttribute("elegirCalidad", false);    
            session.setAttribute("calidad", null);    
            session.setAttribute("ver", false);      
       //     html += login(loginError, pag);
            html.append("          <form name=\"log\" action=\"");
            html.append(pag);
            html.append("\" method=\"post\">\n");
            html.append("               <table width=\"400px\">\n");
            html.append("                   <tr><td>Usuario:</td><td>Contraseña: </td><td></td></tr>\n");
            html.append("                   <tr><td><input name=\"usuarioLogin\" type=\"text\" size=\"20\"></td><td><input name=\"passLogin\" type=\"password\" size=\"20\"></td>  <td><button class=\"boton\" onclick=\"login();\"> Acceder </button></td> </tr>\n");
            html.append("                   <tr><td></td><td><input name=\"ingresar\" type=\"hidden\"></td><td></td></tr>\n");
            if(loginError){
                html.append("               <tr>\n");
                html.append("                   <td colspan=\"3\">\n");
                html.append("                       LA COMBINACION DE USUARIO NO ES VALIDA\n");
                html.append("                   </td>\n");
                html.append("               </tr>\n");   
            }
            html.append("               </table>\n");
            html.append("           </form>      \n");
        }
        else{            
            html.append("           <script LANGUAGE=\"javascript\">");
            html.append("               window.onload = function(){");
            html.append("                   window.location = 'ListadoPeliculas.jsp';");
            html.append("               };");
            html.append("           </script> ");
        }
        return html.toString();
    }
    
    @Override
    public String contenido(AccesoBD abd, HttpSession session, HttpServletRequest request, String pag) {      
        return"<br><br><center><div class=\"fondoLogin\" style=\"box-shadow: 10px 10px 5px #888; text-align: center;background-image: url(styles/images/foto232.jpg);background-repeat: no-repeat; \">Disfruta de las mejores peliculas...<br> Con la mejor calidad y sin interrupciones... </div><center><br><br>\n";
    }
    
    public String login( Boolean error, String pag){
        StringBuilder html= new StringBuilder("<div  id=\"facebox\">\n");
        html.append("   <div style=\"text-align:left;\">\n");
        html.append("       <h2>Ingresar</h2>  \n");
        html.append("       <p>\n");
        html.append("           <form name=\"log\" action=\"");
        html.append(pag);
        html.append("\" method=\"post\">\n");
        html.append("               <table>\n");
        html.append("                   <tr><td>Usuario:</td><td> <input name=\"usuarioLogin\" type=\"text\" size=\"20\"></td></tr>\n");
        html.append("                   <tr><td>Contraseña: </td><td><input name=\"passLogin\" type=\"password\" size=\"20\"></td></tr>\n");
        html.append("                   <tr><td></td><td><input name=\"ingresar\" type=\"hidden\"></td></tr>\n");
        if(error){
            html.append("               <tr>\n");
            html.append("                   <td colspan=\"2\">\n");
            html.append("                       LA COMBINACION DE USUARIO NO ES VALIDA\n");
            html.append("                   </td>\n");
            html.append("               </tr>\n");   
        }
        html.append("			<tr><td colspan=\"2\" align=\"right\"><button class=\"close\" onclick=\"login();\"> Acceder </button></td></tr>\n");
        html.append("               </table>\n");
        html.append("           </form>      \n");
        html.append("       </p>\n");
        html.append("  </div>\n");
        html.append("</div>\n");
        html.append("<script  type=\"text/javascript\">\n");
        html.append("$(document).ready(function() {\n");
        html.append("  $(\"#open_now\").click(function() {\n");
        html.append("      $(\"#facebox\").overlay().load();\n");
        html.append("  });\n");
        html.append("    // select the overlay element - and \"make it an overlay\"\n");
        html.append("  $(\"#facebox\").overlay({\n");
        html.append("    // custom top position\n");
        html.append("    top: 260,\n");
        html.append("    // some mask tweaks suitable for facebox-looking dialogs\n");
        html.append("    mask: {\n");
        html.append("    // you might also consider a \"transparent\" color for the mask\n");
        html.append("    color: '#000',\n");
        html.append("    // load mask a little faster\n");
        html.append("    loadSpeed: 200,\n");
        html.append("    // very transparent\n");
        html.append("    opacity: 0.5\n");
        html.append("    },\n");
        html.append("    // disable this for modal dialog-type of overlays\n");
        html.append("    closeOnClick: false,\n");
        html.append("	closeOnEsc:	false,\n");
        html.append("    // load it immediately after the construction\n");
        html.append("    load: true\n");
        html.append("    });\n");
        html.append("    });\n");
        html.append("</script>\n");
        html.append("<div style=\"position: absolute; top: 0px; left: 0px; width: 1600px; height: 727px; display: none; opacity: 0.5; z-index: 9998; background-color: rgb(255, 255, 255);\" id=\"exposeMask\"></div>\n");
        return html.toString();
    }

}
