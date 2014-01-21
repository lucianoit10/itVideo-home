<%@page import="persistencia.AccesoBD"%>
<%@page import="helpers.HelperVideo"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
try{
    String pag="Video.jsp";
    HelperVideo h = new HelperVideo();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        abd.iniciarTransaccion(); 
        out.print(h.GenerarComienzoGeneral(abd,session,request, "itVideo"));
        out.print(h.manejoDeBotonesVideo(abd,request, session));
        String url = h.getLogo(abd);
%>
            <center>
                <div class="banner">                            
                    <div class="logo" align="left">
                        <%out.print(h.generarLogo(url)); %>   
                    </div>           
                    <div class="log" align="right">
                        <%out.print(h.banner(abd,session, request, pag)); %>                 
                    </div>           
                </div>
            </center>
            <div id="page" >             
                <%String userAgent = request.getHeader("user-agent");
                           if ((userAgent != null)&&(userAgent.indexOf("MSIE") <= -1)) {
                %>
                <center>
                    <div class="columnaMenu">
                        <%out.print(h.menuYBusqueda(session, pag)); %>                
                    </div>
                </center>  
                <div id="contenido">
                    <%out.print(h.contenido(abd,session, request, pag));%>            
                </div>   
                <%
                   }else{
                        out.print(h.navegadorIncompatible());
                   }
                %>
            </div>
 <%out.print(h.PieDePagina());
    ((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
    out.print(h.redireccionar(session));
    out.print(h.generarFinal());
    }
    catch (Exception e) {
        abd.rollbackTransaccion();
        out.print(e.getStackTrace());
    }
}catch(Exception e){
   e.printStackTrace();           
   %>
     <script LANGUAGE="javascript">
        window.onload = function (){
            window.location = "index.jsp";
        }
     </script>
    <%
}
    %>

