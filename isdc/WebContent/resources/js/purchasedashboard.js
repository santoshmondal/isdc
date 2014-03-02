var cnt = 0;
var modalcnt = 0;
var jsonobject = new Array();
var jsonobjectFinal = new Array();
var jsonproductobject = new Array();
var jsoncustomerobject = new Array();
var jsonproductobjectfinal = new Array();
var grandtotal = 0;
var grandquantity = 0;

	$(document).ready(function() {
		$("tbody").on("change", "input.productqty", function(){
			var selectedid = this.id.split("-").pop();
		    var rate = $( "#producttotalrate-"+selectedid ).html();
		    var qty = this.value;
		    if($.isNumeric( qty ) && parseInt(qty)>0){
		    	var rowid = $( "#productrowid-"+selectedid ).val();		    	
		    	jsonproductobjectfinal[rowid].quantity = qty;
		    	
		    	var totalschemeitems = 0;
	            var totalschemediscount = 0;
		    	//var totalschemediscount = parseInt($( "#producttotaldiscountvalue-"+selectedid ).val());
		        //var totalschemeitems = parseInt($( "#producttotalfreeitemvalue-"+selectedid ).val());
		        var totalamount = parseFloat((qty*rate)).toFixed(2);
		    	var totaltax = parseInt($( "#producttotaltaxvalue-"+selectedid ).val());
		    	var totaltaxamount = parseFloat(( totaltax / 100 ) * totalamount).toFixed(2);
		    	var totaldiscountamount = parseFloat(( totalschemediscount / 100 ) * totalamount ).toFixed(2);
		    	var totalitems = parseInt(qty)+parseInt(totalschemeitems);
		    	var tempvartotalprice = parseFloat( (parseFloat(totalamount) + parseFloat(totaltaxamount) ).toFixed(2) - parseFloat(totaldiscountamount)  ).toFixed(2);
	            jsonproductobjectfinal[rowid].totalprice = tempvartotalprice;
	            jsonproductobjectfinal[rowid].totalqty = totalitems;

	            grandtotal = 0;
	            grandquantity = 0;	            
	            for (var i=0;i<jsonproductobjectfinal.length;i++){ 	            
		            grandtotal = parseFloat( parseFloat(grandtotal) + parseFloat(jsonproductobjectfinal[i].totalprice) ).toFixed(2);
		            grandquantity += jsonproductobjectfinal[i].totalqty;                	
	            }
				
	            $("#pTaxTotal-"+selectedid).html(totaltaxamount);
	            $("#pTQnty-"+selectedid).html(totalitems);
	            $("#pTAmt-"+selectedid).html(tempvartotalprice);
	            $("#pTAmtTitle-"+selectedid).attr("title","Amount- "+totalamount+", \nTax- "+totaltaxamount+", \nDiscount-"+totaldiscountamount);
	            $("#pTScheme-"+selectedid).html(totaldiscountamount);
	            $("#pTQntyTitle-"+selectedid).attr("title","Free item- "+totalschemeitems);
	            
	            $( "#producttotalamount-"+selectedid ).html("<div class='taxinnerleft'>Amount</div><div class='taxinnerright'>"+totalamount+"</div>"+
	            		"<div class='taxinnerleft'>Tax</div><div class='taxinnerright'>"+totaltaxamount+"</div>"+
	            		"<div class='taxinnerleft'>Discount (-)</div><div class='taxinnerright'>"+totaldiscountamount+"</div>"+
	            		"<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+tempvartotalprice+"</div><div class='clearboth'></div>"+
	            		"<input id='producttotaldiscountvalue-"+ selectedid +"' type='hidden' value='"+totaldiscountamount+"' />"+
	            		"<input id='producttotalprice-"+ selectedid +"' type='hidden' value='"+tempvartotalprice+"' />");
		    	$( "#producttotalqty-"+selectedid ).html("<div class='taxinnerleft'>Items</div><div class='taxinnerright'>"+qty+"</div>"+
		    			"<div class='taxinnerleft'>Free Items</div><div class='taxinnerright'>"+totalschemeitems+"</div>"+
		    			"<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+totalitems+"</div><div class='clearboth'></div>"+
		    			"<input id='producttotalfreeitemvalue-"+ selectedid +"' type='hidden' value='"+totalschemeitems+"' />"+
		    			"<input id='producttotalquantity-"+ selectedid +"' type='hidden' value='"+totalitems+"' />");
		    	$("#productselectedscheme-"+selectedid).html("");
		    	
	            $( "#grandtotal" ).html("<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+grandtotal+"</div><div class='clearboth'></div>");
	            $( "#grandquantity" ).html("<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+grandquantity+"</div><div class='clearboth'></div>");
		    }
		    else
		    {
		    	$("#errorbox").html("<strong>Error!</strong> Quantity can only be a numeric value, greater than zero.");
		    	$("#errorbox").show();
		    	window.setTimeout(function() {
		    		$("#errorbox").hide();
	    		}, 5000);
		    }
			
		});
		$("tbody").on("click", "a.productremove", function(){		
			var selectedid = this.id.split("-").pop();
			var rowid = null;
			for (var j=0;j<jsonproductobjectfinal.length;j++){
				if(jsonproductobjectfinal[j].id == selectedid){
					rowid = j;
					break;
				}
			}		
			var newArray = new Array( );
			for (var j=0;j<jsonproductobjectfinal.length;j++){
				if(j != rowid){
					newArray.push(jsonproductobjectfinal[j]);
				}
			}
			jsonproductobjectfinal = newArray;
			modalcnt--;           
            grandtotal = 0;
            grandquantity = 0;	            
            for (var i=0;i<jsonproductobjectfinal.length;i++){ 	 
                //grandtotal += jsonproductobjectfinal[i].totalprice;
                grandtotal = parseFloat( parseFloat(grandtotal) + parseFloat(jsonproductobjectfinal[i].totalprice) ).toFixed(2);
                grandquantity += jsonproductobjectfinal[i].totalqty;              	    	          	
            }
            $( "#grandtotal" ).html("Total Amount: "+grandtotal);
            $( "#grandquantity" ).html("Total Quantity: "+grandquantity);
			$("#productrow-"+rowid).remove();
		});
		$(document).on("click", ".open-AddBookDialog", function () {
		     var selectedrow = $(this).data('id');
		     var selectedid = jsonproductobjectfinal[selectedrow].value;
		     $(".modal-body #modalId").val( selectedrow );
		     var strtax="";
		     var qty = parseInt($("#productqty-"+selectedid).val());
		     var cntinner = 0;
		     
		     $.each(jsonproductobjectfinal[selectedrow].productschemelist, function(i, productscheme) {
		    	 cntinner++;
		    	 if(cntinner == 1){
		    		 strtax += "<table class='productscheme' border='1'><thead><tr><td>ID</td><td>Name</td><td>Type</td><td>Discount/Free Items</td><td>Min Qty</td><td>Select</td></tr></thead><tbody>"; 
		    	 }
		    	 var schemetypestring = "";
		    	 var schemevaluestring = "";
		    	 if( productscheme.scheme_type ){
		    		 schemetypestring = "Free Item";
		    		 schemevaluestring = " items";
		    	 }
		    	 else
		    	 {
		    		 schemetypestring = "Percentage Discount";
		    		 schemevaluestring = " %";  		 
		    	 }
		    	 var tempschemestatus = "";
		    	 if(productscheme.scheme_selected != null && productscheme.scheme_selected == true){
		    		 tempschemestatus = "checked";
		    	 }
		    	 
		   		if(qty >= productscheme.scheme_qty){
		   			strtax += "<tr><td>"+productscheme.scheme_id+"</td><td>"+productscheme.scheme_name+"</td><td>"+schemetypestring
		   			+"</td><td>"+productscheme.scheme_value+schemevaluestring+"</td><td>"+productscheme.scheme_qty+
		   			"</td><td><input type='checkbox' name='schemecheckboxes' value='"+productscheme.scheme_id+"' "+tempschemestatus+" /></td></tr>";
		     	}
		   		else{
		     		strtax += "<tr><td>"+productscheme.scheme_id+"</td><td>"+productscheme.scheme_name+"</td><td>"+schemetypestring
		           	+"</td><td>"+productscheme.scheme_value+schemevaluestring+"</td><td>"+productscheme.scheme_qty+
		           	"</td><td></td></tr>";
		     	}
		     }); 
		     if(cntinner == 0){
		    	 strtax += "<tr><td colspan='6'>Currently no schemes available for this product</td></tr>";
		     }
		     strtax += "</tbody></table>";
		    $("#schemeModal .content").html( strtax );
		});
		
		
	    $('#schemeModal .go-back-button').click(function() {
	        $('#schemeModal').modal('hide');
	    });

	    
	    $('#schemeModal .okay-button').click(function() {	    	
	    	
	        $('#schemeModal').modal('hide');
	        var selectedrow = $(".modal-body #modalId").val( );
		    var selectedid = jsonproductobjectfinal[selectedrow].value;	
		    
		    var rowid = null;
			for (var j=0;j<jsonproductobjectfinal.length;j++){
				if(jsonproductobjectfinal[j].id == selectedid){
					rowid = j;
					break;
				}
			}
		    
	        var strscheme="";
	        var totalschemediscount = 0;
            var totalschemeitems = 0;
            $.each(jsonproductobjectfinal[selectedrow].productschemelist, function(i, productscheme) {
            	productscheme.scheme_selected = false;
            });  
	        $('#schemeModal .content input:checked').each(function() {
	             var checkedid = $(this).attr('value');
	             $.each(jsonproductobjectfinal[selectedrow].productschemelist, function(i, productscheme) {
             	  if(productscheme.scheme_id == checkedid){             		 
             		 productscheme.scheme_selected = true;
	             	 var schemetypestring = "";
	   		    	 var schemevaluestring = "";
	   		    	 if(productscheme.scheme_type){
	   		    		 schemetypestring = "Free Item";
	   		    		 schemevaluestring = " items";
	   		    		totalschemeitems += productscheme.scheme_value;
	   		    	 }else{
	   		    		 schemetypestring = "Percentage Discount";
	   		    		 schemevaluestring = " %"; 
	   		    		totalschemediscount += productscheme.scheme_value;
	   		    	 }                 	  
	   		    	strscheme += "<div class='taxinnerleft'>"+schemetypestring+"</div><div class='taxinnerright'>"+productscheme.scheme_value+schemevaluestring+"</div><div class='clearboth'></div>";
             	  }  
             	 });              
	        });
	        $("#productselectedscheme-"+selectedid).html( strscheme );
	        var qty = $("#productqty-"+selectedid).val();
            var totalitems = parseInt(qty)+parseInt(totalschemeitems);	    	
	    	var rate = $( "#producttotalrate-"+selectedid ).html();
	    	var totalamount = parseFloat((qty*rate)).toFixed(2);
	    	var totaltax = $( "#producttotaltaxvalue-"+selectedid ).val();
	    	var totaltaxamount = parseFloat(( totaltax / 100 ) * totalamount).toFixed(2);	
	    	var totaldiscountamount = parseFloat(( totalschemediscount / 100 ) * totalamount ).toFixed(2);
	    	var tempvartotalprice = parseFloat( (parseFloat(totalamount) + parseFloat(totaltaxamount) ).toFixed(2) - parseFloat(totaldiscountamount)  ).toFixed(2);
            jsonproductobjectfinal[rowid].totalprice = tempvartotalprice;
            jsonproductobjectfinal[rowid].totalqty = totalitems;  
            
            grandtotal = 0;
            grandquantity = 0;	            
            for (var i=0;i<jsonproductobjectfinal.length;i++){ 
            	grandtotal = parseFloat( parseFloat(grandtotal) + parseFloat(jsonproductobjectfinal[i].totalprice) ).toFixed(2);
            	grandquantity += jsonproductobjectfinal[i].totalqty;            	
            }

            $("#pTaxTotal-"+selectedid).html(totaltaxamount);
            $("#pTQnty-"+selectedid).html(totalitems);
            $("#pTAmt-"+selectedid).html(tempvartotalprice);
            $("#pTScheme-"+selectedid).html(totaldiscountamount);
            $("#pTQntyTitle-"+selectedid).attr("title","Free item- "+totalschemeitems);
            
	    	$( "#producttotalamount-"+selectedid ).html("<div class='taxinnerleft'>Amount</div><div class='taxinnerright'>"+totalamount+"</div>"+
            		"<div class='taxinnerleft'>Tax</div><div class='taxinnerright'>"+totaltaxamount+"</div>"+
            		"<div class='taxinnerleft'>Discount (-)</div><div class='taxinnerright'>"+totaldiscountamount+"</div>"+
            		"<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+tempvartotalprice+"</div><div class='clearboth'></div>"+
            		"<input id='producttotaldiscountvalue-"+ selectedid +"' type='hidden' value='"+totaldiscountamount+"' />"+
            		"<input id='producttotalprice-"+ selectedid +"' type='hidden' value='"+tempvartotalprice+"' />");
	    	$( "#producttotalqty-"+selectedid ).html("<div class='taxinnerleft'>Items</div><div class='taxinnerright'>"+qty+"</div>"+
	    			"<div class='taxinnerleft'>Free Items</div><div class='taxinnerright'>"+totalschemeitems+"</div>"+
	    			"<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+totalitems+"</div><div class='clearboth'></div>"+
	    			"<input id='producttotalfreeitemvalue-"+ selectedid +"' type='hidden' value='"+totalschemeitems+"' />"+
	    			"<input id='producttotalquantity-"+ selectedid +"' type='hidden' value='"+totalitems+"' />");	    	
	    	
            $( "#grandtotal" ).html("<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+grandtotal+"</div><div class='clearboth'></div>");
            $( "#grandquantity" ).html("<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+grandquantity+"</div><div class='clearboth'></div>");
	    });	 
	    
		$('#totalformsumit').click(function(e){
			var customerid = $("#customerid").val();
			var invoicenumber = $("#invoiceno").html();
			if($("#customerid").val().length > 0){
				var paramData = {invoicenumber: invoicenumber, customerid: customerid, listautocompleteproductResponse: jsonproductobjectfinal};
				$.ajax({
				     type : "POST",
				     url :  salesaentryurl,
				     contentType: "application/json",
				     data : JSON.stringify(paramData),
				     success:function(result){
				    	 window.location='dashboard?message=create-success';
				     }
				});
			}else{
				$("#errorbox").html("<strong>Error!</strong> Please select a customer!");
		    	$("#errorbox").show();
		    	window.setTimeout(function() {
	    		    $("#errorbox").hide();
	    		}, 5000);
			}
			
			e.preventDefault();
			return false;
		});
		
		$('#totalformsumitprint').click(function(e){
			var customerid = $("#customerid").val();
			var invoicenumber = $("#invoiceno").html();
			if($("#customerid").val().length > 0){
				var paramData = {invoicenumber: invoicenumber, customerid: customerid, listautocompleteproductResponse: jsonproductobjectfinal};
				$.ajax({
				     type : "POST",
				     url :  salesaentryurl,
				     contentType: "application/json",
				     data : JSON.stringify(paramData),
				     success:function(result){		    	 
				    	 var thePopup = window.open( 'print?id='+result, "Print", "menubar=0,location=0,height=600,width=800" );
				 	     thePopup.print();
				 	    window.location='dashboard?message=create-success';
				     }
				});
			}else{
				$("#errorbox").html("<strong>Error!</strong> Please select a customer!");
		    	$("#errorbox").show();
		    	window.setTimeout(function() {
	    		    $("#errorbox").hide();
	    		}, 5000);
			}
			e.preventDefault();
			return false;
		});
		
		$('#productModal .go-back-button').click(function() {
	        $('#productModal').modal('hide');
	    });
		
		$('#productModal .search-button').click(function(e){
			$.ajax({
				type: "POST",
				url: productnameurl,
				async: true,
				data: {
			        term: $('#modal-product-name-code').val(),                        
			        type: $('#modal-product-type').val()
			    },
			    success: function(data){
			    	jsonproductobject = data;
			    	var tempStr="";
			    	tempStr += "<table class='productscheme' border='1'><thead><tr><td>ID</td><td>Code</td><td>Name</td><td>Rate</td><td>Quantity</td><td>Weight</td><td>Select</td></tr></thead><tbody>";
			    	for (var i=0;i<data.length;i++){ 
			    		tempStr += "<tr><td>"+data[i].id+"</td><td>"+data[i].code+"</td><td>"+data[i].name+"</td><td>"+data[i].rate+"</td><td>"+data[i].quantity+"</td><td>"+data[i].weight+"</td><td><span class='modal-product-link' data-id="+i+">Add to cart</span></td></tr>";
		            }
			    	tempStr += "</tbody></table>";
                    $('#modal-product-content').html(tempStr);
                }
			});
			e.preventDefault();
			return false;
		});
		
		$(document).on("click", "#productModal .modal-product-link", function (e) {
			var selectedid = $(this).attr('data-id');

			var flag = true;
            for (var i=0;i<jsonproductobjectfinal.length;i++){
            	if( jsonproductobject[selectedid].value == jsonproductobjectfinal[i].value){
            		flag = false;
            	}
            }
            if(flag){
            	jsonproductobjectfinal[modalcnt] = jsonproductobject[selectedid];
            	
            	var rowId = jsonproductobjectfinal[modalcnt].value;
            	$("#mainTable > tbody").append("<tr id='productrow-"+modalcnt+"'><td><input type='hidden' id='productrowid-"+jsonproductobjectfinal[modalcnt].value+"' value='"+modalcnt+"'></input>"+jsonproductobjectfinal[modalcnt].value+"</td><td>"+jsonproductobjectfinal[modalcnt].code+"</td>"
            			+"<td>"+jsonproductobjectfinal[modalcnt].name+"</br>(<a href='#' id='productremove-"+jsonproductobjectfinal[modalcnt].value+"' class='productremove'>delete</a>)</td>"
	            		+"<td><input type='text' id='productqty-"+jsonproductobjectfinal[modalcnt].value+"' class='productqty' value='"+jsonproductobjectfinal[modalcnt].quantity+"'></input></td>"
	            		+"<td><div id='producttotalrate-"+jsonproductobjectfinal[modalcnt].value+"'>"+jsonproductobjectfinal[modalcnt].rate+"</div></td>"
	            		+"<td valign='top' id='pTaxTitle-"+rowId+"'><div id='pTaxTotal-"+rowId+"'></div><div class='producttax' id='producttax-"+jsonproductobjectfinal[modalcnt].value+"'></div></td>"
	            		+"<td valign='top' id='pTSchemeTitle-"+rowId+"'><a class='open-AddBookDialog' data-toggle='modal' data-id='"+modalcnt+"' data-target='#schemeModal'>+ select</a><div id='pTScheme-"+rowId+"'></div><div class='pDiscountScheme' id='productselectedscheme-"+jsonproductobjectfinal[modalcnt].value+"'></div></td>"
	            		+"<td valign='top' id='pTQntyTitle-"+rowId+"'><div id='pTQnty-"+rowId+"'></div><div class='productTQty' id='producttotalqty-"+jsonproductobjectfinal[modalcnt].value+"'>Total Qty : 1</div></td>"
	            		+"<td id='pTAmtTitle-"+rowId+"'><div id='pTAmt-"+rowId+"'></div><div class='productamount' id='producttotalamount-"+jsonproductobjectfinal[modalcnt].value+"'></div></td></tr>");
            	
            	var strtax="";
	            var totaltax = 0;
	            
	            var totalschemeitems = 0;
	            var totalschemediscount = 0;
	            var taxTitle = "";
	            var spacer = "";
	            var totalamount = parseFloat((jsonproductobjectfinal[modalcnt].quantity*jsonproductobjectfinal[modalcnt].rate) ).toFixed(2);            
	            $.each(jsonproductobjectfinal[modalcnt].taxmasterlist, function(i, taxmaster) {
            	  strtax += "<div class='taxinnerleft'>"+taxmaster.tax_name+"</div><div class='taxinnerright'>"+taxmaster.tax_percentage+"%</div>";
            	  taxTitle += spacer+taxmaster.tax_name+"-"+taxmaster.tax_percentage+"%";
            	  spacer = ", \n";
            	  totaltax += taxmaster.tax_percentage;
            	}); 
	            var totaltaxamount = parseFloat(( totaltax / 100 ) * totalamount ).toFixed(2);
	            var totaldiscountamount = parseFloat(( totalschemediscount / 100 ) * totalamount).toFixed(2);           
		    	var tempvartotalprice = parseFloat( (parseFloat(totalamount) + parseFloat(totaltaxamount) ).toFixed(2) - parseFloat(totaldiscountamount)  ).toFixed(2);
	            var qty = jsonproductobjectfinal[modalcnt].quantity;
	            var totalitems = parseInt(qty)+parseInt(totalschemeitems);
	            
	            $("#pTaxTitle-"+rowId).attr("title",taxTitle);
	            $("#pTaxTotal-"+rowId).html(totaltaxamount);
	            $("#pTQnty-"+rowId).html(totalitems);
	            $("#pTAmt-"+rowId).html(tempvartotalprice);
	            $("#pTAmtTitle-"+rowId).attr("title","Amount- "+totalamount+", \nTax-"+totaltaxamount+", \nDiscount- "+totaldiscountamount);
	            $("#pTScheme-"+rowId).html(totaldiscountamount);
	            $("#pTQntyTitle-"+rowId).attr("title","Free item-"+totalschemeitems);
	            
	            $( "#producttax-"+jsonproductobjectfinal[modalcnt].value ).html(strtax + "<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+ totaltax +"%</div><div class='clearboth'></div>"+
	            		"<input id='producttotaltaxvalue-"+ jsonproductobjectfinal[modalcnt].value +"' type='hidden' value='"+totaltax+"' />");
	            $( "#producttotalamount-"+jsonproductobjectfinal[modalcnt].value ).html("<div class='taxinnerleft'>Amount</div><div class='taxinnerright'>"+totalamount+"</div>"+
	            		"<div class='taxinnerleft'>Tax</div><div class='taxinnerright'>"+totaltaxamount+"</div>"+
	            		"<div class='taxinnerleft'>Discount (-)</div><div class='taxinnerright'>"+totaldiscountamount+"</div>"+
	            		"<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+tempvartotalprice+"</div><div class='clearboth'></div>"+
	            		"<input id='producttotaldiscountvalue-"+ jsonproductobjectfinal[modalcnt].value +"' type='hidden' value='"+totaldiscountamount+"' />"+
	            		"<input id='producttotalprice-"+ jsonproductobjectfinal[modalcnt].value +"' type='hidden' value='"+tempvartotalprice+"' />");
		    	
	            $( "#producttotalqty-"+jsonproductobjectfinal[modalcnt].value ).html("<div class='taxinnerleft'>Items</div><div class='taxinnerright'>"+qty+"</div>"+
		    			"<div class='taxinnerleft'>Free Items</div><div class='taxinnerright'>"+totalschemeitems+"</div>"+
		    			"<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+totalitems+"</div><div class='clearboth'></div>"+
		    			"<input id='producttotalfreeitemvalue-"+ jsonproductobjectfinal[modalcnt].value +"' type='hidden' value='"+totalschemeitems+"' />"+
		    			"<input id='producttotalquantity-"+ jsonproductobjectfinal[modalcnt].value +"' type='hidden' value='"+totalitems+"' />");
		    	
	            if(modalcnt == 0){
	            	$("#mainTable > tfoot").append("<tr><td colspan='7'></td><td class='footertotals'><div id='grandquantity'></div></td><td class='footertotals'><div id='grandtotal'></div></td></tr>");
	            }
	            jsonproductobjectfinal[modalcnt].totalprice = tempvartotalprice;
	            jsonproductobjectfinal[modalcnt].totalqty = totalitems;
	            grandtotal = 0;
	            grandquantity = 0;	            
	            for (var i=0;i<jsonproductobjectfinal.length;i++){
	            	grandtotal = parseFloat( parseFloat(grandtotal) + parseFloat(jsonproductobjectfinal[i].totalprice) ).toFixed(2);
	            	grandquantity += jsonproductobjectfinal[i].totalqty;       	            	
	            }	            
	            $( "#grandtotal" ).html("<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+grandtotal+"</div><div class='clearboth'></div>");
	            $( "#grandquantity" ).html("<div class='taxinnerlefttotal'>Total</div><div class='taxinnerrighttotal'>"+grandquantity+"</div><div class='clearboth'></div>");
            	
            	modalcnt++;
            }
						
	        $('#productModal').modal('hide');
	        $("#divbutton").show();
	        e.preventDefault();
			return false;
	    });
		
		$('#customerModal .search-button').click(function(e){
			$.ajax({
				type: "POST",
				url: customernameurl,
				async: true,
				data: {
			        term: $('#modal-customer-name').val()
			    },
			    success: function(data){
			    	jsoncustomerobject = data;
			    	var tempStr="";
			    	tempStr += "<table class='customertable' border='1'><thead><tr><td>ID</td><td>Name</td><td>Pin</td><td>Phone</td><td>Mobile</td><td>Select</td></tr></thead><tbody>";
			    	for (var i=0;i<data.length;i++){ 
			    		tempStr += "<tr><td>"+data[i].customer_id+"</td><td>"+data[i].customer_name+"</td><td>"+data[i].customer_pin+"</td><td>"+data[i].customer_phone+"</td><td>"+data[i].customer_mobile+"</td><td><span class='modal-customer-link' data-id="+i+">Select</span></td></tr>";
		            }
			    	tempStr += "</tbody></table>";
                    $('#modal-customer-content').html(tempStr);
                }
			});
			e.preventDefault();
			return false;
		});
		
		$(document).on("click", "#customerModal .modal-customer-link", function (e) {
			var selectedid = $(this).attr('data-id');
			
			$("#customerid").val(jsoncustomerobject[selectedid].customer_id);
			$("#customer-name").val(jsoncustomerobject[selectedid].customer_name);
			
			$('#customerModal').modal('hide');
			e.preventDefault();
			return false;
	    });
	});	