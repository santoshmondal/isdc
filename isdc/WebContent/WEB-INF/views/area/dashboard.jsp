<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Area Dashboard</title>
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

<jsp:include page="../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <div class="floatleft"><h3 class="panel-title">Area Dashboard</h3></div>
	    <spring:url value="/area/add" var="addURL" />
	    <div class="floatright"><a href="${addURL}" class="btn btn-sm btn-primary"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;&nbsp;&nbsp;Create New</a></div>
	    <div class="clearboth"></div>
	  </div>
	  <div class="panel-body"> 
	  
	  	<core:if test="${param.message=='create-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Create area entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='update-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Update area entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='delete-success'}">												
		  	<div class="alert alert-success">
		        <strong>Success!</strong> Delete area entry request was completed successfully.
		   	</div>					
		</core:if>
		<core:if test="${param.message=='delete-fail'}">												
		  	<div class="alert alert-danger">
		        <strong>Error!</strong> Delete area entry request could not be completed, the area is assigned to a beat entry.
		   	</div>					
		</core:if>

		<table cellpadding="0" cellspacing="0" border="0" class="display" id="dashboard" width="100%">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>City</th>
					<th>State</th>
					<th>Country</th>					
					<th>Created Date</th>
					<th>Updated Date</th>
					<th>Action</th>
					<th>Beat</th>
				</tr>
			</thead>
			<tbody>
			<core:forEach items="${areamasterlist}" var="areamasterlist">
				<tr>
					<td><core:out value="${areamasterlist.area_id}" /></td>
					<td><core:out value="${areamasterlist.area_name}" /></td>
					<td><core:out value="${areamasterlist.area_city}" /></td>
					<td><core:out value="${areamasterlist.area_state}" /></td>
					<td><core:out value="${areamasterlist.area_country}" /></td>
					<td><fmt:formatDate value="${areamasterlist.area_created_date}" pattern="dd-MM-yyyy"/></td>
					<td><fmt:formatDate value="${areamasterlist.area_updated_date}" pattern="dd-MM-yyyy"/></td>
					<spring:url value="/area/edit?id=${areamasterlist.area_id}" var="editURL" />
					<spring:url value="/area/delete?id=${areamasterlist.area_id}" var="deleteURL" />
					<td>
						<a href="${editURL}"><span class="glyphicon glyphicon-edit"></span></a>
						&nbsp;&nbsp;&nbsp;
						<a href="${deleteURL}"><span class="glyphicon glyphicon-remove-circle"></span></a>
					</td>			
					<td>
						<spring:url value="/beat/dashboard?areaid=${areamasterlist.area_id}" var="addBeatURL" />
						<a href="${addBeatURL}">Manage</a>
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