<%-- 
    Document   : parametros
    Created on : 21/04/2017, 12:42:29 AM
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
                                    <h4 class="card-title text-center">CAMBIO DE VALORES PARA PARAMETROS</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-10">
                                                <div class="form-group label-floating input-group col-md-12">
                                                    <label class="control-label">Formulario o Parametro</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="formulario" id="formulario" required="true" onchange="loadtableForm()">
                                                        <option disabled="true">-SELECCIONAR FORMULARIO-</option>
                                                    </select>
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
                                    <form action="#" method="post" id="form2">
                                        <div class="table-responsive" id="formViewService">
                                            <h4 class="card-title text-center" id="titleContend"> Cargando Valores, por favor espere </h4>
                                            <table class="table">
                                                <thead class="">
                                                    <th>Id</th>
                                                    <th>Valor1</th>
                                                    <th>Valor2</th>
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
    
    
    function loadForm(){

        var tipo = "formularios";
        $.post("formParametrosView", { tipof:tipo }, function(data){
            $("#formulario").html(data);
        });
        loadtableForm();
    }
    function loadtableForm(){
        var tipo = "valores";
        var form = document.getElementById('formulario').value;
        $.post("formParametrosView", { tipof:tipo, formulario: form }, function(data){
            $("#formViewService").html(data);
        });
    }
    loadForm();
    function editarV1(id){
        editar(id, "1");
    }
    function editarV2(id){
        editar(id, "2");
    }

    function editar(id, tipo){
        swal({
              title: "Esta seguro?",
              text: "Desea cambiar el valor"+tipo+" del parametro?",
              type: "warning",
              showCancelButton: true,
              confirmButtonColor: "#DD6B55",
              confirmButtonText: "Si, Editar",
              cancelButtonText: "No, Cancelar",
              closeOnConfirm: false,
              closeOnCancel: false
            },
            function(isConfirm){
              if (isConfirm) {
                swal({
                      title: "Nuevo valor",
                      text: "Por favor ingresar el nuevo valor"+tipo+" del parametro ID:"+id,
                      type: "input",
                      showCancelButton: true,
                      closeOnConfirm: false,
                      animation: "slide-from-top",
                      inputPlaceholder: "Nuevo valor"
                    },
                    function(inputValue){
                        if (inputValue === false) return false;
                        if (inputValue === "") {
                            swal.showInputError("You need to write something!");
                            return false;
                        }
                        swal({
                          title: "Por favor esperar!",
                          text: "Se esta procesando su solicitud....",
                          showConfirmButton: false
                        });
                        $("#progressbar").html("<div class=\"progress-bar progress-bar-primary\" role=\"progressbar\" aria-valuenow=\"1\" aria-valuemin=\"0\" aria-valuemax=\"1\"  id=\"progressbarview\" style=\"width: 45%; position: fixed; height: 4px; \"></div>");  
                        $.post("formParametrosAction", { idp: id ,parametro:tipo, valor: inputValue }, function(data){
                            $("#progressbarview").css("width","40%");
                            $("#progressbarview").addClass("progress-bar-success");
                            $("#progressbarview").css("width","100%");
                            $( "#form" ).append(data);
                            $("#progressbar").html("<div></div>");
                            loadtableForm();
                        });        
                    });
              } else {
                    swal("Cancelado", "Se ha cancelado la edición del parametro :)", "error");
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
