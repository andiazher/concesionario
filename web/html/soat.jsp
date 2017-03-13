<%-- 
    Document   : soat
    Created on : 29-ene-2017, 23:54:33
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<p class="text-center text-dange" style="color:#FF0000">A este sevicio se le debe calcular el valor. Por favor ingresar los siguientes valores</p> 
<div class="row">
    <p class="col-md-2 text-right">CLASE VEHICULO:</p>
    <div class="col-md-4">
        <select class="select-with-transition" data-style="btn btn-default" data-size="7" name="tiposoat" id="tiposoat" required="true" onchange="loadEspecifico()">
            <option selected disabled>--</option>
        </select>                                                
    </div>
    <p class="col-md-2 text-right">ESPECIF.:</p>
    <div class="col-md-4">
        <select class="select-with-transition" data-style="btn btn-default" data-size="7" name="tiposoate" id="tiposoate" required="true" onchange="loadvalorsoat()">
        </select>                                                
    </div>
</div>   
<div class="row">
    <p class="col-md-2 text-right">ASEGURADORAS:</p>
    <div class="col-md-4">
        <select class="select-with-transition" data-style="btn btn-default"  title="ASEGURADORAS" data-size="7" name="aseguradora" id="aseguradora" required="true">
        </select>                                                
    </div>
    <p class="col-md-2 text-right">POLIZA:</p>
    <div class="col-md-4">
        <input type="text" name="soat" class="form-control" required="true">
    </div>    
</div>


<script type="text/javascript">

    function loadAseguradoras(){
        var menu="aseguradora";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#aseguradora").html(data);
        });
    }
    function loadClaseVehiculo2(){
        var menu="tipo";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#tiposoat").append(data);
        });
        loadEspecifico();
    }
    function loadEspecifico(){
        var id = document.getElementById('tiposoat').value;
        var menu="especifico";

        $.post("ClaseVehiculoClientView", { variable: menu, clase:id }, function(data){
            $("#tiposoate").html("");
            var valores = JSON.parse(data);
            var v = valores.clases;
            for (option in v.option) {
                var ls = v.option[option];
                if (ls.value!="0") {
                    $("#tiposoate").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
                }
            }
            $("#valor").attr("value",valores.valor);
            var formatter = new Intl.NumberFormat('en-US', {
                  style: 'currency',
                  currency: 'USD',
                  minimumFractionDigits: 2,
                });
            $("#total").attr("value",formatter.format(valores.valor));

        });
    }
    function loadvalorsoat(){
        var id = document.getElementById('tiposoate').value;
        var menu="valor";
        console.log("Valor ID: "+id)
        $.post("ClaseVehiculoClientView", { variable: menu, clase:id }, function(data){
            $("#valor").attr("value",data);
            var formatter = new Intl.NumberFormat('en-US', {
                  style: 'currency',
                  currency: 'USD',
                  minimumFractionDigits: 2,
                });
            $("#total").attr("value",formatter.format(data));
        });      
    }
    loadClaseVehiculo2();
    loadAseguradoras();
</script>
</html>
