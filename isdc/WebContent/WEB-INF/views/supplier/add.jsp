<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-Distribution and Channel Management | Create Customer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="/resources" var="resourcesURL" />
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
	    <h3 class="panel-title">Create New Supplier</h3>
	  </div>
	  	<div class="panel-body"> 

		<spring:url value="/supplier/saveSupplierMaster" var="formUrl" />
		<form:form modelAttribute="supplier" id="add-supplier-form" action="${formUrl}" method="POST" onsubmit="return submitFormFn();">    
		
			<form:hidden path="supplierId"/>
			<div class="field_row">
				<div class="label_left"><label>Name:</label></div>
				<div class="label_right"><form:input path="supplierName" class="text_field" /></div>
				<div class="label_error"><form:errors path="supplierName"></form:errors></div>
			</div>
			<div class="field_row">
				<div class="label_left"><label>Address:</label></div>
				<div class="label_right"><form:textarea path="supplierAddress" class="text_field text_area"/></div>
				<div class="label_error"><form:errors path="supplierAddress"></form:errors></div>
			</div>
			<div class="field_row">
				<div class="label_left"><label>PIN:</label></div>
				<div class="label_right"><form:input path="supplierPin" class="text_field" /></div>
				<div class="label_error"><form:errors path="supplierPin"></form:errors></div>
			</div>
			
			<div class="field_row">
				<div class="label_left"><label>Phone:</label></div>
				<div class="label_right"><form:input path="supplierPhone" class="text_field" /></div>
				<div class="label_error"><form:errors path="supplierPhone"></form:errors></div>
			</div>
			
			<div class="field_row">
				<div class="label_left"><label>Mobile:</label></div>
				<div class="label_right"><form:input path="supplierMobile" class="text_field" /></div>
				<div class="label_error"><form:errors path="supplierMobile"></form:errors></div>
			</div>
			<div class="field_row">
				<div class="label_left"><label>Supplier Remark:</label></div>
				<div class="label_right"><form:textarea path="supplierRemark" class="text_field text_area" /></div>
				<div class="label_error"><form:errors path="supplierRemark"></form:errors></div>
			</div>
			<div class="field_row">
				<div class="label_left"><label>Blacklist:</label></div>
				<div class="label_right">
					<form:select path="supplierBlacklist" class="select_field">
						<form:option value="false">False</form:option>
						<form:option value="true">True</form:option>
					</form:select>
				</div>
				<div class="label_error"><form:errors path="supplierBlacklist"></form:errors></div>
			</div>
			<div class="field_row">
				<div class="label_left"><label>Discount:</label></div>
				<div class="label_right"><form:input path="supplierDiscount" class="text_field" /> (%)</div>
				<div class="label_error"><form:errors path="supplierDiscount"></form:errors></div>
			</div>
			
			<div class="field_row">
				<div class="label_left"><label>City:</label></div>
				<div class="label_right"><form:input path="supplierCity" class="text_field" /></div>
				<div class="label_error"><form:errors path="supplierCity"></form:errors></div>
			</div>
			
			<div class="field_row">
				<div class="label_left"><label>Area:</label></div>
			    <div class="label_right">
			    	<select name="areaId" id="areaId" class="select_field">
			    		<option value="">--Select Area--</option>
			    		<core:forEach items="${areaMasterList}" var="area">
			    			<option value="${area.area_id}" ${area.area_id eq supplier.area.area_id ? 'SELECTED':'' }>${area.area_name}</option>
			    		</core:forEach>
			    	</select>
			    </div>
			    <div class="label_error"><span>&nbsp;</span></div>
			</div>
			
			<div class="field_row">
			    <div class="label_left"><label>Beat</label></div>
			    <div class="label_right">
			    	<select name="beatId" id="beatId" class="select_field">
			    		<option value="">--Select Beat--</option>
			    		<core:forEach items="${beatMasterList}" var="beat">
			    			<option value="${beat.beat_id}" ${beat.beat_id eq supplier.beat.beat_id ? 'SELECTED':''}>${beat.beat_name}</option>
			    		</core:forEach>
			    	</select>
			    </div>
			    <div class="label_error" id="BeatIdError"><span>&nbsp;</span></div>
			</div>
			
			<!-- <div class="submit_button"></div> -->	
			<div class="field_row">
				<div class="label_left">&nbsp;</div>			
				<div class="label_right">
					<form:button class="btn btn-primary">Save</form:button>
				</div>
				<div>
					<spring:url value="/supplier/dashboard" var="dashboardURL" />
	   				<a href="${dashboardURL}" type="button" class="btn btn-link">Cancel</a>
	   			</div>
			</div>

		</form:form>

		</div>
	</div>
</div>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${resourcesURL}/jquery/jquery-1.10.2.min.js"></script>
<script src="${resourcesURL}/jquery/jquery.dataTables.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourcesURL}/bootstrap/js/bootstrap.min.js"></script>
<script src="${resourcesURL}/keyBinderForm.js"></script>

<script type="text/javascript">
				
$(document).ready(function() {
	$('#supplier_name').focus();

	$('#areaId').change(function(){
		var areaId = $( this ).val();
		if( areaId.trim().length <= 0 ){
			return false;
		}
		$("#BeatIdError span").html("<img src=\"${resourcesURL}/images/ajaxLoader.gif\"/>");
		$.ajax({
			url:"getBeatList",
			data:{areaId:areaId},
			type:"POST",
			success:function(result){
				var jsonObj = "";
				try{
					jsonObj = JSON.parse(result);
				}catch(err){ console.log(err); return false;}
				$("#BeatIdError span").html("&nbsp;");
				if(jsonObj.found != undefined && jsonObj.found == 'YES'){
					$('#beatId').html(jsonObj.result);	 
				}
				else{
					$('#beatId').html("");
					$("#BeatIdError span").html( "Beat not found!" );
					return false;
				}
			}
			
		});

	});
	
});

function submitFormFn(){
	
}		

</script>
</body>
</html>