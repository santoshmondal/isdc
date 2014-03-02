<%@page import="com.isdc.app.util.Constants"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Update Product</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="/resources/bootstrap/css/bootstrap.min.css" var="bootstrapCssUrl" />
<link href="${bootstrapCssUrl}" rel="stylesheet" media="screen">
<spring:url value="/resources/bootstrap/datepicker/css/bootstrap-formhelpers.min.css" var="datepickerCssUrl" />
<link href="${datepickerCssUrl}" rel="stylesheet" media="screen">
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
	    <h3 class="panel-title">Update Product</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/product/edit?id=${productmaster.product_master_id}" var="formUrl" />
		<spring:url value="/product/edit.json" var="formJsonUrl" /> 
		<form:form modelAttribute="productmaster" id="edit-product-form" action="${formUrl}" method="POST" > 
		
			<form:hidden path="product_master_id" />
			<div class="form-group" id="product_master_code_field">
			    <label for="product_master_code">Code</label>
			    <form:input path="product_master_code" class="form-control" placeholder="Code" />
			    <span class="help-inline errors"><form:errors path="product_master_code" /></span>
			</div>
			
			<div class="form-group" id="product_master_name_field">
			    <label for="product_master_name">Name</label>
			    <form:input path="product_master_name" class="form-control" placeholder="Name" />
			    <span class="help-inline errors"><form:errors path="product_master_name" /></span>
			</div>
			
			<div class="form-group" id="product_master_alias_field">
			    <label for="product_master_alias">Alias</label>
			    <form:input path="product_master_alias" class="form-control" placeholder="Alias" />
			    <span class="help-inline errors"><form:errors path="product_master_alias" /></span>
			</div>
			
			<div class="form-group" id="product_master_weight_field">
			    <label for="product_master_weight">Weight</label>
			    <form:input path="product_master_weight" class="form-control" placeholder="Weight" />
			    <span class="help-inline errors"><form:errors path="product_master_weight" /></span>
			</div>
					
			<div class="form-group" id="product_master_margin_field">
			    <label for="product_master_margin">Margin</label>
			    <form:input path="product_master_margin" class="form-control" placeholder="Margin (numeric value)" />
			    <span class="help-inline errors"><form:errors path="product_master_margin" /></span>
			</div>
			
			<div class="form-group" id="product_master_mrp_field">
			    <label for="product_master_mrp">MRP</label>
			    <form:input path="product_master_mrp" class="form-control" placeholder="MRP (numeric value)" />
			    <span class="help-inline errors"><form:errors path="product_master_mrp" /></span>
			</div>
			
			<div class="form-group" id="product_master_purchase_rate_field">
			    <label for="product_master_purchase_rate">Purchase Rate</label>
			    <form:input path="product_master_purchase_rate" class="form-control" placeholder="Purchase Rate (numeric value)" />
			    <span class="help-inline errors"><form:errors path="product_master_purchase_rate" /></span>
			</div>
			
			<div class="form-group" id="product_master_sale_rate_field">
			    <label for="product_master_sale_rate">Sale Rate</label>
			    <form:input path="product_master_sale_rate" class="form-control" placeholder="Sale Rate (numeric value)" />
			    <span class="help-inline errors"><form:errors path="product_master_sale_rate" /></span>
			</div>
			
			<div class="form-group" id="product_master_purchase_unit_field">
			    <label for="product_master_purchase_unit">Purchase Unit</label>
			    <form:select path="product_master_purchase_unit" class="form-control">
			    	<option value="<%=Constants.UNIT_BOX%>">Box</option>
			    	<option value="<%=Constants.UNIT_PIECE%>">Piece</option>
			    	<option value="<%=Constants.UNIT_KG%>">KG</option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="product_master_purchase_unit" /></span>
			</div>
			
			<div class="form-group" id="product_master_unit_field">
			    <label for="product_master_sales_unit">Sales Unit</label>
			    <form:select path="product_master_sales_unit" class="form-control">
			    	<option value="<%=Constants.UNIT_BOX%>">Box</option>
			    	<option value="<%=Constants.UNIT_PIECE%>">Piece</option>
			    	<option value="<%=Constants.UNIT_KG%>">KG</option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="product_master_sales_unit" /></span>
			</div>
			
			
			<div class="form-group" id="product_master_pack_field">
			    <label for="product_master_pack">Pack (number of items in a box)</label>
			    <form:input path="product_master_pack" class="form-control" placeholder="Pack" />
			    <span class="help-inline errors"><form:errors path="product_master_pack" /></span>
			</div>
			
			<div class="form-group" id="product_master_lock_item_field">
			    <label for="product_master_lock_item">Lock Item</label>
			    <form:select path="product_master_lock_item" class="form-control">
			    	<option value="true">Yes</option>
			    	<option value="false">No</option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="product_master_lock_item" /></span>
			</div>
			
			
			<div class="form-group" id="product_master_opening_stock_field">
			    <label for="product_master_opening_stock">Opening Stock</label>
			    <form:input path="product_master_opening_stock" class="form-control" placeholder="Opening (numeric value)" />
			    <span class="help-inline errors"><form:errors path="product_master_opening_stock" /></span>
			</div>
		
			<div class="form-group" id="product_master_batch_number_field">
			    <label for="product_master_batch_number">Batch Number</label>
			    <form:input path="product_master_batch_number" class="form-control" placeholder="Batch Number" />
			    <span class="help-inline errors"><form:errors path="product_master_batch_number" /></span>
			</div>
			
			<div class="form-group" id="product_master_expiry_date_field">
			    <label for="product_master_expiry_date">Expiry Date</label>
			    <fmt:formatDate value="${productmaster.product_master_expiry_date}" var="formattedDate" type="date" pattern="MM/dd/yyyy" />
			    <form:input value="${formattedDate}" path="product_master_expiry_date" class="form-control" placeholder="Expiry Date (DD/MM/YYYY)" />
			    <span class="help-inline errors"><form:errors path="product_master_expiry_date" /></span>
			</div>
			
			<div class="form-group" id="product_master_vat_type_field">
			    <label for="product_master_vat_type">Vat Type</label>
			    <form:select path="product_master_vat_type" class="form-control">
			    	<option value="1">Calculate tax on price before deducting discount price</option>
			    	<option value="2">Calculate tax on price after deducting discount price</option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="product_master_vat_type" /></span>
			</div>

			<div class="form-group" id="productmanufacturarstring_field">
			    <label for="productmanufacturarstring">Manufacturar</label>
			    <!--<form:select multiple="false" class="form-control" path="productmanufacturarstring" items="${productmanufacturararray}" itemValue="product_manufacturar_id" itemLabel="product_manufacturar_name"/>-->
			    <form:select multiple="single" path="productmanufacturarstring" class="form-control" >
			    	<core:forEach items="${productmanufacturararray}" var="productmanufacturar" varStatus="loop">
			    		<core:set var="manufacturarselected" value=" "></core:set>			    		
			    		<core:set var="manufacturarselected" value="${productmaster.productmanufacturar.product_manufacturar_id == productmanufacturar.product_manufacturar_id ? 'selected' : ' '}"></core:set>
			    		<form:option value="${productmanufacturar.product_manufacturar_id}" label="${productmanufacturar.product_manufacturar_name}" selected="${manufacturarselected}"/>		    		
			    	</core:forEach>
				</form:select>	
			    <span class="help-inline errors"><form:errors path="productmanufacturarstring" /></span>
			</div>
			
			<div class="form-group" id="productgroupstring_field">
			    <label for="productgroupstring">Group</label>
			    <!--<form:select multiple="false" class="form-control" path="productgroupstring" items="${productgrouparray}" itemValue="product_group_id" itemLabel="product_group_name"/>-->	    
			    <form:select multiple="single" path="productgroupstring" class="form-control" >
			    	<core:forEach items="${productgrouparray}" var="productgroup" varStatus="loop">
			    		<core:set var="groupselected" value=" "></core:set>			    		
			    		<core:set var="groupselected" value="${productmaster.productgroup.product_group_id == productgroup.product_group_id ? 'selected' : ' '}"></core:set>
			    		<form:option value="${productgroup.product_group_id}" label="${productgroup.product_group_name}" selected="${groupselected}"/>		    		
			    	</core:forEach>
				</form:select>				
			    <span class="help-inline errors"><form:errors path="productgroupstring" /></span>
			</div>
			
			<div class="form-group" id="productsubgroupstring_field">
			    <label for="productsubgroupstring">Sub Group</label>
			    <!--<form:select multiple="false" class="form-control" path="productsubgroupstring" items="${productsubgrouparray}" itemValue="product_sub_group_id" itemLabel="product_sub_group_name"/>-->
			    <form:select multiple="single" path="productsubgroupstring" class="form-control" >
			    	<core:forEach items="${productsubgrouparray}" var="productsubgroup" varStatus="loop">
			    		<core:set var="subgroupselected" value=" "></core:set>			    		
			    		<core:set var="subgroupselected" value="${productmaster.productsubgroup.product_sub_group_id == productsubgroup.product_sub_group_id ? 'selected' : ' '}"></core:set>
			    		<form:option value="${productsubgroup.product_sub_group_id}" label="${productsubgroup.product_sub_group_name}" selected="${subgroupselected}"/>		    		
			    	</core:forEach>
				</form:select>	
			    <span class="help-inline errors"><form:errors path="productsubgroupstring" /></span>
			</div>
			
			<div class="form-group" id="taxmasterlist_field">			    
			    <form:select multiple="multiple" path="taxmasterlist" class="form-control" >
			    	<core:forEach items="${taxmasterarray}" var="producttax" varStatus="loop">	
			    		<form:option value="${producttax.tax_id}" label="${producttax.tax_name}" selected="${producttax.selected_status}"/>		    		
			    	</core:forEach>
				</form:select>	
			    <span class="help-inline errors"><form:errors path="taxmasterlist" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/product/dashboard" var="taxDashboardURL" />
		   		<a href="${taxDashboardURL}" type="button" class="btn btn-link">Cancel</a>
		   	</div>

		</form:form>

		</div>
	</div>
</div>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<spring:url value="/resources/jquery/jquery-1.10.2.min.js" var="jqueryURL" />
<script src="${jqueryURL}"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/bootstrap/js/bootstrap.min.js" var="bootstrapJSURL" />
<script src="${bootstrapJSURL}"></script>
<spring:url value="/resources/bootstrap/datepicker/js/bootstrap-formhelpers.min.js" var="datepickerJSURL" />
<script src="${datepickerJSURL}"></script>

<script type="text/javascript">
function collectFormData(fields) {
	var data = {};
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		data[$item.attr('name')] = $item.val();
	}
	return data;
}
				
$(document).ready(function() {
	$('#product_master_code').focus();
	
	var $form = $('#edit-product-form');
	$form.bind('submit', function(e) {
		// Ajax validation
		var $inputs = $form.find('input');
		var data = collectFormData($inputs);
		
		$.post('${formJsonUrl}', data, function(response) {
			$form.find('.help-inline').empty();		
			if (response.status == 'FAIL') {
				for (var i = 0; i < response.errorMessageList.length; i++) {
					var item = response.errorMessageList[i];
					var $controlGroup = $('#' + item.fieldName+"_field");					
					console.debug(item.fieldName);
					if(item.fieldName == "product_master_code"){
						$controlGroup.find('.help-inline').html("Please enter Code.");
					}else if(item.fieldName == "product_master_name"){
						$controlGroup.find('.help-inline').html("Please enter Name.");
					}else if(item.fieldName == "product_master_alias"){
						$controlGroup.find('.help-inline').html("Please enter Alias.");
					}else if(item.fieldName == "product_master_weight"){
						$controlGroup.find('.help-inline').html("Please enter Weight.");
					}else if(item.fieldName == "product_master_margin"){
						$controlGroup.find('.help-inline').html("Please enter Margin.");
					}else if(item.fieldName == "product_master_opening_stock"){
						$controlGroup.find('.help-inline').html("Please enter Opening Stock.");
					}else if(item.fieldName == "product_master_mrp"){
						$controlGroup.find('.help-inline').html("Please enter MRP.");
					}else if(item.fieldName == "product_master_sale_rate"){
						$controlGroup.find('.help-inline').html("Please enter Sale Rate.");
					}else if(item.fieldName == "product_master_purchase_unit"){
						$controlGroup.find('.help-inline').html("Please enter Purchase Unit.");
					}else if(item.fieldName == "product_master_purchase_rate"){
						$controlGroup.find('.help-inline').html("Please enter Purchase Rate.");
					}else if(item.fieldName == "product_master_pack"){
						$controlGroup.find('.help-inline').html("Please enter Pack.");
					}else if(item.fieldName == "product_master_lock_item"){
						$controlGroup.find('.help-inline').html("Please enter Lock Item.");
					}else if(item.fieldName == "product_master_unit"){
						$controlGroup.find('.help-inline').html("Please enter Unit.");
					}else if(item.fieldName == "product_master_batch_number"){
						$controlGroup.find('.help-inline').html("Please enter Batch Number.");
					}else if(item.fieldName == "product_master_expiry_date"){
						$controlGroup.find('.help-inline').html("Please enter Expiry Date.");
					}else if(item.fieldName == "product_master_vat_type"){
						$controlGroup.find('.help-inline').html("Please enter Vat Type.");
					}else if(item.fieldName == "productmanufacturarstring"){
						$controlGroup.find('.help-inline').html("Please select Manufacturar.");
					}else if(item.fieldName == "productgroupstring"){
						$controlGroup.find('.help-inline').html("Please select Group.");
					}else if(item.fieldName == "productsubgroupstring"){
						$controlGroup.find('.help-inline').html("Please select Sub Group.");
					}else if(item.fieldName == "taxmasterlist"){
						$controlGroup.find('.help-inline').html("Please select Tax.");
					}								
				}
			} else {			
				$form.unbind('submit');								       
				$form.submit();
				//window.location='dashboard?message=create-success';
			}
		}, 'json');
		
		e.preventDefault();
		return false;
	});
											
});			

</script>
</body>
</html>