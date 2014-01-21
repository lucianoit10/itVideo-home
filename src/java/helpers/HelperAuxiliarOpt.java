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

public class HelperAuxiliarOpt extends HelperGeneral{

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
        StringBuilder pathSD = new StringBuilder ("http://");
        StringBuilder pathHD = new StringBuilder ("http://");
        StringBuilder pathFHD =  new StringBuilder ("http://");
        StringBuilder streamer =  new StringBuilder ("http://");
        StringBuilder html =  new StringBuilder ("");
        try{
            abd.iniciarTransaccion();
            Boolean esGratis=true;
            Double precio=0.0;
            Pelicula p = (Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString()));
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
                /*busca el usuario que se encuentra logeado*/
                StringBuilder filtro   = new StringBuilder ("nameUsuario.equals(\"");
                filtro.append(session.getAttribute("user"));
                filtro.append("\")");
                Usuario user = null;
                List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,filtro.toString()));                
                if(l.size()>0){
                    user=l.get(0);
                }
                /*obtiene el alquiler previo*/
                Alquiler a = estabaAlquilada(abd,session,p,calidad);
                Alquiler alqUsado = null;
                /*si no estaba alquilado, realiza el alquiler*/
                if (a==null){
                    if(esGratis){
                        a = new Alquiler(request.getRemoteAddr(),0.0,new Date(),p,user,calidad);
                        abd.hacerPersistente(a);
                    }else{
                        a = new Alquiler(request.getRemoteAddr(),precio,new Date(),p,user,calidad);
                        //aca envia la info para el alquiler en la factura
                        abd.hacerPersistente(a);
                    }
                    alqUsado = a;
                }else{
                    alqUsado = new Alquiler(request.getRemoteAddr(),0.0,new Date(),p,user,calidad);
                    alqUsado.setEstaAbierta(false);
                    abd.hacerPersistente(alqUsado);                    
                }
                /*una vez registrado el alquiler, llama al creador de el worker para esa pelicula*/
                Long id = alqUsado.getId();
                StringBuilder comando = new StringBuilder("sudo /usr/bin/python /scripts/crea_srv.py ");
                comando.append(request.getRemoteAddr());
                comando.append(" ");
                comando.append(id);
                comando.append(" ");
                comando.append(p.getRepVideo());
                comando.append(" ");
                comando.append(calidad.toUpperCase());//+" "+tiempo;
                Process proc = Runtime.getRuntime().exec(comando.toString());
                InputStream is = proc.getInputStream();
                int size;
                int exCode = proc.waitFor();
                StringBuilder ret = new StringBuilder("");
                while((size = is.available()) != 0) {
                    byte[] b = new byte[size];
                    is.read(b);
                    ret.append(b);
                }
                String res = ret.toString().replace("\n", "");
                String[] aux = res.split("<");
                if(aux.length>1){
                    res=aux[0];
                }
                if(p.getExtSD()!=null){
                    pathSD.append(res);
                    pathSD.append("/");
                    pathSD.append(p.getRepVideo());
                    pathSD.append(".SD.");
                    pathSD.append(p.getExtSD());
                }
                if (p.getExtHD()!=null){
                    pathHD.append(res);
                    pathHD.append("/");
                    pathHD.append(p.getRepVideo());
                    pathHD.append(".SD.");
                    pathHD.append(p.getExtHD());
                }
                if (p.getExtFHD()!=null){
                    pathFHD.append(res);
                    pathFHD.append("/");
                    pathFHD.append(p.getRepVideo());
                    pathFHD.append(".SD.");
                    pathFHD.append(p.getExtFHD());
                }
                streamer.append(((Configuracion)abd.buscarPorId(Configuracion.class, Long.parseLong("1"))).getIpServidorVideo());
                /***********************************************************/
                Estadistica e = null;
                filtro = new StringBuilder("pelicula.id==");
                filtro.append(p.getId());
                List<Estadistica>ce=new LinkedList<Estadistica>(abd.buscarPorFiltro(Estadistica.class,filtro.toString()));                
                if(ce.size()>0){
                    e=ce.get(0);
                }else{
                    e= new Estadistica(p);
                }
                e.mirar();
                abd.hacerPersistente(e);
                /***********************************************************/
            } catch (Exception ex) {         
               
            }
            session.setAttribute("pSD", pathSD.toString());
            session.setAttribute("pHD", pathHD.toString());
            session.setAttribute("pFHD", pathFHD.toString());
            html.append("           <script type=\"text/javascript\">\n");
            html.append("                   sessionStorage.setItem(\"pathSD\",\"");
            html.append(pathSD.toString());
            html.append("\");\n");                                
            html.append("                   sessionStorage.setItem(\"pathHD\",\"");
            html.append(pathHD.toString());
            html.append("\");\n");
            html.append("                   sessionStorage.setItem(\"streamer\",\"");
            html.append(streamer.toString());
            html.append("\");\n");
            html.append("                   sessionStorage.setItem(\"subs\",\"");
            html.append(p.getSubtitulos());
            html.append("\");\n");
            html.append("                   window.location.replace('espera.jsp');\n");
            html.append("           </script> ");
            abd.concretarTransaccion();    
        }
        catch (Exception e) {
            System.out.println("Error en axiliar.java 198");
            e.printStackTrace();    
            abd.rollbackTransaccion();
        }
        return html.toString();
    }
    
    
    
}
