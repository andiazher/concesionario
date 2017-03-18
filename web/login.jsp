<%-- 
    Document   : login
    Created on : 08-nov-2016, 0:11:31
    Author     : andre
--%>
<%
    String name="";
    try{
        
        if(session.getAttribute("session").equals("true")){
            response.sendRedirect("app.jsp");
        }
        
        if(request.getParameter("logout").equals("true")){
            session.setAttribute("session", "false");
        }
        
    }
    catch(NullPointerException s){ 

    }
    try{
        if(!request.getParameter("validate").equals("null")){
            name =request.getParameter("validate");
        }
    }catch(NullPointerException s){ }
        
    
    
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
	<link rel="apple-touch-icon" sizes="76x76" href="https://www.google.com.co/images/branding/product/ico/googleg_lodp.ico" />
	<link rel="icon" type="image/png" href="img/favicon.ico" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Login App Concesionarios - Sysware Ingenierias - By andiazher Inc</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />

	<!-- Bootstrap core CSS     -->
	<link href="css/bootstrap.min.css" rel="stylesheet" />

	<!--  Material Dashboard CSS    -->
	<link href="css/material-dashboard.css" rel="stylesheet"/>

	<!--  CSS for Demo Purpose, don't include it in your project     -->
	<link href="css/demo.css" rel="stylesheet" />

	<!--     Fonts and icons     -->
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons" />
</head>

<body>
    <nav class="navbar navbar-primary navbar-transparent navbar-absolute">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">Sistema Ordenes de Servicio para Vehículos</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    
                   
                    <li class= " active ">
                        <a href="login.jsp">
                            <i class="material-icons">fingerprint</i>
                            Acceder
                        </a>
                    </li>
                    
                </ul>
            </div>
        </div>
    </nav>


    <div class="wrapper wrapper-full-page">
        <div class="full-page login-page" filter-color="black" data-image="img/login.jpg">
        <!--   you can change the color of the filter page using: data-color="blue | purple | green | orange | red | rose " -->
            <div class="content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                            <form method="post" action="login">
                                <div class="card card-login card-hidden">
                                    <div class="card-header text-center" data-background-color="red">
                                        <h4 class="card-title">Acceso</h4>
                                    </div>
                                    <p class="category text-center">
                                        Ingresar credenciales de la Aplicación
                                    </p>
                                    <div class="card-content">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="material-icons">account_box</i>
                                            </span>

                                            <div class="form-group label-floating">
                                                <label class="control-label">Nombre de Usuario</label>
                                                <input type="text" class="form-control" name="user" autofocus="true" required="true">
                                            </div>
                                        </div>

                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="material-icons">lock_outline</i>
                                            </span>
                                            <div class="form-group label-floating">
                                                <label class="control-label">Contraseña</label>
                                                <input type="password" class="form-control" name="pass" required="true" >
                                            </div>
                                        </div>
                                    </div>
                                    <p class="category text-center text-danger" >
                                        <%=name%>
                                    </p>
                                    <div class="footer text-center">
                                        <button type="submit" class="btn btn-info btn-simple btn-wd btn-lg">Iniciar Sesión</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer">
                <div class="container">
                    <p class=" copyright pull-left"><a href="http://andiazher.com">--</a></p>
                    <p class="copyright pull-right">
                        &copy; <script>document.write(new Date().getFullYear())</script><a href="http://sysware-ingenieria.com"> Sysware Ingenieria</a>
                    </p>
                </div>
            </footer>
        </div>
    </div>
</body>

    <!--   Core JS Files   -->
    <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>
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

    <script type="text/javascript">
        $().ready(function(){
            demo.checkFullPageBackgroundImage();

            setTimeout(function(){
                // after 1000 ms we add the class animated to the login/register card
                $('.card').removeClass('card-hidden');
            }, 0)
        });
    </script>

</html>
