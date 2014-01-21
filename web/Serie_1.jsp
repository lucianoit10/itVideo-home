<%@page import="persistencia.AccesoBD"%>
<%@page import="helpers.HelperSerie"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        
    </head>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
try{
    String pag="Video.jsp";
    HelperSerie h = new HelperSerie();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        out.print(h.importsCSS());
        out.print(h.importsJS());
%>
    <body>
<%
        abd.iniciarTransaccion(); 
        out.print(h.contenido(abd,session, request, pag));
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
    </body>
</html>
