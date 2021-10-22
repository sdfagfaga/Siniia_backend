<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header navbar navbar-fixed-top"
	style="background-color: #154e86; color: black; height: 61px;">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<span class="help-block"></span>
		<div class="page-logo">
			<img src="<c:url value='/img/backgroundheader.png' />" alt="logo" class="logo-default"
				style="width: 74px; margin-top: 2px;" />
		</div>
		<span class="help-block"></span>
		<div class="top-menu" align="right">
		
			<ul class="nav navbar-nav pull-right" style="color: black;">
				<%-- <li class="nav-tab active" id="contestentry"><a
					href="<c:url value='/matcheslist' />">Home</a></li>
				<li class="nav-tab" id="contestentry"><a
					href="<c:url value='/editmatch' />">Match Info</a></li>
				<li class="nav-tab" id="createcontest"><a
					href="<c:url value='/createnewcontest' />">Contest Entry</a></li>--%> 
				<li class="nav-tab" id="contestentry"><a
					href="<c:url value='/advertisementsDashboard' />">Advertisements</a></li>	
					<li class="nav-tab" id="contestentry"><a
					href="<c:url value='/homepage' />">Products</a></li>
				<li class="nav-tab active" id="contestentry"><a
					href="<c:url value='/orderDashboard' />">Reports Dashboard</a></li>	
				<%-- <li class="nav-tab" id="createNotifications"><a
					href="<c:url value='/createNotifications' />">Notifications</a></li> --%>
				<li class="dropdown dropdown-user"><a href="javascript:;" 
				id="logoutHover"
					class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-close-others="true"> <img alt=""
						class="img-circle" src="assets/layouts/layout/img/avatar.png" />
						<span class="username username-hide-on-mobile"
						style="color: white; font-size: 15px; font-weight: 600;">
							${user.userName} </span> <i class="fa fa-angle-down" style="color: black; font-size: 16px; font-weight: 600;"></i>
				</a>
						<ul class="dropdown-menu dropdown-menu-default">
						<li><a href="${pageContext.request.contextPath}/logout">
								<i class="icon-key"></i> Log Out
						</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<Style>
 ul.nav a { cursor: pointer;}
 .nav-pills>li.active>a, .nav-pills>li.active>a:focus, .nav-pills>li.active>a:hover {
 color : white;
 }
a {
	text-shadow: none;
	color: white;
}
</Style>
<script src="assets/global/plugins/jquery.min.js"
	type="text/javascript"></script>
	<script>
	$("#logoutHover").hover(function(){
		$(this).css("background-color", "#36396f");
	});
	</script>