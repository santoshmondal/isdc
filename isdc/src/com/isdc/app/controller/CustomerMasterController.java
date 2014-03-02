package com.isdc.app.controller;

import java.util.ArrayList;
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

import com.isdc.app.domain.AreaMaster;
import com.isdc.app.domain.BeatMaster;
import com.isdc.app.domain.CustomerBalance;
import com.isdc.app.domain.CustomerMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.AreaMasterService;
import com.isdc.app.service.BeatMasterService;
import com.isdc.app.service.CustomerBalanceService;
import com.isdc.app.service.CustomerMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/customer")
public class CustomerMasterController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="CustomerMasterService")
	private CustomerMasterService customermasterservice;
	
	@Resource(name="AreaMasterService")
	private AreaMasterService areamasterservice;
	
	@Resource(name="BeatMasterService")
	private BeatMasterService beatmasterservice;
	
	@Resource(name="CustomerBalanceService")
	private CustomerBalanceService customerbalanceervice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
    	
    	logger.debug("CustomerMasterController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<CustomerMaster> customermasterlist = customermasterservice.getAll();
    	model.addAttribute("customermasterlist", customermasterlist);  	   	
    	return "customer/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCustomerMaster(Model model, HttpSession session) {
		logger.debug("CustomerMasterController : addCustomerMaster : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		CustomerMaster customermaster = new CustomerMaster();
		model.addAttribute("customermaster", customermaster);
		List<AreaMaster> areamasterarray = areamasterservice.getAll();
    	model.addAttribute("areamasterarray", areamasterarray); 
    	if(areamasterarray.size() > 0){
    		List<BeatMaster> beatmasterarray = beatmasterservice.getByArea(areamasterarray.get(0).getArea_id());
    		model.addAttribute("beatmasterarray", beatmasterarray); 
    	}
		return "customer/add";
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addAreaMasterJson(Model model, @ModelAttribute(value="customermaster") @Valid CustomerMaster customermaster, BindingResult result) {
		logger.debug("CustomerMasterController : addCustomerMaster : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			customermasterservice.addSingle(customermaster);
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
    public String addAreaMaster(@ModelAttribute(value="customermaster") @Valid CustomerMaster customermaster, BindingResult result, Model model) {
		logger.debug("CustomerMasterController : addCustomerMaster : POST");		
		if (result.hasErrors()) {
			return "customer/add";
		}else{
			customermasterservice.addSingle(customermaster);
			String message = "create-success";
			return "redirect:/customer/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editCustomerMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("CustomerMasterController : editCustomerMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		CustomerMaster customermaster = customermasterservice.getSingle(id);
		model.addAttribute("customermaster", customermaster);
		List<AreaMaster> areamasterarray = areamasterservice.getAll();
    	model.addAttribute("areamasterarray", areamasterarray); 
    	if(customermaster.getAreamaster().getArea_id() > 0){
    		List<BeatMaster> beatmasterarray = beatmasterservice.getByArea(customermaster.getAreamaster().getArea_id());
    		model.addAttribute("beatmasterarray", beatmasterarray); 
    	}
		return "customer/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCustomerMaster(@ModelAttribute(value="customermaster") @Valid CustomerMaster customermaster, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("CustomerMasterController : editCustomerMaster : POST"+customermaster.getCustomer_id());
		if (result.hasErrors()) {
			return "customer/edit";
		}else{
			customermasterservice.editSingle(customermaster);
			String message = "update-success";
			return "redirect:/customer/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editCustomerMasterJson(Model model, @ModelAttribute(value="customermaster") @Valid CustomerMaster customermaster, BindingResult result) {
		logger.debug("CustomerMasterController : editCustomerMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			customermasterservice.editSingle(customermaster);
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
    public String deleteCustomerMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("CustomerMasterController : deleteCustomerMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		List<CustomerBalance> customerbalancelist = customerbalanceervice.getByCustomer(id);
		
		if(customerbalancelist != null && !customerbalancelist.isEmpty()){
			String message = "delete-fail";
			return "redirect:/customer/dashboard?message="+message;
		}
		
		customermasterservice.deleteSingle(id);
		String message = "delete-success";
		return "redirect:/customer/dashboard?message="+message;
	}
	
	
	@RequestMapping(value = "/select.json", method = RequestMethod.GET)
    //public @ResponseBody ValidationResponse selectCustomerMasterJson(Model model, @ModelAttribute(value="customermaster") @Valid CustomerMaster customermaster, BindingResult result) {
	public @ResponseBody ValidationResponse selectCustomerMasterJson(@RequestParam(value="id", required=true) String id) {
		logger.debug("CustomerMasterController : selectCustomerMasterJson : GET");
		ValidationResponse res = new ValidationResponse();
		res.setStatus("FAIL");
		if(id != null){
			List<BeatMaster> beatmasterlist = beatmasterservice.getByArea(Integer.valueOf(id));
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (BeatMaster beatmaster : beatmasterlist) {
				errorMesages.add(new ErrorMessage( Integer.toString(beatmaster.getBeat_id()), beatmaster.getBeat_name() ));
			}
			res.setErrorMessageList(errorMesages);			
			res.setStatus("SUCCESS");
		}
		return res;
	}
	
}
