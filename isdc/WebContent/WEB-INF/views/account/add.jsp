<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Create Main Account</title>
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
	    <h3 class="panel-title">Create New User</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/account/add" var="formUrl" />
		<spring:url value="/account/add.json" var="formJsonUrl" /> 
		<form:form modelAttribute="mainaccount" id="add-account-form" action="${formUrl}" method="POST" onsubmit="return submitFormfn();">   
		
			<div class="form-group" id="main_account_code_field">
			    <label for="main_account_code">Code</label>
			    <form:input path="main_account_code" class="form-control" placeholder="Code" />
			    <span class="help-inline errors"><form:errors path="main_account_code" /></span>
			</div>
			
			<div class="form-group" id="main_account_username_field">
			    <label for="main_account_username">Username</label>
			    <form:input path="main_account_username" class="form-control" placeholder="Username" />
			    <span class="help-inline errors"><form:errors path="main_account_username" /></span>
			</div>
			
			<div class="form-group" id="main_account_password_field">
			    <label for="main_account_password">Password</label>
			    <form:password path="main_account_password" class="form-control" placeholder="Password" />
			    <span class="help-inline errors"><form:errors path="main_account_password" /></span>
			</div>
			
			<div class="form-group" id="main_account_location_field">
			    <label for="main_account_location">Location</label>
			    <form:input path="main_account_location" class="form-control" placeholder="Location" />
			    <span class="help-inline errors"><form:errors path="main_account_location" /></span>
			</div>
			
			<div class="form-group" id="main_account_description_field">
			    <label for="main_account_description">Description</label>
			    <form:textarea path="main_account_description" class="form-control" placeholder="Description" />
			    <span class="help-inline errors"><form:errors path="main_account_description" /></span>
			</div>
			
			<div class="form-group" id="authorizationrolelist_field">
			    <label for="authorizationrolelist">Roles</label>
			    <form:select multiple="true" class="form-control" path="authorizationrolelist" items="${authorizationrolearray}" itemValue="authorization_role_id" itemLabel="authorization_role_name"/>
			    <span class="help-inline errors"><form:errors path="authorizationrolelist" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" id="saveForm" class="btn btn-primary">Save</button>
		   		<spring:url value="/account/dashboard" var="taxDashboardURL" />
		   		<a href="${taxDashboardURL}" type="button" class="btn btn-link">Cancel</a>
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
	
	$('#main_account_code').focus();
	
	/* check username availability on focus lost */
	$('#main_account_username').blur(function() {
		
		if( $(this).val() != null && $(this).val().length > 0 ){
			$.getJSON("availability", { name:$(this).val() }, function(availability) {
		    	$('#main_account_username_field').find('.help-inline').empty();
		        if (availability) {	
		        	/*var $controlGroup = $('#main_account_username_field');
		        	$controlGroup.removeClass('error');
					$controlGroup.addClass('success');	*/						
		        } else {		        	
		        	var $controlGroup = $('#main_account_username_field');
		        	/*$controlGroup.removeClass('success');
					$controlGroup.addClass('error');*/
					$controlGroup.find('.help-inline').html("Username already registered. Please try another !");
		        }
		    });
		}			        
	});
											
});

function submitFormfn(){

	var $form = $('#add-account-form');
	
	// Ajax validation
	var data = $form.serialize();
	$.post('${formJsonUrl}', data, function(response) {
		$form.find('.help-inline').empty();		
		if (response.status == 'FAIL') {
			for (var i = 0; i < response.errorMessageList.length; i++) {
				var item = response.errorMessageList[i];
				var $controlGroup = $('#' + item.fieldName+"_field");					
				console.debug(item.fieldName);
				if(item.fieldName == "main_account_code"){
					$controlGroup.find('.help-inline').html("Please enter Code.");
				}else if(item.fieldName == "main_account_username"){
					$controlGroup.find('.help-inline').html("Please enter Username.");
				}else if(item.fieldName == "main_account_password"){
					$controlGroup.find('.help-inline').html("Please enter Password.");
				}else if(item.fieldName == "main_account_location"){
					$controlGroup.find('.help-inline').html("Please enter Location.");
				}else if(item.fieldName == "main_account_description"){
					$controlGroup.find('.help-inline').html("Please enter Description.");
				}else if(item.fieldName == "authorizationrolelist"){
					$controlGroup.find('.help-inline').html("Please select Roles.");
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