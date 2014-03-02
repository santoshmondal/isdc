package com.isdc.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isdc.app.domain.Settings;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.SettingsService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/settings")
public class SettingsController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
    	
    	logger.debug("SettingsController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<Settings> settingslist = settingsservice.getAll();
    	model.addAttribute("settingslist", settingslist);  	   	
    	return "settings/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addSettings(Model model, HttpSession session) {
		logger.debug("SettingsController : addSettings : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		Settings settings = new Settings();
		model.addAttribute("settings", settings);
		return "settings/add";
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addSettingsJson(Model model, @ModelAttribute(value="settings") @Valid Settings settings, BindingResult result) {
		logger.debug("SettingsController : addSettingsJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			settingsservice.addSingle(settings);
		}else{
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
			}
			res.setErrorMessageList(errorMesages);		
		}	
		return res;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addSettings(@ModelAttribute(value="settings") @Valid Settings settings, BindingResult result, Model model) {
		logger.debug("SettingsController : addSettings : POST");		
		if (result.hasErrors()) {
			return "settings/add";
		}else{
			settingsservice.addSingle(settings);
			String message = "create-success";
			return "redirect:/settings/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editSettings(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("SettingsController : editSettings : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		Settings settings = settingsservice.getSingle(id);
		model.addAttribute("settings", settings);
		return "settings/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editSettings(@ModelAttribute(value="settings") @Valid Settings settings, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("SettingsController : editSettings : POST"+settings.getSettings_id());
		if (result.hasErrors()) {
			return "settings/edit";
		}else{
			settingsservice.editSingle(settings);
			String message = "update-success";
			return "redirect:/settings/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/editinvoice", method = RequestMethod.GET)
    public String editSettingsInvoice(Model model, HttpSession session) {
		logger.debug("SettingsController : editSettingsInvoice : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		Settings settings = settingsservice.getSingleByLabel("invoice");
		model.addAttribute("settings", settings);
		return "settings/editinvoice";
	}
	
	@RequestMapping(value = "/editinvoice", method = RequestMethod.POST)
    public String editSettingsInvoice(@ModelAttribute(value="settings") @Valid Settings settings, 
    		BindingResult result, Model model) {
		logger.debug("SettingsController : editSettingsInvoice : POST"+settings.getSettings_id());
		if (result.hasErrors()) {
			return "settings/editinvoice";
		}else{
			settingsservice.editSingle(settings);
			String message = "update-success";
			return "redirect:/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/securitykey", method = RequestMethod.GET)
    public String editSettingsSecurityKey(Model model, HttpSession session) {
		logger.debug("SettingsController : editSettingsSecurityKey : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		Settings settings = settingsservice.getSingleByLabel("key01");
		String strKey01 = settings.getSettings_value();
		byte[] encryptedString1 = Base64.decodeBase64(strKey01);
		String decryptedString1 = GlobalFunctions.decryptAES(encryptedString1);
		String delims = "[:]+";
		String[] tokens01 = decryptedString1.split(delims);	
		model.addAttribute("tokens01", tokens01);
		model.addAttribute("totalhours", Integer.parseInt(tokens01[1])* ( Integer.parseInt(tokens01[2])*60) );
		
		Settings settings02 = settingsservice.getSingleByLabel("key02");
		String strKey02 = settings02.getSettings_value();
		byte[] encryptedString02 = Base64.decodeBase64(strKey02);
		String decryptedString02 = GlobalFunctions.decryptAES(encryptedString02);
		String[] tokens02 = decryptedString02.split(delims);	
		model.addAttribute("tokens02", tokens02);
		
		return "settings/securitykey";
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editSettingsJson(Model model, @ModelAttribute(value="settings") @Valid Settings settings, BindingResult result) {
		logger.debug("SettingsController : editSettingsJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			
			/*logger.info("original: "+settings.getSettings_value());
			byte[] encrypted = GlobalFunctions.encryptAES(settings.getSettings_value());
			String tempString = Base64.encodeBase64String(encrypted);
			
			logger.info("encrypted: "+tempString);
			logger.info("decrypted: "+GlobalFunctions.decryptAES(Base64.decodeBase64(tempString)));*/
			
			settingsservice.editSingle(settings);
		}else{
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
			}
			res.setErrorMessageList(errorMesages);		
		}	
		return res;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteSettings(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("SettingsController : deleteSettings : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		settingsservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/settings/dashboard?message="+message;
	}

}
