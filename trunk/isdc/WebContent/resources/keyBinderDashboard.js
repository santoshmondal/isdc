/**
 * Harindra Chaudhary
 * var isAlt = event.altKey; true or false
 * var isCtrl = event.ctrlKey; true or false
 * event.preventDefault(); avoid default action of key
 * String.fromCharCode(keyCode) - keyCode to string
 * someString.toLowerCase() - to convert lower case string
 */

/**
 *  KEY BINDING
 *  F2(113) key for save
 *  F3(114) key
 *  F4(115) key
*/
$(document).ready(function(){
	
	var $currRowEl = null;
	var $currSubMenuLIEl = null;

	$("#MainMenuBar > ul > li").hover(function(){
		$("#MainMenuBar > ul > li").removeClass("currentMenuItem");
		$(this).addClass("currentMenuItem");
		$(".currentMenuItem ul.dropdown-menu").removeAttr("style");
		$("ul.dropdown-menu > li").removeClass("currentSubMenuItem");
		$currSubMenuLIEl = null;
		
	});
	
	
	$("ul.dropdown-menu > li").hover(function(){
		$("ul.dropdown-menu > li").removeClass("currentSubMenuItem");
		$(this).addClass("currentSubMenuItem");
		$currSubMenuLIEl = $(this);
		
	});
	
	$("table#dashboard tbody tr").hover(function(){
		$("table#dashboard tbody tr").removeClass("highlighted");
		$(this).addClass("highlighted");
		$currRowEl = $(this); 
	});
	
	$(document).keydown(function(event){
		
		var keyCode = (event.keyCode) ? event.keyCode : event.which;
		//console.log( keyCode+":"+String.fromCharCode(keyCode) );
		
		var isCtrl = event.ctrlKey;
		
		if( isCtrl ){
			
			if( event.keyCode == 38 ){ // Ctrl + ARROW UP
				event.preventDefault();
				$(".panel-body").attr("tabindex",-1).focus();
				
				if( $currRowEl == null){
					$currRowEl = $("table#dashboard tbody tr").first();
				}
				else{
					if( $currRowEl.prev().length > 0  ){
						$currRowEl.removeClass("highlighted");
						$currRowEl = $currRowEl.prev();
					}
				}
				$currRowEl.addClass("highlighted");
				
			}
			else if( event.keyCode == 40 ){  // Ctrl + ARROW DOWN
				event.preventDefault();
				$(".panel-body").attr("tabindex",-1).focus();
				
				if( $currRowEl == null){
					$currRowEl = $("table#dashboard tbody tr").first();
				}
				else{
					if( $currRowEl.next().length > 0  ){
						$currRowEl.removeClass("highlighted");
						$currRowEl = $currRowEl.next();
					}
				}
				$currRowEl.addClass("highlighted");
			}
			
		}
		else if( keyCode == 114 ) { // F3 - add new entry
		
			event.preventDefault();
 			var queryIndex = window.location.href.search('\\?');
 			var queryString = '';
 			if( queryIndex != -1 ){
 				queryString = window.location.href.substring(queryIndex);
 			}
			window.location='add'+queryString;
			
 		}
 		else if( keyCode == 115 ) { // F4 - edit entry
 			event.preventDefault();
 			if($currRowEl != null)
 			{
 				var $editLinkEl = $currRowEl.children().children("a[class='editLink']");
 				window.location=$editLinkEl.attr("href");
 			}
 			
 		}
 		else if( event.keyCode == 38 ){ // Arrow up
 			
			if( $(".currentMenuItem .dropdown-menu").css("display") == 'block'){
				event.preventDefault();
				
				if( $currSubMenuLIEl == null ){
					$currSubMenuLIEl = $(".currentMenuItem .dropdown-menu > li").first();
				}
				else{
					if( $currSubMenuLIEl.prev().length > 0  ){
						$currSubMenuLIEl.removeClass("currentSubMenuItem");
						$currSubMenuLIEl = $currSubMenuLIEl.prev();
					}
				}
				$currSubMenuLIEl.addClass("currentSubMenuItem");
			}
			
		}
		else if( event.keyCode == 40 ){ // Arrow down
			
			if( $(".currentMenuItem .dropdown-menu").css("display") == 'block'){
				event.preventDefault();
				
				if( $currSubMenuLIEl == null ){
					$currSubMenuLIEl = $(".currentMenuItem .dropdown-menu > li").first();
				}
				else{
					if( $currSubMenuLIEl.next().length > 0  ){
						$currSubMenuLIEl.removeClass("currentSubMenuItem");
						$currSubMenuLIEl = $currSubMenuLIEl.next();
					}
				}
				$currSubMenuLIEl.addClass("currentSubMenuItem");
			}
			
		}
		else if( event.keyCode == 13 ){ // Arrow down
			
			if( $(".currentMenuItem .dropdown-menu").css("display") == 'block'){
				event.preventDefault();
				if( $currSubMenuLIEl != null ){
					var $clickLinkEl = $currSubMenuLIEl.children("a");
	 				window.location=$clickLinkEl.attr("href");
				}
			}
			
		}
		else if( keyCode == 27 ){ // Esc
			if( $(".currentMenuItem .dropdown-menu").css("display") == 'block'){
				event.preventDefault();
				$(".currentMenuItem ul.dropdown-menu").css("display","none");
			}
			
		}
		 	
 		
 		return true;
	}); // Closed keydown 
});