<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Create Manufacturar</title>
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

<jsp:include page="../../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Create New Manufacturar</h3>
	  </div>
	  	<div class="panel-body"> 
		
		<spring:url value="/product/manufacturar/add" var="formUrl" />
		<spring:url value="/product/manufacturar/add.json" var="formJsonUrl" /> 
		<form:form modelAttribute="productmanufacturar" id="add-manufacturar-form" action="${formUrl}" method="POST" onsubmit="return submitFormFn();" >
		
			<div class="form-group" id="product_manufacturar_name_field">
			    <label for="product_manufacturar_name">Name</label>
			    <form:input path="product_manufacturar_name" id="product_manufacturar_name" class="form-control" placeholder="Name" />
			    <span class="help-inline errors"><form:errors path="product_manufacturar_name" /></span>
			</div>
			
			<div class="form-group" id="product_manufacturar_description_field">
			    <label for="product_manufacturar_description">Description</label>
			    <form:textarea path="product_manufacturar_description" class="form-control" placeholder="Description" />
			    <span class="help-inline errors"><form:errors path="product_manufacturar_description" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/product/manufacturar/dashboard" var="dashboardURL" />
		   		<a href="${dashboardURL}" type="button" class="btn btn-link">Cancel</a>
		   	</div>

		</form:form>

		</div>
	</div>
</div>
</div>

<jsp:include page="../../includes/footer.jsp"></jsp:include>

<spring:url value="/resources" var="resourcesURL" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${resourcesURL}/jquery/jquery-1.10.2.min.js"></script>
<script src="${resourcesURL}/jquery/jquery.dataTables.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourcesURL}/bootstrap/js/bootstrap.min.js"></script>
<script src="${resourcesURL}/keyBinderForm.js"></script>

<script type="text/javascript">
function collectFormData(fields, textareas) {
	var data = {};
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		data[$item.attr('name')] = $item.val();
	}
	for (var i = 0; i < textareas.length; i++) {
		var $item = $(textareas[i]);
		data[$item.attr('name')] = $item.val();
	}
	return data;
}
				
$(document).ready(function() {
	$('#product_manufacturar_name').focus();
});

function submitFormFn(){
	
	var $form = $('#add-manufacturar-form');
	
	// Ajax validation
	var $inputs = $form.find('input');
	var $textareas = $form.find('textarea');
	var data = collectFormData($inputs, $textareas);
	
	$.post('${formJsonUrl}', data, function(response) {
		$form.find('.help-inline').empty();		
		if (response.status == 'FAIL') {
			for (var i = 0; i < response.errorMessageList.length; i++) {
				var item = response.errorMessageList[i];
				var $controlGroup = $('#' + item.fieldName+"_field");					
				console.debug(item.fieldName);
				if(item.fieldName == "product_manufacturar_name"){
					$controlGroup.find('.help-inline').html("Please enter Manufacturar Name.");
				}else if(item.fieldName == "product_manufacturar_description"){
					$controlGroup.find('.help-inline').html("Please enter Manufacturar Description.");
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