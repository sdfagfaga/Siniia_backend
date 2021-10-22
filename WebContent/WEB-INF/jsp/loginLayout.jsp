<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>SINIIA</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/font-awesome/css/font-awesome.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/simple-line-icons/simple-line-icons.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css' />"
	rel="stylesheet" type="text/css" />
	<link
	href="<c:url value='/css/theme-style.css' />"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<%-- <!-- BEGIN PROGRESS BAR -->
        <script src="<c:url value='/assets/global/plugins/pace/pace.min.js'/>" type="text/javascript"></script>
        <link href="<c:url value='/assets/global/plugins/pace/themes/pace-theme-big-counter.css'/>" rel="stylesheet" type="text/css" />
        <!-- END PROGRESS BAR --> --%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="<c:url value='/assets/global/plugins/select2/css/select2.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/select2/css/select2-bootstrap.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link
	href="<c:url value='/assets/global/css/components.min.css' />"
	rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value='/assets/global/css/plugins.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="<c:url value='/assets/pages/css/login-5.min.css' />"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/assets/pages/css/login-4.min.css' />"
	rel="stylesheet" type="text/css" />
<link rel="icon" href="<c:url value='/img/backgroundheader.png' />">

<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<!-- END THEME LAYOUT STYLES -->
</head>
<!-- END HEAD -->

<body style="bgcolor: #32c5d2" class=" login">
	<section id="site-content">
		<tiles:insertAttribute name="body" />
	</section>
	<script
		src="<c:url value='/assets/global/plugins/jquery.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/bootstrap/js/bootstrap.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/js.cookie.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js ' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js ' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/jquery.blockui.min.js ' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js ' />"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script
		src="<c:url value='/assets/global/plugins/jquery-validation/js/jquery.validate.min.js ' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/jquery-validation/js/additional-methods.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/global/plugins/select2/js/select2.full.min.js' />"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script
		src="<c:url value='/assets/global/scripts/app.min.js' />"
		type="text/javascript"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script
		src="<c:url value='/assets/pages/scripts/login-5.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/pages/scripts/login-4.min.js'/>"
		type="text/javascript"></script>

	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->

</body>

</html>