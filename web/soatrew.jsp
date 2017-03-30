<%-- 
    Document   : soatrew
    Created on : 29-mar-2017, 18:39:43
    Author     : andresDiaz
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
                        <form method="post" action="formClientSoatRewAction" id="form">
                        <div class="col-md-12">
                            <div class="card">
                                <div  class="form-horizontal">
                                    <div class="card-header " data-background-color="orange">
                                        <h4 class="card-title">Registro de Cliente y Vehiculo para Adquirir SOAT</h4>
                                        <h4 class="card-title text-center" id="nameCanal"></h4>
                                    </div>
                                    
                                    <div class="card-content">
                                        <input type="hidden" name="valor" id="valor">
                                        <div>
                                            <p><b>DATOS DEL VEHÍCULO</b></p>
                                            <div class="row">
                                                <p class="col-md-1 text-right">PLACA</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="placa" value="" id="placa" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">TIPO VEHÍCULO</p>
                                                <div class="col-md-3">
                                                    <select class="select-with-transition" data-style="btn btn-default" title="Tipo de Vehiculo" data-size="2" name="tipo" id="tipo" required="true" onchange="loadDescrip()"">
                                                    </select>
                                                </div>
                                                <p class="col-md-1 text-right">CLASE VEHÍCULO</p>
                                                <div class="col-md-3">
                                                    <select class="select-with-transition" data-style="btn btn-default" title="Tipo de Vehiculo" data-size="2" name="clase" id="clase" required="true" >
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">MARCA VEHÍCULO</p>
                                                <div class="col-md-3">
                                                    <select class="select-with-transition" data-style="btn btn-default" title="Tipo de Vehiculo" data-size="2" name="marca" id="marca" required="true">
                                                    </select>
                                                </div>
                                                <p class="col-md-1 text-right">MODELO</label>
                                                <div class="col-md-1">
                                                    <select class=" select-with-transition " id="selectyear" name="modelo" required="true">
                                                    </select>
                                                </div>
                                                <p class="col-md-2 text-right"></p>
                                                <div class="col-md-4">
                                                    <!--<input type="text" value="-" id="concesionario" readonly class="form-control"> -->
                                                </div>

                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">CILINDRAJE</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="cilindraje" value="" id="cilindraje" required="true" number="true" onkeypress="return numeros2(event)">
                                                    <script type="text/javascript">
                                                            function numeros2(e){
                                                                var key = window.Event ? e.which : e.keyCode;
                                                                return (key>=48 && key<=57);
                                                            }
                                                    </script>
                                                </div>
                                                <p class="col-md-1 text-right">COLOR</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="color" value="" id="color" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">SERVICIO</p>
                                                <div class="col-md-3">
                                                    <select class="select-with-transition" data-style="btn btn-default" title="" data-size="2" name="servicio" id="servicio" required="true">
                                                        <option disabled="true">--</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">N MOTOR</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="motor" value="" id="motor" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">N CHASIS</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="chasis" value=""  id="chasis" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">N VIN</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="vin" value="" id="vin" required="true" style='text-transform:uppercase;'>
                                                </div>
                                            </div>
                                            <p><b>DATOS DEL PROPIETARIO</b></p>
                                            <div class="row">
                                                <p class="col-md-1 text-right">CEDULA NIT</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="cedula" value="" id="cedula" required="true" style='text-transform:uppercase;' onchange="loadDataClient()" >
                                                </div>
                                                <p class="col-md-1 text-right">PRIMER NOMBRE</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="nombre" value="" id="nombre" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">SEGUNDO NOMBRE</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="snombre" value="" id="snombre" style='text-transform:uppercase;'>
                                                </div>
                                                
                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">PRIMER APELLIDO</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="apellido" value="" id="apellido" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">SEGUNDO APELLIDO</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="sapellido" value="" id="sapellido" style='text-transform:uppercase;'>
                                                </div>
                                                <p class="col-md-1 text-right">DIRECCION</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="direccion" value="" id="direccion" required="true" style='text-transform:uppercase;'>
                                                </div>
                                                
                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">CELULAR</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="celular" value="" id="celular" style='text-transform:uppercase;' onkeypress="return numeros2(event)">
                                                </div>
                                                <p class="col-md-1 text-right">TELEFONO:</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="telefono" value="" id="telefono" style='text-transform:uppercase;' onkeypress="return numeros2(event)">
                                                </div>
                                                <p class="col-md-1 text-right">CORREO</p>
                                                <div class="col-md-3">
                                                    <!--<input type="email" class="form-control" name="correo" value="" placeholder="mail@mail.com" id="correo">-->
                                                    <input type="email" class="form-control" name="correo" value="" id="correo">
                                                </div>
                                            </div>

                                            <p><b>DATOS DEL SERVICIO</b></p>
                                            <div id='loadAprobe'>
                                            </div>
                                            <div class="row">
                                                <p class="col-md-2 text-right">VALOR SERVICIO: </p>
                                                <div class="col-md-10">
                                                    <input type="text" class="form-control text-right" name="total" required="true" id="total" readonly="true" value="$0.00" style="font-size:36px;" >
                                                </div>
                                            </div>
                                            <div class="row">
                                                <p class="col-md-2 text-right">OBSERVACIONES</p>
                                                    <div class="col-md-10">
                                                        <input type="text" class="form-control" name="obser" value="" placeholder="Escribe aqui tus observaciones" id="observaciones" style='text-transform:uppercase;'>
                                                    </div>
                                            </div>
                                        
                                    </div>
                                    <div class="card-footer text-center" id="buttonsent">
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
function loadParamsFormClient(){
    $.post("html/soat.jsp", { }, function(data2){
        $("#loadAprobe").html(data2);
    });
    var menu="none";
    $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
        $("#tipo").append(data);
        loadDescrip();
    });
    loadServicios();
}
function loadDescrip(){
    var menu="clase";
    var n = document.getElementById('tipo').value;
    $.post("ClaseVehiculoClientView", { variable: menu, tipo:n }, function(data){
        $("#clase").html(data);
        loadDescrip2();
    });
}
function loadDescrip2(){
    var menu="marca";
    var n = document.getElementById('clase').value;
    $.post("ClaseVehiculoClientView", { variable: menu, marca:n }, function(data){
        $("#marca").html(data);
    });
}
function loadServicios(){
    var menu="servicios";
    var n = document.getElementById('clase').value;
    $.post("ClaseVehiculoClientView", { variable: menu, marca:n }, function(data){
        $("#servicio").html(data);
    });
}


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
loadParamsFormClient();

function loadDataClient(){
    var n = document.getElementById('cedula').value;
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
        $("#buttonsent").html("");
        $("#progressbar").html("<div class=\"progress-bar progress-bar-primary\" role=\"progressbar\" aria-valuenow=\"1\" aria-valuemin=\"0\" aria-valuemax=\"1\"  id=\"progressbarview\" style=\"width: 45%; position: fixed; height: 4px; \"></div>");
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    $("#progressbarview").css("width","40%");
                    $("body,html").animate({scrollTop : 0}, 500);
                    $("#progressbarview").addClass("progress-bar-success");
                    $("#progressbarview").css("width","100%");
                    $( "#form" ).append(data);
                    $("#progressbar").html("<div></div>");
                    load('<%=session.getAttribute("menu")%>');
                }
            })
            return false;
    });
});
</script>

<script>
function creteYearsAndView(){
    var f = new Date();
    var anoActual = f.getFullYear();
    for(i=anoActual+1; i>=1950; i--){
        var se="";
        if(i==anoActual){
            se="selected";
        }
        $("#selectyear").append("<option value=\""+i+ "\" "+se+">" + i + "</option>");  
    }
}
creteYearsAndView();
</script>