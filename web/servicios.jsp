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
        <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/material.min.js" type="text/javascript"></script>
        <script src="js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
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
                                    <h4 class="card-title" id="titleContend"> Cargando Servicios </h4>
                                    <div>
                                        
                                    </div>
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="">
                                                <th>No</th>
                                                <th>Fecha</th>
                                                <th>Documento</th>
                                                <th>Placa</th>
                                                <th>Servicios</th>
                                                <th>Canal</th>
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
        $.post("todosServicios", { variable: menu }, function(data){
            $("#serviciostable").html(data);
        });
    }
    loadtableForm();
    function openViewOrderService(id){
        var service=id;
        var menu="<%=session.getAttribute("menu")%>";
        $.post("getOrderServicie", { variable: service, variablem : menu }, function(data){
            $("#formViewService").html(data);
        });
    }
    function revertService(id){
        var service=id;
        var menu="<%=session.getAttribute("menu")%>";
        $.post("revertService", { variable: service, variablem : menu }, function(data){
            openViewOrderService(data);
        });   
    }
    
    
</script>


