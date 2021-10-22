package com.siniia.app.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.http.apache.request.impl.HttpGetWithBody;
import com.google.gson.Gson;

@Controller
public class PaypalPaymentController {

	private static final Log logger = LogFactory.getLog(PaypalPaymentController.class);

	private Gson gson = new Gson();
	private String clientId = "Ae8IC5moctJz2K7lwkexQPD-qYUoQmSP2WsUaNQsFjXsUAJOYvH6POTezyO8Zb9FiqS9gWsrGNqVHpeV";
	private String clientSecret = "EDK3TmgNBFkoR6L8Ni-EUu22MU3o_Lm6cmk7dX92DIT4w1VpR-JmasCKhLbDXswSTUeA09hhbzspUpp-";

	private String localClientId = "Aa6_Ap-VpJEqH4NnUhtQ8GQRX0nxkfiUrWp7DYO_lIhjpxKvgAPyAvmC-DZnnJFLLVIyFEbqHfmlQ6-K";
	private String localClientSecret = "EOP_6nHMuryA6L0hm6BrgUkn8tjlZQ9YUnV68FD5tKP23Ufb_3YRtarTc1jvKeNyeDi1SFjLSKyYbXG4";

	@RequestMapping(value = "/generateToken", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String generateToken(HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {

			String base = clientId + ":" + clientSecret;
			String encoding = Base64.getEncoder().encodeToString((base).getBytes());

			logger.info("encoding >> " + encoding.toString());

			CredentialsProvider credP = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(clientId, clientSecret);
			credP.setCredentials(AuthScope.ANY, credentials);
			org.apache.http.client.HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credP)
					.build();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
			nvps.add(new BasicNameValuePair(localClientId, localClientSecret));
			HttpPost post = new HttpPost("https://api.sandbox.paypal.com/v1/oauth2/token");
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			post.setHeader("authorization", "Bearer Access-Token");
			MediaType med = MediaType.APPLICATION_FORM_URLENCODED;

			HttpResponse response = client.execute(post);

			int statusCode = response.getStatusLine().getStatusCode();

			logger.info("response >> " + response);

			/*
			 * URL url = new URL
			 * ("https://api-m.sandbox.paypal.com/v1/oauth2/token"); String base
			 * = clientId +":"+clientSecret; String encoding =
			 * Base64.getEncoder().encodeToString((base).getBytes());
			 * HttpURLConnection connection = (HttpURLConnection)
			 * url.openConnection(); connection.setRequestMethod("POST");
			 * connection.setDoOutput(true);
			 * connection.setRequestProperty("grant_type",
			 * "client_credentials");
			 * connection.setRequestProperty("Authorization", "Basic " +
			 * encoding); connection.setRequestProperty("content-type",
			 * "application/x-www-form-urlencoded"); InputStream content =
			 * (InputStream) connection.getInputStream(); BufferedReader in =
			 * new BufferedReader (new InputStreamReader (content)); String
			 * line; while ((line = in.readLine()) != null) {
			 * System.out.println(line); }
			 */

			/*
			 * MediaType mediaType = MediaType.APPLICATION_FORM_URLENCODED;
			 * //parse(application/x-www-form-urlencoded); RequestBody body =
			 * RequestBody.create(mediaType,
			 * "grant_type=refresh_token&refresh_token={refresh_token}");
			 * Request request = new Request.Builder() .url(
			 * "https://api-m.sandbox.paypal.com/v1/identity/openidconnect/tokenservice")
			 * .post(body) .addHeader("authorization",
			 * "Basic Y2xpZW50SUQ6Y2xpZW50U2VjcmV0") .addHeader("content-type",
			 * "application/x-www-form-urlencoded") .build();
			 * 
			 * Response response = client.newCall(request).execute();
			 */
		} catch (Exception e) {
			logger.error(e, e);
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to generate Token");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/generateAPIToken", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String generateAPIToken(HttpServletRequest request) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			String base = clientId + ":" + clientSecret;
			String encoding = Base64.getEncoder().encodeToString((base).getBytes());
			String url = "https://api.paypal.com/v1/oauth2/token";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("accept", "application/json");
			con.setRequestProperty("accept-language", "en_US");
			con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Authorization", "Basic " + encoding);
			String body = "grant_type=client_credentials";

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

			// Print the response
			JSONObject job = new JSONObject(response.toString());
			// logger.info(">> job >>"+job.getString("access_token"));
			jsonObject.put("Status", 200);
			jsonObject.put("Description", "Access Token generated Successfully");
			jsonObject.put("access_token", job.getString("access_token"));
			jsonObject.put("token_type", job.getString("token_type"));
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to generate Access Token");
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/createPayout", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String createPayout(HttpServletRequest request, @RequestBody String userData)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			if (userData != null) {
				String base = localClientId + ":" + localClientSecret;
				String encoding = Base64.getEncoder().encodeToString((base).getBytes());
				String tokenurl = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
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

				BufferedReader tokenin = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String tokeninputLine;
				StringBuffer responses = new StringBuffer();
				while ((tokeninputLine = tokenin.readLine()) != null) {
					responses.append(tokeninputLine);
				}
				tokenin.close();

				// Print the response
				JSONObject job = new JSONObject(responses.toString());
				logger.info(">> job >>" + job.getString("access_token"));

				String url = "https://api-m.sandbox.paypal.com/v1/payments/payouts";
				URL obj = new URL(url);
				HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Authorization", "Bearer "+job.getString("access_token"));
				con.setRequestProperty("Content-Type", "application/json");
				String body = userData;

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

				// Print the response
				System.out.println(response.toString());
				logger.info("response >> " + response.toString());
			} else {
				jsonObject.put("Status", 400);
				jsonObject.put("Description", "No Data Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			jsonObject.put("Status", 500);
			jsonObject.put("Description", "Failed to create Payouts");
		}
		return jsonObject.toString();
	}
}
