<%-- 
    Document   : repconsignaciones
    Created on : 27/04/2017, 01:26:22 AM
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
                                    <h4 class="card-title text-center">RECEPCION DE CONSIGNACIONES EN POLIZAS DE ASISTENCIAS</h4>
                                </div>
                                <div class="card-content">
                                    <form method="post" action="#search" id="form">
                                        <div class="row">
                                            
                                            <div class="col-md-10">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Canal</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="canal" id="canal"  onchange="loadtable()">
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
                                    <form id="form2" method="post" action="#sent">
                                        <div class="table-responsive" id="formViewService">
                                            <h4 class="card-title text-center" id="titleContend"> Cargando Polizas, por favor espere </h4>
                                            <table class="table">
                                                <thead class="">
                                                    <th></th>
                                                    <th>N. Poliza</th>
                                                    <th>Fecha Exp</th>
                                                    <th>Canal</th>
                                                    <th>Cliente</th>
                                                    <th>V.Prima</th>
                                                    <th>V.Consingnar</th>
                                                    <th>Dias</th>
                                                </thead>
                                                <tbody id="tbody">
                                                    <tr>
                                                        
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="text-center">
                                            <button type="submit" class="btn btn-success">
                                                CONSINGNAR
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
        var canal1=document.getElementById('canal').value;
        var menu1="polizas";
        var counter1=0;
        var counter2=0;
        var formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 2,
            });                
        $.post("formRecepcionPolizas", { menu:menu1, canal: canal1}, function(data){
            var cadena = data;
            var valores = JSON.parse(cadena);
            for(i in valores.info.row){
                var r = valores.info.row[i];
                if (r.poliza!="0") {
                    if( $('#'+r.poliza).prop('checked') ) {
                        var c1=parseInt(r.prima);
                        var c2=parseInt(r.pago);
                        counter1=counter1+c1;
                        counter2=counter2+c2
                    }
                }
            }
        $("#prima").html(formatter.format(counter1));
        $("#pago").html(formatter.format(counter2));
        });    
    }
    
    function loadparams(){
        var f = new Date();
        var anoActual = f.getFullYear();
        var mes= f.getMonth();
        var dia = f.getDate();
        var fecha =anoActual+"-"+mes+"-"+dia;
        $("#fecha1").attr("placeholder",fecha);
        $("#fecha1").attr("value",fecha);
        $("#fecha3").attr("value",fecha);

        var f2 = new Date();
        var anoActual2 = f2.getFullYear();
        var mes2= f2.getMonth()+1;
        var dia2 = f2.getDate();
        var fecha2 =anoActual2+"-"+mes2+"-"+dia2;
        $("#fecha2").attr("placeholder",fecha2);
        $("#fecha2").attr("value",fecha2);
        var menu="canalesAll";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#canal").html(data);
        });
        loadtableForm();
        //loadtableForm(fecha, fecha2);
    }
    loadparams();

    function loadtableForm(){
        var canal1=document.getElementById('canal').value;
        var menu1="polizas";
        $.post("formRecepcionPolizas", { menu:menu1, canal: canal1}, function(data){
            var cadena = data;
            var valores = JSON.parse(cadena);
            $("#titleContend").html("<b>"+valores.title+"</b>");
            var formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 2,
            });
            var html="";
            var counter1=0;
            var counter2=0;                
            for(i in valores.info.row){
                var r = valores.info.row[i];
                if (r.poliza!="0") {
                    html+="<tr>";
                    html+="<td><input type=\"checkbox\" name=\""+r.poliza+"\""+ " id=\""+r.poliza+"\" checked "+ "onchange=\"reloadvaluePago('"+r.poliza+"')\"></td>";
                    html+="<td>"+r.poliza+"</td>";
                    html+="<td>"+r.fecha+"</td>";
                    html+="<td>"+r.canal+"</td>";
                    html+="<td><em><b>"+r.cliente+"</b></em></td>";
                    html+="<td class=\"text-right\">"+formatter.format(r.prima)+"</td>";
                    html+="<td class=\"text-right\">"+formatter.format(r.pago)+"</td>";
                    html+="<td>"+r.dias+"</td>";
                    html+="</tr>";
                    var c1=parseInt(r.prima);
                    var c2=parseInt(r.pago);
                    counter1=counter1+c1;
                    counter2=counter2+c2
                }
            }
            html+="<tr  >";
            html+="<td class=\"text-center\" colspan=\"5\" >TOTAL</td>";
            html+="<td class=\"text-right\" colspan=\"1\" id=\"prima\" >"+formatter.format(counter1)+"</td>";
            html+="<td class=\"text-right\" colspan=\"1\" id=\"pago\">"+formatter.format(counter1)+"</td>";
            html+="<td class=\"text-right\" colspan=\"1\" >--</td>";
            html+="</tr>";
            $("#tbody").html(html);
        });
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
});
    
</script>