package com.isdc.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.MainAccountKeys;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.MainAccountKeysService;
import com.isdc.app.service.MainAccountService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/account/keys")
public class MainAccountKeysController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="MainAccountKeysService")
	private MainAccountKeysService mainaccountkeysservice;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, @RequestParam(value="masteraccountid", required=true) Integer masteraccountid, HttpSession session) {  	
    	logger.info("MainAccountKeysController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<MainAccountKeys> mainaccountkeyslist = mainaccountkeysservice.getByMasterAccount(masteraccountid);
    	model.addAttribute("mainaccountkeyslist", mainaccountkeyslist);  	   	
    	return "account/keys/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMainAccountKeys(Model model, @RequestParam(value="masteraccountid", required=true) Integer masteraccountid, HttpSession session) {
		logger.info("MainAccountKeysController : addMainAccountKeys : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		MainAccountKeys mainaccountkeys = new MainAccountKeys(); 
		mainaccountkeys.setMainaccountinteger(masteraccountid);
		model.addAttribute("mainaccountkeys", mainaccountkeys);
		return "account/keys/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addaccountKeys(@ModelAttribute(value="mainaccountkeys") @Valid MainAccountKeys mainaccountkeys, BindingResult result, 
    		@RequestParam(value="masteraccountid", required=true) Integer masteraccountid, Model model) {
		logger.info("MainAccountKeysController : addMainAccountKeys : POST"+mainaccountkeys.getKey_username());		
		if (result.hasErrors()) {
			logger.info("MainAccountKeysController : addMainAccountKeys : POST: "+result.getErrorCount());
			for (ObjectError error : result.getAllErrors()) {
				logger.info("error: "+error.getCode()+" : "+error.getObjectName()+" : "+error.getDefaultMessage());
			}
			return "account/keys/add";
		}else{
			try{
				Date date = new Date();
				mainaccountkeys.setKeys_commencement(date);
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				Date today = Calendar.getInstance().getTime();      
				String reportDate = df.format(today);
				String strTemp =  mainaccountkeys.getKey_username()+":"+mainaccountkeys.getKeys_days()+":"+mainaccountkeys.getKeys_hours()+":"+reportDate;
				logger.info("MainAccountKeysController : addMainAccountKeys : strTemp: "+strTemp);
				byte[] encryptedString = GlobalFunctions.encryptAES(strTemp);
				mainaccountkeys.setKeys_assigned(encryptedString);	
				String value = Base64.encodeBase64String(encryptedString);
				mainaccountkeys.setKeys_assigned_string(value);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			MainAccount mainaccount = mainaccountservice.getSingle(mainaccountkeys.getMainaccountinteger());
			mainaccountkeys.setMainaccount(mainaccount);
			mainaccountkeysservice.addSingle(mainaccountkeys);
			String message = "create-success";
			return "redirect:/account/keys/dashboard?message="+message+"&masteraccountid="+mainaccount.getMain_account_id();
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addMainAccountKeysJson(Model model, 
    		@ModelAttribute(value="mainaccountkeys") @Valid MainAccountKeys mainaccountkeys, 
    		BindingResult result) {
		logger.info("MainAccountKeysController : addMainAccountKeysJson : POST: ");	
		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			mainaccountkeysservice.addSingle(mainaccountkeys);
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
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editMainAccountKeys(@RequestParam(value="id", required=true) Integer id,  Model model,
    		@RequestParam(value="masteraccountid", required=true) Integer masteraccountid, HttpSession session) {
		logger.info("MainAccountKeysController : editMainAccountKeys : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		MainAccountKeys mainaccountkeys = mainaccountkeysservice.getSingle(id);
		mainaccountkeys.setMainaccountinteger(mainaccountkeys.getMainaccount().getMain_account_id());	
		logger.info("MainAccountKeysController : addMainAccountKeys : decrypted: "+GlobalFunctions.decryptAES(mainaccountkeys.getKeys_assigned()));	
		model.addAttribute("mainaccountkeys", mainaccountkeys);
		return "account/keys/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editMainAccountKeys(@ModelAttribute(value="mainaccountkeys") @Valid MainAccountKeys mainaccountkeys, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, 
    		@RequestParam(value="masteraccountid", required=true) Integer masteraccountid, Model model) {
		logger.info("MainAccountKeysController : editMainAccountKeys : POST"+mainaccountkeys.getKey_id());
		if (result.hasErrors()) {
			return "account/keys/edit";
		}else{
			mainaccountkeysservice.editSingle(mainaccountkeys);
			String message = "update-success";
			return "redirect:/account/keys/dashboard?message="+message+"&masteraccountid="+mainaccountkeys.getMainaccountinteger();
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editMainAccountKeysJson(Model model, @ModelAttribute(value="mainaccountkeys") @Valid MainAccountKeys mainaccountkeys, BindingResult result) {
		logger.info("MainAccountKeysController : editMainAccountKeysJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			mainaccountkeysservice.editSingle(mainaccountkeys);
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
    public String deleteMainAccountKeys(@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="masteraccountid", required=true) Integer masteraccountid, Model model, HttpSession session) {
		logger.info("MainAccountKeysController : deleteMainAccountKeys : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		mainaccountkeysservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/account/keys/dashboard?message="+message+"&masteraccountid="+masteraccountid;
	}

}
