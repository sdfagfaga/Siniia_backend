package com.siniia.app.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.siniia.app.dao.UserProfileDAO;
import com.siniia.app.dbo.DeviceTypeData;
import com.siniia.app.dbo.SearchedData;
import com.siniia.app.dbo.UserAddress;
import com.siniia.app.dbo.UserProfile;
import com.siniia.app.dbo.Version;
import com.siniia.app.utils.ActionUtils;
import com.siniia.app.utils.PasswordEncryptionUtil;
import com.siniia.app.utils.S3Utils;
import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;

@Controller
public class UserController {
	private static final Log logger = LogFactory.getLog(UserController.class);

	@Autowired
	private UserProfileDAO userProfileDAOImpl;

	@Autowired
	private ActionUtils actionUtil;

	@Autowired
	private UserProfileDAO userProfileDAO;

	@Autowired
	private OrderDAO orderDao;

	private Gson gson = new Gson();

	public String createRandomCode(int codeLength) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < codeLength; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String createUser(@RequestParam(value = "contact", required = true) String contact,
			@RequestParam(value = "message", required = true) String message, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Properties prop = new Properties();
		InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			if (contact != null && contact != "" && message != null && message != "") {
				prop.load(inputStream);
				String username = prop.getProperty("dream.sms.username");
				String apikey = prop.getProperty("dream.sms.apikey");
				String senderName = prop.getProperty("dream.sms.sender");
				String encodedMessage = URLEncoder.encode(message, "UTF-8");
				/*
				 * String query =
				 * "http://trans.smsfresh.co/api/sendmsg.php?user=" + username +
				 * "&pass=" + password + "&sender=" + senderName + "&phone=" +
				 * contact + "&text=" + encodedMessage +
				 * "&priority=ndnd&stype=normal";
				 */

				String query = " http://newmsg.neudigisolutions.com/api/sendhttp.php?"
						+ "authkey=17220AfwO0ikN5f65e653P15&mobiles=" + contact + "&" + "message=" + message
						+ "&sender=STARNW&route=4&country=91";
				/*
				 * String query =
				 * "http://msg.neudigisolutions.com/sendSMS?username=" +
				 * username + "&message=" + encodedMessage + "&sendername=" +
				 * senderName + "&smstype=TRANS" + "&numbers=" + contact +
				 * "&apikey="+apikey;
				 */
				HttpURLConnection conn = (HttpURLConnection) new URL(query).openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				final StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					stringBuffer.append(line);
				}
				rd.close();
				String messageId = stringBuffer.toString();
				jsonObject.put("Status", "200");
				jsonObject.put("Description", "message sent successfully");
				jsonObject.put("messageId", messageId);
			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Contacts (or) message should not be null");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to register");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/sendOTP", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String sendOTP(@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		try {

		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Unable to update the data");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/verifyOTP", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String verifyOTP(@RequestParam(value = "data", required = true) String phoneNumber,
			@RequestParam(value = "otp", required = true) int otp, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			if (phoneNumber.contains("@")) {
				int id = userProfileDAOImpl.getUserInfoByEmail(phoneNumber, otp);
				if (id > 0) {
					userProfileDAOImpl.updateUserOtpVerifiedByEmail(phoneNumber);
					UserProfile user = userProfileDAO.getUserByUserId(id);
					List<UserProfile> userP = userProfileDAO.getUserListByUserId(id);
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
					if (json.toString().startsWith("{")) {
						jsObject.put("data", new JSONObject(json.toString()));
					} else {
						jsObject.put("data", new JSONArray(json.toString()));
					}
					jsonObject.put("Status", "200");
					jsonObject.put("Verified", "Yes");
					jsonObject.put("id", id);
					jsonObject.put("user", jsObject);
				} else {
					jsonObject.put("Status", "500");
					jsonObject.put("Verified", "No");
				}
			} else {
				int id = userProfileDAOImpl.getUserInfo(phoneNumber, otp);
				if (id > 0) {
					userProfileDAOImpl.updateUserOtpVerified(phoneNumber);
					UserProfile user = userProfileDAO.getUserByUserId(id);
					List<UserProfile> userP = userProfileDAO.getUserListByUserId(id);
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
					// logger.info(json);
					if (json.toString().startsWith("{")) {
						jsObject.put("data", new JSONObject(json.toString()));
					} else {
						jsObject.put("data", new JSONArray(json.toString()));
					}
					jsonObject.put("Status", "200");
					jsonObject.put("Verified", "Yes");
					jsonObject.put("id", id);
					jsonObject.put("user", jsObject);
				} else {
					jsonObject.put("Status", "500");
					jsonObject.put("Verified", "No");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Unable to update the data");
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/verifyEmailOTP", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String verifyEmailOTP(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "otp", required = true) int otp, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			int id = userProfileDAOImpl.getUserInfoByEmail(email, otp);
				if (id > 0) {
					userProfileDAOImpl.updateUserOtpVerifiedByEmail(email);
					UserProfile user = userProfileDAO.getUserByUserId(id);
					List<UserProfile> userP = userProfileDAO.getUserListByUserId(id);
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
					if (json.toString().startsWith("{")) {
						jsObject.put("data", new JSONObject(json.toString()));
					} else {
						jsObject.put("data", new JSONArray(json.toString()));
					}
					jsonObject.put("Status", "200");
					jsonObject.put("Verified", "Yes");
					jsonObject.put("id", id);
					jsonObject.put("user", jsObject);
				} else {
					jsonObject.put("Status", "500");
					jsonObject.put("Verified", "No");
				}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Unable to update the data");
		}
		return jsonObject.toString();
	}

	public int generateRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 1000 + r.nextInt(1000));
	}

	private void sendSms(String contact, String message) {
		Properties prop = new Properties();
		InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			if (contact != null && contact != "" && message != null && message != "") {
				prop.load(inputStream);
				/*
				 * String username = prop.getProperty("dream.sms.username");
				 * String apikey = prop.getProperty("dream.sms.apikey"); String
				 * senderName = prop.getProperty("dream.sms.sender"); String
				 * encodedMessage = URLEncoder.encode(message, "UTF-8");
				 * 
				 * String query =
				 * "http://trans.smsfresh.co/api/sendmsg.php?user=" + username +
				 * "&pass=" + password + "&sender=" + senderName + "&phone=" +
				 * contact + "&text=" + encodedMessage +
				 * "&priority=ndnd&stype=normal";
				 */

				String query = "http://newmsg.neudigisolutions.com/api/sendhttp.php?"
						+ "authkey=17220AfwO0ikN5f65e653P15&mobiles=91" + contact + "&" + "message=" + message
						+ "&sender=STARNW&route=4&country=0&DLT_TE_ID=1707161546391305073";
				// String encodedString = URLEncoder.encode(query, "UTF-8");
				HttpURLConnection conn = (HttpURLConnection) new URL(query).openConnection();
				// String data = apiKey + numbers + message + sender;
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				// conn.setRequestProperty("Content-Length",
				// Integer.toString(data.length()));
				// conn.getOutputStream().write(data.getBytes("UTF-8"));
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				final StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					stringBuffer.append(line);
				}
				rd.close();
				String messageId = stringBuffer.toString();
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private void mailContent(String email, String messageBody) throws MessagingException {
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
			message.setSubject("SINIIA Registration OTP");
			message.setContent(sb.toString(), "text/html; charset=utf-8");
			Transport.send(message);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/*
	 * @RequestMapping(value = "/updateProfile", method = RequestMethod.POST,
	 * produces = "application/json") public @ResponseBody String
	 * uploadFiles(@RequestParam(value = "apiKey", required = true) String
	 * apiKey, MultipartHttpServletRequest multipartRequest) throws
	 * JSONException { org.json.simple.parser.JSONParser jsonParser = new
	 * org.json.simple.parser.JSONParser(); org.json.simple.JSONArray array =
	 * new org.json.simple.JSONArray(); JSONObject output = new JSONObject();
	 * Object obj = new Object(); try { if (apiKey != null && !apiKey.isEmpty())
	 * { String decodedValue = actionUtil.getDecodedValue(apiKey);
	 * logger.info("decoded apiKey : " + decodedValue); boolean checkApiKey =
	 * userProfileDAOImpl.checkApiKeyGcmId(decodedValue); if (checkApiKey) { Set
	 * set = multipartRequest.getFileMap().entrySet(); Iterator it =
	 * set.iterator(); Map<String, MultipartFile> multipartFiles = new
	 * HashMap<String, MultipartFile>(); while (it.hasNext()) { Map.Entry me =
	 * (Map.Entry) it.next(); String fileName = (String) me.getKey();
	 * MultipartFile multipartFile = (MultipartFile) me.getValue();
	 * logger.info("Original fileName - " +
	 * multipartFile.getOriginalFilename()); logger.info("fileName - " +
	 * fileName); if (fileName.equalsIgnoreCase("input.json")) { File convFile =
	 * new File(fileName); convFile.createNewFile(); FileOutputStream fos = new
	 * FileOutputStream(convFile); IOUtils.copy(multipartFile.getInputStream(),
	 * fos); fos.close(); obj = jsonParser.parse(new FileReader(convFile)); //
	 * array = (org.json.simple.JSONArray) obj; } else {
	 * multipartFiles.put(fileName, multipartFile); } } if (obj != null) {
	 * JSONObject jsonObject = new JSONObject(obj.toString()); UserProfile
	 * userProfile = new UserProfile(); if (jsonObject.has("id")) {
	 * userProfile.setId(jsonObject.getInt("id"));
	 * userProfile.setDob(jsonObject.getString("dob"));
	 * userProfile.setGender(jsonObject.getString("gender"));
	 * userProfile.setCountry(jsonObject.getString("country"));
	 * userProfile.setState(jsonObject.getString("state"));
	 * userProfile.setDistrict(jsonObject.getString("district"));
	 * userProfile.setVillage(jsonObject.getString("village"));
	 * userProfile.setPinCode(jsonObject.getString("pinCode")); if
	 * (jsonObject.has("panNumber")) {
	 * userProfile.setPanNumber(jsonObject.getString("panNumber")); } if
	 * (jsonObject.has("name")) {
	 * userProfile.setName(jsonObject.getString("name")); } if
	 * (jsonObject.has("email")) {
	 * userProfile.setEmail(jsonObject.getString("email")); }
	 * 
	 * if (multipartFiles != null && !multipartFiles.isEmpty()) { set =
	 * multipartFiles.entrySet(); it = set.iterator(); int imageFileId = 1; int
	 * videoFileId = 1; int id = userProfile.getId(); while (it.hasNext()) {
	 * Map.Entry met = (Map.Entry) it.next(); String name = (String)
	 * met.getKey(); MultipartFile file = (MultipartFile) met.getValue();
	 * file.transferTo(new File( "/var/deamcric/" + id + "_" +
	 * file.getOriginalFilename() + ".png")); if (jsonObject.has("panNumber")) {
	 * userProfile.setPanUrl( "/var/deamcric/" + id + "_" +
	 * file.getOriginalFilename() + ".png"); } else { userProfile.setPhotoUrl(
	 * "/var/deamcric/" + id + "_" + file.getOriginalFilename() + ".png"); }
	 * 
	 * } } if (userProfile != null) {
	 * userProfileDAOImpl.updateProfileDetails(userProfile); }
	 * output.put("Status", 200); output.put("Description", "success");
	 * output.put("response", "success"); } } } else { output.put("Status",
	 * 400); output.put("Description", "Wrong apiKey"); } } else {
	 * output.put("Status", 400); output.put("Description",
	 * "No apikey recognized"); }
	 * 
	 * } catch (Exception e) { output.put("Status", 500);
	 * output.put("Description", "unable to update attachments");
	 * logger.error(e, e); } return output.toString(); }
	 */

	@RequestMapping(value = "/downloadImage", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String downloadImage(@RequestParam(value = "userId", required = true) int userId,
			HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject output = new JSONObject();
		try {
			Properties prop = new Properties();
			InputStream inputStream = UserController.class.getClassLoader()
					.getResourceAsStream("application.properties");
			prop.load(inputStream);
			UserProfile user = userProfileDAOImpl.getUserByUserId(userId);
			if (user != null && user.getProfilePicUrl() != null) {
				File file = new File(user.getProfilePicUrl());
				byte[] docs = null;
				if (file != null) {
					docs = new byte[(int) file.length()];
					FileInputStream fis = new FileInputStream(file);
					fis.read(docs);
					fis.close();
				}
				String headerKey = "Content-Disposition";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader(headerKey, "attachment; filename=" + file.getName());
				response.setHeader("Cache-Control", "no-store");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				ServletOutputStream responseOutputStream = response.getOutputStream();
				// IOUtils.write(image, responseOutputStream);
				responseOutputStream.write(docs);
				responseOutputStream.flush();
				responseOutputStream.close();
				output.put("Status", 200);
				output.put("Description", "Success");
			} else {
				output.put("Status", 400);
				output.put("Description", "Image is not available");
			}
		} catch (Exception e) {
			output.put("Status", 500);
			output.put("Description", "Failed to download");
			logger.error(e, e);
		}
		return output.toString();
	}

	private void resendSms(String contact, String message) {
		Properties prop = new Properties();
		InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			if (contact != null && contact != "" && message != null && message != "") {
				prop.load(inputStream);
				String username = prop.getProperty("dream.sms1.username");
				String password = prop.getProperty("dream.sms1.password");
				String senderName = prop.getProperty("dream.sms1.sender");
				String encodedMessage = URLEncoder.encode(message, "UTF-8");
				String query = "http://newmsg.neudigisolutions.com/api/sendhttp.php?"
						+ "authkey=17220AfwO0ikN5f65e653P15&mobiles=" + contact + "&" + "message=" + message
						+ "&sender=STARNW&route=4&country=91";
				/*
				 * String query =
				 * "http://msg.neudigisolutions.com/sendSMS?username=" +
				 * username + "&message=" + encodedMessage + "&sendername=" +
				 * senderName + "&smstype=TRANS" + "&numbers=" + contact +
				 * "&apikey="+apikey;
				 */
				String encodedString = URLEncoder.encode(query, "UTF-8");
				HttpURLConnection conn = (HttpURLConnection) new URL(query).openConnection();
				// String data = password + contact + message + senderName;
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				/*
				 * conn.setRequestProperty("Content-Length",
				 * Integer.toString(data.length()));
				 * conn.getOutputStream().write(data.getBytes("UTF-8"));
				 */
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				final StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					stringBuffer.append(line);
				}
				rd.close();
				String messageId = stringBuffer.toString();
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/*
	 * @RequestMapping(value = "/resendOTP", method = RequestMethod.POST,
	 * produces = "application/json") public @ResponseBody String
	 * resendOTP(@RequestParam(value = "phoneNumber", required = true) String
	 * phoneNumber, HttpServletRequest request) throws JSONException {
	 * JSONObject jsonObject = new JSONObject(); Gson gson = new Gson(); try {
	 * UserProfile profileVO = userProfileDAOImpl.getUserProfile(phoneNumber);
	 * if (profileVO != null) { int otp = 0; if (profileVO.getOtp() == 0) { otp
	 * = generateRandomNumber(); profileVO.setOtp(otp);
	 * profileVO.setOtpVerified("No"); String message = "Welcome to STAR11 " +
	 * otp + " is your OTP."; resendSms(phoneNumber, message);
	 * userProfileDAOImpl.updateUserOtp(profileVO); } else { otp =
	 * profileVO.getOtp(); String message = "Welcome to STAR11 " + otp +
	 * " is your OTP."; resendSms(phoneNumber, message); }
	 * jsonObject.put("Status", "200"); jsonObject.put("Description",
	 * "Success"); jsonObject.put("otp", otp); } else { jsonObject.put("Status",
	 * "500"); jsonObject.put("Description",
	 * "User with this email not available"); }
	 * 
	 * } catch (Exception e) { logger.error(e, e); jsonObject.put("Status",
	 * 500); jsonObject.put("Description", "Unable to update the data"); }
	 * return jsonObject.toString(); }
	 */

	@RequestMapping(value = "/getAPKVersion", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getAPKVersion(@RequestBody String userData) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			if (userData != null) {
				logger.info(userData);
				DeviceTypeData deviceData = gson.fromJson(userData, DeviceTypeData.class);
				UserProfile isUserExist = userProfileDAO.getUserByUserId(deviceData.getUserId());
				if (isUserExist != null) {
					int i = userProfileDAO.saveDeviceTypeData(deviceData);
					if (i != -1) {
						List<Version> versionlst = userProfileDAOImpl.getAPKVersion();
						if (versionlst != null && versionlst.size() > 0) {
							jsonObject.put("Status", 200);
							jsonObject.put("Description", "Success");
							jsonObject.put("AndroidAPkLiveVersion", versionlst.get(0).getVersion());
						} else {
							jsonObject.put("Status", 400);
							jsonObject.put("Description", "Failed to get Version data");
						}
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Unable to save Device Version Details");
					}
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Invalid userData");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get data");
		}
		return jsonObject.toString();

	}

	@RequestMapping(value = "/getLoginOTP", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json")
	public @ResponseBody String getLoginOTP(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info(userData);
			UserProfile usersProfile = gson.fromJson(userData, UserProfile.class);
			if (userData == null || userData.isEmpty()) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Please send email or Phone Number");
			} else {
				if (userData.contains("@")) {
					UserProfile user = userProfileDAOImpl.getUserProfileByEmail(usersProfile);
					if (user != null) {
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(user));
						jsObject.put("data", new JSONObject(user));
						int otp = 0;
						if (user.getOtp() == 0) {
							otp = generateRandomNumber();
							user.setOtp(otp);
							user.setIsOTPVerified(0);
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							// sendSms(user.getMobileCountry() +
							// user.getMobileNumber(), message);
							mailContent(user.getEmail(), message);
							userProfileDAOImpl.updateUserOtpGenerated(user);
						} else {
							otp = user.getOtp();
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							// sendSms(data, message);
							mailContent(user.getEmail(), message);
						}
						jsonObject.put("Status", "200");
						jsonObject.put("Description", "Success");
						jsonObject.put("otp", otp);
						jsonObject.put("user", jsObject);
					} else {
						/*
						 * jsonObject.put("Status", "500");
						 * jsonObject.put("Description",
						 * "User with this email not available");
						 */
						int otp = generateRandomNumber();
						usersProfile.setOtp(otp);
						long id = userProfileDAO.createUser(usersProfile);
						jsonObject.put("Status", "200");
						jsonObject.put("Description", "Success");
						jsonObject.put("otp", otp);
						jsonObject.put("userId", id);
					}
				} else {
					UserProfile user = userProfileDAOImpl.getUserProfileByPhoneNumber(usersProfile);
					if (user != null) {
						int otp = 0;
						if (user.getOtp() == 0) {
							otp = generateRandomNumber();
							user.setOtp(otp);
							user.setIsOTPVerified(0);
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							mailContent(user.getEmail(), message);
							/*
							 * if
							 * (user.getMobileCountry().equalsIgnoreCase("91"))
							 * { sendSms(user.getMobileNumber(), message); }
							 * else { sendTwilioSms("+" +
							 * user.getMobileCountry() + user.getMobileNumber(),
							 * message); }
							 */
							userProfileDAOImpl.updateUserOtpGenerated(user);
						} else {
							otp = user.getOtp();
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							mailContent(user.getEmail(), message);
							/*
							 * if
							 * (user.getMobileCountry().equalsIgnoreCase("91"))
							 * { sendSms(user.getMobileNumber(), message); }
							 * else { sendTwilioSms("+" +
							 * user.getMobileCountry() + user.getMobileNumber(),
							 * message); }
							 */
						}
						List<UserProfile> userP = userProfileDAO.getUserListByUserId(user.getId());
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
						jsObject.put("data", new JSONArray(json.toString()));
						jsonObject.put("Status", "200");
						jsonObject.put("Description", "Success");
						jsonObject.put("otp", otp);
						jsonObject.put("user", jsObject);
					} else {
						/*
						 * jsonObject.put("Status", "500");
						 * jsonObject.put("Description",
						 * "User with this mobile Number is not available");
						 */
						int otp = generateRandomNumber();
						usersProfile.setOtp(otp);
						long id = userProfileDAO.createUser(usersProfile);
						jsonObject.put("Status", "200");
						jsonObject.put("Description", "Success");
						jsonObject.put("otp", otp);
						jsonObject.put("userId", id);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get user details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/resendOTP", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json")
	public @ResponseBody String resendOTP(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info(userData);
			UserProfile usersProfile = gson.fromJson(userData, UserProfile.class);
			if (userData == null || userData.isEmpty()) {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Please send email or Phone Number");
			} else {
				if (userData.contains("@")) {
					UserProfile user = userProfileDAOImpl.getUserProfileByEmail(usersProfile);
					if (user != null) {
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(user));
						if (json.toString().startsWith("{")) {
							jsObject.put("data", new JSONObject(json.toString()));
						} else {
							jsObject.put("data", new JSONArray(json.toString()));
						}
						int otp = 0;
						if (user.getOtp() == 0) {
							otp = generateRandomNumber();
							user.setOtp(otp);
							user.setIsOTPVerified(0);
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							// sendSms(user.getMobileCountry() +
							// user.getMobileNumber(), message);
							mailContent(user.getEmail(), message);
							userProfileDAOImpl.updateUserOtpGenerated(user);
						} else {
							otp = user.getOtp();
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							// sendSms(data, message);
							mailContent(user.getEmail(), message);
						}
						jsonObject.put("Status", "200");
						jsonObject.put("Description", "Success");
						jsonObject.put("otp", otp);
						jsonObject.put("user", jsObject);
					} else {

						jsonObject.put("Status", "500");
						jsonObject.put("Description", "User with this email not available");

					}
				} else {
					UserProfile user = userProfileDAOImpl.getUserProfileByPhoneNumber(usersProfile);
					if (user != null) {
						int otp = 0;
						if (user.getOtp() == 0) {
							otp = generateRandomNumber();
							user.setOtp(otp);
							user.setIsOTPVerified(0);
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							mailContent(user.getEmail(), message);
							/*
							 * if
							 * (user.getMobileCountry().equalsIgnoreCase("91"))
							 * { sendSms(user.getMobileNumber(), message); }
							 * else { sendTwilioSms("+" +
							 * user.getMobileCountry() + user.getMobileNumber(),
							 * message); }
							 */
							userProfileDAOImpl.updateUserOtpGenerated(user);
						} else {
							otp = user.getOtp();
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							mailContent(user.getEmail(), message);
							/*
							 * if
							 * (user.getMobileCountry().equalsIgnoreCase("91"))
							 * { sendSms(user.getMobileNumber(), message); }
							 * else { sendTwilioSms("+" +
							 * user.getMobileCountry() + user.getMobileNumber(),
							 * message); }
							 */
						}
						List<UserProfile> userP = userProfileDAO.getUserListByUserId(user.getId());
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
						if (json.toString().startsWith("{")) {
							jsObject.put("data", new JSONObject(json.toString()));
						} else {
							jsObject.put("data", new JSONArray(json.toString()));
						}
						//jsObject.put("data", new JSONArray(json.toString()));
						
						jsonObject.put("Status", "200");
						jsonObject.put("Description", "Success");
						jsonObject.put("otp", otp);
						jsonObject.put("user", jsObject);
					} else {

						jsonObject.put("Status", "500");
						jsonObject.put("Description", "User with this mobile Number is not available");

						/*
						 * int otp = generateRandomNumber();
						 * usersProfile.setOtp(otp); long id =
						 * userProfileDAO.createUser(usersProfile);
						 * jsonObject.put("Status", "200");
						 * jsonObject.put("Description", "Success");
						 * jsonObject.put("otp", otp); jsonObject.put("userId",
						 * id);
						 */
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to get user details");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String registerUser(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info(userData);
			UserProfile user = gson.fromJson(userData, UserProfile.class);
			long id = userProfileDAO.registerUser(user);
			if (id != -1) {
				jsonObject.put("Status", 200);
				jsonObject.put("Description", "Successfully User Registered");
				if (user.getAddress1() != null || user.getAddress2() != null || user.getAddressLat() != null
						|| user.getAddressLong() != null) {
					user.setId(Integer.parseInt(id + ""));
					userProfileDAO.updateAddress(user);
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "User already exists");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to register user details");
		}

		return jsonObject.toString();

	}

	@RequestMapping(value = "/updateUserType", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateUserType(@RequestParam String userType, @RequestParam String userId,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info("userType >> " + userType + " userId >>" + userId);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(Integer.parseInt(userId));
			if (isUserExist != null) {
				long updateUserType = userProfileDAO.updateUserType(userId, userType);
				if (updateUserType == -1) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to update UserType");
				} else if (updateUserType == 0) {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Invalid UserType");
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Successfully updated userType");
				}
			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update UserType");
		}
		return jsonObject.toString();

	}

	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateAddress(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info("userData >> " + userData);
			UserProfile user = gson.fromJson(userData, UserProfile.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(user.getId());
			if (isUserExist != null) {
				long updateAddress = userProfileDAO.updateAddress(user);
				if (updateAddress != -1) {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Successfully updated Address");
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to update Address");
				}
			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update Address");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getCounts", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getCounts(@RequestParam String userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject job = new JSONObject();

		try {
			logger.info("userData >> " + userId);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(Integer.parseInt(userId));
			if (isUserExist != null) {
				long count = orderDao.getCartCount(Long.parseLong(userId));
				job.put("NotificationsCount", isUserExist.getNotificationsCount());
				job.put("BasketOrdersCount", count);
				jsonObject.put("Status", 200);
				jsonObject.put("Description", "Success");
				jsonObject.put("counts", job);

			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update UserType");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getUserDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getUserDetails(@RequestParam String userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userData >> " + userId);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(Integer.parseInt(userId));
			if (isUserExist != null) {
				UserAddress userP = userProfileDAO.getLatestUserAddressByUserId(Integer.parseInt(userId));
				// UserAddressListByUserId(Integer.parseInt(userId));
				List<UserProfile> userProfile = userProfileDAO.getUserListByUserId(Integer.parseInt(userId));
				if (userP != null) {
					JsonElement jsons = (JsonElement) jsonParser.parse(gson.toJson(userProfile));
					jsObject.put("data", new JSONArray(jsons.toString()));
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
					if (json.toString().startsWith("[")) {
						jsonObject.put("userAddressData", new JSONArray(json.toString()));
					} else {
						jsonObject.put("userAddressData", new JSONObject(json.toString()));
					}

					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("userData", jsObject);
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "No address found for user");
				}

			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to getUserDetails");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateProfile(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info("userData >> " + userData);
			UserProfile user = gson.fromJson(userData, UserProfile.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(user.getId());
			if (isUserExist != null) {
				UserProfile userProfile = new UserProfile();
				userProfile.setId(isUserExist.getId());
				if (user.getEmail().equalsIgnoreCase(isUserExist.getEmail())) {
					userProfile.setEmail(user.getEmail());
				} else {
					userProfile.setEmail(user.getEmail());
				}
				if (user.getMobileCountry().equalsIgnoreCase(isUserExist.getMobileCountry())) {
					userProfile.setMobileCountry(user.getMobileCountry());
				} else {
					userProfile.setMobileCountry(user.getMobileCountry());
				}
				if (user.getName().equalsIgnoreCase(isUserExist.getName())) {
					userProfile.setName(isUserExist.getName());
				} else {
					userProfile.setName(user.getName());
				}
				if (user.getMobileNumber().equalsIgnoreCase(isUserExist.getMobileNumber())) {
					userProfile.setMobileNumber(user.getMobileNumber());
				} else {
					userProfile.setMobileNumber(user.getMobileNumber());
				}
				int i = userProfileDAO.updateUserProfile(userProfile);
				if (i != -1) {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Updated User Profile Successfully");
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to Update User Profile");
				}
			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update UserProfile");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateCounts", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateCounts(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject job = new JSONObject();
		try {
			logger.info("userData >> " + userData);
			UserProfile user = gson.fromJson(userData, UserProfile.class);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(user.getId());
			if (isUserExist != null) {
				UserProfile userProfile = new UserProfile();
				userProfile.setId(isUserExist.getId());
				if (user.getNotificationsCount() == isUserExist.getNotificationsCount()) {
					userProfile.setNotificationsCount(user.getNotificationsCount());
				} else {
					userProfile.setNotificationsCount(user.getNotificationsCount());
				}
				if (user.getBasketOrdersCount() == isUserExist.getBasketOrdersCount()) {
					user.setBasketOrdersCount(user.getBasketOrdersCount());
				} else {
					user.setBasketOrdersCount(user.getBasketOrdersCount());
				}

				int i = userProfileDAO.updateUserCounts(userProfile);

				if (i != -1) {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Updated User Counts Successfully");
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to Update User Counts");
				}
			} else {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update UserCounts");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateUserProfile(MultipartHttpServletRequest multipartRequest,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
		UserProfile userProfile = new UserProfile();
		UserAddress userAddress = new UserAddress();
		String imagesUrl = "";
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
				logger.info(">> OBJ is >>" + array.toJSONString());
				JSONObject jsonObjects = new JSONObject(array.get(0).toString());
				if (jsonObjects.has("id")) {
					userProfile.setId(jsonObjects.getInt("id"));
					UserProfile isUserExist = userProfileDAO.getUserByUserId(userProfile.getId());
					if (isUserExist != null) {
						if (isUserExist.getMobileCountry().equalsIgnoreCase("91")) {
							userAddress.setCountry("INDIA");
						} else if (isUserExist.getMobileCountry().equalsIgnoreCase("1")) {
							userAddress.setCountry("US");
						} else {
							userAddress.setCountry("AFRICA");
						}

						if (jsonObjects.has("state")) {
							userAddress.setState(
									jsonObjects.getString("state") != null ? jsonObjects.getString("state") : "");
						}
						userAddress.setUserId(jsonObjects.getInt("id"));
						if (jsonObjects.has("address1")) {
							userAddress.setAddress1(
									jsonObjects.getString("address1") != null ? jsonObjects.getString("address1") : "");
						}
						if (jsonObjects.has("address2")) {
							userAddress.setAddress2(
									jsonObjects.getString("address2") != null ? jsonObjects.getString("address2") : "");
						}
						if (jsonObjects.has("landmark")) {
							userAddress.setLandmark(
									jsonObjects.getString("landmark") != null ? jsonObjects.getString("landmark") : "");
						}
						if (jsonObjects.has("pinCode")) {
							userAddress.setPinCode(
									jsonObjects.getString("pinCode") != null ? jsonObjects.getString("pinCode") : "");
						}
						if (jsonObjects.has("LastLocationLat")) {
							userAddress.setAddressLat(jsonObjects.getString("LastLocationLat") != null
									? jsonObjects.getString("LastLocationLat") : "");
						}
						if (jsonObjects.has("LastLocationLong")) {
							userAddress.setAddressLong(jsonObjects.getString("LastLocationLong") != null
									? jsonObjects.getString("LastLocationLong") : "");
						}
						if (jsonObjects.has("city")) {
							userAddress.setCity(
									jsonObjects.getString("city") != null ? jsonObjects.getString("city") : "");
						}
					}
					/*
					 * if (jsonObjects.has("name")) {
					 * userProfile.setName(jsonObjects.getString("name")); }
					 */
					/*
					 * if (jsonObjects.has("email")) {
					 * userProfile.setEmail(jsonObjects.getString("email")); }
					 */
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
					String fileName = (String) met.getKey();
					MultipartFile file = (MultipartFile) met.getValue();
					// String fileName = file.getOriginalFilename();
					if (!fileName.isEmpty()) {
						logger.info("Original fileName - " + file.getOriginalFilename());
						logger.info("fileName - " + fileName);
						String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
						String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
						LocalDateTime now = LocalDateTime.now();
						DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
						String formatDateTime = now.format(format);
						String keyName = "user_" + userProfile.getId() + "_" + imageName + "_" + formatDateTime + "."
								+ fileType.toUpperCase();
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
			long id = userProfileDAO.updateUser(userProfile);
			if (id != -1) {
				userProfileDAO.updateUserAddress(userAddress);
				logger.info(">> userAddress" + userAddress.toString());
				if (imagesUrl != "") {
					int j = userProfileDAO.updateUserImage(imagesUrl, userProfile.getId());
					if (j == -1) {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Failed to update Images path");
					} else {
						jsonObject.put("Status", 200);
						jsonObject.put("Description", "Successfully Updated User Details and uploaded Image");
					}
				} else {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Successfully Updated User Details");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Unable to Update User Profile");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update user details");
		}

		return jsonObject.toString();

	}

	@RequestMapping(value = "/registerUsers", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String registerUsers(@RequestBody String userData, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info("userData >> " + userData);
			UserProfile user = gson.fromJson(userData, UserProfile.class);
			/*
			 * UserProfile isUserExist =
			 * userProfileDAO.getUserByUserId(user.getId());
			 */
			if (user.getEmail() == null || user.getMobileCountry() == null || user.getMobileNumber() == null
					|| user.getEmail() == "" || user.getMobileCountry() == "" || user.getMobileNumber() == ""
					|| user.getEmail().length() == 0 || user.getMobileCountry().length() == 0
					|| user.getMobileNumber().length() == 0) {
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Mobile Number and Email Id are mnadatory fields.");
			} else {

				UserProfile userEmailProfile = userProfileDAOImpl.getUserProfileByEmail(user);

				if (userEmailProfile != null) {
					if (userEmailProfile.getMobileNumber().equalsIgnoreCase(user.getMobileNumber())) {
						jsonObject.put("Status", 500);
						jsonObject.put("Description", "Email Id already Exists");
					} else {
						jsonObject.put("Status", 500);
						jsonObject.put("Description", "Email Id already Exists with a different mobile number");
					}
				} else {
					UserProfile userProfile = new UserProfile();
					UserAddress userAddress = new UserAddress();
					int otp = generateRandomNumber();
					userProfile.setOtp(otp);
					userProfile.setIsOTPVerified(0);
					userProfile.setEmail(user.getEmail());
					userProfile.setName(user.getName() != null ? user.getName() : "");
					userProfile.setMobileCountry(user.getMobileCountry());
					userProfile.setMobileNumber(user.getMobileNumber());
					String password = user.getPassword();

					PasswordEncryptionUtil encrypt = new PasswordEncryptionUtil();
					String encryptPwd = encrypt.getEncryptedPassword(password);

					userProfile.setPassword(encryptPwd);

					//logger.info("password >> " + password + " enc password >>" + encryptPwd);

					if (user.getMobileCountry().equalsIgnoreCase("91")) {
						userAddress.setCountry("INDIA");
					} else if (user.getMobileCountry().equalsIgnoreCase("1")) {
						userAddress.setCountry("US");
					} else {
						userAddress.setCountry("AFRICA");
					}

					userAddress.setAddress1(user.getAddress1() != null ? user.getAddress1() : "");
					userAddress.setAddress2(user.getAddress2() != null ? user.getAddress2() : "");
					userAddress.setAddressLat(user.getAddressLat() != null ? user.getAddressLat() : "");
					userAddress.setAddressLong(user.getAddressLong() != null ? user.getAddressLong() : "");
					userAddress.setCity(user.getCity() != null ? user.getCity() : "");
					userAddress.setCountry(user.getCountry() != null ?user.getCountry() : "");
					userAddress.setLandmark(user.getLandmark() != null ? user.getLandmark() : "");
					userAddress.setPinCode(user.getPinCode() != null ? user.getPinCode() : "");
					userAddress.setState(user.getState() != null ? user.getState() : "");

					int i = (int) userProfileDAO.registerUser(userProfile);
					if (i != -1) {
						userAddress.setUserId(i);
						userProfile.setId(i);
						int j = userProfileDAO.updateUserAddress(userAddress);
						if (j != -1) {
							String message = "Welcome to Siniia, " + otp + " is your OTP.";
							jsonObject.put("otp", otp);
							jsonObject.put("Status", 200);
							jsonObject.put("Description", "Saved User Profile Successfully");
							mailContent(userProfile.getEmail(), message);
						} else {
							jsonObject.put("Status", 400);
							jsonObject.put("Description", "Unable to Save User Profile");
						}
					} else {
						jsonObject.put("Status", 400);
						jsonObject.put("Description", "Unable to save User Profile");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update UserProfile");
		}
		return jsonObject.toString();
	}

	private void sendTwilioSms(String contact, String userMessage) {
		Properties prop = new Properties();
		InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			if (contact != null && contact != "" && userMessage != null && userMessage != "") {
				logger.info("contact --> " + contact.toString() + "\t userMessage --> " + userMessage.toString());
				prop.load(inputStream);
				String ACCOUNT_SID = prop.getProperty("my.twilio.accountSid");
				String AUTH_TOKEN = prop.getProperty("my.twilio.authToken");
				// String messageServiceId =
				// prop.getProperty("my.twilio.senderId");
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
				/*
				 * Message message = Message.creator(new
				 * com.twilio.type.PhoneNumber(contact),
				 * "MG45afce13b4a6746b6ee11110f17f5053", userMessage.toString())
				 * .create();
				 */
				/*
				 * Message messages = Message.creator(new
				 * com.twilio.type.PhoneNumber(contact),
				 * "MG45afce13b4a6746b6ee11110f17f5053",
				 * userMessage.toString()).create();
				 * System.out.println(messages.getSid());
				 * logger.info(messages.toString());
				 */
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@RequestMapping(value = "/getAddressByUserId", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getAddressByUserId(@RequestParam int userId, HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info("userId >> " + userId);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(userId);
			if (isUserExist != null) {
				List<UserAddress> lst = userProfileDAO.getUserAddressListByUserId(userId);
				if (lst != null && lst.size() > 0) {
					JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(lst));
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Success");
					jsonObject.put("data", new JSONArray(json.toString()));
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to fetch User Address");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to update Address");
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/emailLogin", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String emailLogin(@RequestBody String userData, HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsObject = new JSONObject();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		try {
			logger.info(userData);
			UserProfile user = gson.fromJson(userData, UserProfile.class);
			if(user.getEmail()=="" || user.getEmail()==null || user.getEmail().length()==0 || 
					user.getPassword()==""||user.getPassword()==null||user.getPassword().length()==0){
				jsonObject.put("Status", 500);
				jsonObject.put("Description", "Please send email ID and Password for Login.");
			}else{
				UserProfile isUserExist = userProfileDAO.getUserProfileByEmail(user);
				if(isUserExist!=null){
					PasswordEncryptionUtil encrypt = new PasswordEncryptionUtil();
					String encPassword = encrypt.getEncryptedPassword(user.getPassword());
					if(encPassword.equalsIgnoreCase(isUserExist.getPassword())){
						List<UserProfile> userP = userProfileDAO.getUserListByUserId(isUserExist.getId());
						JsonElement json = (JsonElement) jsonParser.parse(gson.toJson(userP));
						if (json.toString().startsWith("{")) {
							jsObject.put("data", new JSONObject(json.toString()));
						} else {
							jsObject.put("data", new JSONArray(json.toString()));
						}
						jsonObject.put("Status", "200");
						jsonObject.put("Verified", "Yes");
						jsonObject.put("user", jsObject);
					}else{
						jsonObject.put("Status", 500);
						jsonObject.put("Description", "Invalid email ID or Password.");
					}
				}else{
					jsonObject.put("Status", 500);
					jsonObject.put("Description", "Invalid email ID or Password.");	
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Unable to update the data");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/setSearchData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String setSearchData(@RequestParam int userId, @RequestParam String searchKey,
			HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			logger.info("userId >> " + userId);
			UserProfile isUserExist = userProfileDAO.getUserByUserId(userId);
			if (isUserExist != null) {
				SearchedData searchData = new SearchedData();
				searchData.setUserId(userId + "");
				searchData.setSearchedString(searchKey);
				int i = userProfileDAO.saveSearchData(searchData);
				if (i != -1) {
					jsonObject.put("Status", 200);
					jsonObject.put("Description", "Saved searched Data Successfully.");
				} else {
					jsonObject.put("Status", 400);
					jsonObject.put("Description", "Unable to save searched Data.");
				}
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "Invalid UserId");
			}
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to save searchData");
		}
		return jsonObject.toString();
	}

}
