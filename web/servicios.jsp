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
                                <div class="card-header " data-background-color="orange">
                                    <h4 class="card-title text-center"> TODOS LOS SERVICIOS </h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Fecha Inicial</label>
                                                    <input type="text" class="form-control datepicker text-center" value="" id="fecha1" name="fecha1">
                                                </div>
                                            </div>    
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Fecha Final</label>
                                                    <input type="text" class="form-control datepicker text-center" value="" id="fecha2" name="fecha2">
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Orden de Servicio</label>
                                                    <input type="text" class="form-control datepicker text-center" id="os" name="os">
                                                </div>
                                            </div>    
                                            <div class="col-md-4">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Concesionario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="concesionario" id="concesionario"  onchange="">
                                                        <option selected="" value="">--SELECCIONAR--</option>
                                                    </select>                                                
                                                </div>
                                            </div>    
                                            <!--    
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Placa</label>
                                                    <input type="text" class="form-control datepicker text-center" id="placa" name="placa">
                                                </div>
                                            </div>    
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Cliente</label>
                                                    <input type="text" class="form-control datepicker text-center" id="cliente" name="cliente">
                                                </div>
                                            </div>    
                                            -->
                                            <div class="col-md-2">
                                                <button type="submit" class="btn btn-success btn-fill" id="buttonsubmit" onclick="loadtable()">
                                                    <span class="btn-label">
                                                        <i class="material-icons">search</i>
                                                    </span>
                                                    Buscar
                                                </button>
                                            </div>    

                                        </div>
                                    </form>
                                    <div class="table-responsive" id="formViewService">
                                        <h4 class="card-title text-center" id="titleContend"> Cargando Servicios </h4>
                                        <table class="table">
                                            <thead class="">
                                                <th>No</th>
                                                <th>Fecha</th>
                                                <th>Documento</th>
                                                <th>Placa</th>
                                                <th>Servicios</th>
                                                <th>Canal</th>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


<script type="text/javascript">
    function loadparams(){
        var f = new Date();
        var anoActual = f.getFullYear();
        var mes= f.getMonth()+1;
        var dia = f.getDate();
        var fecha =anoActual+"-"+mes+"-"+dia;
        $("#fecha1").attr("value",fecha);

        var f2 = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
        var anoActual2 = f2.getFullYear();
        var mes2= f2.getMonth()+1;
        var dia2 = f2.getDate();
        var fecha2 =anoActual2+"-"+mes2+"-"+dia2;
        $("#fecha2").attr("value",fecha2);
        var menu="concesionarios";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#concesionario").append(data);
        });
        loadtableForm(fecha, fecha2);
    }
    loadparams();

    function loadtableForm(fi1, ff1){
        //var placa1=document.getElementById('placa').value;
        //var cliente1=document.getElementById('cliente').value;
        var concesionario1=document.getElementById('concesionario').value;
        var os1=document.getElementById('os').value;
        //placa:placa1, cliente:cliente1,
        $.post("todosServicios", { fi: fi1, ff: ff1, concesionario: concesionario1, os:os1 }, function(data){
            $("#formViewService").html(data);
        });
    }

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
    function loadtable(){
        loadtableForm(document.getElementById('fecha1').value, document.getElementById('fecha2').value);
    }

$(document).ready(function(){
    $("#form").submit(function(){
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    //console.log("none");
                }
            })
            return false;
    });
});
    
</script>


