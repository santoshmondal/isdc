
function PurchaseMaster(){
	this.invoiceNumber = '-1';
	this.supplierId = -1;
	this.supplierName = '';
	this.grandAmount = 0;
	this.discountAmount = 0;
	this.totalGrandAmount = 0; // grandAmount-discountAmount 
	this.grandQuantity = 0;
	this.productList = new Array();
};

function PurchaseProduct(){
	this.id=-1;
	this.productCode='';
	this.rate=0;
	this.price=0;
	this.discount = 0;
	this.taxPrice=0;
	this.finalPrice=0;
	this.quantity = 0;
	this.freeQuantity = 0;
	this.totalQuantity=0;
	this.taxList=new Array();
	this.schemeList=new Array();
};

function Tax(){
	this.name='';
	this.rate=0;
	this.amount=0;
}

PurchaseMaster.prototype.addProduct = function( product ){
	this.productList.push(product);
};

PurchaseMaster.prototype.removeProduct = function(productCode){
	for(var i=0;i < this.productList.length;i++ )
	{
		if ( this.productList[0].productCode == productCode){
			productList.splice(i,1);
		}
	}
};

PurchaseMaster.prototype.getProduct = function(productCode){
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
	
	var purchaseMaster = new PurchaseMaster();
	
	$("#supplier_name").on("keydown blur", function(event){
		var keyCode = (event.keyCode) ? event.keyCode : event.which;
		if( keyCode == 9 || keyCode == 0){
			$(this).val( purchaseMaster.supplierName);
		}
		if( keyCode == 13 ){
			var supplier = $(this).val().trim();	
			$("#Error").html("");
			$.ajax({
				url:"selectSupplierList",
				data:{supplier:supplier},
				type:"POST",
				success:function(result){
					$("#light_box").show();
					$("#light_box_back").show();
					$("#light_box_title").html("Select Supplier");
					$("#light_box_content").html( result );
				}
			}); // Closed ajax
			event.preventDefault();
			return false;
		}	// CLOSED keydown
	});
	
		
	$("#light_box_content").on("click", "#supplierSelectPopup div.selectBtn", function(event){
		var code =  $(this).attr("id");
		if( code != undefined && code.trim() != '' ){
			purchaseMaster.supplierId = $(this).attr("id");
			purchaseMaster.supplierName = $(this).attr("supplierName");
			$("#supplier_name").val( purchaseMaster.supplierName );
			hideLightBox();
		}
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
			var productCode = $(this).val();
			if( productCode.trim().length > 0 ){
				GVRowIDToFill = "ROW-"+productCode.trim();
			}
			else{
				GVRowIDToFill = 'LastRow';
			}
			
			$.ajax({
				url:"getProductList",
				type:"POST",
				success:function(result){
					$("#light_box").show();
					$("#light_box_back").show();
					$("#light_box_title").html("Add Product");
					$("#light_box_content").html( result );
				}
			});
			event.preventDefault();
			return false;	
			
		} // CLOSED if keyCode == 13
		
	});

	// add product to purchase table
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
				data:{productCode:code},
				type:"POST",
				success:function(result){
					//console.log(result);
					var productObj = "";
					try{
						productObj = JSON.parse(result);
					}catch(err){ console.log(err); return false;}
					
					hideLightBox();
					// If item is already added return, do nothing
					if( $("#ROW-"+productObj.code).length > 0){
						return false;
					}
					
					// ADD into a list for future use
					tempSelectedProductList[productObj.code] = productObj;
					
					var PR = new PurchaseProduct();
					PR.productCode = productObj.code;
					PR.id = productObj.id;
					PR.rate =  productObj.rate;
					PR.quantity = 0;
					PR.price = PR.quantity * PR.rate;
					 
					// DO NOT change the order
					
					if( GVRowIDToFill === 'LastRow'){
						$("#LastRow .productQuantity").val("");
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
					$("#"+GVRowIDToFill+" .productCode").val(productObj.code);
					$("#"+GVRowIDToFill+" .productName").text(productObj.name);
					$("#"+GVRowIDToFill+" .productName").attr("title",productObj.name);
					$("#"+GVRowIDToFill+" .productRate").val(productObj.rate);
					$("#"+GVRowIDToFill+" .productRate").attr("product",productObj.code);
					calculateAndDisplayTaxs( productObj.taxList, PR );
					$("#"+GVRowIDToFill+" .addRemoveScheme").html("Add/Remove");
					$("#"+GVRowIDToFill+" .addRemoveScheme").attr("product",productObj.code);
					
					// ADD into Return object to send to server
					purchaseMaster.addProduct(PR);
					$("#Error").html("");
				}
			});
			
		}
		 
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
		
		var PR = purchaseMaster.getProduct(pCode);
		
		// DO NOT change the calculation order
		PR.quantity = pQnty;
		PR.price = PR.rate * PR.quantity;
		calculateDiscount( PR );
		PR.totalQuantity = PR.quantity + PR.freeQuantity;
		displayQuantity( PR );
		calculateAndDisplayTaxs( pObject.taxList, PR );
		PR.finalPrice = PR.price + PR.taxPrice - PR.discount;
		displayAmount( PR );
		displayGrandTotal(PR);
		
	});
	
	$("#mainTable").on("keydown", ".productRate", function(event){
		var keyCode = (event.keyCode) ? event.keyCode : event.which;
		var codeArr = [8,46,37,39,190,48,49,50,51,52,53,54,55,56,57];
		
		if( codeArr.indexOf(keyCode) == -1 ){
			event.preventDefault();
			return false;
		}
	});
	
	// CALCULATE tax and prices on rate change
	$("#mainTable").on("keyup", ".productRate", function(event){
		var pRate = $(this).val();
		if( pRate.trim().length == 0 ){
			pRate = "0";
		}
		if( !$.isNumeric(pRate) ){
			pRate = pRate.replace(/[^0-9.]/,"");
			$(this).val( pRate );
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
		pRate = parseInt(pRate);
		
		var PR = purchaseMaster.getProduct(pCode);
		
		// DO NOT change the calculation order
		PR.rate = pRate;
		PR.price = PR.rate * PR.quantity;
		calculateDiscount( PR );
		PR.totalQuantity = PR.quantity + PR.freeQuantity;
		displayQuantity( PR );
		calculateAndDisplayTaxs( pObject.taxList, PR );
		PR.finalPrice = PR.price + PR.taxPrice - PR.discount;
		displayAmount( PR );
		displayGrandTotal(PR);
		
	});
	
	function displayGrandTotal(){
		var grandAmount = 0;
		var grandQuantity = 0;
		var productList = purchaseMaster.productList;
		for(var i=0; i < productList.length; i++ ){
			var pr = productList[i];
			grandAmount = grandAmount + pr.finalPrice;
			grandQuantity = grandQuantity + pr.totalQuantity;
		}
		purchaseMaster.grandAmount = grandAmount;
		purchaseMaster.grandQuantity = grandQuantity;
		$("#grandAmount").html( purchaseMaster.grandAmount );
		$("#grandQuantity").html( purchaseMaster.grandQuantity );
	}
	
	function calculateDiscount( PR ){
		var tFreeItem = 0;
		var tFreeDiscount = 0;
		var selectedSchemes = tempSelectedSchemeList[PR.productCode];
		
		if( selectedSchemes != undefined ){
			var html = '';
			$.each( selectedSchemes , function(i,scheme) {
				
				if( PR.quantity >= scheme.quantity ){
					if(scheme.type == true){
						tFreeItem = tFreeItem + scheme.value;
						html +='<div><div class="lableLeft">Free Item</div><div class="lableRight">'+scheme.value+'</div></div>';
					}
					else{
						var tPrice = (PR.price * scheme.value) / 100; 
						tFreeDiscount = tFreeDiscount + tPrice;
						html +='<div><div class="lableLeft">% Discount</div><div class="lableRight">'+scheme.value+' %</div></div>';
					}
				}
				
			});
			PR.freeQuantity = tFreeItem;
			PR.discount = tFreeDiscount;
			$("#ROW-"+PR.productCode+" .productSchemes").html(html);
		}
			
		
	}
	
	// Calculate Amount 
	function displayAmount(RPObject){
		var html = "";
		html +='<div><div class="lableLeft">Price</div><div class="lableRight">'+RPObject.price+'</div></div>';
		html +='<div><div class="lableLeft">Tax</div><div class="lableRight">'+RPObject.taxPrice+'</div></div>';
		html +='<div><div class="lableLeft">Discount(-)</div><div class="lableRight">'+RPObject.discount+'</div></div>';
		html +='<div class="total"><div class="lableLeft">Total</div><div class="lableRight">'+RPObject.finalPrice+'</div></div>';
		$("#ROW-"+RPObject.productCode+" .finalAmount").html(RPObject.finalPrice);
		$("#ROW-"+RPObject.productCode+" .finalAmount").parent().attr("title","Price- "+RPObject.price+", \nTax- "+RPObject.taxPrice+", \nDiscount- "+RPObject.discount);
	}
	
	// Calculate Quantity 
	function displayQuantity(RPObject){
		var html = "";
		html +='<div><div class="lableLeft">Quantity</div><div class="lableRight">'+RPObject.quantity+'</div></div>';
		html +='<div><div class="lableLeft">Free</div><div class="lableRight">'+RPObject.freeQuantity+'</div></div>';
		html +='<div class="total"><div class="lableLeft">Total</div><div class="lableRight">'+RPObject.totalQuantity+'</div></div>';
		$("#ROW-"+RPObject.productCode+" .finalQuantity").html(RPObject.totalQuantity);
		$("#ROW-"+RPObject.productCode+" .finalQuantity").parent().attr("title","Free- "+RPObject.freeQuantity);
	}
	
	
	// Calculate Taxs
	function calculateAndDisplayTaxs(taxList, RPObject){
			var taxHtml = "";
			var tRateTotal = 0;
			var tPriceTotal = 0;
			var taxTitle = "";
			var spacer = "";
			if( taxList != undefined ){
				var pTaxList = new Array();
				for(var i=0;i<taxList.length;i++)
				{
					var pTax = new Tax(); 
					var tax = taxList[i];
					
					taxTitle += spacer+tax.name+"-"+tax.rate+"%";
					spacer = ", \n";
					
					tRateTotal = tRateTotal+tax.rate;
					var taxPrice = (RPObject.price*tax.rate)/100;
					tPriceTotal += taxPrice;	
					//taxHtml += '<div><div class="lableLeft">'+tax.name+' ('+tax.rate+'%) </div><div class="lableRight">'+taxPrice+'</div></div>';
					pTax.name = tax.name;
					pTax.rate = tax.rate;
					pTax.amount = taxPrice
					pTaxList.push(pTax);
				}
			}
			//taxHtml +='<div class="total"><div class="lableLeft">Total ('+tRateTotal+'%) </div><div class="lableRight">'+tPriceTotal+'</div></div>';
			RPObject.taxPrice = tPriceTotal;
			RPObject.taxList = pTaxList;
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
				
				
				html += '<td>'+scheme.quantity+'</td>';
				
				// Lets user to select scheme but aplicable only on valid min quantity
				// var quantity = $("input.productQuantity[product="+pCode+"]").val();
				// if( quantity.trim() != '' && (quantity >= scheme.quantity) ){
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
		$("#light_box_title").html("Product Scheme");
		
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
		var PR = purchaseMaster.getProduct(pCode);
		calculateDiscount(PR);
		PR.totalQuantity = PR.quantity + PR.freeQuantity;
		displayQuantity( PR );
		PR.finalPrice = PR.price + PR.taxPrice - PR.discount;
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
	

	$("#PurchaseSaveBtn").click( function(){
		savePurchaseMaster();
	});
	
	function savePurchaseMaster(){
		if( purchaseMaster.supplierId == -1 || purchaseMaster.supplierName.trim() == ''){
			$("#partyName").html( "Enter Suppilre name !" );
			$("#partyName").addClass("error");
			return false;
		}
		if(purchaseMaster.productList.length == 0){
			$("#Error").html( "No product added to return !" );
			return false;
		}
		
		purchaseMaster.invoiceNumber = $("#invoiceNo").text(); 
		 
		var error = '';
		for(var i=0; i < purchaseMaster.productList.length; i++ ){
			var pr = purchaseMaster.productList[i];
			if(pr.quantity == 0){
				error += pr.productCode+", ";
			}
		}
		if(error != ''){
			$("#Error").html( "No quantity added for product: "+error);
			return false;
		}
		
		//console.log(JSON.stringify(purchaseMaster));
		$.ajax({
			url:"savePurchaseMaster",
			data:{purchaseMaster:JSON.stringify(purchaseMaster)},
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


