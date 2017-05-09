<%-- 
    Document   : dispersion
    Created on : 9/05/2017, 12:11:28 AM
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parametrización de dispersión</title>
    </head>
    <body>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header " data-background-color="orange">
                                    <h4 class="card-title text-center">PARAMETRIZACIÓN DE DISPERSION PARA RUBROS DE SERVICIOS</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Concesionario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="concesionarios" id="concesionarios"  onchange="loadtable()">
                                                        <option selected="" value="">--TODOS--</option>
                                                    </select>                                                
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Servicios</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="servicios" id="servicios"  onchange="loadtable()">
                                                        <option selected="" value="">--TODOS--</option>
                                                    </select>                                                
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Rubros</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="rubros" id="rubros"  onchange="loadtable()">
                                                        <option selected="" value="">--TODOS--</option>
                                                    </select>                                                
                                                </div>
                                            </div>        
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Formulario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="sform" id="sform"  onchange="loadtable()">
                                                        <option selected="" value="">--TODOS--</option>
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
                                    <form id="form2" method="post" action="formRecepcionPolizasAction#sent">
                                        <div class="table-responsive" id="formViewService">
                                            <h4 class="card-title text-center" id="titleContend"> Cargando Parametros, por favor espere </h4>
                                            <table class="table">
                                                <thead class="">
                                                    <th></th>
                                                    <th>Receptor</th>
                                                    <th>Tipo</th>
                                                    <th>Valor</th>
                                                    <th>Retencion</th>
                                                    <th>Impuestos</th>
                                                    <th>Completar</th>
                                                </thead>
                                                <tbody id="tbody">
                                                    <tr>
                                                        
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="text-center">
                                            <button type="submit" class="btn btn-success">
                                                Guardar
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

<script type="text/javascript">
    function reloadvaluePago(poliza){
        
    }
    
    function loadparams(){
        var menu="concesionarios";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#concesionarios").append(data);
        });
        
    }
    loadparams();

    function loadtableForm(){
        
    }

    function loadtable(){
        loadtableForm();
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
                    $("#form2").append(data);
                    loadtableForm();
                }
            })
            return false;
    });
});
    
</script>