<%@page import="entidades.Pelicula"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Estadistica"%>
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
    </head>
    <body >
        <form name="votar" action="voto.jsp" method="POST">
            <input type="hidden" name="voto" value="" />
        </form>
         <script LANGUAGE="javascript">
            window.onload = function (){
                if (sessionStorage.getItem("voto")<6){
                    document.votar.voto.value = sessionStorage.getItem("voto");
                    sessionStorage.setItem("voto",null);
                    document.votar.submit();
                }
            }
         </script>
        <% 
try{        
            AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
            try{
                abd.iniciarTransaccion();
                if (request.getParameter("voto")!=null &&(!request.getParameter("voto").equals(""))){
                   int voto = Integer.parseInt(request.getParameter("voto"));
                   Estadistica e = null;
                    String filtro = "pelicula.id=="+session.getAttribute("movie").toString();
                    List<Estadistica>ce=new LinkedList<Estadistica>(abd.buscarPorFiltro(Estadistica.class,filtro));                
                    if(ce.size()>0){
                        e=ce.get(0);
                    }else{
                        e= new Estadistica((Pelicula)abd.buscarPorId(Pelicula.class, Long.parseLong(session.getAttribute("movie").toString())));
                    }
                   e.votar(voto);
                   abd.hacerPersistente(e);%>
                   <div style="color: red; font-family: verdana;">SU VOTO A SIDO PROCESADO</div>
                    <script type="text/javascript">
                        setTimeout('self.close();',500);
                    </script>
              <% }
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
    </body>
</html>


