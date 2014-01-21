<%@page import="helpers.HelperEspera"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Publicidad"%>
<%@page import="persistencia.AccesoBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>
<html>
    <meta HTTP-EQUIV="REFRESH" content="11; url=Video.jsp">
    <link rel="stylesheet" type="text/css" href="styles/style_1.css" />
    <link href='styles/images/itVideo.gif' rel='shortcut icon' type='image/gif'>
     <script type="text/javascript" src="js/FuncionesComunes.js"></script>
	<body>
        <%try{
            String pag="espera.jsp";
            HelperEspera h = new HelperEspera();
            AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
            try{
                abd.iniciarTransaccion(); 
                String url = h.getLogo(abd);
        %>
                    
                    <div id="page" > 
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
                        <!--center>
                            <div class="columnaMenu">
                                <%--out.print(h.menuYBusqueda(session, pag)); --%>                
                            </div>
                        </center-->  
                        <div id="contenido">
                            <%String userAgent = request.getHeader("user-agent");
                           if ((userAgent != null)&&(userAgent.indexOf("MSIE") <= -1)) {
                               out.print(h.contenido(abd,session, request,pag));
                           }else{
                                out.print(h.navegadorIncompatible());
                           }%>                   
                        </div>   
                    </div>
         <%out.print(h.PieDePagina());
            ((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
            }
            catch (Exception e) {
                abd.rollbackTransaccion();
                out.print(e.getStackTrace());
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
