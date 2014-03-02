<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Update Tax</title>
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
	    <h3 class="panel-title">Update New Tax</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/tax/edit?id=${taxmaster.tax_id}" var="formUrl" />
		<spring:url value="/tax/edit.json" var="formJsonUrl" />  
		<form:form modelAttribute="taxmaster" id="edit-group-form" action="${formUrl}" method="POST" >    
		
			<form:hidden path="tax_id" />
			
			<div class="form-group" id="tax_name_field">
			    <label for="tax_name">Name</label>
			    <form:input path="tax_name" class="form-control" placeholder="Name" />
			    <span class="help-inline errors"><form:errors path="tax_name" /></span>
			</div>
			
			<div class="form-group" id="tax_percentage_field">
			    <label for="tax_percentage">Percentage</label>
			    <form:input path="tax_percentage" class="form-control" placeholder="Percentage" />
			    <span class="help-inline errors"><form:errors path="tax_percentage" /></span>
			</div>
			
			<div class="form-group" id="tax_description_field">
			    <label for="tax_description">Description</label>
			    <form:textarea path="tax_description" class="form-control" placeholder="Description" />
			    <span class="help-inline errors"><form:errors path="tax_description" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/tax/dashboard" var="taxDashboardURL" />
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
	$('#tax_name').focus();
	var $form = $('#edit-group-form');
	$form.bind('submit', function(e) {
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
					if(item.fieldName == "tax_name"){
						$controlGroup.find('.help-inline').html("Please enter Tax Name.");
					}else if(item.fieldName == "tax_percentage"){
						$controlGroup.find('.help-inline').html("Please enter Tax Percentage.");
					}else if(item.fieldName == "tax_description"){
						$controlGroup.find('.help-inline').html("Please enter Tax Description.");
					}								
				}
			} else {			
				/*$form.unbind('submit');								       
				$form.submit();*/
				window.location='dashboard?message=update-success';
			}
		}, 'json');
		
		e.preventDefault();
		return false;
	});
											
});			

</script>
</body>
</html>