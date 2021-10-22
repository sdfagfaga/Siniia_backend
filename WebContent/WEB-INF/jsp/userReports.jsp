<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<div class="page-container" style="background-color: white;">
	<div class="page-content" style="background-color: white;">
		<br>
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-12">
				<div class="col-md-6"></div>
				<div class="col-md-6">
					<div id="errorMessage">
						<c:if test="${not empty errorMessage}">
							<div class="inputError">
								<c:out value="${errorMessage}" />
							</div>
						</c:if>
					</div>
					<!-- <div class="col-sm-1"></div>
				<div class="col-sm-2"
					style="font-weight: bold; font-size: 15px; padding-left: 0px; padding-top: 15px;">Products</div> -->
				</div>
			</div>
		</div>
		<br>
		<div class="content"
			style="background-color: white; min-height: 700px;">
			<div class="row" style="margin-right: 0px; min-height: 700px;">
				<div class="col-sm-1"></div>
				<div class="col-sm-10" id="example"
					style="min-height: 700px; padding: 0px;">
					<div class="portlet light portlet-fit"
						style="background-color: white;">
						<div class="row">
							<div class="row widget-row">
								<div class="col-sm-12">
									<div class="col-sm-12" style="text-align: center;">
										<!-- BEGIN WIDGET THUMB -->
										<div class="row">
											<div class="col-md-12"
												style="background-color: #f3f6f9; min-height: 700px; padding-top: 20px; text-align: left">
												<div class=" col-md-12 portlet-body"
													style="border-bottom-width: 0px; background-color: #f3f6f9;"
													id="adDiv">
													<form:form id="bannerDetailsForm"
														action="${pageContext.request.contextPath}/addBanner"
														modelAttribute="bannerDetailsForm" method="post"
														enctype="multipart/form-data" autocomplete="on">
														<div class="form-body">
															<div class="row">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-4"></div>
																		<div class="col-md-4">
																			<h4 align="center">
																				<label class="control-label col-md-12"
																					style="font-weight: bold; font-size: 17px;">Add
																					Banner Advertisements</label>
																			</h4>
																		</div>
																	</div>
																</div>
															</div>
															<%-- <div class="row" style="padding-top: 40px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-5">
																			<label class="control-label col-md-12">Product
																				Id</label>
																		</div>
																		<div class="col-md-7">
																			<div class="input-group col-sm-10">
																				<form:input path="productId" class="form-control" id="productId"
																					style="background-color: white;" /> 
																			</div>
																		</div>
																	</div>
																</div>
															</div> --%>
															<div class="row" style="padding-top: 30px;"></div>
															<div class="row form-group" >
																<div class="col-md-4" ></div>
																<div class="col-md-6" >
																	<div class="row fileupload-buttonbar">
																		<span class="btn-sm fileinput-button"
																			style="background-color: #154e86; color: white;"><span>UPLOAD
																				VIDEO</span> <form:input type="file" path="uploadedImage"
																				multiple="multiple" id="uploadedImage" /> </span> <span
																			class="fileupload-process"> </span>
																	</div>
																	<span class="alert-danger" id="chequeError"></span>
																</div>
															</div>



															<div class="row form-group" id="showUploadedFiles"
																style="display: none; padding-top: 20px;">
																<label class="control-label col-md-1"><span
																	id="fileTypeError" style="color: #E82734"></span></label>
																<div class="col-md-1"></div>
																<div class="col-md-8">

																	<table
																		class="table table-hover table-bordered  table-sortable"
																		id="upload_files">
																		<thead style="background-color: #154e86; color: #fff">
																			<tr>
																				<th>File Name</th>
																				<th class="hidden-xs">File Type</th>
																				<th class="hidden-xs">File size</th>
																			</tr>
																		</thead>
																		<tbody id="selectedFilesRow">
																		</tbody>
																	</table>
																</div>
															</div>

															<div class="row">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-4"></div>
																		<div class="col-md-4" align="center">
																			<input
																				style="background-color: #154e86; font-size: 16px; color: white;"
																				type="submit" id="saveProduct" class="btn btn-sm"
																				value="Add Video" />
																		</div>
																		<div class="col-md-4"></div>
																	</div>
																</div>
															</div>
														</div>
													</form:form>
												</div>
											</div>
										</div>
										<!-- END WIDGET THUMB -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-2"></div>
				<div class="col-sm-1"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						
						/* var imageFile = document.getElementById("uploadedImage").value;
						if (imageFile == 0) {
							document
									.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'>Video File not Uploaded.</span>";
							return false;
						} */

						$('input[type="file"]')
								.change(
										function(e) {
											document
													.getElementById('fileTypeError').innerHTML = '';
											/* document
													.getElementById('payByChequeBtn').disabled = false; */
											var files = e.target.files;
											$("#selectedFilesRow").empty();
											document
													.getElementById('showUploadedFiles').style.display = 'inline';
											for (var i = 0; i < files.length; i++) {
												var newRowContent = "<tr><td>"
														+ files[i].name
														+ "</td><td>"
														+ files[i].name
																.substr(
																		files[i].name
																				.lastIndexOf('.') + 1)
																.toUpperCase()
														+ "</td><td>"
														+ Math
																.round(files[i].size / 1024)
														+ " Kb" + "</td></tr>";
												$(newRowContent).appendTo(
														$("#upload_files"));
												var fileTypeUploaded = files[i].name
														.substr(
																files[i].name
																		.lastIndexOf('.') + 1)
														.toUpperCase();
												var validFileTypes = [ 'MP4' ];
												if (validFileTypes
														.indexOf(fileTypeUploaded) == -1) {
													document
															.getElementById('payByChequeBtn').disabled = true;
													document
															.getElementById('fileTypeError').innerHTML = 'Invalid File to Upload. Allowed only MP4 type of files';
												}
											}
										});

					});
</script>