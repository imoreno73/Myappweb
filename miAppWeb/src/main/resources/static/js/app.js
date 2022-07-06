$(document).ready(
		function() {
			if($('#dateFromCal').length>0 && $('#dateToCal').length>0){
				var lang=$('html').attr('lang');
				$('#dateFromCal').datetimepicker({
					locale: lang
				});
				$('#dateToCal').datetimepicker({
					locale: lang,			
					useCurrent: false
				});
				$('#dateFromCal').on('change.datetimepicker', function(e) {
					$('#dateToCal').datetimepicker('minDate', e.date);
				});
				$('#dateToCal').on('change.datetimepicker', function(e) {
					$('#dateFromCal').datetimepicker('maxDate', e.date);
				});
			}

			$(window).resize(function() {
				if ($(window).width() < 974) {
					$('#mostrarFiltro').addClass("collapse")
				} else {
					$('#mostrarFiltro').removeClass("collapse")
				}

			});

			if ($(window).width() < 974) {
				$('#mostrarFiltro').addClass("collapse")
			} else {
				$('#mostrarFiltro').removeClass("collapse")
			}

			if (top.location == self.location) {
				$("body").show();
			}

			if (inIframe() == false) {
				// We're deeper than one down
				if ($('#banner').length > 0) {
					mostrar('banner');
				}
				if ($('#footer').length > 0) {
					mostrar('footer');
				}
				if ($('#ampliar').length > 0) {
					$('#ampliar').remove();
				}
			} else {
				if ($('#banner').length > 0) {
					$('#banner').remove;
				}
				if ($('#footer').length > 0) {
					$('#footer').remove();
				}
			}

			// Controlar el click en elementos para volver a la página anterior
			// que deban arrastrar parámetros
			$('*[data-action=goToPrevPage]').on("click", goToPrevPage);
			$('*[data-action="ampliar"]').on('click', openWin); 
			
			changeUrl();
			
			showModalDelete();
			
		});

function showModalDelete(callback) {
	
	$('body').on('click', '*[data-action=delete]', function(e){   
        e.preventDefault();
        $('#delete-form').data('id', $(this).data('id'));
        $('#delete-form').data('url', $(this).data('url'));           
        $('#delete-form').data('callback', $(this).data('callback'));
	});
 
	$('#delete-form').on('submit', function(e){
        e.preventDefault(); 
        var id = $(this).data('id');
        var url = $(this).data('url');
        var callback = $(this).data('callback');
            
        if(id){
        	
        	$.ajax({
				type: 'DELETE',
				url: url,
				success: function (data) {

					showNotification("Eliminar registro", "Registro eliminado correctamente", "success");
					
					if(callback != null) {
						window[callback]();
						
					}
				}, 
				error: function (data) {

					showNotification("Eliminar registro", "No se ha podido realizar el borrado", "error");
				}
			});
        	
        } else {
        	
        	showNotification("Eliminar registro", "No se ha podido realizar el borrado", "error");
        }
        
        $('.modalDelete').modal('hide');
 });

}

function reloadPage() {
	location.reload();
}

function mostrar(id) {
	$('#' + id).removeClass('d-none');
}

function inIframe() {
	try {
		return window.self !== window.top;
	} catch (e) {
		return true;
	}
}

function openWin() {
	var myWindow = window.open(self.location, '_blank',
			'width=1200,height=600');
}

function goToPrevPage(e) {
	e.preventDefault();
	var url = $(this).attr("data-link") + window.location.search;
	window.location = url;
}

function changeUrl() {
	var urlPath = $("#change-url").attr("data-url");
	if(urlPath != null) {
		window.history.pushState({},"", urlPath);
	}
}

function generateSlug(htmlId){
	var str=document.getElementById("slug").value;	
	str = str.replace(/^\s+|\s+$/g, '-'); // trim
	str = str.toLowerCase();
	
	//remueve los acentos, cambia eñes por enes
	var from = "ãàáäâèéëêìíïîõòóöôùúüûñç·/_,:;";
	var to   = "aaaaaeeeeiiiiooooouuuunc------";
	for (var i=0, l=from.length ; i<l ; i++) {
		str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
	}
		
	//borra caracteres que no sean de 
	//la a a la z y del 0 al 9
	str = str
			.replace(/[^a-z0-9 -]/g, '')
			.replace(/\s+/g, '-') // collapsa si hay más de un espacio en blanco y lo remplaza por -
			.replace(/-+/g, '-'); // collapsa si hay más de un guion
	
	document.getElementById(htmlId).value= str;	
}