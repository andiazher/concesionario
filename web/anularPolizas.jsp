<%-- 
    Document   : anularPolizas
    Created on : 23-mar-2017, 1:23:34
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
                                    <h4 class="card-title text-center">ANULACIÓN DE POLIZAS ASISTENCIALES Y DE SEGUROS</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-10">
                                                <div class="form-group label-floating input-group col-md-12">
                                                    <label class="control-label">Identificación del Cliente</label>
                                                    <input type="text" class="form-control text-center" value="" id="identificacion" name="identificacion" placeholder="999999999">
                                                </div>
                                            </div>    

                                            <div class="col-md-2">
                                                <button type="submit" class="btn btn-success btn-fill" id="buttonsubmit" onclick="loadtableForm()">
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
                                            <h4 class="card-title text-center" id="titleContend"> Cargando Polizas, por favor espere </h4>
                                            <table class="table">
                                                <thead class="">
                                                    <th>Poliza</th>
                                                    <th>Fecha</th>
                                                    <th>Identificación</th>
                                                    <th>Servicio</th>
                                                    <th>Valor</th>
                                                    <th>A</th>
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
    
    
    function loadtableForm(){

        var cliente=document.getElementById('identificacion').value;
        $.post("dataPolizasAnuRenView", { identificacion:cliente }, function(data){
            $("#formViewService").html(data);

        });
    }
    loadtableForm();

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