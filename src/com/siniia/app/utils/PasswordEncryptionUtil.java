package com.siniia.app.utils;

import java.security.MessageDigest;
import java.util.Random;

public class PasswordEncryptionUtil {
	
	public String getEncryptedPassword(String password) {
		String generatedKey=null;
		try{
			if(password!=null){
				MessageDigest algorithm = MessageDigest.getInstance("MD5"); 
				algorithm.update(password.getBytes());
		      	byte[] output = algorithm.digest();	      	
				generatedKey = bytesToHex(output).toLowerCase();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return generatedKey;
	}
	
	public static String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	    StringBuffer buf = new StringBuffer();
	    for (int j=0; j<b.length; j++) {
	    	buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	        buf.append(hexDigit[b[j] & 0x0f]);
	    }
	    return buf.toString();
	}
	
	//generate password
	public String getPasswordString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}