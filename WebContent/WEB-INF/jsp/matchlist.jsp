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
					<div class="col-sm-6" style="padding-bottom: 20px; color: red;">
						<c:out value="MATCH LIST"></c:out>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-12">
						<div class="portlet light portlet-fit bordered"
							style="background-color: white;">
							<div class="portlet-body"
								style="padding-top: 20px; padding-bottom: 0px;">
								<table class="display table table-striped table-hover"
									id="sample_editable_1" style="background-color: white;"
									data-page-size="10">
									<thead>
										<tr>
										<th>S.No</th>
											<th>MatchId</th>
											<th>Date</th>
											<th>Team1</th>
											<th>Team2</th>
											<th>Type</th>
											<th>Squad</th>
											<th>Toss Winner Team</th>
											<th>Winner Team</th>
											<th>Match Started</th>
											<!-- <th>Series</th> -->
											<th>IsToss Status Updated</th>
											<th>Is Squad Updated</th>
											<th>Is Match Started Process</th>
											<th>Is Winner Team Updated</th>
											<!-- <th>Team1 SName</th>
											<th>Team2 SName</th> -->
											<th>Score</th>
											<th>Ignore Match</th>
											<th>Statistics</th>
											<!-- <th>Amount Allocation</th> -->
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${empty matches}">
												<tr style="text-transform: uppercase;">
												<td></td>
													<td colspan="20" align="center"><b>No results found</b></td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${matches}" var="match">
													<tr style="text-transform: uppercase;">
														<td></td>
														<td><c:out value="${match.matchId}"></c:out></td>
														<td><c:out value="${match.dateTimeGMT}"></c:out></td>
														<td><c:out value="${match.team1}"></c:out></td>
														<td><c:out value="${match.team2}"></c:out></td>
														<td><c:out value="${match.type}"></c:out></td>
														<td><c:out value="${match.squad}"></c:out></td>
														<td><c:out value="${match.toss_winner_team}"></c:out></td>
														<td><c:out value="${match.winner_team}"></c:out></td>
														<td><c:out value="${match.matchStarted}"></c:out></td>
														<%-- <td><c:out value="${match.series}"></c:out></td> --%>
														<td><c:out value="${match.isTossStatusUpdated}"></c:out></td>
														<td><c:out value="${match.isSquadUpdated}"></c:out></td>
														<td><c:out value="${match.isMatchStartedProcess}"></c:out></td>
														<td><c:out value="${match.isWinnerTeamUpdated}"></c:out></td>
														<%-- <td><c:out value="${match.team1SName}"></c:out></td>
														<td><c:out value="${match.team2SName}"></c:out></td> --%>
														<td><c:out value="${match.score}"></c:out></td>
														<td><c:out value="${match.ignoreMatch}"></c:out></td>
														<td><c:out value="${match.stat}"></c:out></td>
														<%-- <td><c:out value="${match.amountAllocation}"></c:out></td> --%>
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

    $(document).ready(function(){
		var t = $('#sample_editable_1').DataTable({
			"scrollX": "100px",
			"sDom" : "<'row'<'col-sm-6'l><'col-sm-6'f>>"
				+ "<'row'<'col-sm-12'tr>>"
				+ "<'row'<'col-sm-12'p>>",
	    	"info" : false,
	    	"orderClasses" : false,
	    	"order" : [ [ 0, "desc" ]]
	    });
		 t.on( 'order.dt search.dt', function () {
		        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
		            cell.innerHTML = i+1;
		        } );
		    } ).draw();
	})
    
    </script>








