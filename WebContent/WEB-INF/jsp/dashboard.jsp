<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- BEGIN CONTAINER -->
<div class="page-container" style="background-color: white;">
	<div class="page-content" style="background-color: white;"><br></br><br>
		<div class="content"
			style="background-color: white; min-height: 565px;">
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-3"></div><div class="col-sm-6" style="padding-bottom: 20px; color : red;"><c:out value="${successmsg}"></c:out></div><div class="col-sm-3"></div>
					</div>
					</div>
			<div class="row">
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
							<div class="portlet-body" style="border-bottom-width: 0px; background-color: white;">
								<form:form id="contestForm"
									action="${pageContext.request.contextPath}/contestpostentry"
									modelAttribute="contestForm" method="post" autocomplete="on">
									<div class="form-body">
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">Contest Name</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="name" class="form-control"
																id="name" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">Entry Fee</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="entryFee" class="form-control"
																id="entryFee" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">Match Id</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="matchId" class="form-control"
																id="matchId" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">Total Spots</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="totalSpots" class="form-control"
																id="totalSpots" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">Total Win</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="totalWin" class="form-control"
																id="totalWin" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">Type</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="type" class="form-control"
																id="type" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label col-md-12">User Id</label>
													</div>
													<div class="col-md-7">
														<div class="input-group" style="width: 237px;">
															<form:input path="userId" class="form-control"
																id="userId" style="background-color: white;"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<span class="help-block"></span>
										<span class="help-block"></span>
										<div class="row" style="padding-top: 10px;">
											<div class="col-md-12">
												<div class="form-group">
													<div class="col-md-5">
													</div>
													<div class="col-md-2">
													<input style="background-color: #154e86; font-size: 16px; color: white;"
												type="submit" class="btn btn-sm" value="Submit"/>
													</div>
													<div class="col-md-5">
													</div>
												</div>
											</div>
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