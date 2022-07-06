$(document).ready(function() {
	
	if($("#formUserSearch").length > 0) {
		formUserSearch();
	}
		
	if($("#formRoleSearch").length > 0) {
		formRoleSearch();
	}
	
	if($("#formFunctionalGroupSearch").length > 0) {
		formFunctionalGroupSearch();
	}
	
				
});

// Usuarios
function formUserSearch() {

	var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	var URL = $("#formUserSearch").serialize();

	var tableLanguage = contextPath + "/tableLang/langTable_"
			+ $("#languageTable").val() + ".json";
	var form = {};
	$.each($("#formUserSearch").serializeArray(), function(i,
			field) {
		if (form[field.name] != "_csrf")
			form[field.name] = field.value || "";
	});
	var maxSize = $("#page-max-size").val();
	$('#userListTable')
			.DataTable(
					{
						"pagingType" : "full_numbers",
						"processing" : true,
						"serverSide" : true,
						"ordering" : false,
						"searching" : false,
						"LengthMenu": [10, 20, 30, maxSize],
						"bInfo": true,
						"sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12 text-center"ip>>',																																						
						"language" : {
							"url" : tableLanguage						
						},
						"ajax" : {

							"url" : contextPath // need
									+ '/users/search',
							"data" : form,
							dataType : 'json',
							cache : false,
							timeout : 600000,
							dataSrc : function(data) {
								$("#errormsg1").hide();
								$("#errormsg2").hide();
								if (data.data == "") {
									return [];
								} else {
									return data.data;
								}
							},
							error : function(e) {

								var errorCode = e.responseJSON.errorCode;

								$("#errormsg" + errorCode)
										.show();
								$('#userListTable_processing')
										.toggle();
								$('#userListTable')
										.DataTable().rows()
										.clear();
							}
						},
						"columns" : [ 
						{
							"data" : "login",
							"defaultContent" : ""
						}, {
							"data" : "name",
							"defaultContent" : ""
						}, {
							"data" : "surname",
							"defaultContent" : "",
						}, {
							"data" : "email",
							"defaultContent" : ""
						}, {
							"data" : "active",
							"defaultContent" : ""
//						}, {
//							"data" : "lockedDate",
//							"defaultContent" : ""
						}, {
							"data" : null,
							"defaultContent" : ""
						} ],
						"columnDefs" : [
							{
								 "targets": [0,1,2,3,4],
								 render: function ( data, type, full, row ) {
									 var text = "";
									 if(data != null) {
										 text = data;
									 }
								 	 return '<a href="'
									 					+ contextPath
														+ '/users/'
														+ full.login + '?'
														+ URL
														+ '">' + text +'</a>';
								 }          
							},
//							{
//								 "targets":5,
//								 "data":"lockedDate",
//								 render: function ( data, type, full, row ) {
//									 var text = "";									                    
//									 if(data != null) {
//										 newDate = new Date(data);
//										 text = moment(newDate).format("DD-MM-YYYY");
//									 }
//									 return '<a href="'
//									 					+ contextPath
//														+ '/users/'
//														+ full.login + '?'
//														+ URL
//														+ '">' + text +'</a>';
//								 }          
//							},
							{
							 	"targets": 5,
								"className" : "btnReg",
							 	"render": function(data, type, full, meta) {
							 		if(data != null) {
							 			return '<a href="'
								 					+ contextPath
													+ '/users/'
													+ full.login + '?'
													+ URL
													+ '"><span class="material-icons me-4">find_in_page</span></a>'
													+ '<a href="'
								 					+ contextPath
													+ '/users/edit/' + full.login + '"'
													+ '><span class="material-icons me-4">edit</span></a>'
													+ '<a href="#" data-action="delete"' 
													+ ' data-id="'+full.id+'"'
													+ ' data-url="'+contextPath+'/users/delete/'+full.id+'"' 
													+ ' data-bs-toggle="modal"' 
													+ ' data-callback="reloadPage"'
													+ ' data-bs-target=".modalDelete"><span class="material-icons me-4">clear</span></a>';
							 		}
							 	}
							 }
						],
							 retrieve : true,
				        	 order: [[ 1, "asc" ]]
					}).columns.adjust();

}

// Roles
function formRoleSearch() {

	var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	var URL = $("#formRoleSearch").serialize();

	var tableLanguage = contextPath + "/tableLang/langTable_"
			+ $("#languageTable").val() + ".json";
	var form = {};
	$.each($("#formRoleSearch").serializeArray(), function(i,
			field) {
		if (form[field.name] != "_csrf")
			form[field.name] = field.value || "";
	});
	var maxSize = $("#page-max-size").val();
	$('#roleListTable')
			.DataTable(
					{
						"pagingType" : "full_numbers",
						"processing" : true,
						"serverSide" : true,
						"ordering" : false,
						"searching" : false,
						"LengthMenu": [10, 20, 30, maxSize],
						"bInfo": true,
						"sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12 text-center"ip>>',																																						
						"language" : {
							"url" : tableLanguage						
						},
						"ajax" : {

							"url" : contextPath // need
									+ '/roles/search',
							"data" : form,
							dataType : 'json',
							cache : false,
							timeout : 600000,
							dataSrc : function(data) {
								$("#errormsg1").hide();
								$("#errormsg2").hide();
								if (data.data == "") {
									return [];
								} else {
									return data.data;
								}
							},
							error : function(e) {

								var errorCode = e.responseJSON.errorCode;

								$("#errormsg" + errorCode)
										.show();
								$('#roleListTable_processing')
										.toggle();
								$('#roleListTable')
										.DataTable().rows()
										.clear();
							}
						},
						"columns" : [ 
						{
							"data" : "name",
							"defaultContent" : ""
						}, {
							"data" : "functionalGroup",
							"defaultContent" : ""
						}, {
							"data" : "controlTotalMenu",
							"defaultContent" : ""
						}, {
							"data" : "active",
							"defaultContent" : ""
						}, {
							"data" : "lockedDate",
							"defaultContent" : ""
						}, {
							"data" : null,
							"defaultContent" : ""
						} ],
						"columnDefs" : [
							{
								 "targets": [0,2,3],
								 render: function ( data, type, full, row ) {										                    
									 var text = "";
									 if(data != null) {
										 text = data;
									 }
								 	 return '<a href="'
									 					+ contextPath
														+ '/roles/'
														+ full.slug + '?'
														+ URL
														+ '">' + text +'</a>';
								 }          
							},
							{
								 "targets": 1,
								 render: function ( data, type, full, row ) {										                    
									 var text = "";
									 if(data != null) {
										 text = data.name;
									 }
								 	 return '<a href="'
									 					+ contextPath
														+ '/roles/'
														+ full.slug + '?'
														+ URL
														+ '">' + text +'</a>';
								 }          
							},
							{
								 "targets":4,
								 "data":"lockedDate",
								 render: function ( data, type, full, row ) {										                    
									 var text = "";									                    
									 if(data != null) {
										 newDate = new Date(data);
										 text = moment(newDate).format("DD-MM-YYYY");
									 }
								 	 return '<a href="'
									 					+ contextPath
														+ '/roles/'
														+ full.slug + '?'
														+ URL
														+ '">' + text +'</a>';
								 }          
							},
							{
							 	"targets": 5,
								 "className" : "btnReg",
							 	"render": function(data, type, full, meta) {
							 		if(data != null) {
							 			return '<a href="'
								 					+ contextPath
													+ '/roles/'
													+ full.slug + '?'
													+ URL
													+ '"><span class="material-icons me-4">find_in_page</span></a>'
													+ '<a href="'
								 					+ contextPath
													+ '/roles/edit/' + full.slug + '"'
													+ '><span class="material-icons me-4">edit</span></a>'
													+ '<a href="#" data-action="delete"' 
													+ ' data-id="'+full.id+'"'
													+ ' data-url="'+contextPath+'/roles/delete/'+full.id+'"' 
													+ ' data-bs-toggle="modal"' 
													+ ' data-callback="reloadPage"'
													+ ' data-bs-target=".modalDelete"><span class="material-icons me-4">clear</span></a>';
							 		}
							 	}
							 }
						],
							 retrieve : true,
				        	 order: [[ 1, "asc" ]]
					}).columns.adjust();

}

// Grupos funcionales
function formFunctionalGroupSearch() {

	var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	var URL = $("#formFunctionalGroupSearch").serialize();

	var tableLanguage = contextPath + "/tableLang/langTable_"
			+ $("#languageTable").val() + ".json";
	var form = {};
	$.each($("#formFunctionalGroupSearch").serializeArray(), function(i,
			field) {
		if (form[field.name] != "_csrf")
			form[field.name] = field.value || "";
	});
	var maxSize = $("#page-max-size").val();
	$('#functionalGroupListTable')
			.DataTable(
					{
						"pagingType" : "full_numbers",
						"processing" : true,
						"serverSide" : true,
						"ordering" : false,
						"searching" : false,
						"LengthMenu": [10, 20, 30, maxSize],
						"bInfo": true,
						"sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12 text-center"ip>>',																																						
						"language" : {
							"url" : tableLanguage						
						},
						"ajax" : {

							"url" : contextPath // need
									+ '/functionalGroups/search',
							"data" : form,
							dataType : 'json',
							cache : false,
							timeout : 600000,
							dataSrc : function(data) {
								$("#errormsg1").hide();
								$("#errormsg2").hide();
								if (data.data == "") {
									return [];
								} else {
									return data.data;
								}
							},
							error : function(e) {

								var errorCode = e.responseJSON.errorCode;

								$("#errormsg" + errorCode)
										.show();
								$('#functionalGroupListTable_processing')
										.toggle();
								$('#functionalGroupListTable')
										.DataTable().rows()
										.clear();
							}
						},
						"columns" : [ 
						{
							"data" : "name",
							"defaultContent" : ""
						}, {
							"data" : null,
							"defaultContent" : ""
						} ],
						"columnDefs" : [
							{
								 "targets": 0,
								 render: function ( data, type, full, row ) {										                    
									 var text = "";
									 if(data != null) {
										 text = data;
									 }
								 	 return '<a href="'
									 					+ contextPath
														+ '/functionalGroups/'
														+ full.slug + '?'
														+ URL
														+ '">' + text +'</a>';
								 }          
							},
							{
							 	"targets": 1,
								 "className" : "btnReg",
							 	"render": function(data, type, full, meta) {
							 		if(data != null) {
							 			return '<a href="'
								 					+ contextPath
													+ '/functionalGroups/'
													+ full.slug + '?'
													+ URL
													+ '"><span class="material-icons me-4">find_in_page</span></a>'
													+ '<a href="'
								 					+ contextPath
													+ '/functionalGroups/edit/' + full.slug + '"'
													+ '><span class="material-icons me-4">edit</span></a>'
													+ '<a href="#" data-action="delete"' 
													+ ' data-id="'+full.id+'"'
													+ ' data-url="'+contextPath+'/functionalGroups/delete/'+full.id+'"' 
													+ ' data-bs-toggle="modal"' 
													+ ' data-callback="reloadPage"'
													+ ' data-bs-target=".modalDelete"><span class="material-icons me-4">clear</span></a>';
							 		}
							 	}
							 }
						],
							 retrieve : true,
				        	 order: [[ 1, "asc" ]]
					}).columns.adjust();

}
