<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Create Customer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="/resources/bootstrap/css/bootstrap.min.css" var="bootstrapCssUrl" />
<link href="${bootstrapCssUrl}" rel="stylesheet" media="screen">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<spring:url value="/resourcesbootstrap/assets/js/html5shiv.js" var="html5shivUrl" />
  	<script src="${html5shivUrl}"></script>
  	<spring:url value="/resourcesbootstrap/assets/js/respond.min.js" var="respondUrl" />
  	<script src="${respondUrl}"></script>
<![endif]-->
<spring:url value="/resources/css/stylesheet.css" var="cssUrl" />
<link href="${cssUrl}" rel="stylesheet" media="screen">
</head>
<body>

<jsp:include page="../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Create New Customer</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/customer/add" var="formUrl" />
		<spring:url value="/customer/add.json" var="formJsonUrl" /> 
		<spring:url value="/customer/select.json" var="selectJsonUrl" /> 
		<form:form modelAttribute="customermaster" id="add-customer-form" action="${formUrl}" method="POST" onsubmit="return submitFormfn();">    
		
			<div class="form-group" id="customer_name_field">
			    <label for="customer_name">Name</label>
			    <form:input path="customer_name" class="form-control" placeholder="Name" />
			    <span class="help-inline errors"><form:errors path="customer_name" /></span>
			</div>
			
			<div class="form-group" id="customer_address_field">
			    <label for="customer_address">Address</label>
			    <form:textarea path="customer_address" class="form-control" placeholder="Address" />
			    <span class="help-inline errors"><form:errors path="customer_address" /></span>
			</div>
			
			<div class="form-group" id="customer_pin_field">
			    <label for="customer_pin">Pin</label>
			    <form:input path="customer_pin" class="form-control" placeholder="Pin" />
			    <span class="help-inline errors"><form:errors path="customer_pin" /></span>
			</div>
			
			<div class="form-group" id="customer_phone_field">
			    <label for="customer_phone">Phone</label>
			    <form:input path="customer_phone" class="form-control" placeholder="Phone" />
			    <span class="help-inline errors"><form:errors path="customer_phone" /></span>
			</div>
			
			<div class="form-group" id="customer_mobile_field">
			    <label for="customer_mobile">Mobile</label>
			    <form:input path="customer_mobile" class="form-control" placeholder="Mobile" />
			    <span class="help-inline errors"><form:errors path="customer_mobile" /></span>
			</div>
			
			<div class="form-group" id="customer_remark_field">
			    <label for="customer_remark">Remark</label>
			    <form:input path="customer_remark" class="form-control" placeholder="Remark" />
			    <span class="help-inline errors"><form:errors path="customer_remark" /></span>
			</div>
			
			<div class="form-group" id="customer_category_field">
			    <label for="customer_category">Category</label>
			    <form:input path="customer_category" class="form-control" placeholder="Category" />
			    <span class="help-inline errors"><form:errors path="customer_category" /></span>
			</div>
			
			<div class="form-group" id="customer_blacklist_field">
			    <label for="customer_blacklist">Blacklist</label>
			    <form:select path="customer_blacklist" class="form-control">
			    	<form:option value="true">True</form:option>
			    	<form:option value="false">False</form:option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="customer_blacklist" /></span>
			</div>
			
			<div class="form-group" id="customer_class_field">
			    <label for="customer_class">Class</label>
			    <form:input path="customer_class" class="form-control" placeholder="Class" />
			    <span class="help-inline errors"><form:errors path="customer_class" /></span>
			</div>
			
			<div class="form-group" id="customer_key_field">
			    <label for="customer_key">Key</label>
			    <form:select path="customer_key" class="form-control">
			    	<form:option value="true">True</form:option>
			    	<form:option value="false">False</form:option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="customer_key" /></span>
			</div>
			
			<div class="form-group" id="customer_rate_field">
			    <label for="customer_rate">Rate</label>
			    <form:input path="customer_rate" class="form-control" placeholder="Rate" />
			    <span class="help-inline errors"><form:errors path="customer_rate" /></span>
			</div>
			
			<div class="form-group" id="customer_discount_field">
			    <label for="customer_discount">Discount</label>
			    <form:input path="customer_discount" class="form-control" placeholder="Discount (numeric)" />
			    <span class="help-inline errors"><form:errors path="customer_discount" /></span>
			</div>
			
			<div class="form-group" id="ccustomer_credit_days_field">
			    <label for="customer_credit_days">Credit Days</label>
			    <form:input path="customer_credit_days" class="form-control" placeholder="Credit Days (numeric)" />
			    <span class="help-inline errors"><form:errors path="customer_credit_days" /></span>
			</div>
			
			<div class="form-group" id="customer_credit_limit_field">
			    <label for="customer_credit_limit">Credit Limit</label>
			    <form:input path="customer_credit_limit" class="form-control" placeholder="Credit Limit (numeric)" />
			    <span class="help-inline errors"><form:errors path="customer_credit_limit" /></span>
			</div>
			
			<div class="form-group" id="areamasterstring_field">
			    <label for="areamasterstring">Area</label>
			    <form:select multiple="false" class="form-control" path="areamasterstring" items="${areamasterarray}" itemValue="area_id" itemLabel="area_name"/>
			    <span class="help-inline errors"><form:errors path="areamasterstring" /></span>
			</div>
			
			<div class="form-group" id="beatmasterstring_field">
			    <label for="beatmasterstring">Beat</label>
			    <form:select multiple="false" class="form-control" path="beatmasterstring" items="${beatmasterarray}" itemValue="beat_id" itemLabel="beat_name"/>
			    <!--<form:select multiple="false" class="form-control" path="beatmasterstring"/>-->
			    <span class="help-inline errors"><form:errors path="beatmasterstring" /></span>
			</div>
				
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/customer/dashboard" var="customerDashboardURL" />
		   		<a href="${customerDashboardURL}" type="button" class="btn btn-link">Cancel</a>
		   	</div>

		</form:form>

		</div>
	</div>
</div>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>

<spring:url value="/resources" var="resourcesURL" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${resourcesURL}/jquery/jquery-1.10.2.min.js"></script>
<script src="${resourcesURL}/jquery/jquery.dataTables.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourcesURL}/bootstrap/js/bootstrap.min.js"></script>
<script src="${resourcesURL}/keyBinderForm.js"></script>

<script type="text/javascript">
				
$(document).ready(function() {
	$('#customer_name').focus();

	$('#areamasterstring').change(function(){
		var str = "";
		$( "#areamasterstring option:selected" ).each(function() {
		    str = $( this ).val();
		});
		var data = {id: str};
		$.getJSON("${selectJsonUrl}", data ,function(response){
			if (response.status == 'SUCCESS') {
				if(response.errorMessageList.length > 0){
					$('#beatmasterstring').empty(); 
				    for (var i = 0; i < response.errorMessageList.length; i++) {
						var item = response.errorMessageList[i];				
						console.debug("debugloop1: "+item.fieldName+" : "+item.message);
						$('#beatmasterstring').append($("<option></option>").attr("value", item.fieldName).text(item.message));
				    }
				}else{
					$('#beatmasterstring').empty(); 
					$('#beatmasterstring').append($("<option></option>").attr("value", "0").text("None"));
				}
			}else{
				$('#beatmasterstring').empty(); 
				$('#beatmasterstring').append($("<option></option>").attr("value", "0").text("None"));
			}
		  });
	});
	
});

function submitFormfn(){
	
	var $form = $('#add-customer-form');
	
	// Ajax validation
	var data = $form.serialize();
	
	$.post('${formJsonUrl}', data, function(response) {
		$form.find('.help-inline').empty();		
		if (response.status == 'FAIL') {
			for (var i = 0; i < response.errorMessageList.length; i++) {
				var item = response.errorMessageList[i];
				var $controlGroup = $('#' + item.fieldName+"_field");					
				console.debug(item.fieldName);
				if(item.fieldName == "customer_name"){
					$controlGroup.find('.help-inline').html("Please enter Customer Name.");
				}else if(item.fieldName == "customer_address"){
					$controlGroup.find('.help-inline').html("Please enter Customer Address.");
				}else if(item.fieldName == "customer_pin"){
					$controlGroup.find('.help-inline').html("Please enter Customer Pin.");
				}else if(item.fieldName == "customer_phone"){
					$controlGroup.find('.help-inline').html("Please enter Customer Phone.");
				}else if(item.fieldName == "customer_mobile"){
					$controlGroup.find('.help-inline').html("Please enter Customer Mobile.");
				}else if(item.fieldName == "customer_remark"){
					$controlGroup.find('.help-inline').html("Please enter Customer Remark.");
				}else if(item.fieldName == "customer_category"){
					$controlGroup.find('.help-inline').html("Please enter Customer Category.");
				}else if(item.fieldName == "customer_blacklist"){
					$controlGroup.find('.help-inline').html("Please enter Customer Blacklist.");
				}else if(item.fieldName == "customer_class"){
					$controlGroup.find('.help-inline').html("Please enter Customer Class.");
				}else if(item.fieldName == "customer_key"){
					$controlGroup.find('.help-inline').html("Please enter Customer Key.");
				}else if(item.fieldName == "customer_rate"){
					$controlGroup.find('.help-inline').html("Please enter Customer Rate.");
				}else if(item.fieldName == "customer_discount"){
					$controlGroup.find('.help-inline').html("Please enter Customer Discount.");
				}else if(item.fieldName == "customer_credit_days"){
					$controlGroup.find('.help-inline').html("Please enter Customer Credit Days.");
				}else if(item.fieldName == "customer_credit_limit"){
					$controlGroup.find('.help-inline').html("Please enter Customer Credit Limit.");
				}else if(item.fieldName == "areamaster"){
					$controlGroup.find('.help-inline').html("Please enter Customer Area.");
				}else if(item.fieldName == "beatmaster"){
					$controlGroup.find('.help-inline').html("Please enter Customer Beat.");
				}								
			}
		} else {			
			/*$form.unbind('submit');								       
			$form.submit();*/
			window.location='dashboard?message=create-success';
		}
	}, 'json');
	
	e.preventDefault();
	return false;
}		

</script>
</body>
</html>