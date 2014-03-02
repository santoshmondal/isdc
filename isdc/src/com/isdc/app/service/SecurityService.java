package com.isdc.app.service;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.Settings;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("SecurityService")
@Transactional
public class SecurityService {
	
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
	protected static Logger logger = Logger.getLogger("service");

	@Async
	public void processSecurityThread() {
		try {  	
			Settings settings02 = settingsservice.getSingleByLabel("key02");	
			String usernameTemp = GlobalFunctions.securityKeyUsername;				
			byte[] encryptedString = Base64.decodeBase64(settings02.getSettings_value());
			String decryptedString = GlobalFunctions.decryptAES(encryptedString);		
			String delims = "[:]+";
			String[] tokens = decryptedString.split(delims);
			int intTemp = Integer.parseInt(tokens[2]);
			intTemp = intTemp + 1;
			int intTempStatic = 0;
			if(GlobalFunctions.securityKey02 != null){
				byte[] encryptedStringStatic = Base64.decodeBase64(GlobalFunctions.securityKey02);
				String decryptedStringStatic = GlobalFunctions.decryptAES(encryptedStringStatic);		
				String[] tokensStatic = decryptedStringStatic.split(delims);
				intTempStatic = Integer.parseInt(tokensStatic[2]);	
			}
			String strTemp =  usernameTemp+":"+Integer.parseInt(tokens[2])+":"+intTemp;
			encryptedString = GlobalFunctions.encryptAES(strTemp);
			strTemp = Base64.encodeBase64String(encryptedString);		
			if(GlobalFunctions.securityKey02 == null){

				settings02.setSettings_value(strTemp);
				try{
					settingsservice.updateTimer(settings02);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}else if(intTemp > intTempStatic){
				settings02.setSettings_value(strTemp);
				try{
					settingsservice.updateTimer(settings02);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			GlobalFunctions.securityKey02 = strTemp;
            Thread.sleep(60000);    
            processSecurityThread();
        } catch (InterruptedException e) {  
        	logger.info("Error "+e.getMessage());  
        }  
	}
}
