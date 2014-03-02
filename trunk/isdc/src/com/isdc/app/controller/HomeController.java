package com.isdc.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isdc.app.domain.Settings;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.SecurityService;
import com.isdc.app.service.SettingsService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/")
public class HomeController {
		
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
	@Resource(name="SecurityService")
	private SecurityService securityservice;
	
	@RequestMapping("/")
	public String getHomePage() {			
		logger.debug("HomeController : getHomePage");	
		return "login";
	} 
	
	@RequestMapping("/dashboard")
	public String getDashboardPage(HttpSession session) {			
		logger.debug("HomeController : getDashboardPage");
		session.setAttribute("securitycode","1");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		return "dashboard";
	} 
	
	@RequestMapping(value = "/securityexception", method = RequestMethod.GET)
	public String getSecurityExceptionPage(Model model) {			
		logger.debug("HomeController : getSecurityExceptionPage");
		Settings settings = settingsservice.getSingleByLabel("key01");
		settings.setSettings_value(null);
		model.addAttribute("settings", settings);
		return "securityexception";
	} 
	
	@RequestMapping(value = "/securityexception", method = RequestMethod.POST)
    public String getSecurityExceptionPage(@ModelAttribute(value="settings") @Valid Settings settings, BindingResult result, Model model, HttpSession session) {
		logger.debug("HomeController : getSecurityExceptionPage : POST");	
		if (result.hasErrors() || settings.getSettings_value() == null || settings.getSettings_value().length() == 0) {
			if(settings.getSettings_value() == null || settings.getSettings_value().length() == 0){
				//result.addError("");
				model.addAttribute("keyerror", "Security key cannot be blank");
			}
			return "securityexception";
		}else{		
			if(session.getAttribute("securitycode").equals("2")){
				logger.info("HomeController : getSecurityExceptionPage1: "+session.getAttribute("securitycode"));
				settings.setSettings_label("key01");
				String keyEntered = settings.getSettings_value();
				String decryptedString = null;
				try{
					byte[] encryptedString = Base64.decodeBase64(keyEntered);
					decryptedString = GlobalFunctions.decryptAES(encryptedString);
				}
				catch(Exception ex){
					logger.info("HomeController : getSecurityExceptionPage3: "+ex.getMessage());
				}
				if(decryptedString != null){
					String delims = "[:]+";
					String[] tokens = decryptedString.split(delims);									
					settingsservice.editSingle(settings);					
					String strTemp =  tokens[0]+":0:0";
					byte[] encryptedString = GlobalFunctions.encryptAES(strTemp);
					String value = Base64.encodeBase64String(encryptedString);
					Settings settings02 = settingsservice.getSingleByLabel("key02");
					settings02.setSettings_value(value);
					settingsservice.editSingle(settings02);				
					session.setAttribute("securitycode", "1");
					GlobalFunctions.securityKeyUsername = tokens[0];
					securityservice.processSecurityThread();
				}			
				String message = "create-success-key";
				return "redirect:/dashboard?message="+message;
			}else{
				logger.info("HomeController : getSecurityExceptionPage2: "+session.getAttribute("securitycode"));
				String keyerror = "Security key error, enter valid key or contact customer support";
				return "redirect:/securityexception?keyerror="+keyerror;
			}			
		}
	}
	
}