<%-- 
    Document   : asistencia
    Created on : 14-mar-2017, 1:06:11
    Author     : andre
--%>
<%
    String idservicio="00";
    try{
        idservicio = request.getParameter("idservicio");
    }catch(NullPointerException s){ }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<body>
                <div class="container-fluid" >
                    <div class="row">
                        <form method="get" action="formClientAsitDentalAction" id="form">
                        <div class="col-md-12">
                            <div class="card">
                                <div  class="form-horizontal">
                                    <div class="card-header " data-background-color="orange">
                                        <h4 class="card-title">Identificación del cliente</h4>
                                        <h4 class="card-title text-center" id="nameCanal"></h4>
                                    </div>
                                    
                                    <div class="card-content">
                                        
                                        <div class="row">
                                            <label class="col-sm-1 label-on-left">Tipo de Identificación</label>
                                            <div class="col-sm-1">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <select class=" select-with-transition " name="ti" required="true">
                                                        <option value="CC">CC</option>
                                                        <option value="CE">CE</option>
                                                        <option value="NIT">NIT</option>
                                                        <option value="NN">NN</option>
                                                        <option value="PAS">PAS</option>
                                                        <option value="SD">SD</option>
                                                        <option value="TI">TI</option>
                                                    </select>
                                                    <span class="help-block">Numero de identificacion de la persona</span>
                                                </div>
                                            </div>
                                            <input type="hidden" name="idservicio" id="idservicio" value="0">
                                            <label class="col-sm-1 label-on-left">Identificación</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="identification" 
                                                    minLength="4" 
                                                    required="true" 
                                                    number="true" 
                                                    placeholder="1234" 
                                                    id="identificacion" 
                                                    value="" 
                                                    onchange="loadDataClient()" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Numero de identificacion de la persona</span>
                                                </div>
                                            </div>
                                            <label class="col-sm-1 label-on-left">P.Nombre</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="nombre" 
                                                    required="true"
                                                    placeholder="" 
                                                    id="nombre" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Nombre de Cliente</span>
                                                </div>
                                            </div>
                                            <label class="col-sm-1 label-on-left">S.Nombre</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="nombres2" 
                                                    placeholder="" 
                                                    id="nombres2" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Nombre de Cliente</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label class="col-sm-1 label-on-left">P.Apellido</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="apellidos" 
                                                    required="true"
                                                    placeholder="" 
                                                    id="apellidos" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Apellidos del Cliente</span>
                                                </div>
                                            </div>
                                            <label class="col-sm-1 label-on-left">S.Apellido</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="apellidos2" 
                                                    placeholder="" 
                                                    id="apellidos2" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Apellidos del Cliente</span>
                                                </div>
                                            </div>
                                        
                                            <label class="col-sm-1 label-on-left">Fecha Nacimiento</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="fecha" 
                                                    minLength="5" 
                                                    required="true"
                                                    placeholder="2017-12-03" 
                                                    id="fecha" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Fecha de Nacimiento</span>
                                                </div>
                                            </div>
                                            <script type="text/javascript">
                                                    $(function () {
                                                        $('#fecha').datetimepicker({
                                                            format: "YYYY-MM-DD"
                                                        });
                                                    });
                                                </script>

                                            <label class="col-sm-1 label-on-left">Plan</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <select class=" select-with-transition " id="plan" name="plan" required="true">
                                                        <option value="0">SELECCIONAR PLAN</option>
                                                    </select>
                                                    <span class="help-block">Plan</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">    
                                            <label class="col-sm-1 label-on-left">Dirección</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="direccion" 
                                                    required="true"
                                                    placeholder="" 
                                                    id="direccion" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Direccion de Residencia</span>
                                                </div>
                                            </div>

                                        
                                            <label class="col-sm-1 label-on-left">Telefono</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="telefono" 
                                                    placeholder="" 
                                                    id="telefono" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Telefono</span>
                                                </div>
                                            </div>
                                        
                                            <label class="col-sm-1 label-on-left">Celular</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="celular" 
                                                    required="true"
                                                    placeholder="" 
                                                    id="celular" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Celular</span>
                                                </div>
                                            </div>
                                            <label class="col-sm-1 label-on-left">Correo Electronico</label>
                                            <div class="col-sm-2">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" 
                                                    class="form-control" 
                                                    name="correo" 
                                                    required="true"
                                                    placeholder="" 
                                                    id="correo" 
                                                    style='text-transform:uppercase;'>
                                                    <span class="help-block">Correo Electronico</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer text-center">
                                        <button type="submit" class="btn btn-success btn-fill" id="buttonsubmit">
                                            <span class="btn-label">
                                                <i class="material-icons">check</i>
                                            </span>
                                            Enviar
                                        </button>
                                    </div>
                                </div> 
                            </div>
                        </div>

                        </form>
                    </div>
                </div>
</body>
    
<script type="">
function loadAsitencias(){
    var variable = "asistenciasclass";
    $.post("formClientAsisDentalView", { param: variable }, function(data){
        $("#plan").html(data);
    });
}
loadAsitencias();

function loaddata(){
    var idservicio = "<%=idservicio%>";
    if(idservicio!="null"){
        $("#idservicio").attr("value", idservicio);
        var variable = "idservice";
        $.post("formClientAsisDentalView", { id:idservicio, param: variable }, function(data){
            $("#identificacion").attr("value", data);
            loadDataClient();
        });
        
    }
}
loaddata(); 


function loadDataClient(){
    var n = document.getElementById('identificacion').value;
    var variable = "client";
    $.post("formClientAsisDentalView", { cliente:n, param: variable }, function(data){
            var valores = JSON.parse(data);
            $("#nombre").attr("value",valores.nombre);
            $("#nombres2").attr("value",valores.snombre);
            $("#apellidos").attr("value",valores.apellido);
            $("#apellidos2").attr("value",valores.sapellido);
            $("#fecha").attr("value",valores.fnacimiento);
            $("#direccion").attr("value",valores.direccion);
            $("#telefono").attr("value",valores.telefono);
            $("#celular").attr("value",valores.celular);
            $("#correo").attr("value",valores.correo);   
    });
}
$(document).ready(function(){
    $("#form").submit(function(){
        $("#buttonsubmit").disabled=true;
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    $("body,html").animate({scrollTop : 0}, 500);
                    $( "#form" ).append(data);
                    load('<%=session.getAttribute("menu")%>');
                }
            })
            return false;
    });
});
</script>
