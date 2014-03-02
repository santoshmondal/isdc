package com.isdc.app.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

import com.isdc.app.domain.Log;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.Settings;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.LogService;
import com.isdc.app.service.MainAccountService;
import com.isdc.app.service.SecurityService;
import com.isdc.app.service.SettingsService;
 
@SuppressWarnings("restriction")
@Controller
@RequestMapping("/")
public class AccessController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="MainAccountService")
	private MainAccountService mainAccountService;
	
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
	@Resource(name="SecurityService")
	private SecurityService securityservice;
	
	@Resource(name="LogService")
	private LogService logService;
		
	@RequestMapping(value = "/login")
	public String login(Model model, @RequestParam(required=false) String message,@RequestParam(required=false) String fp, HttpSession session) {
		logger.debug("Received request to show login");
		model.addAttribute("message", message);
		return "/login";
	}
	
	@RequestMapping(value = "/denied")	
 	public String denied() {
		logger.debug("Received request to show denied");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/login/failure")
 	public String loginFailure(HttpServletRequest request,HttpSession session) throws URISyntaxException {
		logger.debug("Received request to show loginFailure");
		String message = "fail";
		return "redirect:/login?message="+message;
	}
	
	@RequestMapping(value = "/logout/success")
 	public String logoutSuccess(HttpSession session) {
		logger.debug("Received request to show logoutSuccess");	
		return "redirect:/";
	}
		
	@RequestMapping(value = "/logindetails")
	public String getLoginDetails(HttpServletRequest request,HttpSession session){
		
		String session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
		String agent="";
		String ipAddress = request.getHeader("X-FORWARDED-FOR"); 	
		if (ipAddress == null) {  
		   ipAddress = request.getRemoteAddr();  
		   agent = request.getHeader("User-Agent");
		}	   
		Log log = new Log();
		log.setSession_id(session_id);
		log.setIp_address(ipAddress);
		log.setUser_agent(agent);
		   	   	   
		try{
		   String username = GlobalFunctions.currentUserDetails().getUsername();
		   MainAccount currentUser = mainAccountService.getByUsername(username);
		   if(currentUser != null){
			   session.setAttribute("currentLoginID", currentUser.getMain_account_id());
			   session.setAttribute("currentLoginUsername", username);
			   log.setUser_id(currentUser.getMain_account_id());
			   logService.add(log);
		   }
		}
		catch(Exception ex){
		   ex.printStackTrace();
		}	
		
		session.setAttribute("securitycode", "1");
		return "redirect:/dashboard";
		
		/*if(securityCheck(session)){
			securityservice.processSecurityThread();
			return "redirect:/dashboard";
		}else{
			return "redirect:/securityexception";
		}*/		 		
	}
		
	@RequestMapping(value = "/logoutdetails")
	public String getLogout(){		
		String session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
		logger.debug("Session-id: " + session_id);		 
		List<Log> log = logService.getLogBySession(session_id);	
		if(!log.isEmpty()){
			logService.addLogoutTime(log.get(0).getId());	
		}
		return "redirect:/j_spring_security_logout";		
	}
	
	/*
	 * security codes
	 * 1 - valid
	 * 2 - blank code first time start
	 * 3 - invalid / edited code
	 * 4 - hours lapsed
	 */
	private boolean securityCheck(HttpSession session){
		// CODE for checking security key starts here
		//Settings settings = settingsservice.getSingleByLabel("key01");
		List<Settings> settingsList =  settingsservice.getAll();
		String strKey01 = null;
		String strKey02 = null;
		//String strKey03 = null;
		//String strKey04 = null;
		for (Settings settings : settingsList) {
			if(settings.getSettings_label().equals("key01")){
				strKey01 = settings.getSettings_value();
				if(strKey01 != null && strKey01.length() == 0){
					strKey01 = null;
				}
			}else if(settings.getSettings_label().equals("key02")){
				strKey02 = settings.getSettings_value();
				if(strKey02 != null && strKey02.length() == 0){
					strKey02 = null;
				}
			}
		}
		if(strKey01 == null){		
			if(strKey02 == null){
				session.setAttribute("securitycode", "2");
				return false;
			}else{
				session.setAttribute("securitycode", "3");
				return false;
			}
		}else{
			if(strKey02 != null){
				session.setAttribute("securitycode", "1");
				
				String decryptedString1 = null;
				String decryptedString2 = null;
				try{
					byte[] encryptedString1 = Base64.decodeBase64(strKey01);
					decryptedString1 = GlobalFunctions.decryptAES(encryptedString1);
					
					byte[] encryptedString2 = Base64.decodeBase64(strKey02);
					decryptedString2 = GlobalFunctions.decryptAES(encryptedString2);
				}
				catch(Exception ex){
					logger.info("HomeController : getSecurityExceptionPage3: "+ex.getMessage());
				}			
				if(decryptedString1 != null && decryptedString2 != null){
					String delims = "[:]+";
					String[] tokens01 = decryptedString1.split(delims);
					String[] tokens02 = decryptedString2.split(delims);
					if(!tokens01[0].equals(tokens02[0])){
						session.setAttribute("securitycode", "3");
						return false;
					}else{				
						int intDays = Integer.parseInt(tokens01[1]);
						int intHours = Integer.parseInt(tokens01[2]);
						int intTotalDuration  = intDays * (intHours * 60);
						int currentTiming = Integer.parseInt(tokens02[2]);
						logger.info("HomeController : getSecurityExceptionPageCalculation: "+intDays+" : "+intHours+" : "+intTotalDuration+" : "+currentTiming);
						if(currentTiming<intTotalDuration){
							GlobalFunctions.securityKeyUsername = tokens01[0];
						}else{
							session.setAttribute("securitycode", "4");
							return false;
						}		
					}
				}
				return true;
			}else{
				session.setAttribute("securitycode", "3");
				return false;
			}
		}
	}
	
}