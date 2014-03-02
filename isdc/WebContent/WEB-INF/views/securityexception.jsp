<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>e-Distribution and Channel Management | Security</title>
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
<div class="container">
		<spring:url value="/securityexception" var="formUrl" />
		<c:if test="${message=='fail'}">												
		  	<div class="alert alert-danger">
		        <strong>Oops!</strong> Invalid username or password.
		   	</div>					
		</c:if>
		<c:if test="${keyerror != null }">												
		  	<div class="alert alert-danger">
		        <strong>Oops!</strong> ${keyerror}
		   	</div>					
		</c:if>
		<c:if test="${param.keyerror != null }">												
		  	<div class="alert alert-danger">
		        <strong>Oops!</strong> ${param.keyerror}
		   	</div>					
		</c:if>	
		<c:if test="${sessionScope.securitycode == 2 }">
			<div class="alert alert-danger">
		        <strong>Installation!</strong> Please enter your security key or contact Customer Support.
		   	</div>	
		</c:if>	
		<c:if test="${sessionScope.securitycode == 4 }">
			<div class="alert alert-danger">
		        <strong>Key Expired!</strong> Please enter your new security key or contact Customer Support.
		   	</div>	
		</c:if>	
		<c:if test="${sessionScope.securitycode != 3 }">			
		<form:form modelAttribute="settings" id="edit-key-form" action="${formUrl}" method="POST"  class='form-signin'>    		
			<form:hidden path="settings_id" />			
			<h2 class="form-signin-heading">Application key</h2>			
			<form:input path="settings_value" class="form-control" placeholder="Security Key" />
			<span class="help-inline errors"><form:errors path="settings_value" /></span>
			<button class="btn btn-lg btn-primary btn-block" type="submit" style="margin-top:10px;">Sign in</button>
		</form:form>
		</c:if>		
		<c:if test="${sessionScope.securitycode == 3 }">
			<div class="alert alert-danger">
		        <strong>Error!</strong> There seems to be an error with the security keys please contact Customer Support.
		   	</div>	
		</c:if>			
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<spring:url value="/resources/jquery/jquery-1.10.2.min.js" var="jqueryURL" />
<script src="${jqueryURL}"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/bootstrap/js/bootstrap.min.js" var="bootstrapJSURL" />
<script src="${bootstrapJSURL}"></script>
</body>
</html>