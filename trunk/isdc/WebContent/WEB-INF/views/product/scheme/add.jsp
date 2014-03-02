<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Create Product Scheme</title>
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
	    <h3 class="panel-title">Create Product Scheme</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/product/scheme/add?productid=${param.productid}" var="formUrl" />
		<spring:url value="/product/scheme/add.json" var="formJsonUrl" /> 
		<form:form modelAttribute="productscheme" id="add-scheme-form" action="${formUrl}" method="POST" onsubmit="return submitFormFn();">    
		
			<form:hidden path="productmasterinteger"/>
		
			<div class="form-group" id="scheme_name_field">
			    <label for="scheme_name">Name</label>
			    <form:input path="scheme_name" id="scheme_name" class="form-control" placeholder="Name" />
			    <span class="help-inline errors"><form:errors path="scheme_name" /></span>
			</div>
			
			<div class="form-group" id="scheme_type_field">
			    <label for="scheme_type">Type</label>
			    <form:select path="scheme_type" class="form-control">
			    	<form:option value="0">Percentage Discount</form:option>
			    	<form:option value="1">Free Item</form:option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="scheme_type" /></span>
			</div>
			
			<div class="form-group" id="scheme_value_field">
			    <label for="scheme_value">Value</label>
			    <form:input path="scheme_value" class="form-control" placeholder="Value (numeric)" />
			    <span class="help-inline errors"><form:errors path="scheme_value" /></span>
			</div>
			
			<div class="form-group" id="scheme_qty_field">
			    <label for="scheme_qty">Quantity</label>
			    <form:input path="scheme_qty" class="form-control" placeholder="Quantity (numeric)" />
			    <span class="help-inline errors"><form:errors path="scheme_qty" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/product/scheme/dashboard?productid=${param.productid}" var="areaDashboardURL" />
		   		<a href="${areaDashboardURL}" type="button" class="btn btn-link">Cancel</a>
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
$(document).ready(function() {
	$('#scheme_name').focus();
});

function submitFormfn(){
	document.getElementById('add-scheme-form').submit();
}
</script>


</body>
</html>