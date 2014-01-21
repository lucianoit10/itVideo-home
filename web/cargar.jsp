<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>
<%@page import="entidades.RelacionSerieDirector"%>
<%@page import="entidades.RelacionSerieActor"%>
<%@page import="entidades.Serie"%>
<%@page import="entidades.Temporada"%>
<%@page import="entidades.Capitulo"%>
<%@page import="entidades.Preferencia"%>
<%@page import="entidades.Publicidad"%>
<%@page import="entidades.Estadistica"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Persona"%>
<%@page import="entidades.RelacionPeliculaDirector"%>
<%@page import="entidades.RelacionPeliculaActor"%>
<%@page import="entidades.Pelicula"%>
<%@page import="entidades.Imagen"%>
<%@page import="entidades.Configuracion"%>
<%@page import="entidades.Director"%>
<%@page import="entidades.Usuario"%>
<%@page import="entidades.Actor"%>
<%@page import="entidades.Genero"%>
<%@page import="persistencia.AccesoBD"%>
<%@page import="helpers.HelperIndex"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cargando Ejemplos</title>
    </head>
    <body>
       <% 
       HelperIndex h = new HelperIndex();
    h.iniciarConexion(session);
    AccesoBD abd =((AccesoBD)session.getAttribute("conexion"));
    try{
        abd.iniciarTransaccion(); 
        List<Capitulo> caps = abd.listar(new Capitulo());
        List<RelacionPeliculaActor> acts = null;
        List<RelacionPeliculaDirector> dirs = null;
        Capitulo cap=null; 
        Temporada t=null;
        Serie s = null;
        for (int i = 0; i < caps.size(); i++) {
            cap = caps.get(i);   
            /*************************************/
            t = cap.getTemporada();
            t.setPoster(cap.getPoster());
            /*************************************/
            s = t.getSerie();
            s.setGenero(cap.getGenero());
            s.setIdioma(cap.getIdioma());
            s.setPais(cap.getPais());
            s.setClasificacion(cap.getClasificacion());
            s.setSinopsis(cap.getSinopsis());
            /*************************************/
            acts = abd.getObjectsOrdered(RelacionPeliculaActor.class, "pelicula.id == "+cap.getId().toString(),"");
            if (acts!=null && !acts.isEmpty()){
                for(int j = 0; i<acts.size();i++){
                    Actor actor = acts.get(j).getActor();
                    Collection actsSeries = abd.getObjectsOrdered(RelacionSerieActor.class, "serie.id == "+s.getId().toString()+" && actor.id == "+actor.getId().toString(),"");
                    if(actsSeries==null||actsSeries.isEmpty()){
                        RelacionSerieActor r = new RelacionSerieActor(s, actor);
                        abd.hacerPersistente(r);
                    }
                }
            }
            /*************************************/
            dirs = abd.getObjectsOrdered(RelacionPeliculaDirector.class,"pelicula.id == "+cap.getId().toString() ,"");
            if (dirs!=null && !dirs.isEmpty()){
                for(int j = 0; i<dirs.size();i++){
                    Director dir  = dirs.get(j).getDirector();
                    Collection dirsSeries = abd.getObjectsOrdered(RelacionSerieDirector.class, "serie.id == "+s.getId().toString()+" && director.id == "+dir.getId().toString(),"");
                    if(dirsSeries==null||dirsSeries.isEmpty()){
                        RelacionSerieDirector r = new RelacionSerieDirector(s, dir);
                        abd.hacerPersistente(r);
                    }
                }
            }            
            /*************************************/
            abd.hacerPersistente(cap);
            abd.hacerPersistente(t);
            abd.hacerPersistente(s);
        }
        ((AccesoBD)session.getAttribute("conexion")).concretarTransaccion();    
    }
    catch (Exception e) {
        abd.rollbackTransaccion();
        System.out.println(e);
    }
    %>
    <h1><a href="index.jsp" target="_top">TODO OK</a></h1>
    </body>
</html>
