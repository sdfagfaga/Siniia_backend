package com.siniia.app.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.siniia.app.dao.OrderDAO;
import com.siniia.app.dao.ProductsDAO;
import com.siniia.app.dao.UserProfileDAO;
import com.siniia.app.dbo.BuyProductDetails;
import com.siniia.app.dbo.CartData;
import com.siniia.app.dbo.DonationData;
import com.siniia.app.dbo.LocationBasedShipper;
import com.siniia.app.dbo.NewsLetter;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.OrderStatus;
import com.siniia.app.dbo.PayPalAccountData;
import com.siniia.app.dbo.ProductDetails;
import com.siniia.app.dbo.ShipperDetails;
import com.siniia.app.dbo.ShippingDetails;
import com.siniia.app.dbo.ShippingRates;
import com.siniia.app.dbo.ShippoResponseMessages;
import com.siniia.app.dbo.UserAddress;
import com.siniia.app.dbo.UserProfile;

import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition.OrderDecoratorBucket;

@Controller
public class OrderController {
	private static final Log logger = LogFactory.getLog(OrderController.class);

	private Gson gson = new Gson();

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private UserProfileDAO userProfileDAO;

	@Autowired
	private ProductsDAO productDAO;

	private String shippoToken = "shippo_test_971340bd08c602314c4655605a9f65d2294b5241";

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String addToCart(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			CartData order = gson.fromJson(userData, CartData.class);
			if (order.getUserId() == 0) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			} else {
				if (order.getProductId() == 0) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Product ID is not available");
				} else {
					UserProfile isUserExist = userProfileDAO.getUserByUserId(Integer.parseInt(order.getUserId() + ""));
					if (isUserExist.getId() != 0) {
						ProductDetails product = productDAO.checkProductExists(order.getProductId());
						if (product.getId() != 0) {
							int orderDetails = orderDAO.saveCartOrders(order);
							if (orderDetails == 0) {
								jsonObject.put("Status", 200);
								jsonObject.put("Description", "Successfully Updated the Cart");
							} else if (orderDetails == -1) {
								jsonObject.put("Status", 400);
								jsonObject.put("Description", "Unable to add to the Cart");
							} else {
								jsonObject.put("Status", 200);
								jsonObject.put("Description", "Successfully added to Cart");
							}
						} else {
							jsonObject.put("Status", 400);
							jsonObject.put("Description", "Invalid Product ID");
						}
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Invalid UserID");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to add Order details");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/getCartData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getCartData(@RequestParam int userId, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			List<ProductDetails> prodDetails = new ArrayList<ProductDetails>();
			logger.info("userId >>" + userId);
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<CartData> lst = orderDAO.getOrdersInCartByUserId(userId);
				if (lst != null && lst.size() > 0) {
					for (CartData cartData : lst) {
						ProductDetails product = productDAO.checkProductExists(cartData.getProductId());
						if (product != null) {
							prodDetails.add(product);
						}
					}
					List<UserAddress> userAddress = userProfileDAO.getUserAddressListByUserId(userId);
					JsonElement jsonProd = (JsonElement) jsonParser.parse(gson.toJson(prodDetails));
					JsonElement jsonCart = (JsonElement) jsonParser.parse(gson.toJson(lst));
					JsonElement jsonAddress = (JsonElement) jsonParser.parse(gson.toJson(userAddress));
					/*
					 * jsObject.put("cartData", new
					 * JSONArray(jsonCart.toString()));
					 * jsObject.put("productData", new
					 * JSONArray(jsonProd.toString()));
					 * jsObject.put("userAddressData", new
					 * JSONArray(jsonAddress.toString()));
					 */
					if (jsonProd.toString().startsWith("{")) {
						jsObject.put("productData", new JSONObject(jsonProd.toString()));
					} else if (jsonProd.toString().startsWith("[")) {
						jsObject.put("productData", new JSONArray(jsonProd.toString()));
					} else {
						jsObject.put("productData", new JSONArray());
					}
					if (jsonCart.toString().startsWith("{")) {
						jsObject.put("cartData", new JSONObject(jsonCart.toString()));
					} else if (jsonCart.toString().startsWith("[")) {
						jsObject.put("cartData", new JSONArray(jsonCart.toString()));
					} else {
						jsObject.put("cartData", new JSONArray());
					}
					if (jsonAddress.toString().startsWith("{")) {
						jsObject.put("userAddressData", new JSONObject(jsonAddress.toString()));
					} else if (jsonAddress.toString().startsWith("[")) {
						jsObject.put("userAddressData", new JSONArray(jsonAddress.toString()));
					} else {
						jsObject.put("userAddressData", new JSONArray());
					}
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("data", jsObject);
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "No items in cart.");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Order details");
		}
		return jsonObject.toString();
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/getBuyData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getBuyData(@RequestParam int userId, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >>" + userId);
			List<BuyProductDetails> buyData = new ArrayList<BuyProductDetails>();
			UserProfile user = userProfileDAO.getUserByUserId(userId);
			if (user != null) {
				List<OrderDetails> lst = orderDAO.getOrdersByUserId(userId);
				if (lst != null && lst.size() > 0) {

					for (OrderDetails orderDetails : lst) {
						ProductDetails prod = productDAO.checkProductExists(orderDetails.getProductId());
						BuyProductDetails buyProdDetails = new BuyProductDetails();
						buyProdDetails.setOrderId(orderDetails.getId());
						buyProdDetails.setProductGrade(prod.getProductGrade());
						buyProdDetails.setProductId(orderDetails.getProductId());
						buyProdDetails.setProductName(prod.getProductName());
						buyProdDetails.setProductType(prod.getProductType());
						buyProdDetails.setQuantity(orderDetails.getQuantity());
						buyProdDetails.setQuantityPrice(orderDetails.getQuantityPrice());
						buyProdDetails.setThumbImageURL(prod.getThumbImageURL());
						buyProdDetails.setQuantityType(prod.getQuantityType());
						buyProdDetails.setAddress1(orderDetails.getAddress1());
						buyProdDetails.setAddress2(orderDetails.getAddress2());
						buyProdDetails.setAddressLat(orderDetails.getAddressLat());
						buyProdDetails.setAddressLong(orderDetails.getAddressLong());
						buyProdDetails.setLandmark(orderDetails.getLandmark());
						buyProdDetails.setPinCode(orderDetails.getPinCode());
						buyData.add(buyProdDetails);
					}

					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(buyData));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("data", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "No Orders available for UserID");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserID");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Order details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String placeOrder(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		int res = 0;
		try {
			logger.info("userData >>" + userData);
			Type listType = new TypeToken<List<OrderDetails>>() {
			}.getType();
			List<OrderDetails> orders = gson.fromJson(userData, listType);
			for (OrderDetails order : orders) {
				UserProfile user = userProfileDAO.getUserByUserId(Integer.parseInt(order.getUserId() + ""));
				if (user != null) {
					ProductDetails prod = productDAO.checkProductExists(order.getProductId());
					if (prod != null) {
						logger.info(prod.getQuantityType());
						order.setProductStatus(OrderStatus.ORDERPLACED.getId());
						order.setAvailableAddressId(prod.getAvailableAddressId());
						int quantityTypeId = productDAO.getQuantityId(prod.getQuantityType());
						order.setQuantityTypeId(quantityTypeId);
						order.setDeliveryStatus(OrderStatus.ORDERPLACED.getName());
						if (order.getAddress() != null) {
							UserAddress uA = order.getAddress();
							order.setAddress1(uA.getAddress1() != null ? uA.getAddress1() : "");
							order.setAddress2(uA.getAddress2() != null ? uA.getAddress2() : "");
							order.setAddressLat(uA.getAddressLat() != null ? uA.getAddressLat() : "");
							order.setAddressLong(uA.getAddressLong() != null ? uA.getAddressLong() : "");
							order.setPinCode(uA.getPinCode() != null ? uA.getPinCode() : "");
							order.setLandmark(uA.getLandmark() != null ? uA.getLandmark() : "");
						}

						/*String shipmentTrackingId = createTransactionShipment(order.getShipmentObjectId());
						if (shipmentTrackingId != null) {*/
							//order.setShipmentTrackingId(shipmentTrackingId);
							long orderId = orderDAO.saveOrder(order);
							if (orderId != -1) {
								int prods = productDAO.updateProductQuantity(order.getProductId(), order.getQuantity());
								int del = orderDAO.deleteFromCart(order.getProductId(),
										Integer.parseInt(order.getUserId() + ""));
								JSONObject orderData = new JSONObject();
								orderData.put("ProductID", prod.getId());
								orderData.put("ProductName", prod.getProductName());
								orderData.put("BoughtQuantity", order.getQuantity());
								orderData.put("TransactionAmount", order.getPaymentAmount());
								orderData.put("ProductOwnerName", prod.getProductOwnerName()!=null?prod.getProductOwnerName():"");
								orderData.put("ProductOwnerContact", prod.getProductOwnerContact()!=null?prod.getProductOwnerContact():"");
								orderData.put("OrderedUserContact", user.getMobileNumber());
								orderData.put("OrderedUserAddressDetails", (order.getAddress().getAddress1()+" "+order.getAddress().getCity()+" "+order.getAddress().getState()+" "+order.getAddress().getCountry()+" "+order.getAddress().getPinCode()).toString());
								String message = "An Order with order ID :"+orderId+", has been placed and its details are \n ";
								mailContent(order.getShipmentObjectId(), message, orderData);
								jsonObject.put("Status", 200);
								jsonObject.put("Description", "Successfully Order Placed.");
								
							} else {
								jsonObject.put("Status", 500);
								jsonObject.put("Description", "Unable to place Order");
							}
						/*} else {
							jsonObject.put("Status", 500);
							jsonObject.put("Description", "Unable to place Shipment");
						}*/
					} else {
						jsonObject.put("Status", 500);
						jsonObject.put("Description", "Invalid Product Id");
					}
				} else {
					jsonObject.put("Status", 500);
					jsonObject.put("Description", "Invalid User");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Order details");
		}
		return jsonObject.toString();
	}

	/*
	 * @RequestMapping(value = "/getShippersAvailable", method =
	 * RequestMethod.POST, produces = "application/json") public @ResponseBody
	 * String getShippersAvailable(HttpServletRequest request) throws
	 * JSONException { JSONObject jsonObject = new JSONObject(); JsonParser
	 * jsonParser = new JsonParser(); Gson gson = new Gson(); try {
	 * List<ShippingDetails> lst = orderDAO.getShippersAvailable(); if (lst !=
	 * null && lst.size() > 0) { JsonElement json = (JsonElement)
	 * jsonParser.parse(gson.toJson(lst)); jsonObject.put("Status", 200);
	 * jsonObject.put("Description", "Success."); jsonObject.put("Data", new
	 * JSONArray(json.toString())); } else { jsonObject.put("Status", 400);
	 * jsonObject.put("Description", "Unable to get the Shipping details"); } }
	 * catch (Exception e) { logger.error(e, e); jsonObject.put("Status", 500);
	 * jsonObject.put("Description", "Failed to get Shipping details"); } return
	 * jsonObject.toString(); }
	 */

	@RequestMapping(value = "/getShippersAvailable", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getShippersAvailable(@RequestParam String country, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			if (country != null) {
				List<ShipperDetails> lst = orderDAO.getAllShippersAvailableByCountry(country);
				if (lst != null && lst.size() > 0) {
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success.");
					jsonObject.put("Data", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to get the Shipping details");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid country");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Shipping details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateCart", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateCart(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			CartData order = gson.fromJson(userData, CartData.class);
			if (order.getUserId() == 0) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			} else {
				if (order.getProductId() == 0) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Product ID is not available");
				} else {
					UserProfile isUserExist = userProfileDAO.getUserByUserId(Integer.parseInt(order.getUserId() + ""));
					if (isUserExist.getId() != 0) {
						ProductDetails product = productDAO.checkProductExists(order.getProductId());
						if (product.getId() != 0) {
							int orderDetails = orderDAO.updateCartOrders(order);
							if (orderDetails == -1) {
								jsonObject.put("Status", 400);
								jsonObject.put("Description", "Unable to add to the Cart");
							} else {
								jsonObject.put("Status", 200);
								jsonObject.put("Description", "Successfully Updated the Cart");
							}
						} else {
							jsonObject.put("Status", 400);
							jsonObject.put("Description", "Invalid Product ID");
						}
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Invalid UserID");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to add Order details");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/deleteFromCart", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String deleteFromCart(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			CartData order = gson.fromJson(userData, CartData.class);
			if (order.getUserId() == 0) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			} else {
				if (order.getProductId() == 0) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Product ID is not available");
				} else {
					int orderDetails = orderDAO.deleteFromCart(order);
					if (orderDetails == -1) {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Unable to delete from the Cart");
					} else {
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Successfully deleted from Cart");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to delete item in cart ");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateOrderStatus(@RequestParam int orderId, @RequestParam String status,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		int orderUpdateStatus = -1;
		try {
			logger.info(" --> orderId " + orderId + " --> status " + status);
			OrderDetails order = orderDAO.checkOrderExists(orderId);
			if (order != null) {
				if (!(status.matches("[0-9]+"))) {
					orderUpdateStatus = orderDAO.updateOrderStatus(orderId, status);
				} else {
					if (Integer.parseInt(status) == OrderStatus.ORDERCANCELLED.getId()) {
						orderUpdateStatus = orderDAO.updateOrderStatus(orderId, OrderStatus.ORDERCANCELLED.getName());
					} else if (Integer.parseInt(status) == OrderStatus.ORDERDELIVERED.getId()) {
						orderUpdateStatus = orderDAO.updateOrderStatus(orderId, OrderStatus.ORDERDELIVERED.getName());
					} else if (Integer.parseInt(status) == OrderStatus.ORDERINTRANSIT.getId()) {
						orderUpdateStatus = orderDAO.updateOrderStatus(orderId, OrderStatus.ORDERINTRANSIT.getName());
					}
				}
				if (orderUpdateStatus != -1) {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Successfully Updated Order Status ");
				} else {
					jsonObject.put("Status", 500);
					jsonObject.put("Description", "Failed to Update Order Status ");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to Update Order Status ");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/getShippersByCountry", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getShippersByCountry(HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			List<LocationBasedShipper> locationLst = new ArrayList<LocationBasedShipper>();
			List<String> countryList = orderDAO.getAllShippersCountries();
			if (countryList != null && countryList.size() > 0) {
				for (String country : countryList) {
					List<LocationBasedShipper> lst = orderDAO.getShippersByLocation(country.toLowerCase());
					// getAllShippers();
					if (lst != null && lst.size() > 0) {
						List<ShippingDetails> lstSh = new ArrayList<ShippingDetails>();
						LocationBasedShipper lbs = new LocationBasedShipper();
						lbs.setCountry(country);
						for (LocationBasedShipper locationBasedShipper : lst) {
							ShippingDetails sD = new ShippingDetails();
							sD.setShipperDetails(locationBasedShipper.getShipperLink());
							sD.setShipperName(locationBasedShipper.getShippers());
							lstSh.add(sD);
						}
						lbs.setShippingDetails(lstSh);
						locationLst.add(lbs);
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Unable to get the Shipping details");
					}
				}
				jsonObject.put("Status", 200);
				jsonObject.put("Description", "Success.");
				JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(locationLst));
				jsonObject.put("Data", new JSONArray(json.toString()));
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Shipping details unavailable");
			}

			/*
			 * List<ShippingDetails> lst = orderDAO.getShippersAvailable(); if
			 * (lst != null && lst.size() > 0) { JsonElement json =
			 * (JsonElement) jsonParser.parse(gson.toJson(lst));
			 * jsonObject.put("Status", 200); jsonObject.put("Description",
			 * "Success."); jsonObject.put("Data", new
			 * JSONArray(json.toString())); } else { jsonObject.put("Status",
			 * 400); jsonObject.put("Description",
			 * "Unable to get the Shipping details"); }
			 */
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Shipping details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/addShipper", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String addShipper(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			ShipperDetails shipperDetails = gson.fromJson(userData, ShipperDetails.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(shipperDetails.getUserId());
			if (isUserExist != null) {
				int i = orderDAO.saveShipper(shipperDetails);
				if (i == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to save Shipper");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Shipper saved successfully");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to add Shipper details");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/addNewsLetterDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String addNewsLetterDetails(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			NewsLetter nLetter = gson.fromJson(userData, NewsLetter.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(nLetter.getUserId());
			if (isUserExist != null) {
				int i = orderDAO.saveNewsLetter(nLetter);
				if (i == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to save NewsLetter Details");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "News Letter request saved successfully");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to add NewsLetter details");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/addPayPalAccountDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String addPayPalAccountDetails(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			PayPalAccountData ppData = gson.fromJson(userData, PayPalAccountData.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(ppData.getUserId());
			if (isUserExist != null) {
				int i = orderDAO.savePayPalData(ppData);
				if (i == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to save PayPal Details");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "PayPal details saved successfully");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to save PayPal details");
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/addDonationDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String addDonationDetails(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			DonationData donationData = gson.fromJson(userData, DonationData.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(donationData.getUserId());
			if (isUserExist != null) {
				int i = orderDAO.saveDonationData(donationData);
				if (i == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to save Donation Details");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Donation details saved successfully");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "UserID is not available");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to save Donation details");
		}

		return jsonObject.toString();
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getShippersByOrder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getShippersByOrder(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		int res = 0;
		JSONArray parcelArr = new JSONArray();
		JsonParser jsonParser = new JsonParser();
		List<JSONObject> jArray = new ArrayList<JSONObject>();

		try {
			logger.info("userData >>" + userData);
			Type listType = new TypeToken<List<OrderDetails>>() {
			}.getType();
			List<OrderDetails> orders = gson.fromJson(userData, listType);
			for (OrderDetails order : orders) {
				if (order.getAddress() != null) {
					UserProfile user = userProfileDAO.getUserByUserId(Integer.parseInt(order.getUserId() + ""));
					if (user != null) {
						ProductDetails prod = productDAO.checkProductExists(order.getProductId());
						if (prod != null) {
							JSONObject jObject = new JSONObject();
							jObject.put("productId", prod.getId());
							if (order.getAddress() != null) {

								UserAddress uA = order.getAddress();

								if (uA.getAddress1() == null || uA.getCity() == null || uA.getCountry() == null
										|| uA.getPinCode() == null || uA.getState() == null || uA.getAddress1() == ""
										|| uA.getCity() == "" || uA.getCountry() == "" || uA.getPinCode() == ""
										|| uA.getState() == "") {
										
									jsonObject.put("Status", 400);
									jsonObject.put("Description", "Invalid to Address");
									break;

								} else if (prod.getAddress1() == null || prod.getCity() == null || prod.getCountry() == null
										|| prod.getPinCode() == null || prod.getState() == null || prod.getAddress1() == ""
										|| prod.getCity() == "" || prod.getCountry() == "" || prod.getPinCode() == ""
										|| prod.getState() == "") {
										
									jsonObject.put("Status", 400);
									jsonObject.put("Description", "Invalid from Address");
									break;

								} else {
									// To Address
									// HashMap<String, Object> addressToMap =
									// new
									// HashMap<String, Object>();
									JSONObject addressToMap = new JSONObject();
									addressToMap.put("name", user.getName() != null ? user.getName() : "user");
									addressToMap.put("company", "Go Shippo");
									addressToMap.put("street1", uA.getAddress1() != null ? uA.getAddress1() : "");
									addressToMap.put("city", uA.getCity() != null ? uA.getCity() : "");
									addressToMap.put("state", uA.getState() != null ? uA.getState() : "");
									addressToMap.put("zip", uA.getPinCode() != null ? uA.getPinCode() : "");
									addressToMap.put("country", uA.getCountry() != null ? uA.getCountry() : "");

									UserProfile Fromuser = userProfileDAO.getUserByUserId(prod.getProductOwenerID());
									// From Address
									// HashMap<String, Object> addressFromMap =
									// new
									// HashMap<String, Object>();
									JSONObject addressFromMap = new JSONObject();
									addressFromMap.put("name",
											Fromuser.getName() != null ? Fromuser.getName() : "user");
									addressFromMap.put("company", "Siniia");
									addressFromMap.put("street1", prod.getAddress1() != null ? prod.getAddress1() : "");
									addressFromMap.put("city", prod.getCity() != null ? prod.getCity() : "");
									addressFromMap.put("state", prod.getState() != null ? prod.getState() : "");
									addressFromMap.put("zip", prod.getPinCode() != null ? prod.getPinCode() : "");
									addressFromMap.put("country", prod.getCountry() != null ? prod.getCountry() : "");

									// Parcel
									// HashMap<String, Object> parcelMap = new
									// HashMap<String, Object>();
									JSONObject parcelMap = new JSONObject();
									parcelMap.put("length", prod.getLength() != 0 ? prod.getLength() : 0);
									parcelMap.put("width", prod.getWidth() != 0 ? prod.getWidth() : 0);
									parcelMap.put("height", prod.getHeight() != 0 ? prod.getHeight() : 0);
									parcelMap.put("distance_unit", "in");
									parcelMap.put("weight", prod.getWeight() != 0 ? prod.getWeight() : 0);
									parcelMap.put("mass_unit", "lb");

									parcelArr.put(parcelMap);

									// Shipment
									// HashMap<String, Object> shipmentMap = new
									// HashMap<String, Object>();
									JSONObject shipmentMap = new JSONObject();
									shipmentMap.put("address_to", addressToMap);
									shipmentMap.put("address_from", addressFromMap);
									shipmentMap.put("parcels", parcelArr);
									shipmentMap.put("async", false);

									String url = "https://api.goshippo.com/shipments";
									URL obj = new URL(url);
									HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
									con.setRequestMethod("POST");
									con.setRequestProperty("Authorization", "ShippoToken " + shippoToken);
									con.setRequestProperty("Content-Type", "application/json");
									String body = shipmentMap + "";
									// Send request
									con.setDoOutput(true);

									// Send request
									con.setDoOutput(true);
									DataOutputStream wr = new DataOutputStream(con.getOutputStream());
									wr.writeBytes(body);
									wr.flush();
									wr.close();

									BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
									String inputLine;
									StringBuffer response = new StringBuffer();
									while ((inputLine = in.readLine()) != null) {
										response.append(inputLine);
									}
									in.close();
									int statusCode = con.getResponseCode();
									String status = con.getResponseMessage();
									// Print the response

									logger.info("response" + response.toString());

									if (response != null) {
										JSONObject resp = new JSONObject(response.toString());
										if (resp.has("status")) {
											if (resp.getString("status").equalsIgnoreCase("SUCCESS")) {
												JSONArray rates = new JSONArray();
												JSONArray messages = new JSONArray();
												if (resp.has("rates"))
													rates = resp.getJSONArray("rates");
												if (resp.has("messages"))
													messages = resp.getJSONArray("messages");

												if (rates != null && rates.length() > 0) {
													Type listsType = new TypeToken<List<ShippingRates>>() {
													}.getType();
													List<ShippingRates> shippingRates = gson.fromJson(rates.toString(),
															listsType);
													JsonElement json = (JsonElement) jsonParser
															.parse(gson.toJson(shippingRates));
													jObject.put("rates", new JSONArray(json.toString()));
												} else {

													Type listsType = new TypeToken<List<ShippoResponseMessages>>() {
													}.getType();
													List<ShippoResponseMessages> shippingResponse = gson
															.fromJson(messages.toString(), listsType);
													JsonElement json = (JsonElement) jsonParser
															.parse(gson.toJson(shippingResponse));
													jObject.put("rates", new JSONArray());
													jObject.put("messages", new JSONArray(json.toString()));

												}

											}

										} else {
											jsonObject.put("Status", 500);
											jsonObject.put("Description", "Unable to create shipment from Shippo");
											break;
										}
										jArray.add(jObject);
									} else {
										jsonObject.put("Status", 500);
										jsonObject.put("Description",
												"Invalid Response from Shippo, Please contact admin");
										break;
									}
								}
							} else {
								jsonObject.put("Status", 500);
								jsonObject.put("Description", "Invalid address in request.");
								break;
							}

						} else {
							jsonObject.put("Status", 500);
							jsonObject.put("Description", "Invalid Product Id");
							break;
						}
					} else {
						jsonObject.put("Status", 500);
						jsonObject.put("Description", "Invalid User");
						break;

					}
				}

			}
			if (jArray != null && jArray.size() > 0) {
				jsonObject.put("Status", 200);
				jsonObject.put("Description", "Success.");
				jsonObject.put("Data", new JSONArray(jArray.toString()));
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get Order details");
		}
		return jsonObject.toString();
	}

	private String createTransactionShipment(String shipmentId) {
		String trackingId = null;
		try {

			String url = "https://api.goshippo.com/transactions";

			Map<String, Object> params = new LinkedHashMap<>();
			StringBuilder postData = new StringBuilder();
			params.put("rate", shipmentId);
			params.put("async", "false");
			params.put("label_file_type", "PDF");

			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}

			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "ShippoToken " + shippoToken);
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			con.setDoOutput(true);

			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("charset", "utf-8");
			con.setRequestProperty("Content-Length", Integer.toString(postDataBytes.length));
			con.setUseCaches(false);
			con.getOutputStream().write(postDataBytes);

			int intresult = con.getResponseCode();

			String strresult = con.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;

			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null)

			{

				response.append(inputLine);

			}
			logger.info("response -->" + response);

			JSONObject job = new JSONObject(response.toString());
			if (job.has("tracking_number")) {
				trackingId = job.getString("tracking_number");
			}
			in.close();
			con.disconnect();

		} catch (Exception e) {
			logger.error(e, e);
		}
		return trackingId;
	}
	
	
	private void mailContent(String email, String messageBody, JSONObject job) throws MessagingException {
		try {
			final Properties prop = new Properties();
			InputStream inputStream = UserController.class.getClassLoader()
					.getResourceAsStream("application.properties");
			prop.load(inputStream);
			StringBuilder sb = new StringBuilder(2000);
			sb.append("Hi,<br/>");
			sb.append("<br/> ");
			sb.append(messageBody);
			sb.append("<br/> ");
			sb.append("Product Id :"+job.getInt("ProductID"));
			sb.append("<br/> ");
			sb.append("Product Name :"+job.getString("ProductName"));
			sb.append("<br/> ");
			sb.append("Product Owner Name :"+job.getString("ProductOwnerName"));
			sb.append("<br/> ");
			sb.append("Product Owner Contact :"+job.getString("ProductOwnerContact"));
			sb.append("<br/> ");
			sb.append("Purchased Quantity :"+job.getInt("BoughtQuantity"));
			sb.append("<br/> ");
			sb.append("Total Price :"+job.getDouble("TransactionAmount"));
			sb.append("<br/> ");
			sb.append("<br/> ");
			sb.append("Delivery Details :: ");
			sb.append("<br/> ");
			sb.append("User Contact Number :"+job.getString("OrderedUserContact"));
			sb.append("<br/> ");
			sb.append("Address :"+job.getString("OrderedUserAddressDetails"));
			sb.append("<br/> ");
			sb.append("<br/> ");
			sb.append("Thanks, <br/> ");
			sb.append("SINIIA Team");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", prop.getProperty("siniia.mail.host"));
			prop.put("mail.smtp.port", prop.getProperty("siniia.mail.port")); // Get
																				// //
																				// the
																				// //
																				// Session
																				// //object.
			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(prop.getProperty("siniia.mail.id"),
							prop.getProperty("siniia.mail.password"));
				}
			});
			String[] tos = email.split(",");
			Address[] address = new Address[tos.length];
			for (int i = 0; i < tos.length; i++) {
				address[i] = new InternetAddress(tos[i]);
			}
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop.getProperty("siniia.mail.id")));
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("SINIIA ORDER CONFIRMATION EMAIL");
			message.setContent(sb.toString(), "text/html; charset=utf-8");
			Transport.send(message);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
}
