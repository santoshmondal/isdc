<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Create Area</title>
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
	    <h3 class="panel-title">Create New Area</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/area/add" var="formUrl" />
		<spring:url value="/area/add.json" var="formJsonUrl" /> 
		<form:form modelAttribute="areamaster" id="add-area-form" action="${formUrl}" method="POST" onsubmit="return submitFormFn();">    
		
			<div class="form-group" id="area_name_field">
			    <label for="area_name">Name</label>
			    <form:input path="area_name" id="area_name" class="form-control" placeholder="Name" />
			    <span class="help-inline errors"><form:errors path="area_name" /></span>
			</div>
			
			<div class="form-group" id="area_city_field">
			    <label for="area_city">City</label>
			    <form:input path="area_city" class="form-control" placeholder="City" />
			    <span class="help-inline errors"><form:errors path="area_city" /></span>
			</div>
			
			<div class="form-group" id="area_state_field">
			    <label for="area_state">State</label>
			    <form:input path="area_state" class="form-control" placeholder="State" />
			    <span class="help-inline errors"><form:errors path="area_state" /></span>
			</div>
			
			<div class="form-group" id="area_country_field">
			    <label for="area_country">Country</label>
			    <form:input path="area_country" class="form-control" placeholder="Country" />
			    <span class="help-inline errors"><form:errors path="area_country" /></span>
			</div>
			
			<div class="form-group" id="area_description_field">
			    <label for="area_description">Description</label>
			    <form:textarea path="area_description" class="form-control" placeholder="Description" />
			    <span class="help-inline errors"><form:errors path="area_description" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/area/dashboard" var="areaDashboardURL" />
		   		<a href="${areaDashboardURL}" type="button" class="btn btn-link">Cancel</a>
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
	$('#area_name').focus();
});

function submitFormfn(){
	
	var $form = $('#add-area-form');
	
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
				if(item.fieldName == "area_name"){
					$controlGroup.find('.help-inline').html("Please enter Area Name.");
				}else if(item.fieldName == "area_city"){
					$controlGroup.find('.help-inline').html("Please enter Area City.");
				}else if(item.fieldName == "area_state"){
					$controlGroup.find('.help-inline').html("Please enter Area State.");
				}else if(item.fieldName == "area_country"){
					$controlGroup.find('.help-inline').html("Please enter Area Country.");
				}else if(item.fieldName == "area_description"){
					$controlGroup.find('.help-inline').html("Please enter Area Description.");
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