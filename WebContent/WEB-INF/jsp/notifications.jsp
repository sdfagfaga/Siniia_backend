<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- BEGIN CONTAINER -->
<div class="page-container" style="background-color: white;">
	<div class="page-content" style="background-color: white;">
		<br></br> <br>
		<div class="content"
			style="background-color: white; min-height: 565px;">
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-12">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<div class="portlet light portlet-fit bordered"
								style="background-color: white;">
								<div class="portlet-title" style="border-bottom-width: 0px;">
									<form:form id="notificationForm"
										action="${pageContext.request.contextPath}/sendNotifications"
										modelAttribute="notificationForm" method="post"
										autocomplete="on">
										<div class="row">
											<div class="col-sm-5">
												<div class="col-sm-4">Match List</div>
												<div class="col-sm-8">
													<form:select path="match" id="option"
														class="selectpicker form-control input-sm">
														<form:option value="0" label="Select an Option"></form:option>
														<c:forEach items="${matches}" var="match">
															<form:option value="${match.matchId}"
																label="${match.matchId}-${match.team1}-${match.team2}"></form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
											<div class="col-sm-5">
												<div class="col-sm-4">Users</div>
												<div class="col-sm-8">
													<form:select path="userType" id="option"
														class="selectpicker form-control input-sm">
														<form:option value="0" label="Select an Option"></form:option>
														<c:forEach items="${userType}" var="type">
															<form:option value="${type}" label="${type}"></form:option>
														</c:forEach>
													</form:select>
												</div>

											</div>
										</div>
										<br>		
											<div class="row">								
											<div class="col-sm-5">
												<div class="col-sm-4">Message Type</div>
												<div class="col-sm-8">
													<form:select path = "msgType" id="msgType" onchange = "msgfunction()"
														class="selectpicker form-control input-sm">
														<form:option value="0" label="Select an Option"></form:option>
															<form:option value="list" label="List"></form:option>
															<form:option value="text" label="Text"></form:option>
													</form:select>
												</div>
											</div>
											<div class="col-sm-5">
												<div class="col-sm-4">Message</div>
												<div class="col-sm-8" id = "messagelist">
													<form:select path="message" id="option"
														class="selectpicker form-control input-sm">
														<form:option value="0" label="Select an Option"></form:option>
														<c:forEach items="${messages}" var="msg">
															<form:option value="${msg.message}" label="${msg.message}"></form:option>
														</c:forEach>
													</form:select>
												</div>
												<div class="col-sm-8" id = "messagetext">
													<form:textarea path="message" class="form-control"
																			id="text" style="background-color: white;"/>
												</div>
											</div>
											<div class="col-sm-2">
												<div class="btn-group">
													<button id="search" type="submit" class="btn btn-sm"
														style="background-color: #154e86; color: white;">SEND</button>
												</div>
											</div>
											</div>
									</form:form>
								</div>
							</div>
						</div>
						<div class="col-sm-1"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="<c:url value='/assets/global/plugins/jquery.min.js' />"
	type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript">
$(document)
.ready(
		function() {
			//$("#messagelist").hide();
			$("#messagetext").hide();
		});

function msgfunction(){
	var msgType = document.getElementById("msgType").value;
	if(msgType == "text"){
		$("#messagelist").hide();
		$("#messagetext").show();
	}else{
		$("#messagelist").show();
		$("#messagetext").hide();
	}
}

</script>
