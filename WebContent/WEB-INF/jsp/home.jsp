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
				<div class="col-md-4"></div>
				<div class="col-md-8">
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
													<form:form id="productDetailsForm"
														action="${pageContext.request.contextPath}/addProducts"
														modelAttribute="productDetailsForm" method="post"
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
																					Products Page</label>
																			</h4>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 40px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-5">
																			<label class="control-label col-md-12">Product
																				Category</label>
																		</div>
																		<div class="col-md-7">
																			<div class="input-group col-sm-10">
																				<form:select path="categoryName"
																					class="form-control" id="categoryName"
																					style="background-color: white;">
																					<option id="0" value="Select">Select a
																						Product Category</option>
																					<c:forEach items="${prodCategories}" var="item">
																						<option
																							id="selId_${item.productCategory}_${item.id}"
																							value="${item.productCategory}">${item.productCategory}</option>
																					</c:forEach>
																				</form:select>
																				<%-- <form:input path="categoryName" class="form-control" id="categoryName"
																					style="background-color: white;" /> --%>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-5">
																			<label class="control-label col-md-12">Product
																				Name</label>
																		</div>
																		<div class="col-md-7">
																			<div class="input-group col-sm-10">
																				<%-- <form:input path="productName" class="form-control"
																					id="productName" style="background-color: white;" /> --%>
																				<form:select path="productName" class="form-control"
																					id="productName" style="background-color: white;">
																					<option id="0" value="Select">Select a
																						Product Name</option>
																					<c:forEach items="${prodNames}" var="item">
																						<option id="selId_${item.productName}_${item.id}"
																							value="${item.productName}">${item.productName}</option>
																					</c:forEach>
																				</form:select>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-5">
																			<label class="control-label col-md-12">Product
																				Type</label>
																		</div>
																		<div class="col-md-7">
																			<div class="input-group col-sm-10">
																				<%-- <form:input path="productGrade" class="form-control"
																					id="productGrade" style="background-color: white;" /> --%>
																				<form:select path="productType" class="form-control"
																					id="productType" style="background-color: white;">
																					<option id="0" value="Select">Select a
																						Product Type</option>
																					<c:forEach items="${prodType}" var="item">
																						<option id="selId_${item.productType}_${item.id}"
																							value="${item.productType}">${item.productType}</option>
																					</c:forEach>
																				</form:select>
																			</div>
																		</div>
																	</div>
																</div>
															</div>

															<div class="row" style="padding-top: 30px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-5">
																			<label class="control-label col-md-12">Product
																				Grade</label>
																		</div>
																		<div class="col-md-7">
																			<div class="input-group col-sm-10">
																				<%-- <form:input path="productGrade" class="form-control"
																					id="productGrade" style="background-color: white;" /> --%>
																				<form:select path="productGrade"
																					class="form-control" id="productGrade"
																					style="background-color: white;">
																					<option id="0" value="Select">Select a
																						Product Grade</option>
																					<c:forEach items="${prodGrades}" var="item">
																						<option id="selId_${item.productGrade}_${item.id}"
																							value="${item.productGrade}">${item.productGrade}</option>
																					</c:forEach>
																				</form:select>
																			</div>
																		</div>
																	</div>
																</div>
															</div>

															<div class="row" style="padding-top: 30px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-5">
																			<label class="control-label col-md-12">Product
																				Unit</label>
																		</div>
																		<div class="col-md-7">
																			<div class="input-group col-sm-10">
																				<%-- <form:input path="productGrade" class="form-control"
																					id="productGrade" style="background-color: white;" /> --%>
																				<form:select path="quantityType"
																					class="form-control" id="quantityType"
																					style="background-color: white;">
																					<option id="0" value="Select">Select
																						Product Units</option>
																					<c:forEach items="${prodUnits}" var="item">
																						<option id="selId_${item.productUnits}_${item.id}"
																							value="${item.productUnits}">${item.productUnits}</option>
																					</c:forEach>
																				</form:select>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Total
																					Quantity Available</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="quantityAvailable" type="number"
																						class="form-control" id="quantityAvailable" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Minimum
																					Quantity To Buy</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="minQuantity" type="number"
																						class="form-control" id="minQuantity" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Quantity
																					Per Unit</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="quantityPerUnit" type="number"
																						class="form-control" id="quantityPerUnit" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Price
																					Per Unit</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="pricePerUnit" type="number"
																						class="form-control" id="pricePerUnit" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-3" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Length(in)</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="length" class="form-control"
																						id="length" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-3" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Width(in)</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="width" class="form-control"
																						id="width" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-3" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Height(in)</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="height" class="form-control"
																						id="height" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-3" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-7">
																				<label class="control-label col-md-12">Weight(lb)</label>
																			</div>
																			<div class="col-md-5">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="weight" class="form-control"
																						id="weight" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-3">
																			<label class="control-label col-md-12">Product
																				Description</label>
																		</div>
																		<div class="col-md-9">
																			<div class="input-group input-group-sm col-sm-10">
																				<form:textarea path="productDescription"
																					class="form-control" id="productDescription" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">Address
																				</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="address1" class="form-control"
																						id="address1" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">Street</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="address2" class="form-control"
																						id="address2" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">City</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="city" class="form-control"
																						id="city" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">State</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="state" class="form-control"
																						id="state" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">Country</label>
																			</div>
																			<div class="col-md-7">
																				<form:select path="country" class="form-control"
																					id="country" style="background-color: white;">
																					<option id="0" value="Select">Select
																						Country</option>
																					<option id="selId_1" value="US">US</option>
																					<option id="selId_2" value="AFRICA">AFRICA</option>
																					<option id="selId_3" value="INDIA">INDIA</option>

																				</form:select>
																			</div>
																			<%-- <div class="col-md-5">
																				<label class="control-label col-md-12">Country</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="country"
																						style="text-transform:uppercase"
																						class="form-control" id="country" />
																				</div>
																			</div> --%>
																		</div>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">Pin
																					Code</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="pinCode" type="number"
																						class="form-control" id="pinCode" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>

															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">Product
																					Owner Name</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="productOwnerName"
																						class="form-control" id="productOwnerName" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -25px;">
																	<div class="col-md-12">
																		<div class="form-group">
																			<div class="col-md-5">
																				<label class="control-label col-md-12">Contact
																					No.</label>
																			</div>
																			<div class="col-md-7">
																				<div class="input-group input-group-sm col-sm-10">
																					<form:input path="productOwnerContact"
																						class="form-control" id="productOwnerContact" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row col-md-12"></div>
															<div class="row" style="padding-top: 30px;">
																<div class="col-md-6" style="margin-left: -15px;">
																<div class="col-md-4"></div>
																	<div class="col-md-4" style="margin-left: 15px;"
																		align="right">
																		<div class="row fileupload-buttonbar">
																			<span class="btn-sm fileinput-button"
																				style="background-color: #154e86; color: white;"><span>UPLOAD
																					IMAGE</span> <form:input type="file" path="uploadedImage"
																					multiple="multiple" id="uploadedImage" /> </span> <span
																				class="fileupload-process"> </span>
																		</div>
																		<span class="alert-danger" id="chequeError"></span>
																	</div>
																</div>
																<div class="col-md-6" style="margin-left: -15px;">
																	<div class="col-md-9" style="margin-left: 15px;"
																		align="right">
																		<div class="row fileupload-buttonbar">
																			<span class="btn-sm fileinput-button"
																				style="background-color: #154e86; color: white;"><span>UPLOAD
																					ADVERSITEMENT VIDEO</span> <form:input type="file"
																					path="advertisementVideo" multiple="multiple"
																					id="advertisementVideo" /> </span> <span
																				class="fileupload-process"> </span>
																		</div>
																		<span class="alert-danger" id="chequeError"></span>
																	</div>
																</div>
															</div>

															<div class="row form-group" id="showUploadedFiles"
																style="display: none; padding-top: 20px;">
																<label class="control-label col-md-1"><span
																	id="fileTypeError" style="color: #E82734"></span></label>
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

															<div class="row" style="padding-top: 30px;">
																<div class="col-md-12">
																	<div class="form-group">
																		<div class="col-md-4"></div>
																		<div class="col-md-4" align="center">
																			<input
																				style="background-color: #154e86; font-size: 16px; color: white;"
																				type="submit" id="saveProduct" class="btn btn-sm"
																				value="Add Product" />
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

						$('#saveProduct')
								.click(
										function() {
											var country = $('#country').val();
											var regex = /^[A-Za-z]+$/;
											var pinCode = $('#pinCode').val();
											var minQuantity = $('#minQuantity')
													.val();
											var quantityAvailable = $(
													'#quantityAvailable').val();
											var weight = $('#weight').val();
											var height = $('#height').val();
											var width = $('#width').val();
											var length = $('#length').val();
											var quantityPerUnit = $(
													'#quantityPerUnit').val();
											var pricePerUnit = $(
													'#pricePerUnit').val();
											var address1 = $('#address1').val();
											var address2 = $('#address2').val();
											var city = $('#city').val();
											var state = $('#state').val();
											var contact = $(
													'#productOwnerContact')
													.val();
											var name = $('#productOwnerName')
													.val();
											/* var isValidCountry = regex
													.test(country); */
											var isValidCity = regex.test(city);
											var isValidState = regex
													.test(state);
											var isValidName = regex.test(name);

											var categoryName = document
													.getElementById("categoryName").value;
											var productName = document
													.getElementById("productName").value;
											var productType = document
													.getElementById("productType").value;
											var productGrade = document
													.getElementById("productGrade").value;
											var quantityType = document
													.getElementById("quantityType").value;

											var imageFile = document
													.getElementById("uploadedImage").value;

											if (country == 'Select') {
												document
														.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please select a Valid Country.</span>";
												$('#categoryName').focus();
												return false;
											} else {
												if ((country == 'US')
														&& pinCode.length != 5) {
													document
															.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please enter Valid pincode.</span>";
													$('#categoryName').focus();
													return false;
												} else {
													if ((country == 'INDIA')
															&& pinCode.length != 6) {
														document
																.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please enter Valid pincode.</span>";
														$('#categoryName')
																.focus();
														return false;
													}
												}

												if (!isValidState) {
													document
															.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please enter Valid state.</span>";
													$('#categoryName').focus();
													return false;
												}

												if (!isValidCity) {
													document
															.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please enter Valid city.</span>";
													$('#categoryName').focus();
													return false;
												}

												if (!isValidName) {
													document
															.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please enter Valid contact person Name.</span>";
													$('#categoryName').focus();
													return false;
												}

												if (contact.length < 11) {
													document
															.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'> Please enter Valid Contact No.</span>";
													$('#categoryName').focus();
													return false;
												}
											}

											if (!address1 || !address2 || !city
													|| !state || !contact
													|| !name
													|| minQuantity == 0
													|| quantityAvailable == 0
													|| weight == 0
													|| height == 0
													|| width == 0
													|| length == 0
													|| pricePerUnit == 0
													|| quantityPerUnit == 0
													|| categoryName == 'Select'
													|| productName == 'Select'
													|| productType == 'Select'
													|| productGrade == 'Select'
													|| quantityType == 'Select') {
												document
														.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'>All fields are mandatory, Please re-check and submit.</span>";
												$('#categoryName').focus();
												return false;
											}
											if (imageFile == 0) {
												document
														.getElementById("errorMessage").innerHTML = "<span style='color: red;font-size:14px;font-weight:normal;'>Image File not Uploaded.</span>";
												$('#categoryName').focus();
												return false;
											}
										})

						$('#categoryName')
								.change(
										function() {
											var prodCategory = $(
													'#categoryName').val();

											$('#productName').empty();
											$
													.ajax({
														url : "${pageContext.request.contextPath}/getProductNameFromCategory",
														type : 'GET',
														data : {
															prodCategory : prodCategory
														},
														success : function(data) {
															console.log(data);
															var list = [];
															var x = document
																	.getElementById("productName");
															var option = document
																	.createElement("option");
															for (var i = -1; i < data.length; i++) {
																var option = document
																		.createElement("option");
																if (i == -1) {
																	list
																			.push('Select');
																	option.text = 'Select';
																	option.value = 'Select';
																} else {
																	list
																			.push(data[i]
																					+ '-'
																					+ i)
																	//var option = document.createElement("option");
																	option.text = data[i];
																	option.value = data[i];
																}
																console
																		.log(option);
																var sel = x.options[x.selectedIndex];
																x.add(option,
																		sel);
															}
														}
													})

										})

						$('#productName')
								.change(
										function() {
											var prodCategory = $(
													'#categoryName').val();
											var prodName = $('#productName')
													.val();

											$('#productType').empty();
											$
													.ajax({
														url : "${pageContext.request.contextPath}/getProductTypeFromNameAndCategory",
														type : 'GET',
														data : {
															prodCategory : prodCategory,
															prodName : prodName
														},
														success : function(data) {
															console.log(data);
															var list = [];
															var x = document
																	.getElementById("productType");
															var option = document
																	.createElement("option");
															for (var i = -1; i < data.length; i++) {
																var option = document
																		.createElement("option");
																if (i == -1) {
																	list
																			.push('Select');
																	option.text = 'Select';
																	option.value = 'Select';
																} else {
																	list
																			.push(data[i]
																					+ '-'
																					+ i)
																	//var option = document.createElement("option");
																	option.text = data[i];
																	option.value = data[i];
																}
																console
																		.log(option);
																var sel = x.options[x.selectedIndex];
																x.add(option,
																		sel);
															}
														}
													})

										})

						$('#productType')
								.change(
										function() {
											var prodCategory = $(
													'#categoryName').val();
											var prodName = $('#productName')
													.val();

											$('#productGrade').empty();
											$
													.ajax({
														url : "${pageContext.request.contextPath}/getProductGradeFromNameAndCategory",
														type : 'GET',
														data : {
															prodCategory : prodCategory,
															prodName : prodName
														},
														success : function(data) {
															console.log(data);
															var list = [];
															var x = document
																	.getElementById("productGrade");
															var option = document
																	.createElement("option");
															for (var i = -1; i < data.length; i++) {
																var option = document
																		.createElement("option");
																if (i == -1) {
																	list
																			.push('Select');
																	option.text = 'Select';
																	option.value = 'Select';
																} else {
																	list
																			.push(data[i]
																					+ '-'
																					+ i)
																	//var option = document.createElement("option");
																	option.text = data[i];
																	option.value = data[i];
																}
																console
																		.log(option);
																var sel = x.options[x.selectedIndex];
																x.add(option,
																		sel);
															}
														}
													})

										})

						$('#productGrade')
								.change(
										function() {
											var prodCategory = $(
													'#categoryName').val();
											var prodName = $('#productName')
													.val();

											$('#quantityType').empty();
											$
													.ajax({
														url : "${pageContext.request.contextPath}/getProductUnitsFromNameAndCategory",
														type : 'GET',
														data : {
															prodCategory : prodCategory,
															prodName : prodName
														},
														success : function(data) {
															console.log(data);
															var list = [];
															var x = document
																	.getElementById("quantityType");
															var option = document
																	.createElement("option");
															for (var i = -1; i < data.length; i++) {
																var option = document
																		.createElement("option");
																if (i == -1) {
																	list
																			.push('Select');
																	option.text = 'Select';
																	option.value = 'Select';
																} else {
																	list
																			.push(data[i]
																					+ '-'
																					+ i)
																	//var option = document.createElement("option");
																	option.text = data[i];
																	option.value = data[i];
																}
																console
																		.log(option);
																var sel = x.options[x.selectedIndex];
																x.add(option,
																		sel);
															}
														}
													})

										})

						$('input[type="file"]')
								.change(
										function(e) {
											document
													.getElementById('fileTypeError').innerHTML = '';
											/* document
													.getElementById('payByChequeBtn').disabled = false; */
											var files = e.target.files;
											//$("#selectedFilesRow").empty();
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
												var validFileTypes = [ 'JPEG',
														'PDF', 'JPG', 'PNG',
														'mp4' ];
												if (validFileTypes
														.indexOf(fileTypeUploaded) == -1) {
													document
															.getElementById('payByChequeBtn').disabled = true;
													document
															.getElementById('fileTypeError').innerHTML = 'Invalid File Uploaded. Allowed PDF,JPG,PNG,mp4 type of files';
												}
											}
										});

					});
</script>