<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Customer Balance Dashboard</title>
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
<spring:url value="/resources/css/data_table.css" var="cssDataTableUrl" />
<link href="${cssDataTableUrl}" rel="stylesheet" media="screen">
<spring:url value="/resources/css/stylesheet.css" var="cssUrl" />
<link href="${cssUrl}" rel="stylesheet" media="screen">
</head>
<body>

<jsp:include page="../../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <div class="floatleft"><h3 class="panel-title">Customer Balance Dashboard</h3></div>
	    <spring:url value="/customer/balance/add?customerid=${param.customerid}" var="addURL" />
	    <div class="floatright"><a href="${addURL}" class="btn btn-sm btn-primary"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;&nbsp;&nbsp;Create New</a></div>
	    <div class="clearboth"></div>
	  </div>
	  <div class="panel-body"> 
	  
	  	<core:if test="${param.message=='create-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Create customer balance entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='update-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Update customer balance entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='delete-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Delete customer balance entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='delete-fail'}">												
		  	<div class="alert alert-danger">
		        <strong>Error!</strong> Delete customer balance entry request could not be completed, the customer balance is assigned to a product.
		   	</div>					
		</core:if>

		<table cellpadding="0" cellspacing="0" border="0" class="display" id="dashboard" width="100%">
			<thead>
				<tr>
					<th>ID</th>
					<th>Party</th>
					<th>Debit/Credit</th>
					<th>Amount</th>
					<th>Date</th>					
					<th>Created Date</th>
					<th>Updated Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
			<core:forEach items="${customerbalancelist}" var="customerbalancelist">
				<tr class="odd gradeU">
					<td><core:out value="${customerbalancelist.balance_id}" /></td>
					<td><core:out value="${customerbalancelist.balance_party}" /></td>
					<td><core:out value="${customerbalancelist.balance_debit_credit}" /></td>
					<td><core:out value="${customerbalancelist.balance_amount}" /></td>
					<td><fmt:formatDate value="${customerbalancelist.balance_date}" pattern="dd-MM-yyyy"/></td>
					<td><fmt:formatDate value="${customerbalancelist.balance_created_date}" pattern="dd-MM-yyyy"/></td>
					<td><fmt:formatDate value="${customerbalancelist.balance_updated_date}" pattern="dd-MM-yyyy"/></td>
					<spring:url value="/customer/balance/edit?id=${customerbalancelist.balance_id}&customerid=${param.customerid}" var="editURL" />
					<spring:url value="/customer/balance/delete?id=${customerbalancelist.balance_id}&customerid=${param.customerid}" var="deleteURL" />
					<td>
						<a href="${editURL}"><span class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
						<a href="${deleteURL}"><span class="glyphicon glyphicon-remove-circle"></span></a>
					</td>			
				</tr>
			</core:forEach>
			</tbody>
			
		</table>

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
<script src="${resourcesURL}/keyBinderDashboard.js"></script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#dashboard').dataTable();
	} );
</script>

</body>
</html>