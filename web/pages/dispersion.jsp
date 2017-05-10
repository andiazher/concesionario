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
                                    <form method="post" action="formParametrosDispersionV#search" id="form">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Concesionario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="concesionarios" id="concesionarios"  onchange="loadtable()">
                                                    </select>                                                
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Servicio</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="servicios" id="servicios"  onchange="loadRubros()">
                                                    </select>                                                
                                                </div>
                                            </div>
                                            
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Rubro</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="rubros" id="rubros"  onchange="loadtable()">
                                                        <option selected="" value="">--SELECIONAR--</option>
                                                    </select>                                                
                                                </div>
                                            </div>        
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Formulario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="sform" id="sform"  onchange="loadtable()">
                                                        <option selected="" value="">PREDETER</option>
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
                                    <form id="form2" method="post" action="#sent">
                                        <div class="table-responsive" id="formViewService">
                                            <h4 class="card-title text-center" id="titleContend"> Por favor seleccionar los parmetros de busqueda </h4>
                                            <table class="table">
                                                <thead class="">
                                                    <th></th>
                                                    <th>Receptor</th>
                                                    <th>Valor</th>
                                                    <th>Ret</th>
                                                    <th>Imp</th>
                                                    <th>Action</th>
                                                </thead>
                                                <tbody id="tbody">
                                                    <tr>
                                                        
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="text-center">
                                            <button type="submit" class="btn btn-success" onclick="r()">
                                                Agragar Entidad para Dispersar
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

<script type="text/javascript">
    function loadRubros(){
        var menu="rubrosPorServicio";
        var servi= document.getElementById('servicios').value;
        $.post("formParametrosDispersionV", { variable: menu, servicio: servi }, function(data){
            var cadena = data;
            var valores = JSON.parse(cadena);
            var content="";
            for(i in valores.rubros.option){
                var r = valores.rubros.option[i];
                if (r.value!="0") {
                    var selected="";
                    if(content==""){
                        selected="selected";
                    }
                    content+="<option";
                    content+=" value=\""+r.value+"\" "+selected+" >" +r.name;
                    content+="</option>";
                }
            }
            $("#rubros").html(content);
        });
        loadtable();
    }

    function r(){
        
                    swal(
                    'Error:',
                    'Función no habilitada',
                    'error'
                    );
    }
    
    function loadparams(){
        var menu="concesionarios";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#concesionarios").append(data);
        });
        var menu2="serviciosr";
        $.post("ClaseVehiculoClientView", { variable: menu2 }, function(data){
            $("#servicios").append(data);
        });
        loadRubros();
    }
    loadparams();

    function loadtableForm(data){
        var cadena = data;
        var valores = JSON.parse(cadena);
        $("#titleContend").html(""+valores.title+"");
        var formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 2,
            });
        var html="";
        for(i in valores.info.row){
                var r = valores.info.row[i];
                if (r.id!="0") {
                    html+="<tr>";
                    html+="<td>"+r.id+"</td>";
                    html+="<td>"+r.receptor+"</td>";
                    if(r.tipo=="1"){
                        if(r.valor=="0"){
                            html+="<td class=\"text-center\">% REST</td>";        
                        }
                        else{
                            html+="<td class=\"text-center\">"+r.valor+"%</td>";    
                        }
                    }
                    else{
                        html+="<td class=\"text-right\">"+formatter.format(r.valor)+"</td>";
                    }
                    html+="<td class=\"text-center\">"+r.retencion+"%</td>";
                    html+="<td class=\"text-center\">"+r.impdeclara+"%</td>";
                    html+="<td class=\"text-center\"><button class=\"btn btn-primary btn-xs\" onclick=\"r()\">Editar</button> <button class=\"btn btn-danger btn-xs\" onclick=\"r()\">Borrar</button></td>";
                    html+="</tr>";
                }
            }
        $("#tbody").html(html);
    }

    function loadtable(){
        var menu="controldisper";
        var servi= document.getElementById('servicios').value;
        var conce= document.getElementById('concesionarios').value;
        var rb= document.getElementById('rubros').value;
        var formu= document.getElementById('sform').value;
        $.post("formParametrosDispersionV", { variable: menu, servicio: servi, concesionario: conce, rubro: rb, form: formu }, function(data){
            loadtableForm(data);
        });
        
    }

$(document).ready(function(){
    $("#form").submit(function(){
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    loadtableForm(data);
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
                    //$("#form2").append(data);
                    loadtable();
                }
            })
            return false;
    });
});
    
</script>