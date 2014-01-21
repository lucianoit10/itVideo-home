<%@page import="helpers.HelperListadoPeliculas_ajax"%>
<%@page import="persistencia.AccesoBD"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%try{      
    String pag="ListadoPeliculas.jsp";
    HelperListadoPeliculas_ajax h = new HelperListadoPeliculas_ajax();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        abd.iniciarTransaccion(); 
        h.inicializacion(abd, session, request);
        out.print(h.sugerencias(abd, session));               
        out.print(h.contenido(abd, session, request, pag));               
        ((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
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
