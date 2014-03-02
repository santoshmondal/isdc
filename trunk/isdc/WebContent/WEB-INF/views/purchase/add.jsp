<%@page import="java.util.Date"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | New Purchase</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<spring:url value="/resources/jquery/jquery-1.9.1.js" var="jquery19URL" />
<script src="${jquery19URL}"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/bootstrap/js/bootstrap.min.js" var="bootstrapJSURL" />
<script src="${bootstrapJSURL}"></script>
<!-- Typeahead (necessary for Bootstrap's Typeahead plugins) -->
<spring:url value="/resources/jquery/jquery-ui.min.js" var="jqueryUiURL" />
<script src="${jqueryUiURL}"></script>

<spring:url value="/resources/jquery/jquery-ui.css" var="jqueryUICssUrl" />
<link href="${jqueryUICssUrl}" rel="stylesheet" media="screen">
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
	    <div class="pull-left"><h3 class="panel-title">New Purchase</h3></div>
	    <div class="pull-right"><strong>Invoice No.</strong>&nbsp;<span id="invoiceNo">${invoiceno}</span></div>
	    <div class="clearboth"></div>
	  </div>
	  <div class="panel-body">
	    <div class="content">
	 		
	 		<div class="topsection" style="height:40px;">
				<div style="float:left;">
				    <label>Enter Supplier: </label>
				    <input type="text" name="supplierName" id="supplier_name" placeholder="Supplier name"  class="text_field" />
				    <!-- <input type="text" name="invoiceNumber" id="invoice_no" placeholder="Invoice Number"  class="text_field" /> -->
				</div>
				<div style="float:left;margin-left:20px;font-weight:bold;">
					<div id="partyName"></div>
				</div>
			</div>
			
	 		<table border="1" id="mainTable">
				<thead>
					<tr>
						<th>Product Code</th>
						<th>Name</th>
						<th>Quantity</th>
						<th>Rate</th>
						<th>Tax</th>
						<th>Discount</th>
						<th>Total Quantity</th>
						<th>Total Amount</th>
						<th>Remove</th>
					</tr>
				</thead>					
				<tbody>
					<tr id="LastRow">
						<td><div><input type="text" class="productCode text_field"></div></td>
						<td><div class="productName" title=""></div></td>
						<td><div><input type="text" class="productQuantity text_field">
							<span class="availQuantity"></span></div>
						</td>
						<td><div class="tBox"><input type="text" class="productRate text_field"></div></td>
						<td><div class="productTaxs tBox"></div></td>
						<td><p><a class="addRemoveScheme"></a></p><div class="productSchemes tBox"></div></td>
						<td><div class="finalQuantity tBox"></div></td>
						<td><div class="finalAmount tBox"></div></td>
						<td><div></div></td>
					</tr>
				</tbody>
			</table>
			
			<div class="totalSummary">
				<div style="float:left;"><div id="Error" class="error"></div></div>
				<div style="float:right;">
					<div class="totalDiv">
						<div class="lableLeft">Total: </div>
						<div class="lableRight" id="grandQuantity">0</div>
					</div>
					<div class="totalDiv">
						<div class="lableLeft">Total: </div>
						<div class="lableRight" id="grandAmount">0</div>
					</div>
				</div>
			</div>
			
			<div class="save_button">
				<button id="PurchaseSaveBtn" type="submit" class="btn btn-primary">Save</button>
			</div>
        </div>
        
	  </div>
	</div>
</div>
</div>


<jsp:include page="../includes/footer.jsp"></jsp:include>

<!-- PRODUCT LIST POPUP -->
<div id="light_box_back" class="light_box_back"></div>
<div id="light_box" class="light_box productList">
		<p id="light_box_header" class="light_box_header">
			<span id="light_box_title" class="light_box_title"></span><button>X</button>
		</p>
	 	<div id="light_box_content" class="light_box_content"></div>
</div>

<!-- END PRODUCT LIST POPUP -->

<spring:url value="/resources" var="resourcesURL" />
<script src="${resourcesURL}/jquery/jquery.dataTables.min.js"></script>
<%Long  ts =new Date().getTime(); %>
<script src="${resourcesURL}/js/purchaseAdd.js?ts=<%=ts%>"></script>
<script type="text/javascript">

$(document).ready( function(){ 
	

});



</script>
</body>
</html>