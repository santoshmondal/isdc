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
<title>e-Distribution and Channel Management | New Sale</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<spring:url value="/resources/jquery/jquery-1.9.1.js" var="jquery19URL" />
<script src="${jquery19URL}"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/bootstrap/js/bootstrap.min.js" var="bootstrapJSURL" />
<script src="${bootstrapJSURL}"></script>
<!-- 
<spring:url value="/resources/jquery/json2.min.js" var="json2URL" />
<script src="${json2URL}"></script>
 -->
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

<spring:url value="/sales/customer-name.json" var="dashboardCustomerNameJsonUrl" />
<spring:url value="/sales/product-name.json" var="dashboardProductNameJsonUrl" />
<spring:url value="/sales/add.json" var="formJsonUrl" /> 
<script>
 var customernameurl = "${dashboardCustomerNameJsonUrl}";
 var productnameurl = "${dashboardProductNameJsonUrl}";
 var salesaentryurl = "${formJsonUrl}";
</script>
<spring:url value="/resources/js/purchasedashboard.js" var="purchasedashboardURL" />
<script src="${purchasedashboardURL}"></script>
  
</head>
<body>

<jsp:include page="../includes/header.jsp"></jsp:include>

<div id="wrap">
<div class="container">
<div id="errorbox" class="alert alert-danger" style="display:none">  
</div>	
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <div class="pull-left"><h3 class="panel-title">New Sale</h3></div>
	    <div class="pull-right"><strong>Invoice No.</strong>&nbsp;<span id="invoiceno">${invoiceno}</span></div>
	    <div class="clearboth"></div>
	  </div>
	  <div class="panel-body">
	    <div class="content">
	    	
	    <spring:url value="/sales/add" var="formUrl" /> 	         
		<form:form modelAttribute="autocompleteproductresponse" id="add-purchase-form" action="${formUrl}" method="POST" > 
			<div class="topsection">
				<div class="pull-left">
				    <input type="hidden" id="customerid">
				    Customer: <input type="text" id="customer-name" placeholder="Customer Name" class='open-customerDialog' data-toggle='modal' data-target='#customerModal'>
				</div>
				<div class="pull-right">
					<a class='open-AddProductDialog btn btn-default' data-toggle='modal' data-target='#productModal'><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;&nbsp;Add Product</a>
				</div>
				<div class="clearboth"></div>
			</div>
			
			<table border="1" id="mainTable">
				<thead>
					<tr>
						<th class='column02'>ID</th>
						<th class='column03'>Code</th>
						<th class='column04'>Name</th>
						<th class='column05'>Quantity</th>
						<th class='column06'>Rate</th>
						<th class='column07'>Tax</th>
						<th class='column08'>Discount</th>
						<th class='column09'>Total Quantity</th>
						<th class='column10'>Total Amount</th>
					</tr>
				</thead>					
				<tbody>
					
				</tbody>
				<tfoot>
					<tr>
						<td class='column01' colspan='10'><a class='open-AddProductDialog' data-toggle='modal' data-target='#productModal'><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;&nbsp;Add Product</a></td>
					</tr>
				</tfoot>
			</table>
			
			<div id="divbutton" class="pull-right" style="display:none">
				<button id="totalformsumit" type="submit" class="btn btn-default"><span class="glyphicon glyphicon-save"></span>&nbsp;&nbsp;Save</button>
				<button id="totalformsumitprint" type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-print"></span>&nbsp;&nbsp;Save & Print</button>
			</div>
			
			<!-- Modal -->
			<div class="modal fade" id="schemeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title" id="myModalLabel">Product Scheme</h4>
			      </div>
			      <div class="modal-body">
			        <input type="hidden" name="modalId" id="modalId" value=""/>
			        <div class="content"></div>
			      </div>
			      <div class="modal-footer">
			        <!-- <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        <button type="button" class="btn btn-primary">Save changes &raquo;</button> -->
			        <a href="#" class="okay-button btn btn-primary">Select</a>
        			<a href="#" class="go-back-button btn btn-default">Cancel</a>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
			
			<!-- Modal -->
			<div class="modal fade" id="productModal" tabindex="-1" role="dialog" aria-labelledby="productModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title" id="myModalLabel">Add Product</h4>
			      </div>
			      <div class="modal-body">
			        <div class="content">
			        	<div class="floatleft"><input type="text" class="form-control" id="modal-product-name-code" placeholder="Product Name / Code"></div>
			        	<div class="floatleft">
				        	<select id="modal-product-type" class="form-control">
						  		<option value="true">Search by Name</option>
						  		<option value="false">Search by Code</option>
						  	</select>
					  	</div>
			        	<div class="floatleft"><a href="#" class="search-button btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;Search</a></div>
			        	<div class="clearboth"></div>
			        	<div id="modal-product-content"></div>
			        </div>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
			<!-- Modal -->
			<div class="modal fade" id="customerModal" tabindex="-1" role="dialog" aria-labelledby="customerModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title" id="customerLabel">Customer</h4>
			      </div>
			      <div class="modal-body">
			        <div class="content">
			        	<div class="floatleft"><input type="text" class="form-control" id="modal-customer-name" placeholder="Customer Name"></div>
			        	<div class="floatleft"><a href="#" class="search-button btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;Search</a></div>
			        	<div class="clearboth"></div>
			        	<div id="modal-customer-content"></div>
			        </div>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->

		</form:form>
        </div>
	  </div>
	</div>
</div>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>
<style type="text/css">
	.producttax, .pDiscountScheme,.productTQty, .productamount{
		display: none;
	}
</style>
</body>
</html>