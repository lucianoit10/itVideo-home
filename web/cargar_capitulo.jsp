<%@page import="helpers.HelperListadoSeries_ajax"%>
<%@page import="persistencia.AccesoBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
try{  
    HelperListadoSeries_ajax h = new HelperListadoSeries_ajax();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    abd.iniciarTransaccion();
    out.print(h.cargarCapitulos(abd, request));
    abd.concretarTransaccion();
}
catch (Exception e){
    e.printStackTrace();
}
%>