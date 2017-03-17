<%-- 
    Document   : pagarliquidacion
    Created on : 10-mar-2017, 1:22:50
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
        <script src="js/bootstrap-datetimepicker.js"></script>
        <script src="js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
        
    </head>
    <body>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header " data-background-color="orange">
                                    <h4 class="card-title text-center"> PAGO DE COMISIONES</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group label-floating input-group date">
                                                    <label class="control-label">Fecha Inicial</label>
                                                    <input type="text" class="form-control datepicker text-center" value="" id="fecha1" name="fecha1" >
                                                </div>
                                            </div>    
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Fecha Final</label>
                                                    <input type="text" class="form-control text-center" value="" id="fecha2" name="fecha2" onclick="loadCalendar()">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Concesionario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="concesionario" id="concesionario"  onchange="loadtable()">
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
                                                    <th>Liq.</th>
                                                    <th>Fecha</th>
                                                    <th>Concesionario</th>
                                                    <th>Valor</th>
                                                    <th>P</th>
                                                    <th>A</th>
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
                <div id="calendar" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeCalendar()">
                                    <i class="material-icons">clear</i>
                                </button>
                                <h4 class="modal-title">Datepicker</h4>
                            </div>
                            <div class="modal-body">
                                
                            </div>
                            <div class="modal-footer text-center">
                                <button type="button" class="btn btn-info btn-round" data-dismiss="modal" onclick="closeCalendar()">Seleccionar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="pagar" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closePagar()">
                                    <i class="material-icons">clear</i>
                                </button>
                                <h4 class="modal-title" id="titlemodal">Datos de Pago de liquidación </h4>
                            </div>
                            <form action="formPagoLiquidacionAction?action=true" method="post" id="form3">
                                <div class="modal-body">
                                    <input type="hidden" name="id" value="" id="idLiquidacion">
                                    <div class="form-group label-floating">
                                        <label class="control-label" >Fecha Pago</label>
                                        <input type="text" class="form-control text-center" value="" id="fecha3" name="fecha3" placeholder="2017-03-16">
                                    </div>
                                    <div class="form-group label-floating">
                                        <label class="control-label">Forma de Pago</label>
                                        <select class="select-with-transition" data-style="btn btn-default" name="fpago" id="fpago" >
                                            <option selected="" value="EF">EFECTIVO</option>
                                            <option value="TC">TARGETA CREDITO</option>
                                            <option value="TD">TARGETA DEBITO</option>
                                            <option value="PSE">PSE</option>
                                        </select>                                                
                                    </div>
                                    <div class="form-group label-floating">
                                        <label class="control-label" >Valor</label>
                                        <input type="text" class="form-control text-center" value="" id="valorliq" readonly name="valorliq">
                                    </div>
                                    <div class="form-group label-floating">
                                        <label class="control-label" >Observaciones</label>
                                        <input type="text" class="form-control" value="" id="observacion" name="observacion" placeholder="...">
                                    </div>
                                </div>
                                <div class="modal-footer text-center">
                                    <button type="submit" class="btn btn-info btn-round" data-dismiss="modal" id="btnpagar" onclick="closePagar()">Guardar</button>
                                </div>    
                            </form>
                        </div>
                    </div>
                </div>



<script type="text/javascript">

    //function loadCalendar(){

      //  $("#calendar").addClass("in");
      //  $("#calendar").attr("style","display: block");

    //}
    
    function closeCalendar(){
        $("#calendar").removeClass("in");
        $("#calendar").attr("style","display: none");        
    }

    //Pagar Liquidacion
    function loadPagar(id){
        $("#pagar").addClass("in");
        $("#pagar").attr("style","display: block");
        $("#btnpagar").attr("onclick","closePagar("+id+")");
        $("#titlemodal").html('Datos de Pago de liquidación No '+id);
        $("#idLiquidacion").attr("value",id);
        $.post("formPagoLiquidacionAction?action=loadData", {entitie:id }, function(data){
            var formatter = new Intl.NumberFormat('en-US', {
                      style: 'currency',
                      currency: 'USD',
                      minimumFractionDigits: 2,
            });
            $("#valorliq").attr("value",formatter.format(data));

        });
    }
    function closePagar(id){
        $("#pagar").removeClass("in");
        $("#pagar").attr("style","display: none");
    }
    function closePagar(){
        $("#pagar").removeClass("in");
        $("#pagar").attr("style","display: none");
    }

    function loadparams(){
        var f = new Date();
        var anoActual = f.getFullYear();
        var mes= f.getMonth()+1;
        var dia = f.getDate();
        var fecha =anoActual+"-"+mes+"-"+dia;
        $("#fecha1").attr("placeholder",fecha);
        $("#fecha3").attr("value",fecha);

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
        $.post("formPagoLiquidacion", { fi: fi1, ff: ff1, concesionario: concesionario1 }, function(data){
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

    function pagar(id){
        loadPagar(id);
    }
    function anular(id){
        swal(
                      'No se puede anular!',
                      'No se pudo anular la liquidacion '+id+'',
                      'success'
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
    $("#form3").submit(function(){
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    loadtable();
                    swal(
                      'Pago exitoso!',
                      'Se ha pagado la liquidación No '+data,
                      'success'
                    )
                }
            })
            return false;
    });
});
    
</script>