<%-- 
    Document   : movbolsa
    Created on : 01-mar-2017, 0:08:00
    Author     : andre
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--  DataTables.net Plugin    -->
<script src="js/jquery.datatables.js"></script>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header card-header-icon" data-background-color="orange">
                                    <i class="material-icons">assignment</i>
                                </div>
                                <div class="card-content" id="formViewService">
                                    <form method="post" action="#search" id="form">
                                        <h4 class="card-title" id="titleContend"> Registro Movimientos en Bolsa 
                                            <a href="#download"> <i class="material-icons"> file_download</i></a> 
                                        </h4>
                                        <div class="row">
                                            <div class="col-sm-offset-3 col-md-4">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Concesionario</label>
                                                    <select class="select-with-transition" data-style="btn btn-default" name="concesionario" id="concesionario"  onchange="">
                                                        <option selected="" value="">--SELECCIONAR--</option>
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
                                    <div class="table-responsive">
                                        <table  class="table table-striped table-no-bordered table-hover " cellspacing="0" width="100%" style="width:100%">
                                            <thead class="">
                                                <th>FECHA</th>
                                                <th>OS</th>
                                                <th>SERVICIO</th>
                                                <th>+/-</th>
                                                <th>VALOR</th>
                                                <th>SALDO</th>
                                            </thead>
                                            <tbody id="serviciostable">
                                                <tr>
                                                    <td>00</td>
                                                    <td>--</td>
                                                    <td>--</td>
                                                    <td>+</td>
                                                    <td>$00</td>
                                                    <td>$00</td>
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

    function loadDatatable(){
        setTimeout(function() {
             $(document).ready(function() {
                $('#datatables').DataTable({
                    "pagingType": "full_numbers",
                    "lengthMenu": [
                        [10, 25, 50, -1],
                        [10, 25, 50, "All"]
                    ],
                    responsive: true,
                    language: {
                        search: "_INPUT_",
                        searchPlaceholder: "Buscar",
                    }

                });


                var table = $('#datatables').DataTable();

                // Edit record
                table.on('click', '.edit', function() {
                    $tr = $(this).closest('tr');

                    var data = table.row($tr).data();
                    alert('You press on Row: ' + data[0] + ' ' + data[1] + ' ' + data[2] + '\'s row.');
                });

                // Delete a record
                table.on('click', '.remove', function(e) {
                    $tr = $(this).closest('tr');
                    table.row($tr).remove().draw();
                    e.preventDefault();
                });

                //Like record
                table.on('click', '.like', function() {
                    alert('You clicked on Like button');
                });

                $('.card .material-datatables label').addClass('form-group');
            });     
        },500);   
    }
    
   
</script>
<script type="text/javascript">
function loadparams(){
        var menu="concesionarios";
        $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
            $("#concesionario").append(data);
        });
        loadtableForm();
    }
    loadparams();


    function loadtableForm(){
        var menu="<%=session.getAttribute("menu")%>";
        var concesionario1=document.getElementById('concesionario').value;
        $.post("Informes", { variable: menu, concesionario: concesionario1 }, function(data){
            $("#serviciostable").html(data);
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


