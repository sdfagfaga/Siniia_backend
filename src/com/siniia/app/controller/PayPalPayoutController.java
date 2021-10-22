package com.siniia.app.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.siniia.app.dao.OrderDAO;
import com.siniia.app.dao.UserProfileDAO;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.PayPalAccountData;
import com.siniia.app.dbo.UserProfile;

@Controller
@Configuration
@EnableScheduling
public class PayPalPayoutController {

	private static final Log logger = LogFactory.getLog(PayPalPayoutController.class);

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private UserProfileDAO userProfileDAO;

	private String clientId = "Ae8IC5moctJz2K7lwkexQPD-qYUoQmSP2WsUaNQsFjXsUAJOYvH6POTezyO8Zb9FiqS9gWsrGNqVHpeV";
	private String clientSecret = "EDK3TmgNBFkoR6L8Ni-EUu22MU3o_Lm6cmk7dX92DIT4w1VpR-JmasCKhLbDXswSTUeA09hhbzspUpp-";

	@Scheduled(cron = "* */5 * * * * ")
	@RequestMapping(value = "/paypalPayoutCron", method = RequestMethod.GET, produces = "application/json")
	public void paypalPayoutCron() {
		try {
			List<OrderDetails> orders = orderDAO.getPayoutOrders();
			if (orders != null && orders.size() > 0) {
				 logger.info("Inside paypalPayoutCron, pending orders size >>" + orders.size());
				for (OrderDetails orderdetails : orders) {
					if (orderdetails.getPaymentDetails() != null
							&& orderdetails.getPaymentDetails().toString().contains("live")) {
						String payoutBody = createPayoutBody(orderdetails);
						if (payoutBody != null && payoutBody.length() > 0) {
							if (!payoutBody.equalsIgnoreCase("D")) {
								String base = clientId + ":" + clientSecret;
								String encoding = Base64.getEncoder().encodeToString((base).getBytes());
								String tokenurl = "https://api-m.paypal.com/v1/oauth2/token";
								URL obje = new URL(tokenurl);
								HttpsURLConnection conn = (HttpsURLConnection) obje.openConnection();
								conn.setRequestMethod("POST");
								conn.setRequestProperty("accept", "application/json");
								conn.setRequestProperty("accept-language", "en_US");
								conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
								conn.setRequestProperty("Authorization", "Basic " + encoding);
								String tokenbody = "grant_type=client_credentials";

								// Send request
								conn.setDoOutput(true);
								DataOutputStream tokenwr = new DataOutputStream(conn.getOutputStream());
								tokenwr.writeBytes(tokenbody);
								tokenwr.flush();
								tokenwr.close();

								BufferedReader tokenin = new BufferedReader(
										new InputStreamReader(conn.getInputStream()));
								String tokeninputLine;
								StringBuffer responses = new StringBuffer();
								while ((tokeninputLine = tokenin.readLine()) != null) {
									responses.append(tokeninputLine);
								}
								tokenin.close();

								// Print the response
								JSONObject job = new JSONObject(responses.toString());
								// logger.info(">> job >>" +
								// job.getString("access_token"));

								String url = "https://api-m.paypal.com/v1/payments/payouts";
								URL obj = new URL(url);
								HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
								con.setRequestMethod("POST");
								con.setRequestProperty("Authorization", "Bearer " + job.getString("access_token"));
								con.setRequestProperty("Content-Type", "application/json");
								String body = payoutBody;
								logger.info("payOutBody >>" + payoutBody);
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
								// Print the response
								System.out.println(response.toString());

								if (statusCode == 201) {
									int i = orderDAO.updateOrderPayoutStatus(orderdetails.getId(), "Y");
									if (i == -1) {
										logger.info("Unable to update OrderPayout Status " + orderdetails.getId());
									}
								} else {
									int i = orderDAO.updateOrderPayoutStatus(orderdetails.getId(), "D");
									if (i == -1) {
										logger.info("Unable to update OrderPayout Status " + orderdetails.getId());
									}
								}
							} else {
								int i = orderDAO.updateOrderPayoutStatus(orderdetails.getId(), "D");
								if (i == -1) {
									logger.info("Unable to update OrderPayout Status " + orderdetails.getId());
								}
							}

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
	}

	private String createPayoutBody(OrderDetails order) {
		try {
			UserProfile isUserExist = userProfileDAO.getUserByUserId(Integer.parseInt(order.getUserId() + ""));
			if (isUserExist != null) {
				if (isUserExist.getMobileCountry().equalsIgnoreCase("1")) {
					PayPalAccountData paypalData = orderDAO.getPaypalAccountDataByUserId(order.getUserId());
					if (paypalData != null) {
						JSONObject senderBatchHeader = new JSONObject();
						senderBatchHeader.put("email_subject", "You have a payment");
						senderBatchHeader.put("sender_batch_id", "siniiaPayoutId" + new Date().getTime());
						JSONArray items = new JSONArray();
						JSONObject receiverData = new JSONObject();
						receiverData.put("recipient_type", "PAYPAL_ID");
						JSONObject amount = new JSONObject();
						amount.put("value", order.getPaymentAmount() + "");
						amount.put("currency", "USD");
						receiverData.put("amount", amount);
						receiverData.put("receiver", paypalData.getPayPalAccountId());
						receiverData.put("note", "Payout for the sale of Product in SINIIA");
						receiverData.put("sender_item_id", "item" + new Date().getTime() + "");
						items.put(receiverData);
						JSONObject payOutData = new JSONObject();
						payOutData.put("sender_batch_header", senderBatchHeader);
						payOutData.put("items", items);
						return payOutData.toString();
					} else {
						return "D";
					}
				} else if (isUserExist.getMobileCountry().equalsIgnoreCase("91")) {
					PayPalAccountData paypalData = orderDAO.getPaypalAccountDataByUserId(order.getUserId());
					if (paypalData != null) {
						JSONObject senderBatchHeader = new JSONObject();
						senderBatchHeader.put("email_subject", "You have a payment");
						senderBatchHeader.put("sender_batch_id", "siniiaPayoutId" + new Date().getTime());
						JSONArray items = new JSONArray();
						JSONObject receiverData = new JSONObject();
						receiverData.put("recipient_type", "PAYPAL_ID");
						JSONObject amount = new JSONObject();
						amount.put("value", order.getPaymentAmount() + "");
						amount.put("currency", "INR");
						receiverData.put("amount", amount);
						receiverData.put("receiver", paypalData.getPayPalAccountId());
						receiverData.put("note", "Payout for the sale of Item in SINIIA");
						receiverData.put("sender_item_id", "item" + new Date().getTime() + "");
						items.put(receiverData);
						JSONObject payOutData = new JSONObject();
						payOutData.put("sender_batch_header", senderBatchHeader);
						payOutData.put("items", items);
						return payOutData.toString();
					} else {
						return "D";
					}
				} else {
					return "D";
				}
			} else {
				return "D";
			}
			/*
			 * {"sender_batch_header": {"email_subject":"You have a payment",
			 * "sender_batch_id":"batch-12" }, "items":[
			 * {"recipient_type":"EMAIL","amount":
			 * {"value":"1.00","currency":"USD"},
			 * "receiver":"phanivaddamani@outlook.com",
			 * "note":"Payouts sample transaction", "sender_item_id":"item-2"}
			 * ]}
			 */
		} catch (Exception e) {
			logger.error(e, e);
			e.printStackTrace();
		}
		return null;
	}
}
