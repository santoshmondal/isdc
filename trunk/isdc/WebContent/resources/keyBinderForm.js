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
 *  Esc(27) for cancel add or save form 
*/

$(document).ready(function(){
	
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
	
	$(document).keydown(function(event) {
 	
	 	var keyCode = (event.keyCode) ? event.keyCode : event.which;
	 	//console.log( keyCode+":"+String.fromCharCode(keyCode) );
	
	 	if( keyCode == 27 ){ // Esc
	 		
	 		event.preventDefault();
	 		if( $(".currentMenuItem .dropdown-menu").css("display") == 'block'){
				event.preventDefault();
				$(".currentMenuItem ul.dropdown-menu").css("display","none");
			}
	 		else{
		 		var queryIndex = window.location.href.search('\\?');
	 			var queryString = '';
	 			if( queryIndex != -1 ){
	 				queryString = window.location.href.substring(queryIndex);
	 			}
		 		window.location='dashboard'+queryString;
		 		return;
		 	}
		}
	 	else if( keyCode == 113 ){ // F2
	 		
	 		event.preventDefault();
			try{
				return submitFormfn();
			}
			catch( err ){
				console.log("submitFormfn() not found!");console.log(err);
			}
	 	}
	 	
	 	return true;
	}); // Closed keydown 
});