<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Purchase Master Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="/resources" var="resourcesUrl" />
<link href="${resourcesUrl}/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<spring:url value="/resourcesbootstrap/assets/js/html5shiv.js" var="html5shivUrl" />
  	<script src="${html5shivUrl}"></script>
  	<spring:url value="/resourcesbootstrap/assets/js/respond.min.js" var="respondUrl" />
  	<script src="${respondUrl}"></script>
<![endif]-->

<link href="${resourcesUrl}/css/data_table.css" rel="stylesheet" media="screen">
<link href="${resourcesUrl}/css/stylesheet.css" rel="stylesheet" media="screen">
</head>
<body>

<jsp:include page="../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <div class="floatleft"><h3 class="panel-title">Purchase Dashboard</h3></div>
	    <spring:url value="/purchase/add" var="addURL" />
	    <div class="floatright"><a href="${addURL}" class="btn btn-sm btn-primary"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;&nbsp;&nbsp;New Purchase</a></div>
	    <div class="clearboth"></div>
	  </div>
	  <div class="panel-body"> 
	  
	  	<core:if test="${param.message=='create-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Purchase entry has been completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='update-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Update purchase entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='delete-success'}">												
		  	<div class="alert alert-success">
		        <strong>Error!</strong> Delete purchase entry request was completed successfully.
		   	</div>					
		</core:if>

		<table cellpadding="0" cellspacing="0" border="0" class="display" id="dashboard" width="100%">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>Invoice</th>
					<th>Customer</th>
					<th>Total Amount</th>
					<th>Total Quantity</th>
					<th>Updated Date</th>		
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
			<core:forEach items="${purchaseMasterList}" var="purchaseMaster" varStatus="counter">
				<tr>
					<td>${counter.count}</td>
					<td><core:out value="${purchaseMaster.invoiceNumber}" /></td>
					<td><core:out value="${purchaseMaster.supplier.supplierName}" /></td>
					<td><core:out value="${purchaseMaster.amount}" /></td>
					<td><core:out value="${purchaseMaster.totalQuantity}" /></td>
					<td><fmt:formatDate value="${purchaseMaster.updatedDate}" pattern="dd-MM-yyyy" /></td>				
					<spring:url value="/purchase/edit-${purchaseMaster.id}" var="editURL" />
					<spring:url value="/purchase/delete-${purchaseMaster.id}" var="deleteURL" />
					<td>
						<a href="#${editURL}"><span class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
						<a href="#${deleteURL}"><span class="glyphicon glyphicon-remove-circle"></span></a>
					</td>			
				</tr>
			</core:forEach>
			</tbody>
		</table>

		</div>
	</div>
</div>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${resourcesUrl}/jquery/jquery-1.10.2.min.js" ></script>
<script src="${resourcesUrl}/jquery/jquery.dataTables.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourcesUrl}/bootstrap/js/bootstrap.min.js"></script>
<script src="${resourcesUrl}/keyBinderDashboard.js"></script>

<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {
		$('#dashboard').dataTable();
	} );
</script>

</body>
</html>