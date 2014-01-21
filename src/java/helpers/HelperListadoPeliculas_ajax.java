package helpers;

import entidades.Alquiler;
import entidades.Capitulo;
import entidades.Estadistica;
import entidades.Genero;
import entidades.Pelicula;
import entidades.Preferencia;
import entidades.RelacionPeliculaActor;
import entidades.RelacionPeliculaDirector;
import entidades.RelacionPreferenciaGenero;
import entidades.Serie;
import entidades.Usuario;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import persistencia.AccesoBD;

public class HelperListadoPeliculas_ajax extends HelperGeneral{
    private int cotaScrollable=4;
    
    @Override
    public String importsCSS() {
        StringBuilder html = new StringBuilder ("<style>\n");
        html.append("   a:link, a:visited {");
        html.append("       color : blue;");
        html.append("       text-decoration : none;");
        html.append("   }");
        html.append("   a img {");
        html.append("       border : 0;");
        html.append("   }");
        html.append("</style>\n");
        html.append("<link rel=\"stylesheet\" href=\"styles/accordionImageMenu.css\" type=\"text/css\" />\n");
        html.append("<link rel=\"stylesheet\" href=\"styles/overlay-apple.css\"  type=\"text/css\">\n");
        return html.toString();
    }

    @Override
    public String importsJS() {        
        StringBuilder html = new StringBuilder("<script src=\"js/ajax.googleapis.js\"></script>");
        html.append("<script LANGUAGE=\"javascript\">");
        html.append("$(function () {");
        html.append("     var top = $('#menu').offset().top - parseFloat($('#menu').css('margin-top').replace(/auto/, 0));");
        html.append("     $(window).scroll(function (event) {");
        html.append("         var y = $(this).scrollTop();");
        html.append("         if (y >= top) { ");
        html.append("             $('#menu').addClass('fixed');");
        html.append("         } else { ");
        html.append("             $('#menu').removeClass('fixed'); ");
        html.append("         }");
        html.append("     });");
        html.append("});");
        html.append("</script> ");
        return html.toString();
    }
    
    @Override
    public void inicializacion(AccesoBD abd,HttpSession session,HttpServletRequest request) {
        session.setAttribute("ver", null);
        session.setAttribute("path",null);
        session.setAttribute("subs",null);
        session.setAttribute("elegirCalidad", null);
        session.setAttribute("calidad", null);
        session.setAttribute("movie",null); 
        session.setAttribute("serie",null); 
        session.setAttribute("pagActual","ListadoPeliculas.jsp");
    }

    @Override
    public String title(HttpSession session, String title) {
        StringBuilder html = new StringBuilder("<title>");
        html.append(title);
        html.append("</title>\n");
        return html.toString();
    }

    @Override
    public String generarMedio(){
        StringBuilder html =  new StringBuilder ("   </head>\n");
        html.append("   <body>\n");
        html.append("       <script type=\"text/javascript\" src=\"js/FuncionesComunes.js\"></script>\n");
        return html.toString();
    }
    
    @Override
    public String contenido(AccesoBD abd,HttpSession session, HttpServletRequest request,String pag) {
        StringBuilder filtro = new StringBuilder ("pelicula.baja==false");
        StringBuilder html =new StringBuilder ("");
        String ordenarRecientes ="pelicula.anio descending";
        String ordenarVotadas ="promedioVotos descending";
        String ordenarVistas ="cantidadDeVistas descending";
        List<Pelicula> pelisRecientes=new LinkedList<Pelicula>();
        List<Pelicula> pelisVotadas=new LinkedList<Pelicula>();
        List<Pelicula> pelisVistas=new LinkedList<Pelicula>();
        /*-----------------------------Recientes-----------------------------*/
        Collection   c=null;
            c = abd.getObjectsOrdered(Estadistica.class,filtro.toString(),ordenarRecientes);
            if (c!=null){
                LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                for (int i = 0; i < est.size(); i++) {
                    Estadistica e = (Estadistica) est.get(i);
                    pelisRecientes.add(e.getPelicula());
                    if(i>=cotaScrollable){
                        i=est.size();
                    }
                }
            }
            /*-----------------------------Vistas-----------------------------*/
            c=null;
            c = abd.getObjectsOrdered(Estadistica.class,filtro.toString(),ordenarVistas);
            if (c!=null){
                LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                for (int i = 0; i < est.size(); i++) {
                    Estadistica e = (Estadistica) est.get(i);
                    pelisVistas.add(e.getPelicula());
                    if(i>=cotaScrollable){
                        i=est.size();
                    }
                }
            }
            /*-----------------------------Votadas-----------------------------*/
            c=null;
            c = abd.getObjectsOrdered(Estadistica.class,filtro.toString(),ordenarVotadas);
            if (c!=null){
                LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                for (int i = 0; i < est.size(); i++) {
                    Estadistica e = (Estadistica) est.get(i);
                    pelisVotadas.add(e.getPelicula());
                    if(i>=cotaScrollable){
                        i=est.size();
                    }
                }
            }
            session.setAttribute("listaPeliculas", pelisRecientes);
        pelisRecientes=(List<Pelicula>) session.getAttribute("listaPeliculas");
        if (pelisRecientes!=null && pelisRecientes.size()>0){
            html.append("       <form method=\"post\" name=\"selectVideo\" action= \"Video.jsp\">\n");
            html.append("           <input type=\"hidden\" name=\"idVideo\">\n");
            html.append("       </form>\n");
            html.append("       <form method=\"post\" name=\"selectSerie\" action= \"Serie.jsp\">\n");
            html.append("           <input type=\"hidden\" name=\"idSerie\">\n");
            html.append("       </form>\n");
            html.append("       <center>\n");
            int i =0;
            List<Pelicula> estrenos = abd.getObjectsOrdered(Pelicula.class, "estreno==true && baja==false", "anio descending"); 
            if (estrenos.size()>0){
                html.append("<div class=\"page_scroll\" id='idPagEstrenoss' style='padding-left : 50px;'> ");
                html.append("   <a href=\"#\" class='tituloLista' OnClick=\"estrenoListar()\" title=\"Click aquí para ver todos las películas de estreno.\"><h2>Estrenos</h2></a>");
                html.append("   <div class=\"scrollable\" id='idScrollEstrenos'>");
                html.append("        <div  class='navPrev'></div>");
                for(i = 0;i<cotaScrollable && i<estrenos.size(); i++){
                    Pelicula p = estrenos.get(i);
                    
                    html.append("           <div class=\"item\" >");
                    html.append(infoPelicula(p, abd));
                    html.append("       </div>");
                    if(i>=cotaScrollable){
                        break;
                    }
                } 
                if (i>=cotaScrollable  && i<pelisRecientes.size()){
                    html.append("   <div  class='navNext'><a href=\"#idPagEstrenos\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScrollEstrenos','cargar_peliculas_scrollable.jsp?paso=1&idgen=Estrenos&ini=1');\"  id='idNextEstrenos'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n" );
                }
                html.append("   </div>\n</div>");             
            }
             /*-------------------------------------------------------------------------------*/
             /*--------------------------------- generos--------------------------------------*/
             List<Genero> generos = abd.listar(new Genero() );
             for (i = 0; i < generos.size(); i++) {
                Genero genero = generos.get(i);
                 try{
                    List<Pelicula> peliGenero = new LinkedList<Pelicula> (abd.getObjectsOrdered(Pelicula.class,"genero.id=="+genero.getId()+"  && baja==false","anio descending"));
                   if (peliGenero.size()>=1){
                       StringBuilder paginaNext= new StringBuilder ("cargar_peliculas_scrollable.jsp?paso=1&idgen=");
                       paginaNext.append(genero.getId());
                       paginaNext.append("&ini=1");
                       html.append("<div class=\"page_scroll\" id='idPag");
                       html.append(genero.getId());
                       html.append("s' > \n <a href=\"#\" class='tituloLista' OnClick=\"generoListar('");
                       html.append(genero.getNombre());
                       html.append("')\" title=\"Click aquí para ver todas las películas de este genero.\"><h2>");
                       html.append(genero.getNombre().toUpperCase());
                       html.append("</h2></a>");
                       html.append("   <div class=\"scrollable\" id='idScroll");
                       html.append(genero.getId());
                       html.append("'>");
                       html.append("        <div  class='navPrev'></div>");
                       int j;
                       for(j = 0; j<peliGenero.size()&&j<cotaScrollable; j++){
                            html.append("           <div class=\"item\">");
                            html.append(infoPelicula(peliGenero.get(j), abd));
                            html.append("           </div>");
                            if(j>=cotaScrollable){
                                break;
                            }
                        }
                         
                        if (j>=cotaScrollable && j<peliGenero.size()){
                            html.append("   <div  class='navNext'><a href=\"#idPag");
                            html.append(genero.getId());
                            html.append("\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScroll");
                            html.append(genero.getId());
                            html.append("','");
                            html.append(paginaNext);
                            html.append("');\"  id='idNext");
                            html.append(genero.getId());
                            html.append("'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n");
                        }
                        html.append("   </div></div>");   
                   }
                     }catch(Exception e){
                     }
             }
             /*-------------------------------------------------------------------------------*/
            html.append("<div class=\"page_scroll\" id='idPagRecientess'> ");
            html.append("   <a href=\"#\" class='tituloLista' OnClick=\"MasRecientesListar()\" title=\"Click aquí para ver todas las películas Mas Recientes.\"><h2>Las Mas Recientes</h2></a>");
            html.append("   <div class=\"scrollable\" id='idScrollRecientes'>");
            html.append("        <div  class='navPrev'></div>");
            i =0;
            for(i = 0;i<cotaScrollable && i<pelisRecientes.size(); i++){
                html.append("           <div class=\"item\" >");
                html.append(infoPelicula(pelisRecientes.get(i), abd));
                html.append("       </div>");
                if(i>=cotaScrollable){
                    break;
                }
            } 
            if (i>=cotaScrollable  && i<pelisRecientes.size()){
                html.append("   <div  class='navNext'><a href=\"#idPagRecientes\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScrollRecientes','cargar_peliculas_scrollable.jsp?paso=1&idgen=Recientes&ini=1');\"  id='idNextRecientes'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n") ;
            }
            html.append("   </div>\n</div>");             
             /*-------------------------------------------------------------------------------*/
             /*-------------------------------------------------------------------------------*/
            html.append("<div class=\"page_scroll\"  id='idPagVotadass'> ");
            html.append("   <a href=\"#\" class='tituloLista' OnClick=\"MejorVotadasListar()\" title=\"Click aquí para ver las películas Mmejor Votadas.\"><h2>Las Mejor Votadas</h2></a>");
            html.append("   <div class=\"scrollable\" id='idScrollVotadas'>");
            html.append("        <div  class='navPrev'></div>");
            i =0;
            for(i = 0;i<cotaScrollable &&i<pelisVotadas.size(); i++){
                html.append("           <div class=\"item\">");
                html.append(infoPelicula(pelisVotadas.get(i), abd));
                html.append("           </div>");
                if(i>=cotaScrollable){
                    break;
                }
            } 
            if (i>=cotaScrollable && i<pelisVotadas.size()){
                html.append("   <div  class='navNext'><a href=\"#idPagVotadas\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScrollVotadas','cargar_peliculas_scrollable.jsp?paso=1&idgen=Votadas&ini=1');\"  id='idNextVotadas'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n");
            }
            html.append("   </div></div>");             
             /*-------------------------------------------------------------------------------*/
             /*-------------------------------------------------------------------------------*/
            html.append("<div class=\"page_scroll\" id='idPagVistass'> ");
            html.append("   <a href=\"#\" class='tituloLista' OnClick=\"MasVistasListar()\" title=\"Click aquí para ver las películas Mas Vistas.\"><h2>Las Mas Vistas</h2></a>");
            html.append("   <div class=\"scrollable\" id='idScrollVistas'>");
            html.append("        <div  class='navPrev'></div>");
            i =0;
            for(i = 0;i<cotaScrollable &&i<pelisVistas.size(); i++){
                html.append("           <div class=\"item\">");
                html.append(infoPelicula(pelisVistas.get(i), abd));
                html.append("           </div>");
                if(i>=cotaScrollable){
                    break;
                }
            } 
            if (i>=cotaScrollable && i<pelisVistas.size()){
                html.append("   <div  class='navNext'><a href=\"#idPagVistas\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScrollVistas','cargar_peliculas_scrollable.jsp?paso=1&idgen=Vistas&ini=1');\"  id='idNextVistas'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n");
            }
            html.append("   </div>\n</div>\n</center>\n");
        }
        else {
            html.append("<br><b3>Ups, no se han encontrado resultados en la busqueda.</b3><br><br><br>\n");
        }
        return html.toString();
    }
    
    public String infoPelicula (Pelicula p, AccesoBD abd){
        String desc = p.getTitulo();
        if (desc!=null && desc.length()>20){
            desc=desc.substring(0, 20)+"...";
        }
        StringBuilder html = new StringBuilder("                   <a href=\"#\" HEIGHT=223 OnClick=\"linkAVideo('");
        html.append(p.getId());
        html.append("');\"  class=\"imgTool\" rel=\"tooltip");
        html.append(p.getId());
        html.append("\" ><IMG SRC=\"");
        html.append(p.getPoster().getPath());
        html.append("\" WIDTH=150 HEIGHT=223 ALT=\"imagen\" style=\"box-shadow: 5px 5px 5px #888\"><div style='width:140px; font-size:10px;'>");
        html.append(desc);
        html.append("</div></a>\n");
       // html += codTooltip(p, abd)+codPopups(p, abd);
        return html.toString();
    }
    
    public String infoSerie (Serie s, AccesoBD abd){
        String desc = s.getTitulo();
        if (desc!=null && desc.length()>20){
            desc=desc.substring(0, 20)+"...";
        }
        if (desc==null){
            desc="...";
        }
        StringBuilder html = new StringBuilder("                   <a href=\"#\" HEIGHT=223 OnClick=\"linkASerie('");
        html.append(s.getId());
        html.append("');\"><IMG SRC=\"");
        html.append(s.getPoster().getPath());
        html.append("\" WIDTH=150 HEIGHT=223 ALT=\"imagen\" style=\"box-shadow: 5px 5px 5px #888\"><div style='width:140px; font-size:10px;'>");
        html.append(desc);
        html.append("</div></a>\n");
       // html += codTooltip(p, abd)+codPopups(p, abd);
        return html.toString();
    }
    
    public String generar (AccesoBD abd, String idGen,int ini,int paso){
        List<Pelicula> peli = listaDePeliculas(abd, idGen);
        StringBuilder script = new StringBuilder("");
        StringBuilder html = new StringBuilder("   <div class=\"scrollable\" id='idScroll");
        html.append(idGen);
        html.append("'>");
        html.append("        <div  class='navPrev'>");
        if (ini!=0){
            html.append("        <a href=\"#idPag");
            html.append(idGen);
            html.append("\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScroll");
            html.append(idGen);
            html.append("','cargar_peliculas_scrollable.jsp?paso=-1&idgen=");
            html.append(idGen);
            html.append("&ini=");
            html.append((ini-1));
            html.append("');\"  id='idPrev");
            html.append(idGen);
            html.append("'><IMG SRC=\"styles/images/br_prev.png\" ALT=\"back\"></a>\n");
        }else{
            script =new StringBuilder("<script>window.onload= document.getElementById('idNext");
            script.append(idGen);
            script.append("').focus();</script>");
        }
        html.append("        </div>");
        int j;
        int cotaIni = ini*cotaScrollable;
        int cotaFin = (ini+1)*cotaScrollable;        
        for(j = cotaIni; j<peli.size()&&j<cotaFin; j++){
            Pelicula p = peli.get(j);
            html.append("           <div class=\"item\">");
            html.append(infoPelicula(p, abd));
            html.append("           </div>");
            if(j>=cotaFin){
                break;
            }
        }
        if (j>=cotaFin && j<peli.size()){
            html.append("   <div  class='navNext'><a href=\"#idPag");
            html.append(idGen);
            html.append("\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScroll");
            html.append(idGen);
            html.append("','cargar_peliculas_scrollable.jsp?paso=1&idgen=");
            html.append(idGen);
            html.append("&ini=");
            html.append((ini+1));
            html.append("');\"  id='idNext");
            html.append(idGen);
            html.append("'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n");
        }else{
            script =new StringBuilder("<script>window.onload= document.getElementById('idPrev");
            script.append(idGen);
            script.append("').focus();</script>");
        }
        if (script.toString().equals("")){
            if (paso>0){
                script =new StringBuilder("<script>window.onload= document.getElementById('idNext");
                script.append(idGen);
                script.append("').focus();</script>");
            }else{
                script =new StringBuilder("<script>window.onload= document.getElementById('idPrev");
                script.append(idGen);
                script.append("').focus();</script>");
            }
        }
        html.append("   </div>");
        html.append(script.toString());
        return html.toString();
    }
    
    public String generarListadoSerieInicial (AccesoBD abd){
        StringBuilder html = new StringBuilder("");
        int i = 0;
         /*-------------------------------------------------------------------------------*/
         /*-------------------------------------------------------------------------------*/
         List<Serie> serie = new LinkedList<Serie> (abd.getObjectsOrdered(Serie.class,"baja==false","id descending"));
         if (serie!=null && serie.size()>0){
            html.append("<center><div class=\"page_scroll\" id='idPagSeries'> ");
            html.append("   <a href=\"#\"  Onclick='cargarListasSeries();' class='tituloLista' title=\"Click aquí para ver todas las series.\"><h2>Series</h2></a>");
            html.append("   <div class=\"scrollable\" id='idScrollSerie'>");
            html.append("        <div  class='navPrev'></div>");
            for(i = 0;i<cotaScrollable &&i<serie.size(); i++){
                Serie s = serie.get(i);
                html.append("           <div class=\"item\">");
                html.append(infoSerie(s, abd));
                html.append("           </div>");
                if(i>=cotaScrollable){
                    break;
                }
            } 
            if (i>=cotaScrollable && i<serie.size()){
                html.append("   <div  class='navNext'><a href=\"#idPagSerie\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScrollSerie','cargar_serie_scrollable.jsp?paso=1&idgen=Serie&ini=1');\"  id='idNextSerie'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n" );
            }
            html.append("   </div></div></center>");             
         }
        /*-------------------------------------------------------------------------------*/
         /*-------------------------------------------------------------------------------*/
        return html.toString();
    }
     
    public String generarListadoSerie (AccesoBD abd, String idGen,int ini,int paso){
        //String paginaPrev="cargar_serie_scrollable.jsp?paso=-1&idgen="+idGen+"&ini="+(ini-1);
        //String paginaNext="cargar_serie_scrollable.jsp?paso=1&idgen="+idGen+"&ini="+(ini+1);
        List<Serie> serie = new LinkedList<Serie> (abd.getObjectsOrdered(Serie.class,"baja==false","anio descending"));
        StringBuilder script = new StringBuilder("");
        StringBuilder html =  new StringBuilder("   <div class=\"scrollable\" id='idScroll");
        html.append(idGen);
        html.append("'>");
        html.append("        <div  class='navPrev'>");
        if (ini!=0){
            html.append("        <a href=\"#idPag");
            html.append(idGen);
            html.append("\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScroll");
            html.append(idGen);
            html.append("','cargar_serie_scrollable.jsp?paso=-1&idgen=");
            html.append(idGen);
            html.append("&ini=");
            html.append(ini-1);
            html.append("');\"  id='idPrev");
            html.append(idGen);
            html.append("'><IMG SRC=\"styles/images/br_prev.png\" ALT=\"back\"></a>\n");
        }else{
            script = new StringBuilder("<script>window.onload= document.getElementById('idNext");
            script.append(idGen);
            script.append("').focus();</script>");
        }
        html.append("        </div>");
        int j;
        int cotaIni = ini*cotaScrollable;
        int cotaFin = (ini+1)*cotaScrollable;
        
        for(j = cotaIni; j<serie.size()&&j<cotaFin; j++){
            Serie s = serie.get(j);
            html.append("           <div class=\"item\">");
            html.append(infoSerie(s, abd));
            html.append("           </div>");
            if(j>=cotaFin){
                break;
            }
        }
        if (j>=cotaFin && j<serie.size()){
            html.append( "   <div  class='navNext'><a href=\"#idPag");
            html.append(idGen);
            html.append("\" HEIGHT=223 WIDTH=30 OnClick=\"actualizarScrollableAjax('idScroll");
            html.append(idGen);
            html.append("','cargar_serie_scrollable.jsp?paso=1&idgen=");
            html.append(idGen);
            html.append("&ini=");
            html.append((ini+1));
            html.append("');\"  id='idNext");
            html.append(idGen);
            html.append("'><IMG SRC=\"styles/images/br_next.png\" ALT=\"next\"></a></div>\n");
        }else{
            script = new StringBuilder("<script>window.onload= document.getElementById('idPrev");
            script.append(idGen);
            script.append("').focus();</script>");
        }
        if (script.toString().equals("")){
            if (paso>0){
                script =new StringBuilder("<script>window.onload= document.getElementById('idNext");
                script.append(idGen);
                script.append("').focus();</script>");
            }else{
                script = new StringBuilder("<script>window.onload= document.getElementById('idPrev");
                script.append(idGen);
                script.append("').focus();</script>");
            }
        }
        html.append("   </div>");
        html.append(script);
        return html.toString();
    }
          
    private List<Pelicula> listaDePeliculas(AccesoBD abd,String id){
        List<Pelicula> lista= new LinkedList<Pelicula>();
        String ordenarVotadas ="promedioVotos descending";
        String ordenarVistas ="cantidadDeVistas descending";
        if(id.equals("Recientes")){
            lista=abd.getObjectsOrdered(Pelicula.class, "baja==false", "anio descending");
        }else{
            if(id.equals("Vistas")){
                Collection c = abd.getObjectsOrdered(Estadistica.class,"pelicula.baja==false",ordenarVistas);
                if (c!=null){
                    LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                    for (int i = 0; i < est.size(); i++) {
                        Estadistica e = (Estadistica) est.get(i);
                        lista.add(e.getPelicula());
                    }
                }
            }else{
                if(id.equals("Votadas")){
                    Collection c = abd.getObjectsOrdered(Estadistica.class,"pelicula.baja==false",ordenarVotadas);
                    if (c!=null){
                        LinkedList<Estadistica> est = new LinkedList<Estadistica>(c);
                        for (int i = 0; i < est.size(); i++) {
                            Estadistica e = (Estadistica) est.get(i);
                            lista.add(e.getPelicula());
                        }
                    }
                }else{
                    if(id.equals("Estrenos")){
                        lista=abd.getObjectsOrdered(Pelicula.class, "estreno==true && baja==false", "anio descending");                        
                    }else{
                        lista=abd.getObjectsOrdered(Pelicula.class, "genero.id=="+id+"  && baja==false", "anio descending");
                    }
                }
            }
        }     
        return lista;    
    }
    
    public String sugerencias(AccesoBD abd,HttpSession session){
                /*obtengo el usuario logueado*/
        Usuario user = null;
        List<Usuario>l=new LinkedList<Usuario>(abd.buscarPorFiltro(Usuario.class,"nameUsuario.equals(\""+session.getAttribute("user") +"\")"));                
        if(l.size()>0){
            user=l.get(0);
        } 
        StringBuilder html= new StringBuilder("");
        //StringBuilder html= new  StringBuilder("");
        if (user!=null){
            /*me quedo con las peliculas ya vistas*/
            List<Alquiler> e = abd.getObjectosOrdenadosYAgrupados(Alquiler.class,"usuario.id=="+user.getId(),"pelicula.id descending","usuario.id,pelicula.id");
            Collection<Pelicula> alq = null;
            if (e!=null&&!e.isEmpty()){
                alq=new LinkedList<Pelicula>();
                for(int i=0;i<e.size();i++){
                    alq.add(e.get(i).getPelicula());
                }        
            }
            /*---------------------------------------*/
            Preferencia p = (Preferencia)(new LinkedList(abd.buscarPorFiltro(Preferencia.class, "usuario.id ==" + user.getId()))).get(0);
            StringBuilder filtro = new StringBuilder ("pelicula.baja == false ");
            StringBuilder orden = new StringBuilder ("");
            if(p.isClasicos()){
                filtro.append(" && pelicula.anio < 2000");
            }
            if(p.isBienPuntuadas()){
                orden.append("promedioVotos");
            }
            if(p.isEstrenos()){
                if (!orden.toString().equals(""))
                    orden.append(",");
                orden.append("pelicula.estreno");            
            }
            if (!orden.toString().equals(""))
                orden.append(" descending");
            List<RelacionPreferenciaGenero> g = new LinkedList<RelacionPreferenciaGenero>(abd.buscarPorFiltro(RelacionPreferenciaGenero.class, "preferencia.id=="+p.getId()));
            if (g!=null&&g.size()>0){
                filtro.append("&& (");
                for (int i = 0; i < g.size(); i++) {
                    if (i!=0)
                        filtro.append("||");
                    filtro.append("pelicula.genero.id==");
                    filtro.append(g.get(i).getGenero().getId());                
                }
                filtro.append(")");
            }

            Collection<Pelicula> todos = new HashSet<Pelicula>();
            if(p.isPeliculas()){
                if(p.isSeries()){
                    List<Estadistica> pe = new LinkedList<Estadistica>(abd.getObjectsOrdered(Estadistica.class, filtro.toString(), orden.toString()));                
                    for (int i=0;i<pe.size();i++) {
                        Estadistica a = pe.get(i);
                        if(a.getPelicula()!=null)
                            todos.add(a.getPelicula());
                    }
                }else{
                    List<Estadistica> pe = new LinkedList<Estadistica>(abd.getObjectsOrdered(Estadistica.class, filtro.toString(), orden.toString()));                
                    for (int i=0;i<pe.size();i++) {
                        Estadistica a = pe.get(i);
                        todos.add(a.getPelicula());
                    }
                    Collection<Pelicula> b = abd.buscarPorFiltro(Capitulo.class,"baja==false");
                //    Collection<Pelicula> b = abd.buscarPorFiltro(Serie.class,"baja==false");
                    todos.removeAll(b);                
                }                    
            }else{
                if(p.isSeries()){
                    todos = abd.buscarPorFiltro(Capitulo.class,"baja==false");
                    //todos = abd.buscarPorFiltro(Serie.class,"baja==false");
                    Collection<Pelicula> b = new HashSet<Pelicula>();
                    List<Estadistica> pe = new LinkedList<Estadistica>(abd.getObjectsOrdered(Estadistica.class, filtro.toString(), orden.toString()));                
                    for (int i=0;i<pe.size();i++) {
                        Estadistica a = pe.get(i);
                        b.add(a.getPelicula());
                    }
                    todos.retainAll(b);
                    System.out.println("-------------------------------------------------------");
                    System.out.println("TIPO"+todos.getClass()+" Y TAMAÑO DEL COLLECTION : "+todos.size());
                    System.out.println("-------------------------------------------------------");
                }else{
                    List<Estadistica> pe = new LinkedList<Estadistica>(abd.getObjectsOrdered(Estadistica.class, filtro.toString(), orden.toString()));                
                    if(pe!= null){
                        for (int i=0;i<pe.size();i++) {
                            Estadistica a = pe.get(i);
                            todos.add(a.getPelicula());
                        }            
                    }
                }
            }
            /*ahora me fijo cuales no vio y esas son las sugerencias*/
            List<Pelicula> copy = new LinkedList<Pelicula>(todos);
            List<Pelicula> copy2 = new LinkedList<Pelicula>(todos);
            if (alq !=null)
                copy.removeAll(alq);
            //si quedan poquitas peliculas para sugerir o nunguna se agregan las ya alquiladas
            if(copy!=null && copy.size()<10){
               copy=copy2; 
            }
            todos=copy;
            //--------------------------------------------------------------------------------
            int j = 1;
            int cantSug = 30;
            html.append("        <div class='sugerencias'>\n"); 
            for (Pelicula pe : todos) {
                if (j>cantSug)
                    break;
                html.append("<div id='sug");
                html.append(j);
                html.append("'");
                if (j==1){
                    html.append("style='display: block;'");
                }else{
                    html.append("style='display: none;'");
                }
                html.append(">\n");
                html.append("       <table border=\"0\" class=\"presentacion\">\n");
                html.append("           <tr>\n");
                html.append("               <td class=\"fondoRedondoTL\"></td>\n");
                html.append("               <td class=\"fondoRedondo\"></td>\n");
                html.append("               <td class=\"fondoRedondoTR\"></td>\n");
                html.append("           </tr>\n");
                html.append("           <tr>\n");
                html.append("               <td class=\"fondoRedondo\"></td>\n");
                html.append("               <td class=\"fondoRedondo\"> \n");
                html.append("                   <div class=\"Descripcion\">  \n");
                html.append("                       <table border=\"0\" width=\"100%\">\n");
                html.append("                           <tr>\n");
                html.append("                               <td>\n");
                html.append("                                   <a href=\"#\" HEIGHT=223 OnClick=\"linkAVideo('");
                html.append(pe.getId());
                html.append("');\" class='imgSug'><IMG SRC=\"");
                html.append(pe.getPoster().getPath());
                html.append("\" WIDTH=150 HEIGHT=233 BORDER=2 ALT=\"imagen\"></a>\n");
                html.append("                               </td>\n");
                html.append("                               <td style=\"vertical-align: top\">\n");
                html.append( "                                   <table border=\"0\" >\n");
                html.append("                                       <tr>\n");
                html.append("                                           <td class=\"titulo\"> <a href=\"#\" HEIGHT=223 OnClick=\"linkAVideo('");
                html.append(pe.getId());
                html.append("');\"><h4>");
                html.append(pe.getTitulo());
                if(pe.getTituloOriginal()!=null && (!pe.getTituloOriginal().equals(""))){
                    html.append("(");
                    html.append(pe.getTituloOriginal());
                    html.append(")");
                }
                html.append("</h4></a></td>\n");
                html.append("                                       </tr>\n");
                html.append("                                       <tr>\n");
                html.append("                                           <td class=\"datos\">");
                html.append(pe.getGenero().getNombre());
                html.append(" | ");
                html.append(pe.getIdioma());
                html.append(" | Año: ");
                html.append(pe.getAnio());
                html.append(" | Duracion: ");
                html.append(pe.getDuracion());
                html.append(" min </td>\n");
                html.append("                                       </tr>\n");
                html.append("                                       <tr>\n");
                 if (pe.getSinopsis().length()>250){
                    html.append("                                           <td class=\"desc\"><p>");
                    html.append(pe.getSinopsis().substring(0, 250) );
                    html.append("...</p>\n");
                 }else{
                    html.append("                                           <td class=\"desc\"><p>");
                    html.append(pe.getSinopsis());
                    html.append("</p>\n");
                 }
                Collection c =abd.buscarPorFiltro(RelacionPeliculaDirector.class, "pelicula.id == "+p.getId());        
                List<RelacionPeliculaDirector> rpds = null;
                if (c!=null){
                    rpds = new LinkedList<RelacionPeliculaDirector>(c);
                }
                if (rpds != null && rpds.size()>0){
                    html.append("                                           <p>Directores: ");
                    for (int i = 0; i < rpds.size(); i++) {
                        RelacionPeliculaDirector rpd = rpds.get(i);
                        html.append(rpd.getDirector().getNombre());
                        html.append(" ");
                        html.append(rpd.getDirector().getApellido());
                        if (i<rpds.size()-1){
                           html.append(","); 
                        }
                    }
                    html.append("                                       </p>\n");
                }
                c=abd.buscarPorFiltro(RelacionPeliculaActor.class, "pelicula.id == "+p.getId());
                List<RelacionPeliculaActor> rpas = null;
                if (c!=null){
                    rpas = new LinkedList<RelacionPeliculaActor>(c);
                }
                if (rpas !=null && rpas.size()>0){
                    html.append("                                           <p>Actores: ");
                    for (int i = 0; i < rpas.size(); i++) {
                        RelacionPeliculaActor rpa = rpas.get(i);
                        html.append(rpa.getActor().getNombre());
                        html.append(" ");
                        html.append(rpa.getActor().getApellido());
                        if (i<rpas.size()-1){
                            html.append(","); 
                        }
                    }
                    html.append("                                       </p>\n");
                }
                html.append("                                           </td>\n");
                html.append("                                       </tr>\n");
                html.append("                                       <tr>\n");
                html.append("                                           <td>\n");
                if (j>1){
                    html.append("                               <button id=\"ant");
                    html.append(j);
                    html.append("\" class=\"boton\" name=\"ant");
                    html.append(j);
                    html.append("\" onclick=\"setDisplayNoneBlock('sug");
                    html.append(j);
                    html.append("','sug");
                    html.append((j-1));
                    html.append("');");
                    if(j>2){
                        html.append("document.getElementById('ant");
                        html.append((j-1));
                        html.append("').focus();");
                    }else{
                        html.append("document.getElementById('sig");
                        html.append((j-1));
                        html.append("').focus();");
                    }
                    html.append("\">Sugenercia Anterior</button>\n");
                }
                if (j+1<=todos.size()&&j+1<=cantSug){
                    html.append("                               <button id=\"sig");
                    html.append(j);
                    html.append("\" class=\"boton\" name=\"sig");
                    html.append(j);
                    html.append("\" onclick=\"setDisplayNoneBlock('sug");
                    html.append(j);
                    html.append("','sug");
                    html.append((j+1));
                    html.append("');");
                    if(j+2<=todos.size()&&j+2<=cantSug){
                        html.append("document.getElementById('sig");
                        html.append((j+1));
                        html.append("').focus();");
                    }
                    else   {
                        html.append("document.getElementById('ant");
                        html.append((j+1));
                        html.append("').focus();");
                    }
                    html.append("\">Proxima Sugenercia</button>\n");
                }
                html.append("                                           </td>\n");
                html.append("                                       </tr>\n");
                html.append("                                   </table>\n");
                html.append("                               </td>\n");
                html.append("                               <td >\n");
                html.append("                                   </td>\n");
                html.append("                               </tr>\n");
                html.append("                           </table>\n");
                html.append("                       </div>\n");
                html.append("                   </td>\n");
                html.append("                   <td class=\"fondoRedondo\"></td>\n");
                html.append("               </tr>\n");
                html.append("               <tr>\n");
                html.append("                   <td class=\"fondoRedondoBL\"></td>\n");
                html.append("                   <td class=\"fondoRedondo\"></td>\n");
                html.append("                   <td class=\"fondoRedondoBR\"></td>\n");
                html.append("               </tr>\n");
                html.append("           </table>\n");
                html.append("        </div>\n"); 
                j++;

            }
            html.append("        </div>\n"); 
        }
        return html.toString();
    }

}
