
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<body>
				<div class="container-fluid" >
					<div class="row">
						<form method="get" action="formClientAction" id="form">
						<div class="col-md-12">
							<div class="card">
								<div  class="form-horizontal">
									<div class="card-header " data-background-color="orange">
										<h4 class="card-title">Identificación del cliente y del vehículo</h4>
										<h4 class="card-title text-center" id="nameCanal"></h4>
									</div>
									
									<div class="card-content">
										
										<div class="row">
											<label class="col-sm-1 label-on-left">Tipo de Identificación</label>
				                        	<div class="col-sm-1">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<select class=" select-with-transition " name="ti" required="true">
                                                        <option value="CC">CC</option>
                                                        <option value="CE">CE</option>
                                                        <option value="NIT">NIT</option>
                                                        <option value="NN">NN</option>
                                                        <option value="PAS">PAS</option>
                                                        <option value="SD">SD</option>
                                                        <option value="TI">TI</option>
                                                    </select>
													<span class="help-block">Numero de identificacion de la persona</span>
												</div>
				                            </div>

											<label class="col-sm-1 label-on-left">Identificación</label>
				                        	<div class="col-sm-2">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<input type="text" 
													class="form-control" 
													name="identification" 
													minLength="4" 
													required="true" 
													number="true" 
													placeholder="1234" 
													id="identificacion" >
													<span class="help-block">Numero de identificacion de la persona</span>
												</div>
				                            </div>
										
											<label class="col-sm-1 label-on-left">Placa</label>
				                        	<div class="col-sm-2">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<input type="text" 
													class="form-control" 
													name="placa" 
													minLength="5" 
													required="true"
													placeholder="ABC123" 
													id="placa" 
													onkeypress="return uppercase(event)">
													<span class="help-block">Placa de vehiculo</span>
												</div>
				                            </div>

				                            <label class="col-sm-1 label-on-left">Modelo</label>
				                        	<div class="col-sm-1">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<select class=" select-with-transition " id="selectyear" name="modelo" required="true">
                                                        <!--<option disabled selected>Seleccionar Modelo</option> -->
                                                    </select>
													<script>
                                                        	function creteYearsAndView(){
																var f = new Date();
																var anoActual = f.getFullYear();

																for(i=anoActual+1; i>=1950; i--){
																	var se="";
																	if(i==anoActual){
																		se="selected";
																	}
																	$("#selectyear").append("<option value=\""+i+ "\" "+se+">" + i + "</option>");	
																}
                                                        	}
                                                        	creteYearsAndView();
                                                    </script>
													<span class="help-block">Modelo</span>
												</div>
				                            </div>
				                            <label class="col-sm-1 label-on-left">Cilindraje</label>
				                        	<div class="col-sm-1">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<input type="text" 
													class="form-control" 
													name="cilindraje" 
													minLength="3" 
													required="true" 
													number="true" 
													placeholder="1250" 
													id="cilindraje" >
													<span class="help-block">Cilindraje</span>
												</div>
				                            </div>
				                        </div>
				                        <div class="row">

				                            <label class="col-sm-1 label-on-left">Tipo de Vehículo</label>
				                        	<div class="col-sm-3">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<select class=" select-with-transition " id="tipo" name="tipo" required="true" onchange="loadDescrip()">
                                                    </select>
												</div>
				                            </div>

				                            <label class="col-sm-1 label-on-left">Clase de Vehículo</label>
				                        	<div class="col-sm-3">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<select class=" select-with-transition " id="clase" name="clase" required="true" onchange="loadDescrip2()">
                                                    </select>
												</div>
				                            </div>
				                            <label class="col-sm-1 label-on-left">Marca</label>
				                        	<div class="col-sm-3">
												<div class="form-group label-floating is-empty">
													<label class="control-label"></label>
													<select class=" select-with-transition " id="marca" name="marca" required="true" onchange="loadDescrip3()">
                                                    </select>
												</div>
				                            </div>

										</div>
									</div>
								</div> 
							</div>
						</div>

						<div class="col-md-12">
							<div class="card">
								<!--<form method="get" action="#" class="form-horizontal">
								-->
									<div class="card-header" data-background-color="blue">
										<h4 class="card-title">Servicios</h4>
									</div>
									<div class="card-content" >
										<div class="row" id="checkbox" >
											
										</div>
									</div>
									<div class="card-footer text-center">
				                        <button type="submit" class="btn btn-success btn-fill" id="buttonsubmit">
				                        	<span class="btn-label">
												<i class="material-icons">check</i>
											</span>
				                        	Enviar
				                        </button>
				                    </div>
								</div>
							</div>
						</form>
					</div>
				</div>
</body>
	
<script type="">
function uppercase(e){
        var key = window.Event ? e.which : e.keyCode;
        console.log(key);
        return (key.toUpperCase());
}
function loadChecks(){
	var menu="none";

    $.post("ServiciosView", { variable: menu }, function(data){
    	$("#checkbox").html(data);
    });
    $.post("CanalUserClientView", { variable: menu }, function(data){
    	$("#nameCanal").html(data);
    });
    $.post("ClaseVehiculoClientView", { variable: menu }, function(data){
    	$("#tipo").append(data);
    	loadDescrip();
    });
}
loadChecks();
function loadDescrip(){
	var menu="clase";
	var n = document.getElementById('tipo').value;
	$.post("ClaseVehiculoClientView", { variable: menu, tipo:n }, function(data){
    	$("#clase").html(data);
    	loadDescrip2();
    });
}
function loadDescrip2(){
	var menu="marca";
	var n = document.getElementById('clase').value;
	$.post("ClaseVehiculoClientView", { variable: menu, marca:n }, function(data){
    	$("#marca").html(data);
    });
}
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
					  'Se ha solicitado los servicios!',
					  'success'
					)
					load('<%=session.getAttribute("menu")%>');
                }
            })
            return false;
    });
});
</script>
