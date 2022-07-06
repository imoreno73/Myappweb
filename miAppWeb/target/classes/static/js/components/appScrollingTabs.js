$(document).ready(function () {
  if ($('.tabs-inside-here.tabs-ajax').length > 0) {
    createTabsAjax();
  }

  if ($('.tabs-inside-here').length > 0) {
    createTabs();
  }
});

// Usuarios
function createTabs() {
 /* var contextPath = window.location.pathname.substring(
    0,
    window.location.pathname.indexOf("/", 2)
  );
  var URL = $("#formUserSearch").serialize();

  var tableLanguage =
    contextPath + "/tableLang/langTable_" + $("#languageTable").val() + ".json";
*/
$('.tabs-inside-here ul').scrollingTabs({     
	tabs: null,      
	ignoreTabPanes:  false,
	scrollToTabEdge: false,
	disableScrollArrowsOnFullyScrolled: false,
	forceActiveTab: false,
	reverseScroll: true,
	widthMultiplier: 1,	
	cssClassLeftArrow: 'material-icons',
	cssClassRightArrow: 'material-icons',
	leftArrowContent: ['<div class="scrtabs-tab-scroll-arrow scrtabs-tab-scroll-arrow-left"><span class="material-icons">navigate_before</span></div>'].join(''),
	rightArrowContent: ['<div class="scrtabs-tab-scroll-arrow scrtabs-tab-scroll-arrow-right"><span class="material-icons">navigate_next</span></div>'].join(''),
	tabsLiContent: null,
	tabsPostProcessors: null,
	enableSwiping: true,
	enableRtlSupport: false,
	bootstrapVersion: 4            
  });
  
}

// Roles
function tabsAjax() {
  /*var contextPath = window.location.pathname.substring(
    0,
    window.location.pathname.indexOf("/", 2)
  );
  var URL = $("#formRoleSearch").serialize();

  var tableLanguage =
    contextPath + "/tableLang/langTable_" + $("#languageTable").val() + ".json";
	*/

	$('.tabs-inside-here.tabs-ajax ul').scrollingTabs({     
		tabs: null,      
		ignoreTabPanes:  false,
		scrollToTabEdge: false,
		disableScrollArrowsOnFullyScrolled: false,
		forceActiveTab: false,
		reverseScroll: true,
		widthMultiplier: 1,
		tabClickHandler: pruebaAjax,
		cssClassLeftArrow: 'material-icons',
		cssClassRightArrow: 'material-icons',
		leftArrowContent: ['<div class="scrtabs-tab-scroll-arrow scrtabs-tab-scroll-arrow-left"><span class="material-icons">navigate_before</span></div>'].join(''),
		rightArrowContent: ['<div class="scrtabs-tab-scroll-arrow scrtabs-tab-scroll-arrow-right" style="display: block;"><span class="material-icons">navigate_next</span></div>'].join(''),
		tabsLiContent: null,
		tabsPostProcessors: null,
		enableSwiping: true,
		enableRtlSupport: false,
		bootstrapVersion: 4            
	  });
  
	}
	
	function pruebaAjax(e){
	  alert('Ajax');
	}
