<%@ page language="java" contentType="text/html;charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="row" style="background-color: white;">
<div class="col-sm-7">
<img src="<c:url value='/img/background.png' />" style="width: 586px; height : 640px;">
</div>
<div class="col-sm-3">
<form:form action="login" method="post"
				modelAttribute="userForm" autocomplete="off"  style="width : 320px; ">
				<aside class="aligner" style="width : 320px;">
				<div class="aligner-middle" style="padding-top: 150px;">
					<div class="text-center">
						<h6 class="admin-title text-primary" style="font-size: 20px; font-weight: 600; height : 24px;">Admin Login</h6>
					</div><br>
					<div class="input-icon" style="width : 260px;">
						<i class="fa fa-user"></i>
						<form:input
							class="form-control form-control-solid placeholder-no-fix form-group"
							type="text" placeholder="User Name" name="User Name"
							autofocus="username" path="userName" required="true"
							autocomplete="off" />
					</div>
					<div>&nbsp;</div>
					<div class="input-icon" style="width : 260px;">
						<i class="fa fa-lock"></i>
						<form:input
							class="form-control form-control-solid placeholder-no-fix form-group"
							type="password" placeholder="Password" name="password"
							path="password" required="true"
							onkeypress="submitFormByEnter(event,this)" autocomplete="off" />
					</div>
					<span class="help-block"></span>
					<div class="login-action" align="center">
						<button class="btn btn-primary" type="submit" >Sign
							in</button>
					</div>
					<div class="clearfix"></div>
					<c:if test="${not empty errorMsg }">
						<div class="alert alert-danger" style="margin-top: 20px;">
							<span>${errorMsg}</span>
						</div>
						
					</c:if>
					<div class="alert alert-danger display-hide">
					<button class="close" data-close="alert"></button>
					<span>Enter any username and password. </span>
				</div>
				</div>
				</aside>
			</form:form>
</div>
</div>

<script type="text/javascript">
	/*$(function() {
	 $("form input").keypress(function (e) {
	 if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
	 $('button[type=submit] .default').click();
	 return false;
	 } else {
	 return true;
	 }
	 });
	 });*/
	 function submitFormByEnter(e, text){
		    var code = (e.keyCode ? e.keyCode : e.which);
		    if(code == 13) { //Enter keycode
		     document.getElementById("loginForm").submit();
		    }
		}
	
</script>
<script type="text/javascript">
	/* window.location.hash = "no-back-button";
	window.location.hash = "Again-No-back-button";//again because google chrome don't insert first hash into history
	window.onhashchange = function() {
		window.location.hash = "no-back-button";
	} */
</script>