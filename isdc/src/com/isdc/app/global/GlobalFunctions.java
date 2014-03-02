package com.isdc.app.global;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class GlobalFunctions {

	//static String key = "Bar12345Bar12345"; // 128 bit key
	static String key = "Isdc@2014JN@2014"; // 128 bit key
	public static String securityKeyUsername = null;
	public static String securityKey02 = null;
	
	public static UserDetails currentUserDetails(){
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();        
	        return (UserDetails) (principal instanceof UserDetails ? principal : null);
	    }
	    return null;
	}
	
	public static String SHAHashingExample(String password) {
		try{
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(password.getBytes());
	 
	        byte byteData[] = md.digest(); 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
		}
		catch(Exception ex){		
			ex.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encryptAES(String text){
		byte[] encrypted = null;
		try{
			// Create key and cipher
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
			 // encrypt the text
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         encrypted = cipher.doFinal(text.getBytes());
	         //tempString = new String(encrypted);
	         return encrypted;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return encrypted;
	}
	
	public static String decryptAES(byte[] encrypted){
		String tempString = null;
		try{
			// Create key and cipher
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
			 
	         cipher.init(Cipher.DECRYPT_MODE, aesKey);
	         tempString = new String(cipher.doFinal(encrypted));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return tempString;
	}
}
