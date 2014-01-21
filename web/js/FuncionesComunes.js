/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function limpiandoTexto(valorDefecto,textfield){
         if(textfield.value == valorDefecto){
        	 textfield.value = "";
         }
}

function cerrarSesion(){
    if (confirm('Esta seguro que desea salir de la sesion?')){
        document.CerrarSesion.cerrar.value="true";
        document.CerrarSesion.submit();
    }
}

function infoLogin(){
    alert('Para realiza esa operación debe estar previamente logeado\n por favor ingrese al sistema. En caso de no contar con el usuario, comuníquese con la empresa');
}

function linkAVideo(idVideo){
    document.selectVideo.idVideo.value=idVideo;
    document.selectVideo.submit();
}

function linkASerie(idSerie){
    document.selectSerie.idSerie.value=idSerie;
    document.selectSerie.submit();
}

function todas(){
    document.home.submit();
}

function alquilada(){
    document.menuPrincipal.alquiladas.value=1;
    document.menuPrincipal.submit();
}

function selGenero(id){
    document.selectGenero.idGenero.value=id;
    document.selectGenero.orden.value=document.menuPrincipal.orden.value;
    document.selectGenero.submit();
}

function generoListar(gen){
    document.menuBuscar.criterio.value=3;
    document.menuBuscar.textoBuscar.value=gen.toLocaleString();
    document.getElementById('buscar').click();
}

function estrenoListar(){
    document.menuBuscar.criterio.value=7;
    document.menuBuscar.textoBuscar.value='_';
    document.getElementById('buscar').click();
}

function MasRecientesListar(){
    document.menuBuscar.criterio.value=8;
    document.menuBuscar.textoBuscar.value='_';
    document.getElementById('buscar').click();
}

function MasVistasListar(){
    document.menuBuscar.criterio.value=9;
    document.menuBuscar.textoBuscar.value='_';
    document.getElementById('buscar').click();
}

function MejorVotadasListar(){
    document.menuBuscar.criterio.value=10;
    document.menuBuscar.textoBuscar.value='_';
    document.getElementById('buscar').click();
}

function linkSD(calidad){
    document.vistaVideo.sd.value=calidad;
    document.vistaVideo.submit();
}

function linkHD(cal){
    document.vistaVideo.hd.value=cal;
    document.vistaVideo.submit();
}

function pagina(nroPag,cantFilas,cantlim){
    var inicio,fin;
    if(nroPag==1){
        inicio=1;
        fin=cantlim;
    }else{
        inicio=(cantlim*(nroPag-1))+1;
        fin=cantlim*nroPag;
    }
    for(var pag=1;pag<=cantFilas;pag++){
        var vble=document.getElementById("fila"+pag);
        if(inicio<=pag && pag<=fin){
            vble.style.display="block";
        }else{
            vble.style.display="none";
        }
    }
    for (var p=1;p<=cantFilas;p++){
        var x=document.getElementById("link"+p);
        if (p==nroPag){
            x.style.background="#b8b8b2";
        }else{
            x.style.background="none";
        }
    }
    
}

function setDisplayNoneBlock (idNone,idBlock){
    var v1=document.getElementById(idNone);
    var v2=document.getElementById(idBlock);
    v1.style.display="none";
    v2.style.display="block";    
}


function login (){
    document.log.ingresar.value='true';
    document.log.submit();
}

function marcar1(){
    document.getElementById("voto1").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto2").style.backgroundImage='url(\'styles/images/star_off.png\')';
    document.getElementById("voto3").style.backgroundImage='url(\'styles/images/star_off.png\')';
    document.getElementById("voto4").style.backgroundImage='url(\'styles/images/star_off.png\')';
    document.getElementById("voto5").style.backgroundImage='url(\'styles/images/star_off.png\')';
}
function marcar2(){
    document.getElementById("voto1").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto2").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto3").style.backgroundImage='url(\'styles/images/star_off.png\')';
    document.getElementById("voto4").style.backgroundImage='url(\'styles/images/star_off.png\')';
    document.getElementById("voto5").style.backgroundImage='url(\'styles/images/star_off.png\')';
}
function marcar3(){
    document.getElementById("voto1").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto2").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto3").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto4").style.backgroundImage='url(\'styles/images/star_off.png\')';
    document.getElementById("voto5").style.backgroundImage='url(\'styles/images/star_off.png\')';
}
function marcar4(){
    document.getElementById("voto1").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto2").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto3").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto4").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto5").style.backgroundImage='url(\'styles/images/star_off.png\')';
}
function marcar5(){
    document.getElementById("voto1").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto2").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto3").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto4").style.backgroundImage='url(\'styles/images/star_on.png\')';
    document.getElementById("voto5").style.backgroundImage='url(\'styles/images/star_on.png\')';
}
function desmarcar(){
    getElementById("voto1").style.backgroundImage='url(\'styles/images/star_off.png\')';
    getElementById("voto2").style.backgroundImage='url(\'styles/images/star_off.png\')';
    getElementById("voto3").style.backgroundImage='url(\'styles/images/star_off.png\')';
    getElementById("voto4").style.backgroundImage='url(\'styles/images/star_off.png\')';
    getElementById("voto5").style.backgroundImage='url(\'styles/images/star_off.png\')';
}
function votar(val){
    var e = document.getElementById("votoEstrellas");
    e.innerHTML = " ";
    sessionStorage.setItem("voto",val);
    window.open("voto.jsp", "Votación", 'width=300,height=50');
}

function goFullscreen(id) {
    // Get the element that we want to take into fullscreen mode
    var element = document.getElementById(id);
    
    // These function will not exist in the browsers that don't support fullscreen mode yet, 
    // so we'll have to check to see if they're available before calling them.
    
    if (element.mozRequestFullScreen) {
      // This is how to go into fullscren mode in Firefox
      // Note the "moz" prefix, which is short for Mozilla.
      element.mozRequestFullScreen();
    } else if (element.webkitRequestFullScreen) {
      // This is how to go into fullscreen mode in Chrome and Safari
      // Both of those browsers are based on the Webkit project, hence the same prefix.
      element.webkitRequestFullScreen();
   }
      jwplayer('container').setFullscreen('true');
      jwplayer('container').play('true');
   // Hooray, now we're in fullscreen mode!
  }
  
function actualizarScrollableAjax(idDiv,paginaIr){
     $(document.getElementById(idDiv)).load(paginaIr);
}

function pagina_ajax(idDiv,paginaIr){    
    $(document.getElementById(idDiv)).load(paginaIr);   
}

function cargarHome(){    
    $(document.getElementById('cont')).load("homeListadoPeliculas.jsp");   
}

function cargarListasPeliculas(){    
    $(document.getElementById('cont')).load("ListadoPeliculasAjax.jsp");   
}

function cargarListasSeries(){    
    $(document.getElementById('cont')).load("ListadoSeriesAjax.jsp");   
}

function cargarTV(){    
    $(document.getElementById('cont')).load("TV.jsp");   
}

function cargar_temp(idDiv,paginaIr){  
    document.getElementById(idDiv).innerHTML="Cargando Temporadas...";
    document.getElementById('lista_cap').innerHTML="Seleccione una Tempoarada";
    $(document.getElementById(idDiv)).load(paginaIr);   
}
function cargar_cap(idDiv,paginaIr){    
    document.getElementById(idDiv).innerHTML="Cargando Capitulos...";
    $(document.getElementById(idDiv)).load(paginaIr);   
}

function ver_trailer_pelicula(){
    var l =document.getElementById("linkTrailer");
    var t =document.getElementById("trailer");
    l.style.display.value = 'none';
    t.style.display.value = 'block';
}

function NoBack(){
    history.go(1);
}
/*
function isTouchDevice(){
    try{
        document.createEvent("TouchEvent");
        return true;
    }catch(e){
        return false;
    }
}

function touchScroll(id){
    if(isTouchDevice()){ //if touch events exist...
        var el=document.getElementById(id);
        var scrollStartPos=0;

        document.getElementById(id).addEventListener("touchstart", function(event) {
            scrollStartPos=this.scrollTop+event.touches[0].pageY;
            event.preventDefault();
        },false);

        document.getElementById(id).addEventListener("touchmove", function(event) {
            this.scrollTop=scrollStartPos-event.touches[0].pageY;
            event.preventDefault();
        },false);
    }
}*/

