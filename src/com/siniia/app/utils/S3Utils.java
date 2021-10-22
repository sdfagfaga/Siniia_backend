package com.siniia.app.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.net.URL;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.siniia.app.controller.ProductController;

public class S3Utils {

private static final Logger logger = LogManager.getLogger(S3Utils.class);
	
	
	public static String uploadByteArray(String keyName, byte[] uploadFileBytes, String contentType) throws IOException {
		String uploaded = "0";
		try {
			Properties prop = new Properties();
			InputStream inputStream = ProductController.class.getClassLoader()
					.getResourceAsStream("application.properties");
				prop.load(inputStream);
			String access_key_id = prop.getProperty("aws.access_key_id");
			String secret_access_key = prop.getProperty("aws.secret_access_key");
			AWSCredentials credentials = new BasicAWSCredentials(access_key_id,secret_access_key);
			AmazonS3 s3Client = new AmazonS3Client(credentials);
			String bucketName = prop.getProperty("aws.s3.bucket");			
			InputStream is = new ByteArrayInputStream(uploadFileBytes);
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(uploadFileBytes.length);
			meta.setContentType(contentType);
			s3Client.putObject(new PutObjectRequest(bucketName, keyName, is, meta).withCannedAcl(CannedAccessControlList.PublicRead));
			URL url = s3Client.getUrl(bucketName, keyName);
			String sharableLink = url.toExternalForm();
			logger.info(sharableLink);
			logger.info("file uploaded");
			uploaded = sharableLink;
		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("HTTP Status Code: " + ase.getStatusCode());
			logger.error("AWS Error Code:   " + ase.getErrorCode());
			logger.error("Error Type:       " + ase.getErrorType());
			logger.error("Request ID:       " + ase.getRequestId());
			uploaded = "0";

		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException: ");
			logger.error("Error Message: " + ace.getMessage());
			uploaded = "0";
		}
		return uploaded;
	}
	
	
	/*public static byte[] getFilesAllBytes(String keyName) {
		byte[] bytes = null;
		try {
			S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
			InputStream stream = s3object.getObjectContent();
			bytes = IOUtils.toByteArray(stream);
			return bytes;
		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("HTTP Status Code: " + ase.getStatusCode());
			logger.error("AWS Error Code:   " + ase.getErrorCode());
			logger.error("Error Type:       " + ase.getErrorType());
			logger.error("Request ID:       " + ase.getRequestId());
			return bytes;
		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException: ");
			logger.error("Error Message: " + ace.getMessage());
			return bytes;
		} catch (IOException ioe) {
			logger.error("Caught an IOException: ");
			logger.error("Error Message: " + ioe.getMessage());
			return bytes;
		}
	}*/
}
