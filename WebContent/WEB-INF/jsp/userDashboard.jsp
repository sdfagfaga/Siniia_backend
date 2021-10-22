<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- BEGIN CONTAINER -->
<div class="page-container" style="background-color: white;">
	<div class="page-content" style="background-color: white;">
		<br></br> <br>
		<div id="errorMessage">
			<div class="col-sm-12">
				<div class="col-sm-4"></div>
				<div class="col-sm-6" style="color: red;">
					<c:if test="${not empty errorMessage}">
						<div class="inputError">
							<c:out value="${errorMessage}" />
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<form:form id="reportsDataForm"
			action="${pageContext.request.contextPath}/getUserReportDetails"
			modelAttribute="OrderReportsData" method="post" autocomplete="on">
			<div class="row">
				<div class="row" style="padding-top: 10px;">
					<div class="col-sm-12">
						<div class="col-sm-6"></div>
						<div class="col-sm-2"
							style="font-weight: bold; font-size: 15px; padding-left: 0px; padding-top: 15px;">ORDERS
							DASHBOARD</div>
					</div>
				</div>
				<div class="row" style="padding-top: 30px;" id="selectReportsType">
					<div class="col-sm-4"></div>
					<div class="col-sm-4">
						<label class="col-sm-4" style="margin-top: 5px;">Type</label>
						<form:select id="downloadReports" class="col-sm-8"
							path="reportType">
							<form:option id="0" value="0">Select</form:option>
							<form:option id="downloadUserOrder" value="1">User Order Status Report</form:option>
						</form:select>
					</div>
				</div>
				<div class="row" id="UserReportsdatesBlock">
					<div class="row" style="padding-top: 20px;">
						<div class="col-sm-12">
							<div class="col-md-4"></div>
							<div class="col-md-4">
								<label class="control-label col-md-4">Date</label>
								<div
									class="input-group input-group-sm date date-picker col-sm-6"
									data-date-format="yyyy-mm-dd" data-date-viewmode="years">
									<form:input type="text" name="userfromDate" id="userfromDate"
										class="form-control" path="date" />
									<span class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="padding-top: 50px;">
				<div class="form-group">
					<div class="col-sm-12">
						<div class="col-sm-6"></div>
						<div class="col-sm-2" style="margin-left: -75px;">
							<button type="submit" id="downloadUsersReports"
								class="btn btn-primary btn-block" style="height: 40px">View
								Data</button>
						</div>
					</div>
				</div>
			</div>
		</form:form>
		<div class="row" id="usersPanDetails" style="padding-top: 50px;">
			<div class="col-sm-12">
				<div class="col-sm-12">
					<div class="portlet light portlet-fit bordered"
						style="background-color: white;">
						<div class="portlet-body"
							style="padding-top: 20px; padding-bottom: 0px;">
							<table class="display table table-striped table-hover"
								id="panCardTable" style="background-color: white;"
								data-page-size="10">
								<thead>
									<tr>
										<th>S.No</th>
										<th>Order Id</th>
										<th>Product Name</th>
										<th>Shipment Id</th>
										<th>Provider</th>
										<th>Shipment Status</th>
										<th>Amount</th>
										<th>Date</th>
										<th class="datatable-nosort text-center">Action</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									<c:choose>
										<c:when test="${empty usersPanCardData}">
											<tr style="text-transform: uppercase;">
												<td></td>
												<td colspan="20" align="center"><b>No results found</b></td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${usersPanCardData}" var="panDetails">
												<tr style="text-transform: uppercase;">
													<td></td>
													<td><c:out value="${panDetails.id}"></c:out></td>
													<td><c:out value="${panDetails.productName}"></c:out></td>
													<td><c:out value="${panDetails.shipmentObjectId}"></c:out></td>
													<td><c:out value="${panDetails.provider}"></c:out></td>
													<td><c:out value="${panDetails.shipmentStatus}"></c:out></td>
													<td><c:out value="${panDetails.paymentAmount}"></c:out></td>
													<td><c:out value="${panDetails.createdDate}"></c:out></td>
													<td><c:choose>
															<c:when test="${panDetails.isPayoutDone == 'Y'}"> Payout successfully done</c:when>
															<c:when test="${panDetails.isPayoutDone == 'D'}"> Rejected </c:when>
															<c:when test="${panDetails.isPayoutDone == 'N'}"> Approved,Payout In Process </c:when>
															<c:otherwise>
																<button
																	onclick='edit("${panDetails.id}","${panDetails.shipmentObjectId}","pan")'
																	Style="color: green">Approve</button>
																<button
																	onclick='remove("${panDetails.id}","${panDetails.shipmentObjectId}","pan")'
																	Style="color: Red">Reject</button>
															</c:otherwise>
														</c:choose></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<style>
.dataTables_filter {
	float: right;
	text-align: right;
}

.dataTables_paginate {
	float: right;
	text-align: right;
}
</style>
<script src="<c:url value='/assets/global/plugins/jquery.min.js' />"
	type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<script type="text/javascript"
	src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>

<script>
	$(document)
			.ready(
					function() {
						
						$("#UserReportsdatesBlock").hide();
						$("#usersPanDetails").hide();
						
						var selectedOption = document
								.getElementById("downloadReports").value;
						if (selectedOption == 1 ) {
							$("#UserReportsdatesBlock").show();
						}
						if ("${usersPanCardData}" != '') {
							$("#usersPanDetails").show();
							var table = $('#panCardTable').DataTable();
							table.on('order.dt search.dt', function() {
								table.column(0, {
									search : 'applied',
									order : 'applied'
								}).nodes().each(function(cell, i) {
									cell.innerHTML = i + 1;
								});
							}).draw();

						} 
						$("#selectReportsType")
								.change(
										function() {
											var selectedOption = document
													.getElementById("downloadReports").value;
											if (selectedOption == 1
													) {
												$("#UserReportsdatesBlock")
														.show();
												$("#usersPanDetails").hide();
												/* $("#usersWithdrawDetails")
														.hide();
												$("#usersBankDetails").hide(); */
											} else {
												$("#UserReportsdatesBlock")
														.hide();
												$("#usersPanDetails").hide();
												/* $("#usersWithdrawDetails")
														.hide();
												$("#usersBankDetails").hide(); */
											}
										});

					});

	function edit(indice, index, type) {

		$
				.ajax({
					url : "${pageContext.request.contextPath}/updateUserReportDetails",
					type : 'GET',
					data : {
						userId : indice,
						data : index,
						status : 'N',
						type : type
					},
					success : function(data) {
						if (data) {
							alert("Approved Successfully");
							window.location.reload();
						}
						$('#panCardTable')
								.DataTable(
										{
											"data" : data[i].DATA,
											"columns" : data[i].COLUMNS,
											paging : false,
											searching : false,
											destroy : true,
											"ordering" : false,
											dom : 'frtipB',
											buttons : [
													{
														extend : 'excel',
														text : 'Download Report',
														title : 'Quota_Report_'
																+ surveyId
																+ '_'
																+ new Date($
																		.now()),
														className : 'btn btn-info btn-sm col-sm-2 excelbtn',
														exportOptions : {
															modifier : {
																page : 'all'
															},
															stripHtml : true
														}
													}, ]
										});

					}
				});
	}

	function remove(indice, index, type) {
		$.ajax({
			url : "${pageContext.request.contextPath}/updateUserReportDetails",
			type : 'GET',
			data : {
				userId : indice,
				data : index,
				status : 'D',
				type : type
			},
			success : function(data) {
				//alert(data);
				if (data) {
					alert("Rejected Successfully");
					window.location.reload();
				}

			}
		});
	}
</script>