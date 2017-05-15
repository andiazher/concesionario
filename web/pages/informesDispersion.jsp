<%-- 
    Document   : informesDispersion
    Created on : 14/05/2017, 11:52:32 PM
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header " data-background-color="orange">
                                    <h4 class="card-title text-center">DISPERSIÓN DE VALORES POR RECEPTOR</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group label-floating input-group date">
                                                    <label class="control-label">Fecha Inicial</label>
                                                    <input type="text" class="form-control text-center" value="" id="fecha1" name="fecha1" >
                                                </div>
                                                <!-- SELECCIONAR FECHA DE PAGO-->
                                                <script type="text/javascript">
                                                    $(function () {
                                                        $('#fecha1').datetimepicker({
                                                            format: "YYYY-MM-DD"
                                                        });
                                                    });
                                                </script>
                                            </div>    
                                            <div class="col-md-2">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Fecha Final</label>
                                                    <input type="text" class="form-control text-center" value="" id="fecha2" name="fecha2">
                                                </div>
                                                <!-- SELECCIONAR FECHA DE PAGO-->
                                                <script type="text/javascript">
                                                    $(function () {
                                                        $('#fecha2').datetimepicker({
                                                            format: "YYYY-MM-DD"
                                                        });
                                                    });
                                                </script>
                                            </div>
                                            <div class="col-md-5">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Receptor</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="receptor" id="receptor" onchange="loadtable()">
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
                                        <div class="table-responsive" id="formViewService">
                                            <h4 class="card-title text-center" id="titleContend"> Cargando Polizas, por favor espere </h4>
                                            <table class="table">
                                                <thead class="">
                                                    <th>Conce</th>
                                                    <th>Fecha</th>
                                                    <th>Serv.</th>
                                                    <th>V.Base</th>
                                                    <th>Valor</th>
                                                    <th>V.Ret</th>
                                                    <th>V.Imp</th>
                                                </thead>
                                                <tbody id="tbody">
                                                    <tr>
                                                        
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

<script type="text/javascript">

    
    function loadparams(){
        var f = new Date();
        var anoActual = f.getFullYear();
        var mes= f.getMonth();
        var dia = f.getDate();
        var fecha =anoActual+"-"+mes+"-"+dia;
        $("#fecha1").attr("placeholder",fecha);
        $("#fecha1").attr("value",fecha);

        var f2 = new Date();
        var anoActual2 = f2.getFullYear();
        var mes2= f2.getMonth()+1;
        var dia2 = f2.getDate();
        var fecha2 =anoActual2+"-"+mes2+"-"+dia2;
        $("#fecha2").attr("placeholder",fecha2);
        $("#fecha2").attr("value",fecha2);
        var menu="AllReceptores";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#receptor").html(data);
            loadtable();
        });
        
        //loadtableForm(fecha, fecha2);
    }
    loadparams();

    function loadtableForm(fi1, ff1){
        var canal1=document.getElementById('receptor').value;
        var menu1="getData";
        $.post("InformesDispersionByReceptor", { fi: fi1, ff: ff1, canal: canal1, menu: menu1}, function(data){
            var cadena = data;
            var valores = JSON.parse(cadena);
            $("#titleContend").html("<b>"+valores.title+"</b>");
            var formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 0,
            });
            var html="";
            var counter1=0;
            var counter2=0;                
            for(i in valores.info.row){
                var r = valores.info.row[i];
                if (r.id!="0") {
                    html+="<tr>";
                    html+="<td>"+r.conce+"</td>";
                    //html+="<td>"+r.receptor+"</td>";
                    html+="<td>"+r.fecha+"</td>";
                    html+="<td>"+r.servicio+"/"+r.rubro+"</td>";
                    html+="<td class=\"text-right\">"+formatter.format(r.base)+"</td>";
                    if(r.tipo=="1"){
                        html+="<td class=\"text-right\">("+r.valorprog+"%)<b>"+formatter.format(r.valorpag)+"</b></td>";    
                    }
                    else{
                        html+="<td class=\"text-right\"><b>"+formatter.format(r.valorpag)+"</b></td>";    
                    }
                    if(r.pret!="0"){
                        html+="<td class=\"text-right\">("+r.pret+"%)"+formatter.format(r.vrent)+"</td>";    
                    }
                    else{
                        html+="<td class=\"text-right\">--</td>";    
                    }
                    if(r.pimp!="0"){
                        html+="<td class=\"text-right\">("+r.pimp+"%)"+formatter.format(r.vimp)+"</td>";    
                    }
                    else{
                        html+="<td class=\"text-right\">--</td>";    
                    }
                    html+="</tr>";
                    var c1=parseInt(r.base);
                    var c2=parseInt(r.valorpag);
                    counter1=counter1+c1;
                    counter2=counter2+c2
                }
            }
            html+="<tr  >";
            html+="<td class=\"text-center\" colspan=\"3\" >TOTAL</td>";
            html+="<td class=\"text-right\" colspan=\"1\" id=\"prima\" >"+formatter.format(counter1)+"</td>";
            html+="<td class=\"text-right\" colspan=\"1\" id=\"pago\">"+formatter.format(counter2)+"</td>";
            html+="<td class=\"text-right\" colspan=\"1\" >--</td>";
            html+="<td class=\"text-right\" colspan=\"1\" >--</td>";
            html+="</tr>";
            $("#tbody").html(html);
        });
    }

    function loadtable(){
        loadtableForm(document.getElementById('fecha1').value, document.getElementById('fecha2').value);
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