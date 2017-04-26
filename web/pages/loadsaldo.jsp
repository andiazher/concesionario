<%-- 
    Document   : loadsaldo
    Created on : 16-feb-2017, 2:15:04
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
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header card-header-icon" data-background-color="blue">
                                    <i class="material-icons">contacts</i>
                                </div>
                                <div class="card-content">
                                    <h4 class="card-title">CARGA DE SALDO AL CONCESIONARIO <b id="concesionario">NN</b></h4>
                                    <form class="form-horizontal" action="loadSaldoConcesionario" id="form">
                                        <div class="row">
                                            <label class="col-md-3 label-on-left">Saldo Actual</label>
                                            <div class="col-md-9">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" class="form-control text-center" readonly id="actual" value="0" style="font-size:36px;">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label class="col-md-3 label-on-left">Saldo a Cargar</label>
                                            <div class="col-md-9">
                                                <div class="form-group label-floating is-empty">
                                                    <label class="control-label"></label>
                                                    <input type="text" class="form-control text-center" name="saldo" value="0" style="font-size:36px;" onkeypress="return numeros(event)">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label class="col-md-3"></label>
                                            <div class="col-md-9">
                                                <div class="form-group form-button">
                                                    <button type="submit" class="btn btn-fill btn-success">Cargar Saldo</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

    </body>

<script type="text/javascript">

    function numeros(e){
        var key = window.Event ? e.which : e.keyCode;
        return ((key>=48 && key<=57) );
    }

    function loadData(){
        var id = "000000000000000000";
        var menu="concesionario";

        $.post("ClaseVehiculoClientView", { variable: menu, clase:id }, function(data){
            console.log(data);
            var valores = JSON.parse(data);
            console.log(valores);
            $("#actual").attr("value",valores.valor);
            var formatter = new Intl.NumberFormat('en-US', {
                  style: 'currency',
                  currency: 'USD',
                  minimumFractionDigits: 2,
                });
            $("#actual").attr("value",formatter.format(valores.valor));
            $("#concesionario").html(valores.nombre);
            var min = parseInt(valores.minimo);
            var actual = parseInt(valores.valor);
            if(actual < min){
                $("#actual").attr("style","font-size:36px; color:#FF0000");
            }
        });
    }

loadData();



$(document).ready(function(){
    $("#form").submit(function(){
        $("#buttonsubmit").disabled=true;
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    $("body,html").animate({scrollTop : 0}, 500);
                     swal(
                      'Guardado!',
                      'Se ha cargado el saldo!',
                      'success'
                    )
                    load('<%=session.getAttribute("menu")%>');
                }
            })
            return false;
    });
});
</script>

</html>
