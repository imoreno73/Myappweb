$(document).ready(
		
		function() {
			
			if($("#notification-header").length) {
				var title = $("#notification-header").attr("data-title");
				var text = $("#notification-header").attr("data-text");
				var type = $("#notification-header").attr("data-type");
				
				showNotification(title, text, type);
			}

		});

function showNotification(title, text, type) {
		PNotify.defaults.styling = 'material';
		PNotify.defaults.icons = 'material';
		PNotify.alert({
	          title: title,
	          text: text,
	          type: type
	        })
		
}