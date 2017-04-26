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
        <script src="utils/js/jquery-ui.min.js" type="text/javascript"></script>
        <script src="utils/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="utils/js/material.min.js" type="text/javascript"></script>
        <script src="utils/js/bootstrap-datetimepicker.js"></script>
        <script src="utils/js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
        
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

    function anular(id,tipo){
        swal({
              title: "Esta seguro?",
              text: "Despues de aceptar no se puede renovar la poliza "+id,
              type: "warning",
              showCancelButton: true,
              confirmButtonColor: "#DD6B55",
              confirmButtonText: "Si, Anular",
              cancelButtonText: "No, Cancelar",
              closeOnConfirm: false,
              closeOnCancel: false
            },
            function(isConfirm){
              if (isConfirm) {
                var name="anular";
                $.post("dataPolizasAnuRenAction", { menu:name, poliza: id, servicio:tipo }, function(data){
                    swal("Anulada!", 'Se ha anulado la poliza '+id+'', "success");
                    loadtableForm();
                });   
              } else {
                swal("Cancelado", "Se ha cancelado la anulación :)", "error");
              }
        });
    }


    function renovar(id, tipo){
        swal({
              title: "Esta seguro?",
              text: "Despues de aceptar no se puede renovar la poliza "+id,
              type: "warning",
              showCancelButton: true,
              confirmButtonColor: "#DD6B55",
              confirmButtonText: "Si, Renovar",
              cancelButtonText: "No, Cancelar",
              closeOnConfirm: false,
              closeOnCancel: false
            },
            function(isConfirm){
              if (isConfirm) {
                if(tipo=="1"){
                    swal({
                      title: "Nuevo Número de Poliza",
                      text: "Por favor ingresar el nuevo numero de Poliza de SOAT",
                      type: "input",
                      showCancelButton: true,
                      closeOnConfirm: false,
                      animation: "slide-from-top",
                      inputPlaceholder: "Write something"
                    },
                    function(inputValue){
                      if (inputValue === false) return false;
                      
                      if (inputValue === "") {
                        swal.showInputError("You need to write something!");
                        return false
                      }
                        $("#progressbar").html("<div class=\"progress-bar progress-bar-primary\" role=\"progressbar\" aria-valuenow=\"1\" aria-valuemin=\"0\" aria-valuemax=\"1\"  id=\"progressbarview\" style=\"width: 45%; position: fixed; height: 4px; \"></div>");  
                        var name="renovar";
                        $.post("dataPolizasAnuRenAction", { menu:name, poliza: id, servicio:tipo, npoliza: inputValue }, function(data){
                            $("#progressbarview").css("width","40%");
                            $("#progressbarview").addClass("progress-bar-success");
                            $("#progressbarview").css("width","100%");
                            $( "#form" ).append(data);
                            $("#progressbar").html("<div></div>");
                            loadtableForm();
                        });    
                        //swal("Nice!", "You wrote: " + inputValue, "success");
                    });
                }
                if(tipo=="2"){
                    swal({
                      title: "Por favor esperar!",
                      text: "Se esta procesando su solicitud....",
                      showConfirmButton: false
                    });
                    var name="renovar";
                    $("#progressbar").html("<div class=\"progress-bar progress-bar-primary\" role=\"progressbar\" aria-valuenow=\"1\" aria-valuemin=\"0\" aria-valuemax=\"1\"  id=\"progressbarview\" style=\"width: 45%; position: fixed; height: 4px; \"></div>");
                    $.post("dataPolizasAnuRenAction", { menu:name, poliza: id, servicio:tipo }, function(data){
                        $("#progressbarview").css("width","40%");
                        $("#progressbarview").addClass("progress-bar-success");
                        $("#progressbarview").css("width","100%");
                        $( "#form" ).append(data);
                        $("#progressbar").html("<div></div>");
                        loadtableForm();
                    });    
                }
                   
              } else {
                swal("Cancelado", "Se ha cancelado la renovación :)", "error");
              }
        });
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