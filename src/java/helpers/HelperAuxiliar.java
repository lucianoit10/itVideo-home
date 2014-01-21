/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import entidades.Alquiler;
import entidades.Configuracion;
import entidades.Estadistica;
import entidades.Pelicula;
import entidades.Usuario;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

/**
 *
 * @author Luciano
 */
public class HelperAuxiliar extends HelperGeneral{

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
        String pathSD = "http://";
        String pathHD = "http://";
        String pathFHD = "http://";
        String streamer = "http://";
        String html =   "";
        try{
            abd.iniciarTransaccion();
            Boolean esGratis=true;
            Double precio=0.0;
            Pelicula p = (Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
            String nom = p.getRepVideo();
            try {
                session.setAttribute("ver", true);
                String calidad = session.getAttribute("calidad").toString();
                if (calidad.equals("sd")){
                    esGratis=p.isEsGratisSD();
                    if (!esGratis){
                        precio=p.getPrecioSD();
                    }
                }
                else{
                    if (calidad.equals("hd")){
                        esGratis=p.isEsGratisHD();
                        if (!esGratis){
                            precio=p.getPrecioHD();
                        }
                    }
                    else{
                        if (calidad.equals("fhd")){
                            esGratis=p.isEsGratisFHD();
                            if (!esGratis){
                                precio=p.getPrecioFHD();
                            }
                        }
                        else{
                            System.err.println("ERROOOOOOOOR estoy en auxiliar.java 85");
                        }
                    }
                }
                /*ip del cliente*/
                String ip =request.getRemoteAddr();
                /*busca el usuario que se encuentra logeado*/
                String filtro   = "nameUsuario.equals(\""+session.getAttribute("user") +"\")";
                Usuario user = null;
                List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,filtro));                
                if(l.size()>0){
                    user=l.get(0);
                }
                /*obtiene el alquiler previo*/
                Alquiler a = estabaAlquilada(abd,session,p,calidad);
                Alquiler alqUsado = null;
                /*si no estaba alquilado, realiza el alquiler*/
                if (a==null){
                    if(esGratis){
                        a = new Alquiler(ip,0.0,new Date(),p,user,calidad);
                        abd.hacerPersistente(a);
                    }else{
                        a = new Alquiler(ip,precio,new Date(),p,user,calidad);
                        abd.hacerPersistente(a);
                    }
                    alqUsado = a;
                }else{
                    alqUsado = new Alquiler(ip,0.0,new Date(),p,user,calidad);
                    alqUsado.setEstaAbierta(false);
                    abd.hacerPersistente(alqUsado);                    
                }
                /*una vez registrado el alquiler, llama al creador de el worker para esa pelicula*/
                Long id = alqUsado.getId();
                //int tiempo = ((Configuracion)(abd.listar(new Configuracion())).get(0)).getHsAlquiler();
                String comando = "sudo /usr/bin/python /scripts/crea_srv.py "+ip+" "+id+" "+nom+" "+calidad.toUpperCase();//+" "+tiempo;
                Process proc = Runtime.getRuntime().exec(comando);
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
                String res = ret.toString().replace("\n", "");
                String[] aux = res.split("<");
                if(aux.length>1){
                    res=aux[0];
                }
                if(p.getExtSD()!=null){
                    pathSD+=res+"/"+nom+".SD."+p.getExtSD();
                }
                if (p.getExtHD()!=null){
                    pathHD+=res+"/"+nom+".HD."+p.getExtHD();
                }
                if (p.getExtFHD()!=null){
                    pathFHD+=res+"/"+nom+".FHD."+p.getExtFHD();
                }
     //           System.out.println ("auxiliar 9");
                Configuracion c = (Configuracion)abd.buscarPorId(Configuracion.class, Long.parseLong("1"));
                streamer+=c.getIpServidorVideo();
                /***********************************************************/
                Estadistica e = null;
                filtro = "pelicula.id=="+p.getId();
                List<Estadistica>ce=new LinkedList<Estadistica>(abd.buscarPorFiltro(Estadistica.class,filtro));                
                if(ce.size()>0){
                    e=ce.get(0);
                }else{
                    e= new Estadistica(p);
                }
                e.mirar();
                abd.hacerPersistente(e);
                /***********************************************************/
            } catch (Exception ex) {         
               /*html =   "           <script LANGUAGE=\"javascript\">"+                
                                "               window.onload = function(){"+
                                "                   alert('A Ocurrido un Error');"+
                                "                   window.location = 'Video.jsp';"+
                                "               };"+
                                "           </script> ";*/
            }
            //if (!pathSD.equals("http://")||!pathHD.equals("http://")||!pathFHD.equals("http://")){
                session.setAttribute("pSD", pathSD);
                session.setAttribute("pHD", pathHD);
                session.setAttribute("pFHD", pathFHD);
                        html =  "           <script type=\"text/javascript\">\n"+   
                                "                   sessionStorage.setItem(\"pathSD\",\""+pathSD+"\");\n"+
                                "                   sessionStorage.setItem(\"pathHD\",\""+pathHD+"\");\n"+
                                "                   sessionStorage.setItem(\"streamer\",\""+streamer+"\");\n"+
                                "                   sessionStorage.setItem(\"subs\",\""+p.getSubtitulos()+"\");\n";
                       html+=   "                   window.location.replace('espera.jsp');\n";
                        html+=  "           </script> ";
          /*  }else{
                 html =  "           <script LANGUAGE=\"javascript\">"+                
                                "               window.reload = function(){"+
                                "                   window.location.reload();";
                        html+=  "               };"+
                                "           </script> ";
            }*/
        abd.concretarTransaccion();    
        }
        catch (Exception e) {
            System.out.println("Error en axiliar.java 198");
            e.printStackTrace();    
            abd.rollbackTransaccion();
        }
        return html;
    }
    
    
    
}
