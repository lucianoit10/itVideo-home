<%@page import="persistencia.AccesoBD"%>
<%@page import="helpers.HelperSerie"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
try{
    String pag="Video.jsp";
    HelperSerie h = new HelperSerie();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        abd.iniciarTransaccion(); 
        out.print(h.GenerarComienzoGeneral(abd,session,request, "itVideo"));
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
                    <%out.print(h.menuIzq(abd,session));%>            
                    <div id="cont">
                        <%out.print(h.contenido(abd,session, request, pag));%>            
                    </div>   
                </div>   
                <%
                   }else{
                        out.print(h.navegadorIncompatible());
                   }
                %>
            </div>
 <%out.print(h.PieDePagina());
    out.print(h.generarFinal());
    ((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
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

