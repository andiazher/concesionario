<%-- 
    Document   : app
    Created on : 08-nov-2016, 0:13:44
    Author     : andre
--%>

<%
    try{
        if(!session.getAttribute("session").equals("true")){
            response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
        }
    }
    catch(NullPointerException e){
        response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
    }
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!doctype html>
<html lang="en" id="html">
<head>
	<meta charset="utf-8" />
	<link rel="apple-touch-icon" sizes="76x76" href="../../assets/img/apple-icon.png" />
	<link rel="icon" type="image/png" href="img/favicon.ico" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>
	<title>App Concesionarios - Sysware Ingenierias - By andiazher Inc</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />

	<!-- Bootstrap core CSS     -->
	<link href="css/bootstrap.min.css" rel="stylesheet" />

	<!--  Material Dashboard CSS    -->
	<link href="css/material-dashboard.css" rel="stylesheet"/>

	<!--  CSS for Demo Purpose, don't include it in your project     -->
	<link href="css/demo.css" rel="stylesheet" />

	<!--  Fonts and icons  -->
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons" />
</head>

<body onload="load('<%=session.getAttribute("menu")%>')">
	<div class="progress progress-line-info" style=" position: fixed; height: 0px; " id="progressbar" >
		<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="1"  id="progressbarview" style="width: 22%; position: fixed; height: 4px; "></div>
    </div>
	<div class="wrapper">
	    <div class="sidebar" data-active-color="green" data-background-color="black" data-image="img/sidebar-1.jpg">
	    <!--
	        Tip 1: You can change the color of active element of the sidebar using: data-active-color="purple | blue | green | orange | red | rose"
	        Tip 2: you can also add an image using data-image tag
	        Tip 3: you can change the color of the sidebar with data-background-color="white | black"
	    -->

		    <div class="logo">
		        <a href="app.jsp" class="simple-text">
		            Sysware Ing
		        </a>
		    </div>

		    <div class="logo logo-mini">
				<a href="app.jsp" class="simple-text">
					SI
				</a>
			</div>

		    <div class="sidebar-wrapper">
		        <div class="user">
		            <div class="photo">
		                <img src="img/default-avatar.png" />
		            </div>
		            <div class="info">
		                <a data-toggle="collapse" href="#collapseExample" class="collapsed">
                                    <%=session.getAttribute("userName")%>
		                    <b class="caret"></b>
		                </a>
		                <div class="collapse" id="collapseExample">
		                    <ul class="nav">
		                        <li><a href="#profile" onclick="loadProfile()">Mi Perfil</a></li>
		                    </ul>
		                </div>
		            </div>
		        </div>
		        <ul class="nav" id="menu">
		        </ul>
		    </div>
		</div>

	    <div class="main-panel" data-active-color="green">
			<nav class="navbar navbar-transparent navbar-absolute">
			    <div class="container-fluid">
			        <div class="navbar-minimize">
			            <button id="minimizeSidebar" class="btn btn-round btn-white btn-fill btn-just-icon">
			                <i class="material-icons visible-on-sidebar-regular">more_vert</i>
			                <i class="material-icons visible-on-sidebar-mini">view_list</i>
			            </button>
			        </div>
			        <div class="navbar-header">
			            <button type="button" class="navbar-toggle" data-toggle="collapse">
			                <span class="sr-only">Toggle navigation</span>
			                <span class="icon-bar"></span>
			                <span class="icon-bar"></span>
			                <span class="icon-bar"></span>
			            </button>
			            <a class="navbar-brand" href="#" id="title"> Cargando Sistema... Por favor esperar..... </a>
			        </div>
			        <div class="collapse navbar-collapse">
			            <ul class="nav navbar-nav navbar-right">
			                <li>
			                    <a href="login.jsp?logout=true&sid=false"  >
			                       <i class="material-icons">person</i>
			                       <p class="hidden-lg hidden-md">Profile</p>
			                    </a>
			                    <ul class="dropdown-menu">
			                        <li><a href="#" onclick="loadProfile()">Perfil</a></li>
			                        <li><a href="#">Bloquear</a></li>
			                        <li><a href="login.jsp?logout=true">Cerrar Sesión</a></li>
			                    </ul>
			                </li>

			                <li class="separator hidden-lg hidden-md"></li>
			            </ul>
			            <!--
			            <form class="navbar-form navbar-right" role="search" action="#">
			                <div class="form-group form-search is-empty">
			                    <input type="text" class="form-control" placeholder="Buscar" name="q">
			                    <span class="material-input"></span>
			                </div>
			                <button type="submit" class="btn btn-white btn-round btn-just-icon">
			                    <i class="material-icons">search</i><div class="ripple-container"></div>
			                </button>
			            </form>
			            -->
			        </div>
			    </div>
			</nav>

			<div class="content" id="contenido">
			</div>

			<footer class="footer">
			    <div class="container-fluid">
			        <nav class="pull-left">
			            <ul>
			                <li>
			                    <a href="">
			                        Inicio
			                    </a>
			                </li>
			                <li>
			                    <a href="#What+is+andiazher?">
			                        Nosotros
			                    </a>
			                </li>
			                <li>
			                    <a href="#Portafolio-andiazher-Inc">
			                        Portafolio
			                    </a>
			                </li>
			                
			            </ul>
			        </nav>
			        <p class="copyright pull-right">
			            &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://sysware-ingenieria.com">Sysware Ingenieria</a>
			        </p>
			    </div>
			</footer>
		</div>
	</div>
	<!--
	<div class="fixed-plugin">
	    <div class="dropdown show-dropdown">
	        <a href="#" data-toggle="dropdown">
	        <i class="material-icons">web</i>
	        </a>
	        <ul class="dropdown-menu">
				<li class="header-title"> Sidebar Filters</li>
	            <li class="adjustments-line">
	                <a href="javascript:void(0)" class="switch-trigger active-color">
	                    <div class="badge-colors text-center">
							<span class="badge filter badge-purple" data-color="purple"></span>
	                        <span class="badge filter badge-blue" data-color="blue"></span>
	                        <span class="badge filter badge-green" data-color="green"></span>
	                        <span class="badge filter badge-orange" data-color="orange"></span>
	                        <span class="badge filter badge-red" data-color="red"></span>
	                        <span class="badge filter badge-rose active" data-color="rose"></span>
	                    </div>
	                    <div class="clearfix"></div>
	                </a>
	            </li>
	            <li class="header-title">Sidebar Background</li>
	            <li class="adjustments-line">
	                <a href="javascript:void(0)" class="switch-trigger background-color">
	                    <div class="text-center">
							<span class="badge filter badge-white" data-color="white"></span>
	                        <span class="badge filter badge-black active" data-color="black"></span>
	                    </div>
	                    <div class="clearfix"></div>
	                </a>
	            </li>

	            <li class="adjustments-line">
	                <a href="javascript:void(0)" class="switch-trigger">
	                    <p>Sidebar Mini</p>
	                    <div class="togglebutton switch-sidebar-mini">
	                    	<label>
	                        	<input type="checkbox" unchecked="">
	                    	</label>
	                    </div>
	                    <div class="clearfix"></div>
	                </a>
	            </li>

	            <li class="adjustments-line">
	                <a href="javascript:void(0)" class="switch-trigger">
	                    <p>Sidebar Image</p>
	                    <div class="togglebutton switch-sidebar-image">
	                    	<label>
	                        	<input type="checkbox" checked="">
	                    	</label>
	                    </div>
	                    <div class="clearfix"></div>
	                </a>
	            </li>

	        </ul>
	    </div>
	</div>
	-->



</body>

    <!--   Core JS Files   -->
    <script src="js/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/material.min.js" type="text/javascript"></script>
    <script src="js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>

    <!-- Forms Validations Plugin -->
    <script src="js/jquery.validate.min.js"></script>

    <!--  Plugin for Date Time Picker and Full Calendar Plugin-->
    <script src="js/moment.min.js"></script>

    <!--  Charts Plugin -->
    <script src="js/chartist.min.js"></script>

    <!--   Sharrre Library    -->
    <script src="js/jquery.sharrre.js"></script>

    <!--  Plugin for the Wizard -->
    <script src="js/jquery.bootstrap-wizard.js"></script>

    <!--  Notifications Plugin    -->
    <script src="js/bootstrap-notify.js"></script>

    <!-- DateTimePicker Plugin -->
    <script src="js/bootstrap-datetimepicker.js"></script>

    <!-- Vector Map plugin -->
    <script src="js/jquery-jvectormap.js"></script>

    <!-- Sliders Plugin -->
    <script src="js/nouislider.min.js"></script>


    <!-- Select Plugin -->
    <script src="js/jquery.select-bootstrap.js"></script>

    <!--  DataTables.net Plugin    -->
    <script src="js/jquery.datatables.js"></script>

    <!-- Sweet Alert 2 plugin -->
    <script src="js/sweetalert2.js"></script>

    <!--    Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput -->
    <script src="js/jasny-bootstrap.min.js"></script>

    <!--  Full Calendar Plugin    -->
    <script src="js/fullcalendar.min.js"></script>

    <!-- TagsInput Plugin -->
    <script src="js/jquery.tagsinput.js"></script>

    <!-- Material Dashboard javascript methods -->
    <script src="js/material-dashboard.js"></script>

    <!-- Material Dashboard DEMO methods, don't include it in your project! -->
    <script src="js/demo.js"></script>


<script type="">

function load(id){
	var menu=id;
	console.log("load Menu "+menu);
	
	$("#progressbar").html("<div class=\"progress-bar progress-bar-primary\" role=\"progressbar\" aria-valuenow=\"1\" aria-valuemin=\"0\" aria-valuemax=\"1\"  id=\"progressbarview\" style=\"width: 30%; position: fixed; height: 4px; \"></div>");
    $.post("NameFrame", { variable: menu }, function(data){
    	$("#title").html(data);
    	$("#progressbarview").css("width","40%");
    });
    $.post("MenusView", { variable: menu }, function(data){
    	$("#progressbarview").css("width","55%");
    	$("#menu").html(data);
    	$("#progressbarview").css("width","100%");
    });
    //$("#contenido").html("");
    $.post("NamePathMenu", { variable: menu }, function(data){
    	$.post(data+".jsp", { }, function(data2){
    		$("#progressbarview").css("width","70%");
    		$("#contenido").html(data2);
    		$("#progressbarview").addClass("progress-bar-success");
    		$("#progressbarview").css("width","100%");
    		setTimeout(function() {
    			$("#progressbar").html("<div></div>");
    		},1000);	
    	});
    	
    	
    	//
    });
    
    window.scrollTo(0, 0);
    /*
    setTimeout(function() {
    			$.post("login.jsp?logout=true",{validate:"Se ha cerrado la sessión"},function(data){
    				//$("#html").html(data);
			    	setTimeout("location.href='login.jsp?validate=The session is closed'", 500);
    			});
    },(30*60000));
    */
    setTimeout(function(){
    	$(".ps-scrollbar-x-rail").css("bottom"," 0px");
		$(".ps-scrollbar-y-rail").css("top"," 0px");
		$(".ps-scrollbar-y").css("top"," 0px");
    },1000);
    
}
function loadProfile(){
	var menu=null;
	$("#progressbar").html("<div class=\"progress-bar progress-bar-primary\" role=\"progressbar\" aria-valuenow=\"1\" aria-valuemin=\"0\" aria-valuemax=\"1\"  id=\"progressbarview\" style=\"width: 30%; position: fixed; height: 4px; \"></div>");
    $("#title").html("Perfil de Usuario");
   	$("#progressbarview").css("width","40%");
    $.post("MenusView", { variable: menu}, function(data){
    	$("#menu").html(data);
    	$("#progressbarview").css("width","55%");
    });
    $.post("profile.jsp", { }, function(data2){
    	$("#progressbarview").css("width","70%");
    	$("#contenido").html(data2);
    	$("#progressbarview").css("width","100%");
    	$("#progressbarview").addClass("progress-bar-success");
    	setTimeout(function() {
    		$("#progressbar").html("<div></div>");		
    	},900);
    });
    window.scrollTo(0, 0);	
}

</script>
<script type="text/javascript">
    $(document).ready(function() {

        // Javascript method's body can be found in assets/js/demos.js
        demo.initDashboardPageCharts();

        demo.initVectorMap();
    });
</script>
</html>
