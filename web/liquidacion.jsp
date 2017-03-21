<%-- 
    Document   : liquidacion
    Created on : 06-mar-2017, 22:23:37
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/material.min.js" type="text/javascript"></script>
        <script src="js/bootstrap-datetimepicker.js"></script>
        <script src="js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
        
    </head>
    <body>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header " data-background-color="orange">
                                    <h4 class="card-title text-center"> LIQUIDACIÓN DE COMISIONES</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group label-floating input-group date">
                                                    <label class="control-label">Fecha Inicial</label>
                                                    <input type="text" class="form-control text-center" value="" id="fecha1" name="fecha1" >
                                                </div>
                                                <script type="text/javascript">
                                                    $(function () {
                                                        $('#fecha1').datetimepicker({
                                                            format: "YYYY-MM-DD"
                                                        });
                                                    });
                                                </script>
                                            </div>    
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Fecha Final</label>
                                                    <input type="text" class="form-control text-center" value="" id="fecha2" name="fecha2">
                                                </div>
                                                <script type="text/javascript">
                                                    $(function () {
                                                        $('#fecha2').datetimepicker({
                                                            format: "YYYY-MM-DD"
                                                        });
                                                    });
                                                </script>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Concesionario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="concesionario" id="concesionario"  onchange="loadcanales()">
                                                        <option selected="" value="">--SELECCIONAR--</option>
                                                    </select>                                                
                                                </div>
                                            </div>    
                                            <div class="col-md-3">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Canal</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="canal" id="canal"  onchange="loadtable()">
                                                        <option selected="" value="">--SELECCIONAR--</option>
                                                    </select>                                                
                                                </div>
                                            </div>    
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
                                    <form action="liquidar" method="post" id="form2">
	                                    <div class="table-responsive" id="formViewService">
	                                        <h4 class="card-title text-center" id="titleContend"> Cargando Servicios, por favor espere </h4>
	                                        <table class="table">
	                                            <thead class="">
	                                                <th>Fecha</th>
	                                                <th>Concesionario</th>
	                                                <th>OS</th>
	                                                <th>Servicio</th>
	                                                <th>Comisión</th>
	                                                <th>L</th>
	                                                <th>R</th>
	                                            </thead>
	                                            <tbody>
	                                                <tr>
	                                                    
	                                                </tr>
	                                            </tbody>
	                                        </table>
	                                    </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
<script type="text/javascript">
    function reloadvalue(id){
        swal(
            'En proceso!',
            'Se esta recalculando el valor con el Servicio '+id+'',
            'error'
                    )
    }

    function loadcanales(){
    	var menu="canales";
		var n = document.getElementById('concesionario').value;
		$.post("ClaseVehiculoClientView", { variable: menu, canal:n }, function(data){
	    	$("#canal").html(data);
	    });
	    loadtable();
    }
    
    function loadparams(){
        var f = new Date();
        var anoActual = f.getFullYear();
        var mes= f.getMonth()+1;
        var dia = f.getDate();
        var fecha =anoActual+"-"+mes+"-"+dia;
        $("#fecha1").attr("placeholder",fecha);

        var f2 = new Date();
        var anoActual2 = f2.getFullYear();
        var mes2= f2.getMonth()+1;
        var dia2 = f2.getDate();
        var fecha2 =anoActual2+"-"+mes2+"-"+dia2;
        $("#fecha2").attr("placeholder",fecha2);
        var menu="concesionarios";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#concesionario").append(data);
        });
        loadtableForm("", "");
        //loadtableForm(fecha, fecha2);
    }
    loadparams();

    function loadtableForm(fi1, ff1){
        var concesionario1=document.getElementById('concesionario').value;
        var canal1=document.getElementById('canal').value;
        $.post("formLiquidacion", { fi: fi1, ff: ff1, concesionario: concesionario1, canal:canal1 }, function(data){
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
    function rechazar(id){
        swal(
                      'No se puede rechazar!',
                      'No se pudo rechazar el servicio '+id+'',
                      'error'
                    )
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
    $("#form2").submit(function(){
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                	loadtable();
                	var formatter = new Intl.NumberFormat('en-US', {
		              style: 'currency',
		              currency: 'USD',
		              minimumFractionDigits: 2,
		            });
                    swal(
					  'Se ha liquidado!',
					  'Valor liquidado es '+formatter.format(data),
					  'success'
					)
                }
            })
            return false;
    });
});
    
</script>


