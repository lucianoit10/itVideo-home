<%-- 
    Document   : cargar_peliculas_scrollable
    Created on : 17/10/2012, 18:42:57
    Author     : Luciano
--%>

<%@page import="helpers.HelperListadoPeliculas_ajax"%>
<%@page import="persistencia.AccesoBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>




<%
try{
    String id =request.getParameter("idgen");
    int ini = Integer.parseInt(request.getParameter("ini"));
    int paso = Integer.parseInt(request.getParameter("paso"));
    HelperListadoPeliculas_ajax h = new HelperListadoPeliculas_ajax();
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    abd.iniciarTransaccion();
    out.print(h.generarListadoSerie(abd, id, ini,paso));
    abd.concretarTransaccion();
}
catch (Exception e){
    e.printStackTrace();
}

%>











<!--div class="scrollable">           
    <div class="item">                   <a href="#" HEIGHT=223  class="imgTool" OnClick="linkAVideo('402');" ><IMG SRC="archivos/hereafter_poster402.jpg" WIDTH=150 HEIGHT=223 ALT="imagen" style="box-shadow: 5px 5px 5px #888"></a>
    </div>           <div class="item">                   <a href="#" HEIGHT=223  class="imgTool" OnClick="linkAVideo('420');" ><IMG SRC="archivos/band_of_brothers_07_the_breaking_point_poster420.jpg" WIDTH=150 HEIGHT=223 ALT="imagen" style="box-shadow: 5px 5px 5px #888"></a>
    </div>           <div class="item">                   <a href="#" HEIGHT=223  class="imgTool" OnClick="linkAVideo('421');" ><IMG SRC="archivos/band_of_brothers_08_the_last_patrol_poster421.jpg" WIDTH=150 HEIGHT=223 ALT="imagen" style="box-shadow: 5px 5px 5px #888"></a>
    </div>           <div class="item">                   <a href="#" HEIGHT=223  class="imgTool" OnClick="linkAVideo('423');" ><IMG SRC="archivos/the_break_up_poster423.jpg" WIDTH=150 HEIGHT=223 ALT="imagen" style="box-shadow: 5px 5px 5px #888"></a>
           </div>           <div class="item">                   <a href="#" HEIGHT=223  class="imgTool" OnClick="linkAVideo('424');" ><IMG SRC="archivos/300_poster424.jpg" WIDTH=150 HEIGHT=223 ALT="imagen" style="box-shadow: 5px 5px 5px #888"></a>
           </div>   <div  class='navNext'><a href="#" HEIGHT=223 WIDTH=30 id='idNext1' ><IMG SRC="styles/images/br_next.png" ALT="next"></a></div>
   </div-->