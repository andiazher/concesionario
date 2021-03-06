<%-- 
    Document   : dasboard
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--  DataTables.net Plugin    -->
<script src="utils/js/jquery.datatables.js"></script>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <form method="post" action="validatepurpacheService" class="form-horizontal" id="form">
                                <div class="card-header card-header-icon" data-background-color="orange">
                                    <i class="material-icons">assignment</i>
                                </div>
                                <div class="card-content" id="formViewService">
                                    <h4 class="card-title" id="titleContend"> Informes </h4>

                                    <div class="table-responsive">
                                        <table id="datatables" class="table table-striped table-no-bordered table-hover" cellspacing="0" width="100%" style="width:100%">
                                            <thead class="">
                                                <th>Conce</th>
                                                <th>Recep.</th>
                                                <th>Fecha</th>
                                                <th>Serv.</th>
                                                <th>V.Base</th>
                                                <th>Valor</th>
                                                <th>V.Ret</th>
                                                <th>V.Imp</th>
                                            </thead>
                                            <tbody id="serviciostable">
                                                <tr>
                                                    <td>00</td>
                                                    <td>--</td>
                                                    <td>--</td>
                                                    <td>--</td>
                                                    <td>$00</td>
                                                    <td>0%0</td>
                                                    <td>$00</td>
                                                    <td>$00</td>
                                                    <td>$00</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                </form>
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
   
    function loadtableForm(){
        var menu="<%=session.getAttribute("menu")%>";
        $.post("Informes", { variable: menu }, function(data){
            $("#serviciostable").html(data);
        });
        loadDatatable();
    }
    loadtableForm();
    
    
</script>


