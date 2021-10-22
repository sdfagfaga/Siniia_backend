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
			<%-- <div class="row">
				<div class="col-sm-12">
				<div class="col-sm-2"></div>
					<div class="col-sm-6" style="padding-bottom: 20px; color: red;">
						<c:out value="UPCOMMING MATCH LIST"></c:out>
					</div>
				</div>
			</div> --%>
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-12">
						<div class="col-sm-2"></div>
						<div class="col-sm-8">
							<div class="portlet light portlet-fit bordered"
								style="background-color: white;">
								<div class="portlet-title" style="border-bottom-width: 0px;">
								<form:form id="matcheditForm"
												action="${pageContext.request.contextPath}/getmatchdetails"
												modelAttribute="matcheditForm" method="post" autocomplete="on">
								<div class="col-sm-1"></div>
									<div class="col-sm-3">Upcoming Match List</div>
									<div class="col-sm-5">
										<form:select path = "matchId" id="option" class="selectpicker form-control input-sm">
											<form:option value="0" label = "Select an Option"></form:option> 
											<c:forEach items="${matches}" var="match">
											<form:option value="${match.matchId}"
															label="${match.matchId}-${match.team1}-${match.team2}"></form:option>
												<%-- <c:choose>
													<c:when test="${match.matchId = matchId}">
														<option value="${match.matchId}"
															label="${match.matchId}"></option>

													</c:when>
													<c:otherwise>
														<option value="${match.matchId}"
															label="${match.matchId}"></option>
													</c:otherwise>
												</c:choose> --%>
											</c:forEach>
										</form:select>
									</div>
									<div class="col-md-3" align="center">
										<div class="btn-group">
											<button id="search" type="submit"  class="btn btn-sm" 
											style="background-color: #154e86; color: white;">SEARCH</button>
										</div>
									</div>
									</form:form>
								</div>
							</div>
						</div>
						<div class="col-sm-2"></div>
					</div>
				</div>
			</div>
			<br>
			<c:if test="${matchDetails != null && not empty matchDetails}">
				<div class="row">
					<div class="col-sm-12">
						<div class="col-sm-12">
							<div class="col-sm-2"></div>
							<div class="col-sm-8">
								<div class="portlet light portlet-fit bordered"
									style="background-color: white;">
									<div class="portlet box ">
										<div class="portlet-title" style="background-color: #154e86;">
											<div class="row">
												<div class="col-sm-12">
													<div style="padding-top: 8px; color: white;">
														<div class="caption">MATCH DETAILS</div>
													</div>
												</div>
											</div>
										</div>
										<div class="portlet-body"
											style="border-bottom-width: 0px; background-color: white;">
											<form:form id="contestForm"
												action="${pageContext.request.contextPath}/updatematch"
												modelAttribute="matchDetails" method="post" autocomplete="on">
												<div class="form-body">
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Match Id</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="matchId" class="form-control"
																			id="matchId" style="background-color: white;" 
																			readonly="true"/>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Team 1</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="team1" class="form-control"
																			id="team1" style="background-color: white;"
																			readonly="true" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Team 2</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="team2" class="form-control"
																			id="team2" style="background-color: white;"
																			readonly="true" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Toss
																		Winner Team</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="toss_winner_team"
																			class="form-control" id="toss_winner_team"
																			style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Winner
																		Team</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="winner_team" class="form-control"
																			id="winner_team" style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Match
																		Started</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="matchStarted" class="form-control"
																			id="matchStarted" style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Team1
																		SName</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="team1SName" class="form-control"
																			id="team1SName" style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Team2
																		SName</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="team2SName" class="form-control"
																			id="team2SName" style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Score</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="score" class="form-control"
																			id="score" style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5">
																	<label class="control-label col-md-12">Ignore
																		Match</label>
																</div>
																<div class="col-md-7">
																	<div class="input-group" style="width: 237px;">
																		<form:input path="ignoreMatch" class="form-control"
																			id="ignoreMatch" style="background-color: white;" />
																	</div>
																</div>
															</div>
														</div>
													</div>
													<span class="help-block"></span> <span class="help-block"></span>
													<div class="row" style="padding-top: 10px;">
														<div class="col-md-12">
															<div class="form-group">
																<div class="col-md-5"></div>
																<div class="col-md-2">
																	<input
																		style="background-color: #154e86; font-size: 16px; color: white;"
																		type="submit" class="btn btn-sm" value="Update" />
																</div>
																<div class="col-md-5"></div>
															</div>
														</div>
													</div>
												</div>
											</form:form>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-2"></div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
