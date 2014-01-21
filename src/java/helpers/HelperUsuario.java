package helpers;

import entidades.Genero;
import entidades.Preferencia;
import entidades.RelacionPreferenciaGenero;
import entidades.Usuario;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperUsuario extends HelperGeneral{

    @Override
    public String importsCSS() {
        return "";
    }

    @Override
    public String importsJS() {
        return "";
    }

    @Override
    public void inicializacion(AccesoBD abd, HttpSession session, HttpServletRequest request) {}

    @Override
    public String title(HttpSession session, String title) {
        return "<title>"+title+" - Configuración de perfil</title>\n";
    }

    @Override
    public String contenido(AccesoBD abd, HttpSession session, HttpServletRequest request, String pag) {
        StringBuilder html = new StringBuilder("");
        Preferencia p = null;
        Usuario u =  (Usuario) new LinkedList(abd.buscarPorFiltro(Usuario.class, "nameUsuario.equals('"+session.getAttribute("user").toString()+"')")).get(0);
        LinkedList<Preferencia> lp = new LinkedList(abd.buscarPorFiltro(Preferencia.class, "usuario.id == "+u.getId()));
        if (lp==null){
            p = new Preferencia();
            p.setUsuario(u);
        } 
        else {
            p = lp.get(0);
        }
        String nomb = u.getNombre();
        String apel = u.getApellido();
        String dni  = u.getDni();
        String dir  = u.getDireccion();
        String tel  = u.getTelefono();
        String pass = u.getPassword();
        String email= u.getEmail();
        List<Genero> ser= abd.listar(new Genero());                            
        if (request.getParameter("actUser")!=null){
            try {
                nomb=new String(request.getParameter("nombre").getBytes("ISO-8859-1"), "UTF-8");
                apel=new String(request.getParameter("apellido").getBytes("ISO-8859-1"), "UTF-8");
                dni=new String(request.getParameter("dni").getBytes("ISO-8859-1"), "UTF-8");
                tel=new String(request.getParameter("telefono").getBytes("ISO-8859-1"), "UTF-8");
                dir=new String(request.getParameter("direccion").getBytes("ISO-8859-1"), "UTF-8");
                email=new String(request.getParameter("email").getBytes("ISO-8859-1"), "UTF-8");
                String passConf=new String(request.getParameter("passConf").getBytes("ISO-8859-1"), "UTF-8");
               // passConf= funcionesExtras.getMD5(passConf);
                if (pass.equals(passConf)){
                    u.setNombre(nomb);
                    u.setApellido(apel);
                    u.setDni(dni);
                    u.setDireccion(dir);
                    u.setTelefono(tel);
                    u.setEmail(email);
                    u.actuailizarNombreApellido();
                    if(request.getParameter("peliculas")!=null&&request.getParameter("peliculas").equals("ON")){
                        p.setPeliculas(true);
                    }
                    else{
                        p.setPeliculas(false);
                    }
                    if(request.getParameter("series")!=null&&request.getParameter("series").equals("ON")){
                        p.setSeries(true);
                    }
                    else{
                        p.setSeries(false);
                    }
                    if(request.getParameter("cualquiera")!=null&&request.getParameter("cualquiera").equals("ON")){
                        p.setCualquiera(true);
                    }
                    else{
                        p.setCualquiera(false);
                    }
                    if(request.getParameter("mejorVotadas")!=null&&request.getParameter("mejorVotadas").equals("ON")){
                        p.setBienPuntuadas(true);
                    }
                    else{
                        p.setBienPuntuadas(false);
                    }
                    if(request.getParameter("clasicos")!=null&&request.getParameter("clasicos").equals("ON")){
                        p.setClasicos(true);
                    }
                    else{
                        p.setClasicos(false);
                    }
                    if(request.getParameter("estrenos")!=null&&request.getParameter("estrenos").equals("ON")){
                        p.setEstrenos(true);
                    }
                    else{
                        p.setEstrenos(false);
                    }
                    abd.hacerPersistente(u);
                    abd.hacerPersistente(p);
                    for (int i = 0; i < ser.size(); i++) {
                        Genero g = ser.get(i);
                        RelacionPreferenciaGenero rpg = esUnaPreferencia(abd, p, g);
                        String tilde = "";
                        if (request.getParameter(g.getId().toString())!=null)
                            tilde = request.getParameter(g.getId().toString()).toString();
                        if(rpg!=null&&!tilde.equals("ON")){
                            abd.eliminar(rpg);
                        }else{
                            if(rpg==null&&tilde.equals("ON")){
                                rpg = new RelacionPreferenciaGenero(p, g);
                                abd.hacerPersistente(rpg);
                            }
                        }
                        
                    }
                    
                    html.append("           <script LANGUAGE=\"javascript\">");
                    html.append("               window.onload = function(){");
                    html.append("                   alert('tu perfil fue actualizado con exito.');");
                    html.append("                   window.location = 'ListadoPeliculas.jsp';");
                    html.append("               };");
                    html.append("           </script> ");
                }else{
                    html.append("           <script LANGUAGE=\"javascript\">");
                    html.append("               window.onload = function(){");
                    html.append("                   alert('La confirmación de la contraseña no es valida');");
                    html.append("               };");
                    html.append("           </script> ");
                }
            } catch (UnsupportedEncodingException ex) {
                
            }
        }
        html.append("<form name='usuario' action='");
        html.append(pag);
        html.append("' method='POST'>\n");
        html.append("     <center>");
        html.append("          <table border='0'>");
        html.append("               <thead>");
        html.append("                   <tr>");
        html.append("                           <th colspan='2' ><h2>Configura tu perfil</h2></th>");
        html.append("         <td></td>");
        html.append("        </tr>");
        html.append("     </thead>");
        html.append("      <tbody>");
        html.append("           <tr>");
        html.append("                <td>Nombre</td>");
        html.append("                 <td><input type='text' name='nombre' value='");
        html.append(nomb);
        html.append("' /></td>");
        html.append("                  <td></td>");
        html.append("               </tr>");
        html.append("                <tr>");
        html.append("    <td>Apellido</td>");
        html.append("     <td><input type='text' name='apellido' value='");
        html.append(apel);
        html.append("' /></td>");
        html.append("      <td></td>");
        html.append("   </tr>");
        html.append("    <tr>");
        html.append("         <td>Dni</td>");
        html.append("          <td><input type='text' name='dni' value='");
        html.append(dni);
        html.append("' /></td>");
        html.append("           <td></td>");
        html.append("         <tr>");
        html.append("              <td>Dirección</td>");
        html.append("                    <td><input type='text' name='direccion' value='");
        html.append(dir);
        html.append("' /></td>");
        html.append("                     <td></td>");
        html.append("                  </tr>");
        html.append("              <tr>");
        html.append("              <td>Telefono</td>");
        html.append("                    <td><input type='text' name='telefono' value='");
        html.append(tel);
        html.append("' /></td>");
        html.append("                     <td></td>");
        html.append("                  </tr>");
        html.append("                   <tr>");
        html.append("    <td>Email</td>");
        html.append("     <td><input type='text' name='email' value='");
        html.append(email);
        html.append("' /></td>");
        html.append("      <td></td>");
        html.append("   </tr>");
        html.append("    <tr>");
        html.append("         <td>Contraseña</td>");
        html.append("          <td><input type='password' name='passConf' value='' /></td>");
        html.append("           <td></td>");
        html.append("        </tr>");
        html.append("         <tr>");
        html.append("              <td colspan='2'><b>ingrese su contraseña para confirmar la actualización</b></td>");
        html.append("               <td></td>");
        html.append("            </tr>");
        html.append("    <tr>");
        html.append("      <td colspan=3>");
        html.append("  <hr> <div style=\"float: left;\">");
        html.append("                   <div class='tituloDiv'>¿Cuales son tus preferencias? </div>");
        html.append("                   <div style=\"float: left; padding-left: 15px;\">");
        html.append("                   <input type=\"checkbox\" name=\"peliculas\" value=\"ON\"");
        if (p.isPeliculas()){
            html.append("checked=\"checked\" ");
        }
        html.append(" />Peliculas");
        html.append("</div>");
        html.append("<div style=\"float: left; padding-left: 15px;\">");
        html.append("    <input type=\"checkbox\" name=\"series\" value=\"ON\"");
        if (p.isSeries()){
            html.append("checked=\"checked\" ");
        }      
        html.append("   /> Series </div>");
        html.append("</div>");
        html.append("<div  style=\"float: left; padding-left: 25px;\">");
        html.append("<div class='tituloDiv'>¿Algún criterio en particular?</div>");
        html.append("<div> <input type=\"checkbox\" name=\"cualquiera\" value=\"ON\"");
        if (p.isCualquiera()){
           html.append("checked=\"checked\" ");
        } 
        html.append(" /> Cualquiera </div>");
        html.append("<div> <input type=\"checkbox\" name=\"mejorVotadas\" value=\"ON\"");
        if (p.isBienPuntuadas()){
           html.append("checked=\"checked\" ");
        } 
        html.append("/> Mejor Votadas </div>");
        html.append("<div> <input type=\"checkbox\" name=\"clasicos\" value=\"ON\" ");
        if (p.isClasicos()){
           html.append("checked=\"checked\" ");
        }
        html.append("/>   Clasicos");
        html.append("</div> <div><input type=\"checkbox\" name=\"estrenos\" value=\"ON\" ");
        if (p.isEstrenos()){
            html.append("checked=\"checked\" ");
        } 
        html.append("/>Estrenos  </div>");
        html.append("</div>  ");
        html.append("      </td>");
        html.append("   </tr>");
        html.append("         <tr>");
        html.append("               <td colspan='3'><hr></td>");
        html.append("            </tr>");
        html.append("         <tr>");
        html.append("               <td colspan='3'>");
        html.append("                       <div class='tituloDiv'>Elige los generos que  mas te gustan. </div>");
        html.append("            </tr>");
        html.append("         <tr>");
            int k=1;
            for (int i = 0; i < ser.size(); i++) {
                Genero g = ser.get(i);
                
                html.append("                       <td>");
                html.append("                           <input type=\"checkbox\" name=\"");
                html.append(g.getId());
                html.append("\" value=\"ON\"");
                if (esUnaPreferencia(abd,p,g)!=null){
                   html.append("checked=\"checked\" ");
                }
                html.append(" />");
                html.append(g.getNombre().toLowerCase());
                html.append("</td>");
                if(k>2){
                    html.append("</tr><tr>");
                    k=0;
                }
                k++;
            }
            int falta=ser.size()%3;
            for (int i = 0; i < falta; i++) {
                html.append("<td></td>");
            }
            html.append("            </tr>");
        html.append("         <tr>");
        html.append("             <td><button type='button' name='actUser' value='volver atras' class='boton' Onclick='window.history.back();'>volver atras</button></td>");
        html.append("              <td colspan='2'><input type='submit' name='actUser' value='actualizar los datos' class='boton'/></td>");
        html.append("            </tr>");
        html.append("         </tbody>");
        html.append("      </table>");
        html.append("   </center>");
        html.append("</form>");
        return html.toString();
    }
    
    public String cambiarPass(Usuario u){
        return "";
    }

    private RelacionPreferenciaGenero esUnaPreferencia(AccesoBD abd,Preferencia p, Genero g) {
        //try{
            List<RelacionPreferenciaGenero> c = new LinkedList<RelacionPreferenciaGenero>(abd.buscarPorFiltro(RelacionPreferenciaGenero.class, "preferencia.id=="+p.getId()+"&&genero.id=="+g.getId()));
            if (c!=null && c.size()>0)
                return c.get(0);
            else
                return null;
        /*}catch(Exception e){
            return null;
        }*/
    }
}
