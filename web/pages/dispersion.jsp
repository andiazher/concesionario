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
                                                <input type="hidden" name="variable" value="controldisper">
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
                                    <form method="post" action="formParametrosDispersionV#save" id="form2">
                                        <input type="hidden" name="variable" value="save">";
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
                                    </form>
                                    <div class="text-center" id="buttonAddRow2">
                                        <button type="submit" class="btn btn-success" disabled id="buttonAddRow" onclick="addRow(0,0)">
                                            Agragar Entidad para Dispersar
                                        </button>
                                    </div>
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
            loadtable();
        });
        
    }

    function borrar(q){
        swal({
              title: "Esta seguro?",
              text: "Deseas borrar el parametro?",
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
                    var menu="delete";
                    $.post("formParametrosDispersionV", { variable: menu, id: q }, function(data){
                        $("#tbody").append(data);
                        loadtable();
                    });
                }
                else {
                    swal("Cancelado", "Se ha cancelado :)", "error");
                }
            });
    }
    
    
    function loadparams(){
        var menu="concesionarios";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#concesionarios").append(data);
        });
        var menu2="serviciosr";
        $.post("ClaseVehiculoClientView", { variable: menu2 }, function(data){
            $("#servicios").append(data);
            loadRubros();
        });
        
    }
    loadparams();

    function edit(id){
        //loadtable();
        var menu="rowdispersion";
        $.post("formParametrosDispersionV", { variable: menu, idp:id }, function(data){
            var cadena = data;
            var v = JSON.parse(cadena);
            var content="<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\"receptor\">";
            for(i in v.receptor.option){
                var r = v.receptor.option[i];
                if (r.value!="0") {
                    content+="<option";
                    content+=" value=\""+r.value+"\" "+r.selected+" >" +r.name;
                    content+="</option>";
                }
            }
            content+="</select>";
            $("#"+id+"receptor").html(content);
            content="<input type=\"hidden\" value=\""+id+"\" name=\"id"+id+"\">";
            content+="<input type=\"text\" value=\""+v.valor+"\" name=\"valor\" size=\"7\">";
            content+="<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\"tipo\">";
            content+="<option";
            var selected="";
            var selected2="";
            if(v.tipo==1){
                selected="selected";
            }
            else{
                selected2="selected";   
            }
            content+=" value=\"1\" "+selected+" >%";
            content+="</option>";
            content+="<option";
            content+=" value=\"0\" "+selected2+" >$";
            content+="</option>";
            content+="</select>";
            $("#"+id+"tipo").html(content);
            content="<input class\"form-control\" type=\"text\" name=\"valorR\" value=\""+v.vr+"\" maxlength=\"3\" size=\"3\" max=\"100\" >";
            $("#"+id+"retencion").html(content);
            content="<input class\"form-control\" type=\"text\" name=\"valorI\" value=\""+v.vi+"\" maxlength=\"3\" size=\"3\" max=\"100\">";
            $("#"+id+"impodeclara").html(content);
            $("#"+id+"prim").html("<button class=\"btn btn-primary btn-xs\" type=\"submit\" href=\"#edit\">Guardar</button> <a class=\"btn btn-danger btn-xs\" href=\"#delete\" onclick=\"borrar("+r.id+")\">Borrar</a>");
            $("#buttonAddRow").attr("disabled","");
        });
    }
    function addRow(conce, rubro){
        var menu="receptores";
        $.post("formParametrosDispersionV", { variable: menu}, function(data){
            var cadena = data;
            var v = JSON.parse(cadena);
            var content="<tr>";
            content+="<td>ID</td>";
            content+="<td>";
                content+="<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\"receptor\">";
                for(i in v.receptor.option){
                    var r = v.receptor.option[i];
                    if (r.value!="0") {
                        content+="<option";
                        content+=" value=\""+r.value+"\" "+r.selected+" >" +r.name;
                        content+="</option>";
                    }
                }
                content+="</select>";
            content+="</td>";
            content+="<td>";
                content+="<input type=\"hidden\" value=\"0\" name=\"id0\">";
                content+="<input type=\"text\" value=\"0\" name=\"valor\" size=\"7\">";
                content+="<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\"tipo\">";
                    content+="<option";
                    content+=" value=\"1\">%";
                    content+="</option>";
                    content+="<option";
                    content+=" value=\"0\">$";
                    content+="</option>";
                content+="</select>";
            content+="</td>";
            content+="<td>";
                content+="<input class\"form-control\" type=\"text\" name=\"valorR\" value=\"0\" maxlength=\"3\" size=\"3\" max=\"100\" >";
            content+="</td>";
            content+="<td>";
                content+="<input class\"form-control\" type=\"text\" name=\"valorI\" value=\"0\" maxlength=\"3\" size=\"3\" max=\"100\">";
            content+="</td>";
            content+="<td>";
                content+="<input type=\"hidden\" value=\""+conce+"\" name=\"conce0\">";
                content+="<input type=\"hidden\" value=\""+rubro+"\" name=\"rubro0\">";
                content+="<button class=\"btn btn-success btn-xs\" type=\"submit\" href=\"#edit\">Guardar</button>";
            content+="</td>";
            content+="</tr>";
            $("#tbody").append(content);
            $("#buttonAddRow").attr("disabled","");  
        });

    }
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
                    html+="<td id=\""+r.id+"receptor\">"+r.receptor+"</td>";
                    if(r.tipo=="1"){
                        if(r.valor=="0"){
                            html+="<td class=\"text-center\" id=\""+r.id+"tipo\">% REST</td>";        
                        }
                        else{
                            html+="<td class=\"text-center\" id=\""+r.id+"tipo\">"+r.valor+"%</td>";    
                        }
                    }
                    else{
                        html+="<td class=\"text-right\" id=\""+r.id+"tipo\">"+formatter.format(r.valor)+"</td>";
                    }
                    html+="<td class=\"text-center\" id=\""+r.id+"retencion\">"+r.retencion+"%</td>";
                    html+="<td class=\"text-center\" id=\""+r.id+"impodeclara\">"+r.impdeclara+"%</td>";
                    html+="<td class=\"text-center\" id=\""+r.id+"prim\"><a class=\"btn btn-primary btn-xs\" href=\"#edit\" onclick=\"edit("+r.id+")\">Editar</a> <a class=\"btn btn-danger btn-xs\" href=\"#delete\" onclick=\"borrar("+r.id+")\">Borrar</a></td>";
                    html+="</tr>";
                }
        }

        if(valores.noerror=="0"){
            $("#buttonAddRow").removeAttr("disabled");
            $("#buttonAddRow").attr("onclick","addRow("+valores.conce+","+valores.rubro+")");        
            //$("#buttonAddRow2").html("<button type=\"submit\" class=\"btn btn-success\" id=\"buttonAddRow\" onclick=\"addRow(0,0)\">Agragar Entidad para Dispersar</button>");        
        }
        else{
            $("#buttonAddRow").attr("disabled","");       
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