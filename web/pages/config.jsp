<%-- 
    Document   : config
    Created on : 11-nov-2016, 2:57:17
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <form method="post" class="form-horizontal" id="form">
                    <div class="card-header card-header-icon" data-background-color="orange">
                        <i class="material-icons">assignment</i>
                    </div>
                    <div class="card-content">
                        
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
                    //$tr = $(this).closest('tr');
                    //table.row($tr).remove().draw();
                    //e.preventDefault();
                });

                //Like record
                table.on('click', '.like', function() {
                    alert('You clicked on Like button');
                });

                $('.card .material-datatables label').addClass('form-group');
            });     
        },1000);   
    }
    
   
</script>

<script type="text/javascript">
    function borrar(q){
        var menu="<%=session.getAttribute("menu")%>";
        swal({
              title: "Esta seguro?",
              text: "Deseas borrar la Entidad?",
              type: "warning",
              showCancelButton: true,
              confirmButtonColor: "#DD6B55",
              confirmButtonText: "Si, Editar",
              cancelButtonText: "No, Cancelar",
              closeOnConfirm: false,
              closeOnCancel: false
            },
            function(isConfirm){
                if (isConfirm) {
                    $.post("deleteEntitie", { id: q, variable: menu }, function(data){
                        $("#datatables").append(data);
                        loadTable();
                    });
                }
                else {
                    swal("Cancelado", "Se ha cancelado :)", "error");
                }
            });
    }

    function loadTable(){
        var menu="<%=session.getAttribute("menu")%>";

        $.post("tableEntitieView", { variable: menu }, function(data){
            $(".card-content").html(data);
        });
    }
    loadTable();
    loadDatatable();
    function addForm(id){
        var menu="<%=session.getAttribute("menu")%>";
        var enti=id;
        $.post("formEditableEntidad", { variable: menu, entidad: enti }, function(data){
            $(".card-content").html(data);
        });
        $( "#form" ).append( "<div class=\"card-footer text-center\" id=\"submit\"><button type=\"submit\" class=\"btn btn-success btn-fill\">Guardar</button></div>" );   
        $( "#form" ).attr("action","editEntidadForm?variable="+menu+"&entidad="+enti);
        $("#form").submit(function(){
            $("#submit").remove();
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(data){
                    $("#submit").remove();
                    $( "#form" ).attr("action","#");
                    load(menu);
                    $("body,html").animate({scrollTop : 0}, 500);
                    $("#pick").html(data);
                    swal(
                      'Guardado!',
                      'Se han enviado los datos al servidor!',
                      'success'
                    )
                }
            })
            return false;
        });
    }
    

</script>

