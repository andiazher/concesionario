<%-- 
    Document   : dasboard
    Author     : andre
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="utils/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="utils/js/jquery-ui.min.js" type="text/javascript"></script>
        <script src="utils/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="utils/js/material.min.js" type="text/javascript"></script>
        <script src="utils/js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
    </head>
    <body>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <form method="post" action="validatepurpacheService" class="form-horizontal" id="form">
                                <div class="card-header card-header-icon" data-background-color="orange">
                                    <i class="material-icons">assignment</i>
                                </div>
                                <div class="card-content" id="formViewService">
                                    <h4 class="card-title" id="titleContend"> Cargando Servicios Pendientes </h4>
                                    <a name="" href="" id="windowsinit"></a>    
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="">
                                                <th>No</th>
                                                <th>OS</th>
                                                <th>Placa</th>
                                                <th>Servicio</th>
                                                <th>Valor</th>
                                                <th>Canal</th>
                                                <th>Fecha</th>
                                                <th>Observaciones</th>
                                            </thead>
                                            <tbody id="serviciostable">
                                                <tr>
                                                    
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


<script type="text/javascript">

    function loadtableForm(){
        var menu="<%=session.getAttribute("menu")%>";
        $.post("serviciosPendientes", { variable: menu }, function(data){
            $("#serviciostable").html(data);
        });
    }
    loadtableForm();
    function openViewService(id){
        var service=id;
        var menu="<%=session.getAttribute("menu")%>";
        var idService= service;
        $("#windowsinit").attr("name","OS"+idService);
        $("#windowsinit").attr("href","#OS"+idService);
        $.post("html/formViewServiceHTML.jsp", { variable: service, variablem : menu }, function(data){
            $("#formViewService").html(data);
            $("#windowsinit2").attr("name","OS"+idService);
            $("#windowsinit2").attr("href","#OS"+idService);
        });
    }
    function openViewServiceSpecial(id){
        $.post("asistencia.jsp", { idservicio: id }, function(data){
            $("#contenido").html(data);
        });
    }
    function openViewOrderService(id){
        var service=id;
        var menu="<%=session.getAttribute("menu")%>";
        $.post("getOrderServicie", { variable: service, variablem : menu }, function(data){
            $("#formViewService").html(data);
        });
    }
    
    
    
</script>


