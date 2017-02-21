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
    <p class="col-md-1">SECRETARIA TRANSITO:</p>
    <div class="col-md-4">
        <select class="select-with-transition" data-style="btn btn-default"  title="Single Select" data-size="7" name="secretaria" id="secretaria" required="true" onchange="loadvalue()">
            <option disabled selected="">--</option>
        </select>                                                
    </div>
    <p class="col-md-1 text-right">FACTURA:</p>
    <div class="col-md-2">
        <input type="text" class="form-control" name="factura" required="true" placeholder="000001">
    </div>
    <p class="col-md-1 text-right">FECHA:</p>
    <div class="col-md-2">
        <input type="text" class="form-control text-center" name="fecha"  required="true" placeholder="2017/02/15" id="fecha" onclick="date()">
    </div>
</div>
<div class="row">
    <p class="col-md-1 text-right">GESTORIA:</p>
    <div class="col-md-3">
        <select class="select-with-transition" data-style="btn btn-default"  title="Single Select" data-size="7" name="gestoria" id="gestoria" required="true" onchange="loadvalue()">
            <option disabled selected="">--</option>
        </select>                                                
    </div>
    <p class="col-md-1 text-right">VALOR MATRICLULA:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="matricula" required="true" placeholder="0" onchange="sumarValor()" id="matricula" disabled>
    </div>
    <p class="col-md-1 text-right">VALOR RUNT:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="runt" required="true" placeholder="0" onchange="sumarValor()" id="runt" disabled="true">
    </div>
</div>
<div class="row">
    <p class="col-md-1 text-right">IMPUESTO:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="impuesto" required="true" placeholder="0" onchange="sumarValor()" id="impuesto" value="0" onkeypress="return numeros(event)">
        <a id="link" target="_blank">Liquidar</a>
    </div>
    <p class="col-md-1 text-right">RETE FUENTE:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="retefuente" required="true" placeholder="0" id="retefuente" onchange="sumarValor()" value="0" onkeypress="return numeros(event)">
    </div>

    <p class="col-md-1 text-right">PAPELERIA:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="papelerias" required="true" placeholder="0" onchange="sumarValor()" id="papelerias" value="0" onkeypress="return numeros(event)">
        </div>
    </div>
    

</div>

<div class="row">
    <p class="col-md-1">MENSAJERIA:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="mensajeria" required="true" placeholder="0" id="mensajeria" onchange="sumarValor()" value="0" onkeypress="return numeros(event)">
    </div>
    <p class="col-md-1">OTRAS GESTIONES:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="otros" required="true" placeholder="0" id="otros" onchange="sumarValor()" value="0" onkeypress="return numeros(event)">
        </div>
    </div>
</div>
<div class="row">
    <p class="col-md-1">HONORARIOS:</p>
    <div class="col-md-3">
        <input type="text" class="form-control text-right" name="honorarios" required="true" placeholder="0" id="honorarios" onchange="sumarValor()" value="0" disabled="">
    </div> 
    <p class="col-md-1">H.ANTES IVA:</p>
    <div class="col-md-2">
        <input type="text" class="form-control text-right" placeholder="0" id="honorariosa" onchange="sumarValor()" value="0" disabled="">
    </div> 
    <p class="col-md-1">IVA H.:</p>
    <div class="col-md-2">
        <input type="text" class="form-control text-right" placeholder="0" id="honorariosi" onchange="sumarValor()" value="0" disabled="">
    </div> 
    
</div>


<script type="text/javascript">
    /**
    setTimeout(function() {
        $( function() {
            $( "#fecha" ).datepicker();
        } );    
    }, 1000);
    function date(){
        $( function() {
            $( "#fecha" ).datepicker();
        } );    
    } */
    function loadvalue(){
        var secretaria = document.getElementById('secretaria').value;
        var gestoria2 = document.getElementById('gestoria').value;
        var clase = document.getElementById('clase').value;
        $("#matricula").attr("value","");
        $("#runt").attr("value","");
        $("#link").removeAttr("href");
        $("#honorarios").attr("value","0");
        $("#honorariosa").attr("value","0");
        $("#honorariosi").attr("value","0");
        sumarValor();
        var name = "load";
        $.post("formViewServiceMatricula", { param: name, secretaria: secretaria, gestoria: gestoria2, clase:clase }, function(data){

            var valores = JSON.parse(data);
            console.log(data);
            console.log(valores);
            $("#matricula").attr("value",valores.matricula);
            $("#runt").attr("value",valores.runt);
            $("#link").attr("href",valores.link);
            var honorario = parseInt(valores.honorario);
            var iva = parseInt(valores.iva);
            var impu = (honorario * iva)/100;
            var antes = (honorario-impu);
            $("#honorarios").attr("value",(antes+impu));
            $("#honorariosa").attr("value",antes);
            $("#honorariosi").attr("value",impu);
            sumarValor();
        });

    }
    function numeros(e){
        var key = window.Event ? e.which : e.keyCode;
        return (key>=48 && key<=57);
    }

    function sumarValor(){
        var matricula = parseInt(document.getElementById('matricula').value);
        var runt = parseInt(document.getElementById('runt').value);
        var papelerias = parseInt(document.getElementById('papelerias').value);
        var mensajeria = parseInt(document.getElementById('mensajeria').value);
        var otros = parseInt(document.getElementById('otros').value);
        var impuesto = parseInt(document.getElementById('impuesto').value);
        var retefuente = parseInt(document.getElementById('retefuente').value);
        var honorarios = parseInt(document.getElementById('honorarios').value);
        var suma = matricula+runt+papelerias+mensajeria +retefuente+honorarios+impuesto;
        if (isNaN(suma)) {
            suma=0;
            $("#total").attr("style","font-size:36px; color:#FF0000");
        }
        else{
            $("#total").attr("style","font-size:36px; color:#000000 ");
        }


        $("#valor").attr("value",suma);
        var formatter = new Intl.NumberFormat('en-US', {
              style: 'currency',
              currency: 'USD',
              minimumFractionDigits: 2,
            });
        $("#total").attr("value",formatter.format(suma));
    }

    //LOAD PARAMS 
    loadParamsFormClient();
    function loadParamsFormClient(){
        var f = new Date();
        var anoActual = f.getFullYear();
        var mes= f.getMonth()+1;
        var dia = f.getDate();
        var fecha =anoActual+"/"+mes+"/"+dia;
        $("#fecha").attr("value",fecha);
        var name = "list";
        $.post("formViewServiceMatricula", { param: name }, function(data){
            var valores = JSON.parse(data);
            console.log(data);
            console.log(valores);
            loadSecretarias(valores.secretaria);
            loadGestorias(valores.gestoria);
            $("#papelerias").attr("value",valores.papeleria);
            $("#mensajeria").attr("value",valores.mensajeria);
        });
    }
    function loadSecretarias(secretaria){
        for (option in secretaria.option) {
            var ls = secretaria.option[option];
            if (ls.value!="0") {
                $("#secretaria").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
            }
        }
    }
    function loadGestorias(param){
        for (option in param.option) {
            var ls = param.option[option];
            if (ls.value!="0") {
                $("#gestoria").append("<option value="+ls.value+" "+ls.selected+">"+ls.name+"</option>");    
            }
        }
    }

</script>
<script type="text/javascript">
    $(document).ready(function() {
        md.initSliders()
        demo.initFormExtendedDatetimepickers();
    });
</script>


</html>
