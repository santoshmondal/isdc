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
<title>e-Distribution and Channel Management | Update Customer Balance</title>
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

<jsp:include page="../../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Update Customer Balance</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/customer/balance/edit?customerid=${param.customerid}&id=${param.id}" var="formUrl" />
		<spring:url value="/customer/balance/edit.json" var="formJsonUrl" /> 
		<form:form modelAttribute="customerbalance" id="edit-balance-form" action="${formUrl}" method="POST" >    
		
			<form:hidden path="customermasterinteger"/>
			<form:hidden path="balance_id"/>
		
			<div class="form-group" id="balance_party_field">
			    <label for="balance_party">Party</label>
			    <form:input path="balance_party" class="form-control" placeholder="Party" />
			    <span class="help-inline errors"><form:errors path="balance_party" /></span>
			</div>
			
			<div class="form-group" id="balance_debit_credit_field">
			    <label for="balance_debit_credit">Debit/Credit</label>
			    <form:select path="balance_debit_credit" class="form-control">
			    	<form:option value="true">Debit</form:option>
			    	<form:option value="false">Credit</form:option>
			    </form:select>
			    <span class="help-inline errors"><form:errors path="balance_debit_credit" /></span>
			</div>
			
			<div class="form-group" id="balance_amount_field">
			    <label for="balance_amount">Amount</label>
			    <form:input path="balance_amount" class="form-control" placeholder="Amount (numeric)" />
			    <span class="help-inline errors"><form:errors path="balance_amount" /></span>
			</div>
			
			<div class="form-group" id="balance_date_field">
			    <label for="balance_date">Date</label>
			    <fmt:formatDate value="${customerbalance.balance_date}" var="formattedDate" type="date" pattern="MM/dd/yyyy" />
			    <!-- date picker start -->
				  <div class="bfh-datepicker" data-format="m/d/y" data-date="<core:out value="${formattedDate}"></core:out>">
				<div class="input-group bfh-datepicker-toggle" data-toggle="bfh-datepicker">
				 <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				 <form:input path="balance_date" class="form-control" placeholder="Date (DD/MM/YYYY)" readonly="true" />
				  </div>
				  <div class="bfh-datepicker-calendar">
				    <table class="calendar table table-bordered">
				      <thead>
				        <tr class="months-header">
				          <th class="month" colspan="4">
				            <a class="previous" href="#"><i class="glyphicon glyphicon-chevron-left"></i></a>
				            <span></span>
				            <a class="next" href="#"><i class="glyphicon glyphicon-chevron-right"></i></a>
				          </th>
				          <th class="year" colspan="3">
				            <a class="previous" href="#"><i class="glyphicon glyphicon-chevron-left"></i></a>
				            <span></span>
				            <a class="next" href="#"><i class="glyphicon glyphicon-chevron-right"></i></a>
				          </th>
				        </tr>
				        <tr class="days-header">
				        </tr>
				      </thead>
				      <tbody>
				      </tbody>
				    </table>
				  </div>
				</div>
			   <!-- date picker end --> 
			    
			    <span class="help-inline errors"><form:errors path="balance_date" /></span>
			</div>
			
			<div class="form-group" id="balance_description_field">
			    <label for="balance_description">Description</label>
			    <form:textarea path="balance_description" class="form-control" placeholder="Description" />
			    <span class="help-inline errors"><form:errors path="balance_description" /></span>
			</div>
			
			<div class="form-group">
		   		<button type="submit" class="btn btn-primary">Save</button>
		   		<spring:url value="/customer/balance/dashboard?customerid=${param.customerid}" var="areaDashboardURL" />
		   		<a href="${areaDashboardURL}" type="button" class="btn btn-link">Cancel</a>
		   	</div>

		</form:form>

		</div>
	</div>
</div>
</div>

<jsp:include page="../../includes/footer.jsp"></jsp:include>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<spring:url value="/resources/jquery/jquery-1.10.2.min.js" var="jqueryURL" />
<script src="${jqueryURL}"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/bootstrap/js/bootstrap.min.js" var="bootstrapJSURL" />
<script src="${bootstrapJSURL}"></script>
<spring:url value="/resources/bootstrap/datepicker/js/bootstrap-formhelpers.min.js" var="datepickerJSURL" />
<script src="${datepickerJSURL}"></script>
</body>
</html>