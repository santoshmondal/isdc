
function SalesReturn(){
	this.invoiceNumber = '-1';
	this.grandAmount = 0;
	this.grandQuantity = 0;
	this.productList = new Array();
};

function ProductReturn(){
	this.id=-1;
	this.productCode='';
	this.returnPrice=0;
	this.returnDiscount = 0;
	this.returnTaxPrice=0;
	this.returnFinalPrice=0;
	this.returnQuantity = 0;
	this.returnFreeQuantity = 0;
	this.returnTotalQuantity=0;
	this.returnDefect = 0;
};

SalesReturn.prototype.addProduct = function( product ){
	this.productList.push(product);
};

SalesReturn.prototype.removeProduct = function(productCode){
	for(var i=0;i < this.productList.length;i++ )
	{
		if ( this.productList[0].productCode == productCode){
			productList.splice(i,1);
		}
	}
};

SalesReturn.prototype.getProduct = function(productCode){
	for(var i=0; i < this.productList.length; i++ )
	{
		if( this.productList[i].productCode == productCode ){
			return this.productList[i];
		}
	}
};


$(document).ready( function(){
	
	var tempSelectedProductList = new Array();
	var tempSelectedSchemeList = new Array();
	
	var salesReturn = new SalesReturn();
	
	$("#invoice_no").blur( function(event){
		var invoiceNo = $(this).val().trim();	
		if( invoiceNo == salesReturn.invoiceNumber ){
			return false;
		}
		
		salesReturn = new SalesReturn();
		salesReturn.invoiceNumber = '-1';
		$("#Error").html("");
		
		$.ajax({
			url:"getPartyName",
			data:{invoiceNumber:invoiceNo},
			type:"POST",
			success:function(result){
				var object = "";
				try{
					object = JSON.parse(result);
				}
				catch(err){ console.log(err); return;}
				
				if( object.found == "YES" ){
					$("#partyName").html( object.partyName );
					$("#partyName").removeClass("error");
					salesReturn.invoiceNumber = invoiceNo;
					var clone = $("#LastRow").clone();
					$("table#mainTable tbody").empty();
					$("table#mainTable tbody").append( clone );
				}
				else{
					$("#partyName").html( "Invoice not found !" );
					$("#partyName").addClass("error");
					var clone = $("#LastRow").clone();
					$("table#mainTable tbody").empty();
					$("table#mainTable tbody").append( clone );
				}
			 	
			 
			},
			statusCode: {
				500:function(){
					$("#partyName").html("Internal Server Error !");
					$("#partyName").addClass("error");
		    	},
			    503:function(){
			    	$("#partyName").html("Service Unavailable !");
			    	$("#partyName").addClass("error");
		    	},
		    	404:function(){
			    	$("#partyName").html("Request not found !");
			    	$("#partyName").addClass("error");
		    	}
			}
		});
		event.preventDefault();
		return false;	
	});
	
	var GVRowIDToFill = 'LastRow';
	
	// OPEN POPUP for select product
	$("#mainTable").on("keydown", ".productCode", function(event){
		var keyCode = (event.keyCode) ? event.keyCode : event.which;
		
		if( keyCode != 13 && keyCode != 9){
			event.preventDefault();
			return false;
		}
		
		if( keyCode == 13){
			
			if( salesReturn.invoiceNumber == '-1' ){
				$("#partyName").html( "Enter Invoice Number !" );
				$("#partyName").addClass("error");
				return false;
			}
			
			var productCode = $(this).val();
			if( productCode.trim().length > 0 ){
				GVRowIDToFill = "ROW-"+productCode.trim();
			}
			else{
				GVRowIDToFill = 'LastRow';
			}
			
			var invoiceNo = $("#invoice_no").val();
			
			$.ajax({
				url:"getProductList",
				data:{invoiceNumber:invoiceNo},
				type:"POST",
				success:function(result){
					
					$("#light_box").show();
					$("#light_box_back").show();
					$("#light_box_title").html("Add Product");
					
					$("#light_box_content").html( result );

				},
				statusCode: {
					500:function(){
						$("#light_box_content").html("Internal Server Error !");
			    	},
				    503:function(){
				    	$("#light_box_content").html("Service Unavailable !");
			    	},
			    	404:function(){
				    	$("#light_box_content").html("Request not found !");
			    	}
				}
			});
			event.preventDefault();
			return false;	
			
		} // CLOSED if keyCode == 13
		
	});
	
	
	
	// ADD PRODUCT to return table
	$("#light_box_content").on("click", "#productAddPopup div.addBtn", function(event){
		var code =  $(this).attr("id");
		
		if( code != undefined && code.trim() != '' ){
		
			var invoiceNo = $("#invoice_no").val();
			
			if( $("#ROW-"+code).length > 0){
				hideLightBox();
				return false;
			}
			
			$.ajax({
				url:"getProductDetail",
				data:{productCode:code,invoiceNumber:invoiceNo},
				type:"POST",
				success:function(result){
					//console.log(result);
					var productObj = "";
					try{
						productObj = JSON.parse(result);
					}catch(err){ console.log(err); return false;}
					
					hideLightBox();
					if(productObj.code == undefined || productObj.code == null){
						return;
					}
					
					// If item is already added return, do nothing
					if( $("#ROW-"+productObj.code).length > 0){
						return false;
					}
					
					// ADD into a list for future use
					tempSelectedProductList[productObj.code] = productObj;
					
					var PR = new ProductReturn();
					PR.productCode = productObj.code;
					PR.id = productObj.id;
					PR.returnQuantity = 0;
					PR.returnPrice = PR.returnQuantity * productObj.rate;
					 
					// DO NOT change the order
					
					if( GVRowIDToFill === 'LastRow'){
						$("#LastRow .productQuantity").val("");
						$("#LastRow .defected").val("");
						var clone = $("#LastRow").clone();
						$("#LastRow").attr("id", "ROW-"+productObj.code);
						$("table#mainTable tbody").append( clone );
					}
					else{
						$("#"+GVRowIDToFill+"").attr("id", "ROW-"+productObj.code);
					}
					
					GVRowIDToFill = "ROW-"+productObj.code;
					
					$("#"+GVRowIDToFill+" .productQuantity").val("");
					$("#"+GVRowIDToFill+" .productQuantity").attr("product",productObj.code);
					$("#"+GVRowIDToFill+" .defected").val("");
					$("#"+GVRowIDToFill+" .defected").attr("product",productObj.code);
					$("#"+GVRowIDToFill+" .productCode").val(productObj.code);
					$("#"+GVRowIDToFill+" .productName").text(productObj.name);
					$("#"+GVRowIDToFill+" .productName").attr("title",productObj.name);
					//$("#"+GVRowIDToFill+" .oldQuantity").html(" / "+productObj.quantity);
					$("#"+GVRowIDToFill+" .productRate").html(productObj.rate);
					calculateAndDisplayTaxs( productObj.taxList, PR );
					$("#"+GVRowIDToFill+" .addRemoveScheme").html("Add/Remove");
					$("#"+GVRowIDToFill+" .addRemoveScheme").attr("product",productObj.code);
					
					// ADD into Return object to send to server
					salesReturn.addProduct(PR);
					$("#Error").html("");
				}
			});
			
		}
		 
	});
	
	$("#mainTable").on("keyup", ".defected", function(event){
		
		var pQnty = $(this).val();
		if( pQnty.trim().length == 0 ){
			pQnty = "0";
		}
		if( !$.isNumeric(pQnty) || pQnty.search("\\.") > 0 ){
			pQnty = pQnty.replace(/[^0-9.]/,"");
			pQnty = pQnty.replace('.', '');
			$(this).val( pQnty );
			return false;
		}
		
		var pCode = $(this).attr("product");
		if( pCode == undefined || pCode ==  null){
			return false;
		}
		var PR = salesReturn.getProduct(pCode);
		if( PR == undefined || PR == null ){
			return false;
		}
		$("#Error").html("");
		pQnty = parseInt(pQnty);
		if(pQnty > PR.returnTotalQuantity ){
			$(this).val("");
			pQnty = 0;
		}
		PR.returnDefect = parseInt(pQnty);
	});

	$("#mainTable").on("keydown", ".productQuantity", function(event){
		var keyCode = (event.keyCode) ? event.keyCode : event.which;
		var codeArr = [8,46,37,39,48,49,50,51,52,53,54,55,56,57];
		
		if( codeArr.indexOf(keyCode) == -1 ){
			event.preventDefault();
			return false;
		}
	});
	
	// CALCULATE tax and prices on quantity change
	$("#mainTable").on("keyup", ".productQuantity", function(event){
		
		var pQnty = $(this).val();
		if( pQnty.trim().length == 0 ){
			pQnty = "0";
		}
		if( !$.isNumeric(pQnty) || pQnty.search("\\.") > 0 ){
			pQnty = pQnty.replace(/[^0-9.]/,"");
			pQnty = pQnty.replace('.', '');
			$(this).val( pQnty );
			return false;
		}
		
		var pCode = $(this).attr("product");
		if( pCode == undefined || pCode ==  null){
			return false;
		}
		
		var pObject = tempSelectedProductList[pCode];
		if( pObject == undefined || pObject == null ){
			return false;
		}
		$("#Error").html("");
		pQnty = parseInt(pQnty);
		if(pQnty > pObject.quantity ){
			$(this).val("");
			pQnty = 0;
		}
		
		var PR = salesReturn.getProduct(pCode);
		
		if(pQnty < PR.returnDefect ){
			$("#mainTable .defected").val("");
			PR.returnDefect = 0;
		}
		
		
		// DO NOT change the calculation order
		PR.returnQuantity = pQnty;
		PR.returnPrice = pObject.rate * PR.returnQuantity;
		calculateDiscount( PR );
		PR.returnTotalQuantity = PR.returnQuantity + PR.returnFreeQuantity;
		displayQuantity( PR );
		calculateAndDisplayTaxs( pObject.taxList, PR );
		PR.returnFinalPrice = PR.returnPrice + PR.returnTaxPrice - PR.returnDiscount;
		displayAmount( PR );
		displayGrandTotal(PR);
		
	});
	
	function displayGrandTotal(){
		var grandAmount = 0;
		var grandQuantity = 0;
		var productList = salesReturn.productList;
		for(var i=0; i < productList.length; i++ ){
			var pr = productList[i];
			grandAmount = grandAmount + pr.returnFinalPrice;
			grandQuantity = grandQuantity + pr.returnTotalQuantity;
		}
		salesReturn.grandAmount = grandAmount;
		salesReturn.grandQuantity = grandQuantity;
		$("#grandAmount").html( salesReturn.grandAmount );
		$("#grandQuantity").html( salesReturn.grandQuantity );
	}
	
	function calculateDiscount( PR ){
		var tFreeItem = 0;
		var tFreeDiscount = 0;
		var selectedSchemes = tempSelectedSchemeList[PR.productCode];
		
		if( selectedSchemes != undefined ){
			var pObject = tempSelectedProductList[PR.productCode];
			var html = '';
			$.each( selectedSchemes , function(i,scheme) {
				
				// Minimum required return quantity for return discount
				var minReturnQnty = pObject.quantity - scheme.quantity;
				    minReturnQnty = minReturnQnty+1;
				
				if( PR.returnQuantity >= minReturnQnty ){
					if(scheme.type == true){
						tFreeItem = tFreeItem + scheme.value;
						html +='<div><div class="lableLeft">Free Item</div><div class="lableRight">'+scheme.value+'</div></div>';
					}
					else{
						var tPrice = (PR.returnPrice * scheme.value) / 100; 
						tFreeDiscount = tFreeDiscount + tPrice;
						html +='<div><div class="lableLeft">% Discount</div><div class="lableRight">'+scheme.value+' %</div></div>';
					}
				}
				
			});
			PR.returnFreeQuantity = tFreeItem;
			PR.returnDiscount = tFreeDiscount;
			//$("#ROW-"+PR.productCode+" .productSchemes").html(html);
			$("#ROW-"+PR.productCode+" .productSchemes").html(tFreeDiscount);
		}
			
		
	}
	
	// Calculate Amount 
	function displayAmount(RPObject){
		var html = "";
		html +='<div><div class="lableLeft">Price</div><div class="lableRight">'+RPObject.returnPrice+'</div></div>';
		html +='<div><div class="lableLeft">Tax</div><div class="lableRight">'+RPObject.returnTaxPrice+'</div></div>';
		html +='<div><div class="lableLeft">Discount(-)</div><div class="lableRight">'+RPObject.returnDiscount+'</div></div>';
		html +='<div class="total"><div class="lableLeft">Total</div><div class="lableRight">'+RPObject.returnFinalPrice+'</div></div>';
		//$("#ROW-"+RPObject.productCode+" .finalAmount").html(html);
		$("#ROW-"+RPObject.productCode+" .finalAmount").html(RPObject.returnFinalPrice);
		$("#ROW-"+RPObject.productCode+" .finalAmount").parent().attr("title","Return Price- "+RPObject.returnPrice+", \nReturn Tax- "+RPObject.returnTaxPrice+", \nReturn Discount- "+RPObject.returnDiscount);
	}
	
	// Calculate Quantity 
	function displayQuantity(RPObject){
		var html = "";
		html +='<div><div class="lableLeft">Quantity</div><div class="lableRight">'+RPObject.returnQuantity+'</div></div>';
		html +='<div><div class="lableLeft">Free</div><div class="lableRight">'+RPObject.returnFreeQuantity+'</div></div>';
		html +='<div class="total"><div class="lableLeft">Total</div><div class="lableRight">'+RPObject.returnTotalQuantity+'</div></div>';
		//$("#ROW-"+RPObject.productCode+" .finalQuantity").html(html);
		$("#ROW-"+RPObject.productCode+" .finalQuantity").html(RPObject.returnTotalQuantity);
		$("#ROW-"+RPObject.productCode+" .finalQuantity").parent().attr("title","Return Free items- "+RPObject.returnFreeQuantity);
	}
	
	
	// Calculate Taxs
	function calculateAndDisplayTaxs(taxList, RPObject){
			var taxHtml = "";
			var tRateTotal = 0;
			var tPriceTotal = 0;
			var taxTitle = "";
			var spacer = "";
			
			if( taxList != undefined ){
				for(var i=0;i<taxList.length;i++)
				{
					var tax = taxList[i];
					taxTitle += spacer+tax.name+"- "+tax.rate+"%";
					spacer = ", \n";
					
					tRateTotal = tRateTotal+tax.rate;
					var taxPrice = (RPObject.returnPrice*tax.rate)/100;
					tPriceTotal += taxPrice;	
					//taxHtml += '<div><div class="lableLeft">'+tax.name+' ('+tax.rate+'%) </div><div class="lableRight">'+taxPrice+'</div></div>';
				}
			}
			//taxHtml +='<div class="total"><div class="lableLeft">Total ('+tRateTotal+'%) </div><div class="lableRight">'+tPriceTotal+'</div></div>';
			RPObject.returnTaxPrice = tPriceTotal;
			$("#ROW-"+RPObject.productCode+" .productTaxs").html(tPriceTotal);
			$("#ROW-"+RPObject.productCode+" .productTaxs").parent().attr("title",taxTitle);
	}
	
	
	$("#mainTable").on("click", ".addRemoveScheme", function(event){
		var pCode = $(this).attr("product");
		if( pCode == undefined || pCode ==  null){
			return false;
		}
		
		var pObject = tempSelectedProductList[pCode];
		if( pObject == undefined || pObject == null ){
			return false;
		}
		
		var schemeList = pObject.schemeList;
		var html = '';
		var notFound=0;
		if( schemeList != undefined && schemeList.length > 0 ){
			html ='<table class="productscheme" border="1">';
			html += '<thead><tr><td>Name</td><!--<td>Type</td>--><td>Price Discount</td><td>Free Items</td><td>Minnimum Return Quantity</td><td>Select</td></tr></thead>';
			for(var i=0;i<schemeList.length;i++)
			{
				var scheme = schemeList[i];
				var minReturnQnty = pObject.quantity - scheme.quantity;
				minReturnQnty = minReturnQnty+1;
				if( minReturnQnty <=0 ){
					continue;
				}
				notFound = 1;
				
				html +='<tr><td>'+scheme.name+'</td>';
				if( scheme.type == true){
					//html += '<td>Free Item</td>';
					html += '<td>-</td><td>'+scheme.value+' item</td>';
				}
				else{
					//html +='<td>% Discount</td>';
					html += '<td>'+scheme.value+' %</td><td>-</td>';
				}
				
				
				html += '<td>'+minReturnQnty+'</td>';
				
				// Lets user to select scheme but aplicable only on valid min quantity
				// var returnQuantity = $("input.productQuantity[product="+pCode+"]").val();
				// if( returnQuantity.trim() != '' && (returnQuantity >= minReturnQnty) ){
				var selected = isAlreadySelected( scheme.id, pCode );
				html += '<td><input type="checkbox" name="schemes" value="'+scheme.id+'" '+selected+'/></td></tr>';
				
			} 
			
			html += '</tbody></table>';
			html += '<div class="schemeSave">';
			html += '<button id="schemeSelect" class="schemeSelect btn btn-primary" product="'+pCode+'">Select</button></div>';
		}
		
		if(html.trim().length == 0 || notFound == 0){ 
			html = 'Currently no schemes available for this product!';
		}
		
		$("#light_box_content").html( html );
		$("#light_box").show();
		$("#light_box_back").show();
		$("#light_box_title").html("Return Product Scheme");
		
	});
	

	function isAlreadySelected(schemeId, pCode){
		var selctedSchemes = tempSelectedSchemeList[pCode];
		if(selctedSchemes == undefined || selctedSchemes == 'undefined'){
			return "";
		}
		
		for(var i=0; i < selctedSchemes.length; i++){
			var scheme2 = selctedSchemes[i];
			if(scheme2.id == schemeId){
				return "checked";
				break;
			}
		}
		return "";
	}
	
	
	// Strore selected schemes for calculations
	$("#light_box_content").on("click",".schemeSelect",function(){
		var pCode = $(this).attr("product");
		var pObject = tempSelectedProductList[pCode];
		var schemeList = pObject.schemeList;
		var arrSchemes = new Array();
		$("#light_box_content input:checked").each(function(){
			var schemeId = $(this).val();
			for(var i=0; i < schemeList.length; i++){
				var scheme = schemeList[i];
				if (schemeId == scheme.id){
					arrSchemes.push(scheme);
					break;
				}
			}
		});
		tempSelectedSchemeList[pCode] = arrSchemes;
		var PR = salesReturn.getProduct(pCode);
		calculateDiscount(PR);
		PR.returnTotalQuantity = PR.returnQuantity + PR.returnFreeQuantity;
		displayQuantity( PR );
		PR.returnFinalPrice = PR.returnPrice + PR.returnTaxPrice - PR.returnDiscount;
		displayAmount( PR );
		displayGrandTotal(PR);
		displayGrandTotal();
		hideLightBox();
	}); 
	
	// CLOSED POPUP
	$("#light_box_header button").click(function(){
		hideLightBox();
	});
	
	// CLOSED POPUP
	function hideLightBox(){
		$("#light_box").hide();
		$("#light_box_back").hide();
	}
	

	$("#ReturnSaveBtn").click( function(){
		saveReturn();
	});
	
	function saveReturn(){
		if( salesReturn.invoiceNumber == '-1' ){
			$("#partyName").html( "Enter Invoice Number !" );
			$("#partyName").addClass("error");
			return false;
		}
		if(salesReturn.productList.length == 0){
			$("#Error").html( "No product added to return !" );
			return false;
		}
		
		var error = '';
		for(var i=0; i < salesReturn.productList.length; i++ ){
			var pr = salesReturn.productList[i];
			if(pr.returnQuantity == 0){
				error += pr.productCode+", ";
			}
		}
		if(error != ''){
			$("#Error").html( "No quantity added for product: "+error);
			return false;
		}
		
		//console.log(JSON.stringify(salesReturn));
		$.ajax({
			url:"saveSalesReturn",
			data:{salesReturn:JSON.stringify(salesReturn)},
			type:"POST",
			success:function(result){
				var jsonObj = "";
				try{
					jsonObj = JSON.parse(result);
				}catch(err){
					if( result.search('password') > 0){
						$("#Error").html( "Session Expired!" );
					}
					console.log(err); return false;
				}
				
				if(jsonObj.success != undefined && jsonObj.success != null && jsonObj.success.length > 0){
					 window.location='dashboard?message=create-success';
				}
				else{
					$("#Error").html( jsonObj.error );
					return false;
				}
			}
			
		});
	}
	
});


