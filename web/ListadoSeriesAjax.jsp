
<%-- 
    Document   : preferencia
    Created on : 26/12/2012, 18:05:14
    Author     : Luciano
--%>

<%@page import="helpers.HelperListadoSeries_ajax"%>
<%@page import="persistencia.AccesoBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        
    </head>
<%try{
           
    String pag="ListadoSeries.jsp";
    HelperListadoSeries_ajax h = new HelperListadoSeries_ajax();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        abd.iniciarTransaccion(); 
        h.inicializacion(abd, session, request);        
        out.print(h.importsCSS());
        out.print(h.importsJS());%>
<body onload="touchScroll('lista_series');touchScroll('lista_temp');touchScroll('lista_cap');">
        <%out.print(h.contenido(abd, session, request, pag));%>
</body>
        <%((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
    }
    catch (Exception e) {
        abd.rollbackTransaccion();
        e.printStackTrace();
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
</html>    
