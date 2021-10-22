package com.siniia.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

public class ActionUtils {

	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(ActionUtils.class);

	/** The message key. */
	private final String MESSAGE_KEY = "message";

	/*public String sendCommand(String regId, String userMessage) throws IOException {
		Result result = null;
		String errCode = null;
		String messageId = "gcmmessageid" + System.currentTimeMillis();
		try {
			*//** The google server key. *//*
			Properties prop = new Properties();
			InputStream inputStream = ActionUtils.class.getClassLoader().getResourceAsStream("application.properties");
			prop.load(inputStream);
			final String GOOGLE_SERVER_KEY = prop.getProperty("dreamcric.andriod.key");
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().timeToLive(2419200).delayWhileIdle(false)
					.addData(MESSAGE_KEY, userMessage).addData("messageid", messageId).build();

			result = sender.send(message, regId, 1);
			logger.info("Result in sendCommand::" + result + " \nmessage::" + message + " \nGCMID::" + regId
					+ " messageId::" + messageId);

		} catch (Exception e) {
			logger.error(e, e);
		}
		// GcmData gcmData = new GcmData(regId, userMessage, messageId,
		// new Date(), errCode);
		// gcmDataDAO.insertGcm(gcmData);
		return messageId;
	}*/

	public String sendIOSNotification(String gcmId, String message) throws MalformedURLException, IOException {
		// logger.info("inside sendIOSNotification >>"+gcmId);
		String messageId = "gcmmessageid" + System.currentTimeMillis();
		// BasicConfigurator.configure();
		try {
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(1);
			payload.addSound("default");
			payload.addCustomDictionary("messageId", messageId);
			Properties prop = new Properties();
			InputStream inputStream = ActionUtils.class.getClassLoader().getResourceAsStream("application.properties");
			prop.load(inputStream);
			// System.out.println(payload.toString());
			String path = prop.getProperty("dreamcric.ios.cert.path");
			String password = prop.getProperty("dreamcric.ios.cert.password");
			// logger.info("cert path >>"+path);
			// InputStream is = new URL(path).openStream();
			List<PushedNotification> NOTIFICATIONS = Push.payload(payload, path, password, false, gcmId);
			for (PushedNotification NOTIFICATION : NOTIFICATIONS) {
				if (NOTIFICATION.isSuccessful()) {
					/* APPLE ACCEPTED THE NOTIFICATION AND SHOULD DELIVER IT */
					/*
					 * logger.info("PUSH NOTIFICATION SENT SUCCESSFULLY TO: " +
					 * NOTIFICATION.getDevice().getToken()+"\nmessage::" +
					 * message + " \nGCMID::" + gcmId + " messageId::" +
					 * messageId);
					 */
					/* STILL NEED TO QUERY THE FEEDBACK SERVICE REGULARLY */
				} else {
					String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
					/*
					 * ADD CODE HERE TO REMOVE INVALIDTOKEN FROM YOUR DATABASE
					 */
					/* FIND OUT MORE ABOUT WHAT THE PROBLEM WAS */
					Exception THEPROBLEM = NOTIFICATION.getException();
					THEPROBLEM.printStackTrace();
					/*
					 * IF THE PROBLEM WAS AN ERROR-RESPONSE PACKET RETURNED BY
					 * APPLE, GET IT
					 */
					ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
					if (THEERRORRESPONSE != null) {
						// logger.info(THEERRORRESPONSE.getMessage());
					}
				}
			}
		} catch (CommunicationException e) {
			logger.error(e, e);
		} catch (KeystoreException e) {
			logger.error(e, e);
		} catch (JSONException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
		// GcmData gcmData = new GcmData(regId, userMessage, messageId,
		// new Date(), errCode);
		// gcmDataDAO.insertGcm(gcmData);
		return messageId;
	}
	

	
	public String getDecodedValue(String encodedValue) {
		try{
			Properties prop = new Properties();
			InputStream inputStream = ActionUtils.class.getClassLoader()
					.getResourceAsStream("application.properties");
			prop.load(inputStream);
			String key = prop.getProperty("dream.decryption.key");
			String initVector = prop.getProperty("dream.decryption.iv");
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encodedValue));
			return new String(original);
		}catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}
	
	public String getPlayerShortType(String type){
		String shortType = "";
		if(type.equalsIgnoreCase("Batsman")){
			shortType = "BAT";
		}else if(type.equalsIgnoreCase("Bowler")){
			shortType = "BOWl";
		}else if(type.equalsIgnoreCase("Allrounder")){
			shortType = "AR";
		}else if(type.equalsIgnoreCase("Wicketkeeper	")){
			shortType = "WK";
		}
		return shortType;
	}
	
	public String sendCommand(String regId, String userMessage) throws IOException {
		String messageId = "gcmmessageid" + System.currentTimeMillis();
		try {
			String FMCurl = "https://fcm.googleapis.com/fcm/send";// URL to push
																	// data;
			final String KEY_NOTIFICATION = "data";
			HttpClient httpClient = new HttpClient();
			JSONObject json = new JSONObject();
			json.put("to", regId.trim());
			json.put("content_available", true);
			json.put("mutable_content", true);
			json.put("priority", "high");
			JSONObject notification_pay_load = new JSONObject();
			notification_pay_load.put(MESSAGE_KEY, userMessage);
			notification_pay_load.put("messageId", messageId);
			json.put(KEY_NOTIFICATION, notification_pay_load);
			logger.info(json.toString());

			Properties prop = new Properties();
			InputStream inputStream = (InputStream) ActionUtils.class.getClassLoader()
					.getResourceAsStream("application.properties");
			prop.load(inputStream);
			final String GOOGLE_SERVER_KEY = prop.getProperty("dreamcric.andriod.key");		
			PostMethod method = new PostMethod(FMCurl);
			method.setRequestHeader("Authorization", "key=" + GOOGLE_SERVER_KEY);
			method.setRequestHeader("Content-Type", "application/json");
			StringRequestEntity requestEntity = new StringRequestEntity(json.toString(), "application/json", "UTF-8");
			method.setRequestEntity(requestEntity);
			int statusCode = httpClient.executeMethod(method);
			logger.debug(statusCode);

		} catch (Exception e) {
			logger.error(e, e);
		}
		// GcmData gcmData = new GcmData(regId, userMessage, messageId,
		// new Date(), errCode);
		// gcmDataDAO.insertGcm(gcmData);
		return messageId;
	}
	
	public String sendCommandWithMatchId(String regId, String userMessage, int matchId) throws IOException {
		String messageId = "gcmmessageid" + System.currentTimeMillis();
		try {
			String FMCurl = "https://fcm.googleapis.com/fcm/send";// URL to push
																	// data;
			final String KEY_NOTIFICATION = "data";
			HttpClient httpClient = new HttpClient();
			JSONObject json = new JSONObject();
			json.put("to", regId.trim());json.put("content_available", true);
			json.put("mutable_content", true);
			json.put("priority", "high");
			JSONObject notification_pay_load = new JSONObject();
			notification_pay_load.put(MESSAGE_KEY, userMessage);
			notification_pay_load.put("messageId", messageId);
			notification_pay_load.put("matchId", matchId);
			json.put(KEY_NOTIFICATION, notification_pay_load);
			logger.info(json.toString());

			Properties prop = new Properties();
			InputStream inputStream = (InputStream) ActionUtils.class.getClassLoader()
					.getResourceAsStream("application.properties");
			prop.load(inputStream);
			final String GOOGLE_SERVER_KEY = prop.getProperty("dreamcric.andriod.key");		
			PostMethod method = new PostMethod(FMCurl);
			method.setRequestHeader("Authorization", "key=" + GOOGLE_SERVER_KEY);
			method.setRequestHeader("Content-Type", "application/json");
			StringRequestEntity requestEntity = new StringRequestEntity(json.toString(), "application/json", "UTF-8");
			method.setRequestEntity(requestEntity);
			int statusCode = httpClient.executeMethod(method);
			logger.debug(statusCode);

		} catch (Exception e) {
			logger.error(e, e);
		}
		// GcmData gcmData = new GcmData(regId, userMessage, messageId,
		// new Date(), errCode);
		// gcmDataDAO.insertGcm(gcmData);
		return messageId;
	}
	
	public String sendIOSNotificationWithMAtchId(String gcmId, String message, int matchId) throws MalformedURLException, IOException {
		// logger.info("inside sendIOSNotification >>"+gcmId);
		String messageId = "gcmmessageid" + System.currentTimeMillis();
		// BasicConfigurator.configure();
		try {
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(1);
			payload.addSound("default");
			payload.addCustomDictionary("messageId", messageId);
			payload.addCustomDictionary("matchId", matchId);
			Properties prop = new Properties();
			InputStream inputStream = ActionUtils.class.getClassLoader().getResourceAsStream("application.properties");
			prop.load(inputStream);
			// System.out.println(payload.toString());
			String path = prop.getProperty("dreamcric.ios.cert.path");
			String password = prop.getProperty("dreamcric.ios.cert.password");
			// logger.info("cert path >>"+path);
			// InputStream is = new URL(path).openStream();
			List<PushedNotification> NOTIFICATIONS = Push.payload(payload, path, password, false, gcmId);
			for (PushedNotification NOTIFICATION : NOTIFICATIONS) {
				if (NOTIFICATION.isSuccessful()) {
					/* APPLE ACCEPTED THE NOTIFICATION AND SHOULD DELIVER IT */
					/*
					 * logger.info("PUSH NOTIFICATION SENT SUCCESSFULLY TO: " +
					 * NOTIFICATION.getDevice().getToken()+"\nmessage::" +
					 * message + " \nGCMID::" + gcmId + " messageId::" +
					 * messageId);
					 */
					/* STILL NEED TO QUERY THE FEEDBACK SERVICE REGULARLY */
				} else {
					String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
					/*
					 * ADD CODE HERE TO REMOVE INVALIDTOKEN FROM YOUR DATABASE
					 */
					/* FIND OUT MORE ABOUT WHAT THE PROBLEM WAS */
					Exception THEPROBLEM = NOTIFICATION.getException();
					THEPROBLEM.printStackTrace();
					/*
					 * IF THE PROBLEM WAS AN ERROR-RESPONSE PACKET RETURNED BY
					 * APPLE, GET IT
					 */
					ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
					if (THEERRORRESPONSE != null) {
						// logger.info(THEERRORRESPONSE.getMessage());
					}
				}
			}
		} catch (CommunicationException e) {
			logger.error(e, e);
		} catch (KeystoreException e) {
			logger.error(e, e);
		} catch (JSONException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
		// GcmData gcmData = new GcmData(regId, userMessage, messageId,
		// new Date(), errCode);
		// gcmDataDAO.insertGcm(gcmData);
		return messageId;
	}
	
	public static final int parseInt(String val) {
		if (val != null && !val.isEmpty()) {
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
			}
		}
		return 0;
	}

}
