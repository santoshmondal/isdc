<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<nav class="navbar navbar-fixed-top navbar-inverse" role="navigation" id="TopHeader">
	<a class="navbar-brand" href="#" title="HJFDDG">e-Distribution and Channel Management</a>
  	
  	<div id="UserLogut">
  		<sec:authorize access="hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_REGISTERED', 'ROLE_USER')">
			<span style="color:#fff;">${currentLoginUsername}</span>
			
			<spring:url value="/logoutdetails" var="logoutURL" />
			<a href="${logoutURL}"><span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;Logout</a>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
			<spring:url value="/login" var="loginURL" />
				<a href="${loginURL}">Login</a>
		</sec:authorize>
  		
  	</div>
  	
</nav>

<nav class="navbar navbar-fixed-top navbar-inverse" role="navigation" id="MainMenuNav">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header" style="float: left;margin-left:15px;">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse navbar-ex1-collapse" id="MainMenuBar">
    <ul class="nav navbar-nav navbar-right">
    	
    	<spring:url value="/dashboard" var="homeURL" />
		<li><a href="${homeURL}"><span class="glyphicon glyphicon-stats"></span>&nbsp;&nbsp;Dashboard</a></li>
		
		<li class="dropdown">
			<a href="#">Purchase <b class="caret"></b></a>
			<ul class="dropdown-menu">
      			<spring:url value="/purchase/dashboard" var="purchaseDashboardURL" />
      			<li><a href="${purchaseDashboardURL}"><span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;Purchase Master</a></li>
				<spring:url value="/purchase/return/dashboard" var="purchaseReturnDashboard" />
	          	<li><a href="${purchaseReturnDashboard}"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Purchase Return</a></li>
      		</ul>	
		</li>
		
		<li class="dropdown">
      		<a href="#">Sales <b class="caret"></b></a>
      		<ul class="dropdown-menu">
      			<spring:url value="/sales/dashboard" var="salesDashboardURL" />
      			<li><a href="${salesDashboardURL}"><span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;Sales Master</a></li>
	          	<spring:url value="/sales/return/dashboard" var="salesReturnDashboard" />
	          	<li><a href="${salesReturnDashboard}"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Sales Return</a></li>
      		</ul>
      	</li>
      	
      	<spring:url value="/account/dashboard" var="mainAccountDashboardURL" />
      	<li><a href="${mainAccountDashboardURL}"><span class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Create Users</a></li>
      	
      	<li class="dropdown">
	      	<!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown"> ... </a> -->
	        <a href="#">Product Section <b class="caret"></b></a>
	        <ul class="dropdown-menu">
	        	<spring:url value="/product/dashboard" var="productDashboardURL" />
	          	<li><a href="${productDashboardURL}"><span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;Product Master</a></li>
	          	<spring:url value="/product/group/dashboard" var="groupDashboardURL" />
	          	<li><a href="${groupDashboardURL}"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Group Dashboard</a></li>
	          	<spring:url value="/product/subgroup/dashboard" var="subgroupDashboardURL" />
	          	<li><a href="${subgroupDashboardURL}"><span class="glyphicon glyphicon-tag"></span>&nbsp;&nbsp;Sub-Group Dashboard</a></li>
	          	<spring:url value="/product/manufacturar/dashboard" var="manufacturarDashboardURL" />
	          	<li><a href="${manufacturarDashboardURL}"><span class="glyphicon glyphicon-wrench"></span>&nbsp;&nbsp;Manufacturar Dashboard</a></li>
	          	<spring:url value="/tax/dashboard" var="taxDashboardURL" />
	          	<li><a href="${taxDashboardURL}"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;Tax Dashboard</a></li>
	        </ul>
		</li>
      
      	<li class="dropdown">
	      	<!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown"> ... </a> -->
	        <a href="#">Customer Section <b class="caret"></b></a>
	        <ul class="dropdown-menu">
	        	<spring:url value="/customer/dashboard" var="customerDashboardURL" />
	          	<li><a href="${customerDashboardURL}"><span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;Customer Master</a></li>
	          	<spring:url value="/area/dashboard" var="areaDashboardURL" />
	          	<li><a href="${areaDashboardURL}"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Area Dashboard</a></li>
	          	<spring:url value="/supplier/dashboard" var="supplierDashboardURL" />
	          	<li><a href="${supplierDashboardURL}"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Supplier Dashboard</a></li>
	        </ul>
      	</li>
      
      	<li><a href="#"><span class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Reports</a></li>
      
      	<spring:url value="/settings/dashboard" var="settingsDashboardURL" />
      	<li><a href="${settingsDashboardURL}"><span class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Settings</a></li>
      	
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>