$(document).ready(function() {
	
	formValidation();
	resetForm();
	showModalImportarRolesDeUsuarios();
	showModalImportarRolesDeGruposFuncionales();
        showModalSelectDeGruposFuncionales();
        
        //Inicializamos roles del usuario
        var campoRoles = $('#roles').length ? (typeof $('#roles').val() === 'object' ? $('#roles').val().toString() : $('#roles').val()) : "";
        var roles = campoRoles.split(",");
        $('#modalSelectRolesFromGroupsRoleId').selectpicker('val', roles);
        
        //Inicializamos grupos funcionales del usuario
        var fg = $('#functionalGroups').length ? $('#functionalGroups').val().split(",") : "";
        $('#modalSelectFunctionalGroups').selectpicker('val', fg);
});

function formValidation() {	
  'use strict'
  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation');
  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {     
	     bootstrapSelectMensajes();
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated')
      }, false)
    });    
}

function resetForm() {	
	$("*[data-action=reset").on("click", function(e) {
            $("input[type=text]").each(function (){
                $(this).attr('value','');
            }
            );

                $(".selectpicker").find('option:selected').removeAttr('selected');
		$(".selectpicker").selectpicker("refresh");
	});
}

function bootstrapSelectMensajes(){
	if($('.bootstrap-select + .invalid-feedback').length>0){		   	
	   	var mensajes= $('.bootstrap-select + .invalid-feedback');
		if($('.bootstrap-select + .invalid-feedback').length==1){
			var mensaje= mensajes[0].outerHTML;
			mensajes.parent().find('.bootstrap-select').append(mensaje);
			mensajes.remove();
		}else{
			if($('.bootstrap-select + .invalid-feedback').length>1){
			   	mensajes.each(function(){		     		
			     	var mensaje= $(this)[0].outerHTML;		     			     	
			     	if(mensaje!='undefined' && mensaje!=undefined){
			     		$(this).parent().find('.bootstrap-select').append(mensaje);
			     		$(this).remove();
			     	}
			   	});
		   	}
		}     	
	}
}

function showModalImportarRolesDeUsuarios() {
	
	$('#import-roles-form').on('submit', function(e){
        e.preventDefault();
        
        if($("select[name='import-users']").val().length > 0) {
        	
        	var url = $("#import-roles-form").attr("action");
        	var selectedUsers = $("select[name='import-users']").val();
        	var currentRoles = $('#roles').val();
        	
        	// Realizar llamada ajax para traer los roles de esos usuarios
        	$.ajax({
                        type: 'GET',
                        url: url + selectedUsers,
                        success: function (data) {

                        //Roles actuales del usuario
                        var rolesImp = Array.from(data.roles, x => x.id);
                        //Roles del usuario seleccionado
                        var rolesToGrabar = $('#roles').val() != '' ? $('#roles').val().split(',').map(Number) : '';

                        //Fusionamos roles actuales con los del usuario seleccionado
                        rolesToGrabar = rolesToGrabar.concat(rolesImp.filter(ele => !rolesToGrabar.includes(ele)));
                        $('#roles').val(rolesToGrabar);
                        
                        //Cargamos roles del usuario en combo
                        $('#modalSelectRolesFromGroupsRoleId').selectpicker('val', rolesToGrabar.toString().split(','));

                        // Use filter and "not" includes to filter the full dataset by the filter dataset's val.
                        let tablaRoles = allRoleList.filter(role => rolesToGrabar.includes(role.id));

                        //Actualizar tabla
                        $('#tableRoles')
                                .DataTable( 
                                  {
                                    data: tablaRoles,
                                    "destroy" : true,
                                    "ordering" : false,
                                    "paging":   false,
                                    "searching" : false,
                                    "info":     false,
                                    "columns":  [
                                        {"data" : "name",
					"defaultContent" : ""},
                                        {"data" : "functionalGroup.name",
					"defaultContent" : ""},
                                    ]
//                                    ,
//                                    "columnsDefs": [ {
//                                        "targets": [0],
//                                        "render": function( row, type, val, meta ) {
//                                            if (type === 'set') {
//                                                row.name = val.name;
//                                                return
//                                            }
//                                        }
//                                    },
//                                    {
//                                        "targets": [1],
//                                        "render": function( row, type, val, meta ) {
//                                            if (type === 'set') {
//                                                row.functionalGroup.name = val.functionalGroup.name;
//                                                return
//                                            }
//                                        }
//                                    }
//                                    ]
                                } );//.columns.adjust();

                        //FunctionalGroups actuales del usuario
                        var fgImp = Array.from(data.functionalGroups, x => x.id);
                        //FunctionalGroups del usuario seleccionado
                        var fgToGrabar = $('#functionalGroups').val() != '' ? $('#functionalGroups').val().split(',').map(Number) : '';
                        //Fusionamos functionalGroups actuales con los del usuario seleccionado
                        fgToGrabar = fgToGrabar.concat(fgImp.filter(ele => !fgToGrabar.includes(ele)));
                        $('#functionalGroups').val(fgToGrabar);
                        
                        //Cargamos grupos funcionales del usuario en combo
                        $('#modalSelectFunctionalGroups').selectpicker('val', fgToGrabar.toString().split(','));
                                             
                        // Use filter and "not" includes to filter the full dataset by the filter dataset's val.
                        let tablaFunctionalGroup = allFunctionalGroupList.filter(fg => fgToGrabar.includes(fg.id));

                        //Actualizar tabla
                        $('#tableFunctionalGroup')
                                .DataTable( 
                                  {
                                    data: tablaFunctionalGroup,
                                    "destroy" : true,
                                    "ordering" : false,
                                    "paging":   false,
                                    "searching" : false,
                                        "info":     false,
                                    "columns":  [
                                        {"data" : "name",
					"defaultContent" : ""}
                                    ]
                                } ).columns.adjust();

                        $("select[name='import-users']").val('default');
                        $("select[name='import-users']").selectpicker("refresh");

                        showNotification("Importar permisos", "Permisos seleccionados correctamente", "success");
                        $('.modalImportRoles').modal('hide');
                        }, 
                        error: function (data) {
                            showNotification("Importar permisos", "No se han podido seleccionar los permisos del usuario", "error");
                            $('.modalImportRoles').modal('hide');
                        }
                    });
        	
        } else {
            showNotification("Importar permisos", "No se ha seleccionado usuario", "info");
            $('.modalImportRoles').modal('hide');
        }
        
	});

}

function showModalImportarRolesDeGruposFuncionales() {
	var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	var tableLanguage = contextPath + "/tableLang/langTable_es.json";

        if($('#tableRoles').length) {
            $('#tableRoles').DataTable( {
                "ordering" : false,
                "paging":   false,
                "searching" : false,
                "info":     false,
                "language" : {
                                "url" : tableLanguage						
                            }
            } );
        }
	$('#select-group-and-roles-form').on('submit', function(e){
        e.preventDefault();
        
        if($("#modalSelectRolesFromGroupsRoleId").val().length > 0) {
        	
        	var selectedRoles = $("#modalSelectRolesFromGroupsRoleId").val();
                var dataTableRoles = $('#modalSelectRolesFromGroupsRoleId')
                        .find("option:selected").map(function() {
                            var row = $(this).text().split(' - ');
                            return [[row[1],row[0]]];
                        });//.join();
        	// Ya tenemos los identificadores de los roles a añadir
        	$('#roles').val(selectedRoles);
		    
                $('#tableRoles')
                        .DataTable( 
                          {
                            data: dataTableRoles,
                            "destroy" : true,
                            "ordering" : false,
                            "paging":   false,
                            "searching" : false,
                                "info":     false
//                            ,
//                            columns: [
//                                { title: "Nombre" }
//                            ]
                        } ).columns.adjust();

                showNotification("Roles", "Roles seleccionados correctamente", "success");
                $('.modalSelectRolesFromGroups').modal('hide');
        } else {
                showNotification("Roles", "No se han seleccionado roles", "info");
                $('#roles').val('');

                var dataTableRoles = [['&nbsp;','&nbsp;']];
                $('#tableRoles')
                        .DataTable( 
                          {
                            data: dataTableRoles,
                            "destroy" : true,
                            "ordering" : false,
                            "paging":   false,
                            "searching" : false,
                            "info":     false
//                            ,
//                            columns: [
//                                { title: "Nombre" }
//                            ]
                        } ).columns.adjust();
                $('.modalSelectRolesFromGroups').modal('hide');
        }
        
	});
}

function showModalSelectDeGruposFuncionales() {
	var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	var tableLanguage = contextPath + "/tableLang/langTable_es.json";

        if($('#tableRoles').length) {
            $('#tableFunctionalGroup').DataTable( {
                "destroy" : true,
                "ordering" : false,
                "paging":   false,
                "searching" : false,
                "info":     false,
                "language" : {
                                "url" : tableLanguage						
                            }
            } );
        }
	$('#form-select-functional-groups').on('submit', function(e){
            e.preventDefault();

            if($("#modalSelectFunctionalGroups").val().length > 0) {

                var selectedRoles = $("#modalSelectFunctionalGroups").val();
                var dataTableFunctionalGroups = $('#modalSelectFunctionalGroups')
                        .find("option:selected").map(function() {
                            return [[$(this).text()]];
                        });//.join();
                //var currentRoles = $('#functionalGroups').val();

                // Ya tenemos los identificadores de los grupos funcionales a añadir
                $('#functionalGroups').val(selectedRoles);
                
                //if($.fn.dataTable.isDataTable("#tableFunctionalGroup")) {
                $('#tableFunctionalGroup')
                        .DataTable( 
                          {
                            data: dataTableFunctionalGroups,
                            "destroy" : true,
                            "ordering" : false,
                            "paging":   false,
                            "searching" : false,
                                "info":     false
//                            ,
//                            columns: [
//                                { title: "Nombre" }
//                            ]
                        } ).columns.adjust();

                showNotification("Grupos funcionales", "Grupos funcionales seleccionados correctamente", "success");
                $('.modalFunctionalGroups').modal('hide');
            } else {
                showNotification("Grupos funcionales", "No se han seleccionado grupos funcionales", "info");
                $('#functionalGroups').val('');

                var dataTableFunctionalGroups = [['&nbsp;']];
                $('#tableFunctionalGroup')
                        .DataTable( 
                          {
                            data: dataTableFunctionalGroups,
                            "destroy" : true,
                            "ordering" : false,
                            "paging":   false,
                            "searching" : false,
                            "info":     false
//                            ,
//                            columns: [
//                                { title: "Nombre" }
//                            ]
                        } ).columns.adjust();
                $('.modalFunctionalGroups').modal('hide');
            }
        
	});
    
}