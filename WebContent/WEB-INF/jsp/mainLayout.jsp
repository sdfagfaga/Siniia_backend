<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SINIIA</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta http-equiv="cache-control" content="must-revalidate" />
<meta http-equiv="Cache-control" content="private">

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
	href="<c:url value='/assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<link
	href="<c:url value='/assets/global/plugins/fancybox/source/jquery.fancybox.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/jquery-file-upload/blueimp-gallery/blueimp-gallery.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/jquery-multi-select/css/multi-select.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/select2/css/select2.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/select2/css/select2-bootstrap.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="<c:url value='/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />"
	rel="stylesheet" type="text/css" />

<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link
	href="<c:url value='/assets/layouts/layout2/css/custom.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END THEME LAYOUT STYLES -->

<!-- BEGIN THEME GLOBAL STYLES -->
<link href="<c:url value='/assets/global/css/components.min.css' />"
	rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value='/assets/global/css/plugins.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="<c:url value='/assets/layouts/layout/css/layout.min.css' />"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/assets/layouts/layout/css/themes/darkblue.min.css' />"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="<c:url value='/assets/layouts/layout/css/custom.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END THEME LAYOUT STYLES -->
<link rel="shortcut icon" href="<c:url value='/img/backgroundheader.png' />">


</head>
<!-- END HEAD -->
<body class="page-header-fixed page-content-white" style="background-color: white;">

	  <header id="header">
		<tiles:insertAttribute name="header" />
	</header> 

	<section id="site-content">
		<tiles:insertAttribute name="body" />
	</section>

	 <footer id="footer">
		<tiles:insertAttribute name="footer" />
	</footer> 
<%-- <script src="<c:url value='/assets/global/plugins/jquery.min.js' />"
	type="text/javascript"></script> --%>
<script
	src="<c:url value='/assets/global/plugins/bootstrap/js/bootstrap.min.js' />"
	type="text/javascript"></script>
<script src="<c:url value='/assets/global/plugins/js.cookie.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery.blockui.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js' />"
	type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS 
        
       END THEME GLOBAL SCRIPTS --> <script src="<c:url value='/assets/global/scripts/app.min.js' />" type="text/javascript"></script> 

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script
	src="<c:url value='/assets/pages/scripts/form-samples.min.js' />"
	type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="<c:url value='/assets/global/plugins/moment.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js' />"
	type="text/javascript"></script>
	<script
		src="<c:url value='/assets/pages/scripts/components-date-time-pickers.min.js' />"
		type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script
	src="<c:url value='/assets/global/plugins/fancybox/source/jquery.fancybox.pack.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/vendor/tmpl.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-audio.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-video.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-markdown/lib/markdown.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-markdown/js/bootstrap-markdown.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/select2/js/select2.full.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/global/plugins/JavaScriptSpellCheck/include.js' />"
	type="text/javascript"></script>
</body>


</html>