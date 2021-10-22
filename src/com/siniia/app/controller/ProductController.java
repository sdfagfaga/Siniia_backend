package com.siniia.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.siniia.app.dao.OrderDAO;
import com.siniia.app.dao.ProductsDAO;
import com.siniia.app.dao.UserProfileDAO;
import com.siniia.app.dbo.BannersDetails;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.ProductBuyDetails;
import com.siniia.app.dbo.ProductCategories;
import com.siniia.app.dbo.ProductDetails;
import com.siniia.app.dbo.ProductImages;
import com.siniia.app.dbo.ProductType;
import com.siniia.app.dbo.UpdateProductData;
import com.siniia.app.dbo.UserAddress;
import com.siniia.app.dbo.UserProfile;
import com.siniia.app.dbo.productCategoriesMeta;
import com.siniia.app.utils.S3Utils;

@Controller
public class ProductController {

	private static final Log logger = LogFactory.getLog(ProductController.class);

	@Autowired
	private ProductsDAO productsDAO;

	@Autowired
	private UserProfileDAO userProfileDAO;

	@Autowired
	private OrderDAO orderDAO;

	private Gson gson = new Gson();

	@RequestMapping(value = "/getProductsList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getProductsList(@RequestParam int userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONArray jArr = new JSONArray();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				if (user.getMobileCountry().equalsIgnoreCase("91") || user.getMobileCountry().equalsIgnoreCase("1")) {
					JSONObject jsObject = new JSONObject();
					List<ProductDetails> productDetails = null;
					productDetails = productsDAO.getAllProductsINDIAUSA(userId);
					int noOfRecords = productsDAO.getProductsTableCountINDIAUSA(userId);
					logger.debug("Total Vendors: " + noOfRecords);
					/* jArr.put(productDetails); */
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(productDetails));
					jsObject.put("noOfRecords", noOfRecords);
					jsObject.put("data", new JSONArray(json.toString()));

					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("products", jsObject);
				} else {
					JSONObject jsObject = new JSONObject();
					List<ProductDetails> productDetails = null;
					productDetails = productsDAO.getAllProductsOTHER(userId);
					int noOfRecords = productsDAO.getProductsTableCountOTHER(userId);
					logger.debug("Total Vendors: " + noOfRecords);
					/* jArr.put(productDetails); */
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(productDetails));
					jsObject.put("noOfRecords", noOfRecords);
					jsObject.put("data", new JSONArray(json.toString()));

					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("products", jsObject);
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Products");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateProduct(MultipartHttpServletRequest multipartRequest, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
		ProductDetails prodDetails = new ProductDetails();
		UserAddress userAddress = new UserAddress();
		ProductDetails prod = new ProductDetails();
		String imagesUrl = "";
		int userAddressId = -1;
		try {
			Set set = multipartRequest.getFileMap().entrySet();
			Iterator it = set.iterator();
			Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				String fileName = (String) me.getKey();
				MultipartFile multipartFile = (MultipartFile) me.getValue();
				// String fileName = multipartFile.getOriginalFilename();
				logger.info("Original fileName - " + multipartFile.getOriginalFilename());
				logger.info("fileName - " + fileName);
				if (fileName.equalsIgnoreCase("input.json")) {
					File convFile = new File(fileName);
					convFile.createNewFile();
					FileOutputStream fos = new FileOutputStream(convFile);
					IOUtils.copy(multipartFile.getInputStream(), fos);
					fos.close();
					Object obj = jsonParser.parse(new FileReader(convFile));
					array = (org.json.simple.JSONArray) obj;
				} else {
					multipartFiles.put(fileName, multipartFile);
				}
			}

			if (array != null) {
				JSONObject jsonObjects = new JSONObject(array.get(0).toString());
				{
					if (jsonObjects.has("productId")) {
						prodDetails.setId(jsonObjects.getInt("productId"));
					}

					prod = productsDAO.checkProductExists(jsonObjects.getInt("productId"));

					if (prod != null) {
						if (jsonObjects.has("quantityType")) {
							prodDetails.setQuantityType(jsonObjects.getString("quantityType"));
						} else {
							prodDetails.setQuantityType(prod.getQuantityType());
						}
						if (jsonObjects.has("quantityAvailable")) {
							prodDetails.setQuantityAvailable(jsonObjects.getInt("quantityAvailable"));
						} else {
							prodDetails.setQuantityAvailable(prod.getQuantityAvailable());
						}
						if (jsonObjects.has("quantityPerUnit")) {
							prodDetails.setQuantityPerUnit(jsonObjects.getInt("quantityPerUnit"));
						} else {
							prodDetails.setQuantityPerUnit(prod.getQuantityPerUnit());
						}
						if (jsonObjects.has("categoryName")) {
							prodDetails.setCategoryName(jsonObjects.getString("categoryName"));
						} else {
							prodDetails.setCategoryName(prod.getCategoryName());
						}
						if (jsonObjects.has("productOwnerName")) {
							prodDetails.setProductName(jsonObjects.getString("productOwnerName"));
						} else {
							prodDetails.setProductName(prod.getProductOwnerName());
						}
						if (jsonObjects.has("productGrade")) {
							prodDetails.setProductGrade(jsonObjects.getString("productGrade"));
						} else {
							prodDetails.setProductGrade(prod.getProductGrade());
						}
						if (jsonObjects.has("productName")) {
							prodDetails.setProductName(jsonObjects.getString("productName"));
						} else {
							prodDetails.setProductName(prod.getProductName());
						}
						if (jsonObjects.has("availableAddressId")) {
							prodDetails.setAvailableAddressId(jsonObjects.getInt("availableAddressId"));
						} else {
							prodDetails.setAvailableAddressId(prod.getAvailableAddressId());
						}
						if (jsonObjects.has("highlight")) {
							prodDetails.setHighlight(jsonObjects.getString("highlight"));
						} else {
							prodDetails.setHighlight(prod.getHighlight());
						}
						if (jsonObjects.has("minQuantity")) {
							prodDetails.setMinQuantity(jsonObjects.getInt("minQuantity"));
						} else {
							prodDetails.setMinQuantity(prod.getMinQuantity());
						}
						if (jsonObjects.has("productOwenerID")) {
							prodDetails.setProductOwenerID(jsonObjects.getInt("productOwenerID"));
						} else {
							prodDetails.setProductOwenerID(prod.getProductOwenerID());
						}
						if (jsonObjects.has("productDescription")) {
							prodDetails.setProductDescription(jsonObjects.getString("productDescription"));
						} else {
							prodDetails.setProductDescription(prod.getProductDescription());
						}
						if (jsonObjects.has("productType")) {
							prodDetails.setProductType(jsonObjects.getString("productType"));
						} else {
							prodDetails.setProductType(prod.getProductType());
						}
						if (jsonObjects.has("productSubName")) {
							prodDetails.setProductSubName(jsonObjects.getString("productSubName"));
						} else {
							prodDetails.setProductSubName(prod.getProductSubName());
						}
						if (jsonObjects.has("pricePerUnit")) {
							prodDetails.setPricePerUnit(jsonObjects.getDouble("pricePerUnit"));
						} else {
							prodDetails.setPricePerUnit(prod.getPricePerUnit());
						}
						if (jsonObjects.has("address1")) {
							userAddress.setAddress1(jsonObjects.getString("address1"));
							prodDetails.setAddress1(jsonObjects.getString("address1"));
						}
						if (jsonObjects.has("address2")) {
							userAddress.setAddress2(jsonObjects.getString("address2"));
							prodDetails.setAddress2(jsonObjects.getString("address2"));
						}
						if (jsonObjects.has("pinCode")) {
							userAddress.setPinCode(jsonObjects.getString("pinCode"));
							prodDetails.setPinCode(jsonObjects.getString("pinCode"));
						}
						if (jsonObjects.has("landMark")) {
							userAddress.setLandmark(jsonObjects.getString("landMark"));
							prodDetails.setLandmark(jsonObjects.getString("landMark"));
						}
						if (jsonObjects.has("LastLocationLat")) {
							userAddress.setAddressLat(jsonObjects.getString("LastLocationLat"));
							prodDetails.setAddressLat(jsonObjects.getString("LastLocationLat"));
						}
						if (jsonObjects.has("LastLocationLong")) {
							userAddress.setAddressLong(jsonObjects.getString("LastLocationLong"));
							prodDetails.setAddressLong(jsonObjects.getString("LastLocationLong"));
						}
						if (jsonObjects.has("city")) {
							userAddress.setCity(jsonObjects.getString("city"));
							prodDetails.setCity(jsonObjects.getString("city"));
						}
						if (jsonObjects.has("state")) {
							userAddress.setState(jsonObjects.getString("state"));
							prodDetails.setState(jsonObjects.getString("state"));
						}
						if (jsonObjects.has("country")) {
							userAddress.setCountry(jsonObjects.getString("country"));
							prodDetails.setCountry(jsonObjects.getString("country"));
						}
						if (jsonObjects.has("radius")) {
							prodDetails.setRadius(jsonObjects.getString("radius"));
						} else {
							prodDetails.setRadius(prod.getRadius());
						}
						if (jsonObjects.has("productOwnerContact")) {
							prodDetails.setProductOwnerContact(jsonObjects.getString("productOwnerContact"));
						} else {
							prodDetails.setProductOwnerContact(prod.getProductOwnerContact());
						}
						if (jsonObjects.has("weight")) {
							prodDetails.setWeight(jsonObjects.getDouble("weight"));
						} else {
							prodDetails.setWeight(prod.getWeight());
						}
						if (jsonObjects.has("height")) {
							prodDetails.setHeight(jsonObjects.getDouble("height"));
						} else {
							prodDetails.setHeight(prod.getHeight());
						}
						if (jsonObjects.has("width")) {
							prodDetails.setWidth(jsonObjects.getDouble("width"));
						} else {
							prodDetails.setWidth(prod.getWidth());
						}
						if (jsonObjects.has("length")) {
							prodDetails.setLength(jsonObjects.getDouble("length"));
						} else {
							prodDetails.setLength(prod.getLength());;
						}
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Invalid ProductId");
					}
				}
				if (userAddress != null) {
					userAddressId = userProfileDAO.updateUserAddress(userAddress);
				}

				if (userAddressId != -1) {
					prodDetails.setAvailableAddressId(userAddressId);
				}
			}

			if (multipartFiles != null && !multipartFiles.isEmpty()) {
				set = multipartFiles.entrySet();
				it = set.iterator();
				int imageFileId = 1;
				int videoFileId = 1;
				while (it.hasNext()) {
					Map.Entry met = (Map.Entry) it.next();
					String name = (String) met.getKey();
					MultipartFile file = (MultipartFile) met.getValue();
					if (file != null) {
						// String fileName = file.getOriginalFilename();
						String fileName = (String) met.getKey();
						if (!fileName.isEmpty()) {
							logger.info("Original fileName - " + file.getOriginalFilename());
							logger.info("fileName - " + fileName);
							String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
							String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
							LocalDateTime now = LocalDateTime.now();
							DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
							String formatDateTime = now.format(format);
							String keyName = "product_" + prodDetails.getProductName() + "_" + imageName + "_"
									+ formatDateTime + "." + fileType.toUpperCase();
							logger.info("image key : " + keyName);
							String imageUrl = S3Utils.uploadByteArray(keyName, file.getBytes(),
									multipartRequest.getContentType());
							if (imagesUrl == "") {
								imagesUrl = imageUrl;
							} else {
								imagesUrl = imagesUrl + "," + imageUrl;
							}
							logger.info(">> IMAGE URL " + imagesUrl);
						}
					}
				}
			}

			long id = productsDAO.updateProduct(prodDetails);
			if (id == -1) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Unable to Update Product");
			} else {
				if (imagesUrl == "," || imagesUrl.isEmpty()) {
					imagesUrl = prod.getThumbImageURL();
					int i = productsDAO.updateProductImage(imagesUrl, Integer.parseInt(prod.getId() + ""));
					if (i == -1) {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Failed to update Image path");
					} else {
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Product Updated and Successfully uploaded Image");
					}
				} else {
					int i = productsDAO.updateProductImage(imagesUrl, Integer.parseInt(prod.getId() + ""));
					if (i == -1) {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Failed to update Image path");
					} else {
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Product Updated and Successfully uploaded Image");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to Update Product details");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	public @ResponseBody String addProduct(String userData, MultipartHttpServletRequest multipartRequest,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
		ProductDetails prodDetails = new ProductDetails();
		UserAddress userAddress = new UserAddress();
		String imagesUrl = "";
		int userAddressId = -1;
		try {
			Set set = multipartRequest.getFileMap().entrySet();
			Iterator it = set.iterator();
			Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				String fileName = (String) me.getKey();
				MultipartFile multipartFile = (MultipartFile) me.getValue();
				// String fileName = multipartFile.getOriginalFilename();
				logger.info("Original fileName - " + multipartFile.getOriginalFilename());
				logger.info("fileName - " + fileName);
				if (fileName.equalsIgnoreCase("input.json")) {
					File convFile = new File(fileName);
					convFile.createNewFile();
					FileOutputStream fos = new FileOutputStream(convFile);
					IOUtils.copy(multipartFile.getInputStream(), fos);
					fos.close();
					Object obj = jsonParser.parse(new FileReader(convFile));
					array = (org.json.simple.JSONArray) obj;
				} else {
					multipartFiles.put(fileName, multipartFile);
				}
			}

			if (array != null) {
				JSONObject jsonObjects = new JSONObject(array.get(0).toString());
				{
					if (jsonObjects.has("quantityType")) {
						prodDetails.setQuantityType(jsonObjects.getString("quantityType"));
					}
					if (jsonObjects.has("quantityAvailable")) {
						prodDetails.setQuantityAvailable(jsonObjects.getInt("quantityAvailable"));
					}
					if (jsonObjects.has("quantityPerUnit")) {
						prodDetails.setQuantityPerUnit(jsonObjects.getInt("quantityPerUnit"));
					}
					if (jsonObjects.has("categoryName")) {
						prodDetails.setCategoryName(jsonObjects.getString("categoryName"));
					}
					if (jsonObjects.has("productOwnerName")) {
						prodDetails.setProductName(jsonObjects.getString("productOwnerName"));
					}
					if (jsonObjects.has("productGrade")) {
						prodDetails.setProductGrade(jsonObjects.getString("productGrade"));
					}
					if (jsonObjects.has("productName")) {
						prodDetails.setProductName(jsonObjects.getString("productName"));
					}
					if (jsonObjects.has("availableAddressId")) {
						prodDetails.setAvailableAddressId(jsonObjects.getInt("availableAddressId"));
					}
					if (jsonObjects.has("highlight")) {
						prodDetails.setHighlight(jsonObjects.getString("highlight"));
					}
					if (jsonObjects.has("minQuantity")) {
						prodDetails.setMinQuantity(jsonObjects.getInt("minQuantity"));
					}
					if (jsonObjects.has("productOwenerID")) {
						prodDetails.setProductOwenerID(jsonObjects.getInt("productOwenerID"));
						userAddress.setUserId(jsonObjects.getInt("productOwenerID"));

					}
					if (jsonObjects.has("productDescription")) {
						prodDetails.setProductDescription(jsonObjects.getString("productDescription"));
					}
					if (jsonObjects.has("productType")) {
						prodDetails.setProductType(jsonObjects.getString("productType"));
					}
					if (jsonObjects.has("productSubName")) {
						prodDetails.setProductSubName(jsonObjects.getString("productSubName"));
					}
					if (jsonObjects.has("pricePerUnit")) {
						prodDetails.setPricePerUnit(jsonObjects.getDouble("pricePerUnit"));
					}
					if (jsonObjects.has("address1")) {
						userAddress.setAddress1(jsonObjects.getString("address1"));
						prodDetails.setAddress1(jsonObjects.getString("address1"));
					}
					if (jsonObjects.has("address2")) {
						userAddress.setAddress2(jsonObjects.getString("address2"));
						prodDetails.setAddress2(jsonObjects.getString("address2"));
					}
					if (jsonObjects.has("pinCode")) {
						userAddress.setPinCode(jsonObjects.getString("pinCode"));
						prodDetails.setPinCode(jsonObjects.getString("pinCode"));
					}
					if (jsonObjects.has("landMark")) {
						userAddress.setLandmark(jsonObjects.getString("landMark"));
						prodDetails.setLandmark(jsonObjects.getString("landMark"));
					}
					if (jsonObjects.has("LastLocationLat")) {
						userAddress.setAddressLat(jsonObjects.getString("LastLocationLat"));
						prodDetails.setAddressLat(jsonObjects.getString("LastLocationLat"));
					}
					if (jsonObjects.has("LastLocationLong")) {
						userAddress.setAddressLong(jsonObjects.getString("LastLocationLong"));
						prodDetails.setAddressLong(jsonObjects.getString("LastLocationLong"));
					}
					if (jsonObjects.has("radius")) {
						prodDetails.setRadius(jsonObjects.getString("radius"));
					}
					if (jsonObjects.has("productOwnerContact")) {
						prodDetails.setProductOwnerContact(jsonObjects.getString("productOwnerContact"));
					}
					if (jsonObjects.has("weight")) {
						prodDetails.setWeight(jsonObjects.getDouble("weight"));
					}
					if (jsonObjects.has("height")) {
						prodDetails.setHeight(jsonObjects.getDouble("height"));
					}
					if (jsonObjects.has("width")) {
						prodDetails.setWidth(jsonObjects.getDouble("width"));
					}
					if (jsonObjects.has("length")) {
						prodDetails.setLength(jsonObjects.getDouble("length"));
					}
					if (jsonObjects.has("city")) {
						userAddress.setCity(jsonObjects.getString("city"));
						prodDetails.setCity(jsonObjects.getString("city"));
					}
					if (jsonObjects.has("state")) {
						userAddress.setState(jsonObjects.getString("state"));
						prodDetails.setState(jsonObjects.getString("state"));
					}
					if (jsonObjects.has("country")) {
						userAddress.setCountry(jsonObjects.getString("country"));
						prodDetails.setCountry(jsonObjects.getString("country"));
					}
				}
				if (userAddress != null) {
					userAddressId = userProfileDAO.updateUserAddress(userAddress);
				}

				if (userAddressId != -1) {
					prodDetails.setAvailableAddressId(userAddressId);
				}
			}

			if (multipartFiles != null && !multipartFiles.isEmpty()) {
				set = multipartFiles.entrySet();
				it = set.iterator();
				int imageFileId = 1;
				int videoFileId = 1;
				while (it.hasNext()) {
					Map.Entry met = (Map.Entry) it.next();
					String name = (String) met.getKey();
					MultipartFile file = (MultipartFile) met.getValue();
					if (file != null) {
						// String fileName = file.getOriginalFilename();
						String fileName = (String) met.getKey();
						if (!fileName.isEmpty()) {
							logger.info("Original fileName - " + file.getOriginalFilename());
							logger.info("fileName - " + fileName);
							String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
							String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
							LocalDateTime now = LocalDateTime.now();
							DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
							String formatDateTime = now.format(format);
							String keyName = "product_" + prodDetails.getProductName() + "_" + imageName + "_"
									+ formatDateTime + "." + fileType.toUpperCase();
							logger.info("image key : " + keyName);
							String imageUrl = S3Utils.uploadByteArray(keyName, file.getBytes(),
									multipartRequest.getContentType());
							if (imagesUrl == "") {
								imagesUrl = imageUrl;
							} else {
								imagesUrl = imagesUrl + "," + imageUrl;
							}
							logger.info(">> IMAGE URL " + imagesUrl);
						}
					}
				}
			}

			long id = productsDAO.saveProduct(prodDetails);
			if (id == -1) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Failed to add Product");
			} /*
				 * else if (id == 0) { jsonObject.put("Status", 400);
				 * jsonObject.put("Description", "Product already exists"); }
				 */ else {
				int i = productsDAO.updateProductImage(imagesUrl, Integer.parseInt(id + ""));
				if (i == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Failed to update Image path");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Product Added and Successfully uploaded Image");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to Add Product details");
		}

		return jsonObject.toString();

	}

	@RequestMapping(value = "/getSellData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getSellData(@RequestParam int userId, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<ProductDetails> lst = productsDAO.getProductsByUserId(userId);
				if (lst != null && lst.size() > 0) {
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("data", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "No Products available for UserID");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Product details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getAllProductImages", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getAllProductImages(@RequestParam int userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<ProductImages> lst = productsDAO.getAllProductImages();
				if (lst != null && lst.size() > 0) {
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("data", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to fetch Images");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Images");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getProductsByProductId", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getProductsByProductId(@RequestParam int userId, @RequestParam int productId,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<ProductDetails> lst = productsDAO.checkProductListExists(productId);
				if (lst != null) {
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("products", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to fetch Products");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Images");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody String uploadImage(int id, String type, MultipartHttpServletRequest multipartRequest)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Object obj = new Object();
		org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
		Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>();
		try {
			if (type.equalsIgnoreCase("user")) {
				logger.info("userId >>" + id);
				UserProfile user = userProfileDAO.getUserByUserId(id);
				if (user != null) {
					Set set = multipartRequest.getFileMap().entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry me = (Map.Entry) it.next();
						MultipartFile multipartFile = (MultipartFile) me.getValue();
						String fileName = multipartFile.getOriginalFilename();
						logger.info("Original fileName - " + multipartFile.getOriginalFilename());
						logger.info("fileName - " + fileName);
						String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
						String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
						LocalDateTime now = LocalDateTime.now();
						DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
						String formatDateTime = now.format(format);
						String keyName = "user_" + user.getId() + "_" + imageName + "_" + formatDateTime + "."
								+ fileType.toUpperCase();
						logger.info("image key : " + keyName);
						String imageUrl = S3Utils.uploadByteArray(keyName, multipartFile.getBytes(),
								multipartRequest.getContentType());
						logger.info(">> IMAGE URL " + imageUrl);
						int i = userProfileDAO.updateUserImage(imageUrl, user.getId());
						if (i == -1) {
							jsonObject.put("Status", 400);
							jsonObject.put("Description", "Failed to update Images path");
						} else {
							jsonObject.put("Status", 200);
							jsonObject.put("Description", "Successfully uploaded Image");
						}
					}
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Invalid UserID");
				}
			} else if (type.equalsIgnoreCase("product")) {
				logger.info("productID >>" + id);
				ProductDetails prod = productsDAO.checkProductExists(id);
				if (prod != null) {
					Set set = multipartRequest.getFileMap().entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry me = (Map.Entry) it.next();
						MultipartFile multipartFile = (MultipartFile) me.getValue();
						String fileName = multipartFile.getOriginalFilename();
						logger.info("Original fileName - " + multipartFile.getOriginalFilename());
						logger.info("fileName - " + fileName);
						String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
						String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
						LocalDateTime now = LocalDateTime.now();
						DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
						String formatDateTime = now.format(format);
						String keyName = "product_" + prod.getId() + "_" + imageName + "_" + formatDateTime + "."
								+ fileType.toUpperCase();
						logger.info("image key : " + keyName);
						String imageUrl = S3Utils.uploadByteArray(keyName, multipartFile.getBytes(),
								multipartRequest.getContentType());
						logger.info(">> IMAGE URL " + imageUrl);
						int i = productsDAO.updateProductImage(imageUrl, prod.getId());
						if (i == -1) {
							jsonObject.put("Status", 400);
							jsonObject.put("Description", "Failed to update Image path");
						} else {
							jsonObject.put("Status", 200);
							jsonObject.put("Description", "Successfully uploaded Image");
						}
					}
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Invalid Product ID");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid type");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to upload Images");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getProductTypes", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getProductTypes(HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			List<productCategoriesMeta> prodType = productsDAO.getAllProductCategoriesMeta();
			if (prodType != null) {
				JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(prodType));
				jsonObject.put("Status", 200);
				jsonObject.put("Description", "Success");
				jsonObject.put("products", new JSONArray(json.toString()));
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Unable to get Product Type");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get product Types");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getProductByProductType", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getProductByProductType(@RequestParam int userId, @RequestParam String productType,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				if (user.getMobileCountry().equalsIgnoreCase("91") || user.getMobileCountry().equalsIgnoreCase("1")) {
					List<ProductDetails> lst = productsDAO.getProductByProductTypeINDIAUSA(productType,userId);
					if (lst != null) {
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Success");
						jsObject.put("data", new JSONArray(json.toString()));
						jsonObject.put("products", jsObject);
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "No Products available for " + productType);
					}
				} else {
					List<ProductDetails> lst = productsDAO.getProductByProductTypeOTHER(productType,userId);
					if (lst != null) {
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Success");
						jsObject.put("data", new JSONArray(json.toString()));
						jsonObject.put("products", jsObject);
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "No Products available for " + productType);
					}
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Products");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getAllBanners", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getAllBanners(HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			List<BannersDetails> lst = productsDAO.getAllBanners();
			if (lst != null) {
				JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
				jsonObject.put("Status", 200);
				jsonObject.put("Description", "Success");
				jsonObject.put("products", new JSONArray(json.toString()));
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Unable to fetch Banners");
			}

		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Banners");
		}
		return jsonObject.toString();
	}

	@SuppressWarnings({ "null", "unused" })
	@RequestMapping(value = "/getProductsCategories", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getProductsCategories(@RequestParam int userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		List<JSONObject> jarr = new ArrayList<JSONObject>();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<UserAddress> userAddress = userProfileDAO.getUserAddressListByUserId(userId);
				JsonElement jsonAddress = (JsonElement) jsonParser.parse(gson.toJson(userAddress));
				if (jsonAddress.toString().startsWith("[")) {
					jsonObject.put("userAddressData", new JSONArray(jsonAddress.toString()));
				} else {
					jsonObject.put("userAddressData", new JSONArray());
				}
				List<productCategoriesMeta> list = productsDAO.getAllProductCategoriesMeta();
				if (list != null && list.size() > 0) {
					for (productCategoriesMeta productCategoriesMeta : list) {
						JSONObject job = new JSONObject();
						List<ProductCategories> lst = productsDAO
								.getAllProductCategoriesByMeta(productCategoriesMeta.getProductCategory());
						if (lst != null) {
							job.put("category", productCategoriesMeta.getProductCategory());
							JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
							job.put("details", new JSONArray(json.toString()));
						}
						jarr.add(job);
					}
					if (jarr != null) {

						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Success");
						jsonObject.put("productCategories", jarr);
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Unable to fetch Product Categories");
					}
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to fetch Product Categories");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Product Categories");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String deleteProduct(@RequestParam int productId, @RequestParam int userId,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			UserProfile isUserExist = userProfileDAO.getUserByUserId(userId);
			if (isUserExist.getId() != 0) {
				int prodDetails = productsDAO.deleteProduct(productId, userId);
				if (prodDetails == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to delete the Product");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Successfully deleted the Product");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to Delete Product");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateProductStatus", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateProductStatus(@RequestParam int productId, @RequestParam int userId,
			@RequestParam String status, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			UserProfile isUserExist = userProfileDAO.getUserByUserId(userId);
			if (isUserExist.getId() != 0) {
				int prodDetails = productsDAO.updateProductStatus(productId, userId, status);
				if (prodDetails == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to Update the Product Status");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Successfully Product Status is Updated");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to Update Product Status");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getMyPostsData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getMyPostsData(@RequestParam int userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<ProductDetails> lst = productsDAO.getAllProductsByUserId(userId);
				if (lst != null && lst.size() > 0) {
					for (ProductDetails productDetail : lst) {
						if (productDetail.getAddress1() != null) {
							UserAddress uA = new UserAddress();
							uA.setAddress1(productDetail.getAddress1() != null ? productDetail.getAddress1() : "");
							uA.setAddress2(productDetail.getAddress2() != null ? productDetail.getAddress2() : "");
							uA.setAddressLat(
									productDetail.getAddressLat() != null ? productDetail.getAddressLat() : "");
							uA.setAddressLong(
									productDetail.getAddressLong() != null ? productDetail.getAddressLong() : "");
							uA.setLandmark(productDetail.getLandmark() != null ? productDetail.getLandmark() : "");
							uA.setPinCode(productDetail.getPinCode() != null ? productDetail.getPinCode() : "");

							productDetail.setAddress(uA);

						} else if (productDetail.getAddressLat() != null) {
							UserAddress uA = new UserAddress();
							uA.setAddress1(productDetail.getAddress1() != null ? productDetail.getAddress1() : "");
							uA.setAddress2(productDetail.getAddress2() != null ? productDetail.getAddress2() : "");
							uA.setAddressLat(
									productDetail.getAddressLat() != null ? productDetail.getAddressLat() : "");
							uA.setAddressLong(
									productDetail.getAddressLong() != null ? productDetail.getAddressLong() : "");
							uA.setLandmark(productDetail.getLandmark() != null ? productDetail.getLandmark() : "");
							uA.setPinCode(productDetail.getPinCode() != null ? productDetail.getPinCode() : "");

							productDetail.setAddress(uA);

						} else {
							UserAddress uA = userProfileDAO.getUserAddressListByAddressId(
									productDetail.getAvailableAddressId(), productDetail.getProductOwenerID());
							if (uA != null) {
								productDetail.setAddress(uA);
							}
						}
					}
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("data", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "No Products available for UserID");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Product details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getBuyProductDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getBuyProductDetails(@RequestParam int userId, @RequestParam int productId,
			@RequestParam int orderId, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		ProductBuyDetails prodBDetails = new ProductBuyDetails();
		try {
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				ProductDetails prodDetails = productsDAO.checkProductExists(productId);
				OrderDetails order = orderDAO.checkOrderExists(orderId);
				if (prodDetails != null && order != null) {
					prodBDetails.setHighlights(prodDetails.getHighlight());
					prodBDetails.setMinQuantity(prodDetails.getMinQuantity());
					prodBDetails.setPricePerUnit(prodDetails.getPricePerUnit());
					prodBDetails.setQuantityPerUnit(prodDetails.getQuantityPerUnit());
					prodBDetails.setProductGrade(prodDetails.getProductGrade());
					prodBDetails.setProductName(prodDetails.getProductName());
					prodBDetails.setThumbImageURL(prodDetails.getThumbImageURL());
					prodBDetails.setProductType(prodDetails.getProductType());
					prodBDetails.setQuantityType(prodDetails.getQuantityType());
					prodBDetails.setDeliveryStatus(order.getDeliveryStatus());
					prodBDetails.setQuantity(order.getQuantity());
					prodBDetails.setQuantityPrice(order.getQuantityPrice());
					prodBDetails.setProductOwnerName(prodDetails.getProductOwnerName());
					int addressId = order.getDeliveryAddressID();
					UserAddress uA = new UserAddress();
					uA.setAddress1(order.getAddress1() != null ? order.getAddress1() : "");
					uA.setAddress2(order.getAddress2() != null ? order.getAddress2() : "");
					uA.setAddressLat(order.getAddressLat() != null ? order.getAddressLat() : "");
					uA.setAddressLong(order.getAddressLong() != null ? order.getAddressLong() : "");
					uA.setPinCode(order.getPinCode() != null ? order.getPinCode() : "");
					uA.setLandmark(order.getLandmark() != null ? order.getLandmark() : "");
					UserAddress userAdd = userProfileDAO.getUserAddressListByAddressId(addressId, userId);
					if (userAdd != null)
						prodBDetails.setAddress(userAdd);
					else
						prodBDetails.setAddress(uA);

					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(prodBDetails));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					if (json.toString().startsWith("{")) {
						jsonObject.put("data", new JSONObject(json.toString()));
					} else {
						jsonObject.put("data", new JSONArray(json.toString()));
					}
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to fetch Data");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Product details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateProductDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateProductDetails(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info(userData);
			UpdateProductData product = gson.fromJson(userData, UpdateProductData.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(product.getUserId());
			if (isUserExist != null) {
				ProductDetails pD = productsDAO.checkProductExists(product.getProductId());
				if (pD != null) {
					int update = productsDAO.updateProductDetails(product);
					if (update != -1) {
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Successfully Update Product");
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Unable to Update Product");
					}
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Invalid ProductID");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Product details");
		}
		return jsonObject.toString();
	}
}