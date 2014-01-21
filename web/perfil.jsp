<%@page import="helpers.HelperUsuario"%>
<%@page import="persistencia.AccesoBD"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
try{
    String pag="perfil.jsp";
    HelperUsuario h = new HelperUsuario();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        abd.iniciarTransaccion(); 
        out.print(h.GenerarComienzoGeneral(abd,session,request, "itVideo"));
        String url = h.getLogo(abd);
        %>
                <center>                    
                    <div class="banner" align="right" style="background-image: url(<%=url%>);background-repeat: no-repeat;">
                        <%out.print(h.banner(abd,session, request, pag)); %>                 
                    </div>
                </center>
            <div id="page" >    
                <div id="contenido" style="width: 100%;text-align: center;">
                    <%String userAgent = request.getHeader("user-agent");
                           if ((userAgent != null)&&(userAgent.indexOf("MSIE") <= -1)) {
                               out.print(h.contenido(abd,session, request,pag));
                           }else{
                                out.print(h.navegadorIncompatible());
                           }%>
                </div>
            </div>
        <%out.print(h.PieDePagina());
        out.print(h.generarFinal()); 
        ((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
    }
    catch (Exception e) {
        abd.rollbackTransaccion();
        System.out.println(e);
    }
}catch(Exception e){
    %>
     <script LANGUAGE="javascript">
        window.onload = function (){
            window.location = "index.jsp";
        }
     </script>
    <%
}
    %>
