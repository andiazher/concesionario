<%-- 
    Document   : formServicePurpacheTram
    Created on : 11-feb-2017, 12:51:02
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
        <script src="js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
    </head>

    <body>
                                    <h4 class="card-title" id="nameservice">[NOMBRE DEL SERICIO]</h4>
                                    <!--
                                    <div class="col-md-1 col-md-offset-11">
                                        <a name="" href="" id="windowsinit2" style="position:fixed;" class="btn text-right visible-md-display .visible-lg-display" >UP</a>    
                                    </div>
                                    -->
                                    <ul class="nav nav-pills nav-pills-warning">
                                        <li class="active">
                                            <a href="#pill1" data-toggle="tab" onclick="changevalue(2)">TRAMITAR</a>
                                        </li>
                                        <li>
                                            <a href="#pill2" data-toggle="tab" onclick="changevalue(3)">NO TRAMITAR</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <input type="hidden" name="var" value="2" id="vari">
                                        <input type="hidden" name="id" id="id">
                                        <input type="hidden" name="valor" id="valor">
                                        <div>
                                            <p><b>DATOS DEL VEHÍCULO</b></p>
                                            <div class="row">
                                                <p class="col-md-1 text-right">PLACA</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="placa" value="" id="placa" required="true">
                                                </div>
                                                <p class="col-md-1 text-right">TIPO VEHÍCULO</p>
                                                <div class="col-md-3">
                                                    <select class="select-with-transition" data-style="btn btn-default" title="Tipo de Vehiculo" data-size="2" name="tipo" id="tipo" required="true" onchange="loadDescrip()"">
                                                    </select>
                                                </div>
                                                <p class="col-md-1 text-right">CLASE VEHÍCULO</p>
                                                <div class="col-md-3">
                                                    <select class="select-with-transition" data-style="btn btn-default" title="Tipo de Vehiculo" data-size="2" name="clase" id="clase" required="true">
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
                                                    <input type="text" class="form-control" name="color" value="" id="color" required="true">
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
                                                    <input type="text" class="form-control" name="motor" value="" id="motor" required="true">
                                                </div>
                                                <p class="col-md-1 text-right">N CHASIS</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="chasis" value=""  id="chasis" required="true">
                                                </div>
                                                <p class="col-md-1 text-right">N VIN</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="vin" value="" id="vin" required="true">
                                                </div>
                                            </div>
                                            <p><b>DATOS DEL PROPIETARIO</b></p>
                                            <div class="row">
                                                <p class="col-md-1 text-right">CEDULA NIT</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="cedula" value="" id="cedula" required="true">
                                                </div>
                                                <p class="col-md-1 text-right">PRIMER NOMBRE</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="nombre" value="" id="nombre" required="true">
                                                </div>
                                                <p class="col-md-1 text-right">SEGUNDO NOMBRE</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="snombre" value="" id="snombre">
                                                </div>
                                                
                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">PRIMER APELLIDO</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="apellido" value="" id="apellido" required="true">
                                                </div>
                                                <p class="col-md-1 text-right">SEGUNDO APELLIDO</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="sapellido" value="" id="sapellido" >
                                                </div>
                                                <p class="col-md-1 text-right">DIRECCION</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="direccion" value="" id="direccion" required="true">
                                                </div>
                                                
                                            </div>
                                            <div class="row">
                                                <p class="col-md-1 text-right">CELULAR</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="celular" value="" id="celular">
                                                </div>
                                                <p class="col-md-1 text-right">TELEFONO:</p>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" name="telefono" value="" id="telefono">
                                                </div>
                                                <p class="col-md-1 text-right">CORREO</p>
                                                <div class="col-md-3">
                                                    <!--<input type="email" class="form-control" name="correo" value="" placeholder="mail@mail.com" id="correo">-->
                                                    <input type="text" class="form-control" name="correo" value="" id="correo">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane active" id="pill1">
                                            
                                            <p><b>DATOS DEL SERVICIO</b></p>
                                            <div id='loadAprobe'>
                                            </div>
                                            <div class="row">
                                                <p class="col-md-2 text-right">VALOR SERVICIO: </p>
                                                <div class="col-md-10">
                                                    <input type="text" class="form-control text-right" name="total" required="true" id="total" readonly="true" value="$0.00" style="font-size:36px;" >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="pill2">
                                            <DIV>
                                                <br>
                                                <p class="text-center text-dange" style="color:#FF0000">El servicio seleccionado no va a ser tramitado, por favor <b> Guardar</b> o <b>Cancelar</b> la transacción</p>    
                                            </DIV>
                                        </div>
                                        <div class="row">
                                            <p class="col-md-2 text-right">OBSERVACIONES</p>
                                                <div class="col-md-10">
                                                    <input type="text" class="form-control" name="obser" value="" placeholder="Escribe aqui tus observaciones" id="observaciones">
                                                </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group form-button nav-center">
                                                    <label>
                                                        <input type="checkbox" name="optionsave"> Guardar sin cambiar estado
                                                        <span class="checkbox-material"><span class="check"></span></span>
                                                    </label>
                                                <!--<input type="submit" class="btn btn-fill btn-success" value="Guardar" >-->
                                                <button type="submit" class="btn btn-success btn-fill" id="buttonsubmit">
                                                Guardar
                                                </button>
                                                <a class="btn btn-fill btn-default" onclick="load(<%=session.getAttribute("menu")%>)">Cancelar</a>
                                            </div>    
                                        </div>
                                        
                                    </div>
    </body>
<script type="text/javascript">
    function changevalue(id){
        $("#vari").attr( "value", id);
        if(id==2){
            $("#observaciones").removeAttr("required");
            loadParamsFormClient();
        }
        else{
            $("#observaciones").attr("required","true"); 
            $("#loadAprobe").html("NO DATA REQUIRED");
        }
    }
    //changevalue(2);
    function loadParamsFormClient(){
        var idService= <%=request.getParameter("variable")%>;
        $("#id").attr("value",idService);
        //LOAD ADD FORM DATA
        //loadIDServicio(idService);
        //loadPlacaVe(idService);
        //loadTipoVehiculo(idService);
        //loadModeloVehiculo(loadModeloVehiculo);
        $.post("formViewService", { variable: idService }, function(data){
            //console.log(data);
            var cadena = data;
            var valores = JSON.parse(cadena);
            $("#nameservice").html("<b>SERVICIO:</b> "+valores.nombreservicio +" | <b> CONCESIONARIO: </b>"+valores.conce);
            $("#placa").attr("value",valores.placa);
            loadModeloVehiculo(valores.modelo);
            loadTipoVehiculo(valores.tipo);
            loadClaseVehiculo(valores.clase);
            loadMarcaVehiculo(valores.marca);
            $("#cilindraje").attr("value",valores.cilindraje);
            $("#color").attr("value",valores.color);
            loadServicioVehiculo(valores.servicio);
            $("#motor").attr("value",valores.motor);
            $("#chasis").attr("value",valores.chasis);
            $("#vin").attr("value",valores.vin);
            $("#cedula").attr("value",valores.cedula);
            $("#nombre").attr("value",valores.nombre);
            $("#apellido").attr("value",valores.apellido);
            $("#direccion").attr("value",valores.direccion);
            $("#celular").attr("value",valores.celular);
            $("#correo").attr("value",valores.correo);
            //$("#concesionario").attr("value",valores.conce);
            var total = valores.total;
            $("#valor").attr("value",total);
            var formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 2,
            });
            $("#total").attr("value",formatter.format(total));

            if(valores.idServicio==1){
                $.post("html/soat.jsp", { }, function(data2){
                    $("#loadAprobe").html(data2);
                });
            }
            if(valores.idServicio==2){
                $.post("html/matricula.jsp", { }, function(data2){
                    $("#loadAprobe").html(data2);
                });
            }
            else{
                console.log("No retorna id para formulario adicional");
            }

        }); 
        //$("#placa").attr("value",data);  

    }
    loadParamsFormClient();
    function loadServicioVehiculo(tipo){
        for (option in tipo.option) {
            var ls = tipo.option[option];
            if (ls.value!="0") {
                $("#servicio").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
            }
        }
    }
    function loadTipoVehiculo(tipo){
        for (option in tipo.option) {
            var ls = tipo.option[option];
            if (ls.value!="0") {
                $("#tipo").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
            }
        }
    }
    function loadDescrip(){
        var menu="clase";
        var n = document.getElementById('tipo').value;
        $.post("ClaseVehiculoClientView", { variable: menu, tipo:n }, function(data){
            $("#clase").html(data);
            loadDescrip2();
        });
    }
    function loadClaseVehiculo(clase){
        for (option in clase.option) {
            var ls = clase.option[option];
            if (ls.value!="0") {
                $("#clase").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
            }
        }
    }
    function loadMarcaVehiculo(marca){
        for (option in marca.option) {
            var ls = marca.option[option];
            if (ls.value!="0") {
                $("#marca").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
            }
        }
    }
    function loadModeloVehiculo(modelo){
        var f = new Date();
        var anoActual = f.getFullYear();
        for(i=anoActual+1; i>=1950; i--){
            if(modelo==i){
                $("#selectyear").append("<option value=\""+i+ "\" selected>" + i + "</option>");     
            }
            else{
                $("#selectyear").append("<option value=\""+i+ "\" >" + i + "</option>");     
            }
        }
    }
    
    $(document).ready(function(){
        $("#form").submit(function(){
                $.ajax({
                    type: 'POST',
                    url: $(this).attr('action'),
                    data: $(this).serialize(),
                    success: function(data){
                        console.log("Aqui llegue 1");
                        $("body,html").animate({scrollTop : 0}, 500);
                         $( "#form" ).append(data);
                         load('<%=session.getAttribute("menu")%>');
                    }
                })
                return false;
        });
    });
    
</script>

</html>

