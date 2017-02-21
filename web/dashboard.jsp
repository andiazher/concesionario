<%-- 
    Document   : dasboard
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header card-header-icon" data-background-color="orange">
                                    <i class="material-icons">assignment</i>
                                </div>
                                <div class="card-content">
                                    <h4 class="card-title">Servicios Vendidos </h4>

                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="">
                                                <th>Id</th>
                                                <th>Nombre</th>
                                                <th>Placa</th>
                                                <th>Servicio</th>
                                            </thead>
                                            <tbody id="serviciostable">
                                                <tr>
                                                    <td>0000</td>
                                                    <td>Niger</td>
                                                    <td>ABC123</td>
                                                    <td>SOAT</td>
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
    function loadtableForm(){
        $.post("todosServicios", { }, function(data){
            $("#serviciostable").html(data);
        });
    }
    loadtableForm();
</script>
