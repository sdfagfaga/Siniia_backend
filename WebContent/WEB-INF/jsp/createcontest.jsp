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
					<div class="col-sm-3"></div>
					<div class="col-sm-6" style="padding-bottom: 20px; color: red;">
						<c:out value="${successmsg}"></c:out>
					</div>
					<div class="col-sm-3"></div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-12">
						<div class="col-sm-3"></div>
						<div class="col-sm-6">
							<div class="portlet light portlet-fit bordered"
								style="background-color: white;">
								<div class="portlet box ">
									<div class="portlet-title" style="background-color: #154e86;">
										<div class="row">
											<div class="col-sm-12">
												<div style="padding-top: 8px; color: white;">
													<div class="caption">CONTEST INFORMATION</div>
												</div>
											</div>
										</div>
									</div>
									<div class="portlet-body"
										style="border-bottom-width: 0px; background-color: white;">
										<form:form id="contestForm"
											action="${pageContext.request.contextPath}/createpostnewcontest"
											modelAttribute="contestForm" method="post" autocomplete="on">
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-4">Match List</div>
													<div class="col-sm-8">
														<form:select path="matchId" id="matchId"
															class="form-control input-sm"
															style="font-size : 14px; padding : 3px;">
															<form:option value="0" label="Select an Option"></form:option>
															<c:forEach items="${matches}" var="match">
																<form:option value="${match.matchId}"
																	label="${match.matchId}-${match.team1}-${match.team2}"></form:option>
															</c:forEach>
														</form:select>
														<span class="help-block"></span> <span
															class="alert-danger" id="matchIdError">${errorMessage}</span>
													</div>
												</div>
											</div>
											<br>
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-4">Category</div>
													<div class="col-sm-8">
														<form:select path="category" id="category"
															class="form-control input-sm"
															style="font-size : 14px; padding : 3px;">
															<form:option value="0" label="Select an Option"></form:option>
															<c:forEach items="${catogeries}" var="catogery">
																<form:option value="${catogery}" label="${catogery}"></form:option>
															</c:forEach>
														</form:select>
														<span class="help-block"></span> <span
															class="alert-danger" id="categoryError">${errorMessage}</span>
													</div>
												</div>
											</div>
											<br>
											<span class="help-block"></span>
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-4">Total Win</div>
													<div class="col-sm-8">
														<form:select path="totalWin" id="totalWin"
															class="form-control input-sm"
															style="font-size : 14px; padding : 3px;">
															<form:option value="" label="Select an Option"></form:option>

														</form:select>
														<span class="help-block"></span> <span
															class="alert-danger" id="totalWinError">${errorMessage}</span>
													</div>
												</div>
											</div>
											<br>
											<span class="help-block"></span>
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-4">Winners</div>
													<div class="col-sm-8">
														<form:select path="winners" id="winners"
															class="form-control input-sm"
															style="font-size : 14px; padding : 3px;">
															<form:option value="" label="Select an Option"></form:option>

														</form:select>
														<span class="help-block"></span> <span
															class="alert-danger" id="winnersError">${errorMessage}</span>
													</div>
												</div>
											</div>
											<br>
											<span class="help-block"></span>
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-4">Entry Fee</div>
													<div class="col-sm-8">
														<form:select path="entryFee" id="entryFee"
															class="form-control input-sm"
															style="font-size : 14px; padding : 3px;">
															<form:option value="" label="Select an Option"></form:option>

														</form:select>
														<span class="help-block"></span> <span
															class="alert-danger" id="entryFeeError">${errorMessage}</span>
													</div>
												</div>
											</div>
											<br>
											<span class="help-block"></span>
											<span class="help-block"></span>
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-4">Total Spots</div>
													<div class="col-sm-8">
														<form:select path="totalSpots" id="totalSpots"
															class="form-control input-sm"
															style="font-size : 14px; padding : 3px;">
															<form:option value="0" label="Select an Option"></form:option>

														</form:select>
														<span class="help-block"></span> <span
															class="alert-danger" id="totalSpotsError">${errorMessage}</span>
													</div>
												</div>
											</div>
											<br>
											<span class="help-block"></span>
											<div class="row">
												<div class="col-sm-12">
													<div class="col-sm-5"></div>
													<div class="col-sm-2">
														<div class="btn-group">
															<button id="create" type="button" class="btn btn-sm"
																style="background-color: #154e86; color: white;">CREATE</button>
														</div>
													</div>
													<div class="col-sm-5"></div>
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-3"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="<c:url value='/assets/global/plugins/jquery.min.js' />"
	type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
	$("#create").click(function() {
		/* $("#matchIdError").text('');
		$("#categoryError").text('');
		$("#totalWinError").text('');
		$("#winnersError").text('');
		$("#entryFessError").text('');
		$("#totalSpotsError").text(''); */
		var category = $("#category").find("option:selected").text();
		var totalWin = $("#totalWin").find("option:selected").text();
		var winners = $("#winners").find("option:selected").text();
		var entryFess = $("#entryFee").find("option:selected").text();
		var matchId = $("#matchId").find("option:selected").text();
		var totalSpots = $("#totalSpots").find("option:selected").text();
		if (matchId == 'Select an Option') {
			$("#matchIdError").text('Please select a Match');
		}else if(category == 'Select an Option'){
			$("#categoryError").text('Please select Category');
		}else if(totalWin == 'Select an Option'){
			$("#totalWinError").text('Please select Total Win');
		}else if(winners == 'Select an Option'){
			$("#winnersError").text('Please select Winners');
		}else if(entryFess == 'Select an Option'){
			$("#entryFeeError").text('Please select Entry Fee');
		}else if(totalSpots == 'Select an Option'){
			$("#totalSpotsError").text('Please select Total Spots');
		}else{
			$("#contestForm").submit();
		}
	});
	$("#totalSpots").on("change paste", function() {
		$("#totalSpotsError").text('');		
	});
	$("#matchId").on("change paste", function() {
		$("#matchIdError").text('');		
	});
	

	$("#category").on("change paste", function() {
		$("#categoryError").text('');
		var category = $(this).val();
		var type = "totalWin";
		getcontestdata(type, category, "", "", "");
	});

	$("#totalWin").on("change paste", function() {
		$("#totalWinError").text('');
		var totalWin = $(this).val();
		var type = "winners";
		var category = $("#category").find("option:selected").text();
		getcontestdata(type, category, totalWin, "", "");
	});

	$("#winners").on("change paste", function() {
		$("#winnersError").text('');
		var winners = $(this).val();
		var type = "entryFee";
		var category = $("#category").find("option:selected").text();
		var totalWin = $("#totalWin").find("option:selected").text();
		getcontestdata(type, category, totalWin, winners, "");
	});

	$("#entryFee").on("change paste", function() {
		$("#entryFessError").text('');
		var entryFee = $(this).val();
		var type = "totalSpots";
		var category = $("#category").find("option:selected").text();
		var totalWin = $("#totalWin").find("option:selected").text();
		var winners = $("#winners").find("option:selected").text();
		getcontestdata(type, category, totalWin, winners, entryFee);
	});

	function getcontestdata(type, category, totalWin, winners, entryFee) {
		$.ajax({
			url : '${pageContext.request.contextPath}/getcontestdata',
			data : {
				category : category,
				totalWin : totalWin,
				winners : winners,
				entryFee : entryFee
			},
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (type == 'totalWin') {
					$("#totalWin").empty();
					$('#totalWin').append(
							$("<option></option>").attr("value", "").text(
									"Select an Option"));
					$.each(obj, function(key, value) {
						console.log(key + " value  :  " + value);
						$('#totalWin').append(
								$("<option></option>").attr("value", value)
										.text(value));
					})
				}
				if (type == 'winners') {
					$("#winners").empty();
					$('#winners').append(
							$("<option></option>").attr("value", "").text(
									"Select an Option"));
					$.each(obj, function(key, value) {
						$('#winners').append(
								$("<option></option>").attr("value", value)
										.text(value));
					})
				}
				if (type == 'entryFee') {
					$("#entryFee").empty();
					$('#entryFee').append(
							$("<option></option>").attr("value", "").text(
									"Select an Option"));
					$.each(obj, function(key, value) {
						$('#entryFee').append(
								$("<option></option>").attr("value", value)
										.text(value));
					})
				}
				if (type == 'totalSpots') {
					$("#totalSpots").empty();
					$('#totalSpots').append(
							$("<option></option>").attr("value", "").text(
									"Select an Option"));
					$.each(obj, function(key, value) {
						$('#totalSpots').append(
								$("<option></option>").attr("value", value)
										.text(value));
					})
				}

			}
		});
	}
</script>






