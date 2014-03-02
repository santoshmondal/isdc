<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<!DOCTYPE html>
<!-- saved from url=(0051)http://www.tutorialspoint.com/cgi-bin/printpage.cgi -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Sale Invoice</title>
<spring:url value="/resources/print/print.css" var="printCSSURL" />
<link rel="stylesheet" type="text/css" href="${printCSSURL}">
<spring:url value="/resources/print/prettify.css" var="prettifyCSSURL" />
<link rel="stylesheet" type="text/css" href="${prettifyCSSURL}">
<spring:url value="/resources/print/prettify.js" var="prettifyJSURL" />
<script type="text/javascript" src="${prettifyJSURL}"></script>
</head>
<body screen_capture_injected="true">
<div id="print-wrapper">
   <div id="print-area-wrapper">
      <div class="clearfix" id="actual-print-area">
        <h1 class="title">Invoice: ${salesmaster.sales_master_invoice}</h1>
        <p><strong>Customer: </strong>${salesmaster.sales_product_customer.customer_name}</p>
	    <div id="page-content">
		<div>
			<table border="1" id="mainTable">
				
				<thead>
					<tr>
						<th class='column02'>Product Details</th>
						<th class='column07'>Tax</th>
						<th class='column08'>Scheme</th>
						<th class='column09'>Quantity</th>
						<th class='column10'>Amount</th>
					</tr>
				</thead>					
				<tbody>
					<core:set var="grandtotal" value="${0}"/>
					<core:set var="grandquantity" value="${0}"/>
					<core:forEach items="${salesmaster.salesproductset}" var="salesproductset">
						<tr>
							<td>
							<table>
								<tr>
									<td>ID</td>
									<td><core:out value="${salesproductset.sales_product_pid}" /></td>
								</tr>
								<tr>
									<td>Code</td>
									<td><core:out value="${salesproductset.sales_product_code}" /></td>
								</tr>
								<tr>
									<td>Name</td>
									<td><core:out value="${salesproductset.sales_product_name}" /></td>
								</tr>
								<tr>
									<td>Quantity</td>
									<td><core:out value="${salesproductset.sales_product_quantity}" /></td>
								</tr>
								<tr>
									<td>Rate</td>
									<td><core:out value="${salesproductset.sales_product_rate}" /></td>
								</tr>
							</table>
							</td>						
							
							<core:set var="totaltax" value="${0}"/>
							<core:set var="totalschemeitems" value="${0}"/>
							<core:set var="totalschemediscount" value="${0}"/>
							<core:set var="totalamount" value="${salesproductset.sales_product_quantity * salesproductset.sales_product_rate}"/>
							<td>
								<core:if test="${salesproductset.salesproducttaxmasterset != null}">
									<table>
									<core:set var="loop1flag" value="false"/>
									<core:forEach items="${salesproductset.salesproducttaxmasterset}" var="salesproducttaxmasterset">
										<core:set var="loop1flag" value="true"/>
										<tr>
											<td><core:out value="${salesproducttaxmasterset.tax_name}" /></td>
											<td><core:out value="${salesproducttaxmasterset.tax_percentage}" /></td>
										</tr>
										<core:set var="totaltax" value="${totaltax + salesproducttaxmasterset.tax_percentage}"/>
									</core:forEach>
									<core:if test="${loop1flag}">
									<tr>
										<td><strong>Total</strong></td>
										<td><strong><core:out value="${totaltax}" /></strong></td>
									</tr>
									</core:if>
									</table>
								</core:if>
							</td>
							<core:set var="totaltaxamount" value="${( totaltax / 100 ) * totalamount}"/>					
							<td>
								<core:if test="${salesproductset.salesproductschemeset != null}">
									<table>
									<core:forEach items="${salesproductset.salesproductschemeset}" var="salesproductschemeset">
										<core:if test="${salesproductschemeset.scheme_selected}">
										<tr>
											<core:if test="${salesproductschemeset.scheme_type}">
												<td>Free Item</td>
												<td><core:out value="${salesproductschemeset.scheme_value}" /> items</td>
												<core:set var="totalschemeitems" value="${totalschemeitems + salesproductschemeset.scheme_value}"/>
											</core:if>
											<core:if test="${!salesproductschemeset.scheme_type}">
												<td>Percentage Discount</td>
												<td><core:out value="${salesproductschemeset.scheme_value}" /> %</td>
												<core:set var="totalschemediscount" value="${totalschemediscount + salesproductschemeset.scheme_value}"/>
											</core:if>
										</tr>
										</core:if>
									</core:forEach>
									</table>
								</core:if>
							</td>
							<core:set var="totaldiscountamount" value="${( totalschemediscount / 100 ) * totalamount}"/>
							<core:set var="totalitems" value="${salesproductset.sales_product_quantity + totalschemeitems}"/>
							<core:set var="totalprice" value="${0}"/>
							<td>
								<table>
									<tr>
										<td>Items</td>
										<td><core:out value="${salesproductset.sales_product_quantity}" /></td>									
									</tr>
									<tr>
										<td>Free Items</td>
										<td><core:out value="${totalschemeitems}" /></td>									
									</tr>
									<tr>
										<td><strong>Total</strong></td>
										<td><strong><core:out value="${totalitems}" /></strong></td>									
									</tr>
								</table>					
							</td>
							<core:set var="temptotal" value="${(totalamount + totaltaxamount) - totaldiscountamount}"/>
							<td>
								<table>
									<tr>
										<td>Amount</td>
										<td><core:out value="${totalamount}" /></td>									
									</tr>
									<tr>
										<td>Tax</td>
										<td><core:out value="${totaltaxamount}" /></td>									
									</tr>
									<tr>
										<td>Discount (-)</td>
										<td><core:out value="${totaldiscountamount}" /></td>									
									</tr>
									<tr>
										<td><strong>Total</strong></td>
										<td><strong><core:out value="${temptotal}" /></strong></td>									
									</tr>
								</table>	
								<core:set var="grandtotal" value="${grandtotal + temptotal}"/>
								<core:set var="grandquantity" value="${grandquantity + totalitems}"/>
							</td>				
						</tr>
					</core:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3">Total</td>
						<td><core:out value="${grandquantity}" /></td>
						<td><core:out value="${grandtotal}" /></td>
					</tr>
				</tfoot>
			</table>
         </div>
      </div>
   </div>
</div>
</div>
</body>
</html>
