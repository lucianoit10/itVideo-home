<%@page import="persistencia.AccesoBD"%>
<%@page import="helpers.HelperAuxiliar"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">        
         <!--script type="text/javascript" src="js/FuncionesComunes.js"></script-->
    </head>
    <body>
        <% 
        try{        
            HelperAuxiliar h = new HelperAuxiliar();
            AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
            /*try{
                abd.iniciarTransaccion();*/
                   String userAgent = request.getHeader("user-agent");
                   if ((userAgent != null)&&(userAgent.indexOf("MSIE") <= -1)) {
                       out.print(h.contenido(abd,session, request,""));
                   }else{
                        out.print(h.navegadorIncompatible());
                   }
            /*    abd.concretarTransaccion();    
            }
            catch (Exception e) {
                e.printStackTrace();    
                abd.rollbackTransaccion();
            }*/
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
