$(document).ready(function() {
	
	$('*[data-action=addParent]').on("click", addParent);
	$('*[data-action=addChildren]').on("click", addChildren);
	$('*[data-action=showEditPage]').on("click", showEditPage);
	
	initMenuManagePage();
	
});

function resetFormPage() {
	$("#formPage").trigger("reset");
	$("#formPage #id").val('');
	$("#formPage #idParent").val('');
	$("#formPage .selectpicker").val('default');
	$("#formPage .selectpicker").selectpicker("refresh");
	$("#formPage *[name='navTab']").prop('checked', true);
	$("#formPage *[name='navTab']").val('true');
	$("#formPage").removeClass("was-validated");
}

function initMenuTree() {
	
	var url = $("#treeBasic").attr("data-url");
	
	$.ajax({
        async: true,
        type: "GET",
        url: url,
        dataType: "json",
        success: function (data) {
            createJSTree(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
        	showNotification("Menú", "No se ha podido recuperar el menú", "error");
        }
    });
	
}

function updateMenuTree() {
	
	var url = $("#treeBasic").attr("data-url");
	
	$.ajax({
        async: true,
        type: "GET",
        url: url,
        dataType: "json",
        success: function (data) {
            updateJSTree(data);
            $('#infoPageEdition').removeClass('d-none');
        	$('#formPage').addClass('d-none');
        },
        error: function (xhr, ajaxOptions, thrownError) {
        	showNotification("Menú", "No se ha podido recuperar el menú", "error");
        }
    });
	
}

function initMenuManagePage() {
	
	'use strict';
	
	initMenuTree();
	
	$("#formPage").submit(function(e) {

		event.preventDefault();
        event.stopPropagation();
        var _this = $(this)[0];
        
        // Si no hay errores
		if (_this.checkValidity()) {
			
			var form = {};
			var fieldsJsonArray = [];
			
			$.each($("#formPage").serializeArray(), function(i, field) {
				if (form[field.name] != "_csrf") {
					form[field.name] = field.value || "";
				}
			});
			
			//incluye checkbox
			
			$.each($("#formPage input:checkbox").toArray(), function(i, field) {
				form[field.name] = $("#formPage *[name='"+field.name + "']").get(0).checked;
			});

			
			// Debemos recorrer todos los campos del formulario, y para cada uno, generar el json
			_.map(form, function(value, key) {

				var attrArray = key.split(".");
				var map = createMapFromArray({}, attrArray, value);
				fieldsJsonArray.push(map);
				
			});
			
			// Una vez generado el array de json, debemos mergearlos
			var mergedFields = mergeJsonArrays(fieldsJsonArray);
			
			// Tenemos que ajustar los roles al formato adecuado
			var rolesForm = $("#roles").val();
			var rolesDto = [];
			rolesForm.forEach(function(item) {
				rolesDto.push(item)
			});
			
			mergedFields.roles = rolesDto;
			
			$.ajax({
		        async: true,
		        type: "POST",
		        url: $("#formPage").attr("data-context")+"pages",
		        data: JSON.stringify(mergedFields),
		        dataType: 'json',
		        contentType: "application/json; charset=utf-8",
		        success: function (data) {
		        	
		            // Mostrar notificación
		        	showNotification('Menú', 'Página creada correctamente', 'success');
		            
		            // recargar el menu
		            
		            // Ocultar el formulario
		        	
		        	// Ocultar los errores
		        	$("#error-ajax ul").html("");
		        	$("#error-ajax").addClass("d-none");
		        	
		        	//  Recargamos el menú
		        	updateMenuTree();
		        	
		        	$('#infoPageEdition').removeClass('d-none');
		        	$('#formPage').addClass('d-none');
		            
		        },
		        error: function (xhr, ajaxOptions, thrownError) {
		        	
		        	var li = '<li class="d-flex align-items-center"><span class="material-icons text-primary">clear</span><span class="ms-2">{error_text}</span></li>';
		        	var errors = "";
		        	if(xhr.responseJSON !=null && xhr.responseJSON.errors != null) {
		        		xhr.responseJSON.errors.forEach(function(error) {
		        			errors += li.replace("{error_text}", error);
		        		});
		        	}
		        	
		        	$("#error-ajax ul").html(errors);
		        	$("#error-ajax").removeClass("d-none");
		        	
		        	// Mostrar notificación
		        	showNotification('Menú', 'No se ha podido crear la nueva página', 'error');
		        	
		        }
		    });
	          
	    }
		
		// Marcamos el formulario como validado
		_this.classList.add('was-validated');
	        
	});
	
}

function createJSTree(data) {
	
	$('#treeBasic').jstree({
		'core': {    
		  'themes': {				
				'responsive': false
			  },
			'check_callback' : function (operation, node, node_parent, node_position, more) {
	            if (operation == 'move_node' && node.parent != node_parent.id) {
	            	if(node_parent.children.length > 0) {
	            		return true;
	            	} else {
	            		return false;
	            	}
	            }

	            return true;
	        },  
			'data': data
		},	
		'types' : {
			'default' : {
				'icon' : 'jstree-file'
			},
			'file' : {
				'icon' : 'jstree-file'
			}
		},
		'search': {
			'case_insensitive': true,
			'show_only_matches' : true,		
			'search_callback': function (str, node) {             
	            var tags=String(node.a_attr["data-tag"]);
				tags=tags.toLocaleLowerCase();
				str=str.toLowerCase(); 
	            if(tags!=undefined){                                                
	                var posicion = tags.indexOf(str);
	                if ( posicion === -1 ) {                
	                    return false;
	                }else{
	                    return true;
	                }
	            }else{return false;}
          	},
         },
		'plugins': ['search', 'types', 'dnd']
	}).on('search.jstree', function (nodes, str, res) {
		if (str.nodes.length===0) {
			$('#treeBasic').jstree(true).hide_all();
		}
	}).on('changed.jstree', function (e, data) {
		showEditPage();
	}).on('move_node.jstree', function (e, data) {

		console.log('Se mueve ....');

		var nodeId   = (data.node.id); // element id
		var inst = $('#treeBasic').jstree(true),
		node = inst.get_node(nodeId),
		parent = inst.get_node(node.parent);
		reOrderPage(nodeId, data.parent, data.position, data.old_parent, data.old_position);	


	})
	
	
	$('#treeBasic_search').keyup(function(){
		$('#treeBasic').jstree(true).show_all();
		$('#treeBasic').jstree('search', $(this).val());
	});

}

function updateJSTree(data) {
	$('#treeBasic').jstree(true).settings.core.data = data;
	$('#treeBasic').jstree(true).refresh();
}

function addParent(){	
	resetFormPage();
	
	$('#treeBasic').jstree(true).refresh(false, true);
	
	$('#infoPageEdition').addClass('d-none');
	$('#formPage').removeClass('d-none');		
	cambiarValorPadre('','');
	$('#parentPage').removeClass('d-flex');
	$('#parentPage').addClass('d-none');
	$("button[data-action='delete']").addClass('d-none');
	$("button[data-action='save']").addClass('ms-auto');
	$(this).blur();
}

function cambiarValorPadre(idParent,nameParent){
	$('#parentPage #nameParent').val(nameParent);
	$('#parentPage #idParent').val(idParent);
}

function addChildren(){				
	var idParent=$('#treeBasic').find('.jstree-clicked').attr('data-id');
	var nameParent=$('#treeBasic').find('.jstree-clicked').text();
	if(idParent){
		
		resetFormPage();
		
		$("button[data-action='delete']").addClass('d-none');
		$("button[data-action='save']").addClass('ms-auto');
		$('#infoPageEdition').addClass('d-none');
		$('#formPage').removeClass('d-none');
		cambiarValorPadre(idParent,nameParent);
		$('#parentPage').addClass('d-flex');
		$('#parentPage').removeClass('d-none');
		$(this).blur();
	}else{
		showNotification('Menú', 'Es obligatorio seleccionar un elemento del menú para utilizarlo de padre', 'info');
	}
}

function showEditPage(){
	$("#formPage").removeClass("was-validated");
	var id=$('#treeBasic').find('.jstree-clicked').attr('data-id');
	
	if(id){
		var name=$('#treeBasic').find('.jstree-clicked').text();
		
		
		// realizar llamada ajax para recuperar la información de la página
		$.ajax({
	        async: true,
	        type: "GET",
	        url: $("#formPage").attr("data-context") + "pages/"+id,
	        success: function (data) {
	        	
	            // JSON con los nombes del formulario
	            var fieldNames = $("#formPage").serializeArray();
	            fieldNames.push({"name": "roles"});
	            
	            fieldNames.forEach(function(item) {
	            	var names = item.name.split(".");
	            	var itemValue = data;
	            	names.forEach(function(item) {
	            		itemValue = itemValue[item]
		            });
	            	$("#formPage *[name='"+item.name + "']").val(itemValue);

	            });
	            
	            $('#formPage input:checkbox').each(function(i, item) {
	            	var itemValue =  data[item.name];
	            	$("#formPage *[name='"+item.name + "']").prop('checked', itemValue);
	            	$("#formPage *[name='"+item.name + "']").val(itemValue);
	            });
	            
	            $(".selectpicker").selectpicker("refresh");
	            
	            // Preparamos el botón eliminar
	            $("#formPage button[data-action=delete]").attr("data-id", id);
				$("#formPage button[data-action=delete]").attr("data-url", $("#formPage").attr("data-context")+"pages/delete/"+id);
	            
	            $("button[data-action='delete']").removeClass('d-none');
	    		$("button[data-action='delete']").addClass('ms-auto');
	    		$("button[data-action='save']").removeClass('ms-auto');
	    		$('#parentPage').removeClass('d-flex');	    		
	    		$('#parentPage').addClass('d-none');
	    		$('#infoPageEdition').addClass('d-none');
	    		$('#formPage').removeClass('d-none');	    			    		
	        },
	        error: function (xhr, ajaxOptions, thrownError) {	        	  	
	        	// Mostrar notificación
	        	showNotification('Menú', 'No ha sido podido recuperar la información de la página', 'error');
	        	
	        }
	    });

	} else{
		showNotification('Menú', 'Es obligatorio seleccionar un elemento del menú antes de ver su configuración', 'info');
	}
	$(this).blur();
}

function createMapFromArray(map, array, value) {
	
	var newMap = {};
	
	if(array != null && array.length > 0) {
		
		var last = array[array.length-1];
		
		if(_.isEmpty(map)) {
			newMap[last] = value;
		} else {
			newMap[last] = Object.assign({}, map);
		}
		
		if(array.length > 1) {
			array.pop();
			return createMapFromArray(newMap, array);
		}
	}
	
	return newMap;
}

function mergeJsonArrays(jsonArrays) {
	
	var mergeRequest = [{}];
	
	_.forEach(jsonArrays, function(json){
		mergeRequest.push(json);
	});
	
	var args = _.flatten(mergeRequest);
	
	return _.merge.apply(_, args);
	
}

function reOrderPage(nodeId, parent, position, parentold, positionold){
	
	if(nodeId != null && parent != null && position != null 
			&& parentold != null && positionold != null){
		
		if (parent == '#'){
			parent = 0;
		}
		
		if (parentold == '#'){
			parentold = 0;
		}
		
		var formJson = {id: nodeId, idParent: parent, orden: position, idParentOld: parentold, ordenOld: positionold};
		
		$.ajax({
	        async: true,
	        type: "PATCH",
	        url: $("#formPage").attr("data-context")+"pages",
	        data: JSON.stringify(formJson),
	        dataType: 'json',
	        contentType: "application/json; charset=utf-8",
	        success: function (data) {
	        	
	            // Mostrar notificación
	        	showNotification('Menú', 'Orden correctamente cambiado', 'success');
	        	updateMenuTree();
	        	// Ocultar los errores
	        	$("#error-ajax ul").html("");
	        	$("#error-ajax").addClass("d-none");
	            
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	        	
	        	var li = '<li class="d-flex align-items-center"><span class="material-icons text-primary">clear</span><span class="ms-2">{error_text}</span></li>';
	        	var errors = "";
	        	if(xhr.responseJSON !=null && xhr.responseJSON.errors != null) {
	        		xhr.responseJSON.errors.forEach(function(error) {
	        			errors += li.replace("{error_text}", error);
	        		});
	        	}
	        	
	        	$("#error-ajax ul").html(errors);
	        	$("#error-ajax").removeClass("d-none");
	        	updateMenuTree();
	        	// Mostrar notificación
	        	showNotification('Menú', 'No se ha podido reordenar la página', 'error');
	        	
	        }
	    });

	} else{
		$("#error-ajax").removeClass("d-none");
		updateMenuTree();
		showNotification('Menú', 'No es posible cambiar el orden, faltan datos.', 'error');
	}
}
