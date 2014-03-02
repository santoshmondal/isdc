package com.isdc.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.isdc.app.domain.AuthorizationRole;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.AuthorizationRoleService;
import com.isdc.app.service.MainAccountService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/account")
public class MainAccountController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@Resource(name="AuthorizationRoleService")
	private AuthorizationRoleService authorizationroleservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model) {
    	
    	logger.debug("MainAccountController : getDashboard");
    	List<MainAccount> mainaccountlist = mainaccountservice.getAll();
    	model.addAttribute("mainaccountlist", mainaccountlist);  	
    	return "account/dashboard";	
	}
	
	// Check username availibilty
 	@RequestMapping(value="/availability", method=RequestMethod.GET)
 	public @ResponseBody Boolean getAvailability(@RequestParam String name) {	
 		logger.debug("Received request getAvailability: "+name);
 		
 		MainAccount mainaccount = mainaccountservice.getByUsername(name);
 		
 		if(mainaccount == null){
 			return true;
 		}else{
 			return false;
 		}	    
 	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMainAccount(Model model, HttpSession session) {
		logger.debug("MainAccountController : addMainAccount : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		MainAccount mainaccount = new MainAccount();
		model.addAttribute("mainaccount", mainaccount);
		List<AuthorizationRole> authorizationrolearray = authorizationroleservice.getAll();
    	model.addAttribute("authorizationrolearray", authorizationrolearray); 
		return "account/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMainAccount(@ModelAttribute(value="mainaccount") @Valid MainAccount mainaccount, BindingResult result, Model model) {
		logger.debug("MainAccountController : addMainAccount : POST");		
		if (result.hasErrors()) {
			List<AuthorizationRole> authorizationrolearray = authorizationroleservice.getAll();
	    	model.addAttribute("authorizationrolearray", authorizationrolearray);
			return "account/add";
		}
		else{
			mainaccountservice.addSingle(mainaccount);
			String message = "create-success";
			return "redirect:/account/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	/*public @ResponseBody ValidationResponse processFormAjaxJsonAdd(Model model, @ModelAttribute(value="tag") @Valid Tag tag, BindingResult result ){*/
    public @ResponseBody ValidationResponse addMainAccountJson(Model model, @ModelAttribute(value="mainaccount") @Valid MainAccount mainaccount, BindingResult result) {
		logger.debug("ProductMasterController : addMainAccountJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){
			//TODO here is some problem - any problem in addSingle()
			// still return success with response 
			res.setStatus("SUCCESS");
			mainaccountservice.addSingle(mainaccount);
		}
		else{
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
    public String editMainAccount(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("MainAccountController : editMainAccount : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		MainAccount mainaccount = mainaccountservice.getSingle(id);
		model.addAttribute("mainaccount", mainaccount);
		List<AuthorizationRole> authorizationrolearray = authorizationroleservice.getAll();
    	
    	List<AuthorizationRole> authorizationrolearraytemp = new ArrayList<AuthorizationRole>(authorizationrolearray.size());
    	Iterator<AuthorizationRole> itrAuthorizationRole = authorizationrolearray.iterator();
    	int cnt = 0;  
    	while(itrAuthorizationRole.hasNext()){
    		AuthorizationRole authorizationRole = itrAuthorizationRole.next(); 
			for (AuthorizationRole authorizationRoletemp : mainaccount.getAuthorizationroleset()) {				
				if(authorizationRole.getAuthorization_role_id() == authorizationRoletemp.getAuthorization_role_id()){
					authorizationRole.setAuthorization_role_selected_status("selected");
					authorizationrolearray.set(cnt, authorizationRole);
				}		
			}	
			if(authorizationRole.getAuthorization_role_selected_status() == null){
				authorizationRole.setAuthorization_role_selected_status(" ");
			}
			authorizationrolearraytemp.add(authorizationRole);
			cnt++;
		} 
    	model.addAttribute("authorizationrolearray", authorizationrolearraytemp);
    	
		return "account/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editMainAccount(@ModelAttribute(value="mainaccount") @Valid MainAccount mainaccount, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("MainAccountController : editMainAccount : POST : "+mainaccount.getMain_account_id());
		if (result.hasErrors()) {
			return "account/edit";
		}else{
			mainaccountservice.editSingle(mainaccount);
			String message = "update-success";
			return "redirect:/account/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	/*public @ResponseBody ValidationResponse processFormAjaxJsonAdd(Model model, @ModelAttribute(value="tag") @Valid Tag tag, BindingResult result ){*/
    public @ResponseBody ValidationResponse editMainAccountJson(Model model, @ModelAttribute(value="mainaccount") @Valid MainAccount mainaccount, BindingResult result) {
		logger.debug("ProductMasterController : editMainAccountJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			mainaccountservice.editSingle(mainaccount);
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

}
